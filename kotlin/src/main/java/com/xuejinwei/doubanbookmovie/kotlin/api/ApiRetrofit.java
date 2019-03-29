package com.xuejinwei.doubanbookmovie.kotlin.api;

import android.util.Log;

import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xuejinwei on 16/3/23.
 * retrofit封装api
 */
public class ApiRetrofit {
    private              String baseUrl         = "https://Api.douban.com/v2/";
    private static final int    DEFAULT_TIMEOUT = 10;

    private Retrofit mRetrofit;
    private Api      mApi;

    ApiRetrofit() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.addInterceptor(chain -> {
            /**
             * 转换http状态吗为{@link com.xuejinwei.doubanbookmovie.kotlin.model.Result}中的code
             */
            Request request = chain.request();
            Response response = chain.proceed(request);
            JSONObject wrapper = new JSONObject();
            try {
                wrapper.put("code", response.code());
                String responseString = response.body().string();
                JSONObject raw = new JSONObject();
                if (!responseString.contains("<!DOCTYPE html>")) {
                    raw = new JSONObject(responseString);
                }
                if (responseString.contains("<!DOCTYPE html>")) {
                    wrapper.put("htmlBody", responseString);

                } else if (responseString.contains("code") && responseString.contains("msg")) {
                    wrapper.put("error", raw);
                } else {
                    wrapper.put("body", raw);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ResponseBody body = ResponseBody.create(MediaType.parse("application/json"), wrapper.toString());
            return new Response.Builder()
                    .code(200)
                    .request(request)
                    .protocol(Protocol.HTTP_1_1)
                    .body(body)
                    .build();
        });
        httpClientBuilder.addInterceptor(new HttpLoggingInterceptor(message -> {
            // 打印logo
            if (message.startsWith("{")) {
                Logger.json(message);
            } else if (message.contains("<!DOCTYPE html>")) {
                Logger.d(message);
            } else {
                Log.i("Api_head", message);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY));

        mRetrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
        mApi = mRetrofit.create(Api.class);
    }

    public Api getApi() {
        return mApi;
    }
}
