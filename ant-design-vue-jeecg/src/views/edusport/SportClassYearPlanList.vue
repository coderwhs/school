<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="训练队">
              <j-search-select-tag placeholder="请选择训练队" v-model="queryParam.sportClassId" dict="tb_edu_sport_class,class_name,id" />
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="训练计划名">
              <a-input placeholder="请输入训练计划名" v-model="queryParam.planName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="8" :sm="12">
            <a-form-item label="计划日期">
              <j-date v-model="queryParam.prepareStartDate_begin" date-format="YYYY-MM-DD" style="width:45%" placeholder="请选择开始时间" ></j-date>
              <span style="width: 10px;">~</span>
              <j-date v-model="queryParam.prepareStartDate_end" date-format="YYYY-MM-DD" style="width:45%" placeholder="请选择结束时间"></j-date>
            </a-form-item>
          </a-col>
          <a-col :md="4" :sm="24" >
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
<!--              <a @click="handleToggleSearch" style="margin-left: 8px">-->
<!--                {{ toggleSearchStatus ? '收起' : '展开' }}-->
<!--                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>-->
<!--              </a>-->
            </span>
          </a-col>

        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->
    
    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
<!--      <a-button type="primary" icon="download" @click="handleExportXls('冬训阶段训练计划信息表')">导出</a-button>-->
<!--      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">-->
<!--        <a-button type="primary" icon="import">导入</a-button>-->
<!--      </a-upload>-->
<!--      <a-dropdown v-if="selectedRowKeys.length > 0">-->
<!--        <a-menu slot="overlay">-->
<!--          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>-->
<!--        </a-menu>-->
<!--        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>-->
<!--      </a-dropdown>-->
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
        <span style="margin-left: 48px">默认显示近一年的训练计划</span>
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
      <a-tab-pane tab="运动员年度成绩目标" key="1">
        <Sport-Class-Athlete-Year-Goal-List ref="SportClassAthleteYearGoalList"></Sport-Class-Athlete-Year-Goal-List>
      </a-tab-pane>
      <a-tab-pane tab="年度工作总结" key="2" forceRender>
        <Sport-Class-Year-Summary-List ref="SportClassYearSummaryList"></Sport-Class-Year-Summary-List>
      </a-tab-pane>
    </a-tabs>

    <sportClassYearPlan-modal ref="modalForm" @ok="modalFormOk"></sportClassYearPlan-modal>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import JDate from '@/components/jeecg/JDate'
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag'
  import {initDictOptions, filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  import SportClassYearPlanModal from './modules/SportClassYearPlanModal__Style#Drawer'
  import SportClassAthleteYearGoalList from './SportClassAthleteYearGoalList'
  import SportClassAthleteYearGoalModal from './modules/SportClassAthleteYearGoalModal'
  import SportClassYearSummaryList from './SportClassYearSummaryList'
  import SportClassYearSummaryModal from './modules/SportClassYearSummaryModal'
  import moment from 'moment'

  export default {
    name: "SportClassYearPlanList",
    mixins:[JeecgListMixin],
    components: {
      JDate,
      JSearchSelectTag,
      SportClassYearPlanModal,
      SportClassAthleteYearGoalList,
      SportClassAthleteYearGoalModal,
      SportClassYearSummaryList,
      SportClassYearSummaryModal
    },
    data () {
      return {
        description: '年度训练计划信息表管理页面',
        // Table记录选择器修改为单选框
        tabSelectType: "radio",
        /* 查询条件 */
        queryParam: {
          prepareStartDate_begin:  Number(moment().subtract(365, 'days').year()) + "-01-01",
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
            title:'训练队',
            align:"center",
            dataIndex: 'sportClassId',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['sportClassId'], text+"")
              }
            }
          },
          {
            title:'训练计划名称',
            align:"center",
            dataIndex: 'planName'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' }
          }
        ],
        url: {
          list: "/edusport/sportClassYearPlan/list",
          delete: "/edusport/sportClassYearPlan/delete",
          deleteBatch: "/edusport/sportClassYearPlan/deleteBatch",
          exportXlsUrl: "/edusport/sportClassYearPlan/exportXls",
          importExcelUrl: "edusport/sportClassYearPlan/importExcel",
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
        initDictOptions('tb_edu_sport_class,class_name,id').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'sportClassId', res.result)
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
        let yearPlanId = this.selectedRowKeys[0];

        this.$refs.SportClassAthleteYearGoalList.getListByYearPlanId(yearPlanId);
        this.$refs.SportClassYearSummaryList.getListByYearPlanId(yearPlanId);

      },

      onClearSelected() {
        this.selectedRowKeys = [];
        this.selectionRows = [];

        // 重新初始化子Tab区域数据
        this.$refs.SportClassAthleteYearGoalList.queryParam.yearPlanId = null;
        this.$refs.SportClassAthleteYearGoalList.queryParam.sportClassId = this.queryParam.sportClassId;
        this.$refs.SportClassAthleteYearGoalList.queryParam.planName = this.queryParam.planName;
        this.$refs.SportClassAthleteYearGoalList.queryParam.prepareStartDate_begin = this.queryParam.prepareStartDate_begin;
        this.$refs.SportClassAthleteYearGoalList.queryParam.prepareStartDate_end = this.queryParam.prepareStartDate_end;
        this.$refs.SportClassAthleteYearGoalList.loadData(1);
        this.$refs.SportClassAthleteYearGoalList.onClearSelected();

        this.$refs.SportClassYearSummaryList.queryParam.yearPlanId = null;
        this.$refs.SportClassYearSummaryList.queryParam.sportClassId = this.queryParam.sportClassId;
        this.$refs.SportClassYearSummaryList.queryParam.planName = this.queryParam.planName;
        this.$refs.SportClassYearSummaryList.queryParam.prepareStartDate_begin = this.queryParam.prepareStartDate_begin;
        this.$refs.SportClassYearSummaryList.queryParam.prepareStartDate_end = this.queryParam.prepareStartDate_end;
        this.$refs.SportClassYearSummaryList.loadData(1);
        this.$refs.SportClassYearSummaryList.onClearSelected();
      },
      searchQuery: function() {
        this.loadData(1);
        this.onClearSelected();
      },
      searchReset: function() {
        /* 默认查询条件 */
        this.queryParam = {
          prepareStartDate_begin:  Number(moment().subtract(365, 'days').year()) + "-01-01",
        };
        this.loadData(1);
        this.onClearSelected();
      }
       
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>