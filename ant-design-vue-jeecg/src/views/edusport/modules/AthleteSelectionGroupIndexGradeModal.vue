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
        <a-form-item label="指标" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag v-decorator="['indexId']" dict="tb_edu_athlete_selection_index,cn_name,l3_code" />
        </a-form-item>
        <a-form-item label="性别" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['gender']" :trigger-change="true" dictCode="sex" placeholder="请选择性别"/>
        </a-form-item>
        <a-form-item label="起始年龄" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'startAge', validatorRules.startAge]" placeholder="请输入起始年龄" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="截止年龄" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'endAge', validatorRules.endAge]" placeholder="请输入截止年龄" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="最小数值" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'minData', validatorRules.minData]" placeholder="请输入最小数值" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="最大数值" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'maxData', validatorRules.maxData]" placeholder="请输入最大数值" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="得分" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'score', validatorRules.score]" placeholder="请输入得分" style="width: 100%"/>
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
    name: "AthleteSelectionGroupIndexGradeModal",
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
        groupId:{rules: [{ required: true, message: '请输入所属组别!' }]},
        indexId:{rules: [{ required: true, message: '请输入指标!' }]},
        gender:{rules: [{ required: true, message: '请输入性别!' }]},
        startAge:{rules: [{ required: true, message: '请输入起始年龄!' }]},
        endAge:{rules: [{ required: true, message: '请输入截止年龄!' }]},
        minData:{rules: [{ required: true, message: '请输入最小数值!' }]},
        maxData:{rules: [{ required: true, message: '请输入最大数值!' }]},
        score:{rules: [{ required: true, message: '请输入得分!' }]},
        },
        url: {
          add: "/edusport/athleteSelectionGroupIndexGrade/add",
          edit: "/edusport/athleteSelectionGroupIndexGrade/edit",
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
          this.form.setFieldsValue(pick(this.model,'groupId','indexId','gender','startAge','endAge','minData','maxData','score'))
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
        this.form.setFieldsValue(pick(row,'groupId','indexId','gender','startAge','endAge','minData','maxData','score'))
      },

      
    }
  }
</script>