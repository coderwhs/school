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
          <j-search-select-tag v-decorator="['athleteId']" dict="tb_edu_athlete,athlete_name,id" />
        </a-form-item>
        <a-form-item label="所属测试" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag v-decorator="['testId']" dict="tb_edu_athlete_selection_test,test_name,id" />
        </a-form-item>
        <a-form-item label="测试组别" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag v-decorator="['groupId']" dict="tb_edu_athlete_selection_group,group_name,id" />
        </a-form-item>
        <a-form-item label="小项" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag v-decorator="['eventCode']" dict="tb_edu_sport,sport_name,id" />
        </a-form-item>
        <a-form-item label="指标" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag v-decorator="['indexCode']" dict="tb_edu_athlete_selection_index,cn_name,id" />
        </a-form-item>
        <a-form-item label="测试值" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'testValue', validatorRules.testValue]" placeholder="请输入测试值"></a-input>
        </a-form-item>
        <a-form-item label="得分" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'testScore', validatorRules.testScore]" placeholder="请输入得分" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="测试成绩" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'athleteScoreId', validatorRules.athleteScoreId]" placeholder="请输入测试成绩"></a-input>
        </a-form-item>
        <a-form-item label="测试等级评定" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['testGrade']" :trigger-change="true" dictCode="tb_edu_athlete_selection_group_rating,rating,id" placeholder="请选择测试等级评定"/>
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
    name: "AthleteSelectionAthleteScoreDetailModal",
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
        athleteId:{},
        testId:{},
        groupId:{},
        eventCode:{},
        indexCode:{},
        testValue:{rules: [{ required: true, message: '请输入测试值!' }]},
        testScore:{},
        athleteScoreId:{rules: [{ required: true, message: '请输入测试成绩!' }]},
        testGrade:{},
        },
        url: {
          add: "/edusport/athleteSelectionAthleteScoreDetail/add",
          edit: "/edusport/athleteSelectionAthleteScoreDetail/edit",
        }
     
      }
    },
    created () {
    },
    methods: {
      add(athleteScoreId){
        this.hiding = true;
        if (athleteScoreId) {
          this.athleteScoreId = athleteScoreId;
          this.edit({athleteScoreId}, '');
        } else {
          this.$message.warning("请选择一个运动员信息");
        }
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'athleteId','testId','groupId','eventCode','indexCode','testValue','testScore','athleteScoreId','testGrade'))
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
        this.form.setFieldsValue(pick(row,'athleteId','testId','groupId','eventCode','indexCode','testValue','testScore','athleteScoreId','testGrade'))
      },

      
    }
  }
</script>