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

        <a-form-item label="测试名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'testName', validatorRules.testName]" placeholder="请输入测试名称"></a-input>
        </a-form-item>
        <a-form-item label="发布日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择发布日期" v-decorator="[ 'publishDate', validatorRules.publishDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="测试组别" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag placeholder="请选择测试组别" v-decorator="['groupId', validatorRules.groupId]" dict="tb_edu_athlete_selection_group, group_name, id" @change="handleGroupIdChange"/>
        </a-form-item>
        <a-form-item label="测试大项" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-form-container disabled>
            <j-search-select-tag placeholder="请选择测试大项" v-decorator="['sportCode', validatorRules.sportCode]" :dict="fnDictCodeSport"/>
          </j-form-container>
        </a-form-item>
        <a-form-item label="在训小项" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-form-container disabled>
            <j-multi-select-tag type="list_multi" v-decorator="['eventCodes', validatorRules.eventCodes]" :trigger-change="true" :dictCode="fnDictCodeSportEvent" placeholder="请选择测在训小项"/>
          </j-form-container>
        </a-form-item>
        <a-form-item label="测试指标" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <j-multi-select-tag type="list_multi" v-decorator="['indexCodes', validatorRules.indexCodes]" :trigger-change="true" :dictCode="fnDictCodeGroupIndexCodes" placeholder="请选择测试指标"/>
        </a-form-item>

        <a-form-item label="带训教练" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-multi-select-tag type="list_multi" placeholder="请选择教练" v-decorator="['coachIds', validatorRules.coachIds]" :trigger-change="true" :dictCode="fnDictCodeCoach" @change="handleCoachIdChange"/>
        </a-form-item>
        <a-form-item label="测试运动员" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-multi-select-tag type="list_multi" placeholder="请选择测试运动员" v-decorator="['athleteIds', validatorRules.athleteIds]" :trigger-change="true" :dictCode="fnDictCodeAthlete" />
        </a-form-item>


      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JDate from '@/components/jeecg/JDate'
  import JMultiSelectTag from "@/components/dict/JMultiSelectTag"
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag'
  import {initDictOptions, filterMultiDictText} from '@/components/dict/JDictSelectUtil'

  export default {
    name: "AthleteSelectionTestModal",
    components: { 
      JDate,
      JMultiSelectTag,
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
        dictCodeSport: '',
        dictCodeSportEvent: '',
        dictCodeGroupIndexCodes: '',
        dictCodeCoach: '',
        dictCodeAthlete: '',
        validatorRules:{
          testName:{rules: [{ required: true, message: '请输入测试名称!' }]},
          groupId:{rules: [{ required: true, message: '请选择测试组别!' }]},
          // sportCode:{rules: [{ required: true, message: '请选择测试大项!' }]},
          // eventCodes:{rules: [{ required: true, message: '请选择在训小项!' }]},
          publishDate:{rules: [{ required: true, message: '请输入发布日期!' }]},
          coachIds:{rules: [{ required: true, message: '请选择带训教练!' }]},
          athleteIds:{rules: [{ required: true, message: '请选择测试运动员!' }]},
        },
        url: {
          add: "/edusport/athleteSelectionTest/add",
          edit: "/edusport/athleteSelectionTest/editByStep",
        }
     
      }
    },
    created () {
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
          this.form.setFieldsValue(pick(this.model,'testName','groupId','sportCode','publishDate'))
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
        this.form.setFieldsValue(pick(row,'testName','groupId','sportCode','publishDate'))
      },
      handleGroupIdChange (selectedValue) {
        console.log("handleGroupIdChange: ",selectedValue);

        // 获取所选测试标准组别的测试大项
        if (selectedValue) {
          let dictCodeGroupSportCode = "tb_edu_athlete_selection_group, sport_code, id, id = " + selectedValue;
          initDictOptions(dictCodeGroupSportCode).then((res) => {
            if (res.success) {
              let groupIds = res.result[0];
              let sportCode = groupIds.text;
              this.dictCodeSport = "tb_edu_sport, sport_name, sport_code, sport_code = " + sportCode;
              this.$nextTick(() => {
                this.form.setFieldsValue({'sportCode':sportCode});
              });

              // 获取所选测试标准组别的测试大项相关的带训教练员，及测试运动员
              if (sportCode) {
                console.log("sportCode: ", sportCode);
                let dictCodeAthleteCoachIds = "tb_edu_athlete, distinct(coach_id), coach_id, sport_code = '" + sportCode + "'";
                initDictOptions(dictCodeAthleteCoachIds).then((res) => {
                  if (res.success) {
                    let coachIds = "";
                    for (let i = 0; i < res.result.length; i++) {
                      if (i == 0) {
                        coachIds += res.result[i].text;
                      } else {
                        coachIds += "," + res.result[i].text;
                      }
                    }
                    this.dictCodeCoach = "tb_edu_coach, coach_name, id, id = '" + coachIds.replace(/,/g, "' or id = '") + "'";
                    this.$nextTick(() => {
                      this.form.setFieldsValue({ 'coachIds': coachIds });
                    })
                  }
                });

                // 获取所选测试标准组别的测试大项相关的带训教练员，及测试运动员
                let dictCodeAthleteIds = "tb_edu_athlete, athlete_name, id, sport_code = '" + sportCode + "' order by coach_id asc";
                initDictOptions(dictCodeAthleteIds).then((res) => {
                  if (res.success) {
                    let athleteIds = "";
                    for (let i = 0; i < res.result.length; i++) {
                      if (i == 0) {
                        athleteIds += res.result[i].value;
                      } else {
                        athleteIds += "," + res.result[i].value;
                      }
                    }
                    this.dictCodeAthlete = "tb_edu_athlete as t1, t1.athlete_name, t1.id, t1.sport_code = " + sportCode + " | tb_edu_sport as t2, t2.sport_name, t2.sport_code=t1.sport_code | tb_edu_coach as t3, t3.coach_name, t3.id=t1.coach_id";
                    this.$nextTick(() => {
                      this.form.setFieldsValue({'athleteIds':athleteIds});
                    })
                  }
                });
              }
            }
          });
          // 获取所选测试标准组别的在训小项
          let groupEventCodesDictCode = "tb_edu_athlete_selection_group, event_codes, id, id = " + selectedValue;
          initDictOptions(groupEventCodesDictCode).then((res) => {
            if (res.success) {
              let groupIds = res.result[0];
              let eventCodes = groupIds.text;
              this.dictCodeSportEvent = "tb_edu_sport_event as t1, t1.event_name, t1.event_code, t1.enable_status=1 and (t1.event_code='" + eventCodes.replace(/,/g,"' or t1.event_code = '") + "') | tb_edu_sport t2, t2.sport_name, t2.sport_code = t1.sport_code | tb_edu_sport_disciplines t3, t3.disciplines_name, t3.disciplines_code=t1.disciplines_code";
              this.$nextTick(() => {
                this.form.setFieldsValue({'eventCodes':eventCodes});
              })
            }
          });

          // 获取所选测试标准组别的测试指标
          let dictCodeGroupIndexCodes = "tb_edu_athlete_selection_group, index_codes, id, id = " + selectedValue;
          initDictOptions(dictCodeGroupIndexCodes).then((res) => {
            if (res.success) {
              let groupIds = res.result[0];
              let indexCodes = groupIds.text;
              this.dictCodeGroupIndexCodes = 'tb_edu_athlete_selection_index, cn_name, index_code, index_code=' + indexCodes.replace(/,/g," or index_code = ") ;
              this.$nextTick(() => {
                this.form.setFieldsValue({'indexCodes':indexCodes});
              })
            }
          });
        } else {
        }
      },

      handleCoachIdChange (selectedValue) {
        console.log("handleCoachIdChange: ",selectedValue);

        let dictCodeAthleteIds = "tb_edu_athlete, athlete_name, id, coach_id = '" + selectedValue.replace(/,/g,"' or coach_id = '") + "' order by coach_id asc";
        initDictOptions(dictCodeAthleteIds).then((res) => {
          if (res.success) {
            let athleteIds = "";
            for (let i = 0; i < res.result.length; i++) {
              if (i == 0) {
                athleteIds += res.result[i].value;
              } else {
                athleteIds += "," + res.result[i].value;
              }
            }
            this.dictCodeAthlete = "tb_edu_athlete as t1, t1.athlete_name, t1.id, t1.coach_id = '" + selectedValue.replace(/,/g,"' or t1.coach_id = '") + "' | tb_edu_sport as t2, t2.sport_name, t2.sport_code=t1.sport_code | tb_edu_coach as t3, t3.coach_name, t3.id=t1.coach_id";
            this.$nextTick(() => {
              this.form.setFieldsValue({'athleteIds':athleteIds});
            })
          }
        });
      }
    }
  }
</script>