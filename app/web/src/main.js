import Vue from 'vue'
// import App from './App'
import VueRouter from 'vue-router'

// Routes
import routes from './routes'

// Styles
// import '../static/assets/styles/global.css'
// import '../static/assets/styles/material-icons.css'
// import './assets/styles/normalize.css'
// import './assets/styles/ffskeleton.css'

// UI
import VueMaterial from 'vue-material'
import 'vue-material/dist/vue-material.css'

// Register plugins
Vue.use(VueRouter)
Vue.use(VueMaterial)

let router = new VueRouter({
  routes
})

router.afterEach((currentRoute) => {
  let mainContent = document.querySelector('.main-content')

  if (mainContent) {
    mainContent.scrollTop = 0
  }
})

Vue.material.registerTheme({
  muted: {
    primary: {
      color: 'grey',
      hue: 300
    },
    accent: 'indigo'
  },
  apptheme: {
    primary: 'red',
    accent: 'deep-purple'
  }
})
Vue.material.setCurrentTheme('apptheme')

import App from './App'

let IridiumIonSite = Vue.component('app', App)

/* eslint-disable no-unused-vars */
const app = new IridiumIonSite({
  el: '#app',
  router
})
