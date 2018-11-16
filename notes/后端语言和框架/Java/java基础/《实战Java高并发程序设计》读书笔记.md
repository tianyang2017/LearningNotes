# 《实战Java高并发程序设计》读书笔记

## 第二章 Java并行程序基础

### 2.1 初始线程：线程的基本操作

#### 2.1.1 线程中断

**interrupt**是设置终止标志位，并非真正的去终止线程。用户当线程检测到终止标志位为真时，可以执行对应的终止逻辑。

```java
// Thread 类
public void interrupt()         	// 停止线程
public boolean isInterrupted()    	// 判断是否被中断
public static boolean interrupted() // 判断是否被中断 并清除标志位  
```

```javascript
//不能停止线程
public class Test {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
                while (true) {
                    System.out.println("子线程打印");
              }
        });
        thread.start();
        Thread.sleep(10);
        thread.interrupt();
    }
}

// 能停止线程
public class Test {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    System.out.println("子线程打印");
              }
        });
        thread.start();
        Thread.sleep(10);
        thread.interrupt();
    }
}

// 不能停止线程
public class Test {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (!Thread.interrupted()||!Thread.currentThread().isInterrupted()) {
                    System.out.println("子线程打印");
            }
        });
        thread.start();
        Thread.sleep(10);
        thread.interrupt();
    }
}
```



#### 2.1.2 等待（wait）和通知（notify）

这两个方法在Object 下，意味着任何对象都可以调用这两个方法。

如果是一个线程调用了wait,那么它就会进入object对象的等待队列。notify()**随机唤醒(并非先等待先唤醒，不是公平的唤醒)**等待队列中的一个队列，notifyAll() 唤醒全部等待。

```java
// 正常情况
public class Test {
    private static final Object object = new Object();
    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (object) {
                System.out.println("线程1");
            }
        }).start();
        new Thread(() -> {
            synchronized (object) {
                System.out.println("线程2");
            }
        }).start();
    }
}
//输出：
	 线程1
	 线程2
	
	
// 锁住对象不释放
public class Test {
    private static final Object object = new Object();
    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (object) {
                while (true) {
                    try {
                        Thread.sleep(1000);
                        System.out.println("线程1");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        new Thread(() -> {
            synchronized (object) {
                System.out.println("线程2");
            }
        }).start();
    }
}

// 等待与唤醒
public class Test {
    private static final Object object = new Object();
    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (object) {
                try {
                    System.out.println("对象object等待");
                    object.wait();
                    System.out.println("线程1后续操作");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(() -> {
            synchronized (object) {
                System.out.println("对象object唤醒");
                object.notify();
                System.out.println("线程2后续操作");
            }
        }).start();
    }
}

// 全部唤醒
public class Test {
    private static final Object object = new Object();
    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (object) {
                try {
                    System.out.println("对象objectd在线程1等待");
                    object.wait();
                    System.out.println("线程1后续操作");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(() -> {
            synchronized (object) {
                try {
                    System.out.println("对象object在线程2等待");
                    object.wait();
                    System.out.println("线程2后续操作");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(() -> {
            synchronized (object) {
                System.out.println("对象object唤醒");
                object.notifyAll();    
                //注: 如果是object.notify（）则是随机唤醒任意一个等待
                System.out.println("线程3后续操作");
            }
        }).start();
    }
}
```



#### 2.1.3 等待线程结束（join）和谦让（yield）

```
public static native void yield();
```

yield 会使得当前线程让出CPU,但是在让出后，还会进行CPU资源的争夺，这意味这有可能会再次获得CPU的执行权。

```java
public class Test {
    private static int j=0;
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                j++;
            }
        });
        thread.start();
        System.out.println(j);
    }
}
//结果 0


public class Test {
    private static int j=0;
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                j++;
            }
        });
        thread.start();
        thread.join();
        System.out.println(j);
    }
}
//结果 100000
```

### 2.2 volatile与Java内存模型（JMM）

以下内容摘抄自博客：https://juejin.im/post/5b5727d66fb9a04f834652d6[作者：云起]

一旦一个 共享变量（类的成员变量、类的静态成员变量）被 volatile 修饰之后，那么就具备了两层语义：

