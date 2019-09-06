---
title: 小型项目领域驱动设计实践
date: 2019-09-05 19:09:41
categories: 领域驱动设计(DDD)
tags: [领域驱动设计]
---
#### 前言
>前面已经简介过领域驱动的基本概念，前文介绍的COLA框架在大型项目或者微服务架构中目测有较好的实践，但是对于一个中小项目或者小公司来说管理大量依赖包模块简直就是噩梦，或者就是项目达不到那种规模，采用分包模式也是一种浪费，但是采用领域驱动设计在本人实践过程中确实大大提升了代码质量，最主要的改善就是使开发人员不再以数据库驱动开发，而是真正的开始从业务和领域入手，这样开发出的代码往往能更好的实现面向对象，将代码划分出边界，使代码的可读性更强，代码更加健壮。本文结合现实中使用领域驱动设计时遇到的问题进行了总结，如果错误还需海涵。


#### 项目说明
  本项目主要有3个大包分别包含3次不同的实践，实践的具体内容如全文所述
  * user 为第一次实践包含各种模型设计和简介
  * user2 正对user包下存在的一些问题做了一些优化设计，请查看全文来看具体说明
  * project 为了针对实际项目中出现的大聚合来做的一些设计，这个包下只建立了模型设计，其余部分如果感兴趣可以自己补充(其实就是本人偷懒)

  使用方法如果你使用的是mysql数据库那么修改application.properties中的数据源即可，liquibase会自动将所需要的表建立完毕

#### 分包

>和COLA框架采用模块不同，我采用一个项目下分不同的包的模式来区分领域设计的各个模块项目结构如下   

    demo
    └─com
        └─liu
           └─demo
              ├─app            客户端服务代码
              ├─controller     控制层代码
              ├─domain         领域层
              │  ├─client        领域层防腐对象
              │  └─modal         领域模型
              └─infrastructure 基础层
                 ├─repository    仓库
                 │  └─mapper     mybatis持久包
                 └─serviceimpl   领域服务包



 * app包：客户端代码存放的地方，负责组装调用领域模型，仓库，控制事务，对应六边形架构的应用服务层

 * controller包: 控制层代码，我用SpringMVC实现，对应六边形架构的输入适配器

 * domain包: 为项目中最核心的领域模型相关类存放的地方，对应六边形架构的领域(domain)层,**另外在此根目录下会存放领域服务的接口，该接口由基础设施层去实现，因为领域层是最核心的层，根据六边形架构领域层需要放在最里层，但是领域服务却有需求调用基础设施层(infrastructure)下的仓库(repository),因此在这个层中定义一个接口由infrastructure层去实现，实现依赖倒置。**

 * client包: 我创建此包是为了反腐，为了不使领域模型外泄，有效的控制代码的边界访问而设立，举例在http协议调用中dto对象从controller层到app层，当要进入到领域层（domain）时必须将其转化成领域模型，同样数据持久化在数据库中，从数据库中直接查找到的数据对象和领域对象同样存在差异，因此需要对外创建一个过渡对象提供给基础设施层调用，**也许很多人会对这些对象放在domain层有疑问，但是我认为外部数据的访问领域对象数据的范围和权限是由领域模型去控制的，因此我觉得将其放在领域(domain)包中和适合的。**

 * modal此包主要存放实体(Entity)，值对象(VO),生成领域模型的工厂方法,领域对象验证类.

 * infrastructure基础设施层：主要存放基础设施的地方，比如数据库持久化，调用外部服务，队列等

 * repository仓库，对持久化的抽象，屏蔽数据库对象生成领域对象，领域对象从创建开始就已经开始生命周期，一直到删除才结束，中间会把领域对象存储在数据库中，存储在数据库时领域对象仍然处于生命周期，因此仓库层的作用就是屏蔽持久层，让调用者觉得领域对象一直存在内存中一样.

 * mapper 由于我使用的是mybatis，所以我创建此层建mybatis的类放在此

 * serviceimpl 领域服务包，同样有很多人可能会有疑问为什么我讲领域服务的实现类放在基础设施层中，这一点我上面提过，为了实现依赖导致，只要是领域服务的接口存放在领域层(domain)那么我们仍然认为领域服务属于领域层，因为接口规定了领域服务的功能和方法。


 #### 建模
  >在这里我们假设我们和业务方沟通需要实现这样的功能,用户可以有自己的基础信息，这些信息包括用户名，email地址，且用户可以根据用户id和密码登录系统，且用户可以单独修改登录密码，也可以修改用户信息,根据需求分析我们可以得出用户有一个唯一型标识用户id，因此我们得出用户是实体，用户名和email这两个属性对用户来说并不需要维护状态的变化，修改时候为了简单将其整个对象替换即可，因此我们将其设计成值对象VO,由于用户可以单独修改密码因此修改密码对应前端一个单独入口，所有我们将密码这个属性放在用户对象中，因此我们得到以下模型,实体对象UserE中有一个修改用户的方法，只有一个构造方法，并且可以进行密码验证和获取用户基础信息，注意这里并没有set方法，而是用了类似changePassword等方法名代替set方法，这是为了使领域模型充血，为了使模型更好的体现业务，如果使用set修改密码的话，那我们怎么和业务人员解释修改密码这个方法？难道说我set了密码？这明显无法表示出领域对象的意图，反之将其命名changePassword修改密码那么就可以很好的表示出领域模型的意图，**领域方法名需要表示出领域和业务的意图。**

  ```
  public class UserE  {

    private String userId;

    private String password;

    private BaseInfoVO baseInfo ;

    /**
     * 修改用户密码
     */
    public void changePassword(String password){
         if(password==null){
             throw new IllegalArgumentException("密码不能为空");
         }
         this.password=password;

    }

    public UserE(String userId, String password, BaseInfoVO baseInfo) {
        this.userId = userId;
        this.password = password;
        this.baseInfo = baseInfo;
    }

    public String getUserId() {
        return userId;
    }


    public String getPassword() {

        return this.password;

    }

    /**
     * 认证服务，查询传入密码是否匹配
     * @param password 需要认证的密码
     * @return 认证结果
     */
    public boolean authentication(String password) {
        return password != null && password.equals(this.password);
    }


    public BaseInfoVO getBaseInfo() {
        return baseInfo;
    }


    /**
     * 修改用户基础信息
     */
    public void changeInfo(BaseInfoVO baseInfoVO){
            this.baseInfo=baseInfoVO;
    }

}

  ```
