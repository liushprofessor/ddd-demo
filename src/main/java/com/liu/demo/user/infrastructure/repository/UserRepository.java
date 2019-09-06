package com.liu.demo.user.infrastructure.repository;

import com.liu.demo.user.domain.client.UserDTO;
import com.liu.demo.user.domain.client.UserPO;
import com.liu.demo.user.domain.modal.UserE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.liu.demo.user.infrastructure.repository.mapper.UserMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Liush
 * @description 用户仓库
 * @date 2019/9/5 11:17
 **/
@Repository
public class UserRepository {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepositoryConvert userRepositoryConvert;

    /**
     *  根据用户id查找用户
     */
    public UserE findUser(String userId){
        UserPO userPO =userMapper.findUser(userId);
        return userRepositoryConvert.convertToUserE(userPO);
    }


    /**
     * 添加用户
     */
    public void addUser(UserE user){
        UserPO userPO= userRepositoryConvert.convertToUserPO(user);
        userMapper.insertUser(userPO);

    }

    /**
     * 修改密码
     */
    public void changePassword(UserE userE){
        userMapper.updateUserPassword(userE.getUserId(),userE.getPassword());

    }

    /**
     *根据用户姓名批量查询，查询可以不走领域模型
     */
    public List<UserDTO> findUsersByName(String name){
        List<UserPO> userPOs=userMapper.findUsersByName(name);
        List<UserDTO> userDTOs=new ArrayList<>();
        if (userPOs==null){
            return userDTOs;
        }
        userPOs.forEach(userPO->userDTOs.add(userRepositoryConvert.convertToUserDTO(userPO)));
        return  userDTOs;

    }



}
