<template>
  <a-drawer
    :title="title"
    :width="drawerWidth"
    :maskClosable="true"
    placement="right"
    :closable="true"
    @close="close"
    :visible="visible"
    style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;">


    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item label="申请人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-if="isNewRequest" v-decorator="[ 'coachName', validatorRules.coachName]" placeholder="请填写您的姓名"></a-input>
          <a-input v-else v-decorator="[ 'coachName', validatorRules.coachName]" placeholder="请填写您的姓名" readonly="readonly"></a-input>
        </a-form-item>

        <a-form-item label="请假类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-form-container :disabled="!isNewRequest">
            <j-search-select-tag placeholder="请选择请假类型"  v-decorator="['requestType', validatorRules.requestType]" :dictOptions="dictOptions.requestType" />
          </j-form-container>
        </a-form-item>

        <a-form-item label="开始日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-form-container :disabled="!isNewRequest">
            <j-date placeholder="请选择开始日期" v-decorator="[ 'startDate', validatorRules.startDate]" :trigger-change="true" style="width: 100%"/>
          </j-form-container>
        </a-form-item>

        <a-form-item label="结束日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-form-container :disabled="!isNewRequest">
            <j-date placeholder="请选择结束日期" v-decorator="[ 'endDate', validatorRules.endDate]" :trigger-change="true" style="width: 100%"/>
          </j-form-container>
        </a-form-item>
        <a-form-item label="请假事由" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-if="isNewRequest" v-decorator="[ 'reason', validatorRules.reason]" placeholder="请输入请假事由"></a-textarea>
          <a-textarea v-else v-decorator="[ 'reason', validatorRules.reason]" placeholder="请输入请假事由" readonly="readonly"></a-textarea>
        </a-form-item>

        <a-divider v-if="!isNewRequest" orientation="left">
          审批意见
        </a-divider>
        <a-form-item v-if="!isNewRequest" label="审批结果" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-form-container>
            <j-search-select-tag v-decorator="['approvalResult', validatorRules.approvalResult]" :dictOptions="dictOptions.approvalResult" />
          </j-form-container>
        </a-form-item>
        <a-form-item v-if="!isNewRequest" label="审批意见" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['approvalComment', validatorRules.approvalComment]" placeholder="请输入审批意见"/>
        </a-form-item>

      </a-form>
    </a-spin>

    <a-card
      v-if="!isNewRequest"
      style="width:100%"
      :tab-list="tabListNoTitle"
      :active-tab-key="noTitleKey"
      @tabChange="key => onTabChange(key, 'noTitleKey')">
      <p v-if="noTitleKey === 'workflowLog'">

        <a-steps progress-dot direction="vertical" size="small" :current="workflowLogList.length" >
          <a-step v-for="(item, index) in workflowLogList" :key="index">
            <span slot="title">{{ item.workflowNode }}</span>
            <span slot="description" style="display: block">审批结果：{{ item.approvalResult }}</span>
            <span slot="description" style="display: block">审批意见：{{ item.approvalComment }}</span>
            <span slot="description" style="display: block">({{ item.approver }} : {{ item.approvalDate }})</span>
          </a-step>
          <a-step status="wait" :title="currentWorkflow" description="待审批"/>
        </a-steps>

      </p>
      <p v-else-if="noTitleKey === 'processDiagram'">
        <img alt="请假流程图" style="width: 50%; height: 50%" :src="previewImage"/>
      </p>
    </a-card>



    <div class="drawer-bootom-button" v-show="!disableSubmit">
      <a-popconfirm title="确定放弃编辑？" @confirm="handleCancel" okText="确定" cancelText="取消">
        <a-button style="margin-right: .8rem">取消</a-button>
      </a-popconfirm>
      <a-button type="primary" @click="handleOk" :loading="confirmLoading">提交</a-button>
    </div>
  </a-drawer>
</template>

