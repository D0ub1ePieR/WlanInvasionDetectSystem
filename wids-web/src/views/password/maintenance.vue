<template>
  <div class="device-hotspot">
    <el-form ref="form" :model="form" label-width="80px" class="demo-form-inline" :inline="true">

      <el-form-item label="设备名称">
        <el-input v-model="form.name" style="width: 200px"></el-input>
      </el-form-item>

      <el-form-item label="MAC地址">
        <el-input v-model="form.macAdress" placeholder="FF:FF:FF:FF:FF:FF" style="width: 200px"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button @click="search">查询</el-button>
        <el-button @click="reset">重置</el-button>
        <el-button @click="keyScan">弱密码扫描</el-button>
      </el-form-item>
    </el-form>

    <el-table
      :data="onlineWhiteListWifiList"
      height="500"
      border
      script="true">
      <el-table-column prop="wifiSignal" label="信号强度" width="93"></el-table-column>
      <el-table-column prop="wifiSsid" label="wifi热点名称" width="160"></el-table-column>
      <el-table-column prop="company" label="设备生产厂商"></el-table-column>
      <el-table-column prop="securityStatus" label="安全状况" width="100"></el-table-column>
      <el-table-column prop="wifiBssid" label="MAC地址" width="160"></el-table-column>
      <!-- <el-table-column prop="traffic" label="弱密码扫描进度"></el-table-column> -->
    </el-table>
    <el-pagination
      :style="{textAlign: 'center',marginTop:'20px'}"
      background
      layout="prev, pager, next"
      :page-count="allPage">
    </el-pagination>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      form: {
        name: '',
        macAdress: '',
      },
      allPage: 1,
      onlineWhiteListWifiList: []
    }
  },
  created() {
    axios.post(`http://${this.global.url.host}:${this.global.url.port}`,{
      version: this.global.url.version,
      params: {
        page: 1,
      },
      token: this.global.url.token,
      id: this.global.url.id,
      method: 'wifiDetection.getOnlineWhiteListWifiList'
    })
    .then(res => {
      console.log(res)
      this.onlineWhiteListWifiList = res.data.result.wifiList
      this.fliterData(this.onlineWhiteListWifiList)
      this.allPage = res.data.result.totalPage
    })
  },
  methods: {
    search() {
      this.$message('search!')
    },
    reset() {
      this.form = {
        name: '',
        macAdress: '',
      }
      this.$message('reset!')
    },
    keyScan() {
      this.$message('keyScan!')
    },
    fliterData(data){
      data.forEach(item => {
        if(item.securityStatus == 0)
          item.securityStatus = '不安全'
        if(item.securityStatus == 1)
          item.securityStatus = '安全'
        if(item.securityStatus == 2)
          item.securityStatus = '外部可忽略'
        if(item.isInIntranet == 0)
          item.isInIntranet = '未接入'
        if(item.isInIntranet == 1)
          item.isInIntranet = '接入'
        if(item.isInWhiteList == 0)
          item.isInWhiteList = '不在白名单'
        if(item.isInWhiteList == 1)
          item.isInWhiteList = '在白名单'
        if(item.timeout == 0)
          item.timeout = '未超时'
        if(item.timeout == 1)
          item.timeout = '超时'
      })
    }
  }
}
</script>
