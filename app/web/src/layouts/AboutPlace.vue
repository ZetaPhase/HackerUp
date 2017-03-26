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
          <h3>About HackerUp</h3>
          <p>
            It's a good app.
          </p>
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
