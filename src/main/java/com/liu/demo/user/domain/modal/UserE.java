package com.liu.demo.user.domain.modal;

import java.util.Random;

/**
 * @author Liush
 * @description 用户实体类
 * @date 2019/9/5 9:47
 **/
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
