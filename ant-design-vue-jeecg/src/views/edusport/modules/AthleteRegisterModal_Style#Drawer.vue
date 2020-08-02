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

        <a-form-item label="注册人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-if="isNewRequest" v-decorator="[ 'athleteName', validatorRules.athleteName]" placeholder="请填写您的姓名" readonly="readonly"></a-input>
          <a-input v-else v-decorator="[ 'athleteName', validatorRules.athleteName]" placeholder="请填写您的姓名" readonly="readonly"></a-input>
        </a-form-item>

        <a-form-item label="注册日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-form-container :disabled="!isNewRequest">
            <j-date placeholder="请选择注册日期" v-decorator="[ 'registerDate', validatorRules.registerDate]" :trigger-change="true" style="width: 100%" :read-only="true"/>
          </j-form-container>
        </a-form-item>

        <a-divider orientation="left">
          审批意见
        </a-divider>

        <a-form-item label="审批结果" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-form-container>
            <j-search-select-tag v-decorator="['approvalResult', validatorRules.approvalResult]" :dictOptions="dictOptions.approvalResult" />
          </j-form-container>
        </a-form-item>

        <a-form-item label="审批意见" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['approvalComment', validatorRules.approvalComment]" placeholder="请输入审批意见"/>
        </a-form-item>

      </a-form>
    </a-spin>

    <a-card
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
    name: "AthleteRegisterModal",
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
        // isNewRequest: true,
        confirmLoading: false,
        dictCodeSportClass: '',
        dictCodeDorm: '',
        validatorRules:{
          athleteName:{rules: [{ required: true, message: '请填写您的姓名!' }]},
          registerDate:{rules: [{ required: true, message: '请选择注册日期!' }]},
          sportCode:{rules: [{ required: true, message: '请选择专业项目!' }]},
          approvalResult:{rules: [{ required: true, message: '请选择审批结果!' }]},
          approvalComment:{rules: [{ required: true, message: '请输入审批意见!' }]},
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
          add: "/athleteRegister/add",
          update: "/athleteRegister/update",
          queryByProcessId: "/athleteRegister/queryByProcessId",
          processDiagram:"/athleteRegister/processDiagram",
        },
        dictOptions:{
          approvalResult:[{text:"同意", title:"同意", value:"agree"}, {text:"驳回", title:"驳回", value:"reject"}],
        },
      }
    },
    created () {
    },
    computed: {
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
        this.$nextTick(() => {
          // 对应弹出审批栏的数据
          this.form.setFieldsValue(pick(this.model,'athleteName','registerDate'))
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
              httpurl+=this.url.update;
              method = 'put';//和后端@PutMapping(value = "/update")的Put要一致
              // method = 'post';
            }else{
              httpurl+=this.url.edit;
              method = 'put';
            }
            let formData = Object.assign(this.model, values);
            console.log("表单提交数据：",formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
              console.log("res的内容是:",res)
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
        //对应表格数据
        this.form.setFieldsValue(pick(row,'athleteName', 'registerDate'))
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