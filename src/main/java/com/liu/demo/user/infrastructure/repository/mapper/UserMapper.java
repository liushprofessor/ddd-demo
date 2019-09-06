package com.liu.demo.user.infrastructure.repository.mapper;

import com.liu.demo.user.domain.client.UserPO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author Liush
 * @description
 * @date 2019/9/5 11:17
 **/
@Mapper
public interface UserMapper {
    @Select("select ID_ userId,PASSWORD_ password,USERNAME_ username,EMAIL_ email from T_USER where ID_=#{userId}")
    UserPO findUser(String userId);

    @Insert("insert into T_USER(ID_,PASSWORD_,USERNAME_,EMAIL_) values(#{userId},#{password},#{username},#{email})")
    void insertUser(UserPO userPO);

    @Select("select ID_ userId,USERNAME_ username,EMAIL_ email from T_USER where USERNAME_=#{name}")
    List<UserPO> findUsersByName(String name);

    @Update("update T_USER set PASSWORD_=#{password} where ID_=#{userId}")
    void updateUserPassword(@Param("userId") String userId, @Param("password") String password);

}
