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

        <a-form-item label="运动员" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag type="checkbox" v-decorator="['athleteId']" dict="tb_edu_athlete,athlete_name,id" />
        </a-form-item>
        <a-form-item label="教练员" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag v-decorator="['coachId']" dict="tb_edu_coach,coach_name,coach_no" />
        </a-form-item>
        <a-form-item label="出生日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择出生日期" v-decorator="[ 'birthday', validatorRules.birthday]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="性别" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['gender']" :trigger-change="true" dictCode="sex" placeholder="请选择性别"/>
        </a-form-item>
        <a-form-item label="年级" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'grade', validatorRules.grade]" placeholder="请输入年级"></a-input>
        </a-form-item>
        <a-form-item label="测试日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择测试日期" v-decorator="[ 'testDate', validatorRules.testDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="所属测试" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag v-decorator="['testId']" dict="tb_edu_athlete_selection_test,test_name,id" disabled="disabled"/>
        </a-form-item>
        <a-form-item label="测试组别" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag v-decorator="['groupId']" dict="tb_edu_athlete_selection_group,group_name,id" disabled="disabled"/>
        </a-form-item>
       <a-form-item label="小项" :labelCol="labelCol" :wrapperCol="wrapperCol">
         <j-multi-select-tag type="checkbox" v-decorator="['eventCode']" :trigger-change="true" dictCode="tb_edu_sport_small,event_name,event_code" placeholder="请选择小项"/>
       </a-form-item>
        <a-form-item label="得分" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'testScore', validatorRules.testScore]" placeholder="请输入得分" style="width: 100%" disabled="disabled"/>
        </a-form-item>
        <a-form-item label="测试等级评定" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['testGrade']" :trigger-change="true" dictCode="tb_edu_athlete_selection_group_rating,rating,id" placeholder="请选择测试等级评定" disabled="disabled"/>
        </a-form-item>
        <a-form-item label="审核状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['auditState']" :trigger-change="true" dictCode="audit_state" placeholder="请选择审核状态" disabled="disabled"/>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import JMultiSelectTag from "@/components/dict/JMultiSelectTag"
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag'
  import JDate from '@/components/jeecg/JDate'  
  export default {
    name: "AthleteSelectionAthleteScoreModal",
    components: { 
      JDate,
      JDictSelectTag,
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
        athleteId:{},
        coachId:{},
        birthday:{},
        gender:{},
        grade:{},
        testDate:{},
        testId:{},
        groupId:{},
        // eventCode:{},
        testScore:{},
        testGrade:{},
        auditState:{},
        },
        url: {
          add: "/edusport/athleteSelectionAthleteScore/add",
          edit: "/edusport/athleteSelectionAthleteScore/edit",
        }
     
      }
    },
    created () {
    },
    methods: {
      add(testId,groupId){
        this.hiding = true;
        if (testId) {
          this.edit({testId,groupId}, '');
        } else {
          this.$message.warning("请选择一条测试记录信息");
        }
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'athleteId','coachId','birthday','gender','grade','testDate','testId','groupId','testScore','testGrade','auditState'))
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
        this.form.setFieldsValue(pick(row,'athleteId','coachId','birthday','gender','grade','testDate','testId','groupId','testScore','testGrade','auditState'))
      },

      
    }
  }
</script>