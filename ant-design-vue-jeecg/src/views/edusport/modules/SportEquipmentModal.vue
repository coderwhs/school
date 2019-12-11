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
        <a-form-item label="器材分类" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['equipmentType']" :trigger-change="true" dictCode="equipment_type" placeholder="请选择器材分类"/>
        </a-form-item>
        <a-form-item label="器材序列号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'equipmentSn', validatorRules.equipmentSn]" placeholder="请输入器材序列号"></a-input>
        </a-form-item>
        <a-form-item label="器材名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'equipmentName', validatorRules.equipmentName]" placeholder="请输入器材名称"></a-input>
        </a-form-item>
        <a-form-item label="器材价格" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'equipmentPrice', validatorRules.equipmentPrice]" placeholder="请输入器材价格"></a-input>
        </a-form-item>
        <a-form-item label="存放地点" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'storeLocation', validatorRules.storeLocation]" placeholder="请输入存放地点"></a-input>
        </a-form-item>
        <a-form-item label="库存数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'inventoryQty', validatorRules.inventoryQty]" placeholder="请输入库存数量" style="width: 100%"/>
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
    name: "SportEquipmentModal",
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
        equipmentType:{rules: [{ required: true, message: '请输入器材分类!' }]},
        equipmentSn:{rules: [{ required: true, message: '请输入器材序列号!' }]},
        equipmentName:{rules: [{ required: true, message: '请输入器材名称!' }]},
        equipmentPrice:{rules: [{ required: true, message: '请输入器材价格!' }]},
        storeLocation:{rules: [{ required: true, message: '请输入存放地点!' }]},
        inventoryQty:{rules: [{ required: true, message: '请输入库存数量!' }]},
        },
        url: {
          add: "/edusport/sportEquipment/add",
          edit: "/edusport/sportEquipment/edit",
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
          this.form.setFieldsValue(pick(this.model,'sportCode','equipmentType','equipmentSn','equipmentName','equipmentPrice','storeLocation','inventoryQty'))
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
        this.form.setFieldsValue(pick(row,'sportCode','equipmentType','equipmentSn','equipmentName','equipmentPrice','storeLocation','inventoryQty'))
      },

      
    }
  }
</script>