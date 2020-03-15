<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="姓名">
              <a-input placeholder="请输入姓名" v-model="queryParam.athleteName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="专业项目">
              <j-search-select-tag placeholder="请选择专业项目" v-model="queryParam.sportCode" dict="tb_edu_sport,sport_name,sport_code" />
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="带训教练">
              <j-search-select-tag placeholder="请选择教练员" v-model="queryParam.coachId" dict="tb_edu_coach,coach_name,id" />
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :md="6" :sm="8">
              <a-form-item label="性别">
                <j-dict-select-tag placeholder="请选择性别" v-model="queryParam.gender" dictCode="sex"/>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="8">
              <a-form-item label="学号">
                <a-input placeholder="请输入学号" v-model="queryParam.athleteNo"></a-input>
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
      <a-button type="primary" icon="download" @click="handleExportXls('运动员信息表')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <!--<a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>-->
<!--      <a-button @click="handleSignUp" type="primary" icon="unlock">开通账号</a-button>-->
<!--      <a-button @click="handleSignLock" type="primary" icon="lock">冻结账号</a-button>-->
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
                <a-popconfirm title="确定删除吗?删除有风险，操作需谨慎!" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
              <a-menu-item>
                <a @click="handleSignUp(record)" icon="unlock">开通账号</a>
              </a-menu-item>
              <a-menu-item>
                <a @click="handleSignLock(record)" icon="lock">冻结账号</a>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <a-tabs defaultActiveKey="1">
      <a-tab-pane tab="运动员运动员参赛记录" key="1">
        <Athlete-Contest-List ref="AthleteContestList"></Athlete-Contest-List>
      </a-tab-pane>
      <a-tab-pane tab="运动员文化成绩" key="2" forceRender>
        <Athlete-Literacy-Score-List ref="AthleteLiteracyScoreList"></Athlete-Literacy-Score-List>
      </a-tab-pane>
      <a-tab-pane tab="运动员输送记录" key="3" forceRender>
        <Athlete-Transport-List ref="AthleteTransportList"></Athlete-Transport-List>
      </a-tab-pane>
      <a-tab-pane tab="运动员其他训练事项记录" key="4" forceRender>
        <Athlete-Other-Trianning-Info-List ref="AthleteOtherTrianningInfoList"></Athlete-Other-Trianning-Info-List>
      </a-tab-pane>
