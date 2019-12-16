<template>
  <div class="page-header-index-wide">
    <a-row :gutter="24">
      <a-col :sm="24" :md="12" :xl="12" :style="{ marginBottom: '24px' }">
        <chart-card :loading="loading" title="教练员人数" :total=" coachTotal | NumberFormat">
          <a-tooltip title="指标说明" slot="action">
            <a-icon type="info-circle-o" />
          </a-tooltip>
<!--          <div>-->
<!--            <mini-bar :height="40" />-->
<!--          </div>-->
          <template slot="footer"></template>
        </chart-card>
      </a-col>
      <a-col :sm="24" :md="12" :xl="12" :style="{ marginBottom: '24px' }">
        <chart-card :loading="loading" title="运动员人数" :total=" athleteTotal | NumberFormat">
          <a-tooltip title="指标说明" slot="action">
            <a-icon type="info-circle-o" />
          </a-tooltip>
          <div>
          </div>
          <template slot="footer"></template>
        </chart-card>
      </a-col>
    </a-row>

    <a-card :loading="loading" :bordered="false" :body-style="{padding: '0'}">
      <div class="salesCard">
        <a-tabs default-active-key="1" size="large" :tab-bar-style="{marginBottom: '24px', paddingLeft: '16px'}">
          <a-tab-pane loading="true" tab="运动员" key="1">
<!--            <a-row>-->
<!--              <a-col :xl="16" :lg="12" :md="12" :sm="24" :xs="24">-->
                <bar title="运动员技术等级人数分布" :dataSource="athleteTechGradeGroup"/>
<!--              </a-col>-->
<!--              <a-col :xl="8" :lg="12" :md="12" :sm="24" :xs="24">-->
<!--                <rank-list title="教练员达级人数排行" :list="rankList"/>-->
<!--              </a-col>-->
<!--            </a-row>-->
          </a-tab-pane>
          <a-tab-pane tab="教练员" key="2">
            <bar title="教练员学历结构分布" :dataSource="barData"/>
          </a-tab-pane>
        </a-tabs>
      </div>
    </a-card>

  </div>
</template>

<script>
  import ChartCard from '@/components/ChartCard'
  import ACol from "ant-design-vue/es/grid/Col"
  import ATooltip from "ant-design-vue/es/tooltip/Tooltip"
  import MiniArea from '@/components/chart/MiniArea'
  import MiniBar from '@/components/chart/MiniBar'
  import MiniProgress from '@/components/chart/MiniProgress'
  import RankList from '@/components/chart/RankList'
  import Bar from '@/components/chart/Bar'
  import LineChartMultid from '@/components/chart/LineChartMultid'
  import HeadInfo from '@/components/tools/HeadInfo.vue'
  import Trend from '@/components/Trend'

  import {getAction} from '@/api/manage'

  // const rankList = []
  // for (let i = 0; i < 7; i++) {
  //   rankList.push({
  //     name: '白鹭岛 ' + (i+1) + ' 号店',
  //     total: 1234.56 - i * 100
  //   })
  // }
  const barData = []
  // for (let i = 0; i < 12; i += 1) {
  //   barData.push({
  //     z:1,
  //     x: `${i + 1}月`,
  //     y: Math.floor(Math.random() * 1000) + 200
  //   })
  // }
  barData.push({
    z:1,
    x: `大专`,
    y: Math.floor(Math.random() * 100) + 20
  })
  barData.push({
    z:1,
    x: `本科`,
    y: Math.floor(Math.random() * 100) + 20
  })
  barData.push({
    z:1,
    x: `硕士研究生`,
    y: Math.floor(Math.random() * 100) + 20
  })
  barData.push({
    z:1,
    x: `博士研究生`,
    y: Math.floor(Math.random() * 100) + 20
  })

  export default {
    name: "Analysis",
    components: {
      ATooltip,
      ACol,
      ChartCard,
      MiniArea,
      MiniBar,
      MiniProgress,
      RankList,
      Bar,
      Trend,
      LineChartMultid,
      HeadInfo
    },
    data() {
      return {
        athleteTotal:'',
        coachTotal:'',
        athleteTechGradeGroup:[],
        barData,

        url: {
          coachTotal: "/edusport/graphReport/coach/total",
          athleteTotal: "/edusport/graphReport/athlete/total",
          athleteTechGradeGroup: "/edusport/graphReport/athlete/techGrade/group",
        },

        loading: true,
        // center: null,
        // rankList,
        // barData,
      }
    },
    created() {
      setTimeout(() => {
        this.loading = !this.loading
      }, 1000)
      this.initGraphReportData();
      // console.log("rankList: ", rankList);
      // console.log("barData: ", barData);
    },
    methods: {
      initGraphReportData () {
        let that = this;
        getAction(that.url.coachTotal, {}).then((res) => {
          if (res.success) {
            this.coachTotal = res.result.total
          }
        });
        getAction(that.url.athleteTotal, {}).then((res) => {
          if (res.success) {
            this.athleteTotal = res.result.total
          }
        });
        getAction(that.url.athleteTechGradeGroup, {}).then((res) => {
          if (res.success) {
            this.athleteTechGradeGroup = res.result
            console.log("athleteTechGradeGroup: ", this.athleteTechGradeGroup);
          }
        })
      },
    }
  }
</script>

<style lang="scss" scoped>
  .circle-cust{
    position: relative;
    top: 28px;
    left: -100%;
  }
  .extra-wrapper {
    line-height: 55px;
    padding-right: 24px;

    .extra-item {
      display: inline-block;
      margin-right: 24px;

      a {
        margin-left: 24px;
      }
    }
  }

  /* 首页访问量统计 */
  .head-info {
    position: relative;
    text-align: left;
    padding: 0 32px 0 0;
    min-width: 125px;

    &.center {
      text-align: center;
      padding: 0 32px;
    }

    span {
      color: rgba(0, 0, 0, .45);
      display: inline-block;
      font-size: .95rem;
      line-height: 42px;
      margin-bottom: 4px;
    }
    p {
      line-height: 42px;
      margin: 0;
      a {
        font-weight: 600;
        font-size: 1rem;
      }
    }
  }
</style>