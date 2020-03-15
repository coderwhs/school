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
            <a-form-item label="测试组别">
              <j-search-select-tag placeholder="请选择测试组别" v-model="queryParam.groupId" dict="tb_edu_athlete_selection_group,group_name,id" />
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="测试大项">
              <j-search-select-tag placeholder="请选择测试大项" v-model="queryParam.sportCode" dict="tb_edu_sport,sport_name,sport_code" />
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
<!--      <a-button type="primary" icon="download" @click="handleExportXls('运动员选材测试表')">导出</a-button>-->
<!--      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">-->
<!--        <a-button type="primary" icon="import">导入</a-button>-->
<!--      </a-upload>-->
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
        :rowSelection="{fixed:false, selectedRowKeys: selectedRowKeys, onChange: onSelectChange, type: tabSelectType}"
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

    <!--table区域-end-->

    <a-tabs defaultActiveKey="1">
      <a-tab-pane tab="各教练员测试成绩" key="1">
        <Athlete-Selection-Test-Coach-List ref="AthleteSelectionTestCoachList"></Athlete-Selection-Test-Coach-List>
      </a-tab-pane>
    </a-tabs>

    <athleteSelectionTest-modal ref="modalForm" @ok="modalFormOk"></athleteSelectionTest-modal>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import JDictSelectTag from '@/components/dict/JDictSelectTag.vue'
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag'
  import AthleteSelectionTestModal from './modules/AthleteSelectionTestModal'
  import AthleteSelectionTestCoachList from './AthleteSelectionTestCoachList'
  import AthleteSelectionTestCoachModal from './modules/AthleteSelectionTestCoachModal'
  import {initDictOptions, filterMultiDictText} from '@/components/dict/JDictSelectUtil'

  export default {
    name: "AthleteSelectionTestList",
    mixins:[JeecgListMixin],
    components: {
      JDictSelectTag,
      JSearchSelectTag,
      AthleteSelectionTestModal,
      AthleteSelectionTestCoachList,
      AthleteSelectionTestCoachModal,
    },
    data () {
      return {
        description: '运动员选材测试表管理页面',
        // Table记录选择器修改为单选框
        tabSelectType: "radio",
        isorter: {
          // 排序由后端处理
          column: '',
          order: ''
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
            title:'测试名称',
            align:"center",
            dataIndex: 'testName'
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
            title:'测试大项',
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
            title:'发布日期',
            align:"center",
            dataIndex: 'publishDate',
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
        url: {
          list: "/edusport/athleteSelectionTest/list",
          delete: "/edusport/athleteSelectionTest/delete",
          deleteBatch: "/edusport/athleteSelectionTest/deleteBatch",
          exportXlsUrl: "/edusport/athleteSelectionTest/exportXls",
          importExcelUrl: "edusport/athleteSelectionTest/importExcel",
        },
        dictOptions:{
          groupId:[],
          sportCode:[],
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
        initDictOptions('tb_edu_athlete_selection_group,group_name,id').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'groupId', res.result)
          }
        })
        initDictOptions('tb_edu_sport,sport_name,sport_code').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'sportCode', res.result)
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
        let testId = this.selectedRowKeys[0];

        this.$refs.AthleteSelectionTestCoachList.getListByTestId(testId);
      },

      onClearSelected() {
        this.selectedRowKeys = [];
        this.selectionRows = [];

        this.$refs.AthleteSelectionTestCoachList.queryParam.testId = null;
        this.$refs.AthleteSelectionTestCoachList.loadData();
        this.$refs.AthleteSelectionTestCoachList.selectedRowKeys = [];
        this.$refs.AthleteSelectionTestCoachList.selectionRows = [];
      },

      searchQuery: function() {
        this.selectedRowKeys = [];
        this.selectionRows = [];
        this.loadData();

        // Tab内容检索
          this.$refs.AthleteSelectionTestCoachList.queryParam.testId = this.queryParam.testName;
          this.$refs.AthleteSelectionTestCoachList.queryParam.groupId = this.queryParam.groupId;
          this.$refs.AthleteSelectionTestCoachList.queryParam.sportCode = this.queryParam.sportCode;
          this.$refs.AthleteSelectionTestCoachList.loadData();
          this.$refs.AthleteSelectionTestCoachList.selectedRowKeys = [];
          this.$refs.AthleteSelectionTestCoachList.selectionRows = [];
      }

    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>