下面是BaseInfoVO为用户的基础信息,同样我们也没有暴露set方法,由于它只是一个用户的值对象，因此并没有那么多的领域方法,至此我们的核心领域对象就已经建立完成了
```
/**
 * @author Liush
 * @description 用户基础信息
 * @date 2019/9/5 9:48
 **/
public class BaseInfoVO {

    private String username;

    private String email;

    public BaseInfoVO(String username, String email) {
        if(username==null){
            throw new IllegalArgumentException("用户名不能为空");
        }
        if(email==null){
            throw new IllegalArgumentException("邮箱不能为空");
        }
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}

```
现在让我们考虑如何新增一个用户，创建用户对应领域模型就是创建一个用户实体(UserE),那我们如何做到将领域层的模型信息不外泄到其它地方呢?因为新增用户也属于领域（业务的一部分），举个例子我们去银行开个户也要到银行才能办理，我们不能到公安局去开银行账户，所以我们把创建用户对象放在领域层，而创建用户实体(UserE)有两种方法，一种是直接调用其构造，一种是通过工厂类来创建，但是这里又会出现一个问题，用户实体(UserE)需要一个BaseInfoVO（基础信息）来构造，但是按照领域驱动设计的理念来设计BaseInfoVO（基础信息）只能有领域在领域层中才能去创建，因为我们的通用语言是用户创建和修改了基础信息，如果我们将BaseInfoVO（基础信息）放在领域层外创建就好比一句话少了主语。  
我采用在领域层中使用工厂类去创建用户实体(UserE)，在工厂方法中传入一个DTO来隔离领域层外部的信息，代码如下,其创建了一个用户实体
(UserE)并且使用UUID分配了一个默认的用户ID给用户，最后调用用户实体(UserE)的构造方法去创建用户实体对象，执行完这一行代码，一个用户对象就已经进入了生命周期，直到在数据库中删除或者将用户状态改成不可能用这个用户的生命周期才结束。
```

package com.liu.demo.user.domain.modal;
import com.liu.demo.user.domain.client.UserDTO;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author Liush
 * @description 领域工厂类
 * @date 2019/9/5 14:10
 **/
@Component
public class UserFactory {

     public UserE createUser(UserDTO userDTO ){
         BaseInfoVO baseInfoVO=new BaseInfoVO(userDTO.getUsername(),userDTO.getEmail());
         return new UserE(UUID.randomUUID().toString(),userDTO.getPassword(),baseInfoVO);
     }

}

```

