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

          <a-row>
           <a-col :span="12">
             <a-form-item label="当前步骤" :labelCol="labelCol" :wrapperCol="wrapperCol">
               <a-input v-decorator="[ 'name', validatorRules.name]" placeholder="请输入当前步骤" disabled="disabled"></a-input>
             </a-form-item>
           </a-col>
           <a-col :span="12">
             <a-form-item label="流程名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
               <a-input v-decorator="[ 'category', validatorRules.category]" placeholder="请输入流程名称" disabled="disabled"></a-input>
             </a-form-item>
           </a-col>
         </a-row>
         <a-row>
           <a-col :span="12">
             <a-form-item label="申请理由" :labelCol="labelCol" :wrapperCol="wrapperCol">
               <!-- <a-input v-decorator="[ 'delegation', validatorRules.delegation]" placeholder="请输入申请理由"></a-input> -->
               <a-textarea v-decorator="['delegation']" rows="4" placeholder="请输入申请理由" disabled="disabled"/>
             </a-form-item>
           </a-col>
           <a-col :span="12">
             <a-form-item label="审批意见" :labelCol="labelCol" :wrapperCol="wrapperCol">
               <a-textarea v-decorator="['description']" rows="4" placeholder="请输入审批意见"/>
             </a-form-item>
           </a-col>
         </a-row>
         <a-row>
           <a-col :span="12">
             <a-form-item label="任务描述" :labelCol="labelCol" :wrapperCol="wrapperCol">
               <a-input v-decorator="[ 'owner', validatorRules.owner]" placeholder="请输入owner" disabled="disabled"></a-input>
             </a-form-item>
           </a-col>
           <a-col :span="12">
             <a-form-item label="是否同意" :labelCol="labelCol" :wrapperCol="wrapperCol">
               <j-dict-select-tag type="radio" v-decorator="['isAgree']" :trigger-change="true" dictCode="is_agree" placeholder="请选择是否同意"/>
             </a-form-item>
           </a-col>
         </a-row>
         <a-row>
           <a-col :span="12">
             <a-form-item label="申请人" :labelCol="labelCol" :wrapperCol="wrapperCol">
               <a-input v-decorator="[ 'createBy', validatorRules.createBy]" placeholder="请输入申请人" disabled="disabled"></a-input>
             </a-form-item>
           </a-col>
           <a-col :span="12">
             <a-form-item label="申请时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
               <j-date placeholder="请选择申请时间" v-decorator="[ 'createTime', validatorRules.createTime]" disabled="disabled" :trigger-change="true" style="width: 100%"/>
             </a-form-item>
           </a-col>
         </a-row>
         <a-row>
           <a-col :span="12">
             <a-form-item label="审核人" :labelCol="labelCol" :wrapperCol="wrapperCol">
               <a-input v-decorator="[ 'updateBy', validatorRules.updateBy]" placeholder="请输入审核人" disabled="disabled"></a-input>
             </a-form-item>
           </a-col>
           <a-col :span="12">
             <a-form-item label="审核时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
               <j-date placeholder="请选择审核时间" v-decorator="[ 'updateTime', validatorRules.updateTime]" disabled="disabled" :trigger-change="true" style="width: 100%"/>
             </a-form-item>
           </a-col>
         </a-row>

      </a-form>
    </a-spin>
    <img alt="流程图" style="width: 100% height: 50%" :src="previewImage"/>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JDate from '@/components/jeecg/JDate'  
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import { getUploadFileList,getFilePaths } from '@/utils/commonUploadFile.js'
  import JUpload from '@/components/jeecg/JUpload'

  export default {
    name: "TactRuTaskModal",
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
        name:{},
        description:{},
        owner:{},
        assignee:{},
        delegation:{},
        createBy:{},
        createTime:{},
        updateBy:{},
        updateTime:{},
        isAgree:{'isAgree':'1'},
        },
         previewVisible: false,
         previewImage: '',
         confirmLoading: false,
        url: {
          add: "/edusport/tactRuTask/add",
          edit: "/edusport/tactRuTask/edit",
          processDiagram:"/process/processDiagram",
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
        this.previewImage = window._CONFIG['domianURL'] + this.url.processDiagram + "?procInstId=" + record.procInstId;
        this.visible = true;
        this.previewVisible = true;
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'name','category','description','owner','assignee','delegation','createBy','createTime','updateBy','updateTime','isAgree'))
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
        this.form.setFieldsValue(pick(row,'name','description','category','owner','assignee','delegation','createBy','createTime','updateBy','updateTime','isAgree'))
      },
       handlePicCancel() {
         this.previewVisible = false
         this.previewImage=''
       },
      
    }
  }
</script>