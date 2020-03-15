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

        <a-form-item label="所属组别" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-form-container disabled>
            <j-dict-select-tag placeholder="请选择所属组别" v-decorator="[ 'groupId', validatorRules.groupId]"  :dictCode="fnDictCodeGroupId"/>
<!--            <a-input v-decorator="[ 'groupId', validatorRules.groupId]" placeholder="请输入所属组别"></a-input>-->
          </j-form-container>
        </a-form-item>
        <a-form-item label="指标" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag placeholder="请选择指标" v-decorator="['indexCode', validatorRules.indexCode]" :dictOptions="dictOptions.indexCodes" @change="handleIndexCodeChange"/>
        </a-form-item>
        <a-form-item label="性别" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag placeholder="请选择性别" v-decorator="['gender', validatorRules.gender]" :dictOptions="dictOptions.indexGender" @change="handleGenderChange"/>
<!--          <a-select placeholder="请选择性别"  v-decorator="['gender', validatorRules.gender]" @change="handleGenderChange">-->
<!--            <a-select-option value="1">男</a-select-option>-->
<!--            <a-select-option value="2">女</a-select-option>-->
<!--            <a-select-option value="3">男|女</a-select-option>-->
<!--          </a-select>-->
        </a-form-item>

        <!-- 子表单区域 -->
        <a-tabs defaultActiveKey="1" >
          <a-tab-pane tab="批量添加评分标准" key="1">
            <div>
              <a-row type="flex" style="margin-bottom:16px; text-align:center;" :gutter="16">
                <a-col :span="4"><strong>指标</strong></a-col>
                <a-col :span="2"><strong>性别</strong></a-col>
                <a-col :span="2"><strong>起始年龄</strong></a-col>
                <a-col :span="2"><strong>截止年龄</strong></a-col>
                <a-col :span="2"><strong>最小数值</strong></a-col>
                <a-col :span="2"><strong>最大数值</strong></a-col>
                <a-col :span="2"><strong>得分</strong></a-col>
                <a-col :span="2"><strong>匹配等级</strong></a-col>
                <a-col :span="4"><strong>操作</strong></a-col>
              </a-row>

              <a-row type="flex" style="text-align: center;" :gutter="16" v-for="(item, index) in indexGradeList" :key="index">
                <a-col :span="4">
                  <a-form-item>
                    {{dictTextIndexCode(item.indexCode)}}
                  </a-form-item>
                </a-col>
                <a-col :span="2">
                  <a-form-item>
                    {{dictTextGender(item.gender)}}
                  </a-form-item>
                </a-col>
                <a-col :span="2">
                  <a-form-item>
                    <a-input v-decorator="['indexGradeList['+index+'].startAge', {'initialValue':item.startAge, rules: [{ required: true, message: '请输入起始年龄!' }]}]" placeholder="起始年龄" style="width: 100%"/>
                  </a-form-item>
                </a-col>
                <a-col :span="2">
                  <a-form-item>
                    <a-input v-decorator="['indexGradeList['+index+'].endAge', {'initialValue':item.endAge, rules: [{ required: true, message: '请输入截止年龄!' }]}]" placeholder="截止年龄" style="width: 100%"/>
                  </a-form-item>
                </a-col>
                <a-col :span="2">
                  <a-form-item>
                    <a-input v-decorator="['indexGradeList['+index+'].minData', {'initialValue':item.minData, rules: [{ required: true, message: '请输入最小数值!' }]}]" placeholder="最小数值" style="width: 100%"/>
                  </a-form-item>
                </a-col>
                <a-col :span="2">
                  <a-form-item>
                    <a-input v-decorator="['indexGradeList['+index+'].maxData', {'initialValue':item.maxData, rules: [{ required: true, message: '请输入最大数值!' }]}]" placeholder="最大数值" style="width: 100%"/>
                  </a-form-item>
                </a-col>
                <a-col :span="2">
                  <a-form-item>
                    <a-input v-decorator="['indexGradeList['+index+'].score', {'initialValue':item.score, rules: [{ required: true, message: '请输入得分!' }]}]" placeholder="得分" style="width: 100%"/>
                  </a-form-item>
                </a-col>
                <a-col :span="2">
                  <a-form-item>
                    {{item.testRating?dictTextTestRating(item.testRating):"待计算"}}
                  </a-form-item>
                </a-col>
                <a-col :span="4">
                  <a-form-item>
                    <a-button @click="addRowIndexGrade" icon="plus"></a-button>&nbsp;
                    <a-button @click="delRowIndexGrade(index)" icon="minus"></a-button>
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

  import { httpAction, getAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag'
  import {initDictOptions, filterMultiDictText} from '@/components/dict/JDictSelectUtil'

  export default {
    name: "AthleteSelectionGroupIndexGradeModal",
    components: { 
      JDictSelectTag,
      JSearchSelectTag,
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        width:1000,
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
        dictCodeGroupId: '',
        validatorRules:{
          groupId:{rules: [{ required: true, message: '请输入所属组别!' }]},
          indexCode:{rules: [{ required: true, message: '请选择指标!' }]},
          gender:{rules: [{ required: true, message: '请选择性别!' }]},
        },
        indexGradeList:[],
        /* 分页参数 */
        indexGradePagination:{
          current: 1,
          pageSize: 10,
          pageSizeOptions: ['5', '10', '20'],
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
          groupList: "/edusport/athleteSelectionGroup/list",
          list: "/edusport/athleteSelectionGroupIndexGrade/list",
          addBatch: "/edusport/athleteSelectionGroupIndexGrade/addBatch",
          editBatch: "/edusport/athleteSelectionGroupIndexGrade/editBatch",
          delete: "/edusport/athleteSelectionGroupIndexGrade/delete",
        },
        dictOptions:{
          indexCodes:[],
          indexRating:[],
          indexGender:[{text:"男", title:"男", value:"1"}, {text:"女", title:"女", value:"2"}, {text:"男|女", title:"男|女", value:"3"}],
        },
      }
    },
    computed: {
      fnDictCodeGroupId: function() {
        console.log("fnDictCodeGroupId: ", this.dictCodeGroupId);
        return this.dictCodeGroupId;
      },
    },
    created () {
      // 初始化测试大纲指标等级字典
      initDictOptions('index_rating').then((res) => {
        if (res.success) {
          this.$set(this.dictOptions, 'indexRating', res.result)
        }
      })
    },
    methods: {
      add(groupId) {
        this.hiding = true;
        if (groupId) {
          this.groupId = groupId;
          this.edit({groupId}, '');
        } else {
          this.$message.warning("请选择一个测试组别");
        }
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'groupId','indexCode','gender'))
        })
        // 初始化所属组别字典
        if (this.model.groupId) {
          this.dictCodeGroupId = "tb_edu_athlete_selection_group, group_name, id, id = " + this.model.groupId ;
        }
        // 初始化组别指标下拉项
        this.getDictCodeGroupIndexCodes(this.model.groupId);

        // 初始化评分标准列表
        this.indexGradeList = [];
        if (this.model.indexCode && this.model.gender) {
          this.getIndexGradeList(this.model.groupId, this.model.indexCode, this.model.gender);
        }
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleIndexCodeChange (indexCode) {
        // 触发表单验证(指标，性别，验证错误也要加载对应评分标准)
        this.form.validateFields(['indexCode', 'gender'], (err, values) => {
            this.getIndexGradeList(values.groupId, indexCode, values.gender);
        })
      },
      handleGenderChange (gender) {
        // 触发表单验证(指标，性别，验证错误也要加载对应评分标准)
        this.form.validateFields(['indexCode', 'gender'], (err, values) => {
            this.getIndexGradeList(values.groupId, values.indexCode, gender);
        })
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
              httpurl+=this.url.addBatch;
              method = 'post';
            }else{
              httpurl+=this.url.editBatch;
               method = 'put';
            }

            let formData = Object.assign(this.model, values);
            console.log("httpurl: ",httpurl);
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
      popupCallback(row) {
        this.form.setFieldsValue(pick(row,'groupId','indexCode','gender'))
      },
      // 获取测试组别下评分标准列表
      getIndexGradeList(groupId, indexCode, gender) {
        const that = this;
        var indexGradeParams = {
          groupId: groupId,
          indexCode: indexCode?indexCode:'',
          gender: gender?gender:'',
          column: "",
          order: "",
          pageNo: that.indexGradePagination.current,
          pageSize: that.indexGradePagination.pageSize,
        };//查询条件

        that.confirmLoading = true;
        getAction(that.url.list, indexGradeParams).then((res) => {
          if (res.success) {
            console.log("getIndexGradeList.indexGradeParams: ", indexGradeParams);
            console.log("getIndexGradeList.res.result: ", res.result);
            that.indexGradeList = res.result.records;
            that.indexGradePagination.total = res.result.total;
            if (that.indexGradeList.length === 0){
              that.indexGradeList = [{"indexCode": indexCode, "gender": gender}];
            }
            this.$forceUpdate();
          }
          if (res.code === 510) {
            that.$message.warning(res.message)
          }
        }).finally(() => {
          that.confirmLoading = false;
        });
      },
      // 获取测试组别下指标
      getDictCodeGroupIndexCodes(groupId) {
        const that = this;
        var dictIndexCodeParams = {
          id: groupId,
          column: "",
          order: "",
          pageNo: 1,
          pageSize: 1,
        };//查询条件

        that.confirmLoading = true;
        getAction(that.url.groupList, dictIndexCodeParams).then((res) => {
          if (res.success) {
            console.log("getDictCodeGroupIndexCodes.dictIndexCodeParams: ", dictIndexCodeParams)
            console.log("getDictCodeGroupIndexCodes.res.result: ", res.result)
            let group = res.result.records[0];
            if (group && group.indexCodes) {
              let dictCodeGroupIndexCodes = 'tb_edu_athlete_selection_index, cn_name, index_code, index_code=' + group.indexCodes.replace(/,/g," or index_code = ") ;

              initDictOptions(dictCodeGroupIndexCodes).then((res) => {
                if (res.success) {
                  console.log("indexCodes: ", res.result)
                  this.$set(this.dictOptions, 'indexCodes', res.result)
                }
              })
            }
          }
          if (res.code === 510) {
            that.$message.warning(res.message)
          }
        }).finally(() => {
          that.confirmLoading = false;
        });
      },
      dictTextIndexCode(indexCode) {
        return filterMultiDictText(this.dictOptions['indexCodes'], indexCode+"");
      },
      dictTextGender(gender) {
        return filterMultiDictText(this.dictOptions['indexGender'], gender+"");
      },
      dictTextTestRating(indexRating) {
        return filterMultiDictText(this.dictOptions['indexRating'], indexRating+"");
      },
      addRowIndexGrade () {
        // 评分标准行数最大行数设置为：10行
        if (this.form.getFieldValue('indexGradeList').length == 10) {
          this.$message.warning("不能再添加了！");
          return;
        }

        // 触发表单验证(指标，性别)
        this.form.validateFields(['indexCode', 'gender'], (err, values) => {
          if (!err) {
            this.indexGradeList.push({ "indexCode": values.indexCode, "gender": values.gender });
            this.$forceUpdate();
          }
        })
      },
      delRowIndexGrade (index) {
        if (this.form.getFieldValue('indexGradeList').length == 1) {
          this.$message.warning("不能再删除了！");
          return;
        }

        this.indexGradeList.splice(index,1);
        this.$forceUpdate();
      },

    }
  }
</script>