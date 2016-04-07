package com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.orhanobut.logger.Logger;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.R;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.app.Setting;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.OAuthResult;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.base.activity.SwipeBackActivity;

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

    public static void start(Context context) {
        Intent starter = new Intent(context, AuthActivity.class);
        context.startActivity(starter);
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
                    runRxTaskOnUi(mApiWrapper.getOauthResult(code), new Action1<OAuthResult>() {
                        @Override
                        public void call(OAuthResult oAuthResult) {
                            Setting.putSetting(Setting.Key.access_token, oAuthResult.access_token);
                            Setting.putSetting(Setting.Key.expires_in, oAuthResult.expires_in);
                            Setting.putSetting(Setting.Key.refresh_token, oAuthResult.refresh_token);
                            Setting.putSetting(Setting.Key.douban_user_id, oAuthResult.douban_user_id);
                            Setting.putSetting(Setting.Key.douban_user_name, oAuthResult.douban_user_name);
                            setResult(RESULT_OK);
                            showProgressDialog(false);
                            finish();
                        }
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
//        showProgressDialog(true,"正在登陆中……");
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
