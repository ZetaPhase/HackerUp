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
          <h1 class="t-center">HackerUp</h1>
        </div>
      </div>
      <div class="row">
        <div class="ten columns offset-by-one">
          <!--
            Stuff
          -->
        </div>
      </div>
    </div>
  </div>
</template>
<script>
  import Intro from '../components/Intro'
  import Toolbar from '../components/Toolbar'

  // import axios from 'axios'

  export default {
    name: 'profileInfo',
    data: function () {
      return {
        showIntro: true,
        loading: true
      }
    },
    components: {
      Toolbar,
      Intro
    },
    methods: {

    },
    mounted: function () {
      this.$root.plU()
      let pId = this.$route.params.id
      console.log(pId)
    }
  }

</script>
<style>
  #dashboard {
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    /*color: #2c3e50;*/
  }

  .intro-area {
    margin-top: 5%;
  }

  #footer {
    margin: auto;
    text-align: center;
  }

  .welcome-text,
  .discover-thing {
    animation-delay: 3s;
  }

  .logo {
    padding: 40px;
  }

  .cool-panel {
    margin-top: 5%;
    padding: 60px;
    /*background-color: #262626;*/
    /*box-shadow: 0 0 20px 20px #262626;*/
  }

  .introexit {
  }

</style>
