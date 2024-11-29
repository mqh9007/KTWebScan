#### 技术栈
- 前端：vue + element-ui
- 服务端：springboot + mybatis
- 服务端数据库：mysql
- 缓存数据库：redis

#### 技术要点
- 调用nmap扫描端口
- 调用ZAP实现漏洞扫描

### 接口信息
#### 用户接口
- /user/login **用户登录**
- /user/logout **退出登录**
- /user/info **用户信息**
- /user/register  **用户注册**
#### nmap接口
- /nmap/scan  **提交扫描**
- /nmap/result **扫描结果**
- 123


### ZAP调用
- zap扫描任务是异步执行
- 调用API执行扫描任务（targetURL，ScanType）
- ZAP回调报告扫描完成

1.启动ZAP扫描，ZAP返回一个ScanID

2.将ScanID保存到redis，标记为扫描中

3.轮询扫描任务，1s一次查询扫描进度，进度信息（百分比）保存在redis

4.设置回调函数，报告扫描完成，将扫描记录保存在MySQL

5.将扫描结果保存在文件中文件名与MySQL中的扫描记录对应便于前端调用查看

