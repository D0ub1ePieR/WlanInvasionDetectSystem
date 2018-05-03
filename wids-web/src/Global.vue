<script>
  import { getToken, setToken, removeToken } from '@/utils/auth'
  var url = {
    // host: '101.110.46.27',
    host: '127.0.0.1',
    //host: '192.168.0.100',
    port: '6060',
    username: 'eda',
    password: '123456',
    token: getToken(),
    version: 1,
    id: 1,
  }

  var wifi = {
    wifiBssid: '',
    startTime: '',
    endTime: ''
  }

  var client = {
    clientBssid: '',
    accessPointBssid: '',
    startTime: '',
    endTime: '',
    testClientID : '00-00-00-00-00-00'
  }

  var status = {
    wifiType: 0,
    wifiOnline: 0,
    clientType: 0,
    clientOnline: 0,
    alertType: 0,
  }

  var statisticsInfo = {}

export default {
  url,
  wifi,
  client,
  statisticsInfo,
  status,
}
</script>
