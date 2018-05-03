<template>
  <div class="device-hotspot">
    <el-form ref="form" :model="form" label-width="80px" class="demo-form-inline" :inline="true">

        <el-form-item label="事件类型">
            <el-select v-model="form.type"   placeholder="选择">
              <el-option label="警报" value="0"></el-option>
              <el-option label="提醒" value="1"></el-option>
              <el-option label="弱加密方式提醒" value="2"></el-option>
              <el-option label="伪造A片警报" value="3"></el-option>
              <el-option label="未授权WIFI接入内网警报" value="4"></el-option>
              <el-option label="DOS攻击警报" value="5"></el-option>
              <el-option label="授权WIFI热点接入内网提醒" value="6"></el-option>
              <el-option label="授权终端链接未授权WIFI警报" value="7"></el-option>
              <el-option label="未授权终端接入WIFI警报" value="8"></el-option>
            </el-select>
        </el-form-item>

        <el-form-item label="事件内容">
            <el-input v-model="form.name"></el-input>
        </el-form-item>

        <el-form-item>
            <el-button @click="search">查询</el-button>
            <el-button @click="reset">重置</el-button>
        </el-form-item>
    </el-form>

    <el-table
      :data="eventList"
      height="500"
      border
      style="width: 100%"
      script="true">
      <el-table-column prop="eventType" label="事件类型" width="160"></el-table-column>
      <el-table-column prop="lastOccurTime" label="最近发生时间" width="180"></el-table-column>
      <el-table-column prop="firstOccurTime" label="事件最早发生时间" width="180"></el-table-column>
      <el-table-column prop="eventContent" label="事件内容" width="200"></el-table-column>
      <el-table-column prop="relatedNetworkName" label="相关网络名称" width="160"></el-table-column>
      <el-table-column prop="relatedNetworkAddress" label="相关网络地址" width="160"></el-table-column>
      <el-table-column prop="sourceInfo" label="源信息" width="160"></el-table-column>
      <el-table-column prop="targetInfo" label="目标信息" width="160"></el-table-column>
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
        type: '',
        name: ''
      },
      allPage: 1,
      eventList: []
    };
  },
  created() {
    axios.post(`http://${this.global.url.host}:${this.global.url.port}`,{
      version: this.global.url.version,
      params: {
        type: this.global.status.alertType,
        keyword:'',
        page: 1
      },
      id: this.global.url.id,
      token: this.global.url.token,
      method: 'eventDetection.getEventList'
    })
    .then(res => {
      if(res.data.result){
        console.log(res)
        this.eventList = res.data.result.eventList
        this.allPage = res.data.result.totalPage
      }else{
        console.log(res)
        this.$message(res.data.error.message)
      }
    })
  },
  methods: {
    handleCurrentChange(val){
      axios.post(`http://${this.global.url.host}:${this.global.url.port}`,{
        version: this.global.url.version,
        params: {
          page: val
        },
        id: this.global.url.id,
        token: this.global.url.token,
        method: 'eventDetection.getEventList'
      })
      .then(res => {
        if(res.data.result){
          console.log(res)
          this.eventList = res.data.result.eventList
          this.allPage = res.data.result.totalPage
        }else{
          console.log(res)
          this.$message(res.data.error.message)
        }
      })
    },
    search() {
      axios.post(`http://${this.global.url.host}:${this.global.url.port}`,{
        version: this.global.url.version,
        params: {
          type: this.form.type,
          keyword: this.form.name,
          page: 1
        },
        id: this.global.url.id,
        token: this.global.url.token,
        method: 'eventDetection.getEventList'
      })
      .then(res => {
        if(res.data.result){
          console.log(res)
          this.eventList = res.data.result.eventList
        }else{
          console.log(res)
          this.$message(res.data.error.message)
        }
      })
    },
    reset() {
      this.warningList = []
      this.form = {
        type: '',
        name: ''
      }
      this.$message("重置成功!");
    }
  }
};
</script>
