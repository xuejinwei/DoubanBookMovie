package com.xuejinwei.doubanbookmovie.doubanbookmovie.api;

import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.Movie;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.MovieResult;

import rx.Observable;

/**
 * Created by xuejinwei on 16/3/24.
 */
public class ApiWrapper {
    Api mApi = ApiFactory.getApi();

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

    public Observable<Movie> getMovieById(String movie_id) {
        return mApi.getMovieById(movie_id).flatMap(FlatHandler::flatResult);
    }
}
