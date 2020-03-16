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

        <a-form-item label="教练员" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag v-decorator="['coachId', validatorRules.coachId]" dict="tb_edu_coach,coach_name,id" />
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
          <a-textarea v-decorator="['rewardsPunishments', validatorRules.rewardsPunishments]" rows="4" placeholder="请输入奖惩情况"/>
        </a-form-item>
        <a-form-item label="年度业务考核得分" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'appraisalScore', validatorRules.appraisalScore]" placeholder="请输入年度业务考核得分"></a-input>
        </a-form-item>
        <a-form-item label="年度业务考核等级" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'appraisalGrade', validatorRules.appraisalGrade]" placeholder="请输入年度业务考核等级"></a-input>
        </a-form-item>
        <a-form-item label="训练部门审核意见" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['auditOpinion', validatorRules.appraisalGrade]" rows="4" placeholder="请输入训练部门审核意见"/>
        </a-form-item>

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
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag'

  export default {
    name: "CoachPerformanceModal",
    components: {
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
          coachId:{rules: [{ required: true, message: '请选择教练员!' }]},
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
          rewardsPunishments:{rules: [{  message: '请输入奖惩情况!' }]},
          appraisalScore:{rules: [{ message: '请输入年度业务考核得分!' }]},
          appraisalGrade:{rules: [{ message: '请输入年度业务考核等级!' }]},
          auditOpinion:{rules: [{  message: '请输入训练部门审核意见!' }]},
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
        this.resetScreenSize(); // 调用此方法,根据屏幕宽度自适应调整抽屉的宽度
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
        this.disableSubmit = false;
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