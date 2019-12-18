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

        <a-form-item label="所属组别" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag v-decorator="['groupId']" dict="tb_edu_athlete_selection_group,group_name,id" />
        </a-form-item>
        <a-form-item label="最大分值" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'maxData', validatorRules.maxData]" placeholder="请输入最大分值" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="最小分值" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'minData', validatorRules.minData]" placeholder="请输入最小分值" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="等级" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'rating', validatorRules.rating]" placeholder="请输入等级"></a-input>
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag'

  export default {
    name: "AthleteSelectionGroupRatingModal",
    components: { 
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
        groupId:{rules: [{ required: true, message: '请输入所属组别!' }]},
        maxData:{},
        minData:{},
        rating:{},
        },
        url: {
          add: "/edusport/athleteSelectionGroupRating/add",
          edit: "/edusport/athleteSelectionGroupRating/edit",
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
          this.form.setFieldsValue(pick(this.model,'groupId','maxData','minData','rating'))
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
        this.form.setFieldsValue(pick(row,'groupId','maxData','minData','rating'))
      },

      
    }
  }
</script>