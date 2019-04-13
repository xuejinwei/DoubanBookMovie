package com.xuejinwei.doubanbookmovie.kotlin.api;

import android.app.Activity;
import android.util.Log;

import com.orhanobut.logger.Logger;
import com.xuejinwei.doubanbookmovie.kotlin.app.Setting;
import com.xuejinwei.doubanbookmovie.kotlin.model.ErrorResult;
import com.xuejinwei.doubanbookmovie.kotlin.model.Result;
import com.xuejinwei.doubanbookmovie.kotlin.model.Success;
import com.xuejinwei.doubanbookmovie.kotlin.ui.activity.AuthActivity;
import com.xuejinwei.doubanbookmovie.kotlin.util.CommonUtil;
import com.xuejinwei.doubanbookmovie.kotlin.util.RxUtils;

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
    public static void handleError(Activity activity, Throwable exception) {

        if (exception instanceof ErrorResult) {
            switch (((ErrorResult) exception).code) {
                case 1000:
                    CommonUtil.toast("请登陆");
                    AuthActivity.start(activity);
                    break;
                case 106:
                    RxUtils.callOnUI(mApiWrapper.refreshOauthResult()).subscribe(oAuthResult -> {
                        Setting.Login(oAuthResult);
                        CommonUtil.toast("授权已刷新，请重新进入");

                    });
                    break;
                case 103:
                    Setting.Logout();
                    AuthActivity.start(activity);
                    CommonUtil.toast("请重新登陆");
                    break;
                case 1998:
                    CommonUtil.toast("访问太频繁，请更换IP");
                    break;
                case 6000:
                    CommonUtil.toast("未找到该图书");
                    break;
                default:
                    Logger.e("code:" + ((ErrorResult) exception).code + ".msg:" + ((ErrorResult) exception).msg + ".request:" + ((ErrorResult) exception).request);
                    break;
            }
            return;
        }
        Log.e("Error", String.valueOf(exception));
    }

    /**
     * 转换为 Void 对象，主要用于只需要获取操作是否成功的操作，例如插入、删除数据
     *
     * @param result retrofit 直接返回的结果
     * @return 如果通过 onNext 返回，则是成功（返回数据为 null），错误依然交给 onError 处理
     */
    public static Observable<Success> flatToSuccess(Result result) {
        return Observable.create(subscriber -> {
            switch (result.code) {
                case 204:
                    subscriber.onNext(new Success());
                    break;
            }
            subscriber.onCompleted();
        });
    }
}
