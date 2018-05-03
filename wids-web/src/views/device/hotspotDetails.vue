<template>
  <div class="device-hotspot">
    <div class="mask" v-show="isAdd"></div>
    <p>热点名称: <span>{{wifiName}}</span></p>
    <el-button @click="pasCrack">弱密码破解</el-button>
    <el-button @click="addWhitelist" :disabled="isInWhiteList">加入白名单</el-button>
    <el-tabs v-model="activeName">
      <el-tab-pane label="热点详情信息" name="first">
        <el-table
          :data="wifiDetail"
          border
          style="width: 100%"
          script="true"
        >
          <el-table-column prop="ssid" label="网络名称" width="93"></el-table-column>
          <el-table-column prop="remark" label="备注名称" width="93"></el-table-column>
          <el-table-column prop="signal" label="信号强度" width="93"></el-table-column>
          <el-table-column prop="ssid" label="SSID" width="93"></el-table-column>
          <el-table-column prop="securityStatus" label="安全状况" width="108"></el-table-column>
          <el-table-column prop="encryption" label="加密方式" width="93"></el-table-column>
          <el-table-column prop="isInIntranet" label="接入内网" width="93"></el-table-column>
          <el-table-column prop="isInWhiteList" label="白名单" width="108"></el-table-column>
          <el-table-column prop="timeout" label="超时情况" width="93"></el-table-column>
          <el-table-column prop="timeoutTime" label="超时时间" width="93"></el-table-column>
          <el-table-column prop="connectionsNum" label="连接数"></el-table-column>
          <el-table-column prop="appearTimes" label="出现次数" width="93"></el-table-column>
          <el-table-column prop="firstSeen" label="首次出现" width="112"></el-table-column>
          <el-table-column prop="lastSeen" label="最新出现" width="112"></el-table-column>
          <el-table-column prop="channel" label="无限信道" width="93"></el-table-column>
          <el-table-column prop="bssid" label="热点MAC地址" width="160"></el-table-column>
          <el-table-column prop="company" label="设备厂商" width="155"></el-table-column>
          <el-table-column prop="flow" label="流量"></el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="连接该热点的终端列表" name="second">
        <el-table
          :data="wifiClientList"
          border
          style="width: 100%;"
          script="true"
        >
          <el-table-column prop="clientSignal" label="信号强度" width="150"></el-table-column>
          <el-table-column prop="clientRemark" label="备注名称"></el-table-column>
          <el-table-column prop="isInWhiteList" label="白名单" width="150"></el-table-column>
          <el-table-column prop="securityStatus" label="安全状况"></el-table-column>
          <el-table-column prop="lastSeen" label="最后出现时间" width="150"></el-table-column>
          <el-table-column prop="clientBssid" label="终端MAC地址"></el-table-column>
          <el-table-column prop="timeout" label="当前状态"></el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="信号强度曲线图" name="third">
        <div id="chart" :style="{width:'600px',height:'400px'}"></div>
      </el-tab-pane>
      <el-tab-pane label="事件列表" name="fourth">
        <el-table
          :data="wifiEventList"
          border
          style="width: 100%"
          script="true"
        >
          <el-table-column prop="eventType" label="事件类型"></el-table-column>
          <el-table-column prop="lastOccurTime" label="最近发生时间"></el-table-column>
          <el-table-column prop="firstOccurTime" label="最早发生时间"></el-table-column>
          <el-table-column prop="eventContent" label="事件内容" width="100"></el-table-column>
          <el-table-column prop="relatedNetworkName" label="相关网络名称" width="160"></el-table-column>
          <el-table-column prop="relatedNetworkAddress" label="相关网络地址" width="160"></el-table-column>
          <el-table-column prop="sourceInfo" label="源信息"></el-table-column>
          <el-table-column prop="targetInfo" label="目标信息"></el-table-column>
        </el-table>
      </el-tab-pane>
    </el-tabs>
    <div class="form" v-show="isAdd">
      <el-form ref="form" :model="addList" label-width="120px">
        <el-form-item label="硬件地址">
          <el-input v-model="addList.bssid" disabled></el-input>
        </el-form-item>
        <el-form-item label="网络名称">
          <el-input v-model="addList.ssid" disabled></el-input>
        </el-form-item>
        <el-form-item label="备注名称">
          <el-input v-model="addList.remark"></el-input>
        </el-form-item>
        <el-form-item label="常用位置">
          <el-input v-model="addList.location"></el-input>
        </el-form-item>
        <el-form-item label="负责人">
          <el-input v-model="addList.manager"></el-input>
        </el-form-item>
        <el-form-item label="联系方式">
          <el-input v-model="addList.contact"></el-input>
        </el-form-item>
        <el-button @click="addPost">确定</el-button>
        <el-button @click="formCancel">取消</el-button>
      </el-form>
    </div>
  </div>
</template>

