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
          <j-dict-select-tag type="list" v-decorator="['athleteNo']" :trigger-change="true" dictCode="tb_edu_athlete,athlete_name,athlete_no" placeholder="请选择运动员"/>
        </a-form-item>
        <a-form-item label="请假原因" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'leaveCause', validatorRules.leaveCause]" placeholder="请输入请假原因"></a-input>
        </a-form-item>
        <a-form-item label="开始日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择开始日期" v-decorator="[ 'startDate', validatorRules.startDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="结束日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择结束日期" v-decorator="[ 'endDate', validatorRules.endDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="宿舍房间号"
          v-model="this.dormNo"
          :hidden="hiding"
          hasFeedback>
          <a-input v-decorator="[ 'dormNo', {}]" disabled="disabled"/>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JDate from '@/components/jeecg/JDate'  
  import JDictSelectTag from "@/components/dict/JDictSelectTag"

  export default {
    name: "DormAthleteLeaveModal",
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
        athleteNo:{rules: [{ required: true, message: '请输入运动员!' }]},
        leaveCause:{rules: [{ required: true, message: '请输入请假原因!' }]},
        startDate:{rules: [{ required: true, message: '请输入开始日期!' }]},
        endDate:{},
        },

        fileList: [],
        disableSubmit: false,
        selectedRowKeys: [],
        dormNo:"",
        hiding: false,
        headers: {},
        addStatus: false,
        editStatus: false,
        confirmLoading: false,
        form: this.$form.createForm(this),
        url: {
          add: "/edusport/dormAthleteLeave/add",
          edit: "/edusport/dormAthleteLeave/edit",
        }
     
      }
    },
    created () {
    },
    methods: {
      //add () {
      //  this.edit({});
      //},
      add(dormId){
        this.hiding = true;
        if (dormId) {
          this.dormId = dormId;
          this.edit({dormId}, '');
        } else {
          this.$message.warning("请选择一个宿舍信息");
        }
      },
      edit (record) {
        this.form.resetFields();
        this.dormId = record.dormId;
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'athleteNo','leaveCause','startDate','endDate'))
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
            console.log("表单提交数据",formData);
            formData.dormId = this.dormId;
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
        this.form.setFieldsValue(pick(row,'athleteNo','leaveCause','startDate','endDate'))
      },

      
    }
  }
</script>