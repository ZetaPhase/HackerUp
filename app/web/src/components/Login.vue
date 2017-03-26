<template>
  <div class="login">
    <div class="has-ripple">
      <form v-on:submit.prevent="tryLogin">
        <md-input-container>
          <label>Full Name</label>
          <md-input v-model="login.username"></md-input>
        </md-input-container>
        <md-input-container>
          <label>Hangouts Email</label>
          <md-input v-model="login.email"></md-input>
        </md-input-container>
        <md-input-container md-has-password>
          <label>GitHub Access Token</label>
          <md-input type="password" v-model="login.token"></md-input>
        </md-input-container>
        <a href="https://github.com/settings/tokens/new" target="_blank">Need an access token?</a>
        <p class="error-message">{{ login.err }}</p>
        <input type="submit" class="invisible"></input>
        <md-button class="md-raised md-primary" @click.native="tryLogin" :disabled="!login.e">Log In</md-button>
      </form>
    </div>
  </div>
</template>

<script>
  import axios from 'axios'
  // let axiosRequestConfig = {
  //   validateStatus: function (status) {
  //     return status >= 200 && status < 500
  //   }
  // }

  export default {
    name: 'login',
    data () {
      return {
        login: {
          username: '',
          email: '',
          token: '',
          err: '',
          e: true // enabled
        }
      }
    },
    methods: {
      tryLogin: function () {
        // nothing
        let vm = this
        if (!vm.login.e) return
        if (!vm.login.token || !vm.login.username || !vm.login.email) {
          vm.login.err = 'credentials cannot be empty'
          return
        }
        vm.login.e = false
        // reset error message
        vm.login.err = ''
        // send login post
        axios.post('/a/register', {
          fullname: vm.login.username,
          hangoutsemail: vm.login.email,
          ghauthtoken: vm.login.token
        })
          .then((response) => {
            // TODO: process response
            if (response.status === 200) {
              // succeeded
              // vm.$root.loggedIn = true
              // push user info
              vm.$root.u.key = response.data.ApiKey
              vm.$root.u.name = vm.login.username
              vm.$root.u.email = vm.login.email
              vm.$root.u.tkn = vm.login.token
              vm.$root.u.li = true
              vm.$root.sU()
              // console.log(vm.$root.u.name)
              vm.$router.push('/u')
              vm.login.err = ''
            } else if (response.status === 401) {
              // unauthorized
              vm.login.err = response.data
            }
            vm.login.e = true
          })
          .catch(function (error) {
            // TODO: handle error
            if (error) {
              console.log(error)
              vm.login.err = 'invalid login credentials'
            }
            vm.login.e = true
          })
      }
    },
    mounted: function () {
      // prefill
      this.$root.plU()
      if (this.$root.u.name) {
        this.login.username = this.$root.u.name
        this.login.email = this.$root.u.email
        this.login.token = this.$root.u.tkn
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .login {
    text-align: center;
    margin-top: 5%;
  }

  h1,
  h2 {
    font-weight: normal;
  }

  ul {
    list-style-type: none;
    padding: 0;
  }

  li {
    display: inline-block;
    margin: 0 10px;
  }

  a {
    color: #42b983;
  }
</style>
