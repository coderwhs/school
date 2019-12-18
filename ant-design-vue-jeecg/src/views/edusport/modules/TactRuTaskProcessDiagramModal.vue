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
      aaaaa
    </a-spin>
  </a-modal>
</template>

<script>
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

        confirmLoading: false,
        validatorRules:{
        },
        url: {
        }
     
      }
    },
    created () {
    },
    methods: {
    }
  }
</script>