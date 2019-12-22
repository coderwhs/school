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
<!--    <div class="table-operator">-->
<!--      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>-->
<!--      <a-button type="primary" icon="download" @click="handleExportXls('tact_ru_task')">导出</a-button>-->
<!--      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">-->
<!--        <a-button type="primary" icon="import">导入</a-button>-->
<!--      </a-upload>-->
<!--      <a-dropdown v-if="selectedRowKeys.length > 0">-->
<!--        <a-menu slot="overlay">-->
<!--          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>-->
<!--        </a-menu>-->
<!--        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>-->
<!--      </a-dropdown>-->
<!--    </div>-->

    <!-- table区域-begin -->
    <div>
<!--      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">-->
<!--        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项-->
<!--        <a style="margin-left: 24px" @click="onClearSelected">清空</a>-->
<!--      </div>-->

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
          <a @click="handleApply(record)"> 同意 |</a>
          <a @click="handleReject(record)"> 不同意 |</a>
          <a @click="handleEdit(record)"> 流程图 </a>
<!--          <a-divider type="vertical" />-->
          <!--<a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>-->
        </span>

      </a-table>
    </div>
    <tactRuTask-modal ref="modalForm" @ok="modalFormOk"></tactRuTask-modal>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import TactRuTaskModal from './modules/TactRuTaskModal'
  import {getAction, httpAction} from '@/api/manage'
  export default {
    name: "TactRuTaskList",
    mixins:[JeecgListMixin],
    components: {
      TactRuTaskModal
    },
    data () {
      return {
        description: 'tact_ru_task管理页面',
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
          // {
          //   title:'任务执行人',
          //   align:"center",
          //   dataIndex: 'assignee'
          // },
          /*{
            title:'category',
            align:"center",
            dataIndex: 'category'
          },
          {
            title:'claimTime',
            align:"center",
            dataIndex: 'claimTime',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },*/
          /*{
            title:'创建人',
            align:"center",
            dataIndex: 'createBy'
          },
          {
            title:'createTime',
            align:"center",
            dataIndex: 'createTime',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'delegation',
            align:"center",
            dataIndex: 'delegation'
          },
          {
            title:'description',
            align:"center",
            dataIndex: 'description'
          },
          {
            title:'dueDate',
            align:"center",
            dataIndex: 'dueDate',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'executionId',
            align:"center",
            dataIndex: 'executionId'
          },
          {
            title:'formKey',
            align:"center",
            dataIndex: 'formKey'
          },*/
          // {
          //   title:'任务ID',
          //   align:"center",
          //   dataIndex: 'id'
          // },
          /*{
            title:'idLinkCount',
            align:"center",
            dataIndex: 'idLinkCount'
          },
          {
            title:'isCountEnabled',
            align:"center",
            dataIndex: 'isCountEnabled'
          },*/
          {
            title:'任务描述',
            align:"left",
            dataIndex: 'description'
          },
          {
            title:'当前步骤',
            align:"center",
            dataIndex: 'name'
          },
          /*{
            title:'owner',
            align:"center",
            dataIndex: 'owner'
          },
          {
            title:'parentTaskId',
            align:"center",
            dataIndex: 'parentTaskId'
          },
          {
            title:'priority',
            align:"center",
            dataIndex: 'priority'
          },
          {
            title:'procDefId',
            align:"center",
            dataIndex: 'procDefId'
          },*/
          // {
          //   title:'流程ID',
          //   align:"center",
          //   dataIndex: 'procInstId'
          // },
          /*{
            title:'rev',
            align:"center",
            dataIndex: 'rev'
          },
          {
            title:'scopeDefinitionId',
            align:"center",
            dataIndex: 'scopeDefinitionId'
          },
          {
            title:'scopeId',
            align:"center",
            dataIndex: 'scopeId'
          },
          {
            title:'scopeType',
            align:"center",
            dataIndex: 'scopeType'
          },
          {
            title:'subScopeId',
            align:"center",
            dataIndex: 'subScopeId'
          },
          {
            title:'subTaskCount',
            align:"center",
            dataIndex: 'subTaskCount'
          },
          {
            title:'suspensionState',
            align:"center",
            dataIndex: 'suspensionState'
          },
          {
            title:'taskDefId',
            align:"center",
            dataIndex: 'taskDefId'
          },
          {
            title:'taskDefKey',
            align:"center",
            dataIndex: 'taskDefKey'
          },
          {
            title:'tenantId',
            align:"center",
            dataIndex: 'tenantId'
          },*/
          /*{
            title:'更新人',
            align:"center",
            dataIndex: 'updateBy'
          },
          {
            title:'更新时间',
            align:"center",
            dataIndex: 'updateTime',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'varCount',
            align:"center",
            dataIndex: 'varCount'
          },*/
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' }
          }
        ],
        url: {
          list: "/edusport/tactRuTask/list",
          apply:"/process/apply",
          reject:"/process/reject",
          processDiagram:"/process/processDiagram"
        },
        dictOptions:{

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
      },

      handleApply: function (record) {
        console.log("========================" + record.id);
        let httpurl = '';
        let method = '';
        httpurl+=this.url.apply;
        method = 'put';
        this.model = Object.assign({}, record);
        let formData = Object.assign(this.model, record);
        console.log("表单提交数据",formData);
        //formData.id = id;
        httpAction(httpurl,formData,method).then((res)=>{
          if(res.success){
            this.$message.success(res.message);
            //this.$emit('ok');
          }else{
            this.$message.warning(res.message);
          }
        }).finally(() => {
          this.loadData(1);
        })
      },

      handleReject: function (record) {
        let httpurl = '';
        let method = '';
        httpurl+=this.url.reject;
        method = 'put';
        this.model = Object.assign({}, record);
        let formData = Object.assign(this.model, record);
        console.log("表单提交数据",formData);
        //formData.id = id;
        httpAction(httpurl,formData,method).then((res)=>{
          if(res.success){
            this.$message.success(res.message);
            alert("拒绝操作成功" + res.message);
            //this.$emit('ok');
          }else{
            this.$message.warning(res.message);
            alert("拒绝操作失败" + res.message);
          }
        }).finally(() => {
          this.loadData(1);
        })
      },

      handleProcessDiagram: function (procInstId) {
        let httpurl = '';
        let method = '';
        httpurl+=this.url.processDiagram;
        method = 'put';
        // this.model = Object.assign({}, record);
        // let formData = Object.assign(this.model, record);
        // console.log("表单提交数据",formData);
        getAction(httpurl,{procInstId: procInstId},method)
        .then((res)=>{
          console.log("res", res)
          // if(res.success){
          //   this.$message.success(res.message);
          //   //this.$emit('ok');
          // }else{
          //   this.$message.warning(res.message);
          // }
        }).finally(() => {
          // this.loadData(1);
        })
      },
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>