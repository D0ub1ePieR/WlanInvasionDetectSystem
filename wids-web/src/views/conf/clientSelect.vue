<template>
  <div class="device-select">
    <el-table
      ref="multipleTable"
      :data="clientList"
      border
      tooltip-effect="dark"
      style="width: 100%"
      @selection-change="handleSelectionChange">
      >
      <el-table-column
        type="selection"
        width="55">
      </el-table-column>
      <el-table-column prop="signal" label="信号强度" width="120" sortable></el-table-column>
      <el-table-column prop="wifiSsid" label="接入点名称" width="150"></el-table-column>
      <el-table-column prop="wifiBssid" label="接入点MAC地址" width="160"></el-table-column>
      <el-table-column prop="securityStatus" label="安全状况" width="110"></el-table-column>
      <el-table-column prop="timeout" label="连接状态" width="110"></el-table-column>
      <el-table-column prop="isInIntranet" label="接入内网" width="100"></el-table-column>
      <el-table-column prop="isInWhiteList" label="在白名单" width="110"></el-table-column>
      <el-table-column prop="clientBssid" label="终端MAC地址" width="160"></el-table-column>
      <el-table-column prop="lastSeen" label="最近出现时间" width="170" sortable></el-table-column>
      <el-table-column prop="flow" label="流量" width="100"></el-table-column>
      <el-table-column
        fixed="right"
        label="备注名称	"
        width="100">
        <template scope="scope">
          <el-input v-model="clientList[scope.row.index].remark"></el-input>
        </template>
      </el-table-column>
      <el-table-column
        fixed="right"
        label="负责人"
        width="100">
        <template scope="scope">
          <el-input v-model="clientList[scope.row.index].manager"></el-input>
        </template>
      </el-table-column>
      <el-table-column
        fixed="right"
        label="联系方式"
        width="100">
        <template scope="scope">
          <el-input v-model="clientList[scope.row.index].contact"></el-input>
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
    <el-button @click="importConfirm">确定</el-button>
    <el-button @click="importCancel">取消</el-button>
  </div>
</template>

<script>

  import axios from 'axios'

  export default {
    data() {
      return {
        allPage: 1,
        clientList: [],
        remark: [],
        manager: [],
        contact: [],
      }
    },
    created() {
        var _this = this;
        axios.post(`http://${this.global.url.host}:${this.global.url.port}`,{
        version: this.global.url.version,
        params: {
            page: 1,
            online: this.global.status.clientOnline,
            type: this.global.status.clientType
        },
            token: this.global.url.token,
            id: this.global.url.id,
            method: 'clientDetection.getClientList'
        })
        .then(res => {
            this.clientList = res.data.result.clientList
            this.fliterData(this.clientList)
            for(let i = 0 ; i < this.clientList.length ; i++){
              this.clientList[i].index = i;
            }
            this.allPage = res.data.result.totalPage
        })
    },
    methods: {
      confirm(){
        this.$emit("confirm")
      },
      importConfirm(){
        var data = [];
        for(let i = 0 ; i < this.multipleSelection.length ; i++){
          data.push(this.multipleSelection[i]);
        }
        axios.post(`http://${this.global.url.host}:${this.global.url.port}`, {
          version: this.global.url.version,
          params: {
            clientInfoList: data
          },
          token: this.global.url.token,
          id: this.global.url.id,
          method: 'whiteList.addClientToWhiteList'
        })
        .then(res => {
          console.log(res)
          this.confirm();
        })
      },
      importCancel(){
        this.$emit("confirm")
      },
        handleCurrentChange(val){
        var _this = this;
        axios.post(`http://${this.global.url.host}:${this.global.url.port}`,{
            version: this.global.url.version,
            params: {
                page: val,
            },
            token: this.global.url.token,
            id: this.global.url.id,
            method: 'clientDetection.getClientList'
        })
        .then(res => {
            console.log(res)
            this.clientList = res.data.result.clientList
            this.fliterData(this.clientList)
        })
        },
      toggleSelection(rows) {
        if (rows) {
          rows.forEach(row => {
            this.$refs.multipleTable.toggleRowSelection(row);
          });
        } else {
          this.$refs.multipleTable.clearSelection();
        }
      },
      handleSelectionChange(val) {
        this.multipleSelection = val;
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
      }
    }
  }
</script>
