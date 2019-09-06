package com.liu.demo.user2.common;

/**
 * @author Liush
 * @description 验证抽象类
 * @date 2019/9/5 17:11
 **/
public abstract class ValidateAbstract {

    protected ValidateHandlerI validateHandlerI;


    public ValidateAbstract(ValidateHandlerI validateHandlerI) {
        this.validateHandlerI = validateHandlerI;
    }

    public ValidateAbstract() {
        this.validateHandlerI=ValidateHandlerFactory.doNothingValidateHandler();
    }

    public abstract void validate();

    public void setValidateHandlerI(ValidateHandlerI validateHandlerI) {
        this.validateHandlerI = validateHandlerI;
    }
}
