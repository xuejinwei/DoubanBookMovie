package com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.base.fragment;

import android.support.v4.app.Fragment;

import com.xuejinwei.doubanbookmovie.doubanbookmovie.api.ApiFactory;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.api.ApiWrapper;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.base.activity.BaseActivity;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by xuejinwei on 16/3/24.
 */
public class BaseFragment extends Fragment{
    public static final ApiWrapper mApiWrapper = ApiFactory.getApiWrapper();
    private CompositeSubscription mSubscriptions;

    public void addSubscription(Subscription subscription) {
        mSubscriptions.add(subscription);
    }

    @Override public void onDestroy() {
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }


    /**
     * Activity 中启动新 rx 任务的方法，可以保证在 Activity 生命周期结束时退订 Observable
     *
     * @param observable 任务的 observable
     * @param result     任务结果的回调
     * @param error      任务出错时的回调
     * @param onComplete 任务完成后的回调
     * @param <T>        任务中的实体类型
     */
    public <T> void runRxTaskOnUi(Observable<T> observable, Action1<T> result,
                                  Action1<Throwable> error, Action0 onComplete) {

        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).runRxTaskOnUi(observable, result, error, onComplete);
        } else {
            throw new NotSupportRxTaskException();
        }
    }

    /**
     * Activity 中启动新 rx 任务的方法，可以保证在 Activity 生命周期结束时退订 Observable
     *
     * @param observable 任务的 observable
     * @param result     任务结果的回调
     * @param error      任务出错时的回调
     * @param <T>        任务中的实体类型
     */
    public <T> void runRxTaskOnUi(Observable<T> observable, Action1<T> result,
                                  Action1<Throwable> error) {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).runRxTaskOnUi(observable, result, error);
        } else {
            throw new NotSupportRxTaskException();
        }
    }

    /**
     * Activity 中启动新 rx 任务的方法，可以保证在 Activity 生命周期结束时退订 Observable
     * 使用默认的 Error Handler 处理错误
     *
     * @param observable 任务的 observable
     * @param result     任务结果的回调
     * @param <T>        任务中的实体类型
     */
    public <T> void runRxTaskOnUi(Observable<T> observable, Action1<T> result) {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).runRxTaskOnUi(observable, result);
        } else {
            throw new NotSupportRxTaskException();
        }
    }


    public static class NotSupportRxTaskException extends RuntimeException {
        public NotSupportRxTaskException() {
            super("该 Fragment 不属于 BaseActivity 的子类，不能执行 RxTask 相关任务，请自行管理。");
        }
    }
}
