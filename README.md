# diligentProject
- 这个仓库是用来提交作业的仓库
---
## 项目介绍
- 这个项目是一个动物信息的管理系统，主要功能就是对动物信息进行基本的增删改查。
- 主要包含一下几个模块
  - pojo
    - Animal.java 对动物的基本描述类，包含动物的四个基本属性(id，种类，年龄，性别)和一个存放扩展属性的Map集合。
  - dao
    - AnimalDao.java 一个接口，里面是对动物信息进行增删改查的抽象方法。
    - impl
      - AnimalDaoImpl.java 这是上述接口的实现类，里面实现了对动物信息进行增删改查的所有功能。
  - test
    - Test.java  个测试类，其中写了所有的操作功能以及对增删改查方法的调用，是整个程序的入口。
  - utils
    - IDUtils.java 生成随机id的方法，主要功能就是生成一个5位数的随机的不重复的id。
    - CheckUtils.java 对用户输入的年龄等各种属性进行校验，防止用户随意输入。
    - ImplUtils.java 这个类中是实现增删改查功能的依赖方法，比如对集合进行排序，校验id等许多检查功能方法。
## 总结
- 和上次相比，加入了IO，将动物信息集合写出到了一个文件中。
  - 用到了ObjectInputStream/ObjectOutputStream。
    - 遇到的问题就是在读文件的时候会抛出EOFException异常，还有就是要处理好空指针的问题。
  - 改正了一些不规范的编码陋习。
- 谢谢指出错误！
