package com.xuejinwei.doubanbookmovie.doubanbookmovie.api;

import com.orhanobut.logger.Logger;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.ErrorResult;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.Result;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by xuejinwei on 16/3/26.
 * Email:xuejinwei@outlook.com
 * Flat 之后返回结果对 Error 的统一处理
 */
public class FlatHandler {

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
        Logger.e("Error_code", exception);
        if (exception instanceof ErrorResult) {
            // TODO: 16/3/26 这里对错误进行统一处理
            Logger.e("Error_code", ((ErrorResult) exception).code);
        }
    }
}
