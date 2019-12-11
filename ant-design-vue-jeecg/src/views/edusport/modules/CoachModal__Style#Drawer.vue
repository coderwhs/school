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

        <a-form-item label="教练员代码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'coachNo', validatorRules.coachNo]" placeholder="请输入教练员代码"></a-input>
        </a-form-item>
        <a-form-item label="姓名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'coachName', validatorRules.coachName]" placeholder="请输入姓名"></a-input>
        </a-form-item>
        <a-form-item label="性别" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['gender']" :trigger-change="true" dictCode="sex" placeholder="请选择性别"/>
        </a-form-item>
        <a-form-item label="民族" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['nation']" :trigger-change="true" dictCode="nation" placeholder="请选择民族"/>
        </a-form-item>
        <a-form-item label="出生日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择出生日期" v-decorator="[ 'birthDate', validatorRules.birthDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="身份证号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'idNo', validatorRules.idNo]" placeholder="请输入身份证号"></a-input>
        </a-form-item>
        <a-form-item label="籍贯省" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'nativeProvince', validatorRules.nativeProvince]" placeholder="请输入籍贯省"></a-input>
        </a-form-item>
        <a-form-item label="籍贯市" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'nativeCity', validatorRules.nativeCity]" placeholder="请输入籍贯市"></a-input>
        </a-form-item>
        <a-form-item label="政治面貌" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['politicalStatus']" :trigger-change="true" dictCode="political_status" placeholder="请选择政治面貌"/>
        </a-form-item>
        <a-form-item label="家庭地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'homeAddress', validatorRules.homeAddress]" placeholder="请输入家庭地址"></a-input>
        </a-form-item>
        <a-form-item label="手机号码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'mobile', validatorRules.mobile]" placeholder="请输入手机号码"></a-input>
        </a-form-item>
        <a-form-item label="学历" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['eduBackground']" :trigger-change="true" dictCode="edu_background" placeholder="请选择学历"/>
        </a-form-item>
        <a-form-item label="学位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['eduDegree']" :trigger-change="true" dictCode="edu_degree" placeholder="请选择学位"/>
        </a-form-item>
        <a-form-item label="编制类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['staffType']" :trigger-change="true" dictCode="staff_type" placeholder="请选择编制类型"/>
        </a-form-item>
        <a-form-item label="聘用日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择聘用日期" v-decorator="[ 'hireDate', validatorRules.hireDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="聘用年限" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'hireYears', validatorRules.hireYears]" placeholder="请输入聘用年限" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="何年毕业于何学校专业" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'marjorInfo', validatorRules.marjorInfo]" placeholder="请输入何年毕业于何学校专业"></a-input>
        </a-form-item>
        <a-form-item label="初任教练员时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'coachStartDate', validatorRules.coachStartDate]" placeholder="请输入初任教练员时间"></a-input>
        </a-form-item>
        <a-form-item label="三级教练员聘任时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'l3AppointmentDate', validatorRules.l3AppointmentDate]" placeholder="请输入三级教练员聘任时间"></a-input>
        </a-form-item>
        <a-form-item label="二级教练员聘任时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'l2AppointmentDate', validatorRules.l2AppointmentDate]" placeholder="请输入二级教练员聘任时间"></a-input>
        </a-form-item>
        <a-form-item label="一级教练员聘任时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'l1AppointmentDate', validatorRules.l1AppointmentDate]" placeholder="请输入一级教练员聘任时间"></a-input>
        </a-form-item>
        <a-form-item label="取得高级教练员资格时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'ladvCertDate', validatorRules.ladvCertDate]" placeholder="请输入取得高级教练员资格时间"></a-input>
        </a-form-item>
        <a-form-item label="高级教练员聘任时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'ladvAppointmentDate', validatorRules.ladvAppointmentDate]" placeholder="请输入高级教练员聘任时间"></a-input>
        </a-form-item>
        <a-form-item label="取得国家级教练员资格时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'lcnCertDate', validatorRules.lcnCertDate]" placeholder="请输入取得国家级教练员资格时间"></a-input>
        </a-form-item>
        <a-form-item label="国家级教练员聘任时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'lcnAppointmentDate', validatorRules.lcnAppointmentDate]" placeholder="请输入国家级教练员聘任时间"></a-input>
        </a-form-item>
        <a-form-item label="照片名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-upload v-decorator="['photoName']" :trigger-change="true"></j-upload>
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
  import JUpload from '@/components/jeecg/JUpload'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  
  export default {
    name: "CoachModal",
    components: { 
      JDate,
      JUpload,
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
        coachNo:{rules: [{ required: true, message: '请输入教练员代码!' }]},
        coachName:{rules: [{ required: true, message: '请输入姓名!' }]},
        gender:{rules: [{ required: true, message: '请输入性别!' }]},
        nation:{rules: [{ required: true, message: '请输入民族!' }]},
        birthDate:{},
        idNo:{rules: [{ required: true, message: '请输入身份证号!' }]},
        nativeProvince:{rules: [{ required: true, message: '请输入籍贯省!' }]},
        nativeCity:{rules: [{ required: true, message: '请输入籍贯市!' }]},
        politicalStatus:{rules: [{ required: true, message: '请输入政治面貌!' }]},
        homeAddress:{rules: [{ required: true, message: '请输入家庭地址!' }]},
        mobile:{rules: [{ required: true, message: '请输入手机号码!' }]},
        eduBackground:{rules: [{ required: true, message: '请输入学历!' }]},
        eduDegree:{rules: [{ required: true, message: '请输入学位!' }]},
        staffType:{rules: [{ required: true, message: '请输入编制类型!' }]},
        hireDate:{},
        hireYears:{rules: [{ required: true, message: '请输入聘用年限!' }]},
        marjorInfo:{rules: [{ required: true, message: '请输入何年毕业于何学校专业!' }]},
        coachStartDate:{rules: [{ required: true, message: '请输入初任教练员时间!' }]},
        l3AppointmentDate:{rules: [{ required: true, message: '请输入三级教练员聘任时间!' }]},
        l2AppointmentDate:{rules: [{ required: true, message: '请输入二级教练员聘任时间!' }]},
        l1AppointmentDate:{rules: [{ required: true, message: '请输入一级教练员聘任时间!' }]},
        ladvCertDate:{rules: [{ required: true, message: '请输入取得高级教练员资格时间!' }]},
        ladvAppointmentDate:{rules: [{ required: true, message: '请输入高级教练员聘任时间!' }]},
        lcnCertDate:{rules: [{ required: true, message: '请输入取得国家级教练员资格时间!' }]},
        lcnAppointmentDate:{rules: [{ required: true, message: '请输入国家级教练员聘任时间!' }]},
        photoName:{rules: [{ required: true, message: '请输入照片名称!' }]},
        },
        url: {
          add: "/edusport/coach/add",
          edit: "/edusport/coach/edit",
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
          this.form.setFieldsValue(pick(this.model,'coachNo','coachName','gender','nation','birthDate','idNo','nativeProvince','nativeCity','politicalStatus','homeAddress','mobile','eduBackground','eduDegree','staffType','hireDate','hireYears','marjorInfo','coachStartDate','l3AppointmentDate','l2AppointmentDate','l1AppointmentDate','ladvCertDate','ladvAppointmentDate','lcnCertDate','lcnAppointmentDate','photoName'))
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
        this.form.setFieldsValue(pick(row,'coachNo','coachName','gender','nation','birthDate','idNo','nativeProvince','nativeCity','politicalStatus','homeAddress','mobile','eduBackground','eduDegree','staffType','hireDate','hireYears','marjorInfo','coachStartDate','l3AppointmentDate','l2AppointmentDate','l1AppointmentDate','ladvCertDate','ladvAppointmentDate','lcnCertDate','lcnAppointmentDate','photoName'))
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