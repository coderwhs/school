<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="训练队">
              <a-input placeholder="请输入训练队" v-model="queryParam.sportClassId"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="训练计划名">
              <a-input placeholder="请输入训练计划名" v-model="queryParam.planName"></a-input>
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
    
    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('年度训练计划信息表')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
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
  // import SportClassYearPlanModal from './modules/SportClassYearPlanModal'
  import SportClassYearPlanModal from './modules/SportClassYearPlanModal__Style#Drawer'
  import SportClassAthleteYearGoalList from './SportClassAthleteYearGoalList'
  import SportClassAthleteYearGoalModal from './modules/SportClassAthleteYearGoalModal'
  import SportClassYearSummaryList from './SportClassYearSummaryList'
  import SportClassYearSummaryModal from './modules/SportClassYearSummaryModal'
  import {initDictOptions, filterMultiDictText} from '@/components/dict/JDictSelectUtil'

  export default {
    name: "SportClassYearPlanList",
    mixins:[JeecgListMixin],
    components: {
      SportClassYearPlanModal,
      SportClassAthleteYearGoalList,
      SportClassAthleteYearGoalModal,
      SportClassYearSummaryList,
      SportClassYearSummaryModal
    },
    data () {
      return {
        description: '年度训练计划信息表管理页面',
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
        tabSelectType: "radio",
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

        this.$refs.SportClassAthleteYearGoalList.queryParam.yearPlanId = null;
        this.$refs.SportClassAthleteYearGoalList.loadData();
        this.$refs.SportClassAthleteYearGoalList.selectedRowKeys = [];
        this.$refs.SportClassAthleteYearGoalList.selectionRows = [];

        this.$refs.SportClassYearSummaryList.queryParam.yearPlanId = null;
        this.$refs.SportClassYearSummaryList.loadData();
        this.$refs.SportClassYearSummaryList.selectedRowKeys = [];
        this.$refs.SportClassYearSummaryList.selectionRows = [];
      },

      searchQuery:function(){
        this.selectedRowKeys = [];
        this.selectionRows = [];

        this.$refs.SportClassAthleteYearGoalList.queryParam.yearPlanId = null;
        this.$refs.SportClassAthleteYearGoalList.loadData();
        this.$refs.SportClassAthleteYearGoalList.selectedRowKeys = [];
        this.$refs.SportClassAthleteYearGoalList.selectionRows = [];

        this.$refs.SportClassYearSummaryList.queryParam.yearPlanId = null;
        this.$refs.SportClassYearSummaryList.loadData();
        this.$refs.SportClassYearSummaryList.selectedRowKeys = [];
        this.$refs.SportClassYearSummaryList.selectionRows = [];

        this.loadData();
      }
       
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>