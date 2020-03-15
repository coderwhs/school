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

        <a-form-item label="指标类别代码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'indexCatCode', validatorRules.indexCatCode]" placeholder="请输入指标类别代码"></a-input>
        </a-form-item>
        <a-form-item label="指标类别名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'indexCatName', validatorRules.indexCatName]" placeholder="请输入指标类别名称"></a-input>
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import { duplicateCheck } from '@/api/api'
  import pick from 'lodash.pick'

  export default {
    name: "AthleteSelectionIndexCatModal",
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
        indexCatCode:{rules: [{ required: true, message: '请输入指标类别代码!' }, {validator: this.validateUnionDuplicateCheck}]},
        indexCatName:{rules: [{ required: true, message: '请输入指标类别名称!' }, {validator: this.validateUnionDuplicateCheck}]},
        },
        url: {
          add: "/edusport/athleteSelectionIndexCat/add",
          edit: "/edusport/athleteSelectionIndexCat/edit",
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
          this.form.setFieldsValue(pick(this.model,'indexCatCode','indexCatName'))
        })
      },
      validateUnionDuplicateCheck(rule, value, callback) {
        // 重复校验
        var params = {
          tableName: 'tb_edu_athlete_selection_index_cat',
          fieldName: "concat_ws(',', index_cat_code, index_cat_name)",
          fieldVal: this.form.getFieldValue("indexCatCode") + "," + this.form.getFieldValue("indexCatName"),
          dataId: this.model.id
        }
        duplicateCheck(params).then((res) => {
          if (res.success) {
            this.form.setFields({indexCatCode: {value: this.form.getFieldValue("indexCatCode")}});
            this.form.setFields({indexCatName: {value: this.form.getFieldValue("indexCatName")}});
            callback()
          } else {
            callback("该值不可用，系统中已存在您输入的（指标类别代码+指标类别名称）组合！")
          }
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
        this.form.setFieldsValue(pick(row,'indexCatCode','indexCatName'))
      },

      
    }
  }
</script>