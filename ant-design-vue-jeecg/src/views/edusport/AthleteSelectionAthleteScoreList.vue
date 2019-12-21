<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="运动员">
<!--              <a-input placeholder="请输入运动员" v-model="queryParam.athleteId"></a-input>-->
              <j-search-select-tag v-decorator="['athleteId']" dict="tb_edu_athlete,athlete_name,id" />
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="所属测试">
<!--              <a-input placeholder="请输入所属测试" v-model="queryParam.testId"></a-input>-->
              <j-search-select-tag v-decorator="['testId']" dict="tb_edu_athlete_selection_test,test_name,id" />
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :md="6" :sm="8">
              <a-form-item label="测试组别">
<!--                <a-input placeholder="请输入测试组别" v-model="queryParam.groupId"></a-input>-->
                <j-search-select-tag v-decorator="['groupId']" dict="tb_edu_athlete_selection_group,group_name,id" />
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="8">
              <a-form-item label="测试等级评定">
                <j-dict-select-tag placeholder="请选择测试等级评定" v-model="queryParam.testGrade" dictCode="tb_edu_athlete_selection_group_rating,rating,id"/>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="8">
              <a-form-item label="审核状态">
                <j-dict-select-tag placeholder="请选择审核状态" v-model="queryParam.auditState" dictCode="audit_state"/>
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
<!--            <a-button @click="handleImportAthlete"  type="primary" icon="plus">引入</a-button>-->
            <a-button @click="handleCalculateScore"  type="primary" icon="plus">计算</a-button>
            <a-button @click="handleAudit" type="primary" icon="plus">审核</a-button>
