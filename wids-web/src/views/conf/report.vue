<template>
  <div class="device-hotspot">
    <el-form ref="form" :model="form" label-width="120px">
			<el-form-item>
				<el-button @click="createReport">立即生成报表</el-button>
        <el-button @click="updateReport">更新报表配置</el-button>
			</el-form-item>
		</el-form>

		<el-transfer
      v-model="key"
      :data="transferData"
      class="transfer"
      :titles="['Source', 'Target']"
      @change="handleChange"
      >
    </el-transfer>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  data() {
    return {
        form: {
            address: '',
            people: '',
            contact:'',
        },

        key: [],
        transferData: [{
            key: 1,
            label: 'wifi热点列表',
            disabled: false
        },{
            key: 2,
            label: '无线终端列表',
            disabled: false
        },{
            key: 3,
            label: '报警列表',
            disabled: false
        },{
            key: 4,
            label: 'wifi热点白名单列表',
            disabled: false
        },{
            key: 5,
            label: '无限终端白名单列表',
            disabled: false
        }
        ],
        repostParams: [true,true,true,true,true],
        reportDetail: ''
    }
  },
  created() {
    //获取报表配置
    axios.post(`http://${this.global.url.host}:${this.global.url.port}`,{
      version: this.global.url.version,
      id: this.global.url.id,
      method: 'report.getReportConf',
      token: this.global.url.token,
    })
    .then(res => {
      console.log(res)
      if(res.data.result){
        this.repostParams[0] = res.data.result.wifiList
        this.repostParams[1] = res.data.result.clientList
        this.repostParams[2] = res.data.result.eventList
        this.repostParams[3] = res.data.result.wifiWhiteList
        this.repostParams[4] = res.data.result.clientWhiteList
        for(let i = 0 ; i < 5 ; i++){
          if(this.repostParams[i] == true){
            this.key.push(i+1)
          }
        }
        console.log(this.key)
      }else{
        this.$message(res.data.error.message)
      }
    })
  },
  methods: {
    getReport(row) {
      axios.post(`http://${this.global.url.host}:${this.global.url.port}`,{
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
        if(res.data.result){

          this.$message('创建成功')
        }else{
          this.$message(res.data.error.message)
        }
      })
    },
    handleChange(value, direction, movedKeys) {
      for(let i = 0 ; i < this.repostParams.length ; i++){
        this.repostParams[i] = false;
      }
      for(let i = 0 ; i < this.key.length ; i++){
        this.repostParams[this.key[i] - 1] = true;
      }
      console.log(this.repostParams)
    },
    //创建报表
    createReport() {
      axios.post(`http://${this.global.url.host}:${this.global.url.port}`,{
        version: this.global.url.version,
        params: null,
        token: this.global.url.token,
        id: this.global.url.id,
        method: 'report.createReport'
      })
      .then(res => {
        if(!res.data.error){
          this.$message('创建成功')
        }else{
          this.$message(res.data.error.message)
        }
      })
    },
    //更新报表
    updateReport() {
      axios.post(`http://${this.global.url.host}:${this.global.url.port}`,{
        version: this.global.url.version,
        params: {
          wifiList: this.repostParams[0],
          clientList: this.repostParams[1],
          eventList: this.repostParams[2],
          wifiWhiteList: this.repostParams[3],
          clientWhiteList: this.repostParams[4]
        },
        token: this.global.url.token,
        id: this.global.url.id,
        method: 'report.updateReportConf'
      })
      .then(res => {
        if(!res.data.error){
          this.$message('更新成功')
        }else{
          this.$message(res.data.error.message)
        }
      })
    },
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

