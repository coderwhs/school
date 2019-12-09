<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="文件名称">
              <a-input placeholder="请输入文件名称" v-model="queryParam.fileName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="文件地址">
              <a-input placeholder="请输入文件地址" v-model="queryParam.url"></a-input>
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
    </div>

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <!--      <a-button type="primary" icon="download" @click="handleExportXls('文件列表')">导出</a-button>-->
      <a-upload
          name="file"
          :multiple="false"
          :action="uploadAction"
          :headers="tokenHeader"
          :showUploadList="false"
          :beforeUpload="beforeUpload"
          @change="handleChange">
        <a-button>
          <a-icon type="upload"/>
          文件上传
        </a-button>
      </a-upload>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a
          style="font-weight: 600">{{
        selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
          ref="table"
          size="middle"
          bordered
          rowKey="id"
          :columns="columns"
          :dataSource="dataSource"
          :pagination="ipagination"
          :loading="loading"
          :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
          @change="handleTableChange">

        <span slot="action" slot-scope="text, record">
            <a @click="handleOssFileShow(record)">播放</a>
            <a-divider type="vertical"/>
            <a @click="ossDelete(record.id)">删除</a>
        </span>
      </a-table>
    </div>
    <!-- table区域-end -->

    <!-- 播放oss视频 -->
    <a-modal
      :title="ossVideoReplayTitle"
      :visible="ossVideoReplayVisible"
      @cancel="handleOssVideoReplayClose">
      <template slot="footer">
        <a-button @click="handleOssVideoReplayClose">关闭</a-button>
      </template>
      <video-player class="video-player vjs-custom-skin"
                    ref="videoPlayer"
                    :playsinline="true"
                    :options="playerOptions"></video-player>
    </a-modal>

    <!-- 播放oss图片 -->
    <a-modal
      :title="ossImgPreviewTitle"
      :visible="ossImgPreviewVisible"
      @cancel="handleOssImgPreviewClose">
      <template slot="footer">
        <a-button @click="handleOssImgPreviewClose">关闭</a-button>
      </template>
      <img :src="ossImgPreviewUrl" style="width: 100%" >
    </a-modal>

  </a-card>
</template>

<script>
  import {JeecgListMixin} from '@/mixins/JeecgListMixin'
  import { getAction } from '@/api/manage'

  export default {
    name: "OSSFileList",
    mixins: [JeecgListMixin],
    data() {
      return {
        description: '文件列表',
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key: 'rowIndex',
            width: 60,
            align: "center",
            customRender: function (t, r, index) {
              return parseInt(index) + 1;
            }
          },
          {
            title: '文件名称',
            align: "center",
            dataIndex: 'fileName'
          },
          {
            title: '文件地址',
            align: "center",
            dataIndex: 'url'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align: "center",
            scopedSlots: {customRender: 'action'},
          }
        ],
        url: {
          upload: "/oss/file/upload",
          list: "/oss/file/list",
          delete: "/oss/file/delete",
          replay: "/oss/file/replay"
        },
        //图片预览、视频播放参数
        ossImgPreviewTitle: '',
        ossImgPreviewVisible: false,
        ossImgPreviewUrl: '',
        ossVideoReplayTitle: '',
        ossVideoReplayVisible: false,
        //视频插件参数
        playerOptions: {
          playbackRates: [0.7, 1.0, 1.5, 2.0], //播放速度
          autoplay: false, //如果true,浏览器准备好时开始回放。
          muted: false, // 默认情况下将会消除任何音频。
          loop: false, // 导致视频一结束就重新开始。
          preload: 'auto', // 建议浏览器在<video>加载元素后是否应该开始下载视频数据。auto浏览器选择最佳行为,立即开始加载视频（如果浏览器支持）
          language: 'zh-CN',
          aspectRatio: '16:9', // 将播放器置于流畅模式，并在计算播放器的动态大小时使用该值。值应该代表一个比例 - 用冒号分隔的两个数字（例如"16:9"或"4:3"）
          fluid: true, // 当true时，Video.js player将拥有流体大小。换句话说，它将按比例缩放以适应其容器。
          sources: [{
            type: "video/mp4",
            src: "" //视频url地址
          }],
          poster: "https://stillmed.olympic.org/media/Images/OlympicOrg/Games/Winter/Beijing_2022/Beijing_2022_emblem.png?interpolation=lanczos-none&resize=340:340", //你的封面地址
          // width: document.documentElement.clientWidth,
          notSupportedMessage: '此视频暂无法播放，请稍后再试', //允许覆盖Video.js无法播放媒体源时显示的默认信息。
          controlBar: {
            timeDivider: true,
            durationDisplay: true,
            remainingTimeDisplay: false,
            fullscreenToggle: true  //全屏按钮
          }
        }
      }
    },
    computed: {
      uploadAction() {
        return window._CONFIG['domianURL'] + this.url.upload;
      }
    },
    methods: {
      beforeUpload(file) {
        var fileType = file.type;
        if (fileType === 'image') {
          if (fileType.indexOf('image') < 0) {
            this.$message.warning('请上传图片');
            return false;
          }
        } else if (fileType === 'file') {
          if (fileType.indexOf('image') >= 0) {
            this.$message.warning('请上传文件');
            return false;
          }
        }
        return true
      },
      handleChange(info) {
        if (info.file.status === 'done') {
          if (info.file.response.success) {
            this.loadData()
            this.$message.success(`${info.file.name} 上传成功!`);
          } else {
            this.$message.error(`${info.file.name} 上传失败.`);
          }
        } else if (info.file.status === 'error') {
          this.$message.error(`${info.file.name} 上传失败.`);
        }
      },
      ossDelete(id) {
        var that = this;
        that.$confirm({
          title: "确认删除",
          content: "是否删除选中文件?",
          onOk: function() {
            that.handleDelete(id)
          }
        });
      },
      handleOssFileShow(record) {
        let that = this
        getAction(that.url.replay, { id: record.id }).then((res) => {
          if (res.success) {
            // that.$message.success('获取成功')
            // console.log('player res.file.url!', res.result.url)
            // console.log('player res.file.type!', this.matchType(res.result.fileName))

            var fileType = this.matchType(res.result.fileName)
            if (fileType == 'image') {
              // 更新图片预览地址
              this.ossImgPreviewUrl = res.result.url
              this.ossImgPreviewVisible = true
              this.ossImgPreviewTitle = res.result.fileName
            } else if (fileType == 'video') {
              // 更新视频播放地址
              this.playerOptions.sources[0].src = res.result.url
              this.ossVideoReplayVisible = true
              this.ossVideoReplayTitle = res.result.fileName
            } else {
              that.$message.warning('该文件不支持预览！')
            }
          } else {
            that.$message.warning(res.message)
          }

        })
      },
      handleOssVideoReplayClose() {
        this.ossVideoReplayTitle = ''
        this.ossVideoReplayVisible = false
      },
      handleOssImgPreviewClose() {
        this.ossImgPreviewTitle = ''
        this.ossImgPreviewVisible = false
      },


      // 根据文件名后缀区分 文件类型
      /*
       * @param: fileName - 文件名称
       * @param: 数据返回 1) 无后缀匹配 - false
       * @param: 数据返回 2) 匹配图片 - image
       * @param: 数据返回 3) 匹配 txt - txt
       * @param: 数据返回 4) 匹配 excel - excel
       * @param: 数据返回 5) 匹配 word - word
       * @param: 数据返回 6) 匹配 pdf - pdf
       * @param: 数据返回 7) 匹配 ppt - ppt
       * @param: 数据返回 8) 匹配 视频 - video
       * @param: 数据返回 9) 匹配 音频 - radio
       * @param: 数据返回 10) 其他匹配项 - other
       */
      matchType(fileName) {
        // 后缀获取
        var suffix = '';
        // 获取类型结果
        var result = '';
        try {
          var flieArr = fileName.split('.');
          suffix = flieArr[flieArr.length - 1];
        } catch (err) {
          suffix = '';
        }
        // fileName无后缀返回 false
        if (!suffix) {
          result = false;
          return result;
        }
        // 图片格式
        var imglist = ['png', 'jpg', 'jpeg', 'bmp', 'gif'];
        // 进行图片匹配
        result = imglist.some(function (item) {
          return item == suffix;
        });
        if (result) {
          result = 'image';
          return result;
        };
        // 匹配txt
        var txtlist = ['txt'];
        result = txtlist.some(function (item) {
          return item == suffix;
        });
        if (result) {
          result = 'txt';
          return result;
        };
        // 匹配 excel
        var excelist = ['xls', 'xlsx'];
        result = excelist.some(function (item) {
          return item == suffix;
        });
        if (result) {
          result = 'excel';
          return result;
        };
        // 匹配 word
        var wordlist = ['doc', 'docx'];
        result = wordlist.some(function (item) {
          return item == suffix;
        });
        if (result) {
          result = 'word';
          return result;
        };
        // 匹配 pdf
        var pdflist = ['pdf'];
        result = pdflist.some(function (item) {
          return item == suffix;
        });
        if (result) {
          result = 'pdf';
          return result;
        };
        // 匹配 ppt
        var pptlist = ['ppt'];
        result = pptlist.some(function (item) {
          return item == suffix;
        });
        if (result) {
          result = 'ppt';
          return result;
        };
        // 匹配 视频
        var videolist = ['mp4', 'm2v', 'mkv', 'mov'];
        result = videolist.some(function (item) {
          return item == suffix;
        });
        if (result) {
          result = 'video';
          return result;
        };
        // 匹配 音频
        var radiolist = ['mp3', 'wav', 'wmv'];
        result = radiolist.some(function (item) {
          return item == suffix;
        });
        if (result) {
          result = 'radio';
          return result;
        }
        // 其他 文件类型
        result = 'other';
        return result;
      }

    }
  }
</script>

<style scoped>
  @import '~@assets/less/common.less'
</style>
