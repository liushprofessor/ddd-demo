package com.liu.demo.user2.common;

/**
 * @author Liush
 * @description
 * @date 2019/9/5 17:05
 **/
public class DoNothingValidateHandler implements ValidateHandlerI {


    @Override
    public void handlerError(String message,Exception e) {
        System.out.println(message);

        if(e instanceof IllegalArgumentException){
            throw new IllegalArgumentException(message);
        }

        if(e instanceof RuntimeException){
            throw new RuntimeException(message);
        }
    }
}
