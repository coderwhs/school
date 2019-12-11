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

        <a-form-item label="训练班主键id" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'sportClassId', validatorRules.sportClassId]" placeholder="请输入训练班主键id"></a-input>
        </a-form-item>
        <a-form-item label="运动员学号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'athleteNo', validatorRules.athleteNo]" placeholder="请输入运动员学号"></a-input>
        </a-form-item>
        <a-form-item label="参加日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择参加日期" v-decorator="[ 'attendDate', validatorRules.attendDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="比赛最好成绩" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'athleteBestScore', validatorRules.athleteBestScore]" placeholder="请输入比赛最好成绩"></a-input>
        </a-form-item>
        <a-form-item label="获得等级" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['athleteAwardTechGrade']" :trigger-change="true" dictCode="athlete_tech_grade" placeholder="请选择获得等级"/>
        </a-form-item>
        <a-form-item label="破纪录状况" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'aboutBreakRecord', validatorRules.aboutBreakRecord]" placeholder="请输入破纪录状况"></a-input>
        </a-form-item>
        <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'remark', validatorRules.remark]" placeholder="请输入备注"></a-input>
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
    name: "AthleteSportClassModal",
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
        sportClassId:{rules: [{ required: true, message: '请输入训练班主键id!' }]},
        athleteNo:{rules: [{ required: true, message: '请输入运动员学号!' }]},
        attendDate:{},
        athleteBestScore:{rules: [{ required: true, message: '请输入比赛最好成绩!' }]},
        athleteAwardTechGrade:{rules: [{ required: true, message: '请输入获得等级!' }]},
        aboutBreakRecord:{rules: [{ required: true, message: '请输入破纪录状况!' }]},
        remark:{rules: [{ required: true, message: '请输入备注!' }]},
        },
        url: {
          add: "/edusport/athleteSportClass/add",
          edit: "/edusport/athleteSportClass/edit",
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
          this.form.setFieldsValue(pick(this.model,'sportClassId','athleteNo','attendDate','athleteBestScore','athleteAwardTechGrade','aboutBreakRecord','remark'))
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
        this.form.setFieldsValue(pick(row,'sportClassId','athleteNo','attendDate','athleteBestScore','athleteAwardTechGrade','aboutBreakRecord','remark'))
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