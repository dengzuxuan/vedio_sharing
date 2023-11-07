# vedio_sharing
# 短视频平台

前端 陈典 @cdcdcd

后端 邓祖轩 @dengzuxuan

接口 https://apifox.com/apidoc/shared-4fd4ee82-ac26-4106-9903-b6ad9f08bb8a/api-119916477

地址 http://8.140.38.47:3000/

demo http://s34n6l898.hn-bkt.clouddn.com/demo.mp4

### 1. 项目架构

​		前端以React+ts为架构进行设计，样式上采取了antd以及更加稳定成熟的样式sass。后端使用Java三件套spring springmvc以及Mybatisplus，并使用springboot进行快速开发。在需要快速查询并且需要线程安全的历史记录核心模块上采用了Java本地高速缓存Caffeine。数据库方面，采用了稳定易用的MySQL作为关系型数据库存储内容。中间件采用Redis，为需要大量快速高IO访问的热度排行榜模块提供有效的支持。此外还使用了MongoDB为项目中的搜索模块提供支持，缓解频繁搜索对MySQL数据库的压力。
![Uploading image.png…]()




### 2. 架构说明


![Uploading image.png…]()

​        项目的后端链路如上图所示，以下简单阐述下核心的几个功能。

1. 鉴权功能

   ​        首先是鉴权功能，该功能采用jjwt进行鉴权，将传入的token值去掉头部进行校验，确认登录人的信息并写入Java程序的上下文中，在保证了安全性的同时有利于后续代码的开发，可以很简易的获取到当前登录信息。

2. 搜索功能

   ​		项目中有搜索需求，考虑到搜索部分如果使用MySQL like关键字实现时的时间复杂度较高，会影响整体项目性能，因此选用了文件存储的Mongodb，该数据支持全文检索以及正则表达式检索，在文字搜索上效率更高，适用于搜索查询场景，可以更快速的返回用户需要查询的内容。

3. 播放功能

   ​		在短视频播放中时的主要的操作逻辑是向上滑动观看历史视频，向下滑动观看新视频，因此有存储历史视频队列的需求。在如何存储历史队列上该项目采用了Caffeine高性能本地缓存，存储的结构是登录用户id映射队列类型，队列类型包括当前观看序号以及使用Map<Integer,Integer>存储的序号与视频映射关系。在获取历史视频时可以很快的获取到当前观看序号上一个的视频id，此外使用Caffeine较直接使用Map的优点在于其并发安全，并且可以定时进行自动历史记录清除，Map只能手动释放。

4. 推荐功能

   ​		作为短视频平台，其核心点就在于制作用户画像并根据画像实现“千人千面”的视频推荐机制。该项目实现了推荐机制的简易版本。首先对视频进行贴标签，即视频分类工作，要求用户在上传视频时必须填入视频类型。其次根据用户对视频做出的行为进行视频喜爱值的更迭，观看当前视频超过1/4、超过1/2、看完视频以及对视频进行点赞，收藏，取消点赞预计取消收藏都会对影响该类视频类型喜爱值的变化，进而完善用户画像。在推荐方面，该项目会以不同类型视频的喜爱值作为权重进行随机抽取视频，以实现推荐功能的闭环。

5. 排行榜功能

   ​        短视频平台中通常会有热度排行榜的功能，该功能对热点字段数据的输入输出频率很高，MySQL在高IO情况下有可能会出现雪崩等问题，因此该项目在获取热度时，增加热度时使用了redis中间件作为缓存，在日榜，周榜，月榜以及总榜部分都使用redis进行暂时保存，并在每日整点时刷入MySQL中。



### 3. 运行说明



下载代码后，进入/backend目录执行下行命令，执行完可以生成一个名为dokcer-backend的docker image

```
docker build -t docker-backend .
```

然后根据/backend/src/main/resources/application.yml下的配置信息依次在docker中部署mysql mongodb以及redis

```
docker run -d -p application.yml中mysql的端口号:3306 -v /home/mysql/conf:/etc/mysql/conf.d   -v /home/mysql/data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=你的密码 --name mysql-backend  mysql:5.7
```

```
docker run -itd --name mongo-backend -p application.yml中mongodb的端口号:27017 mongo --auth 
```

```
docker run --restart=always -p application.yml中redis的端口号:6379 --name redis-backend -d redis:7.0.12 
```

配好后可以将sql文件中的相关mysql数据以及mongodb数据导入

然后打包前端文件，进入/fronted目录执行下行命令，执行完可以生成一个build文件

```
npm run build
```

然后还是在/fronted目录下执行下行命令，在docker中部署nginx，记得更改/fronted目录下的nginx.config中相应信息

```
docker run -d --name nginx-video -p 3000:3000 -v ./build:/usr/share/nginx/html -v ./nginx.conf:/etc/nginx/conf nginx
```

然后就可以了

### 

### 4. 代码结构

