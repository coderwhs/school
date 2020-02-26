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

        <a-form-item label="所属测试" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['testId']" :trigger-change="true" dictCode="tb_edu_athlete_selection_test,test_name,id" placeholder="请选择所属测试"/>
        </a-form-item>
        <a-form-item label="教练员" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['coachId']" :trigger-change="true" dictCode="tb_edu_coach,coach_name,id" placeholder="请选择教练员"/>
        </a-form-item>
        <a-form-item label="测试组别" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['groupId']" :trigger-change="true" dictCode="tb_edu_athlete_selection_group,group_name,id" placeholder="请选择测试组别"/>
        </a-form-item>
        <a-form-item label="测试项目" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['sportCode']" :trigger-change="true" dictCode="tb_edu_sport,sport_name,sport_code" placeholder="请选择测试项目"/>
        </a-form-item>
        <a-form-item label="测试小项" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-multi-select-tag type="list_multi" v-decorator="['eventCodes']" :trigger-change="true" dictCode="tb_edu_sport_event,event_name,event_code" placeholder="请选择测试小项"/>
        </a-form-item>
        <a-form-item label="测试指标" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'indexCodes', validatorRules.indexCodes]" placeholder="请输入测试指标"></a-input>
        </a-form-item>
        <a-form-item label="测试日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择测试日期" v-decorator="[ 'testDate', validatorRules.testDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="单据状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'status', validatorRules.status]" placeholder="请输入单据状态"></a-input>
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
  import JMultiSelectTag from "@/components/dict/JMultiSelectTag"
  
  export default {
    name: "AthleteSelectionTestCoachModal",
    components: { 
      JDate,
      JDictSelectTag,
      JMultiSelectTag,
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
        testId:{rules: [{ required: true, message: '请输入所属测试!' }]},
        coachId:{rules: [{ required: true, message: '请输入教练员!' }]},
        groupId:{rules: [{ required: true, message: '请输入测试组别!' }]},
        sportCode:{rules: [{ required: true, message: '请输入测试项目!' }]},
        eventCodes:{rules: [{ required: true, message: '请输入测试小项!' }]},
        indexCodes:{rules: [{ required: true, message: '请输入测试指标!' }]},
        testDate:{rules: [{ required: true, message: '请输入测试日期!' }]},
        status:{rules: [{ required: true, message: '请输入单据状态!' }]},
        },
        url: {
          add: "/edusport/athleteSelectionTestCoach/add",
          edit: "/edusport/athleteSelectionTestCoach/edit",
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
          this.form.setFieldsValue(pick(this.model,'testId','coachId','groupId','sportCode','eventCodes','indexCodes','testDate','status'))
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
        this.form.setFieldsValue(pick(row,'testId','coachId','groupId','sportCode','eventCodes','indexCodes','testDate','status'))
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