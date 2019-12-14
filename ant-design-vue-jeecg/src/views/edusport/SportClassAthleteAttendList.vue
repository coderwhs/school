<template>
  <a-card :bordered="false">

    <!-- 操作按钮区域 -->
    <div class="table-operator" :md="24" :sm="24" style="margin: -25px 0px 10px 0px">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
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
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        
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

    <sportClassAthleteAttend-modal ref="modalForm" @ok="modalFormOk"></sportClassAthleteAttend-modal>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import SportClassAthleteAttendModal from './modules/SportClassAthleteAttendModal'
  import {initDictOptions, filterMultiDictText} from '@/components/dict/JDictSelectUtil'

  export default {
    name: "SportClassAthleteAttendList",
    mixins:[JeecgListMixin],
    components: {
      SportClassAthleteAttendModal
    },
    data () {
      return {
        description: '运动员训练出勤表管理页面',
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
            title:'训练班',
            align:"center",
            dataIndex: 'sportClassId',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['sportClassId'], text+"")
              }
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
            title:'考勤状态',
            align:"center",
            dataIndex: 'attendStatus',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['attendStatus'], text+"")
              }
            }
          },
          {
            title:'考勤日期',
            align:"center",
            dataIndex: 'attendTime',
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
          list: "/edusport/sportClassAthleteAttend/list",
          delete: "/edusport/sportClassAthleteAttend/delete",
          deleteBatch: "/edusport/sportClassAthleteAttend/deleteBatch",
          exportXlsUrl: "/edusport/sportClassAthleteAttend/exportXls",
          importExcelUrl: "edusport/sportClassAthleteAttend/importExcel",
        },
        dictOptions:{
         attendStatus:[],
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
        initDictOptions('tb_edu_sport_class,class_name,id').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'sportClassId', res.result)
          }
        })
        initDictOptions('tb_edu_athlete,athlete_name,id').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'athleteId', res.result)
          }
        })
        initDictOptions('attend_status').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'attendStatus', res.result)
          }
        })
      },
      getListBySportClassId(sportClassId) {
        this.queryParam.sportClassId = sportClassId;
        this.loadData(1);
      },
      handleAdd: function () {
        this.$refs.modalForm.add(this.queryParam.sportClassId);
        this.$refs.modalForm.title = "添加训练考勤";
        this.$refs.modalForm.disableSubmit = false;
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