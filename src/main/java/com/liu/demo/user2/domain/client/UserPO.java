package com.liu.demo.user2.domain.client;



/**
 * @author Liush
 * @description User类对外暴露反腐类
 * @date 2019/9/5 11:18
 **/
public class UserPO extends UserDO {

    public UserPO() {
    }

    public UserPO(String userId, String password, String username, String email) {
        super(userId, password, username, email);
    }
}
