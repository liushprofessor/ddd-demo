package com.liu.demo.user2.infrastructure.repository;


import com.liu.demo.user2.domain.client.UserPO;
import com.liu.demo.user2.domain.modal.UserE;
import com.liu.demo.user2.domain.modal.UserAbstract;
import com.liu.demo.user2.infrastructure.repository.mapper.UserMapper2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


/**
 * @author Liush
 * @description 用户仓库
 * @date 2019/9/5 11:17
 **/
@Repository
public class UserRepository2 {

    @Autowired
    private UserMapper2 userMapper;

    @Autowired
    private UserRepositoryConvert2 userRepositoryConvert;

    public UserAbstract findUser(String userId){
        UserPO userPO =userMapper.findUser(userId);
        return userRepositoryConvert.convertToUserE(userPO);
    }


    public void addUser(UserAbstract user){
        UserPO userPO= userRepositoryConvert.convertToUserPO((UserE) user);
        userMapper.insertUser(userPO);

    }




}
