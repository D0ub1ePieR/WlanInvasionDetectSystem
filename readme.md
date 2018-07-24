* 用户接口测试 zyj

| Function | DDL | Status |
| :-: | :-: | :-: |
| login | 7-11 | ✔ |
| ... | ... | ... |

* 热点检测接口测试 lt

| Function | DDL | Status |
| :-: | :-: | :-: |
| GetOnlineWhiteList | 7-15 | ✔ |
| GetStatisticsInfo | 7-15 | ✔ |
| GetWifiClientList | 7-13 | ✔ |
| GetWifiDetail | 7-13 | ✔ |
| GetWifiEventList | 7-15 | ✔ |
| GetWifiList | 7-13 | ✔ |
| GetWifiSignals | 7-13 | ✔ |

* 终端检测接口模块 zyj

| Function | DDL | Status |
| :-: | :-: | :-: |
| GetClientDetail | 7-19 | ✔ |
| GetClientEventList | 7-19 | ✔ |
| GetClientList | 7-19 | ✔ |

* 事件信息模块实现和调试 zyj
    * ddl 7-21
    * 已拆分合并至终端接口及热点接口中的eventList

* 白名单模块测试 zyj
    * 热点白名单测试
    * 终端白名单测试
    
| Function | DDL | Status |
| :-: | :-: | :-: |
| AddClientToWhiteList | 7-25 | ✘ |
| AddWifiToWhiteList | 7-25 | ✘ |
| DeleteClientFromWhiteList | 7-25 | ✘ |
| DeleteWifiFromWhiteList | 7-25 | ✘ |
| GetClientWhiteList | 7-25 | ✘ |
| GetWifiWhiteList | 7-25 | ✘ |
| UpdateClientWhiteList | 7-25 | ✘ |
| UpdateWifiWhiteList | 7-25 | ✘ |

***
# wlan_invasion_detection_system
* Backend Java
* Frontend Vue
* Connect Vertx
* Database Mongodb
* Detect JKismet
