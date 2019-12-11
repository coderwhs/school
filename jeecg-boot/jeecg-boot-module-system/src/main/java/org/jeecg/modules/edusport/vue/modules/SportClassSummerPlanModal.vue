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

        <a-form-item label="训练班" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag v-decorator="['sportClassId']" dict="tb_edu_sport_class,class_name,id" />
        </a-form-item>
        <a-form-item label="训练计划名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'taskName', validatorRules.taskName]" placeholder="请输入训练计划名称"></a-input>
        </a-form-item>
        <a-form-item label="发布人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag v-decorator="['coachNo']" dict="tb_edu_coach,coach_name,coach_no" />
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
        <a-form-item label="比赛期开始日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择比赛期开始日期" v-decorator="[ 'contestStartDate', validatorRules.contestStartDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="比赛期结束日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择比赛期结束日期" v-decorator="[ 'contestEndDate', validatorRules.contestEndDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="比赛期训练任务" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['contestGoal']" rows="4" placeholder="请输入比赛期训练任务"/>
        </a-form-item>
        <a-form-item label="比赛期素质训练" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'contestQualityPercent', validatorRules.contestQualityPercent]" placeholder="请输入比赛期素质训练"></a-input>
        </a-form-item>
        <a-form-item label="比赛期技术训练" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'contestTechPercent', validatorRules.contestTechPercent]" placeholder="请输入比赛期技术训练"></a-input>
        </a-form-item>
        <a-form-item label="比赛期战术训练" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'contestTacticalPercent', validatorRules.contestTacticalPercent]" placeholder="请输入比赛期战术训练"></a-input>
        </a-form-item>
        <a-form-item label="比赛期心理训练" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'contestMentalPercent', validatorRules.contestMentalPercent]" placeholder="请输入比赛期心理训练"></a-input>
        </a-form-item>
        <a-form-item label="比赛期训练手段" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['contestMethod']" rows="4" placeholder="请输入比赛期训练手段"/>
        </a-form-item>
        <a-form-item label="比赛期训练量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'contestWorkload', validatorRules.contestWorkload]" placeholder="请输入比赛期训练量"></a-input>
        </a-form-item>
        <a-form-item label="比赛期训练强度" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'contestIntensity', validatorRules.contestIntensity]" placeholder="请输入比赛期训练强度"></a-input>
        </a-form-item>
        <a-form-item label="过渡期开始日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择过渡期开始日期" v-decorator="[ 'transitionStartDate', validatorRules.transitionStartDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="过渡期结束日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择过渡期结束日期" v-decorator="[ 'transitionEndDate', validatorRules.transitionEndDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="过渡期训练任务" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['transitionGoal']" rows="4" placeholder="请输入过渡期训练任务"/>
        </a-form-item>
        <a-form-item label="过渡期素质训练" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'transitionQualityPercent', validatorRules.transitionQualityPercent]" placeholder="请输入过渡期素质训练"></a-input>
        </a-form-item>
        <a-form-item label="过渡期技术训练" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'transitionTechPercent', validatorRules.transitionTechPercent]" placeholder="请输入过渡期技术训练"></a-input>
        </a-form-item>
        <a-form-item label="过渡期战术训练" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'transitionTacticalPercent', validatorRules.transitionTacticalPercent]" placeholder="请输入过渡期战术训练"></a-input>
        </a-form-item>
        <a-form-item label="过渡期心理训练" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'transitionMentalPercent', validatorRules.transitionMentalPercent]" placeholder="请输入过渡期心理训练"></a-input>
        </a-form-item>
        <a-form-item label="过渡期训练手段" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['transitionMethod']" rows="4" placeholder="请输入过渡期训练手段"/>
        </a-form-item>
        <a-form-item label="过渡期训练量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'transitionWorkload', validatorRules.transitionWorkload]" placeholder="请输入过渡期训练量"></a-input>
        </a-form-item>
        <a-form-item label="过渡期训练强度" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'transitionIntensity', validatorRules.transitionIntensity]" placeholder="请输入过渡期训练强度"></a-input>
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

  export default {
    name: "SportClassSummerPlanModal",
    components: { 
      JDate,
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
        contestStartDate:{},
        contestEndDate:{},
        contestGoal:{rules: [{ required: true, message: '请输入比赛期训练任务!' }]},
        contestQualityPercent:{rules: [{ required: true, message: '请输入比赛期素质训练!' }]},
        contestTechPercent:{rules: [{ required: true, message: '请输入比赛期技术训练!' }]},
        contestTacticalPercent:{rules: [{ required: true, message: '请输入比赛期战术训练!' }]},
        contestMentalPercent:{rules: [{ required: true, message: '请输入比赛期心理训练!' }]},
        contestMethod:{rules: [{ required: true, message: '请输入比赛期训练手段!' }]},
        contestWorkload:{rules: [{ required: true, message: '请输入比赛期训练量!' }]},
        contestIntensity:{rules: [{ required: true, message: '请输入比赛期训练强度!' }]},
        transitionStartDate:{},
        transitionEndDate:{},
        transitionGoal:{rules: [{ required: true, message: '请输入过渡期训练任务!' }]},
        transitionQualityPercent:{rules: [{ required: true, message: '请输入过渡期素质训练!' }]},
        transitionTechPercent:{rules: [{ required: true, message: '请输入过渡期技术训练!' }]},
        transitionTacticalPercent:{rules: [{ required: true, message: '请输入过渡期战术训练!' }]},
        transitionMentalPercent:{rules: [{ required: true, message: '请输入过渡期心理训练!' }]},
        transitionMethod:{rules: [{ required: true, message: '请输入过渡期训练手段!' }]},
        transitionWorkload:{rules: [{ required: true, message: '请输入过渡期训练量!' }]},
        transitionIntensity:{rules: [{ required: true, message: '请输入过渡期训练强度!' }]},
        deptEvaluation:{rules: [{ required: true, message: '请输入竞赛科评价!' }]},
        deptEvaluationDate:{},
        schoolEvaluation:{rules: [{ required: true, message: '请输入校领导评价!' }]},
        schoolEvaluationDate:{},
        },
        url: {
          add: "/edusport/sportClassSummerPlan/add",
          edit: "/edusport/sportClassSummerPlan/edit",
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
          this.form.setFieldsValue(pick(this.model,'sportClassId','taskName','coachNo','prepareStartDate','prepareEndDate','prepareGoal','prepareQualityPercent','prepareTechPercent','prepareTacticalPercent','prepareMentalPercent','prepareMethod','prepareWorkload','prepareIntensity','contestStartDate','contestEndDate','contestGoal','contestQualityPercent','contestTechPercent','contestTacticalPercent','contestMentalPercent','contestMethod','contestWorkload','contestIntensity','transitionStartDate','transitionEndDate','transitionGoal','transitionQualityPercent','transitionTechPercent','transitionTacticalPercent','transitionMentalPercent','transitionMethod','transitionWorkload','transitionIntensity','deptEvaluation','deptEvaluationDate','schoolEvaluation','schoolEvaluationDate'))
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
        this.form.setFieldsValue(pick(row,'sportClassId','taskName','coachNo','prepareStartDate','prepareEndDate','prepareGoal','prepareQualityPercent','prepareTechPercent','prepareTacticalPercent','prepareMentalPercent','prepareMethod','prepareWorkload','prepareIntensity','contestStartDate','contestEndDate','contestGoal','contestQualityPercent','contestTechPercent','contestTacticalPercent','contestMentalPercent','contestMethod','contestWorkload','contestIntensity','transitionStartDate','transitionEndDate','transitionGoal','transitionQualityPercent','transitionTechPercent','transitionTacticalPercent','transitionMentalPercent','transitionMethod','transitionWorkload','transitionIntensity','deptEvaluation','deptEvaluationDate','schoolEvaluation','schoolEvaluationDate'))
      },

      
    }
  }
</script>