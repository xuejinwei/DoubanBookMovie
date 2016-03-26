package com.xuejinwei.doubanbookmovie.doubanbookmovie.api;

/**
 * Created by twiceYuan on 10/20/15.
 *
 * 网络请求反馈的异常
 */
public class ApiException extends Exception {

    int code;
    String message;

    public ApiException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
