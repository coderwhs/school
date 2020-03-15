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
   <!-- <div class="table-operator"> -->
     <!-- <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button> -->
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
   <!-- </div> -->

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
          <a @click="handleEdit(record)"> 处理 </a>
          <!-- <a @click="handleApply(record)"> 同意 |</a>
          <a @click="handleReject(record)"> 不同意 |</a> -->
          <!-- <a @click="handleProcessDiagram(record)"> | 跟踪 </a> -->
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
  import TactRuTaskProcessDiagramModal from './modules/TactRuTaskProcessDiagramModal'
  import {getAction, httpAction} from '@/api/manage'
  import {initDictOptions, filterMultiDictText} from '@/components/dict/JDictSelectUtil'

  export default {
    name: "TactRuTaskList",
    mixins:[JeecgListMixin],
    components: {
      TactRuTaskModal,
      TactRuTaskProcessDiagramModal,
    },
    data () {
      return {
        description: 'tact_ru_task管理页面',
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
            title:'当前步骤',
            align:"center",
            dataIndex: 'name'
          },
          {
            title:'流程名称',
            align:"center",
            dataIndex: 'category'
          },
          {
            title:'申请理由',
            align:"center",
            dataIndex: 'delegation'
          },
          // {
          //   title:'是否同意',
          //   align:"center",
          //   dataIndex: 'isAgree',
          //   customRender:(text)=>{
          //     if(!text){
          //       return ''
          //     }else{
          //       return filterMultiDictText(this.dictOptions['isAgree'], text+"")
          //     }
          //   }
          // },
          {
            title:'审批意见',
            align:"center",
            dataIndex: 'description'
          },
          // {
          //   title:'任务描述',
          //   align:"center",
          //   dataIndex: 'owner'
          // },
          {
            title:'申请人',
            align:"center",
            dataIndex: 'createBy'
          },
          // {
          //   title:'申请时间',
          //   align:"center",
          //   dataIndex: 'createTime',
          //   customRender:function (text) {
          //     return !text?"":(text.length>10?text.substr(0,10):text)
          //   }
          // },
          // {
          //   title:'审核人',
          //   align:"center",
          //   dataIndex: 'updateBy'
          // },
          // {
          //   title:'审核时间',
          //   align:"center",
          //   dataIndex: 'updateTime',
          //   customRender:function (text) {
          //     return !text?"":(text.length>10?text.substr(0,10):text)
          //   }
          // },
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
          isAgree:[],
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
        initDictOptions('is_agree').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'isAgree', res.result)
          }
        })
      },
      // 同意.
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
      // 拒绝.
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
            // alert("拒绝操作成功" + res.message);
            //this.$emit('ok');
          }else{
            this.$message.warning(res.message);
            // alert("拒绝操作失败" + res.message);
          }
        }).finally(() => {
          this.loadData(1);
        })
      },
      // 生成流程图.
      handleProcessDiagram: function (record) {
        this.$nextTick(() => {
          this.$refs.TactRuTaskProcessDiagramModal.tactRuTaskProcessDiagram(record);        
        })

        // let httpurl = '';
        // let method = '';
        // httpurl+=this.url.processDiagram;
        // method = 'put';
        // // this.model = Object.assign({}, record);
        // // let formData = Object.assign(this.model, record);
        // // console.log("表单提交数据",formData);
        // getAction(httpurl,{procInstId: procInstId},method)
        // .then((res)=>{
        //   console.log("res", res)
        //   // if(res.success){
        //   //   this.$message.success(res.message);
        //   //   //this.$emit('ok');
        //   // }else{
        //   //   this.$message.warning(res.message);
        //   // }
        // }).finally(() => {
        //   // this.loadData(1);
        // })
      },
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>