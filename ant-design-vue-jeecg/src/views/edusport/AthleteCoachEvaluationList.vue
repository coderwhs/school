<template>
  <a-card :bordered="false">

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
        /* 查询条件 */
        queryParam: {
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

        //选择记录的运动员ID
        selectedAthleteId:'',

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
            title:'训练队成员',
            align:"center",
            dataIndex: 'athleteSportClassId',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                let athlete_id = filterMultiDictText(this.dictOptions['athleteSportClassId'], text+"")
                return filterMultiDictText(this.dictOptions['athlete_id'], athlete_id+"")
              }
            }
          },
          {
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
        initDictOptions('tb_edu_athlete_sport_class,athlete_id,id').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'athleteSportClassId', res.result)
          }
        })
        initDictOptions('tb_edu_athlete,athlete_name,id').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'athlete_id', res.result)
          }
        })
        initDictOptions('tb_edu_coach,coach_name,id').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'evaluator', res.result)
          }
        })
      },
      // onSelectChange(selectedRowKeys, selectionRows) {
      //   this.selectedRowKeys = selectedRowKeys;
      //   this.selectionRows = selectionRows;
      //   this.selectedAthleteId = this.selectionRows[0].athleteId;
      //   console.log("this.selectionRows: ", this.selectionRows);
      //   console.log("selectedAthleteId: ", this.selectionRows[0].athleteId);
      //
      // },
      getListByAthleteSportClassId(athleteSportClassId, athleteId) {
        this.queryParam.athleteSportClassId = athleteSportClassId;
        this.queryParam.athleteId = athleteId;

        console.log("--evaluationList.queryParam.athleteId: ", this.queryParam.athleteId);
        this.loadData(1);
      },
      handleAdd: function () {
        console.log("--evaluationList.handleAdd.athleteSportClassId: ", this.queryParam.athleteSportClassId);
        console.log("--evaluationList.handleAdd.athleteId: ", this.queryParam.athleteId);
        this.$refs.modalForm.add(this.queryParam.athleteSportClassId, this.queryParam.athleteId );
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
