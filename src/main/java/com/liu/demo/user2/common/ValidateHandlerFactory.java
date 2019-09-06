package com.liu.demo.user2.common;

/**
 * @author Liush
 * @description 验证错误处理器工厂
 * @date 2019/9/5 17:31
 **/
public class ValidateHandlerFactory {

    public static DoNothingValidateHandler doNothingValidateHandler(){

            return new DoNothingValidateHandler();

    }



}
