# spring-cloud-gray

## 灰度测试

1. 启动user服务，A环境
   java -jar .\user\target\user-1.0.0.jar --server.port=0 --environment.name=A --environment.version=
   java -jar .\user\target\user-1.0.0.jar --server.port=0 --environment.name=A --environment.version=1.1
   java -jar .\user\target\user-1.0.0.jar --server.port=0 --environment.name=A --environment.version=1.2
2. 启动user服务，B环境，没有version
   java -jar .\user\target\user-1.0.0.jar --server.port=0 --environment.name=B --environment.version=
   java -jar .\user\target\user-1.0.0.jar --server.port=0 --environment.name=B --environment.version=1.1
   java -jar .\user\target\user-1.0.0.jar --server.port=0 --environment.name=B --environment.version=1.2
3. 启动payment服务，A环境
   java -jar .\payment\target\payment-1.0.0.jar --server.port=0 --environment.name=A --environment.version=
   java -jar .\payment\target\payment-1.0.0.jar --server.port=0 --environment.name=A --environment.version=1.1
   java -jar .\payment\target\payment-1.0.0.jar --server.port=0 --environment.name=A --environment.version=1.2
3. 启动payment服务，B环境
   java -jar .\payment\target\payment-1.0.0.jar --server.port=0 --environment.name=B --environment.version=
   java -jar .\payment\target\payment-1.0.0.jar --server.port=0 --environment.name=B --environment.version=1.1
   java -jar .\payment\target\payment-1.0.0.jar --server.port=0 --environment.name=B --environment.version=1.2