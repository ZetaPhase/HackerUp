<template>
  <div class="page-view">
    <div class="container">
      <div class="row intro-area">
        <div class="twelve columns">
        <!--
          <div class="introexit animated fadeOut">
            <intro></intro>
          </div>
          -->
        </div>
      </div>
      <div class="row">
        <div class="ten columns offset-by-one">
          <!--
            Stuff
          -->
          <div class="t-center animated welcome-text fadeOutDown zoomOutDown cool-panel">
            <h1>Welcome to HackerUp</h1>
            <div>
              <md-spinner md-indeterminate></md-spinner>
            </div>
            <div>
              <h3>HSHACKS III</h3>
              <h5>Nihal Talur</h5>
              <h5>Dave Ho</h5>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
  import Intro from '../components/Intro'
  import Toolbar from '../components/Toolbar'

  export default {
    name: 'dashboard',
    data: function () {
      return {
        showIntro: true
      }
    },
    components: {
      Toolbar,
      Intro
    },
    methods: {
    },
    mounted: function () {
      this.showIntro = false
    }
  }

</script>
<style>
  #dashboard {
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    color: #2c3e50;
  }

  .intro-area {
    margin-top: 5%;
  }

  #footer {
    margin: auto;
    text-align: center;
  }

  .welcome-text {
    animation-delay: 3s;
  }

  .cool-panel {
    margin-top: 5%;
    padding: 60px;
    background-color: #313131;
    box-shadow: 0 0 20px 20px #313131;
  }

  .introexit {
  }

</style>
