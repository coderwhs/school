<template>
  <a-drawer
    :title="title"
    :width="drawerWidth"
    :maskClosable="true"
    placement="right"
    :closable="true"
    @close="close"
    :visible="visible"
    style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;">
  
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item label="年度计划" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag v-decorator="['yearPlanId', validatorRules.yearPlanId]" dict="tb_edu_sport_class_year_plan,plan_name,id" />
        </a-form-item>
        <a-form-item label="执行情况" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-editor v-decorator="['yearPerformance', validatorRules.yearPerformance]"/>
        </a-form-item>
        <a-form-item label="全年任务" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-editor v-decorator="['yearWeakness', validatorRules.yearWeakness]"/>
        </a-form-item>
        <a-form-item label="改进方向" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-editor v-decorator="['improvement', validatorRules.improvement]"/>
        </a-form-item>
        <a-form-item label="竞赛科评价" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['deptEvaluation', validatorRules.deptEvaluation]" rows="4" placeholder="请输入竞赛科评价"/>
        </a-form-item>
        <a-form-item label="竞赛科评价日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择竞赛科评价日期" v-decorator="[ 'deptEvaluationDate', validatorRules.deptEvaluationDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="校领导评价" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['schoolEvaluation', validatorRules.schoolEvaluation]" rows="4" placeholder="请输入校领导评价"/>
        </a-form-item>
        <a-form-item label="校领导评价日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择校领导评价日期" v-decorator="[ 'schoolEvaluationDate', validatorRules.schoolEvaluationDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        
      </a-form>
    </a-spin>

    <div class="drawer-bootom-button" v-show="!disableSubmit">
      <a-popconfirm title="确定放弃编辑？" @confirm="handleCancel" okText="确定" cancelText="取消">
        <a-button style="margin-right: .8rem">取消</a-button>
      </a-popconfirm>
      <a-button type="primary" @click="handleOk" :loading="confirmLoading">提交</a-button>
    </div>
  </a-drawer>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JDate from '@/components/jeecg/JDate'
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag'
  import JEditor from '@/components/jeecg/JEditor'

  export default {
    name: "SportClassYearSummaryModal",
    components: { 
      JDate,
      JSearchSelectTag,
      JEditor,
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        width:800,
        drawerWidth:800,
        disableSubmit:false,
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
          yearPlanId:{rules: [{ required: true, message: '请输入年度计划!' }]},
          yearPerformance:{trigger:'input', rules: [{ required: true, message: '请输入执行情况!' }]},
          yearWeakness:{trigger:'input', rules: [{ required: true, message: '请输入全年任务!' }]},
          improvement:{trigger:'input', rules: [{ required: true, message: '请输入改进方向!' }]},
          deptEvaluation:{rules: [{ required: true, message: '请输入竞赛科评价!' }]},
          deptEvaluationDate:{},
          schoolEvaluation:{rules: [{ required: true, message: '请输入校领导评价!' }]},
          schoolEvaluationDate:{},
        },
        url: {
          add: "/edusport/sportClassYearSummary/add",
          edit: "/edusport/sportClassYearSummary/edit",
        }
     
      }
    },
    created () {
    },
    methods: {
      add () {
        this.edit({});
      },
      add(yearPlanId) {
        this.hiding = true;
        if (yearPlanId) {
          this.yearPlanId = yearPlanId;
          this.edit({yearPlanId}, '');
        } else {
          this.$message.warning("请选择一个年度训练计划信息");
        }
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'yearPlanId','yearPerformance','yearWeakness','improvement','deptEvaluation','deptEvaluationDate','schoolEvaluation','schoolEvaluationDate'))
        })
      },
      close () {
        this.$emit('close');
        this.visible = false;
        this.disableSubmit = false;
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
        this.form.setFieldsValue(pick(row,'yearPlanId','yearPerformance','yearWeakness','improvement','deptEvaluation','deptEvaluationDate','schoolEvaluation','schoolEvaluationDate'))
      },
      // 根据屏幕变化,设置抽屉尺寸
      resetScreenSize(){
        let screenWidth = document.body.clientWidth;
        if(screenWidth < 500){
          this.drawerWidth = screenWidth;
        }else{
          this.drawerWidth = 700;
        }
      },
      
    }
  }
</script>

<style lang="less" scoped>
  .drawer-bootom-button {
    position: absolute;
    bottom: -8px;
    width: 100%;
    border-top: 1px solid #e8e8e8;
    padding: 10px 16px;
    text-align: right;
    left: 0;
    background: #fff;
    border-radius: 0 0 2px 2px;
  }
</style>