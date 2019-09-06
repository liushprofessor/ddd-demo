package com.liu.demo.user2.infrastructure.repository.mapper;

import com.liu.demo.user2.domain.client.UserPO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author Liush
 * @description
 * @date 2019/9/5 11:17
 **/
@Mapper
public interface UserMapper2 {
    @Select("select ID_ userId,PASSWORD_ password,USERNAME_ username,EMAIL_ email from T_USER where ID_=#{userId}")
    UserPO findUser(String userId);

    @Insert("insert into T_USER(ID_,PASSWORD_,USERNAME_,EMAIL_) values(#{userId},#{password},#{username},#{email})")
    void insertUser(UserPO userPO);

}
