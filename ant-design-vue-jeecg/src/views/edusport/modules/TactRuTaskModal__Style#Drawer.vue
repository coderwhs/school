<template>
  <a-drawer
    :title="title"
    :width="width"
    placement="right"
    :closable="false"
    @close="close"
    :visible="visible">
  
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item label="assignee" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'assignee', validatorRules.assignee]" placeholder="请输入assignee"></a-input>
        </a-form-item>
        <a-form-item label="category" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'category', validatorRules.category]" placeholder="请输入category"></a-input>
        </a-form-item>
        <a-form-item label="claimTime" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择claimTime" v-decorator="[ 'claimTime', validatorRules.claimTime]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="创建人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'createBy', validatorRules.createBy]" placeholder="请输入创建人"></a-input>
        </a-form-item>
        <a-form-item label="createTime" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择createTime" v-decorator="[ 'createTime', validatorRules.createTime]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="delegation" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'delegation', validatorRules.delegation]" placeholder="请输入delegation"></a-input>
        </a-form-item>
        <a-form-item label="description" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['description']" rows="4" placeholder="请输入description"/>
        </a-form-item>
        <a-form-item label="dueDate" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择dueDate" v-decorator="[ 'dueDate', validatorRules.dueDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="executionId" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'executionId', validatorRules.executionId]" placeholder="请输入executionId"></a-input>
        </a-form-item>
        <a-form-item label="formKey" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'formKey', validatorRules.formKey]" placeholder="请输入formKey"></a-input>
        </a-form-item>
        <a-form-item label="id" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'id', validatorRules.id]" placeholder="请输入id"></a-input>
        </a-form-item>
        <a-form-item label="idLinkCount" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'idLinkCount', validatorRules.idLinkCount]" placeholder="请输入idLinkCount" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="isCountEnabled" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'isCountEnabled', validatorRules.isCountEnabled]" placeholder="请输入isCountEnabled" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="name" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'name', validatorRules.name]" placeholder="请输入name"></a-input>
        </a-form-item>
        <a-form-item label="owner" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'owner', validatorRules.owner]" placeholder="请输入owner"></a-input>
        </a-form-item>
        <a-form-item label="parentTaskId" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'parentTaskId', validatorRules.parentTaskId]" placeholder="请输入parentTaskId"></a-input>
        </a-form-item>
        <a-form-item label="priority" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'priority', validatorRules.priority]" placeholder="请输入priority" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="procDefId" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'procDefId', validatorRules.procDefId]" placeholder="请输入procDefId"></a-input>
        </a-form-item>
        <a-form-item label="procInstId" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'procInstId', validatorRules.procInstId]" placeholder="请输入procInstId"></a-input>
        </a-form-item>
        <a-form-item label="rev" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'rev', validatorRules.rev]" placeholder="请输入rev" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="scopeDefinitionId" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'scopeDefinitionId', validatorRules.scopeDefinitionId]" placeholder="请输入scopeDefinitionId"></a-input>
        </a-form-item>
        <a-form-item label="scopeId" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'scopeId', validatorRules.scopeId]" placeholder="请输入scopeId"></a-input>
        </a-form-item>
        <a-form-item label="scopeType" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'scopeType', validatorRules.scopeType]" placeholder="请输入scopeType"></a-input>
        </a-form-item>
        <a-form-item label="subScopeId" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'subScopeId', validatorRules.subScopeId]" placeholder="请输入subScopeId"></a-input>
        </a-form-item>
        <a-form-item label="subTaskCount" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'subTaskCount', validatorRules.subTaskCount]" placeholder="请输入subTaskCount" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="suspensionState" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'suspensionState', validatorRules.suspensionState]" placeholder="请输入suspensionState" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="taskDefId" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'taskDefId', validatorRules.taskDefId]" placeholder="请输入taskDefId"></a-input>
        </a-form-item>
        <a-form-item label="taskDefKey" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'taskDefKey', validatorRules.taskDefKey]" placeholder="请输入taskDefKey"></a-input>
        </a-form-item>
        <a-form-item label="tenantId" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'tenantId', validatorRules.tenantId]" placeholder="请输入tenantId"></a-input>
        </a-form-item>
        <a-form-item label="更新人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'updateBy', validatorRules.updateBy]" placeholder="请输入更新人"></a-input>
        </a-form-item>
        <a-form-item label="更新时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择更新时间" v-decorator="[ 'updateTime', validatorRules.updateTime]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="varCount" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'varCount', validatorRules.varCount]" placeholder="请输入varCount" style="width: 100%"/>
        </a-form-item>
        
      </a-form>
    </a-spin>
    <a-button type="primary" @click="handleOk">确定</a-button>
    <a-button type="primary" @click="handleCancel">取消</a-button>
  </a-drawer>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JDate from '@/components/jeecg/JDate'  
  
  export default {
    name: "TactRuTaskModal",
    components: { 
      JDate,
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
        validatorRules:{
        assignee:{},
        category:{},
        claimTime:{},
        createBy:{rules: [{ required: true, message: '请输入创建人!' }]},
        createTime:{},
        delegation:{},
        description:{},
        dueDate:{},
        executionId:{},
        formKey:{},
        idLinkCount:{},
        isCountEnabled:{},
        name:{},
        owner:{},
        parentTaskId:{},
        priority:{},
        procDefId:{},
        procInstId:{},
        rev:{},
        scopeDefinitionId:{},
        scopeId:{},
        scopeType:{},
        subScopeId:{},
        subTaskCount:{},
        suspensionState:{},
        taskDefId:{},
        taskDefKey:{},
        tenantId:{},
        updateBy:{rules: [{ required: true, message: '请输入更新人!' }]},
        updateTime:{},
        varCount:{},
        },
        url: {
          add: "/edusport/tactRuTask/add",
          edit: "/edusport/tactRuTask/edit",
        }
     
      }
    },
    created () {
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
          this.form.setFieldsValue(pick(this.model,'assignee','category','claimTime','createBy','createTime','delegation','description','dueDate','executionId','formKey','idLinkCount','isCountEnabled','name','owner','parentTaskId','priority','procDefId','procInstId','rev','scopeDefinitionId','scopeId','scopeType','subScopeId','subTaskCount','suspensionState','taskDefId','taskDefKey','tenantId','updateBy','updateTime','varCount'))
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
        this.form.setFieldsValue(pick(row,'assignee','category','claimTime','createBy','createTime','delegation','description','dueDate','executionId','formKey','idLinkCount','isCountEnabled','name','owner','parentTaskId','priority','procDefId','procInstId','rev','scopeDefinitionId','scopeId','scopeType','subScopeId','subTaskCount','suspensionState','taskDefId','taskDefKey','tenantId','updateBy','updateTime','varCount'))
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
</style>