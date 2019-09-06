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
