<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="运动员">
              <!--              <a-input placeholder="请输入运动员" v-model="queryParam.athleteId"></a-input>-->
              <j-search-select-tag v-decorator="['athleteId']" dict="tb_edu_athlete,athlete_name,id" />
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="所属测试">
              <!--              <a-input placeholder="请输入所属测试" v-model="queryParam.testId"></a-input>-->
              <j-search-select-tag v-decorator="['testId']" dict="tb_edu_athlete_selection_test,test_name,id" />
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :md="6" :sm="8">
              <a-form-item label="测试组别">
                <!--                <a-input placeholder="请输入测试组别" v-model="queryParam.groupId"></a-input>-->
                <j-search-select-tag v-decorator="['groupId']" dict="tb_edu_athlete_selection_group,group_name,id" />
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="8">
              <a-form-item label="指标">
                <!--                <a-input placeholder="请输入指标" v-model="queryParam.indexCode"></a-input>-->
                <j-search-select-tag v-decorator="['indexCode']" dict="tb_edu_athlete_selection_index,cn_name,id" />
              </a-form-item>
            </a-col>
          </template>
          <a-col :md="6" :sm="8" >
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>

        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->
    <!-- 操作按钮区域 -->
    <div class="table-operator" >
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
<!--      <a-button type="primary" icon="download" @click="handleExportXls('运动员选材测试成绩明细表')">导出</a-button>-->
<!--      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">-->
<!--        <a-button type="primary" icon="import">导入</a-button>-->
<!--      </a-upload>-->
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{fixed:true,selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        :scroll="tableScroll"
        @change="handleTableChange">

        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无此图片</span>
          <img v-else :src="getImgView(text)" height="25px" alt="图片不存在" style="max-width:80px;font-size: 12px;font-style: italic;"/>
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无此文件</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="uploadFile(text)">
            下载
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <athleteSelectionAthleteScoreDetail-modal ref="modalForm" @ok="modalFormOk"></athleteSelectionAthleteScoreDetail-modal>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import AthleteSelectionAthleteScoreDetailModal from './modules/AthleteSelectionAthleteScoreDetailModal'
  import {initDictOptions, filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag'

  export default {
    name: "AthleteSelectionAthleteScoreDetailList",
    mixins:[JeecgListMixin],
    components: {
      AthleteSelectionAthleteScoreDetailModal,
      JSearchSelectTag
    },
    data () {
      return {
        description: '运动员选材测试成绩明细表管理页面',
        /* 分页参数 */
        ipagination:{
          current: 1,
          pageSize: 5,
          pageSizeOptions: ['5', '10', '20'],
          showTotal: (total, range) => {
            return range[0] + "-" + range[1] + " 共" + total + "条"
          },
          showQuickJumper: true,
          showSizeChanger: true,
          total: 0
        },
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          {
            title:'运动员',
            align:"center",
            dataIndex: 'athleteId',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['athleteId'], text+"")
              }
            }
          },
          {
            title:'所属测试',
            align:"center",
            dataIndex: 'testId',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['testId'], text+"")
              }
            }
          },
          {
            title:'测试组别',
            align:"center",
            dataIndex: 'groupId',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['groupId'], text+"")
              }
            }
          },
          {
            title:'小项',
            align:"center",
            dataIndex: 'eventCode',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['eventCode'], text+"")
              }
            }
          },
          {
            title:'指标',
            align:"center",
            dataIndex: 'indexCode',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['indexCode'], text+"")
              }
            }
          },
          {
            title:'测试值',
            align:"center",
            dataIndex: 'testValue'
          },
          {
            title:'得分',
            align:"center",
            dataIndex: 'testScore'
          },
          // {
          //   title:'测试成绩',
          //   align:"center",
          //   dataIndex: 'athleteScoreId'
          // },
          {
            title:'测试等级评定',
            align:"center",
            dataIndex: 'testGrade',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['testGrade'], text+"")
              }
            }
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' }
          }
        ],
        url: {
          list: "/edusport/athleteSelectionAthleteScoreDetail/list",
          delete: "/edusport/athleteSelectionAthleteScoreDetail/delete",
          deleteBatch: "/edusport/athleteSelectionAthleteScoreDetail/deleteBatch",
          exportXlsUrl: "/edusport/athleteSelectionAthleteScoreDetail/exportXls",
          importExcelUrl: "edusport/athleteSelectionAthleteScoreDetail/importExcel",
        },
        dictOptions:{
         // testGrade:[],
        },
        tableScroll:{x :9*147+50}
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      initDictConfig(){
        initDictOptions('tb_edu_athlete,athlete_name,id').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'athleteId', res.result)
          }
        })
        initDictOptions('tb_edu_athlete_selection_test,test_name,id').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'testId', res.result)
          }
        })
        initDictOptions('tb_edu_athlete_selection_group,group_name,id').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'groupId', res.result)
          }
        })
        initDictOptions('tb_edu_sport,sport_name,sport_code').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'eventCode', res.result)
          }
        })
        initDictOptions('tb_edu_athlete_selection_index,cn_name,l3_code').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'indexCode', res.result)
          }
        }),
        initDictOptions('tb_edu_athlete_selection_group_rating,rating,id').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'testGrade', res.result)
          }
        })
      },
      getAthleteScoreId(id,athleteId ,testId,groupId,eventCode) {
        this.queryParam.athleteScoreId = id;
        this.queryParam.athleteId = athleteId;
        this.queryParam.testId = testId;
        this.queryParam.groupId = groupId;
        this.queryParam.eventCode = eventCode;
        this.loadData(1);
      },
      handleAdd: function () {
        this.$refs.modalForm.add(this.queryParam.athleteScoreId,this.queryParam.athleteId,this.queryParam.testId,this.queryParam.groupId,this.queryParam.eventCode);
        this.$refs.modalForm.title = "运动员成绩详情信息";
      },
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
  .ant-card {
    margin-left: -30px;
    margin-right: -30px;
  }
</style>