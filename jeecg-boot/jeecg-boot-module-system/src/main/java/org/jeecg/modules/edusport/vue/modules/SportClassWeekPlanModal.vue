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

        <a-form-item label="训练队" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag v-decorator="['sportClassId']" dict="tb_edu_sport_class,class_name,id" />
        </a-form-item>
        <a-form-item label="训练计划名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'planName', validatorRules.planName]" placeholder="请输入训练计划名称"></a-input>
        </a-form-item>
        <a-form-item label="周开始日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择周开始日期" v-decorator="[ 'startDate', validatorRules.startDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="周结束日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择周结束日期" v-decorator="[ 'endTime', validatorRules.endTime]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="目的任务" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['taskGoal']" rows="4" placeholder="请输入目的任务"/>
        </a-form-item>
        <a-form-item label="周一训练量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'monWorkload', validatorRules.monWorkload]" placeholder="请输入周一训练量"></a-input>
        </a-form-item>
        <a-form-item label="周一训练强度" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'monIntensity', validatorRules.monIntensity]" placeholder="请输入周一训练强度"></a-input>
        </a-form-item>
        <a-form-item label="周一训练时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'monTime', validatorRules.monTime]" placeholder="请输入周一训练时间"></a-input>
        </a-form-item>
        <a-form-item label="周一训练内容" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['monContent']" rows="4" placeholder="请输入周一训练内容"/>
        </a-form-item>
        <a-form-item label="周二训练量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'tueWorkload', validatorRules.tueWorkload]" placeholder="请输入周二训练量"></a-input>
        </a-form-item>
        <a-form-item label="周二训练强度" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'tueIntensity', validatorRules.tueIntensity]" placeholder="请输入周二训练强度"></a-input>
        </a-form-item>
        <a-form-item label="周二训练时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'tueTime', validatorRules.tueTime]" placeholder="请输入周二训练时间"></a-input>
        </a-form-item>
        <a-form-item label="周二训练内容" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['tueContent']" rows="4" placeholder="请输入周二训练内容"/>
        </a-form-item>
        <a-form-item label="周三训练量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'wedWorkload', validatorRules.wedWorkload]" placeholder="请输入周三训练量"></a-input>
        </a-form-item>
        <a-form-item label="周三训练强度" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'wedIntensity', validatorRules.wedIntensity]" placeholder="请输入周三训练强度"></a-input>
        </a-form-item>
        <a-form-item label="周三训练时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'wedTime', validatorRules.wedTime]" placeholder="请输入周三训练时间"></a-input>
        </a-form-item>
        <a-form-item label="周三训练内容" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['wedContent']" rows="4" placeholder="请输入周三训练内容"/>
        </a-form-item>
        <a-form-item label="周四训练量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'thuWorkload', validatorRules.thuWorkload]" placeholder="请输入周四训练量"></a-input>
        </a-form-item>
        <a-form-item label="周四训练强度" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'thuIntensity', validatorRules.thuIntensity]" placeholder="请输入周四训练强度"></a-input>
        </a-form-item>
        <a-form-item label="周四训练时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'thuTime', validatorRules.thuTime]" placeholder="请输入周四训练时间"></a-input>
        </a-form-item>
        <a-form-item label="周四训练内容" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['thuContent']" rows="4" placeholder="请输入周四训练内容"/>
        </a-form-item>
        <a-form-item label="周五训练量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'friWorkload', validatorRules.friWorkload]" placeholder="请输入周五训练量"></a-input>
        </a-form-item>
        <a-form-item label="周五训练强度" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'friIntensity', validatorRules.friIntensity]" placeholder="请输入周五训练强度"></a-input>
        </a-form-item>
        <a-form-item label="周五训练时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'friTime', validatorRules.friTime]" placeholder="请输入周五训练时间"></a-input>
        </a-form-item>
        <a-form-item label="周五训练内容" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['friContent']" rows="4" placeholder="请输入周五训练内容"/>
        </a-form-item>
        <a-form-item label="周六训练量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'satWorkload', validatorRules.satWorkload]" placeholder="请输入周六训练量"></a-input>
        </a-form-item>
        <a-form-item label="周六训练强度" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'satIntensity', validatorRules.satIntensity]" placeholder="请输入周六训练强度"></a-input>
        </a-form-item>
        <a-form-item label="周六训练时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'satTime', validatorRules.satTime]" placeholder="请输入周六训练时间"></a-input>
        </a-form-item>
        <a-form-item label="周六训练内容" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['satContent']" rows="4" placeholder="请输入周六训练内容"/>
        </a-form-item>
        <a-form-item label="周日训练量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'sunWorkload', validatorRules.sunWorkload]" placeholder="请输入周日训练量"></a-input>
        </a-form-item>
        <a-form-item label="周日训练强度" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'sunIntensity', validatorRules.sunIntensity]" placeholder="请输入周日训练强度"></a-input>
        </a-form-item>
        <a-form-item label="周日训练时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'sunTime', validatorRules.sunTime]" placeholder="请输入周日训练时间"></a-input>
        </a-form-item>
        <a-form-item label="周日训练内容" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['sunContent']" rows="4" placeholder="请输入周日训练内容"/>
        </a-form-item>
        <a-form-item label="本周完成训练天数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'weekDays', validatorRules.weekDays]" placeholder="请输入本周完成训练天数"></a-input>
        </a-form-item>
        <a-form-item label="本周完成训练课数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'weekCourses', validatorRules.weekCourses]" placeholder="请输入本周完成训练课数"></a-input>
        </a-form-item>
        <a-form-item label="本周完成训练时数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'weekHours', validatorRules.weekHours]" placeholder="请输入本周完成训练时数"></a-input>
        </a-form-item>
        <a-form-item label="本周运动量完成情况" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['weekWorkload']" rows="4" placeholder="请输入本周运动量完成情况"/>
        </a-form-item>
        <a-form-item label="本周训练小结" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['weekSummary']" rows="4" placeholder="请输入本周训练小结"/>
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JDate from '@/components/jeecg/JDate'  
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag'

  export default {
    name: "SportClassWeekPlanModal",
    components: { 
      JDate,
      JSearchSelectTag,
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
        sportClassId:{rules: [{ required: true, message: '请输入训练队!' }]},
        planName:{rules: [{ required: true, message: '请输入训练计划名称!' }]},
        startDate:{rules: [{ required: true, message: '请输入周开始日期!' }]},
        endTime:{rules: [{ required: true, message: '请输入周结束日期!' }]},
        taskGoal:{rules: [{ required: true, message: '请输入目的任务!' }]},
        monWorkload:{},
        monIntensity:{},
        monTime:{},
        monContent:{},
        tueWorkload:{},
        tueIntensity:{},
        tueTime:{},
        tueContent:{},
        wedWorkload:{},
        wedIntensity:{},
        wedTime:{},
        wedContent:{},
        thuWorkload:{},
        thuIntensity:{},
        thuTime:{},
        thuContent:{},
        friWorkload:{},
        friIntensity:{},
        friTime:{},
        friContent:{},
        satWorkload:{},
        satIntensity:{},
        satTime:{},
        satContent:{},
        sunWorkload:{},
        sunIntensity:{},
        sunTime:{},
        sunContent:{},
        weekDays:{},
        weekCourses:{},
        weekHours:{},
        weekWorkload:{},
        weekSummary:{},
        },
        url: {
          add: "/edusport/sportClassWeekPlan/add",
          edit: "/edusport/sportClassWeekPlan/edit",
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
          this.form.setFieldsValue(pick(this.model,'sportClassId','planName','startDate','endTime','taskGoal','monWorkload','monIntensity','monTime','monContent','tueWorkload','tueIntensity','tueTime','tueContent','wedWorkload','wedIntensity','wedTime','wedContent','thuWorkload','thuIntensity','thuTime','thuContent','friWorkload','friIntensity','friTime','friContent','satWorkload','satIntensity','satTime','satContent','sunWorkload','sunIntensity','sunTime','sunContent','weekDays','weekCourses','weekHours','weekWorkload','weekSummary'))
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
        this.form.setFieldsValue(pick(row,'sportClassId','planName','startDate','endTime','taskGoal','monWorkload','monIntensity','monTime','monContent','tueWorkload','tueIntensity','tueTime','tueContent','wedWorkload','wedIntensity','wedTime','wedContent','thuWorkload','thuIntensity','thuTime','thuContent','friWorkload','friIntensity','friTime','friContent','satWorkload','satIntensity','satTime','satContent','sunWorkload','sunIntensity','sunTime','sunContent','weekDays','weekCourses','weekHours','weekWorkload','weekSummary'))
      },

      
    }
  }
</script>