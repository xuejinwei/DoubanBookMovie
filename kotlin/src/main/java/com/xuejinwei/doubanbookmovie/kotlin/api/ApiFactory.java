package com.xuejinwei.doubanbookmovie.kotlin.api;

/**
 * Created by xuejinwei on 16/3/24.
 */
public class ApiFactory {

    protected static final Object     monitor     = new Object();
    static                 Api        sApi        = null;
    static                 ApiWrapper sApiWrapper = null;

    public static Api getApi() {
        synchronized (monitor) { //对象锁
            if (sApi == null) {
                sApi = new ApiRetrofit().getApi();
            }
            return sApi;
        }
    }

    public static ApiWrapper getApiWrapper() {
        if (sApiWrapper == null) {
            sApiWrapper = new ApiWrapper();
        }
        return sApiWrapper;
    }
}
