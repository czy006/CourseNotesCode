## 1.项目介绍

此项目主要记录在自己日常在学习并发编程上的一些练习题目和题解，代码相应的上传和开源 仅供参考，如有错误欢迎纠正👏
学习来源主要源自b站各大up主提供的一些培训机构和机构放出的免费视频，经过筛选后提炼出来自己整理的代码块

## 2.项目模块说明
```
ConcurrentStudy -- 父项目，公共依赖
│  
│  │─_volatile
│  │─cas CompareAndSwap
│  ├─sync synchronized锁机制 (Todo)
│  │  ├─ threadlock 线程8锁
│  │  ├─ threadsafe 线程安全演示
│  │  ├─ wait 等待机制
│  │  ├─ monitor 管程
│  │  ├─ biased 偏向锁
│  ├─thread 线程基础
│  │  ├─ compare 线程运行对比
│  │  ├─ complete CompletableFuture演示案例
│  │  ├─ createthread 线程的基本创建方式
│  │  ├─ daemon 守护线程
│  │  ├─ exercise 课后练习
│  │  ├─ interrupted 打断通知工作机制
│  │  ├─ join 等待机制
│  ├─threadlocal -- Threadlocal 实际使用
│  ├─threadpool -- 线程池相关案例
│  ├─model -- 线程模式总结
│  ├─util -- Java并发编程工具类使用demo

```