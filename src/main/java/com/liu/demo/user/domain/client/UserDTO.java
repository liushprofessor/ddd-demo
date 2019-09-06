package com.liu.demo.user.domain.client;

/**
 * @author Liush
 * @description
 * @date 2019/9/5 14:11
 **/
public class UserDTO extends UserDO{

    public UserDTO() {
    }

    public UserDTO(String userId, String password, String username, String email) {
        super(userId, password, username, email);
    }
}
