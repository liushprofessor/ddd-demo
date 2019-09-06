package com.liu.demo.user.infrastructure.repository;

import com.liu.demo.user.domain.client.UserDTO;
import com.liu.demo.user.domain.client.UserPO;
import com.liu.demo.user.domain.modal.BaseInfoVO;
import com.liu.demo.user.domain.modal.UserE;
import org.springframework.stereotype.Component;

/**
 * @author Liush
 * @description SQL组装领域对象工厂
 * @date 2019/9/5 11:37
 **/
@Component
public class UserRepositoryConvert {

    public UserE convertToUserE(UserPO userPO) {

        BaseInfoVO baseInfo = new BaseInfoVO(userPO.getUsername(), userPO.getEmail());
        return new UserE(userPO.getUserId(), userPO.getPassword(), baseInfo);
    }

    public UserPO convertToUserPO(UserE userE) {
        String username=userE.getBaseInfo().getUsername();
        String email=userE.getBaseInfo().getEmail();
        return new UserPO(userE.getUserId(), userE.getPassword(),username,email);

    }

    public UserDTO convertToUserDTO(UserPO userPO){

        return new UserDTO(userPO.getUserId(),userPO.getPassword(),userPO.getUsername(),userPO.getEmail());
    }


}
