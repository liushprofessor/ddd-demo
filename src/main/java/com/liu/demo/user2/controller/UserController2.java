package com.liu.demo.user2.controller;


import com.liu.demo.user2.domain.client.UserDTO;
import com.liu.demo.user2.app.UserAPPService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Liush
 * @description
 * @date 2019/9/5 14:47
 **/
@RestController
public class UserController2 {

    @Autowired
    private UserAPPService2 userAPPService;

    @RequestMapping("user2/addUser")
    public String addUser2(UserDTO userDTO){
        userAPPService.addUser(userDTO);
        return "success";
    }

    @RequestMapping("user2/findUser")
    public UserDTO findUser2(String userId){
        return userAPPService.findUserById(userId);
    }

    @RequestMapping("user2/authentication")
    public String authentication2(String userId,String password){
        return userAPPService.authentication(userId,password);
    }




}
