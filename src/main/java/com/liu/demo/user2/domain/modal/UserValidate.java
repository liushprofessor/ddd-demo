package com.liu.demo.user2.domain.modal;

import com.liu.demo.user2.common.ValidateAbstract;
import com.liu.demo.user2.common.ValidateHandlerI;
import org.springframework.util.StringUtils;

/**
 * @author Liush
 * @description
 * @date 2019/9/5 17:24
 **/
public class UserValidate extends ValidateAbstract {

    protected UserE userE;


    public UserValidate(UserE userE) {
        super();
        this.userE=userE;
    }


    public UserValidate(UserE userE,ValidateHandlerI validateHandlerI) {
        super(validateHandlerI);
        this.userE=userE;
    }

    @Override
    public void validate() {
        if(StringUtils.isEmpty(userE.getPassword()) && StringUtils.isEmpty(userE.getBaseInfo().getUsername())){
            super.validateHandlerI.handlerError("密码和用户名不能同时为空",new  RuntimeException("UserE对象整体验证失败"));

        }
    }
}
