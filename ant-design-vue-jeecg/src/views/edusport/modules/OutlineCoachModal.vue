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

        <a-form-item label="教练员" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag v-decorator="['coachId']" dict="tb_edu_coach,coach_name,id" />
        </a-form-item>
        <a-form-item label="大纲" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag v-decorator="['outlineId']" dict="tb_edu_athlete_selection_test,test_name,id" />
        </a-form-item>
        <a-form-item label="大纲组别" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag v-decorator="['groupId']" dict="tb_edu_athlete_selection_group,group_name,id" />
        </a-form-item>
        <a-form-item label="运动项目" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag v-decorator="['sportId']" dict="tb_edu_sport,sport_name,sport_code" @change="sportChange"/>
        </a-form-item>
        <a-form-item label="运动员" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-multi-select-tag type="list_multi" v-decorator="['althleteNos']" :trigger-change="true" dictCode="tb_edu_athlete,athlete_name,athlete_no" placeholder="请选择运动员"/>
        </a-form-item>
        <a-form-item label="测试项目" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-multi-select-tag type="list_multi" v-decorator="['eventCodes']" :trigger-change="true" dictCode="tb_edu_sport_small,event_name,event_code" placeholder="请选择测试项目"/>
        </a-form-item>
        <a-form-item label="测试日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择测试日期" v-decorator="[ 'testDate', validatorRules.testDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="单据状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['state']" :trigger-change="true" dictCode="bill_state" placeholder="请选择单据状态"/>
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { getAction, httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JDate from '@/components/jeecg/JDate'  
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import JMultiSelectTag from "@/components/dict/JMultiSelectTag"
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag'
  import {initDictOptions, filterMultiDictText} from '@/components/dict/JDictSelectUtil'

  export default {
    name: "OutlineCoachModal",
    components: { 
      JDate,
      JDictSelectTag,
      JMultiSelectTag,
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
        coachId:{rules: [{ required: true, message: '请输入教练员!' }]},
        outlineId:{},
        groupId:{},
        sportId:{},
        althleteNos:{},
        eventCodes:{},
        testDate:{},
        state:{},
        },
        url: {
          add: "/edusport/outlineCoach/add",
          edit: "/edusport/outlineCoach/edit",
        },
        dictOptions:{
         althleteNos:[]
        },
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
          this.form.setFieldsValue(pick(this.model,'coachId','outlineId','groupId','sportId','althleteNos','eventCodes','testDate','state'))
        })
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },

      // 运动项目选择.
      sportChange(selectedValue){
        let params = {};
        let code = 'tb_edu_athlete,athlete_name,athlete_no,major_sport=' + selectedValue;
        getAction(`/sys/dict/getDictItems/${code}`,params).then((res) => 
        {
          if (res.success) {
            try{
              console.log("查询结果：" + JSON.stringify(res.result));
              this.dictOptions.althleteNos = [];
              this.$set(this.dictOptions, 'althleteNos', res.result);
              this.form.setFieldsValue(pick(this.model,'althleteNos'));
            } catch(e){
              this.$message.warning(e.message);
            }
          } else {
            this.$message.warning(res.message);
          }
        }).finally(() => {
          // this.loadData(1);
        })
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
        this.form.setFieldsValue(pick(row,'coachId','outlineId','groupId','sportId','althleteNos','eventCodes','testDate','state'))
      },

      
    }
  }
</script>