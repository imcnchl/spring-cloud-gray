# spring-cloud-gray

## 流量控制（灰度发布）

模拟请求：Postman

请求地址：http://localhost:8000/order/order/detail/{{$timestamp}}

### 流控规则配置说明

PS：为了方便展示，这里采用json配置，如需使用yaml、properties请自行转换

```json5
{
  //根节点，和 ConfigurationProperties 注解的prefix一致
  "flow-control": {
    //是否启用流控策略
    "enable": true,
    //当前环境，设置没有匹配的策略时流量应该到哪个环境，必须指定
    "current": {
      //环境名称，英文字符
      "name": "A",
      //版本信息，同一个环境中可能有多个版本的代码，如果指定了则只访问指定版本的代码
      "version": ""
    },
    //故障隔离策略，暂未实现
    "isolations": [],
    //兜底策略：无法根据“预期环境和版本”匹配实例
    //预期：strategies.environment 指定的环境
    //当前：current 指定的环境
    //0-不使用兜底策略
    //1-使用相同环境的服务实例（预期：B环境 1.1版本，则匹配B环境所有版本）
    //2-使用当前环境当前版本的服务实例（当前：A环境 1.1版本，预期：B环境 1.2版本，则匹配A环境 1.1版本）
    //3-使用当前环境的服务实例（当前：A环境 1.1版本，预期：B环境 1.2版本，则匹配A环境）
    "outStrategy": 2,
    //流控策略列表
    "strategies": [
      {
        //是否启用该流控策略
        "enable": true,
        //流控类型，1-按内容控制，2-按比例控制
        "type": 1,
        //预期环境：满足该策略后使用的环境和版本
        "environment": {
          //环境名称
          "name": "B",
          //版本信息，可空
          "version": "1.2"
        },
        //按内容控制时的控制规则
        "contentRule": {
          //HTTP相对路径，例如/user/name/1234，注意是严格匹配，留空表示匹配任何路径
          "path": "",
          //条件模式，1-同时满足下列条件，2-满足下列任一条件
          "conditionMode": 1,
          //规则条件列表
          "conditions": [
            {
              //条件类型：1-Header参数、2-URL参数、3-Body参数（待实现）、4-Cookie参数
              "type": 1,
              //参数名
              "name": "_environment_",
              //参数值
              "value": "caohongliang"
            },
            {
              "type": 1,
              "name": "_other_header_",
              "value": "caohongliang11"
            }
          ]
        },
        //按比例控制时的流控规则
        "percentageRule": {
          //转发到该策略的流量百分比
          "percentage": 30
        }
      }
    ]
  }
}
```

### 启动服务

1. 启动order服务，A环境

```text
java -jar .\order\target\order-1.0.0.jar --server.port=0 --environment.name=A --environment.version=
java -jar .\order\target\order-1.0.0.jar --server.port=0 --environment.name=A --environment.version=1.1
java -jar .\order\target\order-1.0.0.jar --server.port=0 --environment.name=A --environment.version=1.2
```

2. 启动order服务，B环境

```text
java -jar .\order\target\order-1.0.0.jar --server.port=0 --environment.name=B --environment.version=
java -jar .\order\target\order-1.0.0.jar --server.port=0 --environment.name=B --environment.version=1.1
java -jar .\order\target\order-1.0.0.jar --server.port=0 --environment.name=B --environment.version=1.2
```

3. 启动payment服务，A环境

```text
java -jar .\payment\target\payment-1.0.0.jar --server.port=0 --environment.name=A --environment.version=
   java -jar .\payment\target\payment-1.0.0.jar --server.port=0 --environment.name=A --environment.version=1.1
   java -jar .\payment\target\payment-1.0.0.jar --server.port=0 --environment.name=A --environment.version=1.2
```

4. 启动payment服务，B环境

```text
 java -jar .\payment\target\payment-1.0.0.jar --server.port=0 --environment.name=B --environment.version=
java -jar .\payment\target\payment-1.0.0.jar --server.port=0 --environment.name=B --environment.version=1.1
java -jar .\payment\target\payment-1.0.0.jar --server.port=0 --environment.name=B --environment.version=1.2
```

5. 启动user服务，A环境

```text
java -jar .\user\target\user-1.0.0.jar --server.port=0 --environment.name=A --environment.version=
   java -jar .\user\target\user-1.0.0.jar --server.port=0 --environment.name=A --environment.version=1.1
   java -jar .\user\target\user-1.0.0.jar --server.port=0 --environment.name=A --environment.version=1.2
```

6. 启动user服务，B环境

```text
java -jar .\user\target\user-1.0.0.jar --server.port=0 --environment.name=B --environment.version=
java -jar .\user\target\user-1.0.0.jar --server.port=0 --environment.name=B --environment.version=1.1
java -jar .\user\target\user-1.0.0.jar --server.port=0 --environment.name=B --environment.version=1.2
```

7. 启动gateway服务，无需指定环境，直接在IDEA上启动

### 配置不同的流控规则

修改nacos配置中心的 flow-control.json，并多次请求，看响应返回

## SkyWalking
### 本地开发
启动参数：
-javaagent:D:\Software\apache-skywalking-apm-bin-es7\agent\skywalking-agent.jar -Dskywalking.collector.backend_service=192.168.56.11:11800 -Dskywalking.agen.service_name=gateway
