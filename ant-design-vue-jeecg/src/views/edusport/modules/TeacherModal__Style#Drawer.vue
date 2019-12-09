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

        <a-form-item label="教师代码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'teacherNo', validatorRules.teacherNo]" placeholder="请输入教师代码"></a-input>
        </a-form-item>
        <a-form-item label="教师姓名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'teacherName', validatorRules.teacherName]" placeholder="请输入教师姓名"></a-input>
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
        <a-form-item label="职称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['title']" :trigger-change="true" dictCode="sports_coache_title" placeholder="请选择职称"/>
        </a-form-item>
        <a-form-item label="职务" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'duty', validatorRules.duty]" placeholder="请输入职务"></a-input>
        </a-form-item>
        <a-form-item label="家庭电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'homeTel', validatorRules.homeTel]" placeholder="请输入家庭电话"></a-input>
        </a-form-item>
        <a-form-item label="家庭地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'homeAddress', validatorRules.homeAddress]" placeholder="请输入家庭地址"></a-input>
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
    name: "TeacherModal",
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
        teacherNo:{rules: [{ required: true, message: '请输入教师代码!' }]},
        teacherName:{rules: [{ required: true, message: '请输入教师姓名!' }]},
        gender:{rules: [{ required: true, message: '请输入性别!' }]},
        birthDate:{},
        nation:{},
        nativeProvince:{},
        nativeCity:{},
        idType:{},
        idNo:{},
        mobile:{},
        title:{},
        duty:{},
        homeTel:{},
        homeAddress:{},
        photoType:{},
        photoName:{},
        photoPath:{},
        uploadDate:{},
        },
        url: {
          add: "/edusport/teacher/add",
          edit: "/edusport/teacher/edit",
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
          this.form.setFieldsValue(pick(this.model,'teacherNo','teacherName','gender','birthDate','nation','nativeProvince','nativeCity','idType','idNo','mobile','title','duty','homeTel','homeAddress','photoType','photoName','photoPath','uploadDate'))
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
        this.form.setFieldsValue(pick(row,'teacherNo','teacherName','gender','birthDate','nation','nativeProvince','nativeCity','idType','idNo','mobile','title','duty','homeTel','homeAddress','photoType','photoName','photoPath','uploadDate'))
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