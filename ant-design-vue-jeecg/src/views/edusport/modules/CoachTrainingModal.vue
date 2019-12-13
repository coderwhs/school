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

        <a-row>
          <a-col :span="24" :gutter="8">
            <a-form-item label="教练员" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-search-select-tag v-decorator="['coachId']" dict="tb_edu_coach,coach_name,id" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="12" :gutter="8">
            <a-form-item label="培训名称" :labelCol="labelCol2" :wrapperCol="wrapperCol2">
              <a-input v-decorator="[ 'trainingCourse', validatorRules.trainingCourse]" placeholder="请输入培训名称"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12" :gutter="8">
            <a-form-item label="主办单位" :labelCol="labelCol2" :wrapperCol="wrapperCol2">
              <a-input v-decorator="[ 'organizer', validatorRules.organizer]" placeholder="请输入主办单位"></a-input>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="12" :gutter="8">
            <a-form-item label="开始日期" :labelCol="labelCol2" :wrapperCol="wrapperCol2">
              <j-date placeholder="请选择开始日期" v-decorator="[ 'startDate', validatorRules.startDate]" :trigger-change="true" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="12" :gutter="8">
            <a-form-item label="结束日期" :labelCol="labelCol2" :wrapperCol="wrapperCol2">
              <j-date placeholder="请选择结束日期" v-decorator="[ 'endDate', validatorRules.endDate]" :trigger-change="true" style="width: 100%"/>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="24" :gutter="8">
            <a-form-item label="培训地点" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'trainingPlace', validatorRules.trainingPlace]" placeholder="请输入培训地点"></a-input>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="24" :gutter="8">
            <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-textarea v-decorator="['remark']" rows="4" placeholder="请输入备注"/>
            </a-form-item>
          </a-col>
        </a-row>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JDate from '@/components/jeecg/JDate'  
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag'

  export default {
    name: "CoachTrainingModal",
    components: { 
      JDate,
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
          md: { span: 4 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
          md: { span: 18 },
        },

        labelCol2: {
          xs: { span: 24 },
          sm: { span: 8 },
          md: { span: 8 },
        },
        wrapperCol2: {
          xs: { span: 24 },
          sm: { span: 14 },
          md: { span: 14 },
        },

        confirmLoading: false,
        validatorRules:{
        coachId:{rules: [{ required: true, message: '请输入教练员!' }]},
        trainingCourse:{rules: [{ required: true, message: '请输入培训名称!' }]},
        startDate:{rules: [{ required: true, message: '请输入开始日期!' }]},
        endDate:{},
        trainingPlace:{rules: [{ required: true, message: '请输入培训地点!' }]},
        organizer:{rules: [{ required: true, message: '请输入主办单位!' }]},
        remark:{rules: [{ required: true, message: '请输入备注!' }]},
        },
        url: {
          add: "/edusport/coachTraining/add",
          edit: "/edusport/coachTraining/edit",
        }
     
      }
    },
    created () {
    },
    methods: {
      add(coachId) {
        this.hiding = true;
        if (coachId) {
          this.coachId = coachId;
          this.edit({coachId}, '');
        } else {
          this.$message.warning("请选择一个教练员信息");
        }
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'coachId','trainingCourse','startDate','endDate','trainingPlace','organizer','remark'))
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
        this.form.setFieldsValue(pick(row,'coachId','trainingCourse','startDate','endDate','trainingPlace','organizer','remark'))
      },

      
    }
  }
</script>