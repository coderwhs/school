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

        <a-form-item label="教练员" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-form-container disabled>
            <j-search-select-tag v-decorator="['coachId', validatorRules.coachId]" dict="tb_edu_coach,coach_name,id" />
          </j-form-container>
        </a-form-item>
        <a-form-item label="运动项目" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag v-decorator="['sportCode', validatorRules.sportCode]" dict="tb_edu_sport,sport_name,sport_code" />
        </a-form-item>
        <a-form-item label="参加时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择参加时间" v-decorator="[ 'attendDate', validatorRules.attendDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="获得最高等级" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['awardedHighestGrade', validatorRules.awardedHighestGrade]" :trigger-change="true" dictCode="athlete_tech_grade" placeholder="请选择获得最高等级"/>
        </a-form-item>
        <a-form-item label="获得年度(年)" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'awardedDate', validatorRules.awardedDate]" placeholder="请输入获得年度(年)"></a-input>
        </a-form-item>
        <a-form-item label="最高训练单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'highestTrainingUnit', validatorRules.highestTrainingUnit]" placeholder="请输入最高训练单位"></a-input>
        </a-form-item>
        <a-form-item label="训练年度(年)" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'trainingDate', validatorRules.trainingDate]" placeholder="请输入训练年度(年)"></a-input>
        </a-form-item>
        <a-form-item label="比赛小项" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'contestEvent', validatorRules.contestEvent]" placeholder="请输入比赛小项"></a-input>
        </a-form-item>
        <a-form-item label="最好成绩" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'contestScore', validatorRules.contestScore]" placeholder="请输入最好成绩"></a-input>
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
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag'

  export default {
    name: "CoachSportResumeModal",
    components: {
      JDate,
      JSearchSelectTag,
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        width:800,
        drawerWidth:800,
        disableSubmit:false,
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

        confirmLoading: false,
        validatorRules:{
          coachId:{rules: [{ required: true, message: '请选择教练员!' }]},
          sportCode:{rules: [{ required: true, message: '请选择运动项目!' }]},
          attendDate:{rules: [{ required: true, message: '请选择参加时间!' }]},
          awardedHighestGrade:{rules: [{ required: true, message: '请选择获得最高等级!' }]},
          awardedDate:{rules: [{ required: true, message: '请输入获得年度(年)!' }]},
          highestTrainingUnit:{rules: [{ required: true, message: '请输入最高训练单位!' }]},
          trainingDate:{rules: [{ required: true, message: '请输入训练年度(年)!' }]},
          contestEvent:{rules: [{ required: true, message: '请选择比赛小项!' }]},
          contestScore:{rules: [{ required: true, message: '请输入最好成绩!' }]},
        },
        url: {
          add: "/edusport/coachSportResume/add",
          edit: "/edusport/coachSportResume/edit",
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
        this.resetScreenSize(); // 调用此方法,根据屏幕宽度自适应调整抽屉的宽度
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'coachId','sportCode','attendDate','awardedHighestGrade','awardedDate','highestTrainingUnit','trainingDate','contestEvent','contestScore'))
        })
      },
      close () {
        this.$emit('close');
        this.visible = false;
        this.disableSubmit = false;
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
        this.form.setFieldsValue(pick(row,'coachId','sportCode','attendDate','awardedHighestGrade','awardedDate','highestTrainingUnit','trainingDate','contestEvent','contestScore'))
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