# 《Java核心编程》（卷一）读书笔记

## 第九章 集合

### 9.1 Java集合框架

#### 9.1.1 java 集合框架

![java集合类图](D:\学习笔记\picture\java集合类图.png)</br>![java集合类图](https://github.com/heibaiying/LearningNotes/blob/master/picture/java集合类图.png)

**AbsctractCollection 提供了部分通用方法的实现。**

​                                                                         ![Collection](D:\学习笔记\picture\Collection.png)</br>![Collection](https://github.com/heibaiying/LearningNotes/blob/master/picture/Collection.png)

**ava.util.Coll6ction\<E>**  :

```java
• Iterator <E> iterator()
返回一个用于访问集合中每个元素的迭代器。
• int size()
返回当前存储在集合中的元素个数。
• boolean isEmpty()
如果集合中没有元素， 返回 true。
• boolean contains(Object obj)
如果集合中包含了一个与 obj 相等的对象， 返回 true。
• boolean containsAl 1(Collection<?> other)
如果这个集合包含 other 集合中的所有元素， 返回 trueo
• boolean add(Object element)
将一个元素添加到集合中。如果由于这个调用改变了集合，返回 true。
• boolean addAl 1(Col 1 ection<? extends E> other)
将 other 集合中的所有元素添加到这个集合。如果由于这个调用改变了集合， 返回 true。
• boolean remove(Object obj)
从这个集合中删除等于 obj 的对象。 如果有匹配的对象被删除， 返回 true。
• boolean removeAl 1(Col 1ection<?> other)
从这个集合中删除 other 集合中存在的所有元素。如果由于这个调用改变了集合，返回 true 。
• default boolean removelf(Predicate<? super E> filter)8
从这个集合删除 filter 返回 true 的所有元素。如果由于这个调用改变了集合， 则返回 true。
• void clear()
从这个集合中删除所有的元素。
• boolean retainAl 1(Collection<?> other)
从这个集合中删除所有与 other 集合中的元素不同的元素。 如果由于这个调用改变了
集合， 返回 true。
• Object[]toArray()
返回这个集合的对象数组。
• <T> T[]toArray(T[] arrayToFi11)
返回这个集合的对象数组。 如果 arrayToFill 足够大， 就将集合中的元素填入这个数组中。剩余空间填补 null ; 否则， 分配一个新数组， 其成员类型与 arrayToFill 的成员类型相同， 其长度等于集合的大小， 并填充集合元素。
```