<script>
import echarts from "echarts"
import axios from 'axios'

export default {
  data() {
    return {
      wifiName: '',
      wifiDetail: [],
      wifiClientList: [],
      wifiEventList: [],
      wifiSignals: [],
      xdata: [],
      sdata: [],
      addList: {
        bssid: '',
        ssid: '',
        remark: '',
        location: '',
        manager: '',
        contact: ''
      },
      isAdd: false,
      isInWhiteList: false,
      activeName: 'first'
    }
  },
  created() {
    var _this = this;
    //获取热点详情
    axios.post(`http://${this.global.url.host}:${this.global.url.port}`,{
      version: this.global.url.version,
      params: {
        wifiBssid: this.$route.params.bssid
      },
      token: this.global.url.token,
      id: this.global.url.id,
      method: 'wifiDetection.getWifiDetail'
    })
    .then((res) => {
      //console.log(this);
      _this.isInWhiteList = res.data.result.wifiDetail.isInWhiteList === 0 ? false : true
      this.wifiDetail.push(res.data.result.wifiDetail)
      this.fliterData(this.wifiDetail)
      this.addList.bssid = res.data.result.wifiDetail.bssid
      this.addList.ssid = res.data.result.wifiDetail.ssid
      this.wifiName = res.data.result.wifiDetail.ssid
    })
    //获取连接道该热点的终端列表
    axios.post(`http://${this.global.url.host}:${this.global.url.port}`,{
      version: this.global.url.version,
      params: {
        wifiBssid: this.$route.params.bssid,
        page: 1
      },
      token: this.global.url.token,
      id: this.global.url.id,
      method: 'wifiDetection.getWifiClientList'
    })
    .then(res => {
     // console.log(res)
      _this.wifiClientList = res.data.result.wifiClientList
      _this.fliterData(_this.wifiClientList)
    })
    //获取热点事件列表
    axios.post(`http://${this.global.url.host}:${this.global.url.port}`,{
      version: this.global.url.version,
      params: {
        wifiBssid: this.$route.params.bssid,
        page: 1,
        startTime: this.global.wifi.startTime,
        endTime: this.global.wifi.endTime
      },
      token: this.global.url.token,
      id: this.global.url.id,
      method: 'eventDetection.getEventList'
    })
    .then(res => {
    //  console.log(res)
      _this.wifiEventList = res.data.result.wifiEventList
    })
  },
  methods: {
    pasCrack() {
      this.$message('正在破解!')
    },
    formCancel() {
      this.isAdd = false;
      this.$message('取消添加')
    },
    addWhitelist() {
      this.isAdd = true
    },
    addPost() {
      axios.post(`http://${this.global.url.host}:${this.global.url.port}`,{
        version: this.global.url.version,
        params: {
          wifiInfoList: [this.addList]
        },
        token: this.global.url.token,
        id: this.global.url.id,
        method: 'whiteList.addWifiToWhiteList'
      })
      .then(res => {
        if(res.data.result.resultList[0].singleCode == 0){
          this.$message('添加成功')
          window.location.reload();
        }else{
          this.$message(res.data.result.resultList[0].singleMessage)
        }
        this.isAdd = false
      })
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
  },
  mounted (){
    //获取wifi信号强度
    var _this = this
    axios.post(`http://${this.global.url.host}:${this.global.url.port}`,{
      version: this.global.url.version,
      params: {
        wifiBssid: this.$route.params.bssid,
      },
      token: this.global.url.token,
      id: this.global.url.id,
      method: 'wifiDetection.getWifiSignals'
    })
    .then(res => {
    //  console.log(res)
      _this.wifiSignals = res.data.result.wifiSignals
      var myChart = echarts.init(document.getElementById('chart'));
      var xdata = [],sdata = [];
      for(var  i = 0 ; i < _this.wifiSignals.length ; i++){
        xdata.push(_this.wifiSignals[i].time)
        sdata.push(_this.wifiSignals[i].wifiSignal)
      }
      myChart.setOption({
        grid: {
        show:false,
        top:'15%',
        left: '3%',
        right: '7%',
        bottom: '5%',
        containLabel: true,
      },
      xAxis: {
        type: 'category',
        boundaryGap:false,
        data: xdata
      },
      yAxis: {
        type:'value',
        data: [1]
      },
      series: [
        {
          type:'line',
          data: sdata
        }
      ]
      });
    })
  }
}
</script>
<style scoped>
  .mask{
    position:absolute;
    top:0;
    bottom:0;
    left:0;
    right:0;
    min-height: 100vh;
    background:#000;
    opacity:0.7;
    z-index: 10;
  }
  .form{
    position: absolute;
    top:80%;
    left:50%;
    transform: translate(-50%,-50%);
    background: white;
    padding: 40px 40px 40px 10px;
    text-align: center;
    z-index: 11;
  }
</style>
