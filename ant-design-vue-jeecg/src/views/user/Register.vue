<template>
  <div class="main user-layout-register">
    <h3><span>注册</span></h3>
    <a-form ref="formRegister" :autoFormCreate="(form)=>{this.form = form}" id="formRegister">
      <!--      姓名-->
      <a-form-item
        label="姓名"
        fieldDecoratorId="username"
        :fieldDecoratorOptions="{rules: [{ required: true, message: '用户名不能为空且只能汉字'}, { validator: this.checkUsername }], validateTrigger: ['change', 'blur']}">
        <a-input size="large" type="text" name="userName" autocomplete="off" placeholder="请输入用户名"></a-input>
      </a-form-item>

      <!--      密码-->
      <a-form-item
        label="密码"
        fieldDecoratorId="password"
        :fieldDecoratorOptions="{rules: [{ required: true}, { validator: this.handlePasswordLevel }], validateTrigger: ['change', 'blur']}">
        <a-input size="large" type="password" @click="handlePasswordInputClick" autocomplete="off" placeholder="至少8位密码，区分大小写"></a-input>
      </a-form-item>

      <a-popover placement="rightTop" trigger="click" :visible="state.passwordLevelChecked">
        <template slot="content">
          <div :style="{ width: '240px' }">
            <div :class="['user-register', passwordLevelClass]">强度：<span>{{ passwordLevelName }}</span></div>
            <a-progress :percent="state.percent" :showInfo="false" :strokeColor=" passwordLevelColor "/>
            <div style="margin-top: 10px;">
              <span>请至少输入 8 个字符。请不要使用容易被猜到的密码。</span>
            </div>
          </div>
        </template>
      </a-popover>

      <!--      确定密码-->
      <a-form-item
        label="确认密码"
        fieldDecoratorId="password2"
        :fieldDecoratorOptions="{rules: [{ required: true, message: '至少8位密码，区分大小写' }, { validator: this.handlePasswordCheck }], validateTrigger: ['change', 'blur']}">
        <a-input size="large" type="password" autocomplete="false" placeholder="确认密码"></a-input>
      </a-form-item>
      <!--电话号码-->
      <a-form-item
        label="电话号码"
        fieldDecoratorId="mobile"
        :fieldDecoratorOptions="{rules: [{ required: true, pattern: /^1[3456789]\d{9}$/, message: '请输入正确的手机号' }, { validator: this.handlePhoneCheck } ], validateTrigger: ['change', 'blur'] }">
        <a-input size="large" name="mobile" placeholder="11 位手机号">
          <a-select slot="addonBefore" size="large" defaultValue="+86">
            <a-select-option value="+86">+86</a-select-option>
            <a-select-option value="+87">+87</a-select-option>
          </a-select>
        </a-input>
      </a-form-item>

      <a-form-item
        label="性别"
        fieldDecoratorId="sex"
        :fieldDecoratorOptions="{rules: [{ required: true}],validateTrigger: ['change', 'blur']}">
        <j-dict-select-tag size="large" v-decorator="['sex', {}]" :triggerChange="true" placeholder="请选择性别" dictCode="sex"/>
      </a-form-item>

      <!--      民族：select，必填，字典下拉框-->
      <a-form-item
        label="民族"
        fieldDecoratorId="nation"
        :fieldDecoratorOptions="{rules: [{ required: true}],validateTrigger: ['change', 'blur']}">
        <j-dict-select-tag size="large" v-decorator="['nation', {}]" :triggerChange="true" placeholder="请输入民族" dictCode="nation"/>
      </a-form-item>

      <!--      身份证号: input，必填-->
      <a-form-item
        label="身份证号"
        fieldDecoratorId="idNo"
        :fieldDecoratorOptions="{rules: [{ required: true, message: '请正确输入身份证号码'}], validateTrigger: ['change', 'blur']}">
        <a-input size="large" placeholder="请输入身份证号码"></a-input>
