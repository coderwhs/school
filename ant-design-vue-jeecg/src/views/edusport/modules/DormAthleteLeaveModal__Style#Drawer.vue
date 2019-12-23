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

        <a-form-item label="运动员" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag v-decorator="['athleteId']" dict="tb_edu_athlete,athlete_name,id" />
        </a-form-item>
        <a-form-item label="请假原因" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'leaveCause', validatorRules.leaveCause]" placeholder="请输入请假原因"></a-input>
        </a-form-item>
        <a-form-item label="开始日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择开始日期" v-decorator="[ 'startDate', validatorRules.startDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="结束日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择结束日期" v-decorator="[ 'endDate', validatorRules.endDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['workflowState']" :trigger-change="true" dictCode="workflow_state" disabled="disabled" placeholder="请选择状态"/>
        </a-form-item>
<!--        <a-form-item label="单据类型" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <j-dict-select-tag type="list" v-decorator="['billType']" :trigger-change="true" dictCode="bill_type" placeholder="请选择单据类型"/>-->
<!--        </a-form-item>-->

      </a-form>
    </a-spin>
    <div class="drawer-bootom-button" v-show="!disableSubmit">
      <a-popconfirm title="确定放弃编辑？" @confirm="handleCancel" okText="确定" cancelText="取消">
        <a-button style="margin-right: .8rem">取消</a-button>
      </a-popconfirm>
      <a-button type="primary" @click="handleOk" :loading="confirmLoading">提交</a-button>
    </div>
  </a-drawer>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JDate from '@/components/jeecg/JDate'
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag'

  export default {
    name: "DormAthleteLeaveModal",
    components: {
      JDate,
      JSearchSelectTag,
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        width:800,
        drawerWidth:800,
        disableSubmit:false,
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

        confirmLoading: false,
        validatorRules:{
          athleteId:{rules: [{ required: true, message: '请输入运动员!' }]},
          leaveCause:{rules: [{ required: true, message: '请输入请假原因!' }]},
          startDate:{rules: [{ required: true, message: '请输入开始日期!' }]},
          endDate:{rules: [{ required: true, message: '请输入结束日期!' }]},
          workflowState:{rules: [{ required: true, message: '请输入状态!' }]},
          billType:{rules: [{ required: true, message: '请输入单据类型!' }]},
        },
        url: {
          add: "/edusport/dormAthleteLeave/add",
          edit: "/edusport/dormAthleteLeave/edit",
        }

      }
    },
    created () {
    },
    methods: {
      //add () {
      //  this.edit({});
      //},
      add(dormId){
        this.hiding = true;
        if (dormId) {
          this.dormId = dormId;
          this.edit({dormId}, '');
        } else {
          this.$message.warning("请选择一个宿舍信息");
        }
      },
      edit (record) {// 如果是编辑，状态
        if(record.id && (record.workflowState == '2' || record.workflowState == '3')){
          this.$message.warning("已经提交工作流，不允许修改!");
        } else {
          this.form.resetFields();
          this.dormId = record.dormId;
          this.model = Object.assign({}, record);
          this.visible = true;
          this.$nextTick(() => {
            this.form.setFieldsValue(pick(this.model,'athleteId','leaveCause','startDate','endDate'))
          })
        }
      },
      close () {
        this.$emit('close');
        this.visible = false;
        this.disableSubmit = false;
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
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
              method = 'put';
            }
            let formData = Object.assign(this.model, values);
            console.log("values = " + JSON.stringify(values));
            console.log("表单提交数据",formData);
            formData.dormId = this.dormId;
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
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
        this.form.setFieldsValue(pick(row,'athleteId','leaveCause','startDate','endDate'))
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