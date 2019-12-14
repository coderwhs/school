<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="宿舍楼名称">
              <a-input placeholder="请输入宿舍楼名称" v-model="queryParam.dormBuildingName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="房间号">
              <a-input placeholder="请输入房间号" v-model="queryParam.dormNo"></a-input>
            </a-form-item>
          </a-col>
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
<!--      <a-button type="primary" icon="download" @click="handleExportXls('宿舍信息表')">导出</a-button>-->
<!--      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">-->
<!--        <a-button type="primary" icon="import">导入</a-button>-->
<!--      </a-upload>-->
      <!--<a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>-->
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
        filterMultiple="filterMultiple"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange,type:type}"
        @change="handleTableChange"
        :customRow="clickThenCheck"
      >/* Tab修改@2019-12-12 */

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
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <a-tabs defaultActiveKey="1">
      <a-tab-pane tab="运动员入住名单" key="1">
        <Dorm-Athlete-Living-List ref="DormAthleteLivingList"></Dorm-Athlete-Living-List>
      </a-tab-pane>
      <a-tab-pane tab="运动员请假" key="2" forceRender>
        <Dorm-Athlete-LeaveList ref="DormAthleteLeaveList"></Dorm-Athlete-LeaveList>
      </a-tab-pane>
    </a-tabs>

    <dorm-modal ref="modalForm" @ok="modalFormOk"></dorm-modal>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  // import DormModal from './modules/DormModal'
  import DormModal from './modules/DormModal__Style#Drawer'
  import DormAthleteLivingList from './DormAthleteLivingList'
  import DormAthleteLeaveList from './DormAthleteLeaveList'
  import DormAthleteLeaveModal from './modules/DormAthleteLeaveModal'
  import DormAthleteLivingModal from './modules/DormAthleteLivingModal'/* Tab修改@2019-12-12 */
  import {deleteAction} from '@/api/manage'

  export default {
    name: "DormList",
    mixins:[JeecgListMixin],
    components: {
      DormModal,
      DormAthleteLivingList,
      DormAthleteLeaveList,
      DormAthleteLeaveModal,
      DormAthleteLivingModal/* Tab修改@2019-12-12 */
    },
    data () {
      return {
        description: '宿舍信息表管理页面',
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
            title:'宿舍楼名称',
            align:"center",
            dataIndex: 'dormBuildingName'
          },
          {
            title:'房间号',
            align:"center",
            dataIndex: 'dormNo'
          },
          {
            title:'床位数',
            align:"center",
            dataIndex: 'bedNum'
          },
          {
            title:'宿舍电话',
            align:"center",
            dataIndex: 'dormTel'
          },
          {
            title:'管理员',
            align:"center",
            dataIndex: 'dormAdmin'
          },
          {
            title:'管理员电话',
            align:"center",
            dataIndex: 'dormAdminTel'
          },
          {
            title:'地址',
            align:"center",
            dataIndex: 'dormAddress'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            width:147,
            scopedSlots: { customRender: 'action' }
          }
        ],
        // 分页参数
        type: "radio",/* Tab修改@2019-12-12 */
        url: {
          list: "/edusport/dorm/list",
          delete: "/edusport/dorm/delete",
          deleteBatch: "/edusport/dorm/deleteBatch",
          exportXlsUrl: "/edusport/dorm/exportXls",
          importExcelUrl: "edusport/dorm/importExcel",
        },
        dictOptions:{
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
        this.$refs.DormAthleteLivingList.getDorm(this.selectedRowKeys[0]);
        this.$refs.DormAthleteLeaveList.getDorm(this.selectedRowKeys[0]);/* Tab修改@2019-12-12 */
      },
      onClearSelected() {/* Tab修改@2019-12-12 */
        this.selectedRowKeys = [];
        this.selectionRows = [];
        this.$refs.DormAthleteLivingList.queryParam.dormId = null;
        this.$refs.DormAthleteLeaveList.queryParam.dormId = null;
        this.$refs.DormAthleteLivingList.loadData();
        this.$refs.DormAthleteLeaveList.loadData();
        this.$refs.DormAthleteLivingList.selectedRowKeys = [];
        this.$refs.DormAthleteLivingList.selectionRows = [];
        this.$refs.DormAthleteLeaveList.selectedRowKeys = [];
        this.$refs.DormAthleteLeaveList.selectionRows = [];
      },

      handleDelete: function (id) {/* Tab修改@2019-12-12 */
        var that = this;
        deleteAction(that.url.delete, {id: id}).then((res) => {
          if (res.success) {
            that.$message.success(res.message);
            that.loadData();
            this.$refs.DormAthleteLivingList.loadData();
            this.$refs.DormAthleteLeaveList.loadData();
          } else {
            that.$message.warning(res.message);
          }
        });
      },
      searchQuery:function(){/* Tab修改@2019-12-12 */
        this.selectedRowKeys = [];
        this.selectionRows = [];
        this.$refs.DormAthleteLivingList.queryParam.mainId = null;
        this.$refs.DormAthleteLeaveList.queryParam.mainId = null;
        this.$refs.DormAthleteLivingList.loadData();
        this.$refs.DormAthleteLeaveList.loadData();
        this.$refs.DormAthleteLivingList.selectedRowKeys = [];
        this.$refs.DormAthleteLivingList.selectionRows = [];
        this.$refs.DormAthleteLeaveList.selectedRowKeys = [];
        this.$refs.DormAthleteLeaveList.selectionRows = [];
        this.loadData();
      }

    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>