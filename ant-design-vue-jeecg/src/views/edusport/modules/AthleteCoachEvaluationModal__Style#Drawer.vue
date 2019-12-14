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

        <a-form-item label="训练队成员" :labelCol="labelCol" :wrapperCol="wrapperCol">
          {{athleteName}}
        </a-form-item>

        <a-form-item label="训练队成员" :labelCol="labelCol" :wrapperCol="wrapperCol" v-if="false">
          <j-search-select-tag v-decorator="['athleteSportClassId']" dict="tb_edu_athlete_sport_class,athlete_id,id" />
        </a-form-item>
        <a-form-item label="开始日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择开始日期" v-decorator="[ 'startDate', validatorRules.startDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="结束日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择结束日期" v-decorator="[ 'endDate', validatorRules.endDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="总体评价" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['evaluation']" rows="4" placeholder="请输入总体评价"/>
        </a-form-item>
        <a-form-item label="评价教练" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag v-decorator="['evaluator']" dict="tb_edu_coach,coach_name,id" />
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
  import {initDictOptions, filterMultiDictText} from '@/components/dict/JDictSelectUtil'

  export default {
    name: "AthleteCoachEvaluationModal",
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
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },

        athleteName:'',
        confirmLoading: false,
        validatorRules:{
          athleteSportClassId:{rules: [{ required: true, message: '请输入训练队成员!' }]},
          startDate:{rules: [{ required: true, message: '请输入开始日期!' }]},
          endDate:{rules: [{ required: true, message: '请输入结束日期!' }]},
          evaluation:{rules: [{ required: true, message: '请输入总体评价!' }]},
          evaluator:{rules: [{ required: true, message: '请输入评价教练!' }]},
        },
        url: {
          add: "/edusport/athleteCoachEvaluation/add",
          edit: "/edusport/athleteCoachEvaluation/edit",
        },
        dictOptions:{
        },
      }
    },
    created () {
    },
    methods: {
      // 初始化字典并获取训练队成员
      initDictConfig(){
        initDictOptions('tb_edu_athlete_sport_class,athlete_id,id').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'athleteSportClassId', res.result)
          }
        })
        initDictOptions('tb_edu_athlete,athlete_name,id').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'athlete_id', res.result)
          }
        })

        // initDictOptions 因方法耗时，因此延时500ms获取 训练队成员姓名
        // 最稳妥的方法
        let callback = (function ()  {
          let athleteSportClassId = this.model.athleteSportClassId
          if(!athleteSportClassId){
            return ''
          }else {
            let athlete_id = filterMultiDictText(this.dictOptions['athleteSportClassId'], athleteSportClassId+"")
            this.athleteName = filterMultiDictText(this.dictOptions['athlete_id'], athlete_id + "")
          }
        }).bind(this)
        setTimeout(callback, 500)
      },
      add () {
        this.edit({});
      },
      add(athleteSportClassId) {
        this.hiding = true;
        if (athleteSportClassId) {
          this.athleteSportClassId = athleteSportClassId;
          this.edit({athleteSportClassId}, '');
        } else {
          this.$message.warning("请选择一个训练队成员信息");
        }
      },
      edit (record) {
        this.resetScreenSize(); // 调用此方法,根据屏幕宽度自适应调整抽屉的宽度
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'athleteSportClassId','startDate','endDate','evaluation','evaluator'))
        })

        // 初始化字典并获取训练队成员
        this.initDictConfig()
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
        this.form.setFieldsValue(pick(row,'athleteSportClassId','startDate','endDate','evaluation','evaluator'))
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