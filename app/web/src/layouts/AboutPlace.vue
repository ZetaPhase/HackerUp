<template>
  <div class="page-view">
    <div class="about-pg">
      <div id="app" class="container">
        <div class="row" id="intro-area">
          <div class="twelve columns">
            <intro></intro>
          </div>
        </div>
        <div class="about-pg-content">
          <aboutWidget>
          </aboutwidget>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
  import Intro from '../components/Intro'
  import AboutWidget from '../components/widgets/aboutWidget'

  export default {
    data: function () {
      return {}
    },
    components: {
      Intro,
      AboutWidget
    }
  }

</script>
<style scoped>


</style>
