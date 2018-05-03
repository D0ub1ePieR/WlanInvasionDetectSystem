import Vue from 'vue'
import Router from 'vue-router'
const _import = require('./_import_' + process.env.NODE_ENV)
// in development env not use Lazy Loading,because Lazy Loading too many pages will cause webpack hot update too slow.so only in production use Lazy Loading

/* layout */
import Layout from '../views/layout/Layout'

Vue.use(Router)

/**
* icon : the icon show in the sidebar
* hidden : if `hidden:true` will not show in the sidebar
* redirect : if `redirect:noredirect` will not redirct in the levelbar
* noDropdown : if `noDropdown:true` will not has submenu in the sidebar
* meta : `{ role: ['admin'] }`  will control the page role
**/
export const constantRouterMap = [

  { path: '/', redirect: '/dashboard', hidden: true },
  { path: '/login', component: _import('login/index'), hidden: true },
  { path: '/404', component: _import('404'), hidden: true },
  {
    path: '/dashboard',
    component: Layout,
    redirect: '/dashboard/index',
    icon: 'tubiao',
    noDropdown:true,
    children: [{ path: 'index', name: '网络安全数据统计', component: _import('dashboard/index') }]
  },

  {
    path: '/device',
    component: Layout,
    redirect: 'noredirect',
    name: '设备管理',
    icon: 'zujian',
    children: [
      { path: 'hotspot', name: 'WiFi热点', icon: 'zonghe', component: _import('device/hotspot') },
      { path: 'client', name: '无线终端', icon: 'zonghe', component: _import('device/client') },
      { path: 'hotspotDetails/:bssid', name: 'WiFi热点详情', icon: 'zonghe', component: _import('device/hotspotDetails'),hidden: true },
      { path: 'clientDetails/:clientBssid/:wifiBssid', name: '无线终端详情', icon: 'zonghe', component: _import('device/clientDetails'),hidden: true }
    ]
  },

  {
    path: '/illegal',
    component: Layout,
    redirect: 'noredirect',
    name: '非法设备',
    icon: 'tubiao',
    children: [
      { path: 'hotspot', name: 'WiFi热点', icon: 'zonghe', component: _import('illegal/hotspot') },
      { path: 'client', name: '无线终端', icon: 'zonghe', component: _import('illegal/client') }
    ]
  },

  {
    path: '/weakPas',
    component: Layout,
    redirect: 'noredirect',
    name: '弱密码检测',
    icon: 'tubiao',
    children: [{ path: 'maintenance', name: '弱密码维护', component: _import('password/maintenance')}]
  },

  {
    path: '/warning',
    component: Layout,
    redirect: 'noredirect',
    name: '警告提醒',
    icon: 'tubiao',
    children: [{ path: 'search', name: '警告提醒查询', component: _import('warn/search')}]
  },

  {
    path: '/conf',
    component: Layout,
    redirect: 'noredirect',
    name: '安全配置',
    icon: 'tubiao',
    children: [
      { path: 'hotwhitelist', name: '热点白名单配置', component: _import('conf/hotwhitelist')},
      { path: 'terwhitelist', name: '终端白名单配置', component: _import('conf/terwhitelist')},
      { path: 'report', name: '报表配置', component: _import('conf/report')},
      { path: 'reportList', name: '报表列表', component: _import('conf/reportList')},
    ]
  },

  { path: '*', redirect: '/404', hidden: true }
]

export default new Router({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})