<!--      <a-button type="primary" icon="download" @click="handleExportXls('运动员选材测试成绩表')">导出</a-button>-->
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
        :rowSelection="{fixed:true,selectedRowKeys: selectedRowKeys, onChange: onSelectChange, type:tabSelectType}"
        :scroll="tableScroll"
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
        <a-tabs defaultActiveKey="1">
          <a-tab-pane tab="运动员成绩明细" key="1">
            <Athlete-Selection-Athlete-Score-Detail-List ref="AthleteSelectionAthleteScoreDetailList"></Athlete-Selection-Athlete-Score-Detail-List>
          </a-tab-pane>
        </a-tabs>
    <athleteSelectionAthleteScore-modal ref="modalForm" @ok="modalFormOk"></athleteSelectionAthleteScore-modal>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import AthleteSelectionAthleteScoreModal from './modules/AthleteSelectionAthleteScoreModal'
  import JDictSelectTag from '@/components/dict/JDictSelectTag.vue'
  import {initDictOptions, filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  import AthleteSelectionAthleteScoreDetailList from './AthleteSelectionAthleteScoreDetailList'
  import AthleteSelectionAthleteScoreDetailModal from './modules/AthleteSelectionAthleteScoreDetailModal'
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag'
  import { getAction,httpAction,deleteAction  } from '@/api/manage'

  export default {
    name: "AthleteSelectionAthleteScoreList",
    mixins:[JeecgListMixin],
    components: {
      JDictSelectTag,
      AthleteSelectionAthleteScoreModal,
      AthleteSelectionAthleteScoreDetailModal,
      AthleteSelectionAthleteScoreDetailList,
      JSearchSelectTag
    },
    data () {
      return {
        description: '运动员选材测试成绩表管理页面',
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
            title:'运动员',
            align:"center",
            dataIndex: 'athleteId',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['athleteId'], text+"")
              }
            }
          },
          {
            title:'所属测试',
            align:"center",
            dataIndex: 'testId',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['testId'], text+"")
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
            title:'小项',
            align:"center",
            dataIndex: 'eventCode',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['eventCode'], text+"")
              }
            }
          },
          {
            title:'得分',
            align:"center",
            dataIndex: 'testScore'
          },
          {
            title:'测试等级评定',
            align:"center",
            dataIndex: 'testGrade',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['testGrade'], text+"")
              }
            }
          },
          {
            title:'审核状态',
            align:"center",
            dataIndex: 'auditState',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['auditState'], text+"")
              }
            }
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' }
          }
        ],
        tabSelectType:"radio",/* Tab修改@2019-12-12 */
        url: {
          list: "/edusport/athleteSelectionAthleteScore/list",
          delete: "/edusport/athleteSelectionAthleteScore/delete",
          deleteBatch: "/edusport/athleteSelectionAthleteScore/deleteBatch",
          exportXlsUrl: "/edusport/athleteSelectionAthleteScore/exportXls",
          importExcelUrl: "edusport/athleteSelectionAthleteScore/importExcel",
          // importAthlete:"/edusport/athleteSelectionAthleteScore/importAthlete",
          calculateScore:"/edusport/athleteSelectionAthleteScore/calculateScore",
          audit:"/edusport/athleteSelectionAthleteScore/audit",
        },
        dictOptions:{
         eventCode:[],
         testGrade:[],
         auditState:[],
        },
        tableScroll:{x :7*147+50}
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      initDictConfig(){
        initDictOptions('tb_edu_athlete,athlete_name,id').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'athleteId', res.result)
          }
        })
        initDictOptions('tb_edu_athlete_selection_test,test_name,id').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'testId', res.result)
          }
        })
        initDictOptions('tb_edu_athlete_selection_group,group_name,id').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'groupId', res.result)
          }
        })
        initDictOptions('tb_edu_sport,sport_name,sport_code').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'eventCode', res.result)
          }
        })
        initDictOptions('tb_edu_athlete_selection_group_rating,rating,id').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'testGrade', res.result)
          }
        })
        initDictOptions('audit_state').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'auditState', res.result)
          }
        })
      },

      // 处理成绩信息.
      getAthleteScore(id,groupId) {
        this.queryParam.testId = id;
        this.queryParam.groupId = groupId;
        this.loadData(1);
      },
      handleAdd: function () {
        this.$refs.modalForm.add(this.queryParam.testId,this.queryParam.groupId);
        this.$refs.modalForm.title = "运动员成绩信息";
      },
      // 处理成绩明细信息.
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
        this.queryParam.id = selectionRows[0].id;// 为计算应用参数值.
        // id,athleteId ,testId,groupId,eventCode
        //alert(selectionRows[0].id + ", " + selectionRows[0].athleteId + ", " + selectionRows[0].testId + ", " + selectionRows[0].groupId + ", " + selectionRows[0].eventCode);
        this.$refs.AthleteSelectionAthleteScoreDetailList.getAthleteScoreId(selectionRows[0].id,selectionRows[0].athleteId,selectionRows[0].testId,selectionRows[0].groupId,selectionRows[0].eventCode);
      },

      searchQuery:function(){/* Tab修改@2019-12-12 */
        this.selectedRowKeys = [];
        this.selectionRows = [];
        this.$refs.AthleteSelectionAthleteScoreDetailList.queryParam.athleteScoreId = null;
        this.$refs.AthleteSelectionAthleteScoreDetailList.queryParam.athleteId = null;
        this.$refs.AthleteSelectionAthleteScoreDetailList.queryParam.testId = null;
        this.$refs.AthleteSelectionAthleteScoreDetailList.queryParam.groupId = null;
        this.$refs.AthleteSelectionAthleteScoreDetailList.queryParam.eventCode = null;
        this.$refs.AthleteSelectionAthleteScoreDetailList.loadData();
        this.$refs.AthleteSelectionAthleteScoreDetailList.selectedRowKeys = [];
        this.$refs.AthleteSelectionAthleteScoreDetailList.selectionRows = [];
        this.loadData();
      },

      onClearSelected() {/* Tab修改@2019-12-12 */
        this.selectedRowKeys = [];
        this.selectionRows = [];
        this.$refs.AthleteSelectionAthleteScoreDetailList.queryParam.athleteScoreId = null;
        this.$refs.AthleteSelectionAthleteScoreDetailList.queryParam.athleteId = null;
        this.$refs.AthleteSelectionAthleteScoreDetailList.queryParam.testId = null;
        this.$refs.AthleteSelectionAthleteScoreDetailList.queryParam.groupId = null;
        this.$refs.AthleteSelectionAthleteScoreDetailList.queryParam.eventCode = null;
        this.$refs.AthleteSelectionAthleteScoreDetailList.loadData();
        this.$refs.AthleteSelectionAthleteScoreDetailList.selectedRowKeys = [];
        this.$refs.AthleteSelectionAthleteScoreDetailList.selectionRows = [];
      },

      // 引入运动员.
      handleImportAthlete: function () {

      },
      // 计算运动员成绩.
      handleCalculateScore: function () {
        if(this.queryParam.id){
          // typeof thisObj.city === 'undefined'
          let params = {
            id: this.queryParam.id
          };
          console.log("表单提交数据", params);

          getAction(this.url.calculateScore, params).then((res) => {
            if (res.success) {
              this.$message.success(res.message);
              //this.$emit('ok');
            } else {
              this.$message.warning(res.message);
            }
          }).finally(() => {
            this.loadData(1);
          })
        } else {
          alert("请选择一条学生信息");
        }
      },

      // 审核运动员成绩.
      handleAudit: function () {
        if(this.queryParam.id){
          // typeof thisObj.city === 'undefined'
          let params = {
            id: this.queryParam.id
          };

          getAction(this.url.audit, params).then((res) => {
            if (res.success) {
              this.$message.success(res.message);
              //this.$emit('ok');
            } else {
              this.$message.warning(res.message);
            }
          }).finally(() => {
            this.loadData(1);
          })
        } else {
          alert("请选择一条学生信息");
        }
      }

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