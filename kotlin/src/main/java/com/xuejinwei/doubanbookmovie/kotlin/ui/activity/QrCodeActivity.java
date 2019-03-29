package com.xuejinwei.doubanbookmovie.kotlin.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;

import com.xuejinwei.doubanbookmovie.kotlin.R;
import com.xuejinwei.doubanbookmovie.kotlin.api.FlatHandler;
import com.xuejinwei.doubanbookmovie.kotlin.ui.base.activity.SwipeBackActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

/**
 * Created by xuejinwei on 16/5/6.
 * Email:xuejinwei@outlook.com
 */
public class QrCodeActivity extends SwipeBackActivity implements QRCodeView.Delegate {
    private static final String TAG = QrCodeActivity.class.getSimpleName();
    @Bind(R.id.zxingview) ZXingView zxingview;

    public static void start(Context context) {
        Intent starter = new Intent(context, QrCodeActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);
        ButterKnife.bind(this);
        setTitle("扫码搜书");
        zxingview.setResultHandler(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        zxingview.startCamera();
        zxingview.startSpot();
    }

    @Override
    protected void onStop() {
        zxingview.stopCamera();
        super.onStop();
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        vibrate();
        QrCodeActivity.this.showProgressDialog(true,"识别中……");
        runRxTaskOnUi(mApiWrapper.searchBookByIsbn(result), book -> {
            QrCodeActivity.this.showProgressDialog(false);
            BookDetailActivity.start(QrCodeActivity.this, book.id);
            QrCodeActivity.this.finish();
        }, exception -> {
            FlatHandler.handleError(this,exception);
            QrCodeActivity.this.showProgressDialog(false);
            zxingview.startSpot();
        });

    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }


    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "打开相机出错");
    }
}
