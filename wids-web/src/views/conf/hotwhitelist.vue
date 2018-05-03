<template>
  <div class="device-hotspot">
    <div class="mask" v-show="isUpdate || isImport || isFileImport"></div>
    <el-form ref="form" label-width="120px">
      <el-form-item>
        <el-button @click="batchImport">批量导入实时热点</el-button>
        <el-button @click="fileImport">文件导入热点</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="wifiWhiteList"
      height="500"
      border
      style="width: 100%"
    >
      <el-table-column prop="wifiBssid" label="硬件地址" width="170"></el-table-column>
      <el-table-column prop="wifiSsid" label="网络名称"></el-table-column>
      <el-table-column prop="timeout" label="超时情况" width="100"></el-table-column>
      <el-table-column prop="remark" label="备注名称" width="110"></el-table-column>
      <el-table-column prop="location" label="常用位置" width="100"></el-table-column>
      <el-table-column prop="manager" label="负责人" width="110"></el-table-column>
      <el-table-column prop="contact" label="联系方式" width="130"></el-table-column>
      <el-table-column
        fixed="right"
        label="操作"
        width="150">
        <template scope="scope">
          <el-button @click="view(scope.row)" type="text" size="small">查看</el-button>
          <el-button @click="edit(scope.row)" type="text" size="small">编辑</el-button>
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
    <div class="form" v-show="isUpdate">
      <el-form ref="form" :model="updateList" label-width="120px">
        <el-form-item label="硬件地址">
          <el-input v-model="updateList.wifiBssid" disabled></el-input>
        </el-form-item>
        <el-form-item label="网络名称">
          <el-input v-model="updateList.wifiSsid" disabled></el-input>
        </el-form-item>
        <el-form-item label="备注名称">
          <el-input v-model="updateList.remark"></el-input>
        </el-form-item>
        <el-form-item label="常用位置">
          <el-input v-model="updateList.location"></el-input>
        </el-form-item>
        <el-form-item label="负责人">
          <el-input v-model="updateList.manager"></el-input>
        </el-form-item>
        <el-form-item label="联系方式">
          <el-input v-model="updateList.contact"></el-input>
        </el-form-item>
        <el-button @click="update">确定</el-button>
        <el-button @click="formCancel">取消</el-button>
      </el-form>
    </div>
    <div>
      <div class="importForm" v-show="isImport">
        <device-select @confirm="confirm"></device-select>
      </div>
    </div>

    <div class="form" v-show="isFileImport">
      <label>请选择一个文件：</label>
      <input type="file" accept="text/plain " id="file"/>
      <el-button @click="readAsText">确定</el-button>
      <el-button @click="importCancel">取消</el-button>
    </div>

  </div>
</template>

