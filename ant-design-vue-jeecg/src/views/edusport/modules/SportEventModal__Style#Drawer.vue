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

        <a-form-item label="大项" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag placeholder="请选择大项" v-decorator="[ 'sportCode', validatorRules.sportCode]" dict="tb_edu_sport,sport_name,sport_code" />
        </a-form-item>
        <a-form-item label="分项" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag placeholder="请选择分项" v-decorator="[ 'disciplinesCode', validatorRules.disciplinesCode]" dict="tb_edu_sport_disciplines,disciplines_name,disciplines_code" />
        </a-form-item>
        <a-form-item label="小项代码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'eventCode', validatorRules.eventCode]" placeholder="请输入小项代码"></a-input>
        </a-form-item>
        <a-form-item label="小项名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'eventName', validatorRules.eventName]" placeholder="请输入小项名称"></a-input>
        </a-form-item>
        <a-form-item label="启用状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['enableStatus']" :trigger-change="true" dictCode="enable_status" placeholder="请选择启用状态"/>
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
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag.vue'
  
  export default {
    name: "SportEventModal",
    components: { 
      JDictSelectTag,
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
        sportCode:{rules: [{ required: true, message: '请输入大项代码!' }]},
        disciplinesCode:{rules: [{ required: true, message: '请输入分项代码!' }]},
        eventCode:{rules: [{ required: true, message: '请输入小项代码!' }]},
        eventName:{rules: [{ required: true, message: '请输入小项名称!' }]},
        enableStatus:{rules: [{ required: true, message: '请输入启用状态!' }]},
        },
        url: {
          add: "/edusport/sportEvent/add",
          edit: "/edusport/sportEvent/edit",
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
          this.form.setFieldsValue(pick(this.model,'sportCode','disciplinesCode','eventCode','eventName','enableStatus'))
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
        this.form.setFieldsValue(pick(row,'sportCode','disciplinesCode','eventCode','eventName','enableStatus'))
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