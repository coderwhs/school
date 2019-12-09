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
        <a-form-item label="训练任务名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'taskName', validatorRules.taskName]" placeholder="请输入训练任务名称"></a-input>
        </a-form-item>
        <a-form-item label="发布教练" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag v-decorator="['teacherNo']" dict="tb_edu_teacher,teacher_name,teacher_no" />
        </a-form-item>
        <a-form-item label="开始日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择开始日期" v-decorator="[ 'taskStartDate', validatorRules.taskStartDate]" :trigger-change="true" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="结束日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择结束日期" v-decorator="[ 'taskEndDate', validatorRules.taskEndDate]" :trigger-change="true" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="训练内容" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['taskContent']" rows="4" placeholder="请输入训练内容"/>
        </a-form-item>
        <a-form-item label="训练手段" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['taskMethod']" rows="4" placeholder="请输入训练手段"/>
        </a-form-item>
        <a-form-item label="持续时间(分钟)" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'taskDurationTime', validatorRules.taskDurationTime]" placeholder="请输入持续时间(分钟)" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="训练量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'taskWorkload', validatorRules.taskWorkload]" placeholder="请输入训练量"></a-input>
        </a-form-item>
        <a-form-item label="训练强度" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'taskIntensity', validatorRules.taskIntensity]" placeholder="请输入训练强度"></a-input>
        </a-form-item>
        <a-form-item label="恢复手段" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['taskRecoveryMethod']" rows="4" placeholder="请输入恢复手段"/>
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
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag'

  export default {
    name: "SportClassTaskModal",
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
        taskName:{rules: [{ required: true, message: '请输入训练任务名称!' }]},
        teacherNo:{rules: [{ required: true, message: '请输入发布教练!' }]},
        taskStartDate:{rules: [{ required: true, message: '请输入开始日期!' }]},
        taskEndDate:{rules: [{ required: true, message: '请输入结束日期!' }]},
        taskContent:{rules: [{ required: true, message: '请输入训练内容!' }]},
        taskMethod:{},
        taskDurationTime:{},
        taskWorkload:{},
        taskIntensity:{},
        taskRecoveryMethod:{},
        remark:{},
        },
        url: {
          add: "/edusport/sportClassTask/add",
          edit: "/edusport/sportClassTask/edit",
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
          this.form.setFieldsValue(pick(this.model,'sportClassId','taskName','teacherNo','taskStartDate','taskEndDate','taskContent','taskMethod','taskDurationTime','taskWorkload','taskIntensity','taskRecoveryMethod','remark'))
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
        this.form.setFieldsValue(pick(row,'sportClassId','taskName','teacherNo','taskStartDate','taskEndDate','taskContent','taskMethod','taskDurationTime','taskWorkload','taskIntensity','taskRecoveryMethod','remark'))
      },

      
    }
  }
</script>