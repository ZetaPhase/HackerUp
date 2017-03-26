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
          <div v-if="!loading">
            <h2>{{ p.name }}</h2>
            <h5>{{ p.bio }}</h5>
            <p>Home location: {{ p.homeLoc }}</p>
            <p>Works at <b>{{ p.company }}</b></p>
            <p>{{ p.name }} is <a target="_blank" :href="p.githubLink">{{ p.username }} </a> on GitHub.</p>
            <p>{{ p.name }} has <b>{{ p.repoCount }}</b> public repositories on GitHub.</p>
            <p>Chat with <b>{{ p.hangoutsEmail }}</b> on Google Hangouts</p>
          </div>
          <div class="t-center" v-else>
            <p>Loading Profile</p>
            <md-spinner md-indeterminate class="md-accent"></md-spinner>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
  import Intro from '../components/Intro'
  import Toolbar from '../components/Toolbar'

  import axios from 'axios'

  export default {
    name: 'profileInfo',
    data: function () {
      return {
        showIntro: true,
        loading: true,
        p: {
          name: '',
          company: '',
          bio: '',
          username: '',
          githubLink: '',
          homeLoc: '',
          repoCount: 0,
          hangoutsEmail: ''
        }
      }
    },
    components: {
      Toolbar,
      Intro
    },
    methods: {
      loadProfileInfo: function (id) {
        let vm = this
        axios.post('/a/k/profile/' + id + '?apikey=' + vm.$root.u.key, {})
          .then(function (response) {
            let uDt = response.data
            // console.log(uDt)
            vm.p.name = uDt.FullName
            vm.p.bio = uDt.GitHubBio
            vm.p.username = uDt.GitHubUsername
            vm.p.githubLink = 'https://github.com/' + vm.p.username
            vm.p.hangoutsEmail = uDt.HangoutsEmail
            vm.p.homeLoc = uDt.HomeLocation
            vm.p.company = uDt.Company
            vm.p.repoCount = uDt.RepoCount
            vm.loading = false
          })
      }
    },
    mounted: function () {
      this.$root.plU()
      let pId = this.$route.params.id
      // console.log(pId)
      this.loadProfileInfo(pId)
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
