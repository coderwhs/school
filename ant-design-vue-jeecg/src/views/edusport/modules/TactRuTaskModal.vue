<template>
  <a-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @cancel="handleCancel"
    @ok="handleOk"
    cancelText="关闭">

        <img alt="流程图" style="width: 100% height: 80%" :src="previewImage"/>

  </a-modal>
</template>

<script>

  // import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  // import JDate from '@/components/jeecg/JDate'
  // import { getUploadFileList,getFilePaths } from '@/utils/commonUploadFile.js'
  // import JUpload from '@/components/jeecg/JUpload'

  export default {
    name: "TactRuTaskModal",
    components: { 
      // JDate,
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        width:400,
        height:400,
        visible: false,
        model: {},

        previewVisible: false,
        previewImage: '',
        confirmLoading: false,

        url: {
          processDiagram:"/process/processDiagram"
        },
      }
    },
    created () {
    },
    methods: {
      showModal() {
        this.title = "abcde";
      },
      add () {
        // this.edit({});
      },
      edit (record) {
        console.log("processDiagram");
        this.previewImage = window._CONFIG['domianURL'] + this.url.processDiagram + "?procInstId=" + record.procInstId;
        this.visible = true;
        this.previewVisible = true;
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleCancel () {
        this.close()
      },

      handleOk() {
        this.close()
      },

      handlePicCancel() {
        this.previewVisible = false
        this.previewImage=''
      },
    }
  }
</script>