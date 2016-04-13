package com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.xuejinwei.doubanbookmovie.doubanbookmovie.R;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.base.activity.SwipeBackActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xuejinwei on 16/4/12.
 * Email:xuejinwei@outlook.com
 */
public class WebViewActivity extends SwipeBackActivity {
    public static final String PARAM_URL   = "webURL";
    public static final String PARAM_TITLE = "actionBarTitle";
    @Bind(R.id.toolbar)    Toolbar     toolbar;
    @Bind(R.id.webView)    WebView     webView;
    @Bind(R.id.pb_loading) ProgressBar pb_loading;


    /**
     * @param title toolbar title
     * @param url   webview 要访问的URL
     */
    public static void start(Context context, String title, String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(PARAM_TITLE, title);
        intent.putExtra(PARAM_URL, url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);

        String actionBarTitle = getIntent().getStringExtra(PARAM_TITLE);
        String URL = getIntent().getStringExtra(PARAM_URL);
        setTitle(actionBarTitle);

        WebSettings settings = webView.getSettings();
        setSettings(settings);

        webView.requestFocus();
        webView.setWebChromeClient(new WebChromeClient() {
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
        webView.loadUrl(URL);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器

                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

//                pb_label.setVisibility(View.VISIBLE);
//                if (isFirst) {
//
//                    progressWheel.setVisibility(View.VISIBLE);
//                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

//                pb_label.setVisibility(View.GONE);
//                if (isFirst) {
//                    progressWheel.setVisibility(View.GONE);
//                    isFirst = false;
//                }
            }
        });
    }

    private void setSettings(WebSettings setting) {
        setting.setJavaScriptEnabled(true);
        setting.setBuiltInZoomControls(true);
        setting.setDisplayZoomControls(false);
        setting.setSupportZoom(true);

        setting.setDomStorageEnabled(true);
        setting.setDatabaseEnabled(true);
        // 全屏显示
        setting.setLoadWithOverviewMode(true);
        setting.setUseWideViewPort(true);
//        setting.setJavaScriptEnabled(true);
//        //启用数据库
//        setting.setDatabaseEnabled(true);
//        String dir = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
//
//        //启用地理定位
//        setting.setGeolocationEnabled(true);
//        //设置定位的数据库路径
//        setting.setGeolocationDatabasePath(dir);
//        //最重要的方法，一定要设置，这就是出不来的主要原因
//        setting.setDomStorageEnabled(true);
//        setting.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//        //重新设置websettings
//        setting.setBuiltInZoomControls(true);
//        setting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//        setting.setUseWideViewPort(true);
//        setting.setLoadWithOverviewMode(true);
//        setting.setSavePassword(true);
//        setting.setSaveFormData(true);
//        setting.setDisplayZoomControls(false);
//        setting.setSupportZoom(true);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();//返回上一页面
        } else {
            finish();
        }
    }
}
