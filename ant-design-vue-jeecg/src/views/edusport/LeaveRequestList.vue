<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="请假类型">
              <j-dict-select-tag placeholder="请选择请假类型" v-model="queryParam.requestType" dictCode=""/>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="流程状态">
              <j-dict-select-tag placeholder="请选择流程状态" v-model="queryParam.workflowStatus" dictCode=""/>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">

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
      <a-button @click="handleAdd" type="primary" icon="plus">我要请假</a-button>
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
          <a @click="handleEdit(record)">审批</a>
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

    <leaveRequest-modal ref="modalForm" @ok="modalFormOk"></leaveRequest-modal>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import LeaveRequestModal from './modules/LeaveRequestModal__Style#Drawer'
  import JDictSelectTag from '@/components/dict/JDictSelectTag.vue'
  import {initDictOptions, filterMultiDictText} from '@/components/dict/JDictSelectUtil'

  export default {
    name: "LeaveRequestList",
    mixins:[JeecgListMixin],
    components: {
      JDictSelectTag,
      LeaveRequestModal
    },
    data () {
      return {
        description: '待办任务页面',
        /* 查询条件 */
        queryParam: {
          enableStatus: '1',
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
            title:'请假类型',
            align:"center",
            dataIndex: 'requestType',
            customRender:(text)=> {
              if (!text) {
                return ''
              } else {
                return filterMultiDictText(this.dictOptions['requestType'], text + "")
              }
            }
          },
          {
            title:'申请人',
            align:"center",
            dataIndex: 'athleteName'
          },
          {
            title:'申请日期',
            align:"center",
            dataIndex: 'requestDate'
          },
          {
            title:'开始日期',
            align:"center",
            dataIndex: 'startDate'
          },
          {
            title:'结束日期',
            align:"center",
            dataIndex: 'endDate'
          },
          {
            title:'流程状态',
            align:"center",
            dataIndex: 'workflowStatus'
          },
          {
            title:'流程节点',
            align:"center",
            dataIndex: 'workflowNode'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' }
          }
        ],
        url: {
          list: "/edusport/leaveRequest/taskList",
          add: "/edusport/leaveRequest/add",
          delete: "/edusport/athleteSelectionIndex/delete",
          deleteBatch: "/edusport/athleteSelectionIndex/deleteBatch",
          exportXlsUrl: "/edusport/athleteSelectionIndex/exportXls",
          importExcelUrl: "edusport/athleteSelectionIndex/importExcel",
        },
        dictOptions:{
          requestType:[{text:"宿舍请假", title:"宿舍请假", value:"dormLeaveRequest"}, {text:"训练请假", title:"训练请假", value:"classLeaveRequest"}],
          approvalResult:[{text:"同意", title:"同意", value:"agree"}, {text:"驳回", title:"驳回", value:"reject"}],
          indexCatCode:[],
          isLeaf:[],
          enableStatus:[],
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
        initDictOptions('enable_status').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'enableStatus', res.result)
          }
        })
      },

    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>