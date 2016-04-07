package com.xuejinwei.doubanbookmovie.doubanbookmovie.api;

import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.Celebrity;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.HtmlResult;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.Movie;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.MovieResult;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.Result;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by xuejinwei on 16/3/23.
 */
public interface Api {

    /**
     * 查询正在热映movie
     *
     * @param start int 开始数
     * @param count 查询数量，传入0 为默认数量
     * @return
     */
    @GET("movie/in_theaters")
    Observable<Result<MovieResult>> getMovieInTheaters(@Query("start") int start, @Query("count") int count);

    /**
     * 查询即将上映映movie
     *
     * @param start int 开始数
     * @param count 查询数量，传入0 为默认数量
     * @return
     */
    @GET("movie/coming_soon")
    Observable<Result<MovieResult>> getMovieComingSoon(@Query("start") int start, @Query("count") int count);

    /**
     * 查询TOP250
     *
     * @param start int 开始数
     * @param count 查询数量，传入0 为默认数量
     * @return
     */
    @GET("movie/top250")
    Observable<Result<MovieResult>> getMovieTop250(@Query("start") int start, @Query("count") int count);

    @GET("movie/subject/{id}")
    Observable<Result<Movie>> getMovieById(@Path("id") String id);

    @GET("movie/celebrity/{id}")
    Observable<Result<Celebrity>> getCelebrityDetail(@Path("id") String id);

    @GET("https://movie.douban.com/subject/24750534/comments?start=0&limit=20&sort=new_score")
    Observable<HtmlResult> getComment();

    @GET("book/25843319/collection")
    Observable<Result<String>> getCollectionDetail(@Header("Authorization") String Authorization);
}
