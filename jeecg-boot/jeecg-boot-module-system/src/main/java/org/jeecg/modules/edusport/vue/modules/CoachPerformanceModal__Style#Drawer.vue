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

        <a-form-item label="教练员" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'coachId', validatorRules.coachId]" placeholder="请输入教练员"></a-input>
        </a-form-item>
        <a-form-item label="业务年度" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'performanceYear', validatorRules.performanceYear]" placeholder="请输入业务年度" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="现带训(人)" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'athleteNum', validatorRules.athleteNum]" placeholder="请输入现带训(人)" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="年度进队(人)" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'attendNum', validatorRules.attendNum]" placeholder="请输入年度进队(人)" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="年度离队(人)" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'leaveNum', validatorRules.leaveNum]" placeholder="请输入年度离队(人)" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="市体校(人)" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'citySchoolNum', validatorRules.citySchoolNum]" placeholder="请输入市体校(人)" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="省体校(人)" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'provinceSchoolNum', validatorRules.provinceSchoolNum]" placeholder="请输入省体校(人)" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="省队集试训(人)" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'provinceTeamNum', validatorRules.provinceTeamNum]" placeholder="请输入省队集试训(人)" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="八一集试训(人)" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'bayiTeamNum', validatorRules.bayiTeamNum]" placeholder="请输入八一集试训(人)" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="输送国家队(人)" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'toNationalTeam', validatorRules.toNationalTeam]" placeholder="请输入输送国家队(人)" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="输送国青队(人)" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'toNationalYouthTeam', validatorRules.toNationalYouthTeam]" placeholder="请输入输送国青队(人)" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="输送省队(人)" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'toProvinceTeam', validatorRules.toProvinceTeam]" placeholder="请输入输送省队(人)" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="输送省体校(人)" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'toProvinceSchool', validatorRules.toProvinceSchool]" placeholder="请输入输送省体校(人)" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="输送市体校(人)" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'toCitySchool', validatorRules.toCitySchool]" placeholder="请输入输送市体校(人)" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="输送高校(人)" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'toCollege', validatorRules.toCollege]" placeholder="请输入输送高校(人)" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="输送备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'toRemark', validatorRules.toRemark]" placeholder="请输入输送备注"></a-input>
        </a-form-item>
        <a-form-item label="锦标赛第一(人)" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'contestFirst', validatorRules.contestFirst]" placeholder="请输入锦标赛第一(人)" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="锦标赛第二(人)" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'contestSecond', validatorRules.contestSecond]" placeholder="请输入锦标赛第二(人)" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="锦标赛第三(人)" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'contestThird', validatorRules.contestThird]" placeholder="请输入锦标赛第三(人)" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="锦标赛达优(人)" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'contestExcellence', validatorRules.contestExcellence]" placeholder="请输入锦标赛达优(人)" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="锦标赛达良(人)" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'contestGood', validatorRules.contestGood]" placeholder="请输入锦标赛达良(人)" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="锦标赛总分" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'contestTotalPoints', validatorRules.contestTotalPoints]" placeholder="请输入锦标赛总分" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="锦标赛备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'contestRemark', validatorRules.contestRemark]" placeholder="请输入锦标赛备注" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="是否年度少儿锦标赛精神文明运动队" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'isScTeam', validatorRules.isScTeam]" placeholder="请输入是否年度少儿锦标赛精神文明运动队"></a-input>
        </a-form-item>
        <a-form-item label="奖惩情况" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['rewardsPunishments']" rows="4" placeholder="请输入奖惩情况"/>
        </a-form-item>
        <a-form-item label="年度业务考核得分" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'appraisalScore', validatorRules.appraisalScore]" placeholder="请输入年度业务考核得分"></a-input>
        </a-form-item>
        <a-form-item label="年度业务考核等级" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'appraisalGrade', validatorRules.appraisalGrade]" placeholder="请输入年度业务考核等级"></a-input>
        </a-form-item>
        <a-form-item label="学校训练部门审核意见" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['auditOpinion']" rows="4" placeholder="请输入学校训练部门审核意见"/>
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
  
  export default {
    name: "CoachPerformanceModal",
    components: { 
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
        coachId:{rules: [{ required: true, message: '请输入教练员!' }]},
        performanceYear:{rules: [{ required: true, message: '请输入业务年度!' }]},
        athleteNum:{},
        attendNum:{},
        leaveNum:{},
        citySchoolNum:{},
        provinceSchoolNum:{},
        provinceTeamNum:{},
        bayiTeamNum:{},
        toNationalTeam:{},
        toNationalYouthTeam:{},
        toProvinceTeam:{},
        toProvinceSchool:{},
        toCitySchool:{},
        toCollege:{},
        toRemark:{},
        contestFirst:{},
        contestSecond:{},
        contestThird:{},
        contestExcellence:{},
        contestGood:{},
        contestTotalPoints:{},
        contestRemark:{},
        isScTeam:{},
        rewardsPunishments:{rules: [{ required: true, message: '请输入奖惩情况!' }]},
        appraisalScore:{rules: [{ required: true, message: '请输入年度业务考核得分!' }]},
        appraisalGrade:{rules: [{ required: true, message: '请输入年度业务考核等级!' }]},
        auditOpinion:{rules: [{ required: true, message: '请输入学校训练部门审核意见!' }]},
        },
        url: {
          add: "/edusport/coachPerformance/add",
          edit: "/edusport/coachPerformance/edit",
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
          this.form.setFieldsValue(pick(this.model,'coachId','performanceYear','athleteNum','attendNum','leaveNum','citySchoolNum','provinceSchoolNum','provinceTeamNum','bayiTeamNum','toNationalTeam','toNationalYouthTeam','toProvinceTeam','toProvinceSchool','toCitySchool','toCollege','toRemark','contestFirst','contestSecond','contestThird','contestExcellence','contestGood','contestTotalPoints','contestRemark','isScTeam','rewardsPunishments','appraisalScore','appraisalGrade','auditOpinion'))
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
        this.form.setFieldsValue(pick(row,'coachId','performanceYear','athleteNum','attendNum','leaveNum','citySchoolNum','provinceSchoolNum','provinceTeamNum','bayiTeamNum','toNationalTeam','toNationalYouthTeam','toProvinceTeam','toProvinceSchool','toCitySchool','toCollege','toRemark','contestFirst','contestSecond','contestThird','contestExcellence','contestGood','contestTotalPoints','contestRemark','isScTeam','rewardsPunishments','appraisalScore','appraisalGrade','auditOpinion'))
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