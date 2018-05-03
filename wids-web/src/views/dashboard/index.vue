<template>
  <div class="dashboard-container">

    <el-button type="success" icon="check">当前状态评分：{{statusGrade}}分</el-button>
    <el-tag type="primary">无线安全检查结果统计如下：</el-tag>

    <el-row :gutter="40">
      <el-col style="width: 60%">
        <el-table
          :data="statisticsInfo"
          :show-header="false"
          stripe
          style="margin-top: 5px;">
          <el-table-column
            prop="title">
          </el-table-column>
          <el-table-column
            prop="number">
          </el-table-column>
          <el-table-column>
            <template scope="scope">
              <el-button @click="handleClick(scope.$index)" type="text" size="small">查看</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
      <el-col style="width: 30%">
        <el-row>
          <el-col>
            <pie-chart></pie-chart>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <bar-chart></bar-chart>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import axios from "axios";
  import pieChart from "./pieChart";
  import barChart from "./barChart";
  import ElCol from "element-ui/packages/col/src/col";
  import {getStatisticsInfo} from "../../api/wifiDetection";

  export default {
    name: "dashboard",
    components: {
      ElCol,
      pieChart,
      barChart
    },
    data() {
      return {
        statisticsInfo: [],
        statusGrade: 100
      };
    },
    methods: {
      handleClick(index) {
        console.log(index)
        switch (index) {
          case 0:
            this.global.status.alertType = 0;
            this.$router.push({name: '警告提醒查询', path: 'search'})
            break;
          case 1:
            this.global.status.wifiType = 4;
            this.$router.push({name: 'WiFi热点', path: 'hotspot'})
            break;
          case 3:
            this.global.status.wifiType = 0;
            this.$router.push({name: 'WiFi热点', path: 'hotspot'})
            break;
          case 4:
            this.global.status.wifiType = 1;
            this.$router.push({name: 'WiFi热点', path: 'hotspot'})
            break;
          case 5:
            this.global.status.wifiType = 2;
            this.$router.push({name: 'WiFi热点', path: 'hotspot'})
            break;
          case 6:
            this.global.status.wifiType = 3;
            this.$router.push({name: 'WiFi热点', path: 'hotspot'})
            break;
          case 7:
            this.$router.push({name: '热点白名单配置', path: 'hotwhitelist'})
            break;
          case 2:
            this.global.status.clientType = 4;
            this.$router.push({name: '无线终端', path: 'client'})
            break;
          case 8:
            this.global.status.clientType = 1;
            this.$router.push({name: '无线终端', path: 'client'})
            break;
          case 9:
            this.global.status.clientType = 2;
            this.$router.push({name: '无线终端', path: 'client'})
            break;
          case 10:
            this.global.status.clientType = 3;
            this.$router.push({name: '无线终端', path: 'client'})
            break;
          case 11:
            this.$router.push({name: '终端白名单配置', path: 'terwhitelist'})
            break;
          case 12:
            this.global.status.alertType = 1;
            this.$router.push({name: '警告提醒查询', path: 'search'})
            break;
          case 13:
            this.global.status.alertType = 2;
            this.$router.push({name: '警告提醒查询', path: 'search'})
            break;
          case 14:
            this.global.status.alertType = 6;
            this.$router.push({name: '警告提醒查询', path: 'search'})
            break;
          case 15:
            this.global.status.alertType = 4;
            this.$router.push({name: '警告提醒查询', path: 'search'})
            break;
          case 16:
            this.global.status.alertType = 5;
            this.$router.push({name: '警告提醒查询', path: 'search'})
            break;
          case 17:
            this.global.status.alertType = 10;
            this.$router.push({name: '警告提醒查询', path: 'search'})
            break;
          case 18:
            this.global.status.alertType = 9;
            this.$router.push({name: '警告提醒查询', path: 'search'})
            break;
          case 19:
            this.global.status.alertType = 7;
            this.$router.push({name: '警告提醒查询', path: 'search'})
            break;
          case 20:
            this.global.status.alertType = 7;
            this.$router.push({name: '警告提醒查询', path: 'search'})
            break;
          case 21:
            this.global.status.alertType = 8;
            this.$router.push({name: '警告提醒查询', path: 'search'})
            break;
        }
        // if(index == 1){
        //   this.global.status.wifiType = 4
        // }else if(index == )
        // if(index < 6){
        //   if(index == 1){
        //     this.global.status.wifiType = 4
        //   }else if(index == 2){
        //     this.global.status.wifiType
        //   }
        //   this.$router.push({ name: 'WiFi热点', path:'hotspot' })
        // }else if(index >= 6 && index < 11){
        //   this.$router.push({ name: '无线终端', path:'client' })
        // }else{
        //   this.$router.push({ name: '警告提醒查询', path:'search' })
        // }
      },
      getStatisticsInfo(params) {
        var res = params;

        let statisticsInfo = [
          {
            title: "警报事件数/网络事件总数",
            number: res.totalEventNum
          },
          {
            title: "当前未授权WiFi热点数",
            number: res.unauthorizedWifiNum
          },
          {
            title: "当前未授权网络终端数",
            number: res.unauthorizedClientNum
          },
          {
            title: "当前WiFi热点总数",
            number: res.totalWifiNum
          },
          {
            title: "当前接入内网WiFi热点数",
            number: res.intranetWifiNum
          },
          {
            title: "当前未接入内网WiFi热点数",
            number: res.nonIntranetWifiNum
          },
          {
            title: "当前授权WiFi热点数",
            number: res.authorizedWifiNum
          },
          {
            title: "当前在线白名单WiFi数",
            number: res.onlineWhiteListWifiNum
          },
          {
            title: "当前接入内网无线终端数",
            number: res.intranetClientNum
          },
          {
            title: "当前未接入内网无线终端数",
            number: res.nonIntranetClientNum
          },
          {
            title: "当前授权无线终端数",
            number: res.authorizedClientNum
          },
          {
            title: "当前在线白名单无线终端数",
            number: res.onlineWhiteListClientNum
          },
          {
            title: "报警事件数",
            number: res.alertNum
          },
          {
            title: "提醒事件",
            number: res.warningNum
          },
          {
            title: "Dos攻击警报事件数",
            number: res.dosAttackAlertNum
          },
          {
            title: "伪造WiFi警报事件数",
            number: res.forgedWifiAlertNum
          },
          {
            title: "未授权WiFi警报事件数",
            number: res.unauthorizedWifiAlertNum
          },
          {
            title: "未授权无线终端警报事件数",
            number: res.unauthorizedClientAlertNum
          },
          {
            title: "授权无线终端连接未授权WiFi热点警报事件数",
            number: res.authorizedClientUnauthorizedWifiAlertNum
          },
          {
            title: "检测长期未出现设备提醒事件数",
            number: res.longAbsenceWarningNum
          },
          {
            title: "弱加密WiFi热点提醒事件数",
            number: res.weakEncryptionWifiWarningNum
          },
          {
            title: "授权WiFi热点接入内网提醒事件数",
            number: res.authorizedWifiIntranetWarningNum
          }
        ];
        this.statisticsInfo = statisticsInfo;
      }
    },
    created() {
      var _this = this;
      axios
        .post(`http://${this.global.url.host}:${this.global.url.port}`, {
          version: this.global.url.version,
          params: null,
          token: this.global.url.token,
          id: this.global.url.id,
          method: "wifiDetection.getStatisticsInfo"
        })
        .then(res => {
//          console.log(res);
          this.getStatisticsInfo(res.data.result.statisticsInfo);
          this.global.statisticsInfo = res.data.result.statisticsInfo;
          this.global.statusGrade = res.data.result.statisticsInfo.statusGrade;
        });
    }
  };
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .dashboard {
    &-container {
      margin: 30px;
    }
    &-text {
      font-size: 30px;
      line-height: 46px;
    }
  }

  .el-button {
    display: block;
    margin-bottom: 5px;
  }
</style>
