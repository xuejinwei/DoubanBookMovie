package com.xuejinwei.doubanbookmovie.kotlin.model;

/**
 * Created by xuejinwei on 16/3/26.
 * Email:xuejinwei@outlook.com
 * 豆瓣接口的返回实体封装
 */
public class Result<T> {

    public int code;
    public T body;
    public ErrorResult error;
}
