<template>
  <a-card :bordered="false">

  <div>
    <a-tooltip placement="top" title="根据大纲设置指标的权重，计算测试得分" :autoAdjustOverflow="true">
      <a-button @click="handleTableSet" type="primary">评分</a-button>
    </a-tooltip>


    <j-editable-table
      ref="editableTable"
      :loading="loading"
      :columns="columns"
      :dataSource="dataSource"
      :rowNumber="true"
      :rowSelection="true"
      :actionButton="true"
      :dragSort="false"
      style="margin-top: 8px;"
      @selectRowChange="handleSelectRowChange">

      <template v-slot:action="props">
        <a @click="handleDelete(props)">{{ props.text }}</a>
      </template>

    </j-editable-table>
  </div>

  </a-card>
</template>

<script>
  import moment from 'moment'
  import { FormTypes } from '@/utils/JEditableTableUtil'
  import { randomUUID, randomNumber } from '@/utils/util'
  import JEditableTable from '@/components/jeecg/JEditableTable'

  export default {
    name: 'DefaultTable',
    components: { JEditableTable },
    data() {
      return {
        loading: false,
        columns: [
          {
            title: '类别',
            key: 'indexType',
            width: '20%',
            type: FormTypes.normal,
            defaultValue: '',
          },
          {
            title: '指标',
            key: 'indexName',
            width: '20%',
            type: FormTypes.normal,
            defaultValue: '',
          },
          {
            title: '指标权重(%)',
            key: 'indexWeight',
            width: '20%',
            type: FormTypes.inputNumber,
            defaultValue: 4,
            placeholder: '${title}',
            validateRules: [{ required: true, message: '请输入${title}' }]
          },
          {
            title: '类权重',
            key: 'indexTypeWeight',
            width: '20%',
            type: FormTypes.normal,
            defaultValue: '',
          },
        ],
        dataSource: [
          { indexType: '身体形态', indexName: "身高（厘米）", indexWeight: 4, indexTypeWeight: '' },
          { indexType: '身体形态', indexName: "BMI指数", indexWeight: 4, indexTypeWeight: '' },
          { indexType: '身体形态', indexName: "大小拇指间距（厘米）", indexWeight: 6, indexTypeWeight: '' },
          { indexType: '身体形态', indexName: "指距-身高（厘米）", indexWeight: 6, indexTypeWeight: '' },
          { indexType: '身体机能', indexName: "心功指数", indexWeight: 6, indexTypeWeight: '' },
          { indexType: '身体机能', indexName: "肺活量/体重（毫升/千克）", indexWeight: 6, indexTypeWeight: '' },
          { indexType: '运动能力及专项技术', indexName: "30米跑（秒）", indexWeight: 4, indexTypeWeight: '' },
          { indexType: '运动能力及专项技术', indexName: "立定跳远（米）", indexWeight: 4, indexTypeWeight: '' },
          { indexType: '运动能力及专项技术', indexName: "1分钟仰卧起坐（次）", indexWeight: 4, indexTypeWeight: '' },
          { indexType: '运动能力及专项技术', indexName: "转间距（厘米）", indexWeight: 4, indexTypeWeight: '' },
          { indexType: '运动能力及专项技术', indexName: "20秒反复侧跨步（次）", indexWeight: 4, indexTypeWeight: '' },
          { indexType: '运动能力及专项技术', indexName: "（5~25米）5折返跑（秒）", indexWeight: 4, indexTypeWeight: '' },
          { indexType: '运动能力及专项技术', indexName: "手球掷远（米）", indexWeight: 4, indexTypeWeight: '' },
          { indexType: '运动能力及专项技术', indexName: "绕杆运球（秒）", indexWeight: 4, indexTypeWeight: '' },
          { indexType: '心理指标', indexName: "选择反应时（秒）", indexWeight: 6, indexTypeWeight: '' },
          { indexType: '心理指标', indexName: "辨别反应时（秒）", indexWeight: 6, indexTypeWeight: '' },
          { indexType: '教练员评定', indexName: "体质状况", indexWeight: 3, indexTypeWeight: '' },
          { indexType: '教练员评定', indexName: "技术能力", indexWeight: 4, indexTypeWeight: '' },
          { indexType: '教练员评定', indexName: "场上意识", indexWeight: 4, indexTypeWeight: '' },
          { indexType: '教练员评定', indexName: "合作意识和意志品质", indexWeight: 5, indexTypeWeight: '' },
        ],
        selectedRowIds: []
      }
    },
    mounted() {
      this.randomData(23, false)
    },
    methods: {
      /** 计算测试评分 */
      handleTableSet() {
        this.randomData(12, true)
      },

      handleSelectRowChange(selectedRowIds) {
        this.selectedRowIds = selectedRowIds
      },

      /* 随机生成数据 */
      randomData(size, loading = false) {
        if (loading) {
          this.loading = true
        }

        let randomDatetime = () => {
          let time = parseInt(randomNumber(1000, 9999999999999))
          return moment(new Date(time)).format('YYYY-MM-DD HH:mm:ss')
        }

        let begin = Date.now()
        // let values = []
        // for (let i = 0; i < size; i++) {
        //   values.push({
        //     id: randomUUID(),
        //     indexType: `name_${i + 1}`,
        //     indexName: ['string', ['int', 'double', 'boolean'][randomNumber(0, 2)]],
        //     indexWeight: ['string', 'int', 'double', 'boolean'][randomNumber(0, 3)],
        //     indexTypeWeight: randomNumber(0, 233),
        //   })
        // }

        // this.dataSource = values

        this.dataSource = [
          { indexType: '身体形态', indexName: "身高（厘米）", indexWeight: 4, indexTypeWeight: '' },
          { indexType: '身体形态', indexName: "BMI指数", indexWeight: 4, indexTypeWeight: '' },
          { indexType: '身体形态', indexName: "大小拇指间距（厘米）", indexWeight: 6, indexTypeWeight: '' },
          { indexType: '身体形态', indexName: "指距-身高（厘米）", indexWeight: 6, indexTypeWeight: '' },
          { indexType: '身体机能', indexName: "心功指数", indexWeight: 6, indexTypeWeight: '' },
          { indexType: '身体机能', indexName: "肺活量/体重（毫升/千克）", indexWeight: 6, indexTypeWeight: '' },
          { indexType: '运动能力及专项技术', indexName: "30米跑（秒）", indexWeight: 4, indexTypeWeight: '' },
          { indexType: '运动能力及专项技术', indexName: "立定跳远（米）", indexWeight: 4, indexTypeWeight: '' },
          { indexType: '运动能力及专项技术', indexName: "1分钟仰卧起坐（次）", indexWeight: 4, indexTypeWeight: '' },
          { indexType: '运动能力及专项技术', indexName: "转间距（厘米）", indexWeight: 4, indexTypeWeight: '' },
          { indexType: '运动能力及专项技术', indexName: "20秒反复侧跨步（次）", indexWeight: 4, indexTypeWeight: '' },
          { indexType: '运动能力及专项技术', indexName: "（5~25米）5折返跑（秒）", indexWeight: 4, indexTypeWeight: '' },
          { indexType: '运动能力及专项技术', indexName: "手球掷远（米）", indexWeight: 4, indexTypeWeight: '' },
          { indexType: '运动能力及专项技术', indexName: "绕杆运球（秒）", indexWeight: 4, indexTypeWeight: '' },
          { indexType: '心理指标', indexName: "选择反应时（秒）", indexWeight: 6, indexTypeWeight: '' },
          { indexType: '心理指标', indexName: "辨别反应时（秒）", indexWeight: 6, indexTypeWeight: '' },
          { indexType: '教练员评定', indexName: "体质状况", indexWeight: 3, indexTypeWeight: '' },
          { indexType: '教练员评定', indexName: "技术能力", indexWeight: 4, indexTypeWeight: '' },
          { indexType: '教练员评定', indexName: "场上意识", indexWeight: 4, indexTypeWeight: '' },
          { indexType: '教练员评定', indexName: "合作意识和意志品质", indexWeight: 5, indexTypeWeight: '' },
        ];

        let end = Date.now()
        let diff = end - begin

        if (loading && diff < size) {
          setTimeout(() => {
            this.loading = false
          }, size - diff)
        }

      },

      handleDelete(props) {
        let { rowId, target } = props
        target.removeRows(rowId)
      }
    }
  }
</script>

<style scoped>

</style>