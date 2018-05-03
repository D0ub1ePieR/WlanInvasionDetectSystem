<template>
  <div class="device-hotspot">
    <p>报表列表</p>
    <el-table
      :data="reportList"
      height="250"
      border
      style="width: 100%"
    >
      <el-table-column prop="reportId" label="报表id" width="300"></el-table-column>
      <el-table-column prop="createTime" label="创建时间"></el-table-column>
      <el-table-column
        fixed="right"
        label="操作"
        width="120">
        <template scope="scope">
          <el-button @click="getReport(scope.row)" type="text" size="small">下载</el-button>
          <el-button @click="deleteItem(scope.row)" type="text" size="small">删除</el-button>
        </template>
      </el-table-column>
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
        reportList: [],
        allPage: 1,
      }
    },
    created() {
      this.getReportList()
    },
    methods: {
      getReportList() {
        //获取报表列表
        axios.post(`http://${this.global.url.host}:${this.global.url.port}`, {
          version: this.global.url.version,
          id: this.global.url.id,
          method: 'report.getReportList',
          params: {
            page: 1
          },
          token: this.global.url.token,
        })
          .then(res => {
            console.log(res)
            if (res.data.result) {
              this.reportList = res.data.result.reportList
              this.allPage = res.data.result.totalPage
            } else {
              this.$message(res.data.error.message)
            }
          })
      },
      handleCurrentChange(val) {
        axios.post(`http://${this.global.url.host}:${this.global.url.port}`, {
          version: this.global.url.version,
          id: this.global.url.id,
          params: {
            page: val
          },
          method: 'report.getReportList',
          token: this.global.url.token,
        })
          .then(res => {
            console.log(res)
            if (res.data.result) {
              this.reportList = res.data.result.reportList
            } else {
              this.$message(res.data.error.message)
            }
          })
      },
      getReport(row) {
        axios.post(`http://${this.global.url.host}:${this.global.url.port}`, {
          version: this.global.url.version,
          params: {
            reportId: row.reportId
          },
          token: this.global.url.token,
          id: 1,
          method: 'report.getReportDetail'
        })
          .then(res => {
            console.log(res)
            if (res.data.result) {
              this.reportDetail = res.data.result.reportDetail
              // console.log(JSON.parse(this.reportDetail))
              this.setCatalog()
              this.$message('创建成功')
            } else {
              this.$message(res.data.error.message)
            }
          })
      },
      setCatalog() {
        var aTag = document.createElement('a');

        if (this.reportDetail.wifiList) {
          aTag.download = 'wifiList.txt';
          var blob = new Blob([this.jsonStringWifiList(this.reportDetail.wifiList)]);
          aTag.href = URL.createObjectURL(blob);
          aTag.click();
          URL.revokeObjectURL(blob);
        }
        if (this.reportDetail.clientList) {
          aTag.download = 'clientList.txt';
          var blob = new Blob([this.jsonStringClientList(this.reportDetail.clientList)]);
          aTag.href = URL.createObjectURL(blob);
          aTag.click();
          URL.revokeObjectURL(blob);
        }
        if (this.reportDetail.eventList) {
          aTag.download = 'eventList.txt';
          var blob = new Blob([this.jsonStringEventList(this.reportDetail.eventList)]);
          aTag.href = URL.createObjectURL(blob);
          aTag.click();
          URL.revokeObjectURL(blob);
        }
        if (this.reportDetail.wifiWhiteList) {
          aTag.download = 'wifiWhiteList.txt';
          var blob = new Blob([this.jsonStringWifiWhiteList(this.reportDetail.wifiWhiteList)]);
          aTag.href = URL.createObjectURL(blob);
          aTag.click();
          URL.revokeObjectURL(blob);
        }
        if (this.reportDetail.clientWhiteList) {
          aTag.download = 'clientWhiteList.txt';
          var blob = new Blob([this.jsonStringClientWhiteList(this.reportDetail.clientWhiteList)]);
          aTag.href = URL.createObjectURL(blob);
          aTag.click();
          URL.revokeObjectURL(blob);
        }
      },
      jsonStringClientWhiteList(data) {
        var str = "硬件地址 联系方式 负责人 备注名称 超时情况\n";
        data = eval('(' + data + ')');
        this.fliterData(data);
        for (let i = 0; i < data.length; i++) {
          str += `${data[i].clientBssid} ${data[i].contact} ${data[i].manager} ${data[i].remark} ${data[i].timeout}\n`
        }
        return str;
      },
      jsonStringEventList(data) {
        var str = "事件内容 事件类型 最早发生时间 最近发生时间 相关网络地址 相关网络名称 源信息 目标信息\n";
        data = eval('(' + data + ')');
        this.fliterData(data);
        for (let i = 0; i < data.length; i++) {
          str += `${data[i].eventContent} ${data[i].eventType} ${data[i].firstOccurTime} ${data[i].lastOccurTime} ${data[i].relatedNetworkAddress} ${data[i].relatedNetworkName} ${data[i].sourceInfo} ${data[i].targetInfo}\n`
        }
        return str;
      },
      jsonStringWifiList(data) {
        var str = "信号强度 网络名称 安全状况 加密方式 接入内网 在白名单 超时情况 最新出现 连接数量 热点mac地址 流量\n";
        data = eval('(' + data + ')');
        this.fliterData(data);
        for (let i = 0; i < data.length; i++) {
          str += `${data[i].signal} ${data[i].ssid} ${data[i].securityStatus} ${data[i].encryption} ${data[i].isInIntranet} ${data[i].isInWhiteList} ${data[i].timeout} ${data[i].lastSeen} ${data[i].connectionsNum} ${data[i].bssid} ${data[i].flow}\n`
        }
        return str;
      },
      jsonStringClientList(data) {
        var str = "信号强度 接入点名称 接入点MAC地址 安全状况 加密方式 接入内网 在白名单 超时情况 最新出现 终端mac地址 流量\n";
        data = eval('(' + data + ')');
        this.fliterData(data);
        for (let i = 0; i < data.length; i++) {
          str += `${data[i].signal} ${data[i].wifiSsid} ${data[i].wifiBssid} ${data[i].securityStatus} ${data[i].encryption} ${data[i].isInIntranet} ${data[i].isInWhiteList} ${data[i].timeout} ${data[i].lastSeen} ${data[i].clientBssid} ${data[i].flow}\n`
        }
        return str;
      },
      jsonStringWifiWhiteList(data) {
        var str = "硬件地址 网络名称 超时情况 备注名称 常用位置 负责人 联系方式\n";
        data = eval('(' + data + ')');
        this.fliterData(data);
        console.log(data)
        for (let i = 0; i < data.length; i++) {
          str += `${data[i].wifiBssid} ${data[i].wifiSsid} ${data[i].timeout} ${data[i].remark} ${data[i].location} ${data[i].manager} ${data[i].contact}\n`
        }
        return str;
      },
      fliterData(data) {
        data.forEach(item => {
          if (item.securityStatus == 0)
            item.securityStatus = '不安全'
          if (item.securityStatus == 1)
            item.securityStatus = '安全'
          if (item.securityStatus == 2)
            item.securityStatus = '外部可忽略'
          if (item.isInIntranet == 0)
            item.isInIntranet = '未接入'
          if (item.isInIntranet == 1)
            item.isInIntranet = '接入'
          if (item.isInWhiteList == 0)
            item.isInWhiteList = '不在白名单'
          if (item.isInWhiteList == 1)
            item.isInWhiteList = '在白名单'
          if (item.timeout == 0)
            item.timeout = '未超时'
          if (item.timeout == 1)
            item.timeout = '超时'
        })
      },
      deleteItem(row) {
        this.$confirm('该操作会删除报表, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          axios.post(`http://${this.global.url.host}:${this.global.url.port}`, {
            version: this.global.url.version,
            params: {
              reportId: row.reportId,
            },
            method: 'report.deleteReport',
            token: this.global.url.token,
            id: 1,
          })
            .then(res => {
              console.log(res)
              if (!res.data.error) {
                this.$message('删除成功')
                this.getReportList()
              } else {
                this.$message(res.data.error.message)
              }
            })
          this.$message({
            type: 'success',
            message: '删除成功!'
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      }
    }
  }
</script>


<style>
  .transfer {
    display: block;
    width: 80%;
    margin: 0 auto;
    /* text-align: center; */
  }

  .el-transfer-panel {
    margin-top: 50px;
    width: 300px !important;
  }
</style>

