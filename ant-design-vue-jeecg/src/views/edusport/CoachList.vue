<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="教练员代码">
              <a-input placeholder="请输入教练员代码" v-model="queryParam.coachNo"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="教练员姓名">
              <a-input placeholder="请输入教练员姓名" v-model="queryParam.coachName"></a-input>
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
      <a-button type="primary" icon="download" @click="handleExportXls('教练员信息表')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <a-button @click="handleSignUp" type="primary" icon="unlock">开通账号</a-button>
      <a-button @click="handleSignLock" type="primary" icon="lock">冻结账号</a-button>
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
        filterMultiple="filterMultiple"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange,type:type}"
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
      <a-tab-pane tab="个人简历" key="1">
        <Coach-Edu-Resume-List ref="CoachEduResumeList"></Coach-Edu-Resume-List>
      </a-tab-pane>
      <a-tab-pane tab="运动经历" key="2" forceRender>
        <Coach-Sport-Resume-List ref="CoachSportResumeList"></Coach-Sport-Resume-List>
      </a-tab-pane>
      <a-tab-pane tab="培训经历" key="3" forceRender>
        <Coach-Training-List ref="CoachTrainingList"></Coach-Training-List>
      </a-tab-pane>
      <a-tab-pane tab="论文发表" key="4" forceRender>
        <Coach-Paper-List ref="CoachPaperList"></Coach-Paper-List>
      </a-tab-pane>
    </a-tabs>

    <coach-modal ref="modalForm" @ok="modalFormOk"></coach-modal>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import CoachEduResumeList from './CoachEduResumeList'
  import CoachSportResumeList from './CoachSportResumeList'
  import CoachTrainingList from './CoachTrainingList'
  import CoachPaperList from './CoachPaperList'
  import CoachModal from './modules/CoachModal'
  import CoachEduResumeModal from './modules/CoachEduResumeModal'
  import CoachSportResumeModal from './modules/CoachSportResumeModal'
  import CoachTrainingModal from './modules/CoachTrainingModal'
  import CoachPaperModal from './modules/CoachPaperModal'
  import {initDictOptions, filterMultiDictText} from '@/components/dict/JDictSelectUtil'

  export default {
    name: "CoachList",
    mixins:[JeecgListMixin],
    components: {
      CoachEduResumeList,
      CoachSportResumeList,
      CoachTrainingList,
      CoachPaperList,
      CoachModal,
      CoachEduResumeModal,
      CoachSportResumeModal,
      CoachTrainingModal,
      CoachPaperModal,
    },
    data () {
      return {
        description: '教练员名单管理页面',
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
            title:'姓名',
            align:"center",
            dataIndex: 'coachName'
          },
          {
            title:'教练员代码',
            align:"center",
            dataIndex: 'coachNo'
          },
          {
            title:'性别',
            align:"center",
            dataIndex: 'gender',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['gender'], text+"")
              }
            }
          },
          {
            title:'民族',
            align:"center",
            dataIndex: 'nation',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['nation'], text+"")
              }
            }
          },
          {
            title:'出生日期',
            align:"center",
            dataIndex: 'birthDate',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'手机号码',
            align:"center",
            dataIndex: 'mobile'
          },
          {
            title:'编制类型',
            align:"center",
            dataIndex: 'staffType',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['staffType'], text+"")
              }
            }
          },
          {
            title:'聘用日期',
            align:"center",
            dataIndex: 'hireDate',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'聘用年限',
            align:"center",
            dataIndex: 'hireYears'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' }
          }
        ],
        type: "radio",
        url: {
          list: "/edusport/coach/list",
          delete: "/edusport/coach/delete",
          deleteBatch: "/edusport/coach/deleteBatch",
          exportXlsUrl: "/edusport/coach/exportXls",
          importExcelUrl: "edusport/coach/importExcel",
          signUpUrl: "edusport/coach/signUp",
          signLockUrl: "edusport/coach/signLock",
        },
        dictOptions:{
         gender:[],
         nation:[],
         staffType:[],
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
        initDictOptions('sex').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'gender', res.result)
          }
        })
        initDictOptions('nation').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'nation', res.result)
          }
        })
        initDictOptions('staff_type').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'staffType', res.result)
          }
        })
      },

      handleSignUp: function () {
      },
      handleSignLock: function () {
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
        let coachId = this.selectedRowKeys[0];
        console.log("selectedRowKeys: " + selectedRowKeys);
        console.log("selectionRows: " + JSON.stringify(selectionRows));
        console.log("selectionRows.coachId: " + JSON.stringify(coachId));

        this.$refs.CoachEduResumeList.getListByCoachId(coachId);
        this.$refs.CoachSportResumeList.getListByCoachId(coachId);
        this.$refs.CoachTrainingList.getListByCoachId(coachId);
        this.$refs.CoachPaperList.getListByCoachId(coachId);
      },
      onClearSelected() {
        this.selectedRowKeys = [];
        this.selectionRows = [];

        this.$refs.CoachEduResumeList.queryParam.coachNo = null;
        this.$refs.CoachSportResumeList.queryParam.coachNo = null;
        this.$refs.CoachTrainingList.queryParam.coachNo = null;
        this.$refs.CoachPaperList.queryParam.coachNo = null;

        this.$refs.CoachEduResumeList.loadData();
        this.$refs.CoachSportResumeList.loadData();
        this.$refs.CoachTrainingList.loadData();
        this.$refs.CoachPaperList.loadData();

        this.$refs.CoachEduResumeList.selectedRowKeys = [];
        this.$refs.CoachEduResumeList.selectionRows = [];

        this.$refs.CoachSportResumeList.selectedRowKeys = [];
        this.$refs.CoachSportResumeList.selectionRows = [];

        this.$refs.CoachTrainingList.selectedRowKeys = [];
        this.$refs.CoachTrainingList.selectionRows = [];

        this.$refs.CoachPaperList.selectedRowKeys = [];
        this.$refs.CoachPaperList.selectionRows = [];
      },
      searchQuery:function(){
        this.selectedRowKeys = [];
        this.selectionRows = [];

        this.$refs.CoachEduResumeList.queryParam.coachNo = null;
        this.$refs.CoachSportResumeList.queryParam.coachNo = null;
        this.$refs.CoachTrainingList.queryParam.coachNo = null;
        this.$refs.CoachPaperList.queryParam.coachNo = null;

        this.$refs.CoachEduResumeList.loadData();
        this.$refs.CoachSportResumeList.loadData();
        this.$refs.CoachTrainingList.loadData();
        this.$refs.CoachPaperList.loadData();

        this.$refs.CoachEduResumeList.selectedRowKeys = [];
        this.$refs.CoachEduResumeList.selectionRows = [];

        this.$refs.CoachSportResumeList.selectedRowKeys = [];
        this.$refs.CoachSportResumeList.selectionRows = [];

        this.$refs.CoachTrainingList.selectedRowKeys = [];
        this.$refs.CoachTrainingList.selectionRows = [];

        this.$refs.CoachPaperList.selectedRowKeys = [];
        this.$refs.CoachPaperList.selectionRows = [];

        this.loadData();
      }




    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>