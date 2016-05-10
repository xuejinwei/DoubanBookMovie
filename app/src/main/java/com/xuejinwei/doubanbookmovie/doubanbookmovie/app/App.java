package com.xuejinwei.doubanbookmovie.doubanbookmovie.app;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.orhanobut.logger.Logger;

/**
 * 自己的application
 * Created by xuejinwei on 16/3/12.
 */
public class App extends Application {
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Fresco.initialize(this);
        Logger.init("Api_json");

        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,"45zfVSyyntekSLHioGDBccz5-gzGzoHsz","OcBW6svtdveOO2Lr142iVixB");
    }
    /**
     * 获得 Application 实例
     */
    public static App getApp() {
        if (instance == null) {
            return new App();
        }
        return instance;
    }
}
