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

        <a-row>
          <a-col :span="17">
            <a-row>
              <a-col :span="12">
                <a-form-item label="教练员代码" :labelCol="labelCol2" :wrapperCol="wrapperCol2">
                  <a-input v-decorator="[ 'coachNo', validatorRules.coachNo]" placeholder="请输入教练员代码"></a-input>
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label="教练员姓名" :labelCol="labelCol2" :wrapperCol="wrapperCol2">
                  <a-input v-decorator="[ 'coachName', validatorRules.coachName]" placeholder="请输入教练员姓名"></a-input>
                </a-form-item>
              </a-col>
            </a-row>
            <a-row>
              <a-col :span="12">
                <a-form-item label="性别" :labelCol="labelCol2" :wrapperCol="wrapperCol2">
                  <j-dict-select-tag type="list" v-decorator="['gender']" :trigger-change="true" dictCode="sex" placeholder="请选择性别"/>
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label="民族" :labelCol="labelCol2" :wrapperCol="wrapperCol2">
                  <j-dict-select-tag type="list" v-decorator="['nation']" :trigger-change="true" dictCode="nation" placeholder="请选择民族"/>
                </a-form-item>
              </a-col>
            </a-row>
          </a-col>
          <a-col :span="7">
            <a-form-item
              :labelCol="labelCol2"
              :wrapperCol="wrapperCol2"
              label="2寸照片"
              hasFeedback>
              <a-upload
                :action="uploadAction"
                listType="picture-card"
                :headers="headers"
                :fileList="fileList"
                @change="handlePicChange"
                @preview="handlePicPreview"
              >
                <div v-if="fileList.length < 1">
                  <a-icon type="plus" />
                  <div class="ant-upload-text">上传照片</div>
                </div>
              </a-upload>
              <a-modal :visible="previewVisible" :footer="null" @cancel="handlePicCancel">
                <img alt="example" style="width: 100%" :src="previewImage"/>
              </a-modal>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="8">
            <a-form-item label="出生日期" :labelCol="labelCol2" :wrapperCol="wrapperCol2">
              <j-date placeholder="请选择出生日期" v-decorator="[ 'birthDate', validatorRules.birthDate]" :trigger-change="true" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="11">
            <a-form-item label="身份证号" :labelCol="labelCol2" :wrapperCol="wrapperCol2">
              <a-input v-decorator="[ 'idNo', validatorRules.idNo]" placeholder="请输入身份证号"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="5">

          </a-col>
        </a-row>
        <a-row>
          <a-col :span="8">
            <a-form-item label="籍贯省" :labelCol="labelCol2" :wrapperCol="wrapperCol2">
              <a-input v-decorator="[ 'nativeProvince', validatorRules.nativeProvince]" placeholder="请输入籍贯省"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="籍贯市" :labelCol="labelCol2" :wrapperCol="wrapperCol2">
              <a-input v-decorator="[ 'nativeCity', validatorRules.nativeCity]" placeholder="请输入籍贯市"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="政治面貌" :labelCol="labelCol2" :wrapperCol="wrapperCol2">
              <j-dict-select-tag type="list" v-decorator="['politicalStatus']" :trigger-change="true" dictCode="political_status" placeholder="请选择政治面貌"/>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="8">
            <a-form-item label="学历" :labelCol="labelCol2" :wrapperCol="wrapperCol2">
              <j-dict-select-tag type="list" v-decorator="['eduBackground']" :trigger-change="true" dictCode="edu_background" placeholder="请选择学历"/>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="学位" :labelCol="labelCol2" :wrapperCol="wrapperCol2">
              <j-dict-select-tag type="list" v-decorator="['eduDegree']" :trigger-change="true" dictCode="edu_degree" placeholder="请选择学位"/>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="手机号码" :labelCol="labelCol2" :wrapperCol="wrapperCol2">
              <a-input v-decorator="[ 'mobile', validatorRules.mobile]" placeholder="请输入手机号码"></a-input>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="8">
            <a-form-item label="编制类型" :labelCol="labelCol2" :wrapperCol="wrapperCol2">
              <j-dict-select-tag type="list" v-decorator="['staffType']" :trigger-change="true" dictCode="staff_type" placeholder="请选择编制类型"/>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="聘用日期" :labelCol="labelCol2" :wrapperCol="wrapperCol2">
              <j-date placeholder="请选择聘用日期" v-decorator="[ 'hireDate', validatorRules.hireDate]" :trigger-change="true" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="聘用年限" :labelCol="labelCol2" :wrapperCol="wrapperCol2">
              <a-input-number v-decorator="[ 'hireYears', validatorRules.hireYears]" placeholder="请输入聘用年限" style="width: 100%"/>
            </a-form-item>
          </a-col>
        </a-row>
        <a-form-item label="家庭地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'homeAddress', validatorRules.homeAddress]" placeholder="请输入家庭地址"></a-input>
        </a-form-item>
        <a-form-item label="何年毕业于何学校专业" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'marjorInfo', validatorRules.marjorInfo]" placeholder="请输入何年毕业于何学校专业"></a-input>
        </a-form-item>
        <a-row>
          <a-col :span="12">
            <a-form-item label="初任教练员时间" :labelCol="labelCol3" :wrapperCol="wrapperCol3">
              <a-input v-decorator="[ 'coachStartDate', validatorRules.coachStartDate]" placeholder="请输入初任教练员时间"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="三级教练员聘任时间" :labelCol="labelCol3" :wrapperCol="wrapperCol3">
              <a-input v-decorator="[ 'l3AppointmentDate', validatorRules.l3AppointmentDate]" placeholder="请输入三级教练员聘任时间"></a-input>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="12">
            <a-form-item label="二级教练员聘任时间" :labelCol="labelCol3" :wrapperCol="wrapperCol3">
              <a-input v-decorator="[ 'l2AppointmentDate', validatorRules.l2AppointmentDate]" placeholder="请输入二级教练员聘任时间"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="一级教练员聘任时间" :labelCol="labelCol3" :wrapperCol="wrapperCol3">
              <a-input v-decorator="[ 'l1AppointmentDate', validatorRules.l1AppointmentDate]" placeholder="请输入一级教练员聘任时间"></a-input>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="12">
            <a-form-item label="取得高级教练员资格时间" :labelCol="labelCol3" :wrapperCol="wrapperCol3">
              <a-input v-decorator="[ 'ladvCertDate', validatorRules.ladvCertDate]" placeholder="请输入取得高级教练员资格时间"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="高级教练员聘任时间" :labelCol="labelCol3" :wrapperCol="wrapperCol3">
              <a-input v-decorator="[ 'ladvAppointmentDate', validatorRules.ladvAppointmentDate]" placeholder="请输入高级教练员聘任时间"></a-input>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="12">
            <a-form-item label="取得国家级教练员资格时间" :labelCol="labelCol3" :wrapperCol="wrapperCol3">
              <a-input v-decorator="[ 'lcnCertDate', validatorRules.lcnCertDate]" placeholder="请输入取得国家级教练员资格时间"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="国家级教练员聘任时间" :labelCol="labelCol3" :wrapperCol="wrapperCol3">
              <a-input v-decorator="[ 'lcnAppointmentDate', validatorRules.lcnAppointmentDate]" placeholder="请输入国家级教练员聘任时间"></a-input>
            </a-form-item>
          </a-col>
        </a-row>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import Vue from 'vue'
  import {ACCESS_TOKEN} from "@/store/mutation-types"
  import JDate from '@/components/jeecg/JDate'  
  import JUpload from '@/components/jeecg/JUpload'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import { getUploadFileList,getFilePaths } from '@/utils/commonUploadFile.js'

  export default {
    name: "CoachModal",
    components: { 
      JDate,
      JUpload,
      JDictSelectTag,
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
          sm: { span: 6 },
          md: { span: 6 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
          md: { span: 16 },
        },

        labelCol2: {
          xs: { span: 24 },
          sm: { span: 8 },
          md: { span: 8 },
        },
        wrapperCol2: {
          xs: { span: 24 },
          sm: { span: 14 },
          md: { span: 14 },
        },

        labelCol3: {
          xs: { span: 24 },
          sm: { span: 12 },
          md: { span: 12 },
        },
        wrapperCol3: {
          xs: { span: 24 },
          sm: { span: 10 },
          md: { span: 10 },
        },

        // 图片初始化
        headers: {},
        fileList: [],
        previewVisible: false,
        previewImage: '',

        confirmLoading: false,
        validatorRules:{
          coachNo:{rules: [{ required: true, message: '请输入教练员代码!' }]},
          coachName:{rules: [{ required: true, message: '请输入教练员姓名!' }]},
          gender:{rules: [{ required: true, message: '请输入性别!' }]},
          nation:{rules: [{ required: true, message: '请输入民族!' }]},
          birthDate:{rules: [{ required: true, message: '请输入出生日期!' }]},
          idNo:{rules: [{ required: true, message: '请输入身份证号!' }]},
          nativeProvince:{rules: [{ required: true, message: '请输入籍贯省!' }]},
          nativeCity:{rules: [{ required: true, message: '请输入籍贯市!' }]},
          politicalStatus:{rules: [{ required: true, message: '请输入政治面貌!' }]},
          homeAddress:{rules: [{ required: true, message: '请输入家庭地址!' }]},
          mobile:{rules: [{ required: true, message: '请输入手机号码!' }]},
          eduBackground:{rules: [{ required: true, message: '请输入学历!' }]},
          eduDegree:{rules: [{ required: true, message: '请输入学位!' }]},
          staffType:{rules: [{ required: true, message: '请输入编制类型!' }]},
          hireDate:{},
          hireYears:{rules: [{ required: true, message: '请输入聘用年限!' }]},
          marjorInfo:{rules: [{ required: true, message: '请输入何年毕业于何学校专业!' }]},
          coachStartDate:{rules: [{ required: true, message: '请输入初任教练员时间!' }]},
          l3AppointmentDate:{rules: [{ required: true, message: '请输入三级教练员聘任时间!' }]},
          l2AppointmentDate:{rules: [{ required: true, message: '请输入二级教练员聘任时间!' }]},
          l1AppointmentDate:{rules: [{ required: true, message: '请输入一级教练员聘任时间!' }]},
          ladvCertDate:{rules: [{ required: true, message: '请输入取得高级教练员资格时间!' }]},
          ladvAppointmentDate:{rules: [{ required: true, message: '请输入高级教练员聘任时间!' }]},
          lcnCertDate:{rules: [{ required: true, message: '请输入取得国家级教练员资格时间!' }]},
          lcnAppointmentDate:{rules: [{ required: true, message: '请输入国家级教练员聘任时间!' }]},
          photoPath:{rules: [{ required: true, message: '请输入照片名称!' }]},
        },
        url: {
          add: "/edusport/coach/add",
          edit: "/edusport/coach/edit",
          fileUpload: window._CONFIG['domianURL'] + "/sys/common/upload",
          imgerver: window._CONFIG['domianURL'] + "/sys/common/view",
        }
     
      }
    },
    computed: {
      uploadAction: function () {
        return this.url.fileUpload;
      }
    },
    created() {
      const token = Vue.ls.get(ACCESS_TOKEN);
      this.headers = {"X-Access-Token": token}
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        // 添加图片处理
        let currFileList = getUploadFileList(record.photoPath)
        this.fileList = [...currFileList]

        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'coachNo','coachName','gender','nation','birthDate','idNo','nativeProvince','nativeCity','politicalStatus','homeAddress','mobile','eduBackground','eduDegree','staffType','hireDate','hireYears','marjorInfo','coachStartDate','l3AppointmentDate','l2AppointmentDate','l1AppointmentDate','ladvCertDate','ladvAppointmentDate','lcnCertDate','lcnAppointmentDate'))
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
            console.log("表单提交数据",formData);

            // 图片路径保存
            formData.photoPath = getFilePaths(this.fileList);

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
        this.form.setFieldsValue(pick(row,'coachNo','coachName','gender','nation','birthDate','idNo','nativeProvince','nativeCity','politicalStatus','homeAddress','mobile','eduBackground','eduDegree','staffType','hireDate','hireYears','marjorInfo','coachStartDate','l3AppointmentDate','l2AppointmentDate','l1AppointmentDate','ladvCertDate','ladvAppointmentDate','lcnCertDate','lcnAppointmentDate'))
      },

      handlePicChange(info) {
        this.fileList = info.fileList;
        if (info.file.status === 'uploading') {
          return
        }
        if (info.file.status === 'done') {
          var response = info.file.response;
          if (!response.success) {
            this.$message.warning(response.message);
          }
        }
      },
      handlePicCancel() {
        this.previewVisible = false
        this.previewImage=''
      },
      handlePicView(url){
        this.previewImage = this.url.imgerver + "/" + url
        this.previewVisible = true
      },
      handlePicPreview(file) {
        console.log("handlePicPreview.file.url: ",file.url);
        this.previewImage = file.url || file.thumbUrl
        this.previewVisible = true
      },

    }
  }
</script>