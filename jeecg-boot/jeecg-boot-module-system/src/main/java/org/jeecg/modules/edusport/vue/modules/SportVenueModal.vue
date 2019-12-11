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

        <a-form-item label="运动项目" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag v-decorator="['sportCode']" dict="tb_edu_sport,sport_name,sport_code" />
        </a-form-item>
        <a-form-item label="场馆名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'venueName', validatorRules.venueName]" placeholder="请输入场馆名称"></a-input>
        </a-form-item>
        <a-form-item label="场馆类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['venueType']" :trigger-change="true" dictCode="venue_type" placeholder="请选择场馆类型"/>
        </a-form-item>
        <a-form-item label="容纳人数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'venueCapacity', validatorRules.venueCapacity]" placeholder="请输入容纳人数"></a-input>
        </a-form-item>
        <a-form-item label="场馆地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'venueAddress', validatorRules.venueAddress]" placeholder="请输入场馆地址"></a-input>
        </a-form-item>
        <a-form-item label="管理员" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'venueAdmin', validatorRules.venueAdmin]" placeholder="请输入管理员"></a-input>
        </a-form-item>
        <a-form-item label="联系电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'venueAdminTel', validatorRules.venueAdminTel]" placeholder="请输入联系电话"></a-input>
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag'

  export default {
    name: "SportVenueModal",
    components: { 
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
        sportCode:{rules: [{ required: true, message: '请输入运动项目!' }]},
        venueName:{rules: [{ required: true, message: '请输入场馆名称!' }]},
        venueType:{rules: [{ required: true, message: '请输入场馆类型!' }]},
        venueCapacity:{rules: [{ required: true, message: '请输入容纳人数!' }]},
        venueAddress:{rules: [{ required: true, message: '请输入场馆地址!' }]},
        venueAdmin:{rules: [{ required: true, message: '请输入管理员!' }]},
        venueAdminTel:{rules: [{ required: true, message: '请输入联系电话!' }]},
        },
        url: {
          add: "/edusport/sportVenue/add",
          edit: "/edusport/sportVenue/edit",
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
          this.form.setFieldsValue(pick(this.model,'sportCode','venueName','venueType','venueCapacity','venueAddress','venueAdmin','venueAdminTel'))
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
        this.form.setFieldsValue(pick(row,'sportCode','venueName','venueType','venueCapacity','venueAddress','venueAdmin','venueAdminTel'))
      },

      
    }
  }
</script>