**1. 保证了不同线程对这个变量进行读取时的可见性，即一个线程修改了某个变量的值 ，这新值对其他线程来说是立即可见的 。(volatile  解决了线程间共享变量 的可见性问题）**

- 使用 volatile 关键字会强制将修改的值立即写入主存；
- 使用 volatile 关键字的话，当线程 2 进行修改时，会导致线程 1 的量 工作内存中缓存变量 stop  的缓存行无效（反映到硬件层的话，就是 CPU 的 L1或者 L2 缓存中对应的缓存行无效）**；**
- 由于线程 1 的工作内存中缓存变量 stop 的缓存行无效，所以线程 1再次读取变量 stop 的值时 会去主存读取。那么，在线程 2 修改  stop 值时（当然这里包括 2 个操作，修改线程 2 工作内存中的值，然后将修改后的值写入内存），会使得线程 1 的工作内存中缓存变量  stop 的缓存行无效，然后线程 1  读取时，发现自己的缓存行无效，它会等待缓存行对应的主存地址被更新之后，然后去对应的主存读取最新的值。那么线程 1 读取到的就是最新的正确的值。

**2.禁止进行指令重排序 ，阻止编译器对代码的优化 。**

​	volatile 关键字禁止指令重排序有两层意思：

- 当程序执行到 volatile  变量的读操作或者写操作时，在其前面的操作的更改肯定全部已经进行，且 结果已经对后面的操作可见；在其后面的操作肯定还没有进行。

- 在进行指令优化时，不能把 volatile 变量前面的语句放在其后面执行，也不能把 volatile 变量后面的语句放到其前面执行。

  为了实现  volatile 的内存语义，加入 volatile 关键字时，编译器在生成字节码时，会在指令序列中插入内存屏障，会多出一个 lock  前缀指令。内存屏障是一组处理器指令，解决禁止指令重排序和内存可见性的问题。编译器和 CPU  可以在保证输出结果一样的情况下对指令重排序，使性能得到优化。处理器在进行重排序时是会考虑指令之间的数据依赖性。

  内存屏障有两个作用：

- 先于这个内存屏障的指令必须先执行，后于这个内存屏障的指令必须后执行 

- 使得内存可见性。所以， 如果你的字段是 volatile ，在读指令前插入读屏障，可以让高速缓存中的数据失效，重新从主内存加载数据。在写指令之后插入写屏障，能让写入缓存的最新数据写回到主内存。

### 2.3 分门别类的管理 线程组

```java
public class Test {

    static class Task implements Runnable{
        @Override
        public void run() {
            Thread current = Thread.currentThread();
            System.out.println("当前线程id: "+ current.getId()+"当前所属线程组: "+ current.getThreadGroup().getName());
        }
    }

    public static void main(String[] args) {
       ThreadGroup group=new ThreadGroup("java线程组");
        Thread thread1 = new Thread(group, new Task());
        Thread thread2 = new Thread(group, new Task());
        thread1.start();
        thread2.start();
    }
}
```

### 2.4 守护线程（Daemon）

当一个Java应用中只有守护线程的时候，Java虚拟机就会自动退出。

```java
public class Test {

    static class Task implements Runnable{
        @Override
        public void run() {
            Thread current = Thread.currentThread();
            System.out.println("当前线程id: "+ current.getId()+"当前所属线程组: "+ current.getThreadGroup().getName());
        }
    }

    public static void main(String[] args) {
       ThreadGroup group=new ThreadGroup("java线程组");
        Thread thread1 = new Thread(group, new Task());
        Thread thread2 = new Thread(group, new Task());
        Thread thread3 = new Thread(group, new Task());
        thread1.setDaemon(true);
        thread2.setDaemon(true);
        thread3.setDaemon(true);
        thread1.start();  // 不会执行
        thread2.start();  // 不会执行
        thread3.start();  // 不会执行
    }
}
```

### 2.5 线程安全的概念和synchronized 

**1. volatile 和 synchronized 的区别**

- volatile 是变量修饰符，而 synchronized 则作用于代码块或方法。
- volatile 不会对变量加锁，不会造成线程的阻塞；synchronized 会对变量加锁，可能会造成线程的阻塞。
- volatile 仅能实现变量的修改可见性，并不能保证原子性；而synchronized 则 可 以 保 证 变 量 的 修 改 可 见 性 和  原 子 性 。（synchronized  有两个重要含义：它确保了一次只有一个线程可以执行代码的受保护部分（互斥），而且它确保了一个线程更改的数据对于其它线程是可见的（更改的可见性），在释放锁之前会将对变量的修改刷新到主存中）。
- volatile 标记的变量不会被编译器优化，禁止指令重排序；synchronized 标记的变量可以被编译器优化。

```java
// 线程不安全
public class Test {

    private static int i=0;
    
    public static void main(String[] args) throws InterruptedException {
        Thread thread1= new Thread(new IncreaseTask());
        Thread thread2= new Thread(new IncreaseTask());
        thread1.start();
        thread2.start();
        //等待结束后 才打印返回值
        thread1.join();
        thread2.join();
        //并打印返回值
        System.out.println(i);
    }

    static class IncreaseTask implements Runnable{
        @Override
        public void run() {
            for (int j = 0; j < 100000; j++) {
                inc();
            }
        }
        private void inc() {
            i++;
        }
    }
}


// 虽然用了synchronized，线程还是不安全
public class Test {

    private static int i=0;

    public static void main(String[] args) throws InterruptedException {
        Thread thread1= new Thread(new IncreaseTask());
        Thread thread2= new Thread(new IncreaseTask());
        thread1.start();
        thread2.start();
        //等待结束后 才打印返回值
        thread1.join();
        thread2.join();
        //并打印返回值
        System.out.println(i);
    }

    static class IncreaseTask implements Runnable{
        @Override
        public void run() {
            for (int j = 0; j < 100000; j++) {
                inc();
            }
        }
        private synchronized void inc() {   //synchronized 两个线程启动的不是同一个IncreaseTask实例
            i++;						    // inc() 是实例方法 所以两个线程锁住的是两个方法对象											
        }
    }
}
```

```java
// 线程安全解决办法一 ：两个线程启动同一个实例
public class Test {

    private static int i=0;

    public static void main(String[] args) throws InterruptedException {
        IncreaseTask task = new IncreaseTask();   
        Thread thread1= new Thread(task);     // 两个线程启动的是同一个实例
        Thread thread2= new Thread(task);
        thread1.start();
        thread2.start();
        //等待结束后 才打印返回值
        thread1.join();
        thread2.join();
        //并打印返回值
        System.out.println(i);
    }
    static class IncreaseTask implements Runnable{
        @Override
        public void run() {
            for (int j = 0; j < 100000; j++) {
                inc();
            }
        }
        private synchronized void inc() {     // 由同一个实例对象产生的实例方法
            i++;
        }
    }
}


// 线程安全解决办法二: 将方法声明为static 类方法
public class Test {

    private static int i=0;

    public static void main(String[] args) throws InterruptedException {
        Thread thread1= new Thread(new IncreaseTask());
        Thread thread2= new Thread(new IncreaseTask());
        thread1.start();
        thread2.start();
        //等待结束后 才打印返回值
        thread1.join();
        thread2.join();
        //并打印返回值
        System.out.println(i);
    }

    static class IncreaseTask implements Runnable{
        @Override
        public void run() {
            for (int j = 0; j < 100000; j++) {
                inc();
            }
        }
        private static synchronized void inc() {  //这是一个类方法
            i++;
        }
    }
}

// 线程安全实现方法三   利用 synchronized 锁住 同一个对象
public class Test {

    private static final String s="";

    private static int i=0;

    static class IncreaseTask implements Runnable{
        @Override
        public void run() {
            for (int j = 0; j < 100000; j++) {
                synchronized (s){
                    i++;
                }
            }
        }
        
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread1= new Thread(new IncreaseTask());
        Thread thread2= new Thread(new IncreaseTask());
        thread1.start();
        thread2.start();
        //等待结束后 才打印返回值
        thread1.join();
        thread2.join();
        //并打印返回值
        System.out.println(i);
    }
}
```



## 第三章 JDK 并发包

