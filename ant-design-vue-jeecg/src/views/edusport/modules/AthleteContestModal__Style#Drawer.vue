<template>
  <a-drawer
    :title="title"
    :width="width"
    placement="right"
    :closable="false"
    @close="close"
    :visible="visible">
  
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item label="运动员" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'studentNo', validatorRules.studentNo]" placeholder="请输入运动员"></a-input>
        </a-form-item>
        <a-form-item label="参赛名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'contestName', validatorRules.contestName]" placeholder="请输入参赛名称"></a-input>
        </a-form-item>
        <a-form-item label="参赛日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择参赛日期" v-decorator="[ 'contestDate', validatorRules.contestDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="参赛地点" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'contestAddress', validatorRules.contestAddress]" placeholder="请输入参赛地点"></a-input>
        </a-form-item>
        <a-form-item label="参赛项目" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'contestSport', validatorRules.contestSport]" placeholder="请输入参赛项目"></a-input>
        </a-form-item>
        <a-form-item label="参赛小项" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'contestEvent', validatorRules.contestEvent]" placeholder="请输入参赛小项"></a-input>
        </a-form-item>
        <a-form-item label="参赛成绩" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'contestScore', validatorRules.contestScore]" placeholder="请输入参赛成绩"></a-input>
        </a-form-item>
        <a-form-item label="参赛名次" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'contestResult', validatorRules.contestResult]" placeholder="请输入参赛名次"></a-input>
        </a-form-item>
        <a-form-item label="所在单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'studentDepartment', validatorRules.studentDepartment]" placeholder="请输入所在单位"></a-input>
        </a-form-item>
        <a-form-item label="授予技术等级" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['awardedTechGrade']" :trigger-change="true" dictCode="athlete_tech_grade" placeholder="请选择授予技术等级"/>
        </a-form-item>
        <a-form-item label="授予单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'awardedDepartment', validatorRules.awardedDepartment]" placeholder="请输入授予单位"></a-input>
        </a-form-item>
        <a-form-item label="授予日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择授予日期" v-decorator="[ 'awardedDate', validatorRules.awardedDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        
      </a-form>
    </a-spin>
    <a-button type="primary" @click="handleOk">确定</a-button>
    <a-button type="primary" @click="handleCancel">取消</a-button>
  </a-drawer>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JDate from '@/components/jeecg/JDate'  
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  
  export default {
    name: "AthleteContestModal",
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
        studentNo:{rules: [{ required: true, message: '请输入运动员!' }]},
        contestName:{rules: [{ required: true, message: '请输入参赛名称!' }]},
        contestDate:{rules: [{ required: true, message: '请输入参赛日期!' }]},
        contestAddress:{rules: [{ required: true, message: '请输入参赛地点!' }]},
        contestSport:{rules: [{ required: true, message: '请输入参赛项目!' }]},
        contestEvent:{rules: [{ required: true, message: '请输入参赛小项!' }]},
        contestScore:{rules: [{ required: true, message: '请输入参赛成绩!' }]},
        contestResult:{},
        studentDepartment:{},
        awardedTechGrade:{rules: [{ required: true, message: '请输入授予技术等级!' }]},
        awardedDepartment:{},
        awardedDate:{},
        },
        url: {
          add: "/edusport/athleteContest/add",
          edit: "/edusport/athleteContest/edit",
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
          this.form.setFieldsValue(pick(this.model,'studentNo','contestName','contestDate','contestAddress','contestSport','contestEvent','contestScore','contestResult','studentDepartment','awardedTechGrade','awardedDepartment','awardedDate'))
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
        this.form.setFieldsValue(pick(row,'studentNo','contestName','contestDate','contestAddress','contestSport','contestEvent','contestScore','contestResult','studentDepartment','awardedTechGrade','awardedDepartment','awardedDate'))
      }
      
    }
  }
</script>

<style lang="less" scoped>
/** Button按钮间距 */
  .ant-btn {
    margin-left: 30px;
    margin-bottom: 30px;
    float: right;
  }
</style>