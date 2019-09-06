package com.liu.demo.user2.domain.modal;


import com.liu.demo.user2.common.DoNothingValidateHandler;
import com.liu.demo.user2.common.ValidateHandlerI;

/**
 * @author Liush
 * @description
 * @date 2019/9/5 15:27
 **/
public abstract class UserAbstract {

    protected String userId;

    protected String password;

    protected BaseInfoVO baseInfo ;

    protected ValidateHandlerI validateHandlerI;

    public UserAbstract(String userId, String password, BaseInfoVO baseInfo) {
        this.userId = userId;
        this.password = password;
        this.baseInfo = baseInfo;
        this.validateHandlerI=new DoNothingValidateHandler();
    }

    public abstract boolean authentication(String password);

    public abstract BaseInfoVO getBaseInfo();

    public abstract String getUserId();


}
