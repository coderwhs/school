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

        <a-form-item label="测试成绩id" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['athleteScoreId']" :trigger-change="true" dictCode="tb_edu_athlete_selection_test_athlete_score,id,id" placeholder="请选择测试成绩id"/>
        </a-form-item>
        <a-form-item label="所属组别" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['groupId']" :trigger-change="true" dictCode="tb_edu_athlete_selection_group,group_name,id" placeholder="请选择所属组别"/>
        </a-form-item>
        <a-form-item label="指标" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['indexCode']" :trigger-change="true" dictCode="tb_edu_athlete_selection_index,index_name,index_code" placeholder="请选择指标"/>
        </a-form-item>
        <a-form-item label="测试值" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'testValue', validatorRules.testValue]" placeholder="请输入测试值" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="得分" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'testScore', validatorRules.testScore]" placeholder="请输入得分" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="测试等级评定" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'testGrade', validatorRules.testGrade]" placeholder="请输入测试等级评定"></a-input>
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
    name: "AthleteSelectionTestAthleteScoreDetailModal",
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
        athleteScoreId:{rules: [{ required: true, message: '请输入测试成绩id!' }]},
        groupId:{rules: [{ required: true, message: '请输入所属组别!' }]},
        indexCode:{rules: [{ required: true, message: '请输入指标!' }]},
        testValue:{rules: [{ required: true, message: '请输入测试值!' }]},
        testScore:{rules: [{ required: true, message: '请输入得分!' }]},
        testGrade:{rules: [{ required: true, message: '请输入测试等级评定!' }]},
        },
        url: {
          add: "/edusport/athleteSelectionTestAthleteScoreDetail/add",
          edit: "/edusport/athleteSelectionTestAthleteScoreDetail/edit",
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
          this.form.setFieldsValue(pick(this.model,'athleteScoreId','groupId','indexCode','testValue','testScore','testGrade'))
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
        this.form.setFieldsValue(pick(row,'athleteScoreId','groupId','indexCode','testValue','testScore','testGrade'))
      },

      
    }
  }
</script>