package com.xuejinwei.doubanbookmovie.doubanbookmovie.api;

import com.xuejinwei.doubanbookmovie.doubanbookmovie.app.Setting;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.Celebrity;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.HtmlResult;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.Me;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.Movie;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.MovieResult;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.OAuthResult;

import rx.Observable;

/**
 * Created by xuejinwei on 16/3/24.
 */
public class ApiWrapper {
    Api mApi = ApiFactory.getApi();

    /**
     * @param authorization_code auth认证上一步获取到的authorization_code
     */
    public Observable<OAuthResult> getOauthResult(String authorization_code) {
        return mApi.getOauthResult(Setting.APIKEY, Setting.SERCET, Setting.REDIRECT_URL, "authorization_code", authorization_code).flatMap(FlatHandler::flatResult);
    }

    /**
     * 用来刷新access_token 的
     */
    public Observable<OAuthResult> refreshOauthResult() {
        return mApi.refreshOauthResult(Setting.APIKEY, Setting.SERCET, Setting.REDIRECT_URL, "refresh_token", Setting.getSetting(Setting.Key.refresh_token, "")).flatMap(FlatHandler::flatResult);
    }

    /**
     * 获取个人信息
     */
    public Observable<Me> getMeInformation() {

        return mApi.getMeInformation(Setting.getSetting(Setting.Key.access_token, "")).flatMap(FlatHandler::flatResult);
    }

    /**
     * 查询正在热映movie
     *
     * @param star  int 开始数
     * @param count 查询数量，传入0 为默认数量
     */
    public Observable<MovieResult> getMovieInTheaters(int star, int count) {
        return mApi.getMovieInTheaters(star, count).flatMap(FlatHandler::flatResult);
    }

    /**
     * 查询即将上映映movie
     *
     * @param star  int 开始数
     * @param count 查询数量，传入0 为默认数量
     */
    public Observable<MovieResult> getMovieComingSoon(int star, int count) {
        return mApi.getMovieComingSoon(star, count).flatMap(FlatHandler::flatResult);
    }

    /**
     * 查询Top250
     *
     * @param star  int 开始数
     * @param count 查询数量，传入0 为默认数量
     */
    public Observable<MovieResult> getMovieTop250(int star, int count) {
        return mApi.getMovieTop250(star, count).flatMap(FlatHandler::flatResult);
    }

    public Observable<Movie> getMovieById(String movie_id) {
        return mApi.getMovieById(movie_id).flatMap(FlatHandler::flatResult);
    }

    public Observable<Celebrity> getCelebrityDetail(String celebrity_id) {
        return mApi.getCelebrityDetail(celebrity_id).flatMap(FlatHandler::flatResult);
    }

    public Observable<HtmlResult> getComment() {
        return mApi.getComment();
    }

}
