package com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.base.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.xuejinwei.doubanbookmovie.doubanbookmovie.api.ApiFactory;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.api.ApiWrapper;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.api.FlatHandler;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * activity基础类，该项目每个activity都要继承此类或者其子类
 * Created by xuejinwei on 16/3/12.
 */
public class BaseActivity extends AppCompatActivity implements SwipeBackActivityBase {
    private SwipeBackActivityHelper mSwipeBackHelper;
    public static final ApiWrapper mApiWrapper = ApiFactory.getApiWrapper();
    public  CompositeSubscription mSubscriptions;
    private ProgressDialog        mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mSwipeBackHelper = new SwipeBackActivityHelper(this);
        this.mSwipeBackHelper.onActivityCreate();
        setSwipeBackEnable(canSwipeBack());
        mSubscriptions = new CompositeSubscription();
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(true);

    }

    @Override
    protected void onDestroy() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
        super.onDestroy();
        // 界面退出时，退订本界面所有 subscription（需要使用 addSubscription 或者 runRxTaskXXXX 方法才有效）
        if (mSubscriptions != null) {
            mSubscriptions.unsubscribe();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        this.mSwipeBackHelper.onPostCreate();
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mSwipeBackHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        this.getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        this.getSwipeBackLayout().scrollToFinishActivity();
    }

    /**
     * 是否可以右划返回，默认不可以
     */
    public boolean canSwipeBack() {
        return false;
    }

    public void addSubscription(Subscription subscription) {
        mSubscriptions.add(subscription);
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
        addSubscription(observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(result, error, onComplete));
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
        addSubscription(observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(result, error));
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
        addSubscription(observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(result, FlatHandler::handleError));
    }
    /**
     * 显示progressBar
     *
     * @param show 是否显示
     */
    protected void showProgressDialog(boolean show) {
        showProgressDialog(show, "");
    }

    /**
     * 显示progressBar
     *
     * @param show    是否显示
     * @param message progressBar 消息提示
     */
    protected void showProgressDialog(boolean show, String message) {
        if (show) {
            mProgressDialog.setMessage(message);
            mProgressDialog.show();
        } else {
            mProgressDialog.hide();
        }
    }
}
