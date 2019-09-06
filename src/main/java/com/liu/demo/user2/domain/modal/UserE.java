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
