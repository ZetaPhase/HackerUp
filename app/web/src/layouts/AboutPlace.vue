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
            HackerUp is an HsHacks III project built by Nihal Talur and Dave Ho.
          </p>
          <p>
            HackerUp lets developers meet each other through the app, allowing them
            to see others nearby to join their projects and to view their online portfolios.
            With the app, one can view the GitHub profiles of those around them to get a better idea
            of their interests for possibly forming a programming team. It provides live updates on nearby
            developers and their profiles.
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
