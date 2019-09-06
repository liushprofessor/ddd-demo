package com.liu.demo.user.domain.client;

/**
 * @author Liush
 * @description
 * @date 2019/9/5 14:14
 **/
public class UserDO {
    private String userId;

    private String password;

    private String username;

    private String email;


    public UserDO() {
    }

    public UserDO(String userId, String password, String username, String email) {
        this.userId = userId;
        this.password = password;
        this.username = username;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
