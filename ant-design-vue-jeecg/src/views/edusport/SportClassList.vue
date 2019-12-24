<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="训练队名">
              <a-input placeholder="请输入训练队名" v-model="queryParam.className"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="教练员">
              <j-search-select-tag placeholder="请选择教练员" v-model="queryParam.coachId" dict="tb_edu_coach,coach_name,id" />
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :md="6" :sm="8">
              <a-form-item label="运动项目">
                <j-search-select-tag placeholder="请选择运动项目" v-model="queryParam.sportCode" dict="tb_edu_sport,sport_name,sport_code" />
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
      <a-tab-pane tab="训练考勤" key="1">
        <Sport-Class-Athlete-Attend-List ref="SportClassAthleteAttendList"></Sport-Class-Athlete-Attend-List>
      </a-tab-pane>
      <a-tab-pane tab="训练请假" key="2" forceRender>
        <Sport-Class-Athlete-Leave-List ref="SportClassAthleteLeaveList"></Sport-Class-Athlete-Leave-List>
      </a-tab-pane>
      <a-tab-pane tab="训练队成员" key="3" forceRender>
        <Athlete-Sport-Class-List ref="AthleteSportClassList"></Athlete-Sport-Class-List>
      </a-tab-pane>
    </a-tabs>

    <sportClass-modal ref="modalForm" @ok="modalFormOk"></sportClass-modal>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  // import SportClassModal from './modules/SportClassModal'
  import SportClassModal from './modules/SportClassModal__Style#Drawer'
  import SportClassAthleteAttendList from './SportClassAthleteAttendList'
  import SportClassAthleteAttendModal from './modules/SportClassAthleteAttendModal'
  import SportClassAthleteLeaveList from './SportClassAthleteLeaveList'
  import SportClassAthleteLeaveModal from './modules/SportClassAthleteLeaveModal'
  import {initDictOptions, filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag.vue'
  import AthleteSportClassList from './AthleteSportClassList'
  import AthleteSportClassModal from './modules/AthleteSportClassModal__Style#Drawer'

  export default {
    name: "SportClassList",
    mixins:[JeecgListMixin],
    components: {
      SportClassModal,
      SportClassAthleteAttendList,
      SportClassAthleteAttendModal,
      SportClassAthleteLeaveList,
      SportClassAthleteLeaveModal,
      AthleteSportClassList,
      AthleteSportClassModal,
      JSearchSelectTag
    },
    data () {
      return {
        description: '训练队表管理页面',
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
        let sportClassId = this.selectedRowKeys[0];
        this.$refs.SportClassAthleteAttendList.getListBySportClassId(sportClassId);
        this.$refs.SportClassAthleteLeaveList.getListBySportClassId(sportClassId);
        this.$refs.AthleteSportClassList.getListBySportClassId(sportClassId);
      },

      onClearSelected() {
        this.selectedRowKeys = [];
        this.selectionRows = [];

        this.$refs.SportClassAthleteAttendList.queryParam.sportClassId = null;
        this.$refs.SportClassAthleteAttendList.loadData();
        this.$refs.SportClassAthleteAttendList.selectedRowKeys = [];
        this.$refs.SportClassAthleteAttendList.selectionRows = [];

        this.$refs.SportClassAthleteLeaveList.queryParam.sportClassId = null;
        this.$refs.SportClassAthleteLeaveList.loadData();
        this.$refs.SportClassAthleteLeaveList.selectedRowKeys = [];
        this.$refs.SportClassAthleteLeaveList.selectionRows = [];

        this.$refs.AthleteSportClassList.queryParam.sportClassId = null;
        this.$refs.AthleteSportClassList.loadData();
        this.$refs.AthleteSportClassList.selectedRowKeys = [];
        this.$refs.AthleteSportClassList.selectionRows = [];
      },

      searchQuery:function(){
        this.selectedRowKeys = [];
        this.selectionRows = [];

        this.$refs.SportClassAthleteAttendList.queryParam.sportClassId = null;
        this.$refs.SportClassAthleteAttendList.loadData();
        this.$refs.SportClassAthleteAttendList.selectedRowKeys = [];
        this.$refs.SportClassAthleteAttendList.selectionRows = [];

        this.$refs.SportClassAthleteLeaveList.queryParam.sportClassId = null;
        this.$refs.SportClassAthleteLeaveList.loadData();
        this.$refs.SportClassAthleteLeaveList.selectedRowKeys = [];
        this.$refs.SportClassAthleteLeaveList.selectionRows = [];

        this.$refs.AthleteSportClassList.queryParam.sportClassId = null;
        this.$refs.AthleteSportClassList.loadData();
        this.$refs.AthleteSportClassList.selectedRowKeys = [];
        this.$refs.AthleteSportClassList.selectionRows = [];
        this.loadData();
      }
       
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>