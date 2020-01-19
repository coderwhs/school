<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="测试名称">
              <a-input placeholder="请输入测试名称" v-model="queryParam.testName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="测试大项">
<!--              <a-input placeholder="请输入测试大项" v-model="queryParam.sportCode"></a-input>-->
              <j-search-select-tag v-decorator="['sportCode']" v-model="queryParam.sportCode" dict="tb_edu_sport,sport_name,sport_code" dictCondition=" where sport_code='2'"/>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :md="6" :sm="8">
              <a-form-item label="测试组别">
<!--                <a-input placeholder="请输入测试组别" v-model="queryParam.groupId"></a-input>-->
                <j-search-select-tag v-decorator="['groupId']" v-model="queryParam.groupId" dict="tb_edu_athlete_selection_group,group_name,id" />
              </a-form-item>
            </a-col>
            <a-col :md="12" :sm="16">
              <a-form-item label="业务日期">
                <j-date placeholder="请选择开始日期" class="query-group-cust" v-model="queryParam.publishDate_begin"></j-date>
                <span class="query-group-split-cust"></span>
                <j-date placeholder="请选择结束日期" class="query-group-cust" v-model="queryParam.publishDate_end"></j-date>
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
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <!-- <a-button @click="handleNavigation" type="primary" icon="plus">导航</a-button> -->
      <a-button @click="handleAudit" type="primary" icon="plus">启用</a-button>
      <a-button @click="handleUnAudit" type="primary" icon="plus">禁用</a-button>
      <!-- <a-button type="primary" icon="download" @click="handleExportXls('运动员选材测试表')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload> -->
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
        :rowSelection="{fixed:false,selectedRowKeys: selectedRowKeys, onChange: onSelectChange,type:tabSelectType}"
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
          <a-dropdown  disabled="disabled">
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

    <!-- <a-tabs defaultActiveKey="1">
      <a-tab-pane tab="运动员测试成绩" key="1">
        <Athlete-Selection-Athlete-Score-List ref="AthleteSelectionAthleteScoreList"></Athlete-Selection-Athlete-Score-List>
      </a-tab-pane>
    </a-tabs> -->

    <athleteSelectionTest-modal ref="modalForm" @ok="modalFormOk"></athleteSelectionTest-modal>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import AthleteSelectionTestModal from './modules/AthleteSelectionTestModal'
  import JDate from '@/components/jeecg/JDate.vue'
  import {initDictOptions, filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  import AthleteSelectionAthleteScoreList from './AthleteSelectionAthleteScoreList'
  import AthleteSelectionAthleteScoreModal from './modules/AthleteSelectionAthleteScoreModal'
  import { getAction, httpAction,deleteAction  } from '@/api/manage'
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag'
  import AthleteSelectionAthleteScoreDetailList from './AthleteSelectionAthleteScoreDetailList'
  import AthleteSelectionAthleteScoreDetailModal from './modules/AthleteSelectionAthleteScoreDetailModal'
  import OutlineNavigation from './OutlineNavigation'
  export default {
    name: "AthleteSelectionTestList",
    mixins:[JeecgListMixin],
    components: {
      JDate,
      AthleteSelectionTestModal,
      AthleteSelectionAthleteScoreList,
      AthleteSelectionAthleteScoreModal,
      JSearchSelectTag,
      AthleteSelectionAthleteScoreDetailModal,
      AthleteSelectionAthleteScoreDetailList,
      OutlineNavigation,
    },
    data () {
      return {
        description: '运动员选材测试表管理页面',
        /* 分页参数 */
        ipagination:{
          current: 1,
          pageSize: 5,
          pageSizeOptions: ['10', '20', '30'],
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
            title:'编码',
            align:"center",
            dataIndex: 'testCode'
          },
          {
            title:'名称',
            align:"center",
            dataIndex: 'testName'
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
            title:'运动员',
            align:"center",
            dataIndex: 'athleteNos',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['athleteNos'], text+"")
              }
            }
          },
          {
            title:'指标',
            align:"center",
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
            title:'业务日期',
            align:"center",
            dataIndex: 'publishDate',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'状态',
            align:"center",
            dataIndex: 'billState',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['billState'], text+"")
              }
            }
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            // fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' }
          }
        ],
        tabSelectType: "radio",/* Tab修改@2019-12-12 */
        url: {
          list: "/edusport/athleteSelectionTest/list",
          delete: "/edusport/athleteSelectionTest/delete",
          deleteBatch: "/edusport/athleteSelectionTest/deleteBatch",
          exportXlsUrl: "/edusport/athleteSelectionTest/exportXls",
          importExcelUrl: "edusport/athleteSelectionTest/importExcel",
          audit:"/edusport/athleteSelectionTest/audit",
          unAudit:"/edusport/athleteSelectionTest/unAudit",
        },
        dictOptions:{
         athleteNos:[],
         indexCodes:[],
         billState:[],
        },
        tableScroll:{x :4*147+50}
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      initDictConfig(){
        initDictOptions('tb_edu_sport,sport_name,sport_code').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'sportCode', res.result)
          }
        })
        initDictOptions('tb_edu_athlete_selection_group,group_name,id').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'groupId', res.result)
          }
        })
        initDictOptions('tb_edu_athlete,athlete_name,athlete_no').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'athleteNos', res.result)
          }
        })
        initDictOptions('tb_edu_athlete_selection_index,cn_name,l3_code').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'indexCodes', res.result)
          }
        })
        initDictOptions('bill_state').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'billState', res.result)
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
        this.queryParam.id = selectionRows[0].id;
        // this.$refs.AthleteSelectionAthleteScoreList.getAthleteScore(selectionRows[0].id,selectionRows[0].groupId);

        // // 成绩明细.
        // this.$refs.AthleteSelectionAthleteScoreDetailList.queryParam.athleteScoreId = null;
        // this.$refs.AthleteSelectionAthleteScoreDetailList.queryParam.athleteId = null;
        // this.$refs.AthleteSelectionAthleteScoreDetailList.queryParam.testId = null;
        // this.$refs.AthleteSelectionAthleteScoreDetailList.queryParam.groupId = null;
        // this.$refs.AthleteSelectionAthleteScoreDetailList.queryParam.eventCode = null;
        // this.$refs.AthleteSelectionAthleteScoreDetailList.loadData();
        // this.$refs.AthleteSelectionAthleteScoreDetailList.selectedRowKeys = [];
        // this.$refs.AthleteSelectionAthleteScoreDetailList.selectionRows = [];
      },
      onClearSelected() {/* Tab修改@2019-12-12 */
        this.selectedRowKeys = [];
        this.selectionRows = [];
        this.$refs.AthleteSelectionAthleteScoreList.queryParam.testId = null;
        this.$refs.AthleteSelectionAthleteScoreList.queryParam.groupId = null;
        this.$refs.AthleteSelectionAthleteScoreList.loadData();
        this.$refs.AthleteSelectionAthleteScoreList.selectedRowKeys = [];
        this.$refs.AthleteSelectionAthleteScoreList.selectionRows = [];

        // // 成绩明细.
        // this.$refs.AthleteSelectionAthleteScoreDetailList.queryParam.athleteScoreId = null;
        // this.$refs.AthleteSelectionAthleteScoreDetailList.queryParam.athleteId = null;
        // this.$refs.AthleteSelectionAthleteScoreDetailList.queryParam.testId = null;
        // this.$refs.AthleteSelectionAthleteScoreDetailList.queryParam.groupId = null;
        // this.$refs.AthleteSelectionAthleteScoreDetailList.queryParam.eventCode = null;
        // this.$refs.AthleteSelectionAthleteScoreDetailList.loadData();
        // this.$refs.AthleteSelectionAthleteScoreDetailList.selectedRowKeys = [];
        // this.$refs.AthleteSelectionAthleteScoreDetailList.selectionRows = [];
      },

      searchQuery:function(){/* Tab修改@2019-12-12 */
        this.selectedRowKeys = [];
        this.selectionRows = [];
        this.$refs.AthleteSelectionAthleteScoreList.queryParam.testId = null;
        this.$refs.AthleteSelectionAthleteScoreList.queryParam.sportCode = null;
        this.$refs.AthleteSelectionAthleteScoreList.queryParam.groupId = null;
        this.$refs.AthleteSelectionAthleteScoreList.loadData();
        this.$refs.AthleteSelectionAthleteScoreList.selectedRowKeys = [];
        this.$refs.AthleteSelectionAthleteScoreList.selectionRows = [];
        // // 成绩明细.
        // this.$refs.AthleteSelectionAthleteScoreDetailList.queryParam.athleteScoreId = null;
        // this.$refs.AthleteSelectionAthleteScoreDetailList.queryParam.athleteId = null;
        // this.$refs.AthleteSelectionAthleteScoreDetailList.queryParam.testId = null;
        // this.$refs.AthleteSelectionAthleteScoreDetailList.queryParam.groupId = null;
        // this.$refs.AthleteSelectionAthleteScoreDetailList.queryParam.eventCode = null;
        // this.$refs.AthleteSelectionAthleteScoreDetailList.loadData();
        // this.$refs.AthleteSelectionAthleteScoreDetailList.selectedRowKeys = [];
        // this.$refs.AthleteSelectionAthleteScoreDetailList.selectionRows = [];
        this.loadData();
      },

      handleDelete: function (id) {/* Tab修改@2019-12-12 */
        var that = this;
        deleteAction(that.url.delete, {id: id}).then((res) => {
          if (res.success) {
            that.$message.success(res.message);
            that.loadData();
            this.$refs.AthleteSelectionAthleteScoreList.loadData();
            this.$refs.AthleteSelectionAthleteScoreDetailList.loadData();
          } else {
            that.$message.warning(res.message);
          }
        });
      },

      // 审核.
      handleAudit: function () {
        if(this.queryParam.id){
          let params = {
            id: this.queryParam.id
          };

          getAction(this.url.audit, params).then((res) => {
            if (res.success) {
              this.$message.success(res.message);
            } else {
              this.$message.warning(res.message);
            }
          }).finally(() => {
            this.loadData(1);
          })
        } else {
          alert("请选择一条信息");
        }
      },

      // 反审核.
      handleUnAudit: function () {
        if(this.queryParam.id){
          let params = {
            id: this.queryParam.id
          };

          getAction(this.url.unAudit, params).then((res) => {
            if (res.success) {
              this.$message.success(res.message);
            } else {
              this.$message.warning(res.message);
            }
          }).finally(() => {
            this.loadData(1);
          })
        } else {
          alert("请选择一条信息");
        }
      },
      handleNavigation: function () {
        let routeUrl = this.$router.resolve({
          path: './OutlineNavigation',
          query: {}
        });
        window.open(routeUrl.href, '_blank');
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