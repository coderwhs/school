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
        <a-form-item label="运动员" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag v-decorator="['athleteId']" dict="tb_edu_athlete,athlete_name,id" />
        </a-form-item>
        <a-form-item label="小项" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'eventCode', validatorRules.eventCode]" placeholder="请输入小项"></a-input>
        </a-form-item>
        <a-form-item label="成绩目标" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'performanceGoal', validatorRules.performanceGoal]" placeholder="请输入成绩目标"></a-input>
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag'

  export default {
    name: "SportClassAthleteYearGoalModal",
    components: { 
      JSearchSelectTag,
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
        athleteId:{rules: [{ required: true, message: '请输入运动员!' }]},
        eventCode:{rules: [{ required: true, message: '请输入小项!' }]},
        performanceGoal:{rules: [{ required: true, message: '请输入成绩目标!' }]},
        },
        url: {
          add: "/edusport/sportClassAthleteYearGoal/add",
          edit: "/edusport/sportClassAthleteYearGoal/edit",
        }
     
      }
    },
    created () {
    },
    methods: {
      add () {
        this.edit({});
      },
      add(yearPlanId) {
        this.hiding = true;
        if (yearPlanId) {
          this.yearPlanId = yearPlanId;
          this.edit({yearPlanId}, '');
        } else {
          this.$message.warning("请选择一个年度训练计划信息");
        }
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'yearPlanId','athleteId','eventCode','performanceGoal'))
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
        this.form.setFieldsValue(pick(row,'yearPlanId','athleteId','eventCode','performanceGoal'))
      },

      
    }
  }
</script>