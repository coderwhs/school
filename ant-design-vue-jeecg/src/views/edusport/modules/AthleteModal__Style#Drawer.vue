<template>
  <a-drawer
    :title="title"
    :width="drawerWidth"
    :maskClosable="true"
    placement="right"
    :closable="true"
    @close="close"
    :visible="visible"
    style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;">

  <a-spin :spinning="confirmLoading">
          <a-form :form="form">
            <a-row>
              <a-col :span="17">
                <a-row>
                  <a-col :span="14">
                    <a-form-item label="学号" :labelCol="labelCol" :wrapperCol="wrapperCol">
                      <a-input v-decorator="[ 'athleteNo', validatorRules.athleteNo]" placeholder="请输入学号"></a-input>
                    </a-form-item>
                  </a-col>
                  <a-col :span="10">
                    <a-form-item label="姓名" :labelCol="labelCol" :wrapperCol="wrapperCol">
                      <a-input v-decorator="[ 'athleteName', validatorRules.athleteName]" placeholder="请输入姓名"></a-input>
                    </a-form-item>
                  </a-col>
                </a-row>
                <a-row>
                  <a-col :span="14">
                    <a-form-item label="出生日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
                      <j-date placeholder="请选择出生日期" v-decorator="[ 'birthDate', validatorRules.birthDate]" :trigger-change="true" style="width: 100%"/>
                    </a-form-item>
                  </a-col>
                  <a-col :span="10">
                    <a-form-item label="性别" :labelCol="labelCol" :wrapperCol="wrapperCol">
                      <j-dict-select-tag type="list" v-decorator="['gender']" :trigger-change="true" dictCode="sex" placeholder="请选择性别"/>
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
            <!--<a-row>
            <a-col :span="12">
              <a-form-item label="照片路径" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input v-decorator="[ 'photoPath', validatorRules.photoPath]" placeholder="请输入照片路径"></a-input>
              </a-form-item>
            </a-col>
            <a-col :span="12">
            </a-col>
          </a-row>-->
            <a-row>
              <a-col :span="12">
                <a-form-item label="身高(cm)" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input-number v-decorator="[ 'studentHeight', validatorRules.studentHeight]" placeholder="请输入身高(cm)" style="width: 100%"/>
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label="体重(kg)" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input-number v-decorator="[ 'studentWeight', validatorRules.studentWeight]" placeholder="请输入体重(kg)" style="width: 100%"/>
                </a-form-item>
              </a-col>
            </a-row>
            <a-row>
              <a-col :span="12">
                <a-form-item label="专业项目" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <j-search-select-tag v-decorator="['majorSport']" dict="tb_edu_sport,sport_name,sport_code" />
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label="教练员" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <j-search-select-tag v-decorator="['majorSportTeacherCode']" dict="tb_edu_coach,coach_name,coach_no" />
                </a-form-item>
              </a-col>
            </a-row>
            <a-row>
              <a-col :span="12">
                <a-form-item label="籍贯省" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input v-decorator="[ 'nativeProvince', validatorRules.nativeProvince]" placeholder="请输入籍贯省"></a-input>
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label="籍贯市" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input v-decorator="[ 'nativeCity', validatorRules.nativeCity]" placeholder="请输入籍贯市"></a-input>
                </a-form-item>
              </a-col>
            </a-row>
            <a-row>
              <a-col :span="12">
                <a-form-item label="民族" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <j-dict-select-tag type="list" v-decorator="['nation']" :trigger-change="true" dictCode="nation" placeholder="请选择民族"/>
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label="身份证号" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input v-decorator="[ 'idNo', validatorRules.idNo]" placeholder="请输入身份证号"></a-input>
                </a-form-item>
              </a-col>
            </a-row>
            <a-row>
              <a-col :span="12">
                <a-form-item label="手机号码" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input v-decorator="[ 'mobile', validatorRules.mobile]" placeholder="请输入手机号码"></a-input>
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label="家庭住址" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input v-decorator="[ 'homeAddress', validatorRules.homeAddress]" placeholder="请输入家庭住址"></a-input>
                </a-form-item>
              </a-col>
            </a-row>
            <a-row>
              <a-col :span="12">
                <a-form-item label="邮政编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input v-decorator="[ 'zipcode', validatorRules.zipcode]" placeholder="请输入邮政编码"></a-input>
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label="户籍所在派出所" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input v-decorator="[ 'policeStation', validatorRules.policeStation]" placeholder="请输入户籍所在派出所"></a-input>
                </a-form-item>
              </a-col>
            </a-row>
            <a-row>
              <a-col :span="12">
                <a-form-item label="父亲姓名" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input v-decorator="[ 'father', validatorRules.father]" placeholder="请输入父亲姓名"></a-input>
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label="父亲身高" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input-number v-decorator="[ 'fatherHeight', validatorRules.fatherHeight]" placeholder="请输入父亲身高" style="width: 100%"/>
                </a-form-item>
              </a-col>
            </a-row>
            <a-row>
              <a-col :span="12">
                <a-form-item label="母亲姓名" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input v-decorator="[ 'mother', validatorRules.mother]" placeholder="请输入母亲姓名"></a-input>
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label="母亲身高" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input-number v-decorator="[ 'motherHeight', validatorRules.motherHeight]" placeholder="请输入母亲身高" style="width: 100%"/>
                </a-form-item>
              </a-col>
            </a-row>
            <a-row>
              <a-col :span="12">
                <a-form-item label="父亲电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input v-decorator="[ 'fatherMobile', validatorRules.fatherMobile]" placeholder="请输入父亲电话"></a-input>
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label="母亲电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input v-decorator="[ 'motherMobile', validatorRules.motherMobile]" placeholder="请输入母亲电话"></a-input>
                </a-form-item>
              </a-col>
            </a-row>
            <a-row>
              <a-col :span="12">
                <a-form-item label="输送单位区县" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input v-decorator="[ 'transportDepartmentCity', validatorRules.transportDepartmentCity]" placeholder="请输入输送单位区县"></a-input>
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label="输送单位名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input v-decorator="[ 'transportDepartmentSchool', validatorRules.transportDepartmentSchool]" placeholder="请输入输送单位名称"></a-input>
                </a-form-item>
              </a-col>
            </a-row>
            <a-row>
              <a-col :span="12">
                <a-form-item label="入学日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <j-date placeholder="请选择入学日期" v-decorator="[ 'admissionDate', validatorRules.admissionDate]" :trigger-change="true" style="width: 100%"/>
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label="就读年级" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input v-decorator="[ 'grade', validatorRules.grade]" placeholder="请输入就读年级"></a-input>
                </a-form-item>
              </a-col>
            </a-row>
            <a-row>
              <a-col :span="12">
                <a-form-item label="入队时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <j-date placeholder="请选择入队时间" v-decorator="[ 'majorSportAttendDate', validatorRules.majorSportAttendDate]" :trigger-change="true" style="width: 100%"/>
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label="退役时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input v-decorator="[ 'retireDate', validatorRules.retireDate]" placeholder="请输入退役时间"></a-input>
                </a-form-item>
              </a-col>
            </a-row>
            <a-row>
              <a-col :span="12">
                <a-form-item label="吸收单位类别" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <j-dict-select-tag type="list" v-decorator="['acceptDepartmentType']" :trigger-change="true" dictCode="accept_department_type" placeholder="请选择吸收单位类别"/>
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label="吸收单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input v-decorator="[ 'acceptDepartment', validatorRules.acceptDepartment]" placeholder="请输入吸收单位"></a-input>
                </a-form-item>
              </a-col>
            </a-row>
            <a-row>
              <a-col :span="23">
                <a-form-item label="简介" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-textarea v-decorator="['resume']" rows="4" placeholder="请输入简介"/>
                </a-form-item>
              </a-col>
              <a-col :span="1">
              </a-col>
            </a-row>
          </a-form>
        </a-spin>
        <div class="drawer-bootom-button" v-show="!disableSubmit">
          <a-popconfirm title="确定放弃编辑？" @confirm="handleCancel" okText="确定" cancelText="取消">
            <a-button style="margin-right: .8rem">取消</a-button>
          </a-popconfirm>
          <a-button type="primary" @click="handleOk" :loading="confirmLoading">提交</a-button>
        </div>
  </a-drawer>
    </template>

    <script>

      import { httpAction } from '@/api/manage'
      import pick from 'lodash.pick'
      import JDate from '@/components/jeecg/JDate'
      import JDictSelectTag from "@/components/dict/JDictSelectTag"
      import JSearchSelectTag from '@/components/dict/JSearchSelectTag'
      import { getUploadFileList,getFilePaths } from '@/utils/commonUploadFile.js'
      import JUpload from '@/components/jeecg/JUpload'
      import Vue from 'vue'
      import {ACCESS_TOKEN} from "@/store/mutation-types"
      export default {
        name: "AthleteModal",
        components: {
          JDate,
          JDictSelectTag,
          JSearchSelectTag,
          JUpload,
        },
        data () {
          return {
            form: this.$form.createForm(this),
            title:"操作",
            width:800,
            visible: false,
            drawerWidth:800,
            disableSubmit:false,
            model: {},
            /*labelCol: {
              xs: { span: 24 },
              sm: { span: 8 },
              md: { span: 8 },
            },
            wrapperCol: {
              xs: { span: 24 },
              sm: { span: 14 },
              md: { span: 14 },
            },*/
            labelCol: {
              xs: { span: 24 },
              sm: { span: 5 },
              md: { span: 5 },
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
              sm: { span: 8 },
              md: { span: 8 },
            },
            wrapperCol3: {
              xs: { span: 24 },
              sm: { span: 14 },
              md: { span: 14 },
            },

            // 图片初始化
            headers: {},
            fileList: [],
            previewVisible: false,
            previewImage: '',

            confirmLoading: false,
            validatorRules:{
              athleteNo:{rules: [{ required: true, message: '请输入学号!' }]},
              athleteName:{rules: [{ required: true, message: '请输入姓名!' }]},
              gender:{rules: [{ required: true, message: '请输入性别!' }]},
              nation:{rules: [{ required: true, message: '请输入民族!' }]},
              birthDate:{rules: [{ required: true, message: '请输入出生日期!' }]},
              studentHeight:{rules: [{ required: true, message: '请输入身高(cm)!' }]},
              studentWeight:{rules: [{ required: true, message: '请输入体重(kg)!' }]},
              majorSport:{rules: [{ required: true, message: '请输入专业项目!' }]},
              majorSportTeacherCode:{rules: [{ required: true, message: '请输入教练员!' }]},
              nativeProvince:{rules: [{ required: true, message: '请输入籍贯省!' }]},
              nativeCity:{rules: [{ required: true, message: '请输入籍贯市!' }]},
              idNo:{rules: [{ required: true, message: '请输入身份证号!' }]},
              mobile:{rules: [{ required: true, message: '请输入手机号码!' }]},
              homeAddress:{rules: [{ required: true, message: '请输入家庭住址!' }]},
              zipcode:{rules: [{ required: true, message: '请输入邮政编码!' }]},
              policeStation:{rules: [{ required: true, message: '请输入户籍所在派出所!' }]},
              father:{rules: [{ required: true, message: '请输入父亲姓名!' }]},
              fatherHeight:{rules: [{ required: true, message: '请输入父亲身高!' }]},
              fatherMobile:{rules: [{ required: true, message: '请输入父亲电话!' }]},
              mother:{rules: [{ required: true, message: '请输入母亲姓名!' }]},
              motherHeight:{rules: [{ required: true, message: '请输入母亲身高!' }]},
              motherMobile:{rules: [{ required: true, message: '请输入母亲电话!' }]},
              transportDepartmentCity:{rules: [{ required: true, message: '请输入输送单位区县!' }]},
              transportDepartmentSchool:{rules: [{ required: true, message: '请输入输送单位名称!' }]},
              admissionDate:{},
              grade:{rules: [{ required: true, message: '请输入就读年级!' }]},
              majorSportAttendDate:{},
              retireDate:{rules: [{ required: true, message: '请输入退役时间!' }]},
              acceptDepartmentType:{rules: [{ required: true, message: '请输入吸收单位类别!' }]},
              acceptDepartment:{rules: [{ required: true, message: '请输入吸收单位!' }]},
              photoPath:{rules: [{ required: true, message: '请输入照片路径!' }]},
              resume:{rules: [{ required: true, message: '请输入简介!' }]},
            },
            url: {
              add: "/edusport/athlete/add",
              edit: "/edusport/athlete/edit",
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

        created () {
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
              this.form.setFieldsValue(pick(this.model,'athleteNo','athleteName','gender','nation','birthDate','studentHeight','studentWeight','majorSport','majorSportTeacherCode','nativeProvince','nativeCity','idNo','mobile','homeAddress','zipcode','policeStation','father','fatherHeight','fatherMobile','mother','motherHeight','motherMobile','transportDepartmentCity','transportDepartmentSchool','admissionDate','grade','majorSportAttendDate','retireDate','acceptDepartmentType','acceptDepartment','photoPath','resume'))
            })
          },
          close () {
            this.$emit('close');
            this.visible = false;
            this.disableSubmit = false;
          },

          // 根据屏幕变化,设置抽屉尺寸
          resetScreenSize(){
            let screenWidth = document.body.clientWidth;
            if(screenWidth < 500){
              this.drawerWidth = screenWidth;
            }else{
              this.drawerWidth = 700;
            }
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
                // 图片路径保存
                formData.photoPath = getFilePaths(this.fileList);

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
            this.form.setFieldsValue(pick(row,'athleteNo','athleteName','gender','nation','birthDate','studentHeight','studentWeight','majorSport','majorSportTeacherCode','nativeProvince','nativeCity','idNo','mobile','homeAddress','zipcode','policeStation','father','fatherHeight','fatherMobile','mother','motherHeight','motherMobile','transportDepartmentCity','transportDepartmentSchool','admissionDate','grade','majorSportAttendDate','retireDate','acceptDepartmentType','acceptDepartment','photoPath','resume'))
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

<style lang="less" scoped>
  .drawer-bootom-button {
    position: absolute;
    bottom: -8px;
    width: 100%;
    border-top: 1px solid #e8e8e8;
    padding: 10px 16px;
    text-align: right;
    left: 0;
    background: #fff;
    border-radius: 0 0 2px 2px;
  }
</style>