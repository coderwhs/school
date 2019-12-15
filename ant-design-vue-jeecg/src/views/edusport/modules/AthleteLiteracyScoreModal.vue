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
        <a-form-item label="年级" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['grade']" :trigger-change="true" dictCode="edu_grade" placeholder="请选择年级"/>
        </a-form-item>
        <a-form-item label="教学年度" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'academicYear', validatorRules.academicYear]" placeholder="请输入教学年度"></a-input>
        </a-form-item>
        <a-form-item label="学期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['semester']" :trigger-change="true" dictCode="edu_semester" placeholder="请选择学期"/>
        </a-form-item>
        <a-form-item label="语文" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'chineseScore', validatorRules.chineseScore]" placeholder="请输入语文" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="数学" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'mathsScore', validatorRules.mathsScore]" placeholder="请输入数学" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="英语" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'englishScore', validatorRules.englishScore]" placeholder="请输入英语" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="政治" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'politicsScore', validatorRules.politicsScore]" placeholder="请输入政治" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="物理" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'physicsScore', validatorRules.physicsScore]" placeholder="请输入物理" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="化学" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'chemistryScore', validatorRules.chemistryScore]" placeholder="请输入化学" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="历史" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'historyScore', validatorRules.historyScore]" placeholder="请输入历史" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="地理" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'geographyScore', validatorRules.geographyScore]" placeholder="请输入地理" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="生物" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'biologyScore', validatorRules.biologyScore]" placeholder="请输入生物" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="音乐" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'musicScore', validatorRules.musicScore]" placeholder="请输入音乐" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="美术" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'artScore', validatorRules.artScore]" placeholder="请输入美术" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="体育与健康" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'peScore', validatorRules.peScore]" placeholder="请输入体育与健康" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="信息技术" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'itScore', validatorRules.itScore]" placeholder="请输入信息技术" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="通用技术" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'gtScore', validatorRules.gtScore]" placeholder="请输入通用技术" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="历史与社会" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'societyScore', validatorRules.societyScore]" placeholder="请输入历史与社会" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="科学" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'scienceScore', validatorRules.scienceScore]" placeholder="请输入科学" style="width: 100%"/>
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag'

  export default {
    name: "AthleteLiteracyScoreModal",
    components: { 
      JDictSelectTag,
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
        athleteId:{rules: [{ required: true, message: '请输入运动员!' }]},
        grade:{rules: [{ required: true, message: '请输入年级!' }]},
        academicYear:{rules: [{ required: true, message: '请输入教学年度!' }]},
        semester:{rules: [{ required: true, message: '请输入学期!' }]},
        chineseScore:{},
        mathsScore:{},
        englishScore:{},
        politicsScore:{},
        physicsScore:{},
        chemistryScore:{},
        historyScore:{},
        geographyScore:{},
        biologyScore:{},
        musicScore:{},
        artScore:{},
        peScore:{},
        itScore:{},
        gtScore:{},
        societyScore:{},
        scienceScore:{},
        },
        url: {
          add: "/edusport/athleteLiteracyScore/add",
          edit: "/edusport/athleteLiteracyScore/edit",
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
          this.form.setFieldsValue(pick(this.model,'athleteId','grade','academicYear','semester','chineseScore','mathsScore','englishScore','politicsScore','physicsScore','chemistryScore','historyScore','geographyScore','biologyScore','musicScore','artScore','peScore','itScore','gtScore','societyScore','scienceScore'))
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
        this.form.setFieldsValue(pick(row,'athleteId','grade','academicYear','semester','chineseScore','mathsScore','englishScore','politicsScore','physicsScore','chemistryScore','historyScore','geographyScore','biologyScore','musicScore','artScore','peScore','itScore','gtScore','societyScore','scienceScore'))
      },

      
    }
  }
</script>