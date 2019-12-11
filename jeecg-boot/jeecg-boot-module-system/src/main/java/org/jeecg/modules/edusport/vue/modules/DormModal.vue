<template>
  <a-modal
    :title="title"
    :width="1200"
    :visible="visible"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel">
    <a-spin :spinning="confirmLoading">
      <!-- 主表单区域 -->
      <a-form :form="form">
        <a-row>

          <a-col :span="12">
            <a-form-item label="宿舍楼名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'dormBuildingName', validatorRules.dormBuildingName]" placeholder="请输入宿舍楼名称"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="宿舍房间号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'dormNo', validatorRules.dormNo]" placeholder="请输入宿舍房间号"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="床位数" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="[ 'bedNum', validatorRules.bedNum]" placeholder="请输入床位数" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="宿舍电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'dormTel', validatorRules.dormTel]" placeholder="请输入宿舍电话"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="宿舍地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'dormAddress', validatorRules.dormAddress]" placeholder="请输入宿舍地址"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="宿舍管理员" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'dormAdmin', validatorRules.dormAdmin]" placeholder="请输入宿舍管理员"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="管理员电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'dormAdminTel', validatorRules.dormAdminTel]" placeholder="请输入管理员电话"></a-input>
            </a-form-item>
          </a-col>

        </a-row>
      </a-form>

      <!-- 子表单区域 -->
      <a-tabs v-model="activeKey" @change="handleChangeTabs">
        <a-tab-pane tab="宿舍运动员入住表" :key="refKeys[0]" :forceRender="true">
          <j-editable-table
            :ref="refKeys[0]"
            :loading="dormAthleteLivingTable.loading"
            :columns="dormAthleteLivingTable.columns"
            :dataSource="dormAthleteLivingTable.dataSource"
            :maxHeight="300"
            :rowNumber="true"
            :rowSelection="true"
            :actionButton="true"/>
        </a-tab-pane>
        
        <a-tab-pane tab="宿舍运动员请假表" :key="refKeys[1]" :forceRender="true">
          <j-editable-table
            :ref="refKeys[1]"
            :loading="dormAthleteLeaveTable.loading"
            :columns="dormAthleteLeaveTable.columns"
            :dataSource="dormAthleteLeaveTable.dataSource"
            :maxHeight="300"
            :rowNumber="true"
            :rowSelection="true"
            :actionButton="true"/>
        </a-tab-pane>
        
      </a-tabs>

    </a-spin>
  </a-modal>
</template>

<script>

  import pick from 'lodash.pick'
  import { FormTypes,getRefPromise } from '@/utils/JEditableTableUtil'
  import { JEditableTableMixin } from '@/mixins/JEditableTableMixin'

  export default {
    name: 'DormModal',
    mixins: [JEditableTableMixin],
    components: {
    },
    data() {
      return {
        labelCol: {
          span: 6
        },
        wrapperCol: {
          span: 16
        },
        labelCol2: {
          span: 3
        },
        wrapperCol2: {
          span: 20
        },
        // 新增时子表默认添加几行空数据
        addDefaultRowNum: 1,
        validatorRules: {
          dormBuildingName: { rules: [{ required: true, message: '请输入宿舍楼名称!' }] },
          dormNo: { rules: [{ required: true, message: '请输入宿舍房间号!' }] },
          bedNum: { rules: [{ required: true, message: '请输入床位数!' }] },
          dormTel: { rules: [{ required: true, message: '请输入宿舍电话!' }] },
          dormAddress: { rules: [{ required: true, message: '请输入宿舍地址!' }] },
          dormAdmin: { rules: [{ required: true, message: '请输入宿舍管理员!' }] },
          dormAdminTel: { rules: [{ required: true, message: '请输入管理员电话!' }] },
        },
        refKeys: ['dormAthleteLiving', 'dormAthleteLeave', ],
        tableKeys:['dormAthleteLiving', 'dormAthleteLeave', ],
        activeKey: 'dormAthleteLiving',
        // 宿舍运动员入住表
        dormAthleteLivingTable: {
          loading: false,
          dataSource: [],
          columns: [
            {
              title: '学号',
              key: 'athleteNo',
              type: FormTypes.sel_search,
              dictCode:"tb_edu_athlete,athlete_name,athlete_no",
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
              validateRules: [{ required: true, message: '${title}不能为空' }],
            },
            {
              title: '床位号',
              key: 'bedNo',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
              validateRules: [{ required: true, message: '${title}不能为空' }],
            },
            {
              title: '入住开始日期',
              key: 'startDate',
              type: FormTypes.date,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '入住结束日期',
              key: 'endDate',
              type: FormTypes.date,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
          ]
        },
        // 宿舍运动员请假表
        dormAthleteLeaveTable: {
          loading: false,
          dataSource: [],
          columns: [
            {
              title: '学号',
              key: 'athleteNo',
              type: FormTypes.select,
              dictCode:"tb_edu_athlete,athlete_name,athlete_no",
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
              validateRules: [{ required: true, message: '${title}不能为空' }],
            },
            {
              title: '请假原因',
              key: 'leaveCause',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
              validateRules: [{ required: true, message: '${title}不能为空' }],
            },
            {
              title: '开始日期',
              key: 'startDate',
              type: FormTypes.date,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '结束日期',
              key: 'endDate',
              type: FormTypes.date,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
          ]
        },
        url: {
          add: "/edusport/dorm/add",
          edit: "/edusport/dorm/edit",
          dormAthleteLiving: {
            list: '/edusport/dorm/queryDormAthleteLivingByMainId'
          },
          dormAthleteLeave: {
            list: '/edusport/dorm/queryDormAthleteLeaveByMainId'
          },
        }
      }
    },
    methods: {
      getAllTable() {
        let values = this.tableKeys.map(key => getRefPromise(this, key))
        return Promise.all(values)
      },
      /** 调用完edit()方法之后会自动调用此方法 */
      editAfter() {
        let fieldval = pick(this.model,'dormBuildingName','dormNo','bedNum','dormTel','dormAddress','dormAdmin','dormAdminTel')
        this.$nextTick(() => {
          this.form.setFieldsValue(fieldval)
        })
        // 加载子表数据
        if (this.model.id) {
          let params = { id: this.model.id }
          this.requestSubTableData(this.url.dormAthleteLiving.list, params, this.dormAthleteLivingTable)
          this.requestSubTableData(this.url.dormAthleteLeave.list, params, this.dormAthleteLeaveTable)
        }
      },
      /** 整理成formData */
      classifyIntoFormData(allValues) {
        let main = Object.assign(this.model, allValues.formValue)

        return {
          ...main, // 展开
          dormAthleteLivingList: allValues.tablesValue[0].values,
          dormAthleteLeaveList: allValues.tablesValue[1].values,
        }
      },
      validateError(msg){
        this.$message.error(msg)
      },
     popupCallback(row){
       this.form.setFieldsValue(pick(row,'dormBuildingName','dormNo','bedNum','dormTel','dormAddress','dormAdmin','dormAdminTel'))
     },

    }
  }
</script>

<style scoped>
</style>