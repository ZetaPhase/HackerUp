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
      return {}
    },
    components: {
      Toolbar
    }
  }

</script>
<style lang="scss">
  html,
  body {
    height: 100%;
  }
  
  body {
    display: flex;
  }
  
  .content-container {
    min-height: 100%;
    display: flex;
    flex-flow: column nowrap;
    flex: 1;
    transition: all 0.4s cubic-bezier(0.25, 0.8, 0.25, 1);
  }
  
  @media (min-width: 1280px) {
    .content-container {
      padding-left: 280px;
    }
  }
  
  .page-content {
    display: flex;
    flex-flow: column;
    flex: 1;
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
  
  .page-content-wrapper {
    display: flex;
    flex-flow: column;
    flex: 1;
  }
  
  .page-view {
    padding: 16px;
    flex: 1;
    /*overflow: auto;*/
  }
  
  .main-sidebar.md-sidenav .md-sidenav-content {
    width: 280px;
    display: flex;
    flex-flow: column;
  }
  
  @media (min-width: 1280px) {
    .main-sidebar.md-sidenav .md-sidenav-content {
      top: 0;
      pointer-events: auto;
      transform: translate3d(0, 0, 0);
      box-shadow: 0 1px 5px rgba(0, 0, 0, 0.2), 0 2px 2px rgba(0, 0, 0, 0.14), 0 3px 1px -2px rgba(0, 0, 0, 0.12);
    }
  }
  
  @media (min-width: 1280px) {
    .main-sidebar.md-sidenav .md-backdrop {
      opacity: 0;
      pointer-events: none;
    }
  }
  /*.md-router-enter-active,
  .md-router-leave-active {
    transition: all 0.4s cubic-bezier(0.25, 0.8, 0.25, 1);
  }
  
  @media (min-width: 1280px) {
    .md-router-enter-active,
    .md-router-leave-active {
      left: 280px;
    }
  }
  
  .md-router-enter,
  .md-router-leave-active {
    opacity: 0;
  }

  */
  
  @import './stylesheets/md-variables.scss';
  $sizebar-size: 280px;
  /*.md-router-enter-active,
  .md-router-leave-active {
    position: absolute;
    top: 0;
    right: 0;
    left: 0;
    @media (min-width: 1281px) {
      left: $sizebar-size;
    }
    .page-view {
      opacity: 0;
      overflow: hidden;
    }
  }
  .md-router-leave-active {
    z-index: 1;
    transition: $swift-ease-in;
    transition-duration: .25s;
  }
  .md-router-enter-active {
    z-index: 2;
    transition: $swift-ease-out;
    .page-view {
      transform: translate3D(0, 7%, 0);
    }
  }*/
  /*.page-view {
    padding: 16px;
    flex: 1;
    overflow: auto;
    background-color: #fff;
    transform: translate3D(0, 0, 0);
    transition: $swift-ease-out;
    transition-delay: .2s;
  }*/
  
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

</style>
