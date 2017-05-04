# FragmentStack
一个单Activity多Fragment的框架

### 简单用法:
Activity继承RootActivity
```java
public class MainActivity extends RootActivity{
  ...
}
```
Fragment继承RootFragment
```java
public class FragmentA extends RootFragment{
  ...
}

public class FragmentB extends RootFragment{
  ...
}
```

在一个Activity或者Fragment中启动另一个Fragment
```java
startFragment(FragmentB.class);
```

### 功能：
* 包括四种启动模式，Standard、SingleTop、SingleTask、SingleInstance，SingleTop、SingleTask、SingleInstance有onNewIntent()方法。
* 通过FragmentIntent传递参数
* startFragmentForResult()方法可以达到类似startActivityForResult()的效果，不过貌似还有坑，可以用EventBus代替
* 被系统回收时，保存数据，重建时恢复数据
