<template>
  <div class="discover">
    <md-list class="custom-list md-double-line">
      <md-subheader class="md-inset">Nearby Hackers</md-subheader>

      <md-list-item v-for="(ps, ix) in nearby" @click.native="openPerson(ix)">
        <md-icon class="md-primary">person</md-icon>
        <div class="md-list-text-container">
          <span> {{ ps.Name }}</span>
          <span> {{ Math.round(ps.Distance) }} m </span>
        </div>
        <!--<md-divider class="md-inset"></md-divider>-->
      </md-list-item>
    </md-list>
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
        let rfU = '/a/k/nearby/999999999999?apikey=' + vm.$root.u.key
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
      },
      openPerson: function (ix) {
        let pI = this.nearby[ix]
        // console.log(pI)
        this.$router.push('/p/' + pI.UserId)
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
