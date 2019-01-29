# Memcached基本使用与分布式Session（MSM）

## 目录<br/>
<a href="#一、Memcached基本使用">一、Memcached基本使用</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;<a href="#1Memcached-安装">1.Memcached 安装</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;<a href="#2-Memcached-基本命令">2. Memcached 基本命令</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;<a href="#3-Memcached-底层结构">3. Memcached 底层结构</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#31-底层存储结构">3.1 底层存储结构</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#32-寻找可用的Chunk的过程">3.2 寻找可用的Chunk的过程</a><br/>
<a href="#二、Memcached-Java-客户端">二、Memcached Java 客户端 </a><br/>
<a href="#三、分布式Session解决方案">三、分布式Session解决方案</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;<a href="#1关于sticky和non-sticky的区别">1.关于sticky和non-sticky的区别</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;<a href="#2non-sticky模式下MSM的配置">2.non-sticky模式下MSM的配置</a><br/>
## 正文<br/>

## 一、Memcached基本使用

### 1.Memcached 安装

1. 安装依赖 yum install libevent-devel

2. 下载 [memcached](http://memcached.org/downloads) 并上传至linux 服务器对应目录（/usr/local/）

3. 解压  tar -zxvf memcached-1.5.12.tar.gz

4. 编译 ./configure --prefix=/usr/local/memcached  && make  && make test  &&  sudo make install

5. 启动 ./bin/memcached -m 64 -p 11211 -u nobody -vvv

6. memcached 没有自带的连接工具，可以用telnet进行连接测试  yum install -y telnet

7. 连接 telnet 127.0.0.1 11211

8. 测试是否启动成功

   ```shell
   set hello 0 0 5 
   world
   STORED
   get hello
   VALUE hello 0 5
   world
   END
   ```

### 2. Memcached 基本命令

官方文档地址：https://github.com/memcached/memcached/wiki/Commands

| 命令            | 格式                                               | 说明                                  |
| --------------- | -------------------------------------------------- | ------------------------------------- |
| 新增 set        | set  key  flags   exTime  length -> value          | 无论什么情况，都可以插入              |
| 新增 add        | add key  flags   exTime  length -> value           | 只有当key不存在的情况下，才可以插入   |
| 替换 replace    | replace  key  flags   exTime  length -> value      | 只修改已存在key的value值              |
| 追加内容append  | append  key  flags   exTime  length -> value       | length表示追加的长度而不是总长度      |
| 前面追加prepend | prepend  key  flags   exTime  length -> value      | length表示追加的长度而不是总长度      |
| 查询操作 get    | get  key                                           |                                       |
| 检查更新 cas    | cas  key  flags  exTime  length  version  -> value | 版本正确才更新                        |
| 详细获取 gets   | gets   key                                         | 返回的最后一个数代表 key 的 CAS 令牌  |
| 删除 delete     | delete   key                                       | 将数据打一个删除标记                  |
| 自增 incr       | incr  key  增加偏移量                              | incr和decr只能操作能转换为数字的Value |
| 自减 decr       | decr  key  减少偏移量                              | desr不能将数字减少至0以下             |
| 清库            | flush_all                                          |                                       |

**stats：**

1. pid Memcached进程ID
2. uptime Memcached运行时间，单位：秒
3. time Memcached当前的UNIX时间
4. version Memcached的版本号
5. rusage_user 该进程累计的用户时间，单位：秒
6. rusage_system 该进程累计的系统时间，单位：秒
7. curr_connections 当前连接数量
8. total_connections Memcached运行以来接受的连接总数
9. connection_structures Memcached分配的连接结构的数量
10. cmd_get 查询请求总数
11. get_hits 查询成功获取数据的总次数
12. get_misses 查询成功未获取到数据的总次数
13. cmd_set 存储（添加/更新）请求总数
14. bytes Memcached当前存储内容所占用字节数
15. bytes_read Memcached从网络读取到的总字节数
16. bytes_written Memcached向网络发送的总字节数
17. limit_maxbytes Memcached在存储时被允许使用的字节总数
18. curr_items Memcached当前存储的内容数量
19. total_items Memcached启动以来存储过的内容总数
20. evictions LRU释放对象数，用来释放内存



**stats slabs区块统计：**

1. chunk_size chunk大小，byte
2. chunks_per_page 每个page的chunk数量
3. total_pages page数量
4. total_chunks chunk数量*page数量
5. get_hits get命中数
6. cmd_set set数
7. delete_hits delete命中数
8. incr_hits incr命中数
9. decr_hits decr命中数
10. cas_hits cas命中数
11. cas_badval cas数据类型错误数
12. used_chunks 已被分配的chunk数
13. free_chunks 剩余chunk数
14. free_chunks_end 分完page浪费chunk数
15. mem_requested 请求存储的字节数
16. active_slabs slab数量
17. total_malloced 总内存数量



**stats items数据项统计：**

1. number 该slab中对象数，不包含过期对象
2. age LRU队列中最老对象的过期时间
3. evicted LRU释放对象数
4. evicted_nonzero 设置了非0时间的LRU释放对象数
5. evicted_time 最后一次LRU秒数，监控频率
6. outofmemory 不能存储对象次数，使用-M会报错
7. tailrepairs 修复slabs次数
8. reclaimed 使用过期对象空间存储对象次数



**stats settings查看设置：**

1. maxbytes 最大字节数限制，0无限制
2. maxconns 允许最大连接数
3. tcpport TCP端口
4. udpport UDP端口
5. verbosity 日志0=none,1=som,2=lots
6. oldest 最老对象过期时间
7. evictions on/off，是否禁用LRU
8. domain_socket socket的domain
9. umask 创建Socket时的umask
10. growth_factor 增长因子
11. chunk_size key+value+flags大小
12. num_threads 线程数，可以通过-t设置，默认4
13. stat_key_prefix stats分隔符
14. detail_enabled yes/no，显示stats细节信息
15. reqs_per_event 最大IO吞吐量(每event)
16. cas_enabled yes/no，是否启用CAS，-C禁用
17. tcp_backlog TCP监控日志
18. auth_enabled_sasl yes/no，是否启用SASL验证



### 3. Memcached 底层结构

#### 3.1 底层存储结构

<div align="center"> <img src="https://github.com/heibaiying/LearningNotes/blob/master/pictures/memcached%20%E5%AD%98%E5%82%A8%E7%BB%93%E6%9E%84.png"/> </div>

- slab：可以理解成内存容器
- Page：可以理解成小版的内存容器
- chunk: 实际存放缓存数据的基本单位。
  - chunk是预分配大小的，在创建的时候就被固定下来；
  - chunk 默认大小为80byte,相同slab的chunk大小是一样的，不同slab 的chunk 大小按照自增长因子逐步增加；
  - 在实际的分配中，哪怕是内存出现浪费，chunk 的大小不会改变。比如page是450byte，chunk 是 100 byte,那么存在的50byte 大小的空间是被浪费的。



#### 3.2 寻找可用的Chunk的过程

1. 优先在slot中寻找可用的chunk。 slot 可以理解成为memcached的“垃圾桶”，数据过期或者被delete 后，会将对应的 chunk 标识存入slot；

2. 查找空闲的chunk；

3. 如果没有闲置的chunk，会触发 LRU(最近最少使用原则)；

   **注意：不会因为其他Slab空闲，就不触发LRU流程**

   这种情况是这样：如果待储存的数据为70byte，此时存在chunk大小分别为80byte,100byte 的两个slab (slab1,slab2)，slab1 不存在空闲的chunk，但是 slab2 存在空闲的 chunk,此时会触发slab1 的 LRU，而不会使用 slab2。

**详细介绍推荐阅读博客**：[Memcached内存管理模型分析](https://www.cnblogs.com/moyangvip/p/5259700.html)

## 二、Memcached Java 客户端 

推荐使用 **XMemcached** [官方中文文档](https://github.com/killme2008/xmemcached/wiki/Xmemcached-%E4%B8%AD%E6%96%87%E7%94%A8%E6%88%B7%E6%8C%87%E5%8D%97)

## 三、分布式Session解决方案

[memcached-session-manager 方案的GitHub地址](https://github.com/magro/memcached-session-manager) 

### 1.关于sticky和non-sticky的区别

Sticky 模式：tomcat session 为主session， memcached为备 session。Request请求到来时， 从memcached加载备 session到 tomcat (仅当tomcat jvm route发生变化时，否则直接取tomcat session)；Request请求结束时，将tomcat session更新至memcached，以达到主备同步之目的。

Non-Sticky模式：tomcat session 为中转session，memcached1为主 session，memcached2为备session。Request请求到来时，从memcached2加载备 session 到 tomcat，（当容器中还是没有session则从memcached1加载主 session 到 tomcat， 这种情况是只有一个memcached节点，或者有memcached1 出错时），Request请求结束时，将tomcat session更新至主memcached1和备memcached2，并且清除tomcat session 。以达到主备同步之目的。

上面描述摘自博客：https://blog.csdn.net/luckey_zh/article/details/51373724

简单来说：

non-sticky模式：需要session时 ，tomcat 每次都要访问 memcached，这种模式优点是通用性强，但性能不能达到极致；

sticky模式：Tomcat会优先操作本地session，本地session发生变化时候，会回写至memcached

### 2.non-sticky模式下MSM的配置

1. 引入msm相关[依赖包]()

   <div align="center"> <img src="https://github.com/heibaiying/LearningNotes/blob/master/pictures/msm相关依赖.png"/> </div></br>

2. 配置Tomcat/conf/context.xml

   ```xml
   <Context>
   ...
   <Manager className="de.javakaffee.web.msm.MemcachedBackupSessionManager"
   memcachedNodes="n1:192.168.200.228:11211,n2:192.168.200.229:11211"
   sticky="false"
   sessionBackupAsync="false"
   lockingMode="auto"
   requestUriIgnorePattern=".*\.(ico|png|gif|jpg|css|js)$"
   transcoderFactoryClass="de.javakaffee.web.msm.serializer.kryo.KryoTranscoderFactory"
   />
   </Context>
   ```

   **lockingMode**：指定非粘性Session的锁定策略:

   - none:从来不加锁
   - all: 当请求时对Session锁定，直到请求结束
   - auto:对只读的request不加锁，对非只读的request加锁
   - uriPattern:\<regexp>: 使用正则表达式来比较requestRUI + "?" + queryString来决定是否加锁

3. 复制tomcat为多份，修改端口号保证端口号不冲突后启动



