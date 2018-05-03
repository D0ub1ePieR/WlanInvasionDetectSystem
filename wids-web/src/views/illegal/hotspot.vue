<template>
  <div class="device-hotspot">
    <el-form ref="form" :model="form" :inline="true" label-width="120px" class="demo-form-inline">
      <el-form-item label="出现时间（起）">
        <el-date-picker
          v-model="form.firstSeenFrom"
          type="datetime"
          placeholder="选择日期"
          @change="getFromTime">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="MAC地址">
        <el-input v-model="form.macAddress" placeholder="FF:FF:FF:FF:FF:FF" ></el-input>
      </el-form-item>
      <el-form-item label="设备分类">
        <el-select disabled placeholder="非法" v-model="form.type">
          <el-option label="所有" value="0"></el-option>
          <el-option label="接入内网" value="1"></el-option>
          <el-option label="未接入内网" value="2"></el-option>
          <el-option label="合法" value="3"></el-option>
          <el-option label="非法" value="4"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="停止时间（止）">
        <el-date-picker
          v-model="form.firstSeenTo"
          type="datetime"
          placeholder="选择日期"
          @change="getFromTime">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="设备名称" size="small">
        <el-input v-model="form.name"></el-input>
      </el-form-item>
      <el-form-item label="是否在线">
        <el-select v-model="form.online" placeholder="选择">
          <el-option label="所有" value="0"></el-option>
          <el-option label="在线" value="1"></el-option>
          <el-option label="超时" value="2"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button @click="search">搜索</el-button>
        <el-button @click="reset">重置</el-button>
        <el-button @click="keyCrack">弱密钥破解</el-button>
        <!--<el-button @click="addWhitelist">加入白名单</el-button>-->
        <!--<el-button @click="removeWhitelist">移除白名单</el-button>-->
      </el-form-item>
    </el-form>

    <el-table
      :data="wifiList"
      height="500"
      border
      style="width: 100%"
      script="true"
      @row-click="toDetails"
      >
      <el-table-column prop="signal" label="信号强度" width="120" sortable></el-table-column>
      <el-table-column prop="ssid" label="SSID" width="150"></el-table-column>
      <el-table-column prop="securityStatus" label="安全状况" width="110"></el-table-column>
      <el-table-column prop="encryption" label="加密方式" width="100"></el-table-column>
      <el-table-column prop="isInIntranet" label="接入内网" width="110"></el-table-column>
      <el-table-column prop="isInWhiteList" label="白名单" width="110"></el-table-column>
      <el-table-column prop="timeout" label="超时情况" width="100"></el-table-column>
      <el-table-column prop="lastSeen" label="最近出现时间" width="170" sortable></el-table-column>
      <el-table-column prop="connectionsNum" label="连接数" width="80"></el-table-column>
      <el-table-column prop="bssid" label="MAC地址" width="160"></el-table-column>
      <el-table-column prop="flow" label="流量" width="100"></el-table-column>
    </el-table>
    <el-pagination
      :style="{textAlign: 'center',marginTop:'20px'}"
      background
      @current-change="handleCurrentChange"
      layout="prev, pager, next"
      :page-count="allPage">
    </el-pagination>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  data() {
    return {
      form: {
        online: '',
        name: '',
        type: '',
        macAdress: '',
        sfirstSeenFrom:'',
        firstSeenTo:''
      },
      allPage: 1,
      wifiList: []
    }
  },
  created() {
    var _this = this;
    axios.post(`http://${this.global.url.host}:${this.global.url.port}`,{
      version: this.global.url.version,
      params: {
        type: 4,
        page: 1,
      },
      token: this.global.url.token,
      id: this.global.url.id,
      method: 'wifiDetection.getWifiList'
    })
    .then(res => {
      console.log(res)
      _this.wifiList = res.data.result.wifiList
      this.fliterData(_this.wifiList)
      this.allPage = res.data.result.totalPage
    })
  },
  methods: {
    handleCurrentChange(val){
      var _this = this;
      axios.post(`http://${this.global.url.host}:${this.global.url.port}`,{
        version: this.global.url.version,
        params: {
          page: val,
        },
        token: this.global.url.token,
        id: this.global.url.id,
        method: 'wifiDetection.getWifiList'
      })
      .then(res => {
        console.log(res)
        _this.wifiList = res.data.result.wifiList
        this.fliterData(_this.wifiList)
      })
    },
    getFromTime(val) {
      this.form.firstSeenFrom = val
    },
    getToTime(val) {
      this.form.firstSeenTo = val
    },
    search() {
      var _this = this;
      axios.post(`http://${this.global.url.host}:${this.global.url.port}`,{
        version: this.global.url.version,
        params: {
          online: this.form.online,
          type: 4,
          keyword: this.form.name || this.form.macAddress,
          firstSeenFrom: '',
          firstSeenTo: '',
          page: 1,
        },
        token: this.global.url.token,
        id: this.global.url.id,
        method: 'wifiDetection.getWifiList'
      })
      .then(res => {
        console.log(res)
        _this.wifiList = res.data.result.wifiList
        this.fliterData(_this.wifiList)
      })
    },
    reset() {
      this.form = {
        online: '',
        type: '',
        name: '',
        address: '',
        firstSeenFrom:'',
        firstSeenTo:''
      },
      this.$message('reset!')
    },
    keyCrack() {
      this.$message('keyCrack!')
    },
    addWhitelist()
    {
      this.$message('addWhitelist!')
    },
    removeWhitelist(){
      this.$message('removeWhitelist!')
    },
    toDetails(row){
      this.global.wifi.wifiBssid = row.bssid
      this.$router.push(`/device/hotspotDetails/${row.bssid}`)
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
