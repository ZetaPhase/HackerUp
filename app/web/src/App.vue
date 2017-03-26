<template>
  <div class="content-container">
    <toolbar></toolbar>
    <transition name="md-router">
      <router-view></router-view>
    </transition>
  </div>
</template>
<script>
  import Toolbar from './components/Toolbar'

  export default {
    data: function () {
      return {
        u: {
          name: '',
          key: '',
          email: '',
          tkn: '',
          li: false
        }
      }
    },
    components: {
      Toolbar
    },
    methods: {
      nukeU: function () {
        this.u.name = ''
        this.u.key = ''
        this.u.email = ''
        this.u.tkn = ''
        this.u.li = false
        this.sU()
      },
      plU: function () {
        this.u.name = window.localStorage.getItem('u.name')
        this.u.key = window.localStorage.getItem('u.key')
        this.u.email = window.localStorage.getItem('u.email')
        this.u.tkn = window.localStorage.getItem('u.tkn')
      },
      sU: function () {
        window.localStorage.setItem('u.name', this.u.name)
        window.localStorage.setItem('u.key', this.u.key)
        window.localStorage.setItem('u.email', this.u.email)
        window.localStorage.setItem('u.tkn', this.u.tkn)
      }
    }
  }

</script>
<style lang="scss">
  html,
  body {
    height: 100%;
  }

  body {
    // display: flex;
  }

  .content-container {
    // min-height: 100%;
    // display: flex;
    // flex-flow: column nowrap;
    // flex: 1;
    transition: all 0.4s cubic-bezier(0.25, 0.8, 0.25, 1);
  }

  // @media (min-width: 1280px) {
  //   .content-container {
  //     padding-left: 280px;
  //   }
  // }

  .page-content {
    overflow: auto;
    position: relative;
    z-index: 1;
  }

  .space-top {
    margin-top: 5%;
  }

  .surround {
    margin: 2%;
  }

  img.center-horiz {
    margin-left: auto;
    margin-right: auto;
    display: block;
  }

  .page-view {
    padding: 16px;
    flex: 1;
    /*overflow: auto;*/
  }

  @import './stylesheets/md-variables.scss';
  $sizebar-size: 280px;

  .page-view {
    /*transform: translate3D(0, 0, 0);
    transition: $swift-ease-out;
    transition-delay: .2s;*/
  }

  .md-router-enter-active,
  .md-router-leave-active {
    transition: all 0.9s cubic-bezier(0.25, 0.8, 0.25, 1);
  }

  .md-router-enter-active {
    .page-view {
      opacity: 0;
      overflow: hidden;
    }
  }

  .md-router-enter-active {
    z-index: 2;
    transition: $swift-ease-out;
    .page-view {
      /*transform: translate3D(0, 7%, 0);*/
    }
  }

  @media (min-width: 1280px) {
    .md-router-enter-active,
    .md-router-leave-active {
      left: 280px;
    }
  }

  .md-router-enter,
  .md-router-leave-active {
    /*transform: translateY(20px);*/
    transform: translate3d(2%, 9%, 10px);
    opacity: 0;
  }

  .md-router-enter {
    /*opacity: 1;*/
    /*opacity: 0;*/
  }

  .md-router-leave {}

  .md-router-leave-active {
    /*opacity: 0;*/
    /*opacity: 1;*/
  }

  /* manual theme override!!! */
  body.md-theme-hudark,
  .md-theme-hudark.md-sidenav .md-sidenav-content,
  .md-theme-hudark.md-list,
  .md-theme-hudark.md-list .md-list-item-expand .md-list-item-container,
  .md-theme-hudark.md-input-container .md-icon:after {
    background-color: #212121 !important;
  }

  .md-input-container label {
    color: rgba(255, 255, 255, 0.4);
  }

  .md-input-container.md-has-value input, .md-input-container.md-has-value textarea {
    color: rgba(255, 255, 255, 0.66);
  }

  .invisible {
    display: none;
  }

  h1, h2, h3, h4, h5, h6 {
    color: rgba(255, 255, 255, .87);
  }

  .t-center {
    text-align: center;
  }

</style>
