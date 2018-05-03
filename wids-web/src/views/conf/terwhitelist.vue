<template>
  <div class="device-hotspot">
    <div class="mask" v-show="isUpdate || isImport || isFileImport"></div>
    <el-form ref="form" label-width="120px">
      <el-form-item>
        <el-button @click="batchImport">批量导入网络终端</el-button>
        <el-button @click="fileImport">文件导入终端</el-button>
      </el-form-item>
    </el-form>

    <el-table
    :data="clientWhiteList"
    height="500"
    border
    style="width: 100%"
    script="true">
        <el-table-column prop="clientBssid" label="硬件地址" width="170"></el-table-column>
        <el-table-column prop="timeout" label="超时情况" width="110"></el-table-column>
        <el-table-column prop="remark" label="备注名称"></el-table-column>
        <el-table-column prop="manager" label="负责人"></el-table-column>
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
      <el-form ref="form" :model="form" label-width="120px">
        <el-form-item label="硬件地址">
          <el-input v-model="updateList.clientBssid" disabled></el-input>
        </el-form-item>
        <el-form-item label="超时情况">
          <el-input v-model="updateList.timeout" disabled></el-input>
        </el-form-item>
        <el-form-item label="备注名称">
          <el-input v-model="updateList.remark"></el-input>
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
        <client-select @confirm="confirm"></client-select>
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
import clientSelect from './clientSelect'

export default {
  components: {
    clientSelect,
  },
  data() {
    return {
      form: {
        online: '',
        type: '',
        name: '',
        address: '',
        firstSeenFrom:'',
        firstSeenTo:''
      },
      updateList: {
        clientBssid: '',
        remark: '',
        manager: '',
        contact: ''
      },
      allPage: 1,
      clientWhiteList: [],
      isUpdate: false,
      isImport: false,
      isFileImport: false
    }
  },
  mounted() {
    this.getWhiteList()
  },
  methods: {
    getWhiteList() {
      axios.post(`http://${this.global.url.host}:${this.global.url.port}`,{
        version: this.global.url.version,
        params: {
          page: 1
        },
        id: this.global.url.id,
        token: this.global.url.token,
        method: 'whiteList.getClientWhiteList'
      })
        .then(res => {
          console.log(res)
          if(res.data.result){
            this.clientWhiteList = res.data.result.clientWhiteList
            this.fliterData(this.clientWhiteList)
            this.allPage = res.data.result.totalPage
          }else{
            console.log(res)
            this.$message(res.data.error.message)
          }
        })
    },
    readAsText() {
      var _this = this;
      var file = document.getElementById("file").files[0];
      var reader = new FileReader();
      var clientWhiteLists = [];
      reader.readAsText(file);
      reader.onload = function (f) {
        var textLines = this.result.split("\n")
        var formatError = false
        for (var line in textLines) {
          var words = textLines[line].split(",")
          if (words.length == 4) {
            var clientWhiteList = {};
            clientWhiteList.clientBssid = words[0]
            clientWhiteList.remark = words[1]
            clientWhiteList.manager = words[2]
            clientWhiteList.contact = words[3]
            clientWhiteLists.push(clientWhiteList)
          }
          else {
            formatError = true
          }
        }

        if (formatError) {
          _this.$message("文件格式错误")
        }
        else {
          _this.clientWhiteLists = clientWhiteLists
          console.log(_this.clientWhiteLists)
          axios.post(`http://${_this.global.url.host}:${_this.global.url.port}`, {
            version: _this.global.url.version,
            params: {
              clientInfoList: _this.clientWhiteLists
            },
            id: _this.global.url.id,
            token: _this.global.url.token,
            method: 'whiteList.addClientToWhiteList'
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
    },
    confirm(){
      this.isImport = false;
    },
    importCancel(){
      this.isImport = false;
    },
    handleCurrentChange(val){
      axios.post(`http://${this.global.url.host}:${this.global.url.port}`,{
        version: this.global.url.version,
        params: {
          page: val
        },
        id: this.global.url.id,
        token: this.global.url.token,
        method: 'whiteList.getClientWhiteList'
      })
      .then(res => {
        console.log(res)
        if(res.data.result){
          this.clientWhiteList = res.data.result.clientWhiteList
          this.fliterData(this.clientWhiteList)
          this.allPage = res.data.result.totalPage
        }else{
          console.log(res)
          this.$message(res.data.error.message)
        }
      })
    },
    formCancel() {
      this.isUpdate = false;
      this.$message('取消编辑')
    },
    addWhitelist() {
      axios.post(`http://${this.global.url.host}:${this.global.url.port}`,{
        version: this.global.url.version,
        params: {
          clientInfoList: [{
            clientBssid: this.form.address,
            manager: this.form.manager,
            contact: this.form.contact
          }]
        },
        id: this.global.url.id,
        token: this.global.url.token,
        method: 'whiteList.addClientToWhiteList'
      })
      .then(res => {
        if(res.data.result.resultList){
          console.log(res)
          this.$message('添加成功')
        }else{
          console.log(res)
          this.$message(res.data.error.message)
        }
      })
    },
    update() {
      axios.post(`http://${this.global.url.host}:${this.global.url.port}`,{
        version: this.global.url.version,
        params: this.updateList,
        id: this.global.url.id,
        token: this.global.url.token,
        method: 'whiteList.updateClientWhiteList'
      })
      .then(res => {
        if(!res.data.error){
          console.log(res)
          this.$message('更新成功')
          this.isUpdate = false
        }else{
          console.log(res)
          this.$message(res.data.error.message)
          this.isUpdate = false
        }
      })
    },
    reset() {
      this.$message('reset!')
    },
    addTerminal() {
      this.$message('addTerminal!')
    },
    batchImport(){
      this.isImport = true
      this.$message('batchImport!')
    },
    fileImport(){
      this.isFileImport = true
    },
    deleteItem(row){
      var id = row.clientBssid
      this.$confirm('此操作会将终端从白名单中删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
          axios.post(`http://${this.global.url.host}:${this.global.url.port}`,{
            version: this.global.url.version,
            params: {
              clientBssid: id
            },
            token: this.global.url.token,
            id: this.global.url.id,
            method: 'whiteList.deleteClientFromWhiteList'
          })
          .then(res => {
            if(!res.data.error){
              this.$message('删除成功')
              this.getWhiteList()
            }else{
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
    toDetails(id){
      this.$router.push(`/device/clientDetails/${id}/00:00:00:00:00:00`)
    },
    view(row){
      this.toDetails(row.clientBssid)
    },
    edit(row){
      this.isUpdate = true
      this.updateList = row
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
    top:50%;
    left:50%;
    transform: translate(-50%,-50%);
    background: white;
    padding: 40px 40px 40px 10px;
    text-align: center;
    z-index: 11;
  }
  .importForm{
    position: absolute;
    top:55%;
    left:55%;
    width: 80%;
    transform: translate(-50%,-50%);
    background: white;
    text-align: center;
    z-index: 11;
  }
</style>
