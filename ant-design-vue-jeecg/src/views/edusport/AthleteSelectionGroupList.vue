<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="组别名称">
              <a-input placeholder="请输入组别名称" v-model="queryParam.groupName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="大项">
              <j-search-select-tag placeholder="请选择大项" v-model="queryParam.sportCode" dict="tb_edu_sport,sport_name,sport_code" />
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="启用状态">
              <j-dict-select-tag placeholder="请选择启用状态" v-model="queryParam.enableStatus" dictCode="enable_status"/>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8" >
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
            </span>
          </a-col>

        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->
    
    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
<!--      <a-button type="primary" icon="download" @click="handleExportXls('运动员选材测试组别标准表')">导出</a-button>-->
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
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange,type:tabSelectType}"
        :customRow="clickThenCheck"
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
    <!-- table区域-end -->

    <a-tabs defaultActiveKey="1">
      <a-tab-pane tab="测试组别指标评分标准设定" key="1">
        <Athlete-Selection-Group-Index-Grade-List ref="AthleteSelectionGroupIndexGradeList"></Athlete-Selection-Group-Index-Grade-List>
      </a-tab-pane>
      <a-tab-pane tab="测试组别指标等级标准设定" key="2" forceRender>
        <Athlete-Selection-Group-Rating-List ref="AthleteSelectionGroupRatingList"></Athlete-Selection-Group-Rating-List>
      </a-tab-pane>
    </a-tabs>

    <athleteSelectionGroup-modal ref="modalForm" @ok="modalFormOk"></athleteSelectionGroup-modal>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import AthleteSelectionGroupModal from './modules/AthleteSelectionGroupModal__Style#Drawer'
  import JDictSelectTag from '@/components/dict/JDictSelectTag.vue'
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag'
  import {initDictOptions, filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  import AthleteSelectionGroupIndexGradeList from './AthleteSelectionGroupIndexGradeList'
  import AthleteSelectionGroupIndexGradeModal from './modules/AthleteSelectionGroupIndexGradeModal__Style#Drawer'
  import AthleteSelectionGroupRatingList from './AthleteSelectionGroupRatingList'
  import AthleteSelectionGroupRatingModal from './modules/AthleteSelectionGroupRatingModal__Style#Drawer'

  export default {
    name: "AthleteSelectionGroupList",
    mixins:[JeecgListMixin],
    components: {
      JDictSelectTag,
      JSearchSelectTag,
      AthleteSelectionGroupModal,
      AthleteSelectionGroupIndexGradeList,
      AthleteSelectionGroupIndexGradeModal,
      AthleteSelectionGroupRatingList,
      AthleteSelectionGroupRatingModal
    },
    data () {
      return {
        description: '运动员选材测试组别标准表管理页面',
        // Table记录选择器修改为单选框
        tabSelectType: "radio",
        /* 查询条件 */
        queryParam: {
          enableStatus: '1',
        },
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
        /* 排序参数 */
        isorter: {
          // 排序由后端处理
          column: '',
          order: ''
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
            title:'组别名称',
            align:"center",
            dataIndex: 'groupName'
          },
          {
            title:'启用状态',
            align:"center",
            dataIndex: 'enableStatus',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['enableStatus'], text+"")
              }
            }
          },
          {
            title:'大项',
            align:"center",
            dataIndex: 'sportCode',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['sportCode'], text+"")
              }
            }
          },
          {
            title:'小项',
            align:"center",
            width:200,
            dataIndex: 'eventCodes',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['eventCodes'], text+"")
              }
            }
          },
          {
            title:'测试指标',
            align:"center",
            width:300,
            dataIndex: 'indexCodes',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['indexCodes'], text+"")
              }
            }
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' }
          }
        ],
        url: {
          list: "/edusport/athleteSelectionGroup/list",
          delete: "/edusport/athleteSelectionGroup/delete",
          deleteBatch: "/edusport/athleteSelectionGroup/deleteBatch",
          exportXlsUrl: "/edusport/athleteSelectionGroup/exportXls",
          importExcelUrl: "edusport/athleteSelectionGroup/importExcel",
        },
        dictOptions:{
          enableStatus:[],
          eventCodes:[],
          indexCodes:[],
        },
      }
    },
    created () {
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      initDictConfig(){
        initDictOptions('enable_status').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'enableStatus', res.result)
          }
        })
        initDictOptions('tb_edu_sport,sport_name,sport_code').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'sportCode', res.result)
          }
        })

        initDictOptions('tb_edu_sport_event as t1, t1.event_name, t1.event_code, t1.enable_status=1 | tb_edu_sport t2, t2.sport_name, t2.sport_code = t1.sport_code | tb_edu_sport_disciplines t3, t3.disciplines_name, t3.disciplines_code=t1.disciplines_code').then((res) => {
          if (res.success) {
            console.log("eventCodes: ", res.result)
            this.$set(this.dictOptions, 'eventCodes', res.result)
          }
        })
        initDictOptions('tb_edu_athlete_selection_index, cn_name, index_code').then((res) => {
          if (res.success) {
            console.log("indexCodes: ", res.result)
            this.$set(this.dictOptions, 'indexCodes', res.result)
          }
        })
      },
      clickThenCheck(record) {
        return {
          on: {
            click: () => {
              this.onSelectChange(record.id.split(","), [record]);
            }
          }
        };
      },
      onSelectChange(selectedRowKeys, selectionRows) {
        this.selectedRowKeys = selectedRowKeys;
        this.selectionRows = selectionRows;
        let groupId = this.selectedRowKeys[0];

        this.$refs.AthleteSelectionGroupIndexGradeList.getListByGroupId(groupId);
        this.$refs.AthleteSelectionGroupRatingList.getListByGroupId(groupId);
      },
      onClearSelected() {
        this.selectedRowKeys = [];
        this.selectionRows = [];

        this.$refs.AthleteSelectionGroupIndexGradeList.queryParam.groupId = null;
        this.$refs.AthleteSelectionGroupIndexGradeList.loadData();
        this.$refs.AthleteSelectionGroupIndexGradeList.selectedRowKeys = [];
        this.$refs.AthleteSelectionGroupIndexGradeList.selectionRows = [];

        this.$refs.AthleteSelectionGroupRatingList.queryParam.groupId = null;
        this.$refs.AthleteSelectionGroupRatingList.loadData();
        this.$refs.AthleteSelectionGroupRatingList.selectedRowKeys = [];
        this.$refs.AthleteSelectionGroupRatingList.selectionRows = [];
      },
      searchQuery: function() {
        this.selectedRowKeys = [];
        this.selectionRows = [];
        this.loadData();

        if (this.queryParam.groupName || this.queryParam.sportCode) {
          this.$refs.AthleteSelectionGroupIndexGradeList.dataSource=[];
          this.$refs.AthleteSelectionGroupIndexGradeList.selectedRowKeys = [];
          this.$refs.AthleteSelectionGroupIndexGradeList.selectionRows = [];

          this.$refs.AthleteSelectionGroupRatingList.dataSource=[];
          this.$refs.AthleteSelectionGroupRatingList.selectedRowKeys = [];
          this.$refs.AthleteSelectionGroupRatingList.selectionRows = [];
        } else {
          this.$refs.AthleteSelectionGroupIndexGradeList.queryParam.groupId = null;
          this.$refs.AthleteSelectionGroupIndexGradeList.loadData();
          this.$refs.AthleteSelectionGroupIndexGradeList.selectedRowKeys = [];
          this.$refs.AthleteSelectionGroupIndexGradeList.selectionRows = [];

          this.$refs.AthleteSelectionGroupRatingList.queryParam.groupId = null;
          this.$refs.AthleteSelectionGroupRatingList.loadData();
          this.$refs.AthleteSelectionGroupRatingList.selectedRowKeys = [];
          this.$refs.AthleteSelectionGroupRatingList.selectionRows = [];
        }

      }
       
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>