
import Landing from './layouts/Landing'
import AboutPlace from './layouts/AboutPlace'
import NotFound from './layouts/NotFound'
import Dashboard from './layouts/Dashboard'
import ProfileInfo from './layouts/ProfileInfo'

const main = [
  {
    path: '/',
    name: 'landing',
    component: Landing
  },
  {
    path: '/landing',
    redirect: '/'
  },
  {
    path: '/about',
    name: 'about',
    component: AboutPlace
  },
  {
    path: '/u',
    name: 'dashboard',
    component: Dashboard
  },
  {
    path: '/p/:id',
    name: 'view_profile',
    component: ProfileInfo
  }
]

const error = [
  {
    path: '*',
    name: 'error',
    component: NotFound
  }
]

export default [].concat(main, error)
