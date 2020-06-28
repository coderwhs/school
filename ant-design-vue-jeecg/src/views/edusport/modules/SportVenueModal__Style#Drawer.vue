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
        <a-form-item label="所有权" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'venueOwnership', validatorRules.venueOwnership]" placeholder="请输入所有权"></a-input>
        </a-form-item>
        <a-form-item label="使用权" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'venueUseRight', validatorRules.venueUseRight]" placeholder="请输入使用权"></a-input>
        </a-form-item>
        <a-form-item label="附件" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-editor  v-decorator="[ 'venueDoc', validatorRules.venueDoc]" placeholder="请输入附件"/>
        </a-form-item>
        <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-editor v-decorator="[ 'venueRemark', validatorRules.venueRemark]" placeholder="请输入备注"/>
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
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import JEditor from '@/components/jeecg/JEditor'
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag'

  export default {
    name: "SportVenueModal",
    components: { 
      JDictSelectTag,
      JEditor,
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
          sportCode:{rules: [{ required: true, message: '请输入运动项目!' }]},
          venueName:{rules: [{ required: true, message: '请输入场馆名称!' }]},
          venueType:{rules: [{ required: true, message: '请输入场馆类型!' }]},
          venueCapacity:{rules: [{ required: false, message: '请输入容纳人数!' }]},
          venueAddress:{rules: [{ required: true, message: '请输入场馆地址!' }]},
          venueAdmin:{rules: [{ required: true, message: '请输入管理员!' }]},
          venueAdminTel:{rules: [{ required: true, message: '请输入联系电话!' }]},
          venueOwnership:{rules: [{ required: false, message: '请输入所有权!' }]},
          venueUseRight:{rules: [{ required: false, message: '请输入使用权!' }]},
          venueDoc:{trigger:'input', rules: [{ required: false, message: '请输入附件!' }]},
          venueRemark:{trigger:'input', rules: [{ required: false, message: '请输入备注!' }]},
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
          this.form.setFieldsValue(pick(this.model,'sportCode','venueName','venueType','venueCapacity','venueAddress','venueAdmin','venueAdminTel','venueOwnership','venueUseRight','venueDoc','venueRemark'))
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
        this.form.setFieldsValue(pick(this.model,'sportCode','venueName','venueType','venueCapacity','venueAddress','venueAdmin','venueAdminTel','venueOwnership','venueUseRight','venueDoc','venueRemark'))
      },
      // 根据屏幕变化,设置抽屉尺寸
      resetScreenSize(){
        let screenWidth = document.body.clientWidth;
        if(screenWidth < 500){
          this.drawerWidth = screenWidth;
        }else{
          this.drawerWidth = 800;
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