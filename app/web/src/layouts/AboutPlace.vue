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

        </div>
      </div>
    </div>
  </div>
</template>
<script>
  import Intro from '../components/Intro'

  export default {
    data: function () {
      return {}
    },
    components: {
      Intro
    }
  }

</script>
<style scoped>


</style>
