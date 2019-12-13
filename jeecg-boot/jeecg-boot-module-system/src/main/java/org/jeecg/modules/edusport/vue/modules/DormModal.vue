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

        <a-form-item label="宿舍楼名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'dormBuildingName', validatorRules.dormBuildingName]" placeholder="请输入宿舍楼名称"></a-input>
        </a-form-item>
        <a-form-item label="房间号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'dormNo', validatorRules.dormNo]" placeholder="请输入房间号"></a-input>
        </a-form-item>
        <a-form-item label="床位数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'bedNum', validatorRules.bedNum]" placeholder="请输入床位数" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="宿舍电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'dormTel', validatorRules.dormTel]" placeholder="请输入宿舍电话"></a-input>
        </a-form-item>
        <a-form-item label="管理员" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'dormAdmin', validatorRules.dormAdmin]" placeholder="请输入管理员"></a-input>
        </a-form-item>
        <a-form-item label="管理员电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'dormAdminTel', validatorRules.dormAdminTel]" placeholder="请输入管理员电话"></a-input>
        </a-form-item>
        <a-form-item label="地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'dormAddress', validatorRules.dormAddress]" placeholder="请输入地址"></a-input>
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'

  export default {
    name: "DormModal",
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
        dormBuildingName:{rules: [{ required: true, message: '请输入宿舍楼名称!' }]},
        dormNo:{rules: [{ required: true, message: '请输入房间号!' }]},
        bedNum:{rules: [{ required: true, message: '请输入床位数!' }]},
        dormTel:{rules: [{ required: true, message: '请输入宿舍电话!' }]},
        dormAdmin:{rules: [{ required: true, message: '请输入管理员!' }]},
        dormAdminTel:{rules: [{ required: true, message: '请输入管理员电话!' }]},
        dormAddress:{rules: [{ required: true, message: '请输入地址!' }]},
        },
        url: {
          add: "/edusport/dorm/add",
          edit: "/edusport/dorm/edit",
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
          this.form.setFieldsValue(pick(this.model,'dormBuildingName','dormNo','bedNum','dormTel','dormAdmin','dormAdminTel','dormAddress'))
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
        this.form.setFieldsValue(pick(row,'dormBuildingName','dormNo','bedNum','dormTel','dormAdmin','dormAdminTel','dormAddress'))
      },

      
    }
  }
</script>