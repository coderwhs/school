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

        <a-form-item label="指标类别" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag v-decorator="['indexCatId']" dict="tb_edu_athlete_selection_index_cat,index_cat_name,id" />
        </a-form-item>
        <a-form-item label="大类代码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'l1Code', validatorRules.l1Code]" placeholder="请输入大类代码"></a-input>
        </a-form-item>
        <a-form-item label="中类代码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'l2Code', validatorRules.l2Code]" placeholder="请输入中类代码"></a-input>
        </a-form-item>
        <a-form-item label="小类代码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'l3Code', validatorRules.l3Code]" placeholder="请输入小类代码"></a-input>
        </a-form-item>
        <a-form-item label="中文名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'cnName', validatorRules.cnName]" placeholder="请输入中文名称"></a-input>
        </a-form-item>
        <a-form-item label="英文名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'enName', validatorRules.enName]" placeholder="请输入英文名称"></a-input>
        </a-form-item>
        <a-form-item label="英文简称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'enShortName', validatorRules.enShortName]" placeholder="请输入英文简称"></a-input>
        </a-form-item>
        <a-form-item label="单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['unit']" :trigger-change="true" dictCode="unit_measurement" placeholder="请选择单位"/>
        </a-form-item>
        <a-form-item label="启用状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['enableStatus']" :trigger-change="true" dictCode="bill_state" placeholder="请选择启用状态"/>
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
    name: "AthleteSelectionIndexModal",
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
        indexCatId:{rules: [{ required: true, message: '请输入指标类别!' }]},
        l1Code:{rules: [{ required: true, message: '请输入大类代码!' }]},
        l2Code:{rules: [{ required: true, message: '请输入中类代码!' }]},
        l3Code:{rules: [{ required: true, message: '请输入小类代码!' }]},
        cnName:{rules: [{ required: true, message: '请输入中文名称!' }]},
        enName:{},
        enShortName:{},
        unit:{},
        enableStatus:{},
        },
        url: {
          add: "/edusport/athleteSelectionIndex/add",
          edit: "/edusport/athleteSelectionIndex/edit",
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
          this.form.setFieldsValue(pick(this.model,'indexCatId','l1Code','l2Code','l3Code','cnName','enName','enShortName','unit','enableStatus'))
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
        this.form.setFieldsValue(pick(row,'indexCatId','l1Code','l2Code','l3Code','cnName','enName','enShortName','unit','enableStatus'))
      },

      
    }
  }
</script>