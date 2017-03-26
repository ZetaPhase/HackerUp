<template>
  <div class="page-view">
    <div class="container">
      <div class="row" id="intro-area">
        <div class="twelve columns">
          <intro></intro>
        </div>
      </div>
      <div class="row">
        <div class="ten columns offset-by-one">
          <!--
            Stuff
          -->
          <login></login>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
  import Intro from '../components/Intro'
  import Toolbar from '../components/Toolbar'
  import Login from '../components/Login'

  export default {
    name: 'landing',
    components: {
      Toolbar,
      Intro,
      Login
    },
    methods: {
    }
  }

</script>
<style>
  #landing {
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    color: #2c3e50;
  }

  #intro-area {
    margin-top: 5%;
  }

  #footer {
    margin: auto;
    text-align: center;
  }

</style>
