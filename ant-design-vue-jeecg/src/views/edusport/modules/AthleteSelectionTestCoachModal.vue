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

        <a-form-item label="所属测试" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['testId']" :trigger-change="true" dictCode="tb_edu_athlete_selection_test,test_name,id" placeholder="请选择所属测试"/>
        </a-form-item>
        <a-form-item label="教练员" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['coachId']" :trigger-change="true" dictCode="tb_edu_coach,coach_name,id" placeholder="请选择教练员"/>
        </a-form-item>
        <a-form-item label="测试组别" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['groupId']" :trigger-change="true" dictCode="tb_edu_athlete_selection_group,group_name,id" placeholder="请选择测试组别"/>
        </a-form-item>
        <a-form-item label="测试项目" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-form-container disabled>
            <j-search-select-tag placeholder="请选择测试大项" v-decorator="['sportCode', validatorRules.sportCode]" dict="tb_edu_sport,sport_name,sport_code"/>
          </j-form-container>
        </a-form-item>
        <a-form-item label="在训小项" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-form-container disabled>
            <j-multi-select-tag type="list_multi" v-decorator="['eventCodes', validatorRules.eventCodes]" :trigger-change="true" :dictCode="fnDictCodeSportEvent" placeholder="请选择测在训小项"/>
          </j-form-container>
        </a-form-item>
        <a-form-item label="测试指标" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-multi-select-tag type="list_multi" v-decorator="['indexCodes', validatorRules.indexCodes]" :trigger-change="true" :options="dictOptions.indexCodes"  placeholder="请选择测试指标"/>
        </a-form-item>

        <a-form-item label="测试日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择测试日期" v-decorator="[ 'testDate', validatorRules.testDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="单据状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'status', validatorRules.status]" placeholder="请输入单据状态"></a-input>
        </a-form-item>

        <!-- 子表单区域 -->
        <a-tabs defaultActiveKey="1" >
          <!-- 操作按钮区域 -->
          <a-button slot="tabBarExtraContent"  type="primary" icon="download" @click="handleExportXls('运动员选材测试运动员成绩表')">导出成绩</a-button>
          <a-upload slot="tabBarExtraContent" name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="url.importExcelUrl" @change="handleImportExcel">
            <a-button type="primary" icon="import" >导入成绩</a-button>
          </a-upload>
          <a-tab-pane tab="测试运动员成绩" key="1" >
            <div style="text-align: center">
              <a-row type="flex" style="margin-bottom:16px; text-align:center;" :gutter="10">
                <a-col style="width:60px"><strong>姓名</strong></a-col>
                <a-col style="width:60px"><strong>性别</strong></a-col>
                <a-col style="width:80px"><strong>年级</strong></a-col>
                <a-col style="width:80px"><strong>出生年月</strong></a-col>
                <a-col style="width:60px"><strong>年龄</strong></a-col>
                <a-col style="width:60px"><strong>总成绩</strong></a-col>
                <a-col style="width:80px"><strong>评定等级</strong></a-col>
                <a-col style="width:100px" v-for="item in dictOptions.indexCodes" :key="item.value"><strong>{{item.text}}</strong></a-col>
                <a-col style="width:140px"><strong>操作</strong></a-col>
              </a-row>

              <a-row type="flex" style="text-align: center;" :gutter="10" v-for="(item, index) in testAthleteScoreList" :key="index">
                <a-col style="width:60px">
                  <a-form-item>
                      <div v-show="false">
                        <a-input style="width:1px" v-decorator="['testAthleteScoreList['+index+'].id', {'initialValue':item.id}]" />
                        <a-input style="width:1px" v-decorator="['testAthleteScoreList['+index+'].athleteId', {'initialValue':item.athleteId}]" />
                        <a-input style="width:1px" v-decorator="['testAthleteScoreList['+index+'].gender', {'initialValue':item.gender}]" />
                        <a-input style="width:1px" v-decorator="['testAthleteScoreList['+index+'].age', {'initialValue':item.age}]" />
                      </div>
                      {{dictTextAthleteName(item.athleteId)}}
                  </a-form-item>
                </a-col>
                <a-col style="width:60px">
                  <a-form-item>
                    {{dictTextGender(item.gender)}}
                  </a-form-item>
                </a-col>
                <a-col style="width:80px">
                  <a-form-item>
                    {{dictTextEduGrade(item.grade)}}
                  </a-form-item>
                </a-col>
                <a-col style="width:80px">
                  <a-form-item>
                    {{(item.birthDate+"").substr(0,7)}}
                  </a-form-item>
                </a-col>
                <a-col style="width:60px">
                  <a-form-item>
                    {{item.age}}岁
                  </a-form-item>
                </a-col>
                <a-col style="width:60px">
                  <a-form-item>
                    {{item.score?item.score:"待计算"}}
                  </a-form-item>
                </a-col>
                <a-col style="width:80px">
                  <a-form-item>
                    {{item.testRating?dictTextTestRating(item.testRating):"待计算"}}
                  </a-form-item>
                </a-col>

                <a-col style="width:100px" v-for="(detailItem, detailIndex) in item.testAthleteScoreDetailList" :key="detailIndex">
                  <a-form-item>
                      <div v-show="false">
                        <a-input style="width:1px" v-decorator="['testAthleteScoreList['+index+'].testAthleteScoreDetailList['+detailIndex+'].id', {'initialValue':detailItem.id}]" />
                        <a-input style="width:1px" v-decorator="['testAthleteScoreList['+index+'].testAthleteScoreDetailList['+detailIndex+'].athleteScoreId', {'initialValue':detailItem.athleteScoreId}]" />
                        <a-input style="width:1px" v-decorator="['testAthleteScoreList['+index+'].testAthleteScoreDetailList['+detailIndex+'].indexCode', {'initialValue':detailItem.indexCode}]" />
                      </div>
                      <a-input v-decorator="['testAthleteScoreList['+index+'].testAthleteScoreDetailList['+detailIndex+'].testValue', {'initialValue':detailItem.testValue}]" :placeholder="dictTextIndexCode(detailItem.indexCode)" style="width:70px"/>
                      {{detailItem.testRating?dictTextTestRating(detailItem.testRating):"未匹配"}}
                  </a-form-item>
                </a-col>

                <a-col style="width:140px">
                  <a-form-item>
                    <a-button @click="addRowTestAthleteScore" icon="plus"></a-button>&nbsp;
                    <a-button @click="delRowTestAthleteScore(index)" icon="minus"></a-button>
                  </a-form-item>
                </a-col>

              </a-row>
            </div>
          </a-tab-pane>
        </a-tabs>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction, getAction, downFile } from '@/api/manage'
  import pick from 'lodash.pick'
  import JDate from '@/components/jeecg/JDate'  
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import JMultiSelectTag from "@/components/dict/JMultiSelectTag"
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag'
  import {initDictOptions, filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  import { ACCESS_TOKEN } from "@/store/mutation-types"
  import Vue from 'vue'
  import moment from 'moment'

  export default {
    name: "AthleteSelectionTestCoachModal",
    components: {
      JDate,
      JDictSelectTag,
      JMultiSelectTag,
      JSearchSelectTag,
    },
    data () {
      return {
        //token header
        tokenHeader: {'X-Access-Token': Vue.ls.get(ACCESS_TOKEN)},
        form: this.$form.createForm(this),
        title:"操作",
        width:1300,
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
        dictCodeSport: '',
        dictCodeSportEvent: '',
        dictCodeGroupIndexCodes: '',
        dictCodeCoach: '',
        dictCodeAthlete: '',
        validatorRules:{
          testId:{rules: [{ required: true, message: '请输入所属测试!' }]},
          coachId:{rules: [{ required: true, message: '请输入教练员!' }]},
          groupId:{rules: [{ required: true, message: '请输入测试组别!' }]},
          sportCode:{rules: [{ required: true, message: '请输入测试项目!' }]},
          eventCodes:{rules: [{ required: true, message: '请输入测试小项!' }]},
          indexCodes:{rules: [{ required: true, message: '请输入测试指标!' }]},
          testDate:{rules: [{ required: true, message: '请输入测试日期!' }]},
          status:{rules: [{ required: true, message: '请输入单据状态!' }]},
        },
        testAthleteScoreList:[],
        /* 分页参数 */
        testAthleteScorePagination:{
          current: 1,
          pageSize: 100,
          pageSizeOptions: ['100', '10', '20'],
          showTotal: (total, range) => {
            return range[0] + "-" + range[1] + " 共" + total + "条"
          },
          showQuickJumper: false,
          showSizeChanger: false,
          total: 0
        },
        isorter: {
          // 排序由后端处理
          column: '',
          order: ''
        },
        url: {
          testAthleteScoreList: "/edusport/athleteSelectionTestAthleteScore/list",
          add: "/edusport/athleteSelectionTestCoach/add",
          edit: "/edusport/athleteSelectionTestCoach/editBatch",
          exportXlsUrl: "/edusport/athleteSelectionTestCoach/exportXls",
          importExcelUrl: "edusport/athleteSelectionTestCoach/importExcel",
        },
        dictOptions:{
          eventCodes:[],
          indexCodes:[],
          athleteIds:[],
          gender:[],
          eduGrade:[],
          indexRating:[],
        },

      }
    },
    created () {
      // 初始化性别字典
      initDictOptions('sex').then((res) => {
        if (res.success) {
          this.$set(this.dictOptions, 'gender', res.result)
        }
      })
      // 初始化年级字典
      initDictOptions('edu_grade').then((res) => {
        if (res.success) {
          this.$set(this.dictOptions, 'eduGrade', res.result)
        }
      })
      // 初始化测试大纲指标等级字典
      initDictOptions('index_rating').then((res) => {
        if (res.success) {
          this.$set(this.dictOptions, 'indexRating', res.result)
        }
      })
    },
    computed: {
      fnDictCodeSport: function() {
        console.log("fnDictCodeSport: ", this.dictCodeSport);
        return this.dictCodeSport;
      },
      fnDictCodeSportEvent: function() {
        console.log("fnDictCodeSportEvent: ", this.dictCodeSportEvent);
        return this.dictCodeSportEvent;
      },
      fnDictCodeGroupIndexCodes: function() {
        console.log("fnDictCodeGroupIndexCodes: ", this.dictCodeGroupIndexCodes);
        return this.dictCodeGroupIndexCodes;
      },

      fnDictCodeCoach: function() {
        console.log("fnDictCodeCoach: ", this.dictCodeCoach);
        return this.dictCodeCoach;
      },
      fnDictCodeAthlete: function() {
        console.log("fnDictCodeAthlete: ", this.dictCodeAthlete);
        return this.dictCodeAthlete;
      }

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
          this.form.setFieldsValue(pick(this.model,'testId','coachId','groupId','sportCode','eventCodes','indexCodes','testDate','status'))
        })

        // 初始化在训小项字典
        if (this.model.eventCodes) {
          this.dictCodeSportEvent = "tb_edu_sport_event as t1, t1.event_name, t1.event_code, t1.enable_status=1 and (t1.event_code='" + this.model.eventCodes.replace(/,/g,"' or t1.event_code = '") + "') | tb_edu_sport t2, t2.sport_name, t2.sport_code = t1.sport_code | tb_edu_sport_disciplines t3, t3.disciplines_name, t3.disciplines_code=t1.disciplines_code";
        }

        // 初始化测试组别指标字典
        if (this.model.indexCodes) {
          this.dictCodeGroupIndexCodes = 'tb_edu_athlete_selection_index, cn_name, index_code, index_code=' + this.model.indexCodes.replace(/,/g," or index_code = ") ;
        }
        initDictOptions(this.dictCodeGroupIndexCodes).then((res) => {
          if (res.success) {
            console.log("indexCodes: ", res.result)
            this.$set(this.dictOptions, 'indexCodes', res.result)
          }
        })
        // 初始化运动员字典
        initDictOptions('tb_edu_athlete, athlete_name, id').then((res) => {
          if (res.success) {
            console.log("athleteIds: ", res.result)
            this.$set(this.dictOptions, 'athleteIds', res.result)
          }
        })

        // 初始化测试运动员成绩及明细列表
        this.testAthleteScoreList = [];
        if (this.model.id) {
          this.getTestAthleteScoreList(this.model.id);
        }

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
        this.form.setFieldsValue(pick(row,'testId','coachId','groupId','sportCode','eventCodes','indexCodes','testDate','status'))
      },

      // 初始化测试运动员成绩及明细列表
      getTestAthleteScoreList(testCoachId) {
        const that = this;
        var testAthleteScoreParams = {
          testCoachId: testCoachId,
          column: "",
          order: "",
          pageNo: that.testAthleteScorePagination.current,
          pageSize: that.testAthleteScorePagination.pageSize,
        };//查询条件

        that.confirmLoading = true;
        getAction(that.url.testAthleteScoreList, testAthleteScoreParams).then((res) => {
          if (res.success) {
            console.log("getTestAthleteScoreList.testAthleteScoreParams: ", testAthleteScoreParams);
            console.log("getTestAthleteScoreList.res.result: ", res.result);
            that.testAthleteScoreList = res.result.records;
            that.testAthleteScorePagination.total = res.result.total;

            this.$forceUpdate();
          }
          if (res.code === 510) {
            that.$message.warning(res.message)
          }
        }).finally(() => {
          that.confirmLoading = false;
        });
      },
      dictTextAthleteName(athleteId) {
        return filterMultiDictText(this.dictOptions['athleteIds'], athleteId+"");
      },
      dictTextGender(gender) {
        return filterMultiDictText(this.dictOptions['gender'], gender+"");
      },
      dictTextEduGrade(grade) {
        return filterMultiDictText(this.dictOptions['eduGrade'], grade+"");
      },
      dictTextTestRating(indexRating) {
        return filterMultiDictText(this.dictOptions['indexRating'], indexRating+"");
      },
      dictTextIndexCode(indexCode) {
        return filterMultiDictText(this.dictOptions['indexCodes'], indexCode+"");
      },
      addRowTestAthleteScore () {
        // 评分标准行数最大行数设置为：10行
        if (this.form.getFieldValue('testAthleteScoreList').length == 10) {
          this.$message.warning("不能再添加了！");
          return;
        }

        // 触发表单验证(指标，性别)
        this.form.validateFields(['testDate', 'status'], (err, values) => {
          if (!err) {
            this.testAthleteScoreList.push({});
            this.$forceUpdate();
          }
        })
      },
      delRowTestAthleteScore (index) {
        if (this.form.getFieldValue('testAthleteScoreList').length == 1) {
          this.$message.warning("不能再删除了！");
          return;
        }

        this.testAthleteScoreList.splice(index,1);
        this.$forceUpdate();
      },

      handleExportXls(fileName){
        if(!fileName || typeof fileName != "string"){
          fileName = "导出文件"
        }
        if (!this.model.id) {
          this.$message.warning("新增状态下不能导出文件！");
          return;
        }

        console.log("handleExportXls.testCoachId: ", this.model.id);
        let param = {testCoachId: this.model.id};
        downFile(this.url.exportXlsUrl, param).then((data)=>{
          if (!data) {
            this.$message.warning("文件下载失败")
            return
          }
          if (typeof window.navigator.msSaveBlob !== 'undefined') {
            window.navigator.msSaveBlob(new Blob([data],{ type: 'application/vnd.ms-excel' }), fileName + moment(new Date()).format("YYYY-DD-MM") + '.xls')
          }else{
            let url = window.URL.createObjectURL(new Blob([data],{ type: 'application/vnd.ms-excel' }))
            let link = document.createElement('a')
            link.style.display = 'none'
            link.href = url
            link.setAttribute('download', fileName + moment(new Date()).format("YYYY-MM-DD") + '.xls')
            document.body.appendChild(link)
            link.click()
            document.body.removeChild(link); //下载完成移除元素
            window.URL.revokeObjectURL(url); //释放掉blob对象
          }
        })
      },
      /* 导入 */
      handleImportExcel(info){
        if (info.file.status !== 'uploading') {
          console.log(info.file, info.fileList);
        }
        if (info.file.status === 'done') {
          if (info.file.response.success) {
            // this.$message.success(`${info.file.name} 文件上传成功`);
            if (info.file.response.code === 201) {
              let { message, result: { msg, fileUrl, fileName } } = info.file.response
              let href = window._CONFIG['domianURL'] + fileUrl
              this.$warning({
                  title: message,
                  content: (
                    <div>
                    <span>{msg}</span><br/>
                    <span>具体详情请 <a href={href} target="_blank" download={fileName}>点击下载</a> </span>
                </div>
              )
            })
            } else {
              this.$message.success(info.file.response.message || `${info.file.name} 文件上传成功`)
            }
            this.loadData()
          } else {
            this.$message.error(`${info.file.name} ${info.file.response.message}.`);
          }
        } else if (info.file.status === 'error') {
          this.$message.error(`文件上传失败: ${info.file.msg} `);
        }
      },
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>