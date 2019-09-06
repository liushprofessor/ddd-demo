package com.liu.demo.user2.common;

/**
 * @author Liush
 * @description  验证错误处理器
 * @date 2019/9/5 17:03
 **/
public interface ValidateHandlerI {

    void handlerError(String message,Exception e);

}
