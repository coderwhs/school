<template>
  <a-drawer
    :title="title"
    :width="width"
    placement="right"
    :closable="false"
    @close="close"
    :visible="visible">
  
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item label="训练队" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'sportClassId', validatorRules.sportClassId]" placeholder="请输入训练队"></a-input>
        </a-form-item>
        <a-form-item label="训练计划名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'planName', validatorRules.planName]" placeholder="请输入训练计划名称"></a-input>
        </a-form-item>
        <a-form-item label="课训日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择课训日期" v-decorator="[ 'courseDate', validatorRules.courseDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="目的任务" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['taskGoal']" rows="4" placeholder="请输入目的任务"/>
        </a-form-item>
        <a-form-item label="准备训练量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'prepareWorkload', validatorRules.prepareWorkload]" placeholder="请输入准备训练量"></a-input>
        </a-form-item>
        <a-form-item label="准备训练强度" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'prepareIntensity', validatorRules.prepareIntensity]" placeholder="请输入准备训练强度"></a-input>
        </a-form-item>
        <a-form-item label="准备训练时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'prepareTime', validatorRules.prepareTime]" placeholder="请输入准备训练时间"></a-input>
        </a-form-item>
        <a-form-item label="准备训练内容" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['prepareContent']" rows="4" placeholder="请输入准备训练内容"/>
        </a-form-item>
        <a-form-item label="基本训练量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'basicWorkload', validatorRules.basicWorkload]" placeholder="请输入基本训练量"></a-input>
        </a-form-item>
        <a-form-item label="基本训练强度" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'basicIntensity', validatorRules.basicIntensity]" placeholder="请输入基本训练强度"></a-input>
        </a-form-item>
        <a-form-item label="基本训练时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'basicTime', validatorRules.basicTime]" placeholder="请输入基本训练时间"></a-input>
        </a-form-item>
        <a-form-item label="基本训练内容" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['basicContent']" rows="4" placeholder="请输入基本训练内容"/>
        </a-form-item>
        <a-form-item label="结束训练量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'finishWorkload', validatorRules.finishWorkload]" placeholder="请输入结束训练量"></a-input>
        </a-form-item>
        <a-form-item label="结束训练强度" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'finishIntensity', validatorRules.finishIntensity]" placeholder="请输入结束训练强度"></a-input>
        </a-form-item>
        <a-form-item label="结束训练时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'finishTime', validatorRules.finishTime]" placeholder="请输入结束训练时间"></a-input>
        </a-form-item>
        <a-form-item label="结束训练内容" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['finishContent']" rows="4" placeholder="请输入结束训练内容"/>
        </a-form-item>
        <a-form-item label="课后小结" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['courseSummary']" rows="4" placeholder="请输入课后小结"/>
        </a-form-item>
        
      </a-form>
    </a-spin>
    <a-button type="primary" @click="handleOk">确定</a-button>
    <a-button type="primary" @click="handleCancel">取消</a-button>
  </a-drawer>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JDate from '@/components/jeecg/JDate'  
  
  export default {
    name: "SportClassCoursePlanModal",
    components: { 
      JDate,
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        width:800,
        visible: false,
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
        sportClassId:{rules: [{ required: true, message: '请输入训练队!' }]},
        planName:{rules: [{ required: true, message: '请输入训练计划名称!' }]},
        courseDate:{rules: [{ required: true, message: '请输入课训日期!' }]},
        taskGoal:{rules: [{ required: true, message: '请输入目的任务!' }]},
        prepareWorkload:{},
        prepareIntensity:{},
        prepareTime:{},
        prepareContent:{},
        basicWorkload:{},
        basicIntensity:{},
        basicTime:{},
        basicContent:{},
        finishWorkload:{},
        finishIntensity:{},
        finishTime:{},
        finishContent:{},
        courseSummary:{},
        },
        url: {
          add: "/edusport/sportClassCoursePlan/add",
          edit: "/edusport/sportClassCoursePlan/edit",
        }
     
      }
    },
    created () {
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'sportClassId','planName','courseDate','taskGoal','prepareWorkload','prepareIntensity','prepareTime','prepareContent','basicWorkload','basicIntensity','basicTime','basicContent','finishWorkload','finishIntensity','finishTime','finishContent','courseSummary'))
        })
      },
      close () {
        this.$emit('close');
        this.visible = false;
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
            console.log("表单提交数据",formData)
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
        this.form.setFieldsValue(pick(row,'sportClassId','planName','courseDate','taskGoal','prepareWorkload','prepareIntensity','prepareTime','prepareContent','basicWorkload','basicIntensity','basicTime','basicContent','finishWorkload','finishIntensity','finishTime','finishContent','courseSummary'))
      }
      
    }
  }
</script>

<style lang="less" scoped>
/** Button按钮间距 */
  .ant-btn {
    margin-left: 30px;
    margin-bottom: 30px;
    float: right;
  }
</style>