<!--      <a-tab-pane tab="运动员测试记录" key="5" forceRender>-->
<!--        <Athlete-Sport-Score-List ref="AthleteSportScoreList"></Athlete-Sport-Score-List>-->
<!--      </a-tab-pane>-->
    </a-tabs>

    <athlete-modal ref="modalForm" @ok="modalFormOk"></athlete-modal>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  // import AthleteModal from './modules/AthleteModal'
  import AthleteModal from './modules/AthleteModal__Style#Drawer'
  import AthleteContestList from './AthleteContestList'
  // import AthleteSportScoreList from './AthleteSportScoreList'
  import AthleteTransportList from './AthleteTransportList'
  import AthleteOtherTrianningInfoList from './AthleteOtherTrianningInfoList'
  import AthleteLiteracyScoreList from './AthleteLiteracyScoreList'
  import AthleteLiteracyScoreModal from './modules/AthleteLiteracyScoreModal__Style#Drawer'

  import AthleteTransportModal from './modules/AthleteTransportModal'
  import AthleteSportScoreModal from './modules/AthleteSportScoreModal'
  import AthleteOtherTrianningInfoModal from './modules/AthleteOtherTrianningInfoModal'
  import AthleteContestModal from './modules/AthleteContestModal'/* Tab修改@2019-12-12 */
  import JDictSelectTag from '@/components/dict/JDictSelectTag.vue'
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag.vue'
  import {initDictOptions, filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  import {deleteAction, putAction} from '@/api/manage'


  export default {
    name: "AthleteList",
    mixins:[JeecgListMixin],
    components: {
      JDictSelectTag,
      JSearchSelectTag,
      AthleteModal,
      AthleteTransportList,
      // AthleteSportScoreList,
      AthleteOtherTrianningInfoList,
      AthleteContestList,
      AthleteTransportModal,
      AthleteSportScoreModal,
      AthleteOtherTrianningInfoModal,
      AthleteContestModal,
      AthleteLiteracyScoreList,
      AthleteLiteracyScoreModal,
      /* Tab修改@2019-12-12 */
    },
    data () {
      return {
        description: '运动员信息表管理页面',
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
            dataIndex: 'athleteName'
          },
          {
            title:'学号',
            align:"center",
            dataIndex: 'athleteNo'
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
            title:'出生日期',
            align:"center",
            dataIndex: 'birthDate',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'专业项目',
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
            title:'带训教练',
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
            title:'入队时间',
            align:"center",
            dataIndex: 'majorSportAttendDate',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          // {
          //   title:'身高(cm)',
          //   align:"center",
          //   dataIndex: 'athleteHeight'
          // },
          // {
          //   title:'体重(kg)',
          //   align:"center",
          //   dataIndex: 'athleteWeight'
          // },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            width:147,
            scopedSlots: { customRender: 'action' }
          }
        ],
        tabSelectType: "radio",/* Tab修改@2019-12-12 */
        url: {
          list: "/edusport/athlete/list",
          delete: "/edusport/athlete/delete",
          deleteBatch: "/edusport/athlete/deleteBatch",
          exportXlsUrl: "/edusport/athlete/exportXls",
          importExcelUrl: "edusport/athlete/importExcel",
          signUp: "/edusport/athlete/signUp",
          signLock: "/edusport/athlete/signLock",
        },
        dictOptions:{
          gender:[],
          nation:[],
        },
        tableScroll:{x :10*147+50}
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
        initDictOptions('tb_edu_sport,sport_name,sport_code').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'sportCode', res.result)
          }
        })
        initDictOptions('tb_edu_coach,coach_name,id').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'coachId', res.result)
          }
        })
      },

      handleSignUp: function (record) {
        var that = this;
        putAction(that.url.signUp, record).then((res) => {
          if (res.success) {
            that.$message.success(res.message);
            that.loadData();
          } else {
            that.$message.warning(res.message);
          }
        })
      },
      handleSignLock: function (record) {
        var that = this;
        putAction(that.url.signLock, record).then((res) => {
          if (res.success) {
            that.$message.success(res.message);
            that.loadData();
          } else {
            that.$message.warning(res.message);
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
        let athleteId = this.selectedRowKeys[0];
        console.log("athleteId = " + athleteId)

        this.$refs.AthleteContestList.getAthleteByAthleteId(athleteId);
        this.$refs.AthleteLiteracyScoreList.getAthleteByAthleteId(athleteId);
        this.$refs.AthleteTransportList.getAthleteByAthleteId(athleteId);
        this.$refs.AthleteOtherTrianningInfoList.getAthleteByAthleteId(athleteId);
        // this.$refs.AthleteSportScoreList.getAthleteByAthleteId(athleteId);
        /* Tab修改@2019-12-12 */
      },
      onClearSelected() {/* Tab修改@2019-12-12 */
        this.selectedRowKeys = [];
        this.selectionRows = [];

        this.$refs.AthleteContestList.queryParam.athleteId = null;
        this.$refs.AthleteLiteracyScoreList.queryParam.athleteId = null;
        this.$refs.AthleteTransportList.queryParam.athleteId = null;
        this.$refs.AthleteOtherTrianningInfoList.queryParam.athleteId = null;
        // this.$refs.AthleteSportScoreList.queryParam.athleteId = null;

        this.$refs.AthleteContestList.loadData(1);
        this.$refs.AthleteLiteracyScoreList.loadData(1);
        this.$refs.AthleteTransportList.loadData(1);
        this.$refs.AthleteOtherTrianningInfoList.loadData(1);
        // this.$refs.AthleteSportScoreList.loadData(1);

        this.$refs.AthleteContestList.selectedRowKeys = [];
        this.$refs.AthleteContestList.selectionRows = [];
        this.$refs.AthleteLiteracyScoreList.selectedRowKeys = [];
        this.$refs.AthleteLiteracyScoreList.selectionRows = [];
        this.$refs.AthleteTransportList.selectedRowKeys = [];
        this.$refs.AthleteTransportList.selectionRows = [];
        this.$refs.AthleteOtherTrianningInfoList.selectedRowKeys = [];
        this.$refs.AthleteOtherTrianningInfoList.selectionRows = [];
        // this.$refs.AthleteSportScoreList.selectedRowKeys = [];
        // this.$refs.AthleteSportScoreList.selectionRows = [];

      },

      handleDelete: function (id) {/* Tab修改@2019-12-12 */
        var that = this;
        deleteAction(that.url.delete, {id: id}).then((res) => {
          if (res.success) {
            that.$message.success(res.message);

            that.loadData(1);
            this.$refs.AthleteTransportList.loadData(1);
            this.$refs.AthleteOtherTrianningInfoList.loadData(1);
            this.$refs.AthleteContestList.loadData(1);
            this.$refs.AthleteLiteracyScoreList.loadData(1);
            // this.$refs.AthleteSportScoreList.loadData(1);

          } else {
            that.$message.warning(res.message);
          }
        });
      },

      searchQuery:function(){/* Tab修改@2019-12-12 */
        this.selectedRowKeys = [];
        this.selectionRows = [];
        this.loadData(1);

        this.$refs.AthleteContestList.queryParam.athleteName = this.queryParam.athleteName;
        this.$refs.AthleteContestList.queryParam.contestSportCode = this.queryParam.sportCode;
        this.$refs.AthleteContestList.queryParam.coachId = this.queryParam.coachId;

        this.$refs.AthleteLiteracyScoreList.queryParam.athleteId = this.queryParam.athleteName;
        this.$refs.AthleteLiteracyScoreList.queryParam.sportCode = this.queryParam.sportCode;
        this.$refs.AthleteLiteracyScoreList.queryParam.coachId = this.queryParam.coachId;

        this.$refs.AthleteTransportList.queryParam.athleteId = this.queryParam.athleteName;
        this.$refs.AthleteTransportList.queryParam.sportCode = this.queryParam.sportCode;
        this.$refs.AthleteTransportList.queryParam.transportCoachId = this.queryParam.coachId;

        // this.$refs.AthleteSportScoreList.queryParam.athleteId = null;
        this.$refs.AthleteOtherTrianningInfoList.queryParam.athleteId = this.queryParam.athleteName;
        this.$refs.AthleteOtherTrianningInfoList.queryParam.sportCode = this.queryParam.sportCode;
        this.$refs.AthleteOtherTrianningInfoList.queryParam.coachId = this.queryParam.coachId;

        this.$refs.AthleteContestList.loadData(1);
        this.$refs.AthleteLiteracyScoreList.loadData(1);
        this.$refs.AthleteTransportList.loadData(1);
        this.$refs.AthleteOtherTrianningInfoList.loadData(1);
        // this.$refs.AthleteSportScoreList.loadData(1);

        this.$refs.AthleteContestList.selectedRowKeys = [];
        this.$refs.AthleteContestList.selectionRows = [];
        this.$refs.AthleteLiteracyScoreList.selectionRows = [];
        this.$refs.AthleteTransportList.selectionRows = [];
        this.$refs.AthleteOtherTrianningInfoList.selectedRowKeys = [];
        this.$refs.AthleteOtherTrianningInfoList.selectionRows = [];
        // this.$refs.AthleteSportScoreList.selectedRowKeys = [];
        // this.$refs.AthleteSportScoreList.selectionRows = [];
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
