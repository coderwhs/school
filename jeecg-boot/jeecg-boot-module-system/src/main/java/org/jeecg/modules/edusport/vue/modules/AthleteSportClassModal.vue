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

        <a-form-item label="训练队" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag v-decorator="['sportClassId']" dict="tb_edu_sport_class,class_name,id" />
        </a-form-item>
        <a-form-item label="运动员" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag v-decorator="['athleteId']" dict="tb_edu_athlete,athlete_name,id" />
        </a-form-item>
        <a-form-item label="入队日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择入队日期" v-decorator="[ 'attendDate', validatorRules.attendDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="比赛最好成绩" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'athleteBestScore', validatorRules.athleteBestScore]" placeholder="请输入比赛最好成绩"></a-input>
        </a-form-item>
        <a-form-item label="获得等级" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['athleteAwardTechGrade']" :trigger-change="true" dictCode="athlete_tech_grade" placeholder="请选择获得等级"/>
        </a-form-item>
        <a-form-item label="破纪录状况" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'aboutBreakRecord', validatorRules.aboutBreakRecord]" placeholder="请输入破纪录状况"></a-input>
        </a-form-item>
        <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['remark']" rows="4" placeholder="请输入备注"/>
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
    name: "AthleteSportClassModal",
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
        sportClassId:{rules: [{ required: true, message: '请输入训练队!' }]},
        athleteId:{rules: [{ required: true, message: '请输入运动员!' }]},
        attendDate:{rules: [{ required: true, message: '请输入入队日期!' }]},
        athleteBestScore:{},
        athleteAwardTechGrade:{},
        aboutBreakRecord:{},
        remark:{},
        },
        url: {
          add: "/edusport/athleteSportClass/add",
          edit: "/edusport/athleteSportClass/edit",
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
          this.form.setFieldsValue(pick(this.model,'sportClassId','athleteId','attendDate','athleteBestScore','athleteAwardTechGrade','aboutBreakRecord','remark'))
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
        this.form.setFieldsValue(pick(row,'sportClassId','athleteId','attendDate','athleteBestScore','athleteAwardTechGrade','aboutBreakRecord','remark'))
      },

      
    }
  }
</script>