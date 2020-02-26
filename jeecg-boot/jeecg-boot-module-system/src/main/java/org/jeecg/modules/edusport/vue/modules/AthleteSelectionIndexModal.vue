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
          <j-dict-select-tag type="list" v-decorator="['indexCatCode']" :trigger-change="true" dictCode="tb_edu_athlete_selection_index_cat,index_cat_name,index_cat_code" placeholder="请选择指标类别"/>
        </a-form-item>
        <a-form-item label="上级指标代码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag v-decorator="['parentCode']" dict="tb_edu_athlete_selection_index,cn_name,index_code" />
        </a-form-item>
        <a-form-item label="指标代码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'indexCode', validatorRules.indexCode]" placeholder="请输入指标代码"></a-input>
        </a-form-item>
        <a-form-item label="是否叶节点" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['isLeaf']" :trigger-change="true" dictCode="" placeholder="请选择是否叶节点"/>
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
          <a-input v-decorator="[ 'unit', validatorRules.unit]" placeholder="请输入单位"></a-input>
        </a-form-item>
        <a-form-item label="启用状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['enableStatus']" :trigger-change="true" dictCode="enable_status" placeholder="请选择启用状态"/>
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
        indexCatCode:{rules: [{ required: true, message: '请输入指标类别!' }]},
        parentCode:{rules: [{ required: true, message: '请输入上级指标代码!' }]},
        indexCode:{rules: [{ required: true, message: '请输入指标代码!' }]},
        isLeaf:{rules: [{ required: true, message: '请输入是否叶节点!' }]},
        cnName:{},
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
          this.form.setFieldsValue(pick(this.model,'indexCatCode','parentCode','indexCode','isLeaf','cnName','enName','enShortName','unit','enableStatus'))
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
        this.form.setFieldsValue(pick(row,'indexCatCode','parentCode','indexCode','isLeaf','cnName','enName','enShortName','unit','enableStatus'))
      },

      
    }
  }
</script>