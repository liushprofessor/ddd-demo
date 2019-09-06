package com.liu.demo.user.domain.modal;

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
