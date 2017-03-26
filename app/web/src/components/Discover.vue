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
        nearby: []
      }
    },
    methods: {
      refreshNearby: function () {
        console.log('refreshing nearby')
        let vm = this
        let rfU = '/a/k/nearby/150000?apikey=' + vm.$root.u.key
        // console.log(rfU)
        axios.post(rfU, {})
          .then(function (response) {
            // todo
            // console.log(response.data)
            let dataA = response.data
            vm.nearby.splice(0, vm.nearby.length)
            for (var i = 0, len = dataA.length; i < len; i++) {
              vm.nearby.push(dataA[i])
            }
            console.log(vm.nearby)
            setTimeout(vm.refreshNearby, 2000)
          })
      }
    },
    mounted: function () {
      let vm = this
      vm.refreshNearby()
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
