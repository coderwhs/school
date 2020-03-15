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

        <a-form-item label="组别名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'groupName', validatorRules.groupName]" placeholder="请输入组别名称"></a-input>
        </a-form-item>
        <a-form-item label="启用状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['enableStatus', validatorRules.enableStatus]" :trigger-change="true" dictCode="enable_status" placeholder="请选择启用状态"/>
        </a-form-item>
        <a-form-item label="选择运动项目大项" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag v-decorator="['sportCode', validatorRules.sportCode]" dict="tb_edu_sport,sport_name,sport_code" @change="handleSportCodeChange"/>
        </a-form-item>
        <a-form-item label="选择在训小项" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-multi-select-tag type="list_multi" v-decorator="['eventCodes', validatorRules.eventCodes]" :trigger-change="true" :dictCode="sportEventDictCode" placeholder="请选择小项"/>
        </a-form-item>
        <a-form-item label="选择指标分类" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <!-- @change="handleIndexCatCodeChange"-->
          <j-dict-select-tag type="list" v-model="indexCatCode" :trigger-change="true" dictCode="tb_edu_athlete_selection_index_cat,index_cat_name,index_cat_code" placeholder="请选择指标分类"/>
        </a-form-item>
        <a-form-item label="选择测试指标" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-tree-select
            v-decorator="['indexCodes', validatorRules.indexCodes]"
            placeholder="请选择测试指标"
            dict="tb_edu_athlete_selection_index, cn_name, index_code"
            pidField="parent_code"
            :pidValue="indexCatCode"
            condition='{"enable_status":"1"}'
            multiple
          />
        </a-form-item>

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
  import { duplicateCheck } from '@/api/api'
  import pick from 'lodash.pick'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import JMultiSelectTag from "@/components/dict/JMultiSelectTag"
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag'
  import JTreeSelect from '@/components/jeecg/JTreeSelect'
  
  export default {
    name: "AthleteSelectionGroupModal",
    components: { 
      JDictSelectTag,
      JMultiSelectTag,
      JSearchSelectTag,
      JTreeSelect,
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
        groupName:{rules: [{ required: true, message: '请输入组别名称!' }, {validator: this.validateDuplicateCheckGroupName}]},
        enableStatus:{rules: [{ required: true, message: '请选择启用状态!' }]},
        sportCode:{rules: [{ required: true, message: '请选择大项!' }]},
        eventCodes:{rules: [{ required: true, message: '请选择小项!' }]},
        indexCodes:{rules: [{ required: true, message: '请选择测试指标!' }]},
        },

        // sportEventDictCode: 'tb_edu_sport_event,event_name,event_code, enable_status=1',
        // sportEventDictCode: 'tb_edu_sport_event,event_name,event_code, t1.enable_status=1| tb_edu_sport, sport_name, sport_code| tb_edu_sport_disciplines, disciplines_name, disciplines_code',
        sportEventDictCode: '',
        indexCatCode: '',
        url: {
          add: "/edusport/athleteSelectionGroup/add",
          edit: "/edusport/athleteSelectionGroup/edit",
        }
     
      }
    },
    created () {
    },
    // computed: {
    //   sportEventDictCode: function(record) {
    //     const that = this;
    //     console.log("this.form: ", this.form);
    //     // 触发表单验证
    //     this.form.validateFields((err, values) => {
    //       let formData = Object.assign(this.model, values);
    //       console.log("formData: ", formData);
    //     })
    //    return "tb_edu_sport_event,event_name,event_code, enable_status=1 and sport_code='" + this.model.sportCode + "'";
    //   }
    // },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'groupName','enableStatus','sportCode','eventCodes','indexCodes'))
        })
        // 初始化在训小项字典
        if (this.model.eventCodes) {
          this.sportEventDictCode = "tb_edu_sport_event as t1, t1.event_name, t1.event_code, t1.enable_status=1 and (t1.event_code='" + this.model.eventCodes.replace(/,/g,"' or t1.event_code = '") + "') | tb_edu_sport t2, t2.sport_name, t2.sport_code = t1.sport_code | tb_edu_sport_disciplines t3, t3.disciplines_name, t3.disciplines_code=t1.disciplines_code";
        }
      },
      validateDuplicateCheckGroupName(rule, value, callback) {
        // 重复校验
        var params = {
          tableName: 'tb_edu_athlete_selection_group',
          fieldName: 'group_name',
          fieldVal: value,
          dataId: this.model.id
        }
        duplicateCheck(params).then((res) => {
          if (res.success) {
            callback()
          } else {
            callback(res.message)
          }
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
      handleSportCodeChange (selectedValue) {
        if (selectedValue) {
          console.log("handleSportCodeChange: ",selectedValue);
          this.sportEventDictCode = "tb_edu_sport_event as t1, t1.event_name, t1.event_code, t1.enable_status=1 and t1.sport_code='" + selectedValue + "'" + "| tb_edu_sport t2, t2.sport_name, t2.sport_code = t1.sport_code | tb_edu_sport_disciplines t3, t3.disciplines_name, t3.disciplines_code=t1.disciplines_code";
        } else {
          this.sportEventDictCode = '';
        }
      },
      handleCancel () {
        this.close()
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'groupName','enableStatus','sportCode','eventCodes','indexCodes'))
      }
      
    }
  }
</script>

<style lang="less" scoped>
/** Button按钮间距 */
  .ant-btn {
    margin-left: 30px;
    margin-bottom: 30px;
    float: right;
  }

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