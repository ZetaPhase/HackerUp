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
          <div v-if="loading" class="t-center animated welcome-text fadeOutDown zoomOutDown cool-panel">
            <h1>Welcome to HackerUp</h1>
            <div class="logo">
              <img src="../assets/logo.png" width="140" height="140">
            </div>
            <div>
              <md-spinner md-indeterminate></md-spinner>
            </div>
            <div>
              <h3>HSHACKS III</h3>
              <h5>Nihal Talur</h5>
              <h5>Dave Ho</h5>
            </div>
          </div>
          <div v-else class="t-center discover-thing animated fadeInUp cool-panel">
            <discover></discover>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
  import Intro from '../components/Intro'
  import Toolbar from '../components/Toolbar'
  import Discover from '../components/Discover'

  import axios from 'axios'

  export default {
    name: 'dashboard',
    data: function () {
      return {
        showIntro: true,
        loading: true
      }
    },
    components: {
      Toolbar,
      Intro,
      Discover
    },
    methods: {
      sendPing: function () {
        // send ping post to server
        let vm = this
        axios.post('/a/k/ping?apikey=' + vm.$root.u.key, {
          latitude: 0,
          longitude: 0
        })
          .then((response) => {
            if (response.status !== 200) {
              // uh oh...
            } else if (response.status === 200) {
              // k.
              setTimeout(function () { this.sendPing() }.bind(this), 10000)
            }
          })
          .catch(function (err) {
            if (err) {
              // crap.
            }
          })
      }
    },
    mounted: function () {
      this.$root.plU()
      this.showIntro = false
      setTimeout(function () { this.loading = false }.bind(this), 4000)
      // set ping timeout
      setTimeout(function () { this.sendPing() }.bind(this), 3000)
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
