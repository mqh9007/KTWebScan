#### 启动ZAP
```shell
./doc/ZAP_2.15.0/zap.sh -daemon -config api.disablekey=true
```
- -daemon 是让 ZAP 在后台运行
- -config api.disablekey = true 表示禁用 API 密钥验证
### 使用ZAP API
#### 启动扫描
```shell
http://localhost:8080/JSON/spider/action/scan
```
 

