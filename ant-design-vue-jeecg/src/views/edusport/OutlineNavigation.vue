<template>
  <a-card :bordered="false" style="width: 160%;text-align: center;margin-left:-25%">
    <a-steps class="steps" :current="currentTab">
      <a-step title="大纲" />
      <a-step title="教练" />
      <a-step title="成绩" />
    </a-steps>
    <div class="content">
      <step1 v-if="currentTab === 0" @nextStep="nextStep"  />
      <step2 v-if="currentTab === 1" @nextStep="nextStep" @prevStep="prevStep" :userList="userList"/>
      <step3 v-if="currentTab === 2" @prevStep="prevStep" @finish="finish" :userList="userList"/>
    </div>
  </a-card>
</template>

<script>
  import Step1 from './modules/AthleteSelectionTestModal'
  import Step2 from './OutlineCoachList'
  import Step3 from './AthleteSelectionAthleteScoreList'
  export default {
    name: "OutlineNavigation",
    components: {
      Step1,
      Step2,
      Step3
    },
    data () {
      return {
        description: '大纲导航',
        currentTab: 0,
        userList:{},
        // form
        form: null,
      }
    },
    methods: {

      // handler
      nextStep (data) {
        this.userList=data;
        if (this.currentTab < 4) {
          this.currentTab += 1
        }
      },
      prevStep (data) {
        this.userList=data;
        if (this.currentTab > 0) {
          this.currentTab -= 1
        }
      },
      finish () {
        this.currentTab = 0
      }
    }
  }
</script>

<style lang="scss" scoped>
  .steps {
    max-width: 750px;
    margin: 16px auto;
  }
</style>