<template>
<!--  <div class="clearfix">-->
<!--    <a-upload-->
<!--      action="uploadAction"-->
<!--      listType="picture-card"-->
<!--      :fileList="fileList"-->
<!--      @preview="handlePreview"-->
<!--      @change="handleChange"-->
<!--    >-->
<!--      <div v-if="fileList.length < 3">-->
<!--        <a-icon type="plus" />-->
<!--        <div class="ant-upload-text">Upload</div>-->
<!--      </div>-->
<!--    </a-upload>-->
<!--    <a-modal :visible="previewVisible" :footer="null" @cancel="handleCancel">-->
<!--      <img alt="example" style="width: 100%" :src="previewImage" />-->
<!--    </a-modal>-->
<!--  </div>-->
  <a-row>
    <a-col>sysUserCode: {{sysUserCode}}</a-col>
    <a-col>sysUserName: {{sysUserName}}</a-col>
    <a-col>sysOrgCode: {{sysOrgCode}}</a-col>
    <a-col>sysDate: {{sysDate}}</a-col>
    <a-col>sysTime: {{sysTime}}</a-col>
  </a-row>

</template>
<script>
  import {JeecgListMixin} from '@/mixins/JeecgListMixin'

  export default {
    name: "OSSFileList",
    mixins: [JeecgListMixin],

    data() {
      return {
        previewVisible: false,
        previewImage: '',
        fileList: [
          {
            uid: '-1',
            name: 'xxx.png',
            status: 'done',
            url: 'https://zos.alipayobjects.com/rmsportal/jkjgkEfvpUPVyRjUImniVslZfWPnJuuZ.png',
          },
        ],
      };
    },
    methods: {
      handleCancel() {
        this.previewVisible = false;
      },
      handlePreview(file) {
        this.previewImage = file.url || file.thumbUrl;
        this.previewVisible = true;
      },
      // handleChange({ fileList }) {
      //   this.fileList = fileList;
      // },
    },
  };
</script>
<style>
  /* you can make up upload button and sample style by using stylesheets */
  .ant-upload-select-picture-card i {
    font-size: 32px;
    color: #999;
  }

  .ant-upload-select-picture-card .ant-upload-text {
    margin-top: 8px;
    color: #666;
  }
</style>
