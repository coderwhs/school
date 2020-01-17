<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="教练员">
              <a-input placeholder="请输入教练员" v-model="queryParam.coachId"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="运动员">
              <a-input placeholder="请输入运动员" v-model="queryParam.althleteNos"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :md="12" :sm="16">
              <a-form-item label="测试日期">
                <j-date placeholder="请选择开始日期" class="query-group-cust" v-model="queryParam.testDate_begin"></j-date>
                <span class="query-group-split-cust"></span>
                <j-date placeholder="请选择结束日期" class="query-group-cust" v-model="queryParam.testDate_end"></j-date>
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
      <!-- <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button> -->
      <a-button type="primary" icon="download" @click="handleExportXls('教练表')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :format ="['xls']" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <div v-if="selectedRowKeys.length > 0">
          <a-button type="primary" icon="import" @click="importExcelBtn">导入</a-button>
        </div>
        <div v-else>
          <a-button type="primary" icon="import" @click.stop="importExcelBtn">导入</a-button>
        </div>
      </a-upload>
      <a-dropdown v-if="selectedRowKeys.length > 0" disabled="disabled">
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
        :rowSelection="{fixed:false,selectedRowKeys: selectedRowKeys, onChange: onSelectChange, type:tabSelectType}"
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
          <a @click="handleEdit(record)" disabled="disabled">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link" disabled="disabled">更多 <a-icon type="down" /></a>
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

    <outlineCoach-modal ref="modalForm" @ok="modalFormOk"></outlineCoach-modal>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import OutlineCoachModal from './modules/OutlineCoachModal'
  import JDate from '@/components/jeecg/JDate.vue'
  import {initDictOptions, filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  import { filterObj } from '@/utils/util';
  import Vue from 'vue'
  import { ACCESS_TOKEN } from "@/store/mutation-types"
  import moment from "moment"
  import { deleteAction, getAction,downFile } from '@/api/manage'

  export default {
    name: "OutlineCoachList",
    mixins:[JeecgListMixin],
    components: {
      JDate,
      OutlineCoachModal
    },
    data () {
      return {
        description: 'tb_edu_outline_coach管理页面',
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
            title:'大纲',
            align:"center",
            dataIndex: 'outlineId',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['outlineId'], text+"")
              }
            }
          },
          {
            title:'大纲组别',
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
            title:'运动项目',
            align:"center",
            dataIndex: 'sportId',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['sportId'], text+"")
              }
            }
          },
          {
            title:'运动员',
            align:"center",
            dataIndex: 'althleteNos',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['althleteNos'], text+"")
              }
            }
          },
          // {
          //   title:'测试项目',
          //   align:"center",
          //   dataIndex: 'eventCodes',
          //   customRender:(text)=>{
          //     if(!text){
          //       return ''
          //     }else{
          //       return filterMultiDictText(this.dictOptions['eventCodes'], text+"")
          //     }
          //   }
          // },
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
            title:'测试日期',
            align:"center",
            dataIndex: 'testDate',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'导入状态',
            align:"center",
            dataIndex: 'state',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['state'], text+"")
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
        tabSelectType:"radio",
        url: {
          list: "/edusport/outlineCoach/list",
          delete: "/edusport/outlineCoach/delete",
          deleteBatch: "/edusport/outlineCoach/deleteBatch",
          exportXlsUrl: "/edusport/outlineCoach/exportXls",
          importExcelUrl: "edusport/outlineCoach/importExcel",
        },
        dictOptions:{
         althleteNos:[],
         eventCodes:[],
         indexCodes:[],
         state:[],
        },
        tableScroll:{x :8*147+50},
        ok: false,
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
        initDictOptions('tb_edu_athlete_selection_test,test_name,id').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'outlineId', res.result)
          }
        })
        initDictOptions('tb_edu_athlete_selection_group,group_name,id').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'groupId', res.result)
          }
        })
        initDictOptions('tb_edu_sport,sport_name,id').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'sportId', res.result)
          }
        })
        initDictOptions('tb_edu_athlete,athlete_name,athlete_no').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'althleteNos', res.result)
          }
        })
        initDictOptions('tb_edu_sport_small,event_name,event_code').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'eventCodes', res.result)
          }
        })
        initDictOptions('tb_edu_athlete_selection_index,cn_name,l3_code').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'indexCodes', res.result)
          }
        })
        initDictOptions('import_status').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'state', res.result)
          }
        })
      },
      onSelectChange(selectedRowKeys, selectionRows) {
        this.selectedRowKeys = selectedRowKeys;
        this.selectionRows = selectionRows;
        this.queryParam.id = selectedRowKeys[0];
        this.url.importExcelUrl = this.url.importExcelUrl.split("?")[0];
      },
      onClearSelected() {
        this.selectedRowKeys = [];
        this.selectionRows = [];
        this.queryParam.id = [];
        this.url.importExcelUrl = this.url.importExcelUrl.split("?")[0];
      },
      importExcelBtn(event){
        if(this.selectedRowKeys.length > 0){
          this.url.importExcelUrl = this.url.importExcelUrl.split("?")[0];
          this.url.importExcelUrl = this.url.importExcelUrl + '?id=' + this.queryParam.id;
        } else {
          alert("请选择一条记录!");
        }
      },
      
      // /* 导入 */
      // handleImportExcel(info){
      //   if(this.selectedRowKeys.length<=0){
      //     alert("请选择一条记录!");
      //   } else {
      //     if (info.file.status !== 'uploading') {
      //       console.log(info.file, info.fileList);
      //     }
      //     if (info.file.status === 'done') {
      //       if (info.file.response.success) {
      //         // this.$message.success(`${info.file.name} 文件上传成功`);
      //         if (info.file.response.code === 201) {
      //           let { message, result: { msg, fileUrl, fileName } } = info.file.response
      //           let href = window._CONFIG['domianURL'] + fileUrl;
      //           this.$warning({
      //             title: message,
      //             content: (
      //               <div>
      //                 <span>{msg}</span><br/>
      //                 <span>具体详情请 <a href={href} target="_blank" download={fileName}>点击下载</a> </span>
      //               </div>
      //             )
      //           })
      //         } else {
      //           this.$message.success(info.file.response.message || `${info.file.name} 文件上传成功`)
      //         }
      //         this.loadData()
      //       } else {
      //         this.$message.error(`${info.file.name} ${info.file.response.message}.`);
      //       }
      //     } else if (info.file.status === 'error') {
      //       this.$message.error(`文件上传失败: ${info.file.msg} `);
      //     }
      //   }
      // },

      handleExportXls(fileName){
        if(this.selectedRowKeys.length<=0){
          alert("请选择一条记录!");
        } else {
          if(!fileName || typeof fileName != "string"){
            fileName = "导出文件"
          }
          let param = {...this.queryParam};
          if(this.selectedRowKeys && this.selectedRowKeys.length>0){
            param['selections'] = this.selectedRowKeys.join(",")
          }
          console.log("导出参数",param)
          downFile(this.url.exportXlsUrl,param).then((data)=>{
            if (!data) {
              this.$message.warning("文件下载失败")
              return
            }
            if (typeof window.navigator.msSaveBlob !== 'undefined') {
                window.navigator.msSaveBlob(new Blob([data],{ type: 'application/vnd.ms-excel' }), fileName + moment(new Date()).format("YYYY-DD-MM") + '.xls')
            }else{
              let url = window.URL.createObjectURL(new Blob([data],{ type: 'application/vnd.ms-excel' }))
              let link = document.createElement('a')
              link.style.display = 'none'
              link.href = url
              link.setAttribute('download', fileName + moment(new Date()).format("YYYY-MM-DD") + '.xls')
              document.body.appendChild(link)
              link.click()
              document.body.removeChild(link); //下载完成移除元素
              window.URL.revokeObjectURL(url); //释放掉blob对象
            }
          })
        }
      },
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>