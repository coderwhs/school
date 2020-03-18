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
              <j-date v-model="queryParam.startDate_begin" date-format="YYYY-MM-DD" style="width:45%" placeholder="请选择开始时间" ></j-date>
              <span style="width: 10px;">~</span>
              <j-date v-model="queryParam.startDate_end" date-format="YYYY-MM-DD" style="width:45%" placeholder="请选择结束时间"></j-date>
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
<!--      <a-button type="primary" icon="download" @click="handleExportXls('周训练计划信息表')">导出</a-button>-->
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
        <span style="margin-left: 48px">默认显示过去一周，及未来一周内的训练计划</span>
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
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange, type: tabSelectType}"
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
      <a-tab-pane tab="课训练计划" key="1">
        <Sport-Class-Course-Plan-List ref="SportClassCoursePlanList"></Sport-Class-Course-Plan-List>
      </a-tab-pane>
    </a-tabs>

    <sportClassWeekPlan-modal ref="modalForm" @ok="modalFormOk"></sportClassWeekPlan-modal>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import JDate from '@/components/jeecg/JDate'
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag'
  import {initDictOptions, filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  // import SportClassWeekPlanModal from './modules/SportClassWeekPlanModal'
  import SportClassWeekPlanModal from './modules/SportClassWeekPlanModal__Style#Drawer'
  import SportClassCoursePlanModal from './modules/SportClassCoursePlanModal'
  import SportClassCoursePlanList from './SportClassCoursePlanList'
  import moment from 'moment'

  export default {
    name: "SportClassWeekPlanList",
    mixins:[JeecgListMixin],
    components: {
      JDate,
      JSearchSelectTag,
      SportClassWeekPlanModal,
      SportClassCoursePlanList,
      SportClassCoursePlanModal
    },
    data () {
      return {
        description: '周训练计划信息表管理页面',
        // Table记录选择器修改为单选框
        tabSelectType: "radio",
        /* 查询条件 */
        queryParam: {
          startDate_begin:  moment().subtract(parseInt(moment().format('d')) + 6, 'days').format('YYYY-MM-DD') ,
          startDate_end: moment().add(14 - parseInt(moment().format('d')), 'days').format('YYYY-MM-DD') ,
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
            title:'周开始日期',
            align:"center",
            dataIndex: 'startDate',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'周结束日期',
            align:"center",
            dataIndex: 'endDate',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'目的任务',
            align:"center",
            dataIndex: 'taskGoal'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' }
          }
        ],
        url: {
          list: "/edusport/sportClassWeekPlan/list",
          delete: "/edusport/sportClassWeekPlan/delete",
          deleteBatch: "/edusport/sportClassWeekPlan/deleteBatch",
          exportXlsUrl: "/edusport/sportClassWeekPlan/exportXls",
          importExcelUrl: "edusport/sportClassWeekPlan/importExcel",
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
        // 训练队
        let sportClassId = this.selectionRows[0].sportClassId;
        // 周开始日期
        let startDate = this.selectionRows[0].startDate;
        // 周结束日期
        let endDate = this.selectionRows[0].endDate;

        this.$refs.SportClassCoursePlanList.getListByWeeklySportClassId(sportClassId, startDate, endDate);
      },

      onClearSelected() {
        this.selectedRowKeys = [];
        this.selectionRows = [];

        // 重新初始化子Tab区域数据
        this.$refs.SportClassCoursePlanList.queryParam.sportClassId = this.queryParam.sportClassId;
        this.$refs.SportClassCoursePlanList.queryParam.planName = this.queryParam.planName;
        this.$refs.SportClassCoursePlanList.queryParam.courseDate_begin = this.queryParam.startDate_begin;
        this.$refs.SportClassCoursePlanList.queryParam.courseDate_end = this.queryParam.startDate_end;
        this.$refs.SportClassCoursePlanList.loadData(1);
        this.$refs.SportClassCoursePlanList.onClearSelected();
      },
      searchQuery: function() {
        this.loadData(1);
        this.onClearSelected();
      },
      searchReset: function() {
        /* 查询条件 */
        this.queryParam = {
          startDate_begin:  moment().subtract(parseInt(moment().format('d')) + 6, 'days').format('YYYY-MM-DD') ,
            startDate_end: moment().add(14 - parseInt(moment().format('d')), 'days').format('YYYY-MM-DD') ,
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