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

        <a-form-item label="训练队" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-form-container disabled>
            <j-search-select-tag v-decorator="['sportClassId', validatorRules.sportClassId]" :dict="fnDictCodeSportClass" />
          </j-form-container>
        </a-form-item>
        <a-form-item label="运动员" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag v-decorator="['athleteId', validatorRules.athleteId]" dict="tb_edu_athlete,athlete_name,id" />
        </a-form-item>
        <a-form-item label="入队日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择入队日期" v-decorator="[ 'attendDate', validatorRules.attendDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="比赛最好成绩" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'athleteBestScore', validatorRules.athleteBestScore]" placeholder="请输入比赛最好成绩"></a-input>
        </a-form-item>
        <a-form-item label="获得等级" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['athleteAwardTechGrade']" :trigger-change="true" dictCode="athlete_tech_grade" placeholder="请选择获得等级"/>
        </a-form-item>
        <a-form-item label="破纪录状况" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'aboutBreakRecord', validatorRules.aboutBreakRecord]" placeholder="请输入破纪录状况"></a-input>
        </a-form-item>
        <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['remark', validatorRules.remark]" rows="4" placeholder="请输入备注"/>
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
    name: "AthleteSportClassModal",
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
        drawerWidth:800,
        disableSubmit:false,
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
        dictCodeSportClass: '',
        dictCodeAthlete: '',

        validatorRules:{
          sportClassId:{rules: [{ required: true, message: '请输入训练队!' }]},
          athleteId:{rules: [{ required: true, message: '请输入运动员!' }]},
          attendDate:{rules: [{ required: true, message: '请输入入队日期!' }]},
          athleteBestScore:{},
          athleteAwardTechGrade:{},
          aboutBreakRecord:{},
          remark:{},
        },
        url: {
          add: "/edusport/athleteSportClass/add",
          edit: "/edusport/athleteSportClass/edit",
        }

      }
    },
    created () {
    },
    computed: {
      fnDictCodeSportClass: function() {
        return this.dictCodeSportClass;
      },
      fnDictCodeAthlete: function() {
        return this.dictCodeAthlete;
      }
    },
    methods: {
      add () {
        this.edit({});
      },
      add(sportClassId) {
        this.hiding = true;
        if (sportClassId) {
          this.sportClassId = sportClassId;
          this.edit({sportClassId: sportClassId});
        } else {
          this.$message.warning("请选择一个训练队信息");
        }
      },
      edit (record) {
        this.resetScreenSize(); // 调用此方法,根据屏幕宽度自适应调整抽屉的宽度
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'sportClassId','athleteId','attendDate','athleteBestScore','athleteAwardTechGrade','aboutBreakRecord','remark'))
        })

        // 初始化训练队字典
        if (this.model.sportClassId) {
          this.dictCodeSportClass = "tb_edu_sport_class, class_name, id, id = '" + this.model.sportClassId + "'";
        }

        // 初始化运动员字典
        if (this.model.athleteId) {
          this.dictCodeAthlete = "tb_edu_athlete, athlete_name, id, id = '" + this.model.athleteId + "'";
        } else {
          this.dictCodeAthlete = "tb_edu_athlete, athlete_name, id";
        }
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
        this.form.setFieldsValue(pick(row,'sportClassId','athleteId','attendDate','athleteBestScore','athleteAwardTechGrade','aboutBreakRecord','remark'))
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