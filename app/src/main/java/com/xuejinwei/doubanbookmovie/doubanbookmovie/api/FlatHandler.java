package com.xuejinwei.doubanbookmovie.doubanbookmovie.api;

import com.orhanobut.logger.Logger;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.app.App;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.app.Setting;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.Result;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.activity.AuthActivity;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.util.CommonUtil;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.util.RxUtils;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by xuejinwei on 16/3/26.
 * Email:xuejinwei@outlook.com
 * Flat 之后返回结果对 Error 的统一处理
 */
public class FlatHandler {
    public static final ApiWrapper mApiWrapper = ApiFactory.getApiWrapper();

    /**
     * 将带 Result 的请求结果转换为更为直接的结果对象，非法结果交给 Error handler 处理
     *
     * @param result Result 对象
     * @param <T>    结果类型
     * @return T 类型的结果，非法结果抛出给 Error handler 处理
     */
    public static <T> Observable<T> flatResult(Result<T> result) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                if (result.body == null) {
                    subscriber.onError(result.error);
                } else {
                    subscriber.onNext(result.body);
                }
                subscriber.onCompleted();
            }
        });
    }

    /**
     * 如果在 ApiWrapper 中使用了 {@link FlatHandler#flatResult(Result)} 方法，则返回数据分为两种，一种是
     * onNext 发布的可以直接进行处理的正确结果，一种是 onError 抛出的异常 ErrorResult，这里根据 ErrorResult
     * 的错误码进行统一处理。
     */
    public static void handleError(Throwable exception) {
        Logger.e(exception.getMessage());
        if (exception instanceof ApiException) {
            switch (((ApiException) exception).code) {
                case 1000:
                    CommonUtil.toast("请登陆");
                    AuthActivity.start(App.getApp());
                    break;
                case 106:
                    RxUtils.callOnUI(mApiWrapper.refreshOauthResult()).subscribe(oAuthResult -> {
                        Setting.putSetting(Setting.Key.access_token, oAuthResult.access_token);
                        Setting.putSetting(Setting.Key.expires_in, oAuthResult.expires_in);
                        Setting.putSetting(Setting.Key.refresh_token, oAuthResult.refresh_token);
                        Setting.putSetting(Setting.Key.douban_user_id, oAuthResult.douban_user_id);
                        Setting.putSetting(Setting.Key.douban_user_name, oAuthResult.douban_user_name);
                        CommonUtil.toast("授权已刷新，请重新进入");

                    });
                    break;
                case 103:
                    Setting.putSetting(Setting.Key.access_token, "");
                    Setting.putSetting(Setting.Key.expires_in, "");
                    Setting.putSetting(Setting.Key.refresh_token, "");
                    Setting.putSetting(Setting.Key.douban_user_id, "");
                    Setting.putSetting(Setting.Key.douban_user_name, "");
                    AuthActivity.start(App.getApp());
                    CommonUtil.toast("请重新登陆");
                    break;
                default:
                    Logger.e(((ApiException) exception).code+((ApiException) exception).message);
                    break;
            }
            return;
        }
    }
}
