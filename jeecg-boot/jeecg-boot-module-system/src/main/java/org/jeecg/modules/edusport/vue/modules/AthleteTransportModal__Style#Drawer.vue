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

        <a-form-item label="运动员学号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'athleteNo', validatorRules.athleteNo]" placeholder="请输入运动员学号"></a-input>
        </a-form-item>
        <a-form-item label="运动项目代码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'sportCode', validatorRules.sportCode]" placeholder="请输入运动项目代码"></a-input>
        </a-form-item>
        <a-form-item label="运动员等级" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['athleteTechGrade']" :trigger-change="true" dictCode="athlete_tech_grade" placeholder="请选择运动员等级"/>
        </a-form-item>
        <a-form-item label="输送单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'transportDepartment', validatorRules.transportDepartment]" placeholder="请输入输送单位"></a-input>
        </a-form-item>
        <a-form-item label="输送教练员代码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'transportCoachNo', validatorRules.transportCoachNo]" placeholder="请输入输送教练员代码"></a-input>
        </a-form-item>
        <a-form-item label="输送时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择输送时间" v-decorator="[ 'transportDate', validatorRules.transportDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="吸收单位类别" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['acceptDepartmentType']" :trigger-change="true" dictCode="accept_department_type" placeholder="请选择吸收单位类别"/>
        </a-form-item>
        <a-form-item label="吸收单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'acceptDepartment', validatorRules.acceptDepartment]" placeholder="请输入吸收单位"></a-input>
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
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  
  export default {
    name: "AthleteTransportModal",
    components: { 
      JDate,
      JDictSelectTag,
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
        athleteNo:{rules: [{ required: true, message: '请输入运动员学号!' }]},
        sportCode:{rules: [{ required: true, message: '请输入运动项目代码!' }]},
        athleteTechGrade:{rules: [{ required: true, message: '请输入运动员等级!' }]},
        transportDepartment:{rules: [{ required: true, message: '请输入输送单位!' }]},
        transportCoachNo:{rules: [{ required: true, message: '请输入输送教练员代码!' }]},
        transportDate:{},
        acceptDepartmentType:{rules: [{ required: true, message: '请输入吸收单位类别!' }]},
        acceptDepartment:{rules: [{ required: true, message: '请输入吸收单位!' }]},
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
          this.form.setFieldsValue(pick(this.model,'athleteNo','sportCode','athleteTechGrade','transportDepartment','transportCoachNo','transportDate','acceptDepartmentType','acceptDepartment'))
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
        this.form.setFieldsValue(pick(row,'athleteNo','sportCode','athleteTechGrade','transportDepartment','transportCoachNo','transportDate','acceptDepartmentType','acceptDepartment'))
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