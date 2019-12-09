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

        <a-form-item label="运动员" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag v-decorator="['studentNo']" dict="tb_edu_student,student_name,student_no" />
        </a-form-item>
        <a-form-item label="运动项目" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag v-decorator="['sportCode']" dict="tb_edu_sport,sport_name,sport_code" />
        </a-form-item>
        <a-form-item label="运动员等级" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['athleteTechGrade']" :trigger-change="true" dictCode="athlete_tech_grade" placeholder="请选择运动员等级"/>
        </a-form-item>
        <a-form-item label="输送单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'transportDepartment', validatorRules.transportDepartment]" placeholder="请输入输送单位"></a-input>
        </a-form-item>
        <a-form-item label="输送教练员" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag v-decorator="['transportTeacherNo']" dict="tb_edu_teacher,teacher_name,teacher_no" />
        </a-form-item>
        <a-form-item label="输送时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择输送时间" v-decorator="[ 'transportDate', validatorRules.transportDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="吸收单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'acceptDepartment', validatorRules.acceptDepartment]" placeholder="请输入吸收单位"></a-input>
        </a-form-item>
        <a-form-item label="吸收单位类别" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'acceptDepartmentType', validatorRules.acceptDepartmentType]" placeholder="请输入吸收单位类别"></a-input>
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
    name: "AthleteTransportModal",
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
        studentNo:{rules: [{ required: true, message: '请输入运动员!' }]},
        sportCode:{rules: [{ required: true, message: '请输入运动项目!' }]},
        athleteTechGrade:{rules: [{ required: true, message: '请输入运动员等级!' }]},
        transportDepartment:{},
        transportTeacherNo:{},
        transportDate:{rules: [{ required: true, message: '请输入输送时间!' }]},
        acceptDepartment:{rules: [{ required: true, message: '请输入吸收单位!' }]},
        acceptDepartmentType:{},
        },
        url: {
          add: "/edusport/athleteTransport/add",
          edit: "/edusport/athleteTransport/edit",
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
          this.form.setFieldsValue(pick(this.model,'studentNo','sportCode','athleteTechGrade','transportDepartment','transportTeacherNo','transportDate','acceptDepartment','acceptDepartmentType'))
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
        this.form.setFieldsValue(pick(row,'studentNo','sportCode','athleteTechGrade','transportDepartment','transportTeacherNo','transportDate','acceptDepartment','acceptDepartmentType'))
      },

      
    }
  }
</script>