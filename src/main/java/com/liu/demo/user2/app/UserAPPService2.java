package com.liu.demo.user2.app;

import com.liu.demo.user2.domain.UserServiceI2;
import com.liu.demo.user2.domain.client.UserConvert2;
import com.liu.demo.user2.domain.client.UserDTO;
import com.liu.demo.user2.domain.modal.UserAbstract;
import com.liu.demo.user2.domain.modal.UserE;
import com.liu.demo.user2.domain.modal.UserFactory2;
import com.liu.demo.user2.infrastructure.repository.UserRepository2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Liush
 * @description  用户APP层服务
 * @date 2019/9/5 14:25
 **/
@Service
public class UserAPPService2 {

    @Autowired
    private UserFactory2 userFactory;

    @Autowired
    private UserConvert2 userConvert;

    @Autowired
    private UserRepository2 userRepository;

    @Autowired
    private UserServiceI2 userServiceI2;


    public void addUser(UserDTO userDTO){
        UserAbstract user=userFactory.createUser(userDTO);
        userRepository.addUser(user);
    }

    public void changePassword(String userId,String password){
        userServiceI2.changePassword(userId,password);

    }


    public UserDTO findUserById(String userId){
        UserE userE=(UserE)userRepository.findUser(userId);
        return userConvert.convertToUserDTO(userE);
    }


    public String authentication(String userId,String password){
        UserAbstract user=userRepository.findUser(userId);
        if(user==null){
            return "找不到用户";
        }
        if(user.authentication(password)){
            return "认证成功";
        }else {
            return "认证失败";
        }


    }

}