<!--        <a-input size="large" v-decorator="['idNo', {validateTrigger: ['change', 'blur']}]" placeholder="请输入身份证号码"></a-input>-->
      </a-form-item>

      <!--带训教练-->
      <a-form-item label="带训教练" fieldDecoratorId="coachId">
        <j-search-select-tag
          placeholder="请选择带训教练"
          dict="tb_edu_coach,coach_name,id"
          :async="true">
        </j-search-select-tag>

      </a-form-item>

      <!--      专业项目: select，必填，带检索运动项目下拉框-->
      <a-form-item
        label="专业项目"
        fieldDecoratorId="sportCode"
        :fieldDecoratorOptions="{rules: [{ required: true}],validateTrigger: ['change', 'blur']}">
        <j-search-select-tag
          placeholder="请选择专业项目"
          dict="tb_edu_sport,sport_name,id"
          :async="true">
        </j-search-select-tag>

      </a-form-item>

      <!--      家庭住址: input，必填-->
      <a-form-item
        label="家庭住址"
        fieldDecoratorId="homeAddress"
        :fieldDecoratorOptions="{rules: [{ required: true}],validateTrigger: ['change', 'blur']}">
        <a-input size="large" type="text" autocomplete="false" placeholder="请输入家庭住址"></a-input>
      </a-form-item>

      <!--      父亲姓名: input，校验汉字（父亲和母亲的姓名手机必填其一）-->
      <a-form-item
        label="父亲姓名"
        fieldDecoratorId="father"
        :fieldDecoratorOptions="{rules: [{ required: true, message: '用户名不能为空且只能汉字'}, { validator: this.checkUsername }], validateTrigger: ['change', 'blur']}">
        <a-input size="large" type="text" name="fatherName" autocomplete="false" placeholder="请输入您父亲的名字"></a-input>
      </a-form-item>

      <!--      父亲电话: input，校验电话-->
      <a-form-item
        label="父亲电话号码"
        fieldDecoratorId="fatherMobile"
        :fieldDecoratorOptions="{rules: [{ required: true, pattern: /^1[3456789]\d{9}$/, message: '请输入正确的手机号' }, { validator: this.handlePhoneCheck } ], validateTrigger: ['change', 'blur'] }">
        <a-input size="large" placeholder="11 位手机号">
          <a-select slot="addonBefore" name="fatherMobile" size="large" defaultValue="+86">
            <a-select-option value="+86">+86</a-select-option>
            <a-select-option value="+87">+87</a-select-option>
          </a-select>
        </a-input>
      </a-form-item>

      <!--      母亲姓名-->
      <a-form-item
        label="母亲姓名"
        fieldDecoratorId="mother"
        :fieldDecoratorOptions="{rules: [{ required: true, message: '用户名不能为空且只能汉字'}, { validator: this.checkUsername }], validateTrigger: ['change', 'blur']}">
        <a-input size="large" type="text" name="mother" autocomplete="false" placeholder="请输入您母亲的名字"></a-input>
      </a-form-item>

      <!--      母亲电话-->
      <a-form-item
        label="母亲电话号码"
        fieldDecoratorId="motherMobile"
        :fieldDecoratorOptions="{rules: [{ required: true, pattern: /^1[3456789]\d{9}$/, message: '请输入正确的手机号' }, { validator: this.handlePhoneCheck } ], validateTrigger: ['change', 'blur'] }">
        <a-input size="large" placeholder="11 位手机号">
          <a-select slot="addonBefore" name="motherMobile" size="large" defaultValue="+86">
            <a-select-option value="+86">+86</a-select-option>
            <a-select-option value="+87">+87</a-select-option>
          </a-select>
        </a-input>
      </a-form-item>

      <!--验证码部分-->
      <a-row :gutter="0">
        <a-col :span="14">
          <a-form-item>
            <a-input
              v-decorator="['inputCode',validatorRules.inputCode]"
              size="large"
              type="text"
              @change="inputCodeChange"
              placeholder="请输入验证码">
              <a-icon slot="prefix" v-if=" inputCodeContent===verifiedCode " type="smile" :style="{ color: 'rgba(0,0,0,0.25)' }"/>
              <a-icon slot="prefix" v-else type="frown" :style="{ color: 'rgba(0,0,0,.25)' }"/>
            </a-input>
          </a-form-item>
        </a-col>

        <a-col :span="10">
          <j-graphic-code @success="generateCode" ref="jgraphicCodeRef" style="float: right" remote></j-graphic-code>
        </a-col>
      </a-row>

      <a-form-item>
        <a-button
          size="large"
          type="primary"
          htmlType="submit"
          class="register-button"
          :loading="registerBtn"
          @click.stop.prevent="formHandleSubmit"
          :disabled="registerBtn">注册
        </a-button>

                <router-link class="login" :to="{ name: 'login' }">使用已有账户登录</router-link>
      </a-form-item>

    </a-form>
  </div>
</template>

<script>
  import {mixinDevice} from '@/utils/mixin.js'
  import {getSmsCaptcha} from '@/api/login'
  import {getAction, postAction} from '@/api/manage'
  import {checkOnlyUser} from '@/api/api'
  import {ajaxGetDictItems} from '@/api/api'
  import JGraphicCode from '@/components/jeecg/JGraphicCode'
  import JDictSelectTag from '@/components/dict/JDictSelectTag.vue'
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag'

  import JSelectDepart from '@/components/jeecgbiz/JSelectDepart'
  //引入依赖方法
  import {initDictOptions, filterDictText} from '@/components/dict/JDictSelectUtil'



  const levelNames = {
    0: '低',
    1: '低',
    2: '中',
    3: '强'
  }
  const levelClass = {
    0: 'error',
    1: 'error',
    2: 'warning',
    3: 'success'
  }
  const levelColor = {
    0: '#ff0000',
    1: '#ff0000',
    2: '#ff7e05',
    3: '#52c41a',
  }
  export default {
    name: "Register",
    components: {
      JSelectDepart,
      JGraphicCode,
      JDictSelectTag,
      JSearchSelectTag
    },
    mixins: [mixinDevice],
    data() {
      return {
        form: null,
        asyncSelectValue:"",
        dictOptions:[],//储存返回的性别
        state: {
          time: 60,
          smsSendBtn: false,
          passwordLevel: 0,
          passwordLevelChecked: false,
          percent: 10,
          progressColor: '#FF0000'
        },
        registerBtn: false,
        validatorRules:{
          captcha:{rule: [{ required: true, message: '请输入验证码!'}]},
          inputCode:{rules: [{ required: true, message: '请输入验证码!'},{validator: this.validateInputCode}]}
        },
        verifiedCode:"",
        inputCodeContent:"",
        inputCodeNull:true,
      }
    },
    created() {
    },
    computed: {
      passwordLevelClass() {
        return levelClass[this.state.passwordLevel]
      },
      passwordLevelName() {
        return levelNames[this.state.passwordLevel]
      },
      passwordLevelColor() {
        return levelColor[this.state.passwordLevel]
      }
    },
    methods: {
      checkUsername(rule, value, callback) {
        var params = {
          username: value,
        };
        //设置中文校验
        if (value) {
          if ((/[^\u4e00-\u9fa5]/g.test(value))) {
            callback(new Error('只能输入汉字!'))
          }
        }
        checkOnlyUser(params).then((res) => {
          if (res.success) {
            callback()
          } else {
            callback("用户名已存在!")
          }
        })
      },
      handleEmailCheck(rule, value, callback) {
        var params = {
          email: value,
        };
        checkOnlyUser(params).then((res) => {
          if (res.success) {
            callback()
          } else {
            callback("邮箱已存在!")
          }
        })
      },
      handlePasswordLevel(rule, value, callback) {

        let level = 0
        let reg = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[~!@#$%^&*()_+`\-={}:";'<>?,./]).{8,}$/;
        if (!reg.test(value)) {
          callback(new Error('密码由8位数字、大小写字母和特殊符号组成!'))
        }
        // 判断这个字符串中有没有数字
        if (/[0-9]/.test(value)) {
          level++
        }
        // 判断字符串中有没有字母
        if (/[a-zA-Z]/.test(value)) {
          level++
        }
        // 判断字符串中有没有特殊符号
        if (/[^0-9a-zA-Z_]/.test(value)) {
          level++
        }
        this.state.passwordLevel = level
        this.state.percent = level * 30
        if (level >= 2) {
          if (level >= 3) {
            this.state.percent = 100
          }
          callback()
        } else {
          if (level === 0) {
            this.state.percent = 10
          }
          callback(new Error('密码强度不够'))
        }
      },

      handlePasswordCheck(rule, value, callback) {
        let password = this.form.getFieldValue('password')
        //console.log('value', value)
        if (value === undefined) {
          callback(new Error('请输入密码'))
        }
        if (value && password && value.trim() !== password.trim()) {
          callback(new Error('两次密码不一致'))
        }
        callback()
      },

      handlePhoneCheck(rule, value, callback) {
        var params = {
          phone: value,
        };
        checkOnlyUser(params).then((res) => {
          if (res.success) {
            callback()
          } else {
            callback("手机号已存在!")
          }
        })
      },

      handlePasswordInputClick() {
        if (!this.isMobile()) {
          this.state.passwordLevelChecked = true
          return;
        }
        this.state.passwordLevelChecked = false
      },

      formHandleSubmit() {
        this.form.validateFields((err, values) => {
          if (!err) {
            var register = {
              // 左边的数据要和后端数据名称一样
              athleteName: values.username,
              password: values.password,
              mobile: values.mobile,
              nation: values.nation,
              gender: values.sex,
              idNo: values.idNo,
              coachId: values.coachId,
              sportCode: values.sportCode,
              homeAddress: values.homeAddress,
              father: values.father,
              fatherMobile: values.fatherMobile,
              mother: values.mother,
              motherMobile: values.motherMobile,
              smscode: values.captcha//验证码
            };
            postAction("/athleteRegister/add", register).then((res) => {
              if (!res.success) {
                this.registerFailed(res.message)
              } else {
                console.log("register的值:",register)
                this.$router.push({ name: 'registerResult', params: { ...values } })
              }
            })
          }
        })
      },

      getCaptcha(e) {
        e.preventDefault()
        let that = this
        this.form.validateFields(['mobile'], { force: true }, (err, values) => {
            if (!err) {
              this.state.smsSendBtn = true;
              let interval = window.setInterval(() => {
                if (that.state.time-- <= 0) {
                  that.state.time = 60;
                  that.state.smsSendBtn = false;
                  window.clearInterval(interval);
                }
              }, 1000);
              const hide = this.$message.loading('验证码发送中..', 0);
              const params = {
                mobile: values.mobile,
                smsmode: "1"
              };
              postAction("/sys/sms", params).then((res) => {
                if (!res.success) {
                  this.registerFailed(res.message);
                  setTimeout(hide, 0);
                }
                setTimeout(hide, 500);
              }).catch(err => {
                setTimeout(hide, 1);
                clearInterval(interval);
                that.state.time = 60;
                that.state.smsSendBtn = false;
                this.requestFailed(err);
              });
            }
          }
        );
      },
      registerFailed(message) {
        this.$notification['error']({
          message: "注册失败",
          description: message,
          duration: 2,
        });

      },
      requestFailed(err) {
        this.$notification['error']({
          message: '错误',
          description: ((err.response || {}).data || {}).message || "请求出现错误，请稍后再试",
          duration: 4,
        });
        this.registerBtn = false;
      },
//实现字段的customRender方法
      customRender: (text) => {
        //字典值替换通用方法
        return filterDictText(this.dictOptions, text);
      },
      inputCodeChange(e){
        this.inputCodeContent = e.target.value
        if(!e.target.value||0==e.target.value){
          this.inputCodeNull=true
        }else{
          this.inputCodeContent = this.inputCodeContent.toLowerCase()
          this.inputCodeNull=false
        }
      },
      validateInputCode(rule,value,callback){
        if(!value || this.verifiedCode===this.inputCodeContent){
          callback();
        }else{
          callback("您输入的验证码不正确!");
        }
      },
      generateCode(value){
        this.verifiedCode = value.toLowerCase()
      },
      getCaptcha (e) {
        e.preventDefault();
        let that = this;
        this.form.validateFields([ 'mobile' ], { force: true },(err,values) => {
            if(!values.mobile){
              that.cmsFailed("请输入手机号");
            }else if (!err) {
              this.state.smsSendBtn = true;
              let interval = window.setInterval(() => {
                if (that.state.time-- <= 0) {
                  that.state.time = 60;
                  that.state.smsSendBtn = false;
                  window.clearInterval(interval);
                }
              }, 1000);

              const hide = this.$message.loading('验证码发送中..', 0);
              let smsParams = {};
              smsParams.mobile=values.mobile;
              smsParams.smsmode="0";
              postAction("/sys/sms",smsParams)
                .then(res => {
                  if(!res.success){
                    setTimeout(hide, 0);
                    this.cmsFailed(res.message);
                  }
                  console.log(res);
                  setTimeout(hide, 500);
                })
                .catch(err => {
                  setTimeout(hide, 1);
                  clearInterval(interval);
                  that.state.time = 60;
                  that.state.smsSendBtn = false;
                  this.requestFailed(err);
                });
            }
          }
        );
      },
      handleSubmit () {
        let that = this
        let loginParams = {
          remember_me: that.formLogin.rememberMe
        };
        that.loginBtn = true;

        that.form.validateFields(['username', 'password', 'inputCode'], { force: true }, (err, values) => {
          if (!err) {
            loginParams.username = values.username
            // update-begin- --- author:scott ------ date:20190805 ---- for:密码加密逻辑暂时注释掉，有点问题
            //loginParams.password = md5(values.password)
            //loginParams.password = encryption(values.password,that.encryptedString.key,that.encryptedString.iv)
            loginParams.password = values.password
            // update-begin- --- author:scott ------ date:20190805 ---- for:密码加密逻辑暂时注释掉，有点问题
            let checkParams = this.$refs.jgraphicCodeRef.getLoginParam()
            loginParams.captcha = checkParams.checkCode
            loginParams.checkKey = checkParams.checkKey

            that.Login(loginParams).then((res) => {
              this.departConfirm(res)
            }).catch((err) => {
              that.requestFailed(err);
            });


          } else {
            that.loginBtn = false;
          }
        })

      },

    },
    watch: {
      'state.passwordLevel'(val) {
        console.log(val)

      }
    }
  }
</script>
<style lang="scss">
  .user-register {

    &.error {
      color: #ff0000;
    }

    &.warning {
      color: #ff7e05;
    }

    &.success {
      color: #52c41a;
    }

  }

  .user-layout-register {
    .ant-input-group-addon:first-child {
      background-color: #fff;
    }
  }
</style>
<style lang="scss" scoped>
  .user-layout-register {

    & > h3 {
      font-size: 16px;
      margin-bottom: 20px;
    }

    .getCaptcha {
      display: block;
      width: 100%;
      height: 40px;
    }

    .register-button {
      width: 50%;
    }

    .login {
      float: right;
      line-height: 40px;
    }
  }
</style>