#### 持久化
现在是时候考虑用户对象持久化的问题了，毕竟用户对象不能永远存留在内存中，必须在不使用对象时将其持久化到硬盘中基础设施层包infrastructure下的repository就是为了解决这个问题,它的作用是屏蔽数据库持久化的一些代码，让代码看起来更贴近领域设计一些，我们可以从仓库中根据查找条件直接还原出一个用户实体对象，对领域代码来说数据库持久化代码就好像不存在一样，下面是用户仓库代码,**这里注意一下一个方法findUsersByName，这是一个查询方法，从数据库中查询出UserPO然后将其转成UserDTO，这里我们看到我们并没有走领域模型，因为查询往往为了效率特别是批量查询我们做了一部分妥协，但是这部分妥协是可以接受的，因为我们并没有执行领域动作(command)的代码，只是返回一个dto对象给前端。**
```
package com.liu.demo.user.infrastructure.repository;

import com.liu.demo.user.domain.client.UserDTO;
import com.liu.demo.user.domain.client.UserPO;
import com.liu.demo.user.domain.modal.UserE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.liu.demo.user.infrastructure.repository.mapper.UserMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Liush
 * @description 用户仓库
 * @date 2019/9/5 11:17
 **/
@Repository
public class UserRepository {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepositoryConvert userRepositoryConvert;

    /**
     *  根据用户id查找用户
     */
    public UserE findUser(String userId){
        UserPO userPO =userMapper.findUser(userId);
        return userRepositoryConvert.convertToUserE(userPO);
    }


    /**
     * 添加用户
     */
    public void addUser(UserE user){
        UserPO userPO= userRepositoryConvert.convertToUserPO(user);
        userMapper.insertUser(userPO);

    }

    /**
     * 修改密码
     */
    public void changePassword(UserE userE){
        userMapper.updateUserPassword(userE.getUserId(),userE.getPassword());

    }

    /**
     *根据用户姓名批量查询，查询可以不走领域模型
     */
    public List<UserDTO> findUsersByName(String name){
        List<UserPO> userPOs=userMapper.findUsersByName(name);
        List<UserDTO> userDTOs=new ArrayList<>();
        if (userPOs==null){
            return userDTOs;
        }
        userPOs.forEach(userPO->userDTOs.add(userRepositoryConvert.convertToUserDTO(userPO)));
        return  userDTOs;

    }



}


```

#### 领域服务
**下面考虑一下这个问题，修改密码，我们在修改密码时一般都会调用远程接口，比如获取短信验证码和校验验证码，这部分放在用户实体中是不合适的，但是远程调用短信接口这部分代码又属于基础设施层的内容，但是在提供给app层调用的时候我们又不想把这部分属于业务逻辑暴露给app层，因为由app层去组装的话，那么开发app层的人员就需要知道业务的流程，他必须知道修改密码内部的流程走向（调用短信验证接口），我们想做的就是客户端开发人员只要调用一个修改密码的方法就好了，至于里面执行什么业务逻辑客户端开发人员不要操心，所以我们采用领域服务去封装修改密码的业务，由客户端开发人员去调用领域服务来屏蔽业务的细节，那么现在就会产生这样一个问题，领域层要依赖基础设施层，但是这样是有悖于六边形架构的（领域层应该放在依赖的最内部），于是我们使用依赖倒置技术，在领域层中创建一个领域服务接口，由基础设施去实现，这样就实现了基础设施层依赖领域层，但是领域层又通过接口对基础设施层领域服务可以做什么做了规定和约束。下面是领域层中的领域服务接口的定义，它提供了一个修改密码的接口**
```
package com.liu.demo.user.domain;

/**
 * @author Liush
 * @description 用户领域服务
 * @date 2019/9/5 11:07
 **/
public interface UserServiceI {

    void changePassword(String userId,String password);

}

```
下面由基础设施层去实现这个接口
```
package com.liu.demo.user.infrastructure.serviceimpl;

import com.liu.demo.user.domain.UserServiceI;
import com.liu.demo.user.domain.modal.UserE;
import com.liu.demo.user.infrastructure.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author Liush
* @description User领域服务
* @date 2019/9/5 11:12
**/
@Service
public class UserServiceImpl implements UserServiceI {

  @Autowired
  private UserRepository userRepository;

  @Override
  public void changePassword(String userId, String password) {
      //执行短信验证代码这边省略
      UserE user =userRepository.findUser(userId);
      user.changePassword(password);
      userRepository.changePassword(user);
  }
}

```

