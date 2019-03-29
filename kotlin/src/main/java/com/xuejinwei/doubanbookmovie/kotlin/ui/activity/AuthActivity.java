package com.xuejinwei.doubanbookmovie.kotlin.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.orhanobut.logger.Logger;
import com.xuejinwei.doubanbookmovie.kotlin.R;
import com.xuejinwei.doubanbookmovie.kotlin.app.Setting;
import com.xuejinwei.doubanbookmovie.kotlin.model.Me;
import com.xuejinwei.doubanbookmovie.kotlin.ui.base.activity.SwipeBackActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action1;

/**
 * Created by xuejinwei on 16/4/7.
 * Email:xuejinwei@outlook.com
 * auth授权豆瓣
 */
public class AuthActivity extends SwipeBackActivity {

    @Bind(R.id.toolbar)      Toolbar     toolbar;
    @Bind(R.id.webview_auth) WebView     webview_auth;
    @Bind(R.id.pb_loading)   ProgressBar pb_loading;

    public final String HTTP_AUTH = "https://www.douban.com/service/auth2/auth?" +
            "client_id=" + Setting.APIKEY + "&" +
            "redirect_uri=" + Setting.REDIRECT_URL + "&" +
            "response_type=code&" +
            "scope=book_basic_r,book_basic_w,douban_basic_common,movie_basic_r,movie_basic_w";

    public static void start(Activity context) {
        Intent starter = new Intent(context, AuthActivity.class);
        context.startActivityForResult(starter, 9999);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);
        setTitle("登陆授权");
        init();
    }

    private void init() {
        //在webview内部打开网页
        //根据url获取code
        webview_auth.loadUrl(HTTP_AUTH);
        webview_auth.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("code=")) {
                    int index = url.indexOf("=");
                    String code = url.substring(index + 1, url.length());
                    Logger.i(code);
                    showProgressDialog(true, "登陆中……");
                    runRxTaskOnUi(mApiWrapper.getOauthResult(code), oAuthResult -> {
                        Setting.Login(oAuthResult);
                        runRxTaskOnUi(mApiWrapper.getMeInformation(), new Action1<Me>() {
                            @Override
                            public void call(Me me) {
                                Setting.putMeInformation(me);
                                setResult(RESULT_OK);
                                showProgressDialog(false);
                                finish();
                            }
                        });
                    });
                } else {
                    webview_auth.loadUrl(url);
                }
                return true;
            }
        });
        //获取webview加载网页的进度并显示到progressbar中
        webview_auth.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    pb_loading.setVisibility(View.INVISIBLE);
                } else {
                    if (pb_loading.getVisibility() == View.INVISIBLE) {
                        pb_loading.setVisibility(View.VISIBLE);
                    }
                    pb_loading.setProgress(newProgress);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        //重写返回按键响应
        //返回webview的上一页
        if (webview_auth.canGoBack()) {
            webview_auth.goBack();
            return;
        }
        super.onBackPressed();
    }
}
