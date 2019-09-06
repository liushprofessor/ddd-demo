package com.liu.demo.user.controller;

import com.liu.demo.user.app.UserAPPService;
import com.liu.demo.user.domain.client.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Liush
 * @description
 * @date 2019/9/5 14:47
 **/
@RestController
public class UserController {

    @Autowired
    private UserAPPService userAPPService;

    @RequestMapping("user/addUser")
    public String addUser(UserDTO userDTO){
        userAPPService.addUser(userDTO);
        return "success";
    }

    @RequestMapping("user/findUser")
    public UserDTO findUser(String userId){
        return userAPPService.findUserById(userId);
    }

    @RequestMapping("user/authentication")
    public String authentication(String userId,String password){
        return userAPPService.authentication(userId,password);
    }

    @RequestMapping("user/findUsersByName")
    public List<UserDTO> findUsersByName(String name){
        return userAPPService.findUsersByName(name);
    }

    @RequestMapping("user/changePassword")
    public String  changePassword(String userId,String password){
        userAPPService.changePassword(userId,password);
        return "success";

    }




}
