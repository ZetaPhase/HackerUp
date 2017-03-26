<template>
  <div class="discover">
  </div>
</template>

<script>
  import axios from 'axios'

  export default {
    name: 'discover',
    data () {
      return {
      }
    },
    methods: {
      refreshNearby: function () {
        console.log('refreshing nearby')
        let vm = this
        let rfU = '/a/k/nearby/150000?apikey=' + vm.$root.u.key
        console.log(rfU)
        axios.get(rfU)
          .then(function (response) {
            // todo
            console.log(response.data)
            setTimeout(vm.refreshNearby, 2000)
          })
      }
    },
    mounted: function () {
      let vm = this
      setTimeout(vm.refreshNearby, 500)
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
