package com.liu.demo.user2.domain.modal;

import com.liu.demo.user2.domain.client.UserDTO;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author Liush
 * @description 领域工厂类
 * @date 2019/9/5 14:10
 **/
@Component
public class UserFactory2 {


     public UserAbstract createUser(UserDTO userDTO ){
         BaseInfoVO baseInfoVO=new BaseInfoVO(userDTO.getUsername(),userDTO.getEmail());
         UserE user=new UserE(UUID.randomUUID().toString(),userDTO.getPassword(),baseInfoVO);
         return user;
     }



}
