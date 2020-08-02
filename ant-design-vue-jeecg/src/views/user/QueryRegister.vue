<template>
  <a-form :form="form">

    <a-form-item label="身份证号码" :labelCol="labelCol" :wrapperCol="wrapperCol">
      <a-input placeholder="请输入身份证号码进行查询" v-decorator="[ 'idNo', validatorRules.idNo]"/>
    </a-form-item>

    <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol">
      <a-button size="large" type="primary" style="width: 100%" @click="handleQueryRegister">
        查询注册进度
      </a-button>
    </a-form-item>

<!--    显示返回结果-->
    <a-form-item label="查询结果" :labelCol="labelCol" :wrapperCol="wrapperCol">
      <a-input v-model="result" readonly="readonly"/>
    </a-form-item>

    <router-link class="login" :to="{ name: 'login' }">返回首页</router-link>

  </a-form>
</template>

<script>
  import { getAction} from '../../api/manage'
  export default {
    name: "QueryRegister",
    components: {},
    data() {
      return {
        result:'',
        validatorRules: {
          idNo: { rules: [{pattern: /^([0-9]{15}|[0-9]{18})$/, message: '请输入正确的身份证号码' }] }
        },
        labelCol: {
          xs: { span: 40 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 40 },
          sm: { span: 20 },//控制框的长度
        },
        form: this.$form.createForm(this),
        model: {},
        url: {
          queryRegister: "/athleteRegister/queryRegister",
        },
      }
    },
    methods: {
      handleQueryRegister() {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let formData = Object.assign(this.model, values);
            console.log("提交的数据内容是：", formData)
            getAction('/athleteRegister/queryRegister', formData).then((res) => {
              if (res.success) {
                that.result = res.message;
                console.log("that.result",that.result);//教练员审批
              } else {
                that.$message.warning(res.message);
              }
            })
          }
        })
      }
    }
  }
</script>

<style scoped>

</style>