<script>

  import { httpAction, getAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JDate from '@/components/jeecg/JDate'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag'
  import JTreeSelect from '@/components/jeecg/JTreeSelect'

  export default {
    name: "CoachLeaveRequestModal",
    components: {
      JDate,
      JDictSelectTag,
      JSearchSelectTag,
      JTreeSelect
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        width:800,
        visible: false,
        drawerWidth:800,
        disableSubmit:false,
        model: {},

        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },

        isNewRequest: false,
        confirmLoading: false,
        dictCodeSportClass: '',
        dictCodeDorm: '',
        validatorRules:{
          coachName:{rules: [{ required: true, message: '请填写您的姓名!' }]},
          requestType:{rules: [{ required: true, message: '请选择请假类型!' }]},
          startDate:{rules: [{ required: true, message: '请选择开始日期!' }]},
          endDate:{rules: [{ required: true, message: '请选择结束日期!' }]},
          reason:{rules: [{ required: true, message: '请输入请假事由!' }]},
          // approvalResult:{rules: [{ required: true, message: '请选择审批结果!' }]},
          // approvalComment:{rules: [{ required: true, message: '请输入审批意见!' }]},
        },
        tabListNoTitle: [
          {
            key: 'workflowLog',
            tab: '审批日志',
          },
          {
            key: 'processDiagram',
            tab: '流程图',
          },
        ],
        noTitleKey: 'workflowLog',
        workflowLogList: '',
        currentWorkflow: '',
        previewImage: '',
        url: {
          add: "/edusport/coachLeaveRequest/add",
          update: "/edusport/coachLeaveRequest/update",
          queryByProcessId: "/edusport/coachLeaveRequest/queryByProcessId",
          processDiagram:"/edusport/coachLeaveRequest/processDiagram",
        },
        dictOptions:{
          requestType:[{text:"事假", title:"事假", value:"thingsLeaveRequest"}, {text:"病假", title:"病假", value:"sickLeaveRequest"},
            {text:"培训", title:"培训", value:"trainLeaveRequest"}, {text:"赛事", title:"赛事", value:"matchLeaveRequest"}],
          approvalResult:[{text:"同意", title:"同意", value:"agree"}, {text:"驳回", title:"驳回", value:"reject"}],
        },

      }
    },
    created () {
    },
    computed: {
      // fnDictCodeSportClass: function() {
      //   return this.dictCodeSportClass;
      // },
      // fnDictCodeDorm: function() {
      //   return this.dictCodeDorm;
      // }
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.isNewRequest = false;
        console.log("record: ", record);
        this.form.resetFields();
        this.model = Object.assign({}, record);
        if (!this.model.requestId) {this.isNewRequest = true};
        console.log("this.requestId: ", this.model.requestId);
        console.log("this.isNewRequest: ", this.isNewRequest);
        this.visible = true;
        //对应弹出审批栏的数据
        this.$nextTick(() => {
          // this.form.setFieldsValue(pick(this.model,'requestType',  'startDate','endDate','reason'))
          this.form.setFieldsValue(pick(this.model,'coachName','requestType',  'startDate','endDate','reason'))
        })


        // 获取审批日志 & 初始化流程图url
        if (record.processId) {
          // 获取审批日志
          var params = {
            processId: record.processId,
          };//查询条件
          getAction(this.url.queryByProcessId, params).then((res) => {
            if (res.success) {
              this.workflowLogList = res.result.approvalList;
              this.currentWorkflow = res.result.workflowNode;
              console.log("this.workflowLogList: ", this.workflowLogList);
              console.log("this.workflowLogList.length: ", this.workflowLogList.length);
            }
            if (res.code === 510) {
              this.$message.warning(res.message)
            }
          })

          // 初始化流程图url
          this.previewImage = window._CONFIG['domianURL'] + this.url.processDiagram + "?processId=" + record.processId;
        }

      },
      close () {
        this.$emit('close');
        this.visible = false;
      },

      // 根据屏幕变化,设置抽屉尺寸
      resetScreenSize(){
        let screenWidth = document.body.clientWidth;
        if(screenWidth < 500){
          this.drawerWidth = screenWidth;
        }else{
          this.drawerWidth = 700;
        }
      },
      onTabChange(key, type) {
        console.log(key, type);
        this[type] = key;
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.requestId){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
              method = 'put';
            }
            let formData = Object.assign(this.model, values);
            console.log("表单提交数据",formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
              console.log("res:",res)
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })
          }

        })
      },
      handleCancel () {
        this.close()
      },
      popupCallback(row){
        //对应表格的数据
        this.form.setFieldsValue(pick(row,'requestType', 'dormId', 'sportClassId', 'startDate','endDate','reason'))
      }

    }
  }
</script>

<style lang="less" scoped>
  .drawer-bootom-button {
    position: absolute;
    bottom: -8px;
    width: 100%;
    border-top: 1px solid #e8e8e8;
    padding: 10px 16px;
    text-align: right;
    left: 0;
    background: #fff;
    border-radius: 0 0 2px 2px;
  }
</style>