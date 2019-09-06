package com.liu.demo.user.domain.client;

import com.liu.demo.user.domain.modal.UserE;
import org.springframework.stereotype.Component;

/**
 * @author Liush
 * @description 转换类
 * @date 2019/9/5 14:52
 **/
@Component
public class UserConvert {

     public UserDTO convertToUserDTO(UserE userE){
         String username=userE.getBaseInfo().getUsername();
         String email=userE.getBaseInfo().getEmail();
         return new UserDTO(userE.getUserId(),userE.getPassword(),username,email);
     }


}
