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
          <j-form-container disabled>
            <j-dict-select-tag type="list" v-decorator="[ 'groupId', validatorRules.groupId]"  :trigger-change="true" dictCode="tb_edu_athlete_selection_group,group_name,id" placeholder="请选择所属组别"/>
          </j-form-container>
        </a-form-item>
        <a-form-item label="最小分值" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'minData', validatorRules.minData]" placeholder="请输入最小分值" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="最大分值" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'maxData', validatorRules.maxData]" placeholder="请输入最大分值" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="等级" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="[ 'rating', validatorRules.rating]" :trigger-change="true" dictCode="index_rating" placeholder="请选择等级"/>
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"

  export default {
    name: "AthleteSelectionGroupRatingModal",
    components: {
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
        groupId:{rules: [{ required: true, message: '请输入所属组别!' }]},
        minData:{rules: [{ required: true, message: '请输入最小分值!' }]},
        maxData:{rules: [{ required: true, message: '请输入最大分值!' }]},
        rating:{rules: [{ required: true, message: '请输入等级!' }]},
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
      add(groupId) {
        this.hiding = true;
        if (groupId) {
          this.groupId = groupId;
          this.edit({groupId}, '');
        } else {
          this.$message.warning("请选择一个测试组别");
        }
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'groupId','minData','maxData','rating'))
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
        this.form.setFieldsValue(pick(row,'groupId','minData','maxData','rating'))
      },

      
    }
  }
</script>