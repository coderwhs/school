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
        <a-form-item label="初始情况" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'teamSituation', validatorRules.teamSituation]" placeholder="请输入初始情况"></a-input>
        </a-form-item>
        <a-form-item label="全年任务" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'yearGoal', validatorRules.yearGoal]" placeholder="请输入全年任务"></a-input>
        </a-form-item>
        <a-form-item label="准备期开始日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择准备期开始日期" v-decorator="[ 'prepareStartDate', validatorRules.prepareStartDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="准备期结束日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择准备期结束日期" v-decorator="[ 'prepareEndDate', validatorRules.prepareEndDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="准备期周数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'prepareWeeks', validatorRules.prepareWeeks]" placeholder="请输入准备期周数"></a-input>
        </a-form-item>
        <a-form-item label="准备期天数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'prepareDays', validatorRules.prepareDays]" placeholder="请输入准备期天数"></a-input>
        </a-form-item>
        <a-form-item label="准备期训练任务" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['prepareGoal']" rows="4" placeholder="请输入准备期训练任务"/>
        </a-form-item>
        <a-form-item label="准备期身体训练" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'prepareBodyPercent', validatorRules.prepareBodyPercent]" placeholder="请输入准备期身体训练"></a-input>
        </a-form-item>
        <a-form-item label="准备期素质训练" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'prepareQualityPercent', validatorRules.prepareQualityPercent]" placeholder="请输入准备期素质训练"></a-input>
        </a-form-item>
        <a-form-item label="准备期技术训练" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'prepareTechPercent', validatorRules.prepareTechPercent]" placeholder="请输入准备期技术训练"></a-input>
        </a-form-item>
        <a-form-item label="准备期其他训练" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'prepareOtherPercent', validatorRules.prepareOtherPercent]" placeholder="请输入准备期其他训练"></a-input>
        </a-form-item>
        <a-form-item label="准备期训练手段" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['prepareMethod']" rows="4" placeholder="请输入准备期训练手段"/>
        </a-form-item>
        <a-form-item label="比赛期开始日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择比赛期开始日期" v-decorator="[ 'contestStartDate', validatorRules.contestStartDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="比赛期结束日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择比赛期结束日期" v-decorator="[ 'contestEndDate', validatorRules.contestEndDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="比赛期周数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'contestWeeks', validatorRules.contestWeeks]" placeholder="请输入比赛期周数"></a-input>
        </a-form-item>
        <a-form-item label="比赛期天数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'contestDays', validatorRules.contestDays]" placeholder="请输入比赛期天数"></a-input>
        </a-form-item>
        <a-form-item label="比赛期训练任务" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['contestGoal']" rows="4" placeholder="请输入比赛期训练任务"/>
        </a-form-item>
        <a-form-item label="比赛期身体训练" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'contestBodyPercent', validatorRules.contestBodyPercent]" placeholder="请输入比赛期身体训练"></a-input>
        </a-form-item>
        <a-form-item label="比赛期素质训练" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'contestQualityPercent', validatorRules.contestQualityPercent]" placeholder="请输入比赛期素质训练"></a-input>
        </a-form-item>
        <a-form-item label="比赛期技术训练" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'contestTechPercent', validatorRules.contestTechPercent]" placeholder="请输入比赛期技术训练"></a-input>
        </a-form-item>
        <a-form-item label="比赛期其他训练" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'contestOtherPercent', validatorRules.contestOtherPercent]" placeholder="请输入比赛期其他训练"></a-input>
        </a-form-item>
        <a-form-item label="比赛期训练手段" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['contestMethod']" rows="4" placeholder="请输入比赛期训练手段"/>
        </a-form-item>
        <a-form-item label="过渡期开始日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择过渡期开始日期" v-decorator="[ 'transitionStartDate', validatorRules.transitionStartDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="过渡期结束日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择过渡期结束日期" v-decorator="[ 'transitionEndDate', validatorRules.transitionEndDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="过渡期周数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'transitionWeeks', validatorRules.transitionWeeks]" placeholder="请输入过渡期周数"></a-input>
        </a-form-item>
        <a-form-item label="过渡期天数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'transitionDays', validatorRules.transitionDays]" placeholder="请输入过渡期天数"></a-input>
        </a-form-item>
        <a-form-item label="过渡期训练任务" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['transitionGoal']" rows="4" placeholder="请输入过渡期训练任务"/>
        </a-form-item>
        <a-form-item label="过渡期身体训练" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'transitionBodyPercent', validatorRules.transitionBodyPercent]" placeholder="请输入过渡期身体训练"></a-input>
        </a-form-item>
        <a-form-item label="过渡期素质训练" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'transitionQualityPercent', validatorRules.transitionQualityPercent]" placeholder="请输入过渡期素质训练"></a-input>
        </a-form-item>
        <a-form-item label="过渡期技术训练" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'transitionTechPercent', validatorRules.transitionTechPercent]" placeholder="请输入过渡期技术训练"></a-input>
        </a-form-item>
        <a-form-item label="过渡期其他训练" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'transitionOtherPercent', validatorRules.transitionOtherPercent]" placeholder="请输入过渡期其他训练"></a-input>
        </a-form-item>
        <a-form-item label="过渡期训练手段" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['transitionMethod']" rows="4" placeholder="请输入过渡期训练手段"/>
        </a-form-item>
        <a-form-item label="1月训练量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'm1Workload', validatorRules.m1Workload]" placeholder="请输入1月训练量" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="2月训练量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'm2Workload', validatorRules.m2Workload]" placeholder="请输入2月训练量" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="3月训练量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'm3Workload', validatorRules.m3Workload]" placeholder="请输入3月训练量" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="4月训练量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'm4Workload', validatorRules.m4Workload]" placeholder="请输入4月训练量" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="5月训练量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'm5Workload', validatorRules.m5Workload]" placeholder="请输入5月训练量" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="6月训练量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'm6Workload', validatorRules.m6Workload]" placeholder="请输入6月训练量" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="7月训练量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'm7Workload', validatorRules.m7Workload]" placeholder="请输入7月训练量" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="8月训练量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'm8Workload', validatorRules.m8Workload]" placeholder="请输入8月训练量" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="9月训练量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'm9Workload', validatorRules.m9Workload]" placeholder="请输入9月训练量" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="10月训练量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'm10Workload', validatorRules.m10Workload]" placeholder="请输入10月训练量" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="11月训练量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'm11Workload', validatorRules.m11Workload]" placeholder="请输入11月训练量" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="12月训练量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'm12Workload', validatorRules.m12Workload]" placeholder="请输入12月训练量" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="1月训练强度" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'm1Intensity', validatorRules.m1Intensity]" placeholder="请输入1月训练强度" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="2月训练强度" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'm2Intensity', validatorRules.m2Intensity]" placeholder="请输入2月训练强度" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="3月训练强度" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'm3Intensity', validatorRules.m3Intensity]" placeholder="请输入3月训练强度" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="4月训练强度" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'm4Intensity', validatorRules.m4Intensity]" placeholder="请输入4月训练强度" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="5月训练强度" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'm5Intensity', validatorRules.m5Intensity]" placeholder="请输入5月训练强度" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="6月训练强度" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'm6Intensity', validatorRules.m6Intensity]" placeholder="请输入6月训练强度" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="7月训练强度" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'm7Intensity', validatorRules.m7Intensity]" placeholder="请输入7月训练强度" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="8月训练强度" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'm8Intensity', validatorRules.m8Intensity]" placeholder="请输入8月训练强度" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="9月训练强度" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'm9Intensity', validatorRules.m9Intensity]" placeholder="请输入9月训练强度" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="10月训练强度" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'm10Intensity', validatorRules.m10Intensity]" placeholder="请输入10月训练强度" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="11月训练强度" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'm11Intensity', validatorRules.m11Intensity]" placeholder="请输入11月训练强度" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="12月训练强度" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'm12Intensity', validatorRules.m12Intensity]" placeholder="请输入12月训练强度" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="考核安排" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['performanceCheck']" rows="4" placeholder="请输入考核安排"/>
        </a-form-item>
        <a-form-item label="指标检查标准" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['targetCheck']" rows="4" placeholder="请输入指标检查标准"/>
        </a-form-item>
        <a-form-item label="实施措施" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['actionSteps']" rows="4" placeholder="请输入实施措施"/>
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
    name: "SportClassYearPlanModal",
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
        teamSituation:{},
        yearGoal:{},
        prepareStartDate:{},
        prepareEndDate:{},
        prepareWeeks:{rules: [{ required: true, message: '请输入准备期周数!' }]},
        prepareDays:{rules: [{ required: true, message: '请输入准备期天数!' }]},
        prepareGoal:{rules: [{ required: true, message: '请输入准备期训练任务!' }]},
        prepareBodyPercent:{rules: [{ required: true, message: '请输入准备期身体训练!' }]},
        prepareQualityPercent:{rules: [{ required: true, message: '请输入准备期素质训练!' }]},
        prepareTechPercent:{rules: [{ required: true, message: '请输入准备期技术训练!' }]},
        prepareOtherPercent:{rules: [{ required: true, message: '请输入准备期其他训练!' }]},
        prepareMethod:{rules: [{ required: true, message: '请输入准备期训练手段!' }]},
        contestStartDate:{},
        contestEndDate:{},
        contestWeeks:{rules: [{ required: true, message: '请输入比赛期周数!' }]},
        contestDays:{rules: [{ required: true, message: '请输入比赛期天数!' }]},
        contestGoal:{rules: [{ required: true, message: '请输入比赛期训练任务!' }]},
        contestBodyPercent:{rules: [{ required: true, message: '请输入比赛期身体训练!' }]},
        contestQualityPercent:{rules: [{ required: true, message: '请输入比赛期素质训练!' }]},
        contestTechPercent:{rules: [{ required: true, message: '请输入比赛期技术训练!' }]},
        contestOtherPercent:{rules: [{ required: true, message: '请输入比赛期其他训练!' }]},
        contestMethod:{rules: [{ required: true, message: '请输入比赛期训练手段!' }]},
        transitionStartDate:{},
        transitionEndDate:{},
        transitionWeeks:{rules: [{ required: true, message: '请输入过渡期周数!' }]},
        transitionDays:{rules: [{ required: true, message: '请输入过渡期天数!' }]},
        transitionGoal:{rules: [{ required: true, message: '请输入过渡期训练任务!' }]},
        transitionBodyPercent:{rules: [{ required: true, message: '请输入过渡期身体训练!' }]},
        transitionQualityPercent:{rules: [{ required: true, message: '请输入过渡期素质训练!' }]},
        transitionTechPercent:{rules: [{ required: true, message: '请输入过渡期技术训练!' }]},
        transitionOtherPercent:{rules: [{ required: true, message: '请输入过渡期其他训练!' }]},
        transitionMethod:{rules: [{ required: true, message: '请输入过渡期训练手段!' }]},
        m1Workload:{rules: [{ required: true, message: '请输入1月训练量!' }]},
        m2Workload:{rules: [{ required: true, message: '请输入2月训练量!' }]},
        m3Workload:{rules: [{ required: true, message: '请输入3月训练量!' }]},
        m4Workload:{rules: [{ required: true, message: '请输入4月训练量!' }]},
        m5Workload:{rules: [{ required: true, message: '请输入5月训练量!' }]},
        m6Workload:{rules: [{ required: true, message: '请输入6月训练量!' }]},
        m7Workload:{rules: [{ required: true, message: '请输入7月训练量!' }]},
        m8Workload:{rules: [{ required: true, message: '请输入8月训练量!' }]},
        m9Workload:{rules: [{ required: true, message: '请输入9月训练量!' }]},
        m10Workload:{rules: [{ required: true, message: '请输入10月训练量!' }]},
        m11Workload:{rules: [{ required: true, message: '请输入11月训练量!' }]},
        m12Workload:{rules: [{ required: true, message: '请输入12月训练量!' }]},
        m1Intensity:{rules: [{ required: true, message: '请输入1月训练强度!' }]},
        m2Intensity:{rules: [{ required: true, message: '请输入2月训练强度!' }]},
        m3Intensity:{rules: [{ required: true, message: '请输入3月训练强度!' }]},
        m4Intensity:{rules: [{ required: true, message: '请输入4月训练强度!' }]},
        m5Intensity:{rules: [{ required: true, message: '请输入5月训练强度!' }]},
        m6Intensity:{rules: [{ required: true, message: '请输入6月训练强度!' }]},
        m7Intensity:{rules: [{ required: true, message: '请输入7月训练强度!' }]},
        m8Intensity:{rules: [{ required: true, message: '请输入8月训练强度!' }]},
        m9Intensity:{rules: [{ required: true, message: '请输入9月训练强度!' }]},
        m10Intensity:{rules: [{ required: true, message: '请输入10月训练强度!' }]},
        m11Intensity:{rules: [{ required: true, message: '请输入11月训练强度!' }]},
        m12Intensity:{rules: [{ required: true, message: '请输入12月训练强度!' }]},
        performanceCheck:{rules: [{ required: true, message: '请输入考核安排!' }]},
        targetCheck:{rules: [{ required: true, message: '请输入指标检查标准!' }]},
        actionSteps:{rules: [{ required: true, message: '请输入实施措施!' }]},
        deptEvaluation:{rules: [{ required: true, message: '请输入竞赛科评价!' }]},
        deptEvaluationDate:{},
        schoolEvaluation:{rules: [{ required: true, message: '请输入校领导评价!' }]},
        schoolEvaluationDate:{},
        },
        url: {
          add: "/edusport/sportClassYearPlan/add",
          edit: "/edusport/sportClassYearPlan/edit",
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
          this.form.setFieldsValue(pick(this.model,'sportClassId','taskName','coachNo','teamSituation','yearGoal','prepareStartDate','prepareEndDate','prepareWeeks','prepareDays','prepareGoal','prepareBodyPercent','prepareQualityPercent','prepareTechPercent','prepareOtherPercent','prepareMethod','contestStartDate','contestEndDate','contestWeeks','contestDays','contestGoal','contestBodyPercent','contestQualityPercent','contestTechPercent','contestOtherPercent','contestMethod','transitionStartDate','transitionEndDate','transitionWeeks','transitionDays','transitionGoal','transitionBodyPercent','transitionQualityPercent','transitionTechPercent','transitionOtherPercent','transitionMethod','m1Workload','m2Workload','m3Workload','m4Workload','m5Workload','m6Workload','m7Workload','m8Workload','m9Workload','m10Workload','m11Workload','m12Workload','m1Intensity','m2Intensity','m3Intensity','m4Intensity','m5Intensity','m6Intensity','m7Intensity','m8Intensity','m9Intensity','m10Intensity','m11Intensity','m12Intensity','performanceCheck','targetCheck','actionSteps','deptEvaluation','deptEvaluationDate','schoolEvaluation','schoolEvaluationDate'))
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
        this.form.setFieldsValue(pick(row,'sportClassId','taskName','coachNo','teamSituation','yearGoal','prepareStartDate','prepareEndDate','prepareWeeks','prepareDays','prepareGoal','prepareBodyPercent','prepareQualityPercent','prepareTechPercent','prepareOtherPercent','prepareMethod','contestStartDate','contestEndDate','contestWeeks','contestDays','contestGoal','contestBodyPercent','contestQualityPercent','contestTechPercent','contestOtherPercent','contestMethod','transitionStartDate','transitionEndDate','transitionWeeks','transitionDays','transitionGoal','transitionBodyPercent','transitionQualityPercent','transitionTechPercent','transitionOtherPercent','transitionMethod','m1Workload','m2Workload','m3Workload','m4Workload','m5Workload','m6Workload','m7Workload','m8Workload','m9Workload','m10Workload','m11Workload','m12Workload','m1Intensity','m2Intensity','m3Intensity','m4Intensity','m5Intensity','m6Intensity','m7Intensity','m8Intensity','m9Intensity','m10Intensity','m11Intensity','m12Intensity','performanceCheck','targetCheck','actionSteps','deptEvaluation','deptEvaluationDate','schoolEvaluation','schoolEvaluationDate'))
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