现在由APP层去调用修改密码
```
public void changePassword(String userId,String password){
      userServiceI.changePassword(userId,password);
  }
```

#### 关于client二方包的思考
在我最开始接触领域设计的时候这些二方包我是没有放在领域模型层里的，当时我会创建一个common包，把这些东西放在common包下，当时随着理解的深入，我认为领域驱动最核心的内容之一就是边界的划分，边界的划分就以为着可以由不同开发者去开发不同的模块(比如一个人去开发领域模型，一个人去开发app层负责组装),如果我们把写代码放在common包中，那么app层开发人员就要去建立自己的领域防腐模型(DTO,PO等)，这样一定程度上也将领域模型的内容泄露出去，与其让客户端开发人员去编写防腐代码，比如有领域层开发人员去设计这部分代码，规定领域层的输入和输出，当然还包括一些领域层和防腐层对象的装换比如示例代码中提供的UserConvert对象装换类其实现如下,这样即让领域层代码不外泄，也很好的把控客户端可以访问什么属性。
```
package com.liu.demo.user.domain.client;

import com.liu.demo.user.domain.modal.UserE;
import org.springframework.stereotype.Component;

/**
 * @author Liush
 * @description 转换类
 * @date 2019/9/5 14:52
 **/
@Component
public class UserConvert {

     public UserDTO convertToUserDTO(UserE userE){
         String username=userE.getBaseInfo().getUsername();
         String email=userE.getBaseInfo().getEmail();
         return new UserDTO(userE.getUserId(),userE.getPassword(),username,email);
     }

}

```

#### 更进一步
上诉代码在一般小项目中其实也已经够用，但是眼尖的同学可能会发现存在两个问题
* 用户实体(UserE)中的changePassword方法是暴露给客户端的，客户端人员在APP层可以直接获取UserE对象然后绕过领域服务去修改密码，这样就不要去验证短信服务等接口了，这样做明显是不符合业务逻辑的。

* 实体的整体验证，上面代码用户实体(UserE)只对单个属性进行验证比如属性是否为空，但是有些实体对象存在整体验证，比如用户实体(UserE)中如果业务规定密码和邮箱都必须以数字开头怎么办？

这一节将着手解决这个问题，这部分代码在user2包下

首先我们解决第一个问题，我们创建了一个用户抽象类,这个抽象类并没有修改密码的方法，它只暴露了客户端可以调用的代码
```
package com.liu.demo.user2.domain.modal;


import com.liu.demo.user2.common.DoNothingValidateHandler;
import com.liu.demo.user2.common.ValidateHandlerI;

/**
 * @author Liush
 * @description
 * @date 2019/9/5 15:27
 **/
public abstract class UserAbstract {

    protected String userId;

    protected String password;

    protected BaseInfoVO baseInfo ;

    protected ValidateHandlerI validateHandlerI;

    public UserAbstract(String userId, String password, BaseInfoVO baseInfo) {
        this.userId = userId;
        this.password = password;
        this.baseInfo = baseInfo;
        this.validateHandlerI=new DoNothingValidateHandler();
    }

    public abstract boolean authentication(String password);

    public abstract BaseInfoVO getBaseInfo();

    public abstract String getUserId();


}

```
然后我们实现这个类，子类中包行了changePassword方法，
```
package com.liu.demo.user2.domain.modal;

/**
 * @author Liush
 * @description 用户实体类
 * @date 2019/9/5 9:47
 **/
public class UserE extends UserAbstract {


    public void changePassword(String password){
         if(password==null){
             validateHandlerI.handlerError("密码不能为空",new IllegalArgumentException());
         }
         this.password=password;

    }

    /**
     * 构造完实体后对实体进行整体验证
     */
    public UserE(String userId, String password, BaseInfoVO baseInfo) {
        super(userId,password,baseInfo);
        new UserValidate(this).validate();
    }

    public String getUserId() {
        return super.userId;
    }



    public String getPassword() {
        return super.password;

    }

    /**
     * 认证服务，查询传入密码是否匹配
     * @param password 需要认证的密码
     * @return 认证结果
     */
    public boolean authentication(String password) {
        return password != null && password.equals(this.password);
    }


    public BaseInfoVO getBaseInfo() {
        return baseInfo;
    }
}

```
现在我们在所有返回给客户端代码中的返回用户实体对象不再是UserE而是UserAbstract,如repository包下的repository对象，这样客户端就不能直接通过用户实体去修改密码，而不需通过领域服务去修改密码，而在对应的领域层代码或者基础设施代码中完成一次对象的装换即可（将UserE转成UserAbstract）
```
package com.liu.demo.user2.infrastructure.repository;


import com.liu.demo.user2.domain.client.UserPO;
import com.liu.demo.user2.domain.modal.UserE;
import com.liu.demo.user2.domain.modal.UserAbstract;
import com.liu.demo.user2.infrastructure.repository.mapper.UserMapper2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


/**
 * @author Liush
 * @description 用户仓库
 * @date 2019/9/5 11:17
 **/
@Repository
public class UserRepository2 {

    @Autowired
    private UserMapper2 userMapper;

    @Autowired
    private UserRepositoryConvert2 userRepositoryConvert;

    public UserAbstract findUser(String userId){
        UserPO userPO =userMapper.findUser(userId);
        return userRepositoryConvert.convertToUserE(userPO);
    }


    public void addUser(UserAbstract user){
        UserPO userPO= userRepositoryConvert.convertToUserPO((UserE) user);
        userMapper.insertUser(userPO);

    }


}

```

