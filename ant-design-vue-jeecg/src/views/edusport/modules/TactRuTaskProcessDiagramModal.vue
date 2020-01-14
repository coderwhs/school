<template>
  <a-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">
    <img alt="流程图" style="width: 50% height: 50%" :src="previewImage"/>
  </a-modal>
</template>

<script>
  import pick from 'lodash.pick'
  export default {
    name: "TactRuTaskProcessDiagramModal",
    name: 'ImagPreview',
    components: { 
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"流程图",
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
        previewVisible: false,
        previewImage: '',
        confirmLoading: false,
        validatorRules:{
        },
        url: {
          processDiagram:"/process/processDiagram",
        }
     
      }
    },
    created () {
    },
    methods: {
      tactRuTaskProcessDiagram(record) {
        this.previewImage = window._CONFIG['domianURL'] + this.url.processDiagram + "?procInstId=" + record.procInstId;
        this.visible = true;
        this.previewVisible = true;
      },
      handlePicCancel() {
         this.previewVisible = false
         this.previewImage=''
      },
    }
  }
</script>