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

        <a-form-item label="训练班" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'sportClassId', validatorRules.sportClassId]" placeholder="请输入训练班"></a-input>
        </a-form-item>
        <a-form-item label="训练计划名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'taskName', validatorRules.taskName]" placeholder="请输入训练计划名称"></a-input>
        </a-form-item>
        <a-form-item label="发布人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'coachNo', validatorRules.coachNo]" placeholder="请输入发布人"></a-input>
        </a-form-item>
        <a-form-item label="准备期开始日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择准备期开始日期" v-decorator="[ 'prepareStartDate', validatorRules.prepareStartDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="准备期结束日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择准备期结束日期" v-decorator="[ 'prepareEndDate', validatorRules.prepareEndDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="准备期训练任务" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['prepareGoal']" rows="4" placeholder="请输入准备期训练任务"/>
        </a-form-item>
        <a-form-item label="准备期素质训练" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'prepareQualityPercent', validatorRules.prepareQualityPercent]" placeholder="请输入准备期素质训练"></a-input>
        </a-form-item>
        <a-form-item label="准备期技术训练" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'prepareTechPercent', validatorRules.prepareTechPercent]" placeholder="请输入准备期技术训练"></a-input>
        </a-form-item>
        <a-form-item label="准备期战术训练" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'prepareTacticalPercent', validatorRules.prepareTacticalPercent]" placeholder="请输入准备期战术训练"></a-input>
        </a-form-item>
        <a-form-item label="准备期心理训练" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'prepareMentalPercent', validatorRules.prepareMentalPercent]" placeholder="请输入准备期心理训练"></a-input>
        </a-form-item>
        <a-form-item label="准备期训练手段" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['prepareMethod']" rows="4" placeholder="请输入准备期训练手段"/>
        </a-form-item>
        <a-form-item label="准备期训练量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'prepareWorkload', validatorRules.prepareWorkload]" placeholder="请输入准备期训练量"></a-input>
        </a-form-item>
        <a-form-item label="准备期训练强度" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'prepareIntensity', validatorRules.prepareIntensity]" placeholder="请输入准备期训练强度"></a-input>
        </a-form-item>
        <a-form-item label="基本期开始日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择基本期开始日期" v-decorator="[ 'basicStartDate', validatorRules.basicStartDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="基本期结束日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择基本期结束日期" v-decorator="[ 'basicEndDate', validatorRules.basicEndDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="基本期训练任务" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['basicGoal']" rows="4" placeholder="请输入基本期训练任务"/>
        </a-form-item>
        <a-form-item label="基本期素质训练" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'basicQualityPercent', validatorRules.basicQualityPercent]" placeholder="请输入基本期素质训练"></a-input>
        </a-form-item>
        <a-form-item label="基本期技术训练" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'basicTechPercent', validatorRules.basicTechPercent]" placeholder="请输入基本期技术训练"></a-input>
        </a-form-item>
        <a-form-item label="基本期战术训练" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'basicTacticalPercent', validatorRules.basicTacticalPercent]" placeholder="请输入基本期战术训练"></a-input>
        </a-form-item>
        <a-form-item label="基本期心理训练" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'basicMentalPercent', validatorRules.basicMentalPercent]" placeholder="请输入基本期心理训练"></a-input>
        </a-form-item>
        <a-form-item label="基本期训练手段" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['basicMethod']" rows="4" placeholder="请输入基本期训练手段"/>
        </a-form-item>
        <a-form-item label="基本期训练量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'basicWorkload', validatorRules.basicWorkload]" placeholder="请输入基本期训练量"></a-input>
        </a-form-item>
        <a-form-item label="基本期训练强度" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'basicIntensity', validatorRules.basicIntensity]" placeholder="请输入基本期训练强度"></a-input>
        </a-form-item>
        <a-form-item label="结束期开始日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择结束期开始日期" v-decorator="[ 'finishStartDate', validatorRules.finishStartDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="结束期结束日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择结束期结束日期" v-decorator="[ 'finishEndDate', validatorRules.finishEndDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="结束期训练任务" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['finishGoal']" rows="4" placeholder="请输入结束期训练任务"/>
        </a-form-item>
        <a-form-item label="结束期素质训练" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'finishQualityPercent', validatorRules.finishQualityPercent]" placeholder="请输入结束期素质训练"></a-input>
        </a-form-item>
        <a-form-item label="结束期技术训练" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'finishTechPercent', validatorRules.finishTechPercent]" placeholder="请输入结束期技术训练"></a-input>
        </a-form-item>
        <a-form-item label="结束期战术训练" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'finishTacticalPercent', validatorRules.finishTacticalPercent]" placeholder="请输入结束期战术训练"></a-input>
        </a-form-item>
        <a-form-item label="结束期心理训练" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'finishMentalPercent', validatorRules.finishMentalPercent]" placeholder="请输入结束期心理训练"></a-input>
        </a-form-item>
        <a-form-item label="结束期训练手段" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['finishMethod']" rows="4" placeholder="请输入结束期训练手段"/>
        </a-form-item>
        <a-form-item label="结束期训练量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'finishWorkload', validatorRules.finishWorkload]" placeholder="请输入结束期训练量"></a-input>
        </a-form-item>
        <a-form-item label="结束期训练强度" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'finishIntensity', validatorRules.finishIntensity]" placeholder="请输入结束期训练强度"></a-input>
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
    <a-button type="primary" @click="handleOk">确定</a-button>
    <a-button type="primary" @click="handleCancel">取消</a-button>
  </a-drawer>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JDate from '@/components/jeecg/JDate'  
  
  export default {
    name: "SportClassWinterPlanModal",
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
        sportClassId:{rules: [{ required: true, message: '请输入训练班!' }]},
        taskName:{rules: [{ required: true, message: '请输入训练计划名称!' }]},
        coachNo:{rules: [{ required: true, message: '请输入发布人!' }]},
        prepareStartDate:{},
        prepareEndDate:{},
        prepareGoal:{rules: [{ required: true, message: '请输入准备期训练任务!' }]},
        prepareQualityPercent:{rules: [{ required: true, message: '请输入准备期素质训练!' }]},
        prepareTechPercent:{rules: [{ required: true, message: '请输入准备期技术训练!' }]},
        prepareTacticalPercent:{rules: [{ required: true, message: '请输入准备期战术训练!' }]},
        prepareMentalPercent:{rules: [{ required: true, message: '请输入准备期心理训练!' }]},
        prepareMethod:{rules: [{ required: true, message: '请输入准备期训练手段!' }]},
        prepareWorkload:{rules: [{ required: true, message: '请输入准备期训练量!' }]},
        prepareIntensity:{rules: [{ required: true, message: '请输入准备期训练强度!' }]},
        basicStartDate:{},
        basicEndDate:{},
        basicGoal:{rules: [{ required: true, message: '请输入基本期训练任务!' }]},
        basicQualityPercent:{rules: [{ required: true, message: '请输入基本期素质训练!' }]},
        basicTechPercent:{rules: [{ required: true, message: '请输入基本期技术训练!' }]},
        basicTacticalPercent:{rules: [{ required: true, message: '请输入基本期战术训练!' }]},
        basicMentalPercent:{rules: [{ required: true, message: '请输入基本期心理训练!' }]},
        basicMethod:{rules: [{ required: true, message: '请输入基本期训练手段!' }]},
        basicWorkload:{rules: [{ required: true, message: '请输入基本期训练量!' }]},
        basicIntensity:{rules: [{ required: true, message: '请输入基本期训练强度!' }]},
        finishStartDate:{},
        finishEndDate:{},
        finishGoal:{rules: [{ required: true, message: '请输入结束期训练任务!' }]},
        finishQualityPercent:{rules: [{ required: true, message: '请输入结束期素质训练!' }]},
        finishTechPercent:{rules: [{ required: true, message: '请输入结束期技术训练!' }]},
        finishTacticalPercent:{rules: [{ required: true, message: '请输入结束期战术训练!' }]},
        finishMentalPercent:{rules: [{ required: true, message: '请输入结束期心理训练!' }]},
        finishMethod:{rules: [{ required: true, message: '请输入结束期训练手段!' }]},
        finishWorkload:{rules: [{ required: true, message: '请输入结束期训练量!' }]},
        finishIntensity:{rules: [{ required: true, message: '请输入结束期训练强度!' }]},
        deptEvaluation:{rules: [{ required: true, message: '请输入竞赛科评价!' }]},
        deptEvaluationDate:{},
        schoolEvaluation:{rules: [{ required: true, message: '请输入校领导评价!' }]},
        schoolEvaluationDate:{},
        },
        url: {
          add: "/edusport/sportClassWinterPlan/add",
          edit: "/edusport/sportClassWinterPlan/edit",
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
          this.form.setFieldsValue(pick(this.model,'sportClassId','taskName','coachNo','prepareStartDate','prepareEndDate','prepareGoal','prepareQualityPercent','prepareTechPercent','prepareTacticalPercent','prepareMentalPercent','prepareMethod','prepareWorkload','prepareIntensity','basicStartDate','basicEndDate','basicGoal','basicQualityPercent','basicTechPercent','basicTacticalPercent','basicMentalPercent','basicMethod','basicWorkload','basicIntensity','finishStartDate','finishEndDate','finishGoal','finishQualityPercent','finishTechPercent','finishTacticalPercent','finishMentalPercent','finishMethod','finishWorkload','finishIntensity','deptEvaluation','deptEvaluationDate','schoolEvaluation','schoolEvaluationDate'))
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
        this.form.setFieldsValue(pick(row,'sportClassId','taskName','coachNo','prepareStartDate','prepareEndDate','prepareGoal','prepareQualityPercent','prepareTechPercent','prepareTacticalPercent','prepareMentalPercent','prepareMethod','prepareWorkload','prepareIntensity','basicStartDate','basicEndDate','basicGoal','basicQualityPercent','basicTechPercent','basicTacticalPercent','basicMentalPercent','basicMethod','basicWorkload','basicIntensity','finishStartDate','finishEndDate','finishGoal','finishQualityPercent','finishTechPercent','finishTacticalPercent','finishMentalPercent','finishMethod','finishWorkload','finishIntensity','deptEvaluation','deptEvaluationDate','schoolEvaluation','schoolEvaluationDate'))
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