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
| finish time 7-22 |

* 终端检测接口模块 zyj

| Function | DDL | Status |
| :-: | :-: | :-: |
| GetClientDetail | 7-19 | ✔ |
| GetClientEventList | 7-19 | ✔ |
| GetClientList | 7-19 | ✔ |
| finish time 7-23 |

* 事件信息模块实现和调试 zyj
    * ddl 7-21
    * 已拆分合并至终端接口及热点接口中的eventList

* 白名单模块测试 zyj
    * 热点白名单测试
    * 终端白名单测试
    
| Function | DDL | Status |
| :-: | :-: | :-: |
| AddClientToWhiteList | 7-25 | ✔ |
| AddWifiToWhiteList | 7-25 | ✔ |
| DeleteClientFromWhiteList | 7-25 | ✔ |
| DeleteWifiFromWhiteList | 7-25 | ✔ |
| GetClientWhiteList | 7-25 | ✔ |
| GetWifiWhiteList | 7-25 | ✔ |
| UpdateClientWhiteList | 7-25 | ✔ |
| UpdateWifiWhiteList | 7-25 | ✔ |
| finish time 7-26 |

```$xslt
    需要注意类似删除更新操作时，测试类未对设备是否存在做出判断
    只是对报文以及报文内容的格式做出了解析和判断正确性
```

* 报表模块
    * 报表模块实现
        * 初始化及增删改查
    * 报表模块测试
        * 功能测试及显示

| Function | DDL | Status |
| :-: | :-: | :-: |
| 更新报表配置 | 7-27 | ✘ |
| 获取报表配置 | 7-27 | ✘ |
| 报表生成 | 7-27 | ✘ |
| 报表删除 | 7-27 | ✘ |
| 获取报表列表 | 7-27 | ✘ |

***
# wlan_invasion_detection_system
* Backend Java
* Frontend Vue
* Connect Vertx
* Database Mongodb
* Detect JKismet
