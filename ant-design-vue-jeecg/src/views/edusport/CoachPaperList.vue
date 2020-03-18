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
    <div class="table-operator" :md="24" :sm="24" style="margin: -25px 0px 10px 0px">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button @click="handleSearchByPublishDateBegin(1)" type="primary" icon="search">奥运周期</a-button>
      <a-button @click="handleSearchByPublishDateBegin(0)" type="primary" icon="search">全部</a-button>
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
        <span style="margin-left: 48px">默认显示上个奥运年以来的论文发表</span>
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

    <coachPaper-modal ref="modalForm" @ok="modalFormOk"></coachPaper-modal>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  // import CoachPaperModal from './modules/CoachPaperModal'
  import CoachPaperModal from './modules/CoachPaperModal__Style#Drawer'
  import {initDictOptions, filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  import moment from 'moment'

  export default {
    name: "CoachPaperList",
    mixins:[JeecgListMixin],
    components: {
      CoachPaperModal
    },
    data () {
      return {
        description: '教练员论文发表信息表管理页面',
        /* 查询条件 */
        queryParam: {
          publishDate_begin: Number(moment().year()) - Number(((moment().year()-1896)%4) == 0 ? 4: (moment().year()-1896)%4) + "-06-01",
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
            title:'论文名称',
            align:"center",
            dataIndex: 'paperTitle'
          },
          {
            title:'刊物名称',
            align:"center",
            dataIndex: 'publicationName'
          },
          {
            title:'刊物等级',
            align:"center",
            dataIndex: 'publicationLevel',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['publicationLevel'], text+"")
              }
            }
          },
          {
            title:'发表日期',
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
          list: "/edusport/coachPaper/list",
          delete: "/edusport/coachPaper/delete",
          deleteBatch: "/edusport/coachPaper/deleteBatch",
          exportXlsUrl: "/edusport/coachPaper/exportXls",
          importExcelUrl: "edusport/coachPaper/importExcel",
        },
        dictOptions:{
         publicationLevel:[],
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
        initDictOptions('publication_level').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'publicationLevel', res.result)
          }
        })
      },
      getListByCoachId(coachId) {
        this.queryParam.coachId = coachId;
        this.loadData(1);
      },
      handleAdd: function () {
        this.$refs.modalForm.add(this.queryParam.coachId);
        this.$refs.modalForm.title = "添加论文发表";
        this.$refs.modalForm.disableSubmit = false;
      },
      handleSearchByPublishDateBegin: function (useLastOlympicYear) {
        // 近8年培训经历，近100年培训经历表示查询全部
        if (useLastOlympicYear) {
          this.queryParam.publishDate_begin = Number(moment().year()) - Number(((moment().year()-1896)%4) == 0 ? 4: (moment().year()-1896)%4) + "-06-01";
        } else {
          this.queryParam.publishDate_begin = moment().subtract(100, 'years').format('YYYY-MM-DD');
        }
        this.loadData(1);
      },
    }
  }
</script>
<style scoped>
  .ant-card {
    margin-left: -30px;
    margin-right: -30px;
  }
</style>