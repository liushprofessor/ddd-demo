package com.liu.demo.user.app;

import com.liu.demo.user.domain.UserServiceI;
import com.liu.demo.user.domain.client.UserConvert;
import com.liu.demo.user.domain.client.UserDTO;
import com.liu.demo.user.domain.modal.UserE;
import com.liu.demo.user.domain.modal.UserFactory;
import com.liu.demo.user.infrastructure.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Liush
 * @description  用户APP层服务
 * @date 2019/9/5 14:25
 **/
@Service
public class UserAPPService {

    @Autowired
    private UserFactory userFactory;

    @Autowired
    private UserConvert userConvert;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceI userServiceI;

    public void addUser(UserDTO userDTO){
        UserE user=userFactory.createUser(userDTO);
        userRepository.addUser(user);
    }

    public void changePassword(String userId,String password){
        userServiceI.changePassword(userId,password);
    }


    public UserDTO findUserById(String userId){
        UserE userE=userRepository.findUser(userId);
        return userConvert.convertToUserDTO(userE);
    }

    /**
     * 根据名字查询
     * @param name
     * @return
     */
    public List<UserDTO> findUsersByName(String name){

        return userRepository.findUsersByName(name);
    }


    public String authentication(String userId,String password){
        UserE user=userRepository.findUser(userId);
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
