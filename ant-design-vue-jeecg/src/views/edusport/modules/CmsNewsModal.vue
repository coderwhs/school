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

        <a-form-item label="新闻类别" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['newsCat']" :trigger-change="true" dictCode="news_cat" placeholder="请选择新闻类别"/>
        </a-form-item>
        <a-form-item label="标题" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'newsTitle', validatorRules.newsTitle]" placeholder="请输入标题"></a-input>
        </a-form-item>
        <a-form-item label="副标题" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'newsSubTitle', validatorRules.newsSubTitle]" placeholder="请输入副标题"></a-input>
        </a-form-item>
        <a-form-item label="链接地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'newsLink', validatorRules.newsLink]" placeholder="请输入链接地址"></a-input>
        </a-form-item>
        <a-form-item label="置顶状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="radio" v-decorator="['topStatus']" :trigger-change="true" dictCode="enable_status" placeholder="请选择置顶状态"/>
        </a-form-item>
        <a-form-item label="显示顺序" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'newsOrder', validatorRules.newsOrder]" placeholder="请输入显示顺序" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="启用状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="radio" v-decorator="['enableStatus']" :trigger-change="true" dictCode="enable_status" placeholder="请选择启用状态"/>
        </a-form-item>
        <a-form-item label="来源" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'newsFrom', validatorRules.newsFrom]" placeholder="请输入来源"></a-input>
        </a-form-item>
        <a-form-item label="报道时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择报道时间" v-decorator="[ 'publishDate', validatorRules.publishDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="标题图片" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-upload v-decorator="['titleImage']" :trigger-change="true"></j-upload>
        </a-form-item>
        <a-form-item label="摘要" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'newsSummary', validatorRules.newsSummary]" placeholder="请输入摘要"></a-input>
        </a-form-item>
        <a-form-item label="内容" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-editor v-decorator="['newsContent',{trigger:'input'}]"/>
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JDate from '@/components/jeecg/JDate'  
  import JUpload from '@/components/jeecg/JUpload'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import JEditor from '@/components/jeecg/JEditor'

  export default {
    name: "CmsNewsModal",
    components: { 
      JDate,
      JUpload,
      JDictSelectTag,
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
          newsCat:{rules: [{ required: true, message: '请输入新闻类别!' }]},
          newsTitle:{rules: [{ required: true, message: '请输入标题!' }]},
          newsSubTitle:{},
          newsLink:{},
          topStatus:{},
          newsOrder:{rules: [{ required: true, message: '请输入显示顺序!' }]},
          enableStatus:{rules: [{ required: true, message: '请输入启用状态!' }]},
          newsFrom:{},
          publishDate:{},
          titleImage:{},
          newsSummary:{},
          newsContent:{},
        },
        url: {
          add: "/edusport/cmsNews/add",
          edit: "/edusport/cmsNews/edit",
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
          this.form.setFieldsValue(pick(this.model,'newsCat','newsTitle','newsSubTitle','newsLink','topStatus','newsOrder','enableStatus','newsFrom','publishDate','titleImage','newsSummary','newsContent'))
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
        this.form.setFieldsValue(pick(row,'newsCat','newsTitle','newsSubTitle','newsLink','topStatus','newsOrder','enableStatus','newsFrom','publishDate','titleImage','newsSummary','newsContent'))
      },

      
    }
  }
</script>