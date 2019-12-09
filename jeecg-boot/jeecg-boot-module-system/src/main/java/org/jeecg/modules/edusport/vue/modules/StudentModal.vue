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

        <a-form-item label="学号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'studentNo', validatorRules.studentNo]" placeholder="请输入学号"></a-input>
        </a-form-item>
        <a-form-item label="姓名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'studentName', validatorRules.studentName]" placeholder="请输入姓名"></a-input>
        </a-form-item>
        <a-form-item label="性别" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="radio" v-decorator="['gender']" :trigger-change="true" dictCode="sex" placeholder="请选择性别"/>
        </a-form-item>
        <a-form-item label="出生日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择出生日期" v-decorator="[ 'birthDate', validatorRules.birthDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="民族" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['nation']" :trigger-change="true" dictCode="nation" placeholder="请选择民族"/>
        </a-form-item>
        <a-form-item label="籍贯省" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'nativeProvince', validatorRules.nativeProvince]" placeholder="请输入籍贯省"></a-input>
        </a-form-item>
        <a-form-item label="籍贯市" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'nativeCity', validatorRules.nativeCity]" placeholder="请输入籍贯市"></a-input>
        </a-form-item>
        <a-form-item label="证件类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'idType', validatorRules.idType]" placeholder="请输入证件类型"></a-input>
        </a-form-item>
        <a-form-item label="证件号码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'idNo', validatorRules.idNo]" placeholder="请输入证件号码"></a-input>
        </a-form-item>
        <a-form-item label="手机号码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'mobile', validatorRules.mobile]" placeholder="请输入手机号码"></a-input>
        </a-form-item>
        <a-form-item label="入学日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择入学日期" v-decorator="[ 'admissionDate', validatorRules.admissionDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="入学成绩" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'admissionScore', validatorRules.admissionScore]" placeholder="请输入入学成绩" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="学籍状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['studentStatus']" :trigger-change="true" dictCode="student_status" placeholder="请选择学籍状态"/>
        </a-form-item>
        <a-form-item label="政治面貌" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['politicalStatus']" :trigger-change="true" dictCode="political_status" placeholder="请选择政治面貌"/>
        </a-form-item>
        <a-form-item label="职务" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'duty', validatorRules.duty]" placeholder="请输入职务"></a-input>
        </a-form-item>
        <a-form-item label="照片类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'photoType', validatorRules.photoType]" placeholder="请输入照片类型"></a-input>
        </a-form-item>
        <a-form-item label="照片名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'photoName', validatorRules.photoName]" placeholder="请输入照片名称"></a-input>
        </a-form-item>
        <a-form-item label="照片路径" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'photoPath', validatorRules.photoPath]" placeholder="请输入照片路径"></a-input>
        </a-form-item>
        <a-form-item label="照片上传时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择照片上传时间" v-decorator="[ 'uploadDate', validatorRules.uploadDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="骨龄" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'boneAge', validatorRules.boneAge]" placeholder="请输入骨龄" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="身高" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'studentHeight', validatorRules.studentHeight]" placeholder="请输入身高" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="体重" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'studentWeight', validatorRules.studentWeight]" placeholder="请输入体重" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="父亲姓名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'father', validatorRules.father]" placeholder="请输入父亲姓名"></a-input>
        </a-form-item>
        <a-form-item label="父亲身高" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'fatherHeight', validatorRules.fatherHeight]" placeholder="请输入父亲身高" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="父亲体重" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'fatherWeight', validatorRules.fatherWeight]" placeholder="请输入父亲体重" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="父亲电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'fatherMobile', validatorRules.fatherMobile]" placeholder="请输入父亲电话"></a-input>
        </a-form-item>
        <a-form-item label="父亲工作单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'fatherJob', validatorRules.fatherJob]" placeholder="请输入父亲工作单位"></a-input>
        </a-form-item>
        <a-form-item label="母亲姓名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'mother', validatorRules.mother]" placeholder="请输入母亲姓名"></a-input>
        </a-form-item>
        <a-form-item label="母亲身高" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'motherHeight', validatorRules.motherHeight]" placeholder="请输入母亲身高" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="母亲体重" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'motherWeight', validatorRules.motherWeight]" placeholder="请输入母亲体重" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="母亲电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'motherMobile', validatorRules.motherMobile]" placeholder="请输入母亲电话"></a-input>
        </a-form-item>
        <a-form-item label="母亲工作单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'motherJob', validatorRules.motherJob]" placeholder="请输入母亲工作单位"></a-input>
        </a-form-item>
        <a-form-item label="家庭电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'homeTel', validatorRules.homeTel]" placeholder="请输入家庭电话"></a-input>
        </a-form-item>
        <a-form-item label="家庭住址" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'homeAddress', validatorRules.homeAddress]" placeholder="请输入家庭住址"></a-input>
        </a-form-item>
        <a-form-item label="户口所在派出所" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'policeStation', validatorRules.policeStation]" placeholder="请输入户口所在派出所"></a-input>
        </a-form-item>
        <a-form-item label="简介" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['resume']" rows="4" placeholder="请输入简介"/>
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
    name: "StudentModal",
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
        studentNo:{rules: [{ required: true, message: '请输入学号!' }]},
        studentName:{rules: [{ required: true, message: '请输入姓名!' }]},
        gender:{rules: [{ required: true, message: '请输入性别!' }]},
        birthDate:{},
        nation:{},
        nativeProvince:{},
        nativeCity:{},
        idType:{},
        idNo:{},
        mobile:{},
        admissionDate:{},
        admissionScore:{},
        studentStatus:{},
        politicalStatus:{},
        duty:{},
        photoType:{},
        photoName:{},
        photoPath:{},
        uploadDate:{},
        boneAge:{},
        studentHeight:{},
        studentWeight:{},
        father:{},
        fatherHeight:{},
        fatherWeight:{},
        fatherMobile:{},
        fatherJob:{},
        mother:{},
        motherHeight:{},
        motherWeight:{},
        motherMobile:{},
        motherJob:{},
        homeTel:{},
        homeAddress:{},
        policeStation:{},
        resume:{},
        },
        url: {
          add: "/edusport/student/add",
          edit: "/edusport/student/edit",
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
          this.form.setFieldsValue(pick(this.model,'studentNo','studentName','gender','birthDate','nation','nativeProvince','nativeCity','idType','idNo','mobile','admissionDate','admissionScore','studentStatus','politicalStatus','duty','photoType','photoName','photoPath','uploadDate','boneAge','studentHeight','studentWeight','father','fatherHeight','fatherWeight','fatherMobile','fatherJob','mother','motherHeight','motherWeight','motherMobile','motherJob','homeTel','homeAddress','policeStation','resume'))
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
        this.form.setFieldsValue(pick(row,'studentNo','studentName','gender','birthDate','nation','nativeProvince','nativeCity','idType','idNo','mobile','admissionDate','admissionScore','studentStatus','politicalStatus','duty','photoType','photoName','photoPath','uploadDate','boneAge','studentHeight','studentWeight','father','fatherHeight','fatherWeight','fatherMobile','fatherJob','mother','motherHeight','motherWeight','motherMobile','motherJob','homeTel','homeAddress','policeStation','resume'))
      },

      
    }
  }
</script>