**第二个问题解决方法**
这次我们定义了一个common包下面有两个基类ValidateAbstract（验证抽象类）,此类为实体整体验证的一个基类，其构成方法中需要传入一个验证处理器，这个处理器的作用是为了如过验证类整体验证失败则调用验证处理器完成错误信息处理，这样做的目的是为了可以将验证和异常处理做解耦，同样在没有参数的构造方法中体用了一个默认的验证处理器
```
package com.liu.demo.user2.common;

/**
 * @author Liush
 * @description 验证抽象类
 * @date 2019/9/5 17:11
 **/
public abstract class ValidateAbstract {

    protected ValidateHandlerI validateHandlerI;


    public ValidateAbstract(ValidateHandlerI validateHandlerI) {
        this.validateHandlerI = validateHandlerI;
    }

    public ValidateAbstract() {
        this.validateHandlerI=ValidateHandlerFactory.doNothingValidateHandler();
    }

    public abstract void validate();

    public void setValidateHandlerI(ValidateHandlerI validateHandlerI) {
        this.validateHandlerI = validateHandlerI;
    }
}

```
用户实体整体验证类实现
```
package com.liu.demo.user2.domain.modal;

import com.liu.demo.user2.common.ValidateAbstract;
import com.liu.demo.user2.common.ValidateHandlerI;
import org.springframework.util.StringUtils;

/**
 * @author Liush
 * @description
 * @date 2019/9/5 17:24
 **/
public class UserValidate extends ValidateAbstract {

    protected UserE userE;


    public UserValidate(UserE userE) {
        super();
        this.userE=userE;
    }


    public UserValidate(UserE userE,ValidateHandlerI validateHandlerI) {
        super(validateHandlerI);
        this.userE=userE;
    }

    @Override
    public void validate() {
        if(StringUtils.isEmpty(userE.getPassword()) && StringUtils.isEmpty(userE.getBaseInfo().getUsername())){
            super.validateHandlerI.handlerError("密码和用户名不能同时为空",new  RuntimeException("UserE对象整体验证失败"));

        }
    }
}

```

验证处理器
```
package com.liu.demo.user2.common;

/**
 * @author Liush
 * @description  验证错误处理器
 * @date 2019/9/5 17:03
 **/
public interface ValidateHandlerI {

    void handlerError(String message,Exception e);

}

```

一个默认实现的验证处理器
```
package com.liu.demo.user2.common;

/**
 * @author Liush
 * @description
 * @date 2019/9/5 17:05
 **/
public class DoNothingValidateHandler implements ValidateHandlerI {


    @Override
    public void handlerError(String message,Exception e) {
        System.out.println(message);

        if(e instanceof IllegalArgumentException){
            throw new IllegalArgumentException(message);
        }

        if(e instanceof RuntimeException){
            throw new RuntimeException(message);
        }
    }
}

```



下面我们进行代码整合，你会发现现在再用户实体(UserE)的构造中最后多了一行代码
```
/**
  * 构造完实体后对实体进行整体验证
  */
 public UserE(String userId, String password, BaseInfoVO baseInfo) {
     super(userId,password,baseInfo);
     new UserValidate(this).validate();
 }

```
在创建用户实体(UserE)时会进行整体验证，如果不通过就抛出异常  
此处这两个问题解决完毕


