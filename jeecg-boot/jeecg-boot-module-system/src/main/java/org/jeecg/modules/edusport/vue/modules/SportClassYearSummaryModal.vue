<template>
  <a-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item label="年度计划" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag v-decorator="['yearPlanId']" dict="tb_edu_sport_class_year_plan,plan_name,id" />
        </a-form-item>
        <a-form-item label="执行情况" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-editor v-decorator="['yearPerformance',{trigger:'input'}]"/>
        </a-form-item>
        <a-form-item label="全年任务" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-editor v-decorator="['yearWeakness',{trigger:'input'}]"/>
        </a-form-item>
        <a-form-item label="改进方向" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-editor v-decorator="['improvement',{trigger:'input'}]"/>
        </a-form-item>
        <a-form-item label="竞赛科评价" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['deptEvaluation']" rows="4" placeholder="请输入竞赛科评价"/>
        </a-form-item>
        <a-form-item label="竞赛科评价日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择竞赛科评价日期" v-decorator="[ 'deptEvaluationDate', validatorRules.deptEvaluationDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="校领导评价" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['schoolEvaluation']" rows="4" placeholder="请输入校领导评价"/>
        </a-form-item>
        <a-form-item label="校领导评价日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择校领导评价日期" v-decorator="[ 'schoolEvaluationDate', validatorRules.schoolEvaluationDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JDate from '@/components/jeecg/JDate'  
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag'
  import JEditor from '@/components/jeecg/JEditor'

  export default {
    name: "SportClassYearSummaryModal",
    components: { 
      JDate,
      JSearchSelectTag,
      JEditor,
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
        yearPlanId:{rules: [{ required: true, message: '请输入年度计划!' }]},
        yearPerformance:{},
        yearWeakness:{},
        improvement:{},
        deptEvaluation:{rules: [{ required: true, message: '请输入竞赛科评价!' }]},
        deptEvaluationDate:{},
        schoolEvaluation:{rules: [{ required: true, message: '请输入校领导评价!' }]},
        schoolEvaluationDate:{},
        },
        url: {
          add: "/edusport/sportClassYearSummary/add",
          edit: "/edusport/sportClassYearSummary/edit",
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
          this.form.setFieldsValue(pick(this.model,'yearPlanId','yearPerformance','yearWeakness','improvement','deptEvaluation','deptEvaluationDate','schoolEvaluation','schoolEvaluationDate'))
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
        this.form.setFieldsValue(pick(row,'yearPlanId','yearPerformance','yearWeakness','improvement','deptEvaluation','deptEvaluationDate','schoolEvaluation','schoolEvaluationDate'))
      },

      
    }
  }
</script>