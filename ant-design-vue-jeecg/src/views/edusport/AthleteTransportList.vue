<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">

        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('运动员输送表')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
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
        :rowSelection="{fixed:true,selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
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

    <athleteTransport-modal ref="modalForm" @ok="modalFormOk"></athleteTransport-modal>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import AthleteTransportModal from './modules/AthleteTransportModal'
  import AthleteList from './AthleteList'
  import {initDictOptions, filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  import {getAction} from '@/api/manage'/* Tab修改@2019-12-12 */


  export default {
    name: "AthleteTransportList",
    mixins:[JeecgListMixin],
    components: {
      AthleteTransportModal,
      AthleteList/* Tab修改@2019-12-12 */
    },
    data () {
      return {
        description: '运动员输送表管理页面',
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
            title:'运动员等级',
            align:"center",
            dataIndex: 'athleteTechGrade',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['athleteTechGrade'], text+"")
              }
            }
          },
          {
            title:'输送教练员',
            align:"center",
            dataIndex: 'transportCoachId',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['transportCoachId'], text+"")
              }
            }
          },
          {
            title:'输送时间',
            align:"center",
            dataIndex: 'transportDate',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'吸收单位类别',
            align:"center",
            dataIndex: 'acceptDepartmentType',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['acceptDepartmentType'], text+"")
              }
            }
          },
          {
            title:'吸收单位',
            align:"center",
            dataIndex: 'acceptDepartment'
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
        url: {
          list: "/edusport/athleteTransport/list",
          delete: "/edusport/athleteTransport/delete",
          deleteBatch: "/edusport/athleteTransport/deleteBatch",
          exportXlsUrl: "/edusport/athleteTransport/exportXls",
          importExcelUrl: "edusport/athleteTransport/importExcel",
        },
        dictOptions:{
          athleteTechGrade:[],
          acceptDepartmentType:[],
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
        initDictOptions('tb_edu_sport,sport_name,sport_code').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'sportCode', res.result)
          }
        })
        initDictOptions('athlete_tech_grade').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'athleteTechGrade', res.result)
          }
        })
        initDictOptions('tb_edu_coach,coach_name,id').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'transportCoachId', res.result)
          }
        })
        initDictOptions('accept_department_type').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'acceptDepartmentType', res.result)
          }
        })
      },
      loadData(arg) {/* Tab修改@2019-12-12 */
        if (arg === 1) {
          this.ipagination.current = 1;
        }
        //update-begin--Author:kangxiaolin  Date:20190905 for：[442]主子表分开维护，生成的代码子表的分页改为真实的分页--------------------
        var params = this.getQueryParams();
        getAction(this.url.list, {athleteId: params.athleteId, pageNo : this.ipagination.current,
          pageSize :this.ipagination.pageSize}).then((res) => {
          if (res.success) {
            this.dataSource = res.result.records;
            this.ipagination.total = res.result.total;
          } else {
            this.dataSource = null;
          }
        })
        //update-end--Author:kangxiaolin  Date:20190905 for：[442]主子表分开维护，生成的代码子表的分页改为真实的分页--------------------

      },
      getAthlete(id) {/* Tab修改@2019-12-12 */
        this.queryParam.athleteId = id;
        this.loadData(1);
      },
      handleAdd: function () {
        this.$refs.modalForm.add(this.queryParam.athleteId);
        this.$refs.modalForm.title = "运动员输送信息";
      },

    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>