#### 大聚合对象
在实际实践过程中遇到过一些大聚合对象，什么是大聚合对象？举个例子，现在有这么一个业务，一个软件项目，下面有成败上千的子项目，子项目都必须在父项目中创建，如果我们按照原始的设计那么代码就会变这样,这样做有什么不妥呢？比如我们以后想查看工程的名字或者修改，那么我们就必须加载整个工程的子项目，如果子项目较小，这样设计也是可以接受的，但是如果子项目有成千上万个，那么这样做有些浪费资源，而且我只是单纯的修改工程名，和子项目并没有什么关联
```
package com.liu.demo.project.domain.modal;

import java.util.Date;
import java.util.List;

/**
 * @author Liush
 * @description 项目旧模型
 * @date 2019/9/6 10:59
 **/
public class ProjectEOld {

    //项目ID
    private String projectId;

    //项目名
    private String name;

    //项目开始日期
    private Date beginDate;

    //子项目
    private List<ItemE> items;


    public ProjectEOld(String projectId, String name, Date beginDate, List<ItemE> items) {
        this.projectId = projectId;
        this.name = name;
        this.beginDate = beginDate;
        this.items = items;
    }

    //创建子项目
    public void createItem(ItemE itemE){

        items.add(itemE);

    }

    public String getProjectId() {
        return projectId;
    }

    public String getName() {
        return name;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public List<ItemE> getItems() {
        return items;
    }
}

```
于是我们退而求次改建模型,我们现在创建了两个模型ProjectE，ItemE我们现在将这两个实体分开，ProjectE不再包含ItemE集合，在ItemE我们加入了一个属性projectId和父工程做关联，需要注意的是为了凸显领域和我们的模型是对应的我们在ProjectE中有一个createItem的方法，这个方法符合领域描述子项目是在工程中创建的，也许你们有疑问这样在视图展示方法是不是很不方便？（比如我要一次查找工程名字和工程下面所有的子项目列表），这一点我在上文已经说过，在领域设计中查询和命令是可以做分离的，查询设计可以不走领域模型.
```
package com.liu.demo.project.domain.modal;

import java.util.Date;

/**
 * @author Liush
 * @description 工程实体
 * @date 2019/9/6 10:49
 **/
public class ProjectE {

    //项目ID
    private String projectId;

    //项目名
    private String name;

    //项目开始日期
    private Date beginDate;


    /**
     *由于工程子项目属于工程，按照通用语言，工程子项目要由工程去穿件，这样保证了领域和业务模型的统一性
     */
    public ItemE createItem(String itemId,String name){
        return new ItemE(itemId,name);
    }


    public ProjectE(String projectId, String name, Date beginDate) {
        this.projectId = projectId;
        this.name = name;
        this.beginDate = beginDate;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getName() {
        return name;
    }

    public Date getBeginDate() {
        return beginDate;
    }
}

```
子项目模型
```
package com.liu.demo.project.domain.modal;

/**
 * @author Liush
 * @description 项目实体
 * @date 2019/9/6 10:49
 **/
public class ItemE {

    //工程id
    private String projectId;

    //项目实体
    private String itemId;

    //项目名
    private String name;

    public ItemE(String itemId, String name) {
        this.itemId = itemId;
        this.name = name;
    }

    public String getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public String getProjectId() {
        return projectId;
    }
}

```


#### 总结
使用了领域驱动设计是一个长期过程，随着业务的变化原来的模型可能不再适用，或者有一天觉得原来的模型并不合理，这些都是非常常见的现象，领域驱动设计的另一个核心就是不断重构来创造出合适的模型，没有什么所谓银弹，只有不断摸着石头过河才能构建出好的设计。  
最后说一点在现实生活中肯定会有一些设计和需求的矛盾，比如客户坚持要批量新增而且又要快速响应怎么办？领域设计可能会影响一部分效率，但是什么才是慢？我觉得的在合理时间内就不算是慢，比如采用过程化开发调用一个接口的响应速度是1秒，采用领域驱动设计是1.1秒那我觉得这个慢是可接受的，并不影响用户体验，但是这0.1秒换来的是代码设设计的清晰，我觉得这是一笔稳赚不赔的买卖，最后再次声明，没有所谓的银弹，世界上没有完美的事，代码一样，人生也一样，不要和甲方爸爸过不去，因为他是你的爸爸。
