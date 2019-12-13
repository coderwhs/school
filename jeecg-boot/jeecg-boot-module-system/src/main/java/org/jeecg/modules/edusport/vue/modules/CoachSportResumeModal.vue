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

        <a-form-item label="教练员" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag v-decorator="['coachId']" dict="tb_edu_coach,coach_name,id" />
        </a-form-item>
        <a-form-item label="运动项目" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag v-decorator="['sportCode']" dict="tb_edu_sport,sport_name,sport_code" />
        </a-form-item>
        <a-form-item label="参加时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择参加时间" v-decorator="[ 'attendDate', validatorRules.attendDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="获得最高等级" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['awardedHighestGrade']" :trigger-change="true" dictCode="athlete_tech_grade" placeholder="请选择获得最高等级"/>
        </a-form-item>
        <a-form-item label="获得年度(年)" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'awardedDate', validatorRules.awardedDate]" placeholder="请输入获得年度(年)"></a-input>
        </a-form-item>
        <a-form-item label="最高训练单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'highestTrainingUnit', validatorRules.highestTrainingUnit]" placeholder="请输入最高训练单位"></a-input>
        </a-form-item>
        <a-form-item label="训练年度(年)" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'trainingDate', validatorRules.trainingDate]" placeholder="请输入训练年度(年)"></a-input>
        </a-form-item>
        <a-form-item label="比赛小项" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'contestEvent', validatorRules.contestEvent]" placeholder="请输入比赛小项"></a-input>
        </a-form-item>
        <a-form-item label="最好成绩" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'contestScore', validatorRules.contestScore]" placeholder="请输入最好成绩"></a-input>
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JDate from '@/components/jeecg/JDate'  
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag'

  export default {
    name: "CoachSportResumeModal",
    components: { 
      JDate,
      JDictSelectTag,
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
        coachId:{rules: [{ required: true, message: '请输入教练员!' }]},
        sportCode:{rules: [{ required: true, message: '请输入运动项目!' }]},
        attendDate:{rules: [{ required: true, message: '请输入参加时间!' }]},
        awardedHighestGrade:{},
        awardedDate:{},
        highestTrainingUnit:{},
        trainingDate:{},
        contestEvent:{},
        contestScore:{},
        },
        url: {
          add: "/edusport/coachSportResume/add",
          edit: "/edusport/coachSportResume/edit",
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
          this.form.setFieldsValue(pick(this.model,'coachId','sportCode','attendDate','awardedHighestGrade','awardedDate','highestTrainingUnit','trainingDate','contestEvent','contestScore'))
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
        this.form.setFieldsValue(pick(row,'coachId','sportCode','attendDate','awardedHighestGrade','awardedDate','highestTrainingUnit','trainingDate','contestEvent','contestScore'))
      },

      
    }
  }
</script>