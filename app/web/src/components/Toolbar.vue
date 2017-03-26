<template>
  <div class="toolbar">
    <md-toolbar>
      <md-button class="md-icon-button" @click="toggleLeftSidenav">
        <md-icon>menu</md-icon>
      </md-button>
      <h2 class="md-title" style="flex: 1">{{ appName }}</h2>
      <md-menu md-direction="bottom left">
        <md-button class="md-icon-button" md-menu-trigger>
          <md-icon>more_vert</md-icon>
        </md-button>
        <md-menu-content>
          <md-menu-item @click="visitIridiumIon">GitHub</md-menu-item>
          <md-divider></md-divider>
          <md-menu-item @click="visitGitHub">0xFireball</md-menu-item>
        </md-menu-content>
      </md-menu>
    </md-toolbar>
    <md-sidenav class="md-left md-fixed main-sidebar" ref="leftSidenav">
      <md-toolbar class="md-large">
        <div class="md-toolbar-container">
          <h2 class="md-title">{{ appName }}</h2>
        </div>
      </md-toolbar>
      <div class="toolbar-content">
        <div class="row">
          <div class="twelve columns">
          </div>
        </div>
        <div class="sidebar-links">
          <md-list class="md-dense">
            <md-list-item @click="closeSidenav">
              <router-link exact to="/">Home</router-link>
            </md-list-item>
            <md-list-item @click="closeSidenav">
              <router-link exact to="/projects">Projects</router-link>
            </md-list-item>
            <md-list-item @click="visitBlog">
              Blog
            </md-list-item>
            <md-list-item @click="closeSidenav">
              <router-link exact to="/about">About</router-link>
            </md-list-item>
            <md-list-item>
              <span>More</span>
              <md-list-expand>
                <md-list>
                  <md-list-item class="md-inset" @click="visitIridiumIon">
                    GitHub
                  </md-list-item>
                  <md-list-item class="md-inset" @click="visitFireball">
                    0xFireball
                  </md-list-item>
                </md-list>
              </md-list-expand>
            </md-list-item>
          </md-list>
        </div>
      </div>
    </md-sidenav>
  </div>
</template>
<script>
  export default {
    name: 'toolbar',
    data: function () {
      return {
        appName: 'IridiumIon Software'
      }
    },
    methods: {
      visitGitHub: function () {
        window.open('https://github.com/0xFireball/')
      },
      visitIridiumIon: function () {
        window.open('https://github.com/IridiumIon/')
      },
      visitFireball: function () {
        window.open('https://0xfireball.me/')
      },
      visitBlog: function () {
        window.open('https://blog.iridiumion.xyz')
      },
      toggleLeftSidenav: function () {
        this.$refs.leftSidenav.toggle()
      },
      closeSidenav: function () {
        this.$refs.leftSidenav.close()
      }
    }
  }

</script>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .toolbar-content {}

</style>