<script>
  import axios from 'axios'
  import deviceSelect from './deviceSelect'

  export default {
    components: {
      deviceSelect
    },
    data() {
      return {
        wifiWhiteList: [],
        wifiWhiteLists: [],
        updateList: {
          wifiBssid: '',
          wifiSsid: '',
          remark: '',
          location: '',
          manager: '',
          contact: ''
        },
        allPage: 1,
        isUpdate: false,
        isImport: false,
        isFileImport: false
      };
    },
    mounted() {
      this.getWhiteList()
    },
    methods: {
      getWhiteList() {
        axios.post(`http://${this.global.url.host}:${this.global.url.port}`, {
          version: this.global.url.version,
          params: {
            page: 1
          },
          id: this.global.url.id,
          token: this.global.url.token,
          method: 'whiteList.getWifiWhiteList'
        })
          .then(res => {
            console.log(res)
            if (res.data.result) {
              this.wifiWhiteList = res.data.result.wifiWhiteList
              this.fliterData(this.wifiWhiteList)
              this.allPage = res.data.result.totalPage
            } else {
              console.log(res)
              this.$message(res.data.error.message)
            }
          })
      },
      readAsText() {
        var _this = this;
        var file = document.getElementById("file").files[0];
        var reader = new FileReader();
        var wifiWhiteLists = [];
        reader.readAsText(file);
        reader.onload = function (f) {
          var textLines = this.result.split("\n")
          var formatError = false
          for (var line in textLines) {
            var words = textLines[line].split(",")
            if (words.length == 6) {
              var wifiWhiteList = {};
              wifiWhiteList.bssid = words[0]
              wifiWhiteList.ssid = words[1]
              wifiWhiteList.remark = words[2]
              wifiWhiteList.location = words[3]
              wifiWhiteList.manager = words[4]
              wifiWhiteList.contact = words[5]
              wifiWhiteLists.push(wifiWhiteList)
            }
            else {
              formatError = true
            }
          }

          if (formatError) {
            _this.$message("文件格式错误")
          }
          else {
            _this.wifiWhiteLists = wifiWhiteLists
            console.log(_this.wifiWhiteLists)
            axios.post(`http://${_this.global.url.host}:${_this.global.url.port}`, {
              version: _this.global.url.version,
              params: {
                wifiInfoList: _this.wifiWhiteLists
              },
              id: _this.global.url.id,
              token: _this.global.url.token,
              method: 'whiteList.addWifiToWhiteList'
            })
              .then(res => {
                var errorNum = 0
                if (res.data.result.resultList) {
                  for(var tmp in res.data.result.resultList) {
                    if(res.data.result.resultList[tmp].singleCode != 0){
                      errorNum++
                    }
                  }
                  _this.$message(res.data.result.resultList.length - errorNum + "条添加成功")
                  _this.isFileImport = false
                } else {
                  _this.$message(res.data.error.message)
                  _this.isFileImport = false
                }
              })
          }
        }
//
      },
      confirm() {
        this.isImport = false;
      },
      handleCurrentChange(val) {
        var _this = this;
        axios.post(`http://${this.global.url.host}:${this.global.url.port}`, {
          version: this.global.url.version,
          params: {
            page: val
          },
          id: this.global.url.id,
          token: this.global.url.token,
          method: 'whiteList.getWifiWhiteList'
        })
          .then(res => {
            console.log(res)
            if (res.data.result) {
              this.wifiWhiteList = res.data.result.wifiWhiteList
              this.fliterData(this.wifiWhiteList)
            } else {
              console.log(res)
              this.$message(res.data.error.message)
            }
          })
      },
      addWhitelist() {
        axios.post(`http://${this.global.url.host}:${this.global.url.port}`, {
          version: this.global.url.version,
          params: {
            wifiInfoList: [{
              wifiSsid: this.form.name,
              wifiBssid: this.form.macAddress,
              location: this.form.location,
              manager: this.form.manager,
              contact: this.form.contact
            }]
          },
          id: this.global.url.id,
          token: this.global.url.token,
          method: 'whiteList.addWifiToWhiteList'
        })
          .then(res => {
            if (res.data.result.resultList) {
              console.log(res)
              this.$message('添加成功')
            } else {
              console.log(res)
              this.$message(res.data.error.message)
            }
          })
      },
      update() {
        axios.post(`http://${this.global.url.host}:${this.global.url.port}`, {
          version: this.global.url.version,
          params: this.updateList,
          id: this.global.url.id,
          token: this.global.url.token,
          method: 'whiteList.updateWifiWhiteList'
        })
          .then(res => {
            console.log(res)
            if (!res.data.error) {
              console.log(res)
              this.isUpdate = false
              this.$message('更新成功')
            } else {
              console.log(res)
              this.isUpdate = false
              this.$message(res.data.error.message)
            }
          })
      },
      formCancel() {
        this.isUpdate = false;
        this.$message('取消编辑')
      },
      importCancel() {
        this.isFileImport = false;
        this.$message('取消导入')
      },
      reset() {
        this.$message("重置成功!");
      },
      batchImport() {
        this.isImport = true
      },
      fileImport() {
        this.isFileImport = true
      },
      deleteItem(row, column) {
        var id = row.wifiBssid
        this.$confirm('该操作会将热点从白名单删除, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          axios.post(`http://${this.global.url.host}:${this.global.url.port}`, {
            version: this.global.url.version,
            params: {
              wifiBssid: id
            },
            token: this.global.url.token,
            id: this.global.url.id,
            method: 'whiteList.deleteWifiFromWhiteList'
          })
            .then(res => {
              if (!res.data.error) {
                this.$message('删除成功')
                this.getWhiteList()
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
      },
      toDetails(id) {
        this.$router.push(`/device/hotspotDetails/${id}`)
      },
      view(row) {
        this.toDetails(row.wifiBssid)
      },
      edit(row) {
        this.isUpdate = true
        this.updateList = row
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
  };
</script>

<style scoped>
  .mask {
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    min-height: 100vh;
    background: #000;
    opacity: 0.7;
    z-index: 10;
  }

  .form {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    background: white;
    padding: 40px 40px 40px 10px;
    text-align: center;
    z-index: 11;
  }

  .importForm {
    position: absolute;
    top: 55%;
    left: 55%;
    width: 80%;
    transform: translate(-50%, -50%);
    background: white;
    text-align: center;
    z-index: 11;
  }
</style>

