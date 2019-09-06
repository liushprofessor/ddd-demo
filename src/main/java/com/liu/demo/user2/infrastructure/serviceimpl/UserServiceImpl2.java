package com.liu.demo.user2.infrastructure.serviceimpl;


import com.liu.demo.user2.domain.UserServiceI2;
import com.liu.demo.user2.domain.modal.UserE;

import com.liu.demo.user2.infrastructure.repository.UserRepository2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Liush
 * @description User领域服务
 * @date 2019/9/5 11:12
 **/
@Service
public class UserServiceImpl2 implements UserServiceI2 {

    @Autowired
    private UserRepository2 userRepository;

    @Override
    public void changePassword(String userId, String password) {
        UserE user =(UserE) userRepository.findUser(userId);
        user.changePassword(password);
    }
}
