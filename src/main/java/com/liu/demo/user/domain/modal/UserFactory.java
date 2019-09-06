package com.liu.demo.user.domain.modal;

import com.liu.demo.user.domain.client.UserDTO;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author Liush
 * @description 领域工厂类
 * @date 2019/9/5 14:10
 **/
@Component
public class UserFactory {


     public UserE createUser(UserDTO userDTO ){
         BaseInfoVO baseInfoVO=new BaseInfoVO(userDTO.getUsername(),userDTO.getEmail());
         return new UserE(UUID.randomUUID().toString(),userDTO.getPassword(),baseInfoVO);
     }



}
