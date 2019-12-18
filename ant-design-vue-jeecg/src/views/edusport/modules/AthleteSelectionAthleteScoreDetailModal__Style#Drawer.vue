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

        <a-form-item label="测试成绩" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'athleteScoreId', validatorRules.athleteScoreId]" placeholder="请输入测试成绩"></a-input>
        </a-form-item>
        <a-form-item label="运动员" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'athleteId', validatorRules.athleteId]" placeholder="请输入运动员"></a-input>
        </a-form-item>
        <a-form-item label="所属测试" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'testId', validatorRules.testId]" placeholder="请输入所属测试"></a-input>
        </a-form-item>
        <a-form-item label="测试组别" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'groupId', validatorRules.groupId]" placeholder="请输入测试组别"></a-input>
        </a-form-item>
        <a-form-item label="小项" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'eventCode', validatorRules.eventCode]" placeholder="请输入小项"></a-input>
        </a-form-item>
        <a-form-item label="指标" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'indexCode', validatorRules.indexCode]" placeholder="请输入指标"></a-input>
        </a-form-item>
        <a-form-item label="测试值" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'testValue', validatorRules.testValue]" placeholder="请输入测试值"></a-input>
        </a-form-item>
        <a-form-item label="得分" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'testScore', validatorRules.testScore]" placeholder="请输入得分" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="测试等级评定" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['testGrade']" :trigger-change="true" dictCode="tb_edu_athlete_selection_group_rating,rating,id" placeholder="请选择测试等级评定"/>
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
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  
  export default {
    name: "AthleteSelectionAthleteScoreDetailModal",
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
        athleteScoreId:{rules: [{ required: true, message: '请输入测试成绩!' }]},
        athleteId:{},
        testId:{},
        groupId:{},
        eventCode:{},
        indexCode:{},
        testValue:{rules: [{ required: true, message: '请输入测试值!' }]},
        testScore:{},
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
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'athleteScoreId','athleteId','testId','groupId','eventCode','indexCode','testValue','testScore','testGrade'))
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
        this.form.setFieldsValue(pick(row,'athleteScoreId','athleteId','testId','groupId','eventCode','indexCode','testValue','testScore','testGrade'))
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