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

        <a-form-item label="所属测试教练" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['testCoachId']" :trigger-change="true" dictCode="tb_edu_athlete_selection_test_coach,id,id" placeholder="请选择所属测试教练"/>
        </a-form-item>
        <a-form-item label="运动员" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['athleteId']" :trigger-change="true" dictCode="tb_edu_athlete,athlete_name,id" placeholder="请选择运动员"/>
        </a-form-item>
        <a-form-item label="性别" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'gender', validatorRules.gender]" placeholder="请输入性别"></a-input>
        </a-form-item>
        <a-form-item label="年级" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'grade', validatorRules.grade]" placeholder="请输入年级"></a-input>
        </a-form-item>
        <a-form-item label="出生年月" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择出生年月" v-decorator="[ 'birthDate', validatorRules.birthDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="年龄" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'age', validatorRules.age]" placeholder="请输入年龄" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="总成绩" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'score', validatorRules.score]" placeholder="请输入总成绩" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="测试等级评定" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'testGrade', validatorRules.testGrade]" placeholder="请输入测试等级评定"></a-input>
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
    name: "AthleteSelectionTestAthleteScoreModal",
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
        testCoachId:{rules: [{ required: true, message: '请输入所属测试教练!' }]},
        athleteId:{rules: [{ required: true, message: '请输入运动员!' }]},
        gender:{rules: [{ required: true, message: '请输入性别!' }]},
        grade:{rules: [{ required: true, message: '请输入年级!' }]},
        birthDate:{rules: [{ required: true, message: '请输入出生年月!' }]},
        age:{rules: [{ required: true, message: '请输入年龄!' }]},
        score:{rules: [{ required: true, message: '请输入总成绩!' }]},
        testGrade:{rules: [{ required: true, message: '请输入测试等级评定!' }]},
        },
        url: {
          add: "/edusport/athleteSelectionTestAthleteScore/add",
          edit: "/edusport/athleteSelectionTestAthleteScore/edit",
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
          this.form.setFieldsValue(pick(this.model,'testCoachId','athleteId','gender','grade','birthDate','age','score','testGrade'))
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
        this.form.setFieldsValue(pick(row,'testCoachId','athleteId','gender','grade','birthDate','age','score','testGrade'))
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