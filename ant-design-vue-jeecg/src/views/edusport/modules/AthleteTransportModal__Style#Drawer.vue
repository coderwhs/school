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

        <a-form-item label="运动员" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag v-decorator="['athleteId']" dict="tb_edu_athlete,athlete_name,id" />
        </a-form-item>
        <a-form-item label="运动项目" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag v-decorator="['sportCode']" dict="tb_edu_sport,sport_name,sport_code" />
        </a-form-item>
        <a-form-item label="运动员等级" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['athleteTechGrade']" :trigger-change="true" dictCode="athlete_tech_grade" placeholder="请选择运动员等级"/>
        </a-form-item>
        <a-form-item label="输送单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'transportDepartment', validatorRules.transportDepartment]" placeholder="请输入输送单位"></a-input>
        </a-form-item>
        <a-form-item label="输送教练员" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag v-decorator="['transportCoachId']" dict="tb_edu_coach,coach_name,id" />
        </a-form-item>
        <a-form-item label="输送时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择输送时间" v-decorator="[ 'transportDate', validatorRules.transportDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="吸收单位类别" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['acceptDepartmentType']" :trigger-change="true" dictCode="accept_department_type" placeholder="请选择吸收单位类别"/>
        </a-form-item>
        <a-form-item label="吸收单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'acceptDepartment', validatorRules.acceptDepartment]" placeholder="请输入吸收单位"></a-input>
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
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag'

  export default {
    name: "AthleteTransportModal",
    components: {
      JDate,
      JDictSelectTag,
      JSearchSelectTag,
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        width:800,
        visible: false,
        drawerWidth:800,
        disableSubmit:false,
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
          athleteId:{rules: [{ required: true, message: '请输入运动员!' }]},
          sportCode:{rules: [{ required: true, message: '请输入运动项目!' }]},
          athleteTechGrade:{rules: [{ required: true, message: '请输入运动员等级!' }]},
          transportDepartment:{rules: [{ required: true, message: '请输入输送单位!' }]},
          transportCoachId:{rules: [{ required: true, message: '请输入输送教练员!' }]},
          transportDate:{rules: [{ required: true, message: '请输入输送时间!' }]},
          acceptDepartmentType:{rules: [{ required: true, message: '请输入吸收单位类别!' }]},
          acceptDepartment:{rules: [{ required: true, message: '请输入吸收单位!' }]},
        },
        url: {
          add: "/edusport/athleteTransport/add",
          edit: "/edusport/athleteTransport/edit",
        }

      }
    },
    created () {
    },
    methods: {
      /*add () {
        this.edit({});
      },*/
      add(athleteId){/* Tab修改@2019-12-12 */
        this.hiding = true;

        if (athleteId) {
          this.athleteId = athleteId;
          this.edit({athleteId:athleteId},'');
        } else {
          this.$message.warning("请选择一条运动员信息");
        }
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'athleteId','sportCode','athleteTechGrade','transportDepartment','transportCoachId','transportDate','acceptDepartmentType','acceptDepartment'))
        })
      },
      close () {
        this.$emit('close');
        this.visible = false;
        this.disableSubmit = false;
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
        this.form.setFieldsValue(pick(row,'athleteId','sportCode','athleteTechGrade','transportDepartment','transportCoachId','transportDate','acceptDepartmentType','acceptDepartment'))
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