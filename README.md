<p align="center">
	<img src="https://user-images.githubusercontent.com/43493852/127585072-ccafe163-4a43-439e-9594-156b4d10a72e.png">
	<p align="center">
	<img src="https://app.codacy.com/project/badge/Grade/02e473424ae64646b7f24ec11455bc42"/></img>
	<img src="https://img.shields.io/github/commit-activity/m/GoogTech/vsblog?color=ff69b4"></img>
	<img src="https://img.shields.io/github/repo-size/GoogTech/vsblog"></img>
	<img src="https://img.shields.io/github/license/GoogTech/vsblog.svg"></img>
	</p>
</p>

*🖖 最近在学习前后端分离开发模式，为了整合 Springboot 与 Vue 框架而写了这个小项目. 账号 : `admin`，密码 : `iblog.run`.*

### 技术
#### 前端 
* element-ui
* axios
* vue

#### 后端 
* hibernate validatior
* mybatis plus
* springboot
* mysql
* shiro
* redis
* jwt


### 功能
* [x] 登录验证
* [x] 文章展示
* [x] 文章添加
* [x] 文章删除
* [x] 文章修改
* [x] 文章查找
* [x] 文章归档
* [x] 页面特效
* [x] 点击特效
* [ ] 文章分类
* [ ] 友链
* [ ] 留言


### 部署
*以下是我在 `Ubuntu18.04` 远程服务器上部署 `vsblog` 的流程*.

<details>
    <summary>1. 安装 JDK</summary>

```shell
# install jdk
sudo apt install openjdk-8-jdk-headless

# check
java -version
```
</details>

<details>
    <summary>2. 安装 MySQL8.0+</summary>
    
```shell
# the installation package as follows you need to download it
# decompress the installation package
sudo dpkg -i mysql-apt-config_0.8.10-1_all.deb

# update installation package
sudo apt update 

# install mysql server
sudo apt install mysql-server 

# run mysql
mysql -u root -p
```
</details>

<details>
    <summary>3. 安装 Redis</summary>
    
```shell
# install redis server
sudo apt-get install redis-server

# check redis server status
netstat -nlt|grep 6379

# test
redis-cli
```
</details>

<details>
    <summary>4. 导入 vueblog.sql 数据库</summary>
    
```shell
# mysql -u root -p < vueblog.sql
mysql -u root -p < 'the specified path of database file'
```
</details>

<details>
    <summary>5. 运行项目</summary>
    
```shell
# kill the specified server port:8080
kill -9 $(netstat -nlp | grep :8080 | awk '{print $7}' | awk -F"/" '{ print $1 }')

# run
# nohup /usr/lib/jvm/java-8-openjdk-amd64/jre/bin/java -jar /tmp/vueblog-0.0.1-SNAPSHOT.jar > vsblog.file 2>&1 &
nuhup -jar vueblog-0.0.1-SNAPSHOT.jar > vsblog.file 2>&1 &

# test
http://ip:8080/
```
</details>

<details>
    <summary>6. 绑定域名</summary>
    
```shell
# first you need install the nginx
sudo apt install nginx

# vim /etc/nginx/nginx.conf
# such as the example configuraion be added as follows
http{
	server{
		listen  80;
		server_name  vue.iblog.run; 
		location / {
			proxy_pass  http://ip:8080;	
		}            
	}
}
. . .

# then check the default configuration file
nginx -t

# reload the nginx
nginx -s reload

# test
http://vue.iblog.run
```
</details>


### 注意事项

#### 若需本地运行该项目，请修改如下配置
<details>
<summary>1. 前端 : axios.js</summary>
	
```js
// 自定义全局请求头(后台)
// axios.defaults.baseURL = "http://49.233.79.152:8080"
axios.defaults.baseURL = "http://localhost:8080"
```
</details>

<details>
<summary>2. 后端 : application.yml</summary>
	
```js
redis-manager:
	// host: 49.233.79.152:6379
	host: 127.0.0.1:6379
```
</details>

#### 该项目使用的 MySQL 的版本号为 `8.0.11` ,如果你的数据库版本低于它，请修改如下配置
1. `pom.xml` : 修改 mysql-connector-java 的版本
2. `application.yml` : 将 driver-class-name 修改为 com.mysql.jdbc.Driver

> 详情请参考我的博文 : [JDBC-常见错误分析](https://goog.tech/blog/2019/03/14/JDBC-%E5%B8%B8%E8%A7%81%E9%94%99%E8%AF%AF%E5%88%86%E6%9E%90/)


### 致谢
* [VBlog](https://github.com/lenve/VBlog)
* [MarkerHub](https://space.bilibili.com/13491144)
