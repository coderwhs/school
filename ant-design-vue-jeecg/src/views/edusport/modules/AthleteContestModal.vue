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
          <j-search-select-tag v-decorator="['athleteId']" dict="tb_edu_athlete,athlete_name,id" />
        </a-form-item>
        <a-form-item label="教练员" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag v-decorator="['coachId']" dict="tb_edu_coach,coach_name,id" />
        </a-form-item>
        <a-form-item label="比赛名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'contestName', validatorRules.contestName]" placeholder="请输入比赛名称"></a-input>
        </a-form-item>
        <a-form-item label="比赛项目" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag v-decorator="['contestSportCode']" dict="tb_edu_sport,sport_name,sport_code" />
        </a-form-item>
        <a-form-item label="比赛日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择比赛日期" v-decorator="[ 'contestDate', validatorRules.contestDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="比赛地点" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'contestAddress', validatorRules.contestAddress]" placeholder="请输入比赛地点"></a-input>
        </a-form-item>
        <a-form-item label="比赛小项" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'contestEvent', validatorRules.contestEvent]" placeholder="请输入比赛小项"></a-input>
        </a-form-item>
        <a-form-item label="名次" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'contestResult', validatorRules.contestResult]" placeholder="请输入名次"></a-input>
        </a-form-item>
        <a-form-item label="所在单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'athleteDepartment', validatorRules.athleteDepartment]" placeholder="请输入所在单位"></a-input>
        </a-form-item>
        <a-form-item label="授予技术等级" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['awardedTechGrade']" :trigger-change="true" dictCode="athlete_tech_grade" placeholder="请选择授予技术等级"/>
        </a-form-item>
        <a-form-item label="授予单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'awardedDepartment', validatorRules.awardedDepartment]" placeholder="请输入授予单位"></a-input>
        </a-form-item>
        <a-form-item label="授予日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择授予日期" v-decorator="[ 'awardedDate', validatorRules.awardedDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="个人风采" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-editor v-decorator="['athleteNews',{trigger:'input'}]"/>
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
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag'
  import JEditor from '@/components/jeecg/JEditor'

  export default {
    name: "AthleteContestModal",
    components: {
      JDate,
      JDictSelectTag,
      JSearchSelectTag,
      JEditor,
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
          athleteId:{rules: [{ required: true, message: '请输入运动员!' }]},
          coachId:{rules: [{ required: true, message: '请输入教练员!' }]},
          contestName:{rules: [{ required: true, message: '请输入比赛名称!' }]},
          contestSportCode:{rules: [{ required: true, message: '请输入比赛项目!' }]},
          contestDate:{},
          contestAddress:{},
          contestEvent:{},
          contestResult:{},
          athleteDepartment:{},
          awardedTechGrade:{},
          awardedDepartment:{},
          awardedDate:{},
          athleteNews:{},
        },
        url: {
          add: "/edusport/athleteContest/add",
          edit: "/edusport/athleteContest/edit",
        }

      }
    },
    created () {
    },
    methods: {
     /*   add () {
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
      this.form.setFieldsValue(pick(this.model,'athleteId','coachId','contestName','contestSportCode','contestDate','contestAddress','contestEvent','contestResult','athleteDepartment','awardedTechGrade','awardedDepartment','awardedDate','athleteNews'))
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
    this.form.setFieldsValue(pick(row,'athleteId','coachId','contestName','contestSportCode','contestDate','contestAddress','contestEvent','contestResult','athleteDepartment','awardedTechGrade','awardedDepartment','awardedDate','athleteNews'))
  },


  }
  }
</script>