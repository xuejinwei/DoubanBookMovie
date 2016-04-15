package com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
public class BaseFragment extends Fragment {
    public static final ApiWrapper mApiWrapper = ApiFactory.getApiWrapper();
    private CompositeSubscription mSubscriptions;

    public void addSubscription(Subscription subscription) {
        mSubscriptions.add(subscription);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mSubscriptions.unsubscribe();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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

    public static class BaseFragmentBuilder {

        Bundle args;

        public BaseFragmentBuilder() {
            args = new Bundle();
        }

        public interface PutArguments {
            void put(Bundle args);
        }

        public BaseFragmentBuilder put(PutArguments putArguments) {
            putArguments.put(args);
            return this;
        }

        public <T extends BaseFragment> T build(Class<T> clazz) {
            T fragment = null;
            //noinspection TryWithIdenticalCatches
            try {
                //noinspection unchecked
                fragment = clazz.newInstance();
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (fragment != null) {
                fragment.setArguments(args);
            }
            //noinspection unchecked
            return fragment;
        }
    }
}
