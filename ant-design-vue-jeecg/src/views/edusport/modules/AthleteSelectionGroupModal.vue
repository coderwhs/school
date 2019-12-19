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

        <a-form-item label="组别名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'groupName', validatorRules.groupName]" placeholder="请输入组别名称"></a-input>
        </a-form-item>
        <a-form-item label="大项" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag v-decorator="['sportCode']" dict="tb_edu_sport,sport_name,sport_code" placeholder="请选择大小项" @change="handleValueChange"/>
        </a-form-item>
        <a-form-item label="小项" :labelCol="labelCol" :wrapperCol="wrapperCol" >
          <j-multi-select-tag type="list_multi" v-decorator="['eventCodes']" :trigger-change="true" dictCode="tb_edu_sport,sport_name,sport_code" :async="true" placeholder="请选择小项"/>
<!--          <j-multi-select-tag type="checkbox" v-decorator="['eventCodes']" :trigger-change="true" dictCode="tb_edu_sport,sport_name,sport_code" :async="true" placeholder="请选择小项"/>-->
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JMultiSelectTag from "@/components/dict/JMultiSelectTag"
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag'

  export default {
    name: "AthleteSelectionGroupModal",
    components: { 
      JMultiSelectTag,
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
        groupName:{},
        sportCode:{},
        eventCodes:{},
        },
        url: {
          add: "/edusport/athleteSelectionGroup/add",
          edit: "/edusport/athleteSelectionGroup/edit",
        }
     
      }
    },
    created () {
    },
    methods: {

      /** 当选项被改变时，联动其他组件 */
      handleValueChange(selectedValue) {
        // alert("value = " + selectedValue);
        this.eventCodes = this.request(selectedValue);
      },

      request(selectedValue) {
        // return this.$refs.dictOptions.filter(i => i.sportCode.substring(0,1) === selectedValue)
      },

      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'groupName','sportCode','eventCodes'))
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
        this.form.setFieldsValue(pick(row,'groupName','sportCode','eventCodes'))
      },

    }
  }
</script>