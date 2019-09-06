package com.liu.demo.user2.infrastructure.repository;

import com.liu.demo.user2.domain.client.UserPO;
import com.liu.demo.user2.domain.modal.BaseInfoVO;
import com.liu.demo.user2.domain.modal.UserE;
import org.springframework.stereotype.Component;

/**
 * @author Liush
 * @description SQL组装领域对象工厂
 * @date 2019/9/5 11:37
 **/
@Component
public class UserRepositoryConvert2 {

    public UserE convertToUserE(UserPO userPO) {

        BaseInfoVO baseInfo = new BaseInfoVO(userPO.getUsername(), userPO.getEmail());
        return new UserE(userPO.getUserId(), userPO.getPassword(), baseInfo);
    }

    public UserPO convertToUserPO(UserE userE) {
        String username=userE.getBaseInfo().getUsername();
        String email=userE.getBaseInfo().getEmail();
        return new UserPO(userE.getUserId(), userE.getPassword(),username,email);

    }

}
