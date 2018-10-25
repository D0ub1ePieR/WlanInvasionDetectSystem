TODO<br> 
加载真实数据

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
    7-26
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
| updateReportConf | 7-27 | ✔ |
| getReportConf | 7-27 | ✔ |
| createReport | 7-27 | ✔ |
| deleteReport | 7-27 | ✔ |
| getReportList | 7-27 | ✔ |
| getReportDetail | # | ✔ |
| finish time 7-27 |

* 弱密码模块

| Function | DDL | Status |
| :-: | :-: | :-: |
| crackWifiPasswd | 7-31 | ✘ |
| getCrackTaskProgress | 7-31 | ✘ |

```aidl
    7-28
    * 首先讨论区分是实现弱密码的检测还是破解的功能
    * 由于密码穷举可能消耗大量时间以及有功能模块为获取进度
      可能需要多进程的代码，需要学习一下
    对于弱密码模块准备采用一些弱密码生成脚本
    1-生成普遍性的弱密码
    2-根据用户信息或设备信息生成密码字典
    
    ps:1-准备构建一个脚本，一键运行数据库，前后端
       2-构建脚本，扫面目录下测试类，一键运行测试，并输出返回报文
    要写不完了QAQ
```

***
# wlan_invasion_detection_system
* Backend Java
* Frontend Vue
* Connect Vertx
* Database Mongodb
* Detect JKismet
