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
        <a-form-item label="编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'testCode', validatorRules.testCode]" placeholder="请输入编码"></a-input>
        </a-form-item>
        <a-form-item label="名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'testName', validatorRules.testName]" placeholder="请输入测试名称"></a-input>
        </a-form-item>
        <a-form-item label="大项" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag v-decorator="['sportCode']" dict="tb_edu_sport,sport_name,sport_code" :trigger-change="true" @change="sportChange"/>
        </a-form-item>
        <a-form-item label="组别" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag v-decorator="['groupId']" dict="tb_edu_athlete_selection_group,group_name,id" />
        </a-form-item>
        <a-form-item label="运动员" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-multi-select-tag type="list_multi" v-decorator="['athleteNos']" :trigger-change="true" dictCode="tb_edu_athlete,athlete_name,athlete_no" placeholder="请选择运动员"/>
        </a-form-item>
        <a-form-item label="发布日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择发布日期" v-decorator="[ 'publishDate', validatorRules.publishDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['billState']" :trigger-change="true" dictCode="bill_state" placeholder="请选择状态"/>
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JDate from '@/components/jeecg/JDate'  
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag'
  import JMultiSelectTag from "@/components/dict/JMultiSelectTag"
  import JDictSelectTag from "@/components/dict/JDictSelectTag"

  import {initDictOptions, filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  export default {
    name: "AthleteSelectionTestModal",
    components: { 
      JDate,
      JSearchSelectTag,
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
        testCode:{rules: [{ required: true, message: '请输入编码!' }]},
        testName:{rules: [{ required: true, message: '请输入名称!' }]},
        sportCode:{rules: [{ required: true, message: '请输入大项!' }]},
        groupId:{rules: [{ required: true, message: '请输入组别!' }]},
        publishDate:{rules: [{ required: true, message: '请输入发布日期!' }]},
        },
        url: {
          add: "/edusport/athleteSelectionTest/add",
          edit: "/edusport/athleteSelectionTest/edit",
        },
        sportCode:''
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
          this.form.setFieldsValue(pick(this.model,'testCode','testName','sportCode','groupId','athleteNos','publishDate','billState'))
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
        this.form.setFieldsValue(pick(row,'testCode','testName','sportCode','groupId','athleteNos','publishDate','billState'))
      },

      sportChange(e){
        if(e.target === e.currentTarget ||
            e.timeStamp >= attachedTimestamp ||
            e.timeStamp <= 0 ||
            e.target.ownerDocument !== document){
          alert("ok = " + e.setFieldsValue);
          
          initDictOptions("tb_edu_athlete,athlete_name,athlete_no,major_sport='" + 7 + "'").then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'athleteNos', res.result)
          }
        })
        }
      }
    }
  }
</script>