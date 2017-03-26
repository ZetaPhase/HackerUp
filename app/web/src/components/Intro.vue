<template>
  <div class="intro">
    <div class="has-ripple">
      <img src="../assets/logo.png" width="240" height="240">
      <h1 class="main-title-text">{{ title }}</h1>
      <h3 class="main-slogan-text">{{ motto }}</h3>
    </div>
  </div>
</template>

<script>
export default {
  name: 'intro',
  data () {
    return {
      title: 'IridiumIon Software',
      motto: 'Random awesome software!'
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .intro {
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

  h5.company-brand {
    letter-spacing: 0.12rem;
    font-weight: 300;
    text-transform: uppercase;
  }

  h1.main-title-text {
    margin-top: 5%;
  }

  h3.main-slogan-text {
    font-size: 3rem;
  }
</style>
