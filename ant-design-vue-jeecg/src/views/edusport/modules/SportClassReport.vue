<template>
  <a-card :bordered="false">
    <!-- 操作按钮区域 -->
    <div class="table-operator" :md="24" :sm="24" style="margin: -25px 0px 10px 0px">
      <a-button type="primary" @click="searchQuery" icon="search">查看报告</a-button>
    </div>

    <!-- 报告区域-begin -->
    <page-layout title="江西省青少年业余训练--教练员年度、阶段训练计划及总结" :breadcrumb="false" logo="">
      <detail-list slot="headerContent" size="small" :col="1" class="detail-layout">
        <detail-list-item term="单位">（江西省体育运动学校）</detail-list-item>
        <detail-list-item term="姓名">（XXX教练员）</detail-list-item>
        <detail-list-item term="项目">（田径）</detail-list-item>
        <detail-list-item term="时间">（2020-03-01）</detail-list-item>
      </detail-list>

      <div style="background-color: #ececec; padding: 24px 32px 24px;">
        <a-card :bordered="false" title="运动员基本情况一览表">
          <a-table
            :columns="columns"
            :dataSource="data"
            bordered
            size="middle"
            :scroll="{ x: '130%', y: 240 }"
          />
        </a-card>

        <a-card style="margin-top: 24px" :bordered="true" title="年度训练计划">
          <a-card type="inner" :bordered="false" title="一、运动队起始状态诊断（包括对重点运动员个体分析）">
            Inner Card content
          </a-card>
          <a-card type="inner" :bordered="false" title="二、确定全年训练基本任务">
            Inner Card content
          </a-card>
          <a-card type="inner" :bordered="false" title="三、制订运动员身体素质、专项技能成绩目标">
            Inner Card content
          </a-card>
          <a-card type="inner" :bordered="false" title="四、周期划分，各阶段训练任务、时数及训练基本手段">
            Inner Card content
          </a-card>
          <a-card type="inner" :bordered="false" title="五、训练负荷、比赛及定期综合考核的安排">
            Inner Card content
          </a-card>
          <a-card type="inner" :bordered="false" title="六、检查和测定各项训练指标的时间和次数">
            Inner Card content
          </a-card>
          <a-card type="inner" :bordered="false" title="七、实施计划具体的措施">
            Inner Card content
          </a-card>
          <a-card type="inner" :bordered="false" title="检查评价（年度训练计划）">
            Inner Card content
          </a-card>
        </a-card>

        <a-card style="margin-top: 24px" :bordered="false" title="冬训阶段训练计划">
          content
          <a-card type="inner" :bordered="false" title="检查评价（冬训）">
            Inner Card content
          </a-card>
        </a-card>

        <a-card style="margin-top: 24px" :bordered="false" title="夏训阶段训练计划">
          content
          <a-card type="inner" :bordered="false" title="检查评价（夏训）">
            Inner Card content
          </a-card>
        </a-card>

        <a-card style="margin-top: 24px" :bordered="false" title="年度训练工作总结">
          <a-card type="inner" :bordered="false" title="一、训练计划的实施情况（突出重点队员执行情况）">
            Inner Card content
          </a-card>
          <a-card type="inner" :bordered="false" title="二、经验体会，缺点与不足">
            Inner Card content
          </a-card>
          <a-card type="inner" :bordered="false" title="三、今后努力方向">
            Inner Card content
          </a-card>
          <a-card type="inner" :bordered="false" title="检查评价">
            Inner Card content
          </a-card>
        </a-card>
      </div>

    </page-layout>

    <!-- 报告区域-end -->


  </a-card>
</template>

<script>

  import { mixinDevice } from '@/utils/mixin.js'
  import PageLayout from '@/components/page/PageLayout'
  import DetailList from '@/components/tools/DetailList'
  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JDate from '@/components/jeecg/JDate'
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag'

  const DetailListItem = DetailList.Item

  export default {
    name: "SportClassReport",
    components: {
      PageLayout,
      DetailList,
      DetailListItem,
      JDate,
      JSearchSelectTag,
    },
    data () {
      return {
        description: '年度、阶段训练计划及总结',
        /* 查询条件 */
        queryParam: {
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
            title:'训练队名',
            align:"center",
            dataIndex: 'className'
          },
          {
            title:'教练员',
            align:"center",
            dataIndex: 'coachId',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['coachId'], text+"")
              }
            }
          },
          {
            title:'运动项目',
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
            title:'训练形式',
            align:"center",
            dataIndex: 'trainingType',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['trainingType'], text+"")
              }
            }
          },
          {
            title:'训练年度',
            align:"center",
            dataIndex: 'trainingYear'
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
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' }
          }
        ],
        tabSelectType: "radio",
        url: {
          list: "/edusport/sportClass/list",
          delete: "/edusport/sportClass/delete",
          deleteBatch: "/edusport/sportClass/deleteBatch",
          exportXlsUrl: "/edusport/sportClass/exportXls",
          importExcelUrl: "edusport/sportClass/importExcel",
        },
        dictOptions:{
         trainingType:[],
        },
      }
    },
    created() {
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      initDictConfig(){
        initDictOptions('tb_edu_coach,coach_name,id').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'coachId', res.result)
          }
        })
        initDictOptions('tb_edu_sport,sport_name,sport_code').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'sportCode', res.result)
          }
        })
        initDictOptions('training_type').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'trainingType', res.result)
          }
        })
      },
      getBreadcrumb() {
        this.breadList = [];
      },
      searchQuery: function() {
        this.loadData(1);
        this.onClearSelected();
      },
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>

<style scoped>
  .ant-card {
    margin-left: -30px;
    margin-right: -30px;
  }
</style>