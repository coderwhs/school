<template>
  <a-card :bordered="false">
<<<<<<< .mine
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="运动员学号">
              <a-input placeholder="请输入运动员学号" v-model="queryParam.athleteNo"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="教练员代码">
              <a-input placeholder="请输入教练员代码" v-model="queryParam.coachNo"></a-input>
            </a-form-item>
          </a-col>
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
=======





























>>>>>>> .theirs
    
    <!-- 操作按钮区域 -->
    <div class="table-operator" :md="24" :sm="24" style="margin: -25px 0px 10px 0px">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
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

    <athleteCoachEvaluation-modal ref="modalForm" @ok="modalFormOk"></athleteCoachEvaluation-modal>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  // import AthleteCoachEvaluationModal from './modules/AthleteCoachEvaluationModal'
  import AthleteCoachEvaluationModal from './modules/AthleteCoachEvaluationModal__Style#Drawer'
  import {initDictOptions, filterMultiDictText} from '@/components/dict/JDictSelectUtil'

  export default {
    name: "AthleteCoachEvaluationList",
    mixins:[JeecgListMixin],
    components: {
      AthleteCoachEvaluationModal
    },
    data () {
      return {
        description: '训练队成员评价信息表管理页面',
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
<<<<<<< .mine
            title:'运动员学号',
=======
            title:'训练队成员',
>>>>>>> .theirs
            align:"center",
<<<<<<< .mine
            dataIndex: 'athleteNo',
=======
            dataIndex: 'athleteSportClassId',
>>>>>>> .theirs
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
<<<<<<< .mine
                return filterMultiDictText(this.dictOptions['athleteNo'], text+"")

=======
                let athlete_id = filterMultiDictText(this.dictOptions['athleteSportClassId'], text+"")
                return filterMultiDictText(this.dictOptions['athlete_id'], athlete_id+"")
>>>>>>> .theirs
              }
            }
          },
          {
<<<<<<< .mine
            title:'教练员代码',
            align:"center",
            dataIndex: 'coachNo',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['coachNo'], text+"")
              }
            }
          },
          {
=======












>>>>>>> .theirs
            title:'开始日期',
            align:"center",
            dataIndex: 'startDate',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'结束日期',
            align:"center",
            dataIndex: 'endDate',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'总体评价',
            align:"center",
            dataIndex: 'evaluation'
          },
          {
            title:'评价教练',
            align:"center",
            dataIndex: 'evaluator',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['evaluator'], text+"")
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
          list: "/edusport/athleteCoachEvaluation/list",
          delete: "/edusport/athleteCoachEvaluation/delete",
          deleteBatch: "/edusport/athleteCoachEvaluation/deleteBatch",
          exportXlsUrl: "/edusport/athleteCoachEvaluation/exportXls",
          importExcelUrl: "edusport/athleteCoachEvaluation/importExcel",
        },
        dictOptions:{
        },
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      initDictConfig(){
<<<<<<< .mine
        initDictOptions('tb_edu_athlete,athlete_name,athlete_no').then((res) => {





=======
        initDictOptions('tb_edu_athlete_sport_class,athlete_id,id').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'athleteSportClassId', res.result)
          }
        })
        initDictOptions('tb_edu_athlete,athlete_name,id').then((res) => {
>>>>>>> .theirs
          if (res.success) {
<<<<<<< .mine
            this.$set(this.dictOptions, 'athleteNo', res.result)
=======
            this.$set(this.dictOptions, 'athlete_id', res.result)
>>>>>>> .theirs
          }
        })
        initDictOptions('tb_edu_coach,coach_name,coach_no').then((res) => {
          if (res.success) {
<<<<<<< .mine
            this.$set(this.dictOptions, 'coachNo', res.result)
=======
            this.$set(this.dictOptions, 'evaluator', res.result)
>>>>>>> .theirs
          }
        })
      },
      getListByAthleteSportClassId(athleteSportClassId) {
        this.queryParam.athleteSportClassId = athleteSportClassId;
        this.loadData(1);
      },
      handleAdd: function () {
        this.$refs.modalForm.add(this.queryParam.athleteSportClassId);
        this.$refs.modalForm.title = "添加带训教练员评价";
        this.$refs.modalForm.disableSubmit = false;
      },
       
    }
  }
</script>
<style scoped>
  .ant-card {
    margin-left: -30px;
    margin-right: -30px;
  }
</style>