```
[root@ColinAl backend]# tree
.
├── Dockerfile 打包
├── HELP.md
├── mvnw
├── mvnw.cmd
├── pom.xml
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── vediosharing
│   │   │           └── backend 
│   │   │               ├── BackendApplication.java                        启动文件
│   │   │               ├── controller                                     控制层
│   │   │               │   ├── ResourceController.java
│   │   │               │   ├── SearchController.java
│   │   │               │   ├── UserController.java
│   │   │               │   ├── UserOptController.java
│   │   │               │   ├── UserVideoController.java
│   │   │               │   └── VideoController.java
│   │   │               ├── core                                            任务核心
│   │   │               │   ├── aspect
│   │   │               │   ├── common
│   │   │               │   │   ├── constant
│   │   │               │   │   │   └── result
│   │   │               │   │   │       └── ResultCodeEnum.java             错误码及提示消息
│   │   │               │   │   ├── cronjob
│   │   │               │   │   │   └── RedisRankListClearTask.java         定时任务 redis落库mysql
│   │   │               │   │   └── exception
│   │   │               │   ├── config                                      配置文件  
│   │   │               │   │   ├── CaffeineConfig.java
│   │   │               │   │   ├── CorsConfig.java
│   │   │               │   │   ├── MybatisPlusConfig.java
│   │   │               │   │   ├── QiniuStorageConfig.java
│   │   │               │   │   ├── RedisConfig.java
│   │   │               │   │   └── SecurityConfig.java
│   │   │               │   ├── constant                                    常量
│   │   │               │   │   ├── ApiRouterConsts.java
│   │   │               │   │   ├── LikeConsts.java
│   │   │               │   │   ├── MessageConsts.java
│   │   │               │   │   ├── RankConsts.java
│   │   │               │   │   ├── Result.java
│   │   │               │   │   └── VideoTypeConsts.java
│   │   │               │   ├── filter
│   │   │               │   │   └── JwtAuthenticationTokenFilter.java       jwt过滤
│   │   │               │   └── utils                                       操作类包括caffeine redis mongodb等
│   │   │               │       ├── CaffeineUtil.java                       
│   │   │               │       ├── JwtUtil.java                 
│   │   │               │       ├── OrderDateComparator.java
│   │   │               │       ├── PathUtils.java
│   │   │               │       ├── RankUtil.java
│   │   │               │       ├── RegexUtil.java
│   │   │               │       ├── SearchUtil.java
│   │   │               │       └── VideoFrameGrabber.java
│   │   │               ├── dao
│   │   │               │   ├── entity                                      dao层实体
│   │   │               │   │   ├── Collects.java
│   │   │               │   │   ├── Comment.java
│   │   │               │   │   ├── CommentLikes.java
│   │   │               │   │   ├── Friend.java
│   │   │               │   │   ├── History.java
│   │   │               │   │   ├── Likes.java
│   │   │               │   │   ├── Message.java
│   │   │               │   │   ├── User.java
│   │   │               │   │   ├── UsertLikely.java
│   │   │               │   │   └── Video.java
│   │   │               │   └── mapper                                      dao层映射
│   │   │               │       ├── CollectMapper.java
│   │   │               │       ├── CommentLikesMapper.java
│   │   │               │       ├── CommentMapper.java
│   │   │               │       ├── FriendMapper.java
│   │   │               │       ├── LikeMapper.java
│   │   │               │       ├── MessageMapper.java
│   │   │               │       ├── UserLikelyMapper.java
│   │   │               │       ├── UserMapper.java
│   │   │               │       └── VideoMapper.java
│   │   │               ├── dto
│   │   │               │   ├── req                                         dto层入参实体
│   │   │               │   │   ├── CommentReqDto.java
│   │   │               │   │   ├── UserInfoReqDto.java
│   │   │               │   │   ├── UserRegisterReqDto.java
│   │   │               │   │   ├── VideoJudgeReqDto.java
│   │   │               │   │   └── VideoReqDto.java
│   │   │               │   └── resp                                        dto层出参实体
│   │   │               │       ├── CommentDetailRespDto.java
│   │   │               │       ├── MessageDetailRespDto.java
│   │   │               │       ├── SearchDeatilRespDto.java
│   │   │               │       └── VideoDetailRespDto.java
│   │   │               └── service
│   │   │                   ├── Impl                                        service层实现类
│   │   │                   │   ├── MessageServiceImpl.java
│   │   │                   │   ├── OptVideoServiceImpl.java
│   │   │                   │   ├── ResourceServiceImpl.java
│   │   │                   │   ├── SearchServiceImpl.java
│   │   │                   │   ├── UserDetailServiceImpl.java
│   │   │                   │   ├── UserServiceImpl.java
│   │   │                   │   ├── UserVideoServiceImpl.java
│   │   │                   │   ├── utils
│   │   │                   │   │   ├── UserDetailsImpl.java
│   │   │                   │   │   ├── VideoPool.java
│   │   │                   │   │   └── VideoPools.java
│   │   │                   │   └── VideoServiceImpl.java
│   │   │                   ├── MessageService.java
│   │   │                   ├── OptVideoService.java
│   │   │                   ├── ResourceService.java
│   │   │                   ├── SearchService.java
│   │   │                   ├── UserService.java
│   │   │                   ├── UserVideoService.java
│   │   │                   └── VideoService.java
│   │   └── resources
│   │       ├── application.yml                                            配置文件
│   │       ├── static
│   │       └── templates
│   └── test
│       └── java
│           └── com
│               └── vediosharing
│                   └── backend
│                       ├── BackendApplicationTests.java
│                       ├── JedisTest.java
│                       └── log4jTest.java
└── target
    └── backend-0.0.1-SNAPSHOT.jar

```

