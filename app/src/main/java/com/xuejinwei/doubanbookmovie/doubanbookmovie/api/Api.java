package com.xuejinwei.doubanbookmovie.doubanbookmovie.api;

import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.Book;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.BookCollections;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.BookResult;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.Celebrity;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.CollectionsResult;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.HtmlResult;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.Me;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.Movie;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.MovieResult;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.OAuthResult;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.Result;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.SubjectCollectionResult;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.Success;

import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by xuejinwei on 16/4/7.
 * Email:xuejinwei@outlook.com
 */
public interface Api {

    @FormUrlEncoded
    @POST("https://www.douban.com/service/auth2/token")
    Observable<Result<OAuthResult>> getOauthResult(@Field("client_id") String client_id,
                                                   @Field("client_secret") String client_secret,
                                                   @Field("redirect_uri") String redirect_uri,
                                                   @Field("grant_type") String grant_type,
                                                   @Field("code") String authorization_code);

    @FormUrlEncoded
    @POST("https://www.douban.com/service/auth2/token")
    Observable<Result<OAuthResult>> refreshOauthResult(@Field("client_id") String client_id,
                                                       @Field("client_secret") String client_secret,
                                                       @Field("redirect_uri") String redirect_uri,
                                                       @Field("grant_type") String grant_type,
                                                       @Field("refresh_token") String refresh_token);

    @GET("user/~me")
    Observable<Result<Me>> getMeInformation(@Header("Authorization") String Authorization);

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


    @GET("movie/search")
    Observable<Result<MovieResult>> searchMovie(@Query("start") int start, @Query("count") int count, @Query("q") String q);

    @GET("book/search")
    Observable<Result<BookResult>> searchBook(@Query("start") int start, @Query("count") int count, @Query("q") String q);

    @GET("movie/subject/{id}")
    Observable<Result<Movie>> getMovieById(@Path("id") String id);

    @GET("book/{id}")
    Observable<Result<Book>> getBookById(@Path("id") String id);

    @GET("movie/celebrity/{id}")
    Observable<Result<Celebrity>> getCelebrityDetail(@Path("id") String id);

    @GET("https://movie.douban.com/subject/{id}/comments")
    Observable<HtmlResult> getMovieComment(@Path("id") String id, @Query("start") int start, @Query("limit") int limit, @Query("sort") String sortType);

    @GET("https://movie.douban.com/subject/{id}/reviews")
    Observable<HtmlResult> getMovieReviews(@Path("id") String id, @Query("start") int start, @Query("limit") int limit, @Query("sort") String sortType);

    @GET("https://book.douban.com/subject/{id}/comments/hot")
    Observable<HtmlResult> getBookComment(@Path("id") String id);

    @GET("https://book.douban.com/subject/{id}/reviews")
    Observable<HtmlResult> getBookReviews(@Path("id") String id, @Query("start") int start, @Query("limit") int limit, @Query("sort") String sortType);

    @GET("book/25843319/collection")
    Observable<Result<String>> getCollectionDetail(@Header("Authorization") String Authorization);

    @GET("https://frodo.douban.com/jsonp/subject_collection/{type}/items")
    Observable<Result<SubjectCollectionResult>> getSubjectCollection(@Path("type") String type, @Query("start") int start, @Query("count") int count);

    @GET("book/user/{user}/collections")
    Observable<Result<CollectionsResult>> getBookCollections(@Path("user") String user, @Header("Authorization") String Authorization);

    @GET("book/{book_id}/collection")
    Observable<Result<BookCollections>> getBookCollectionsDetail(@Path("book_id") String user, @Header("Authorization") String Authorization);

    @FormUrlEncoded
    @POST("book/{book_id}/collection")
    Observable<Result<BookCollections>> addBookCollection(@Header("Authorization") String Authorization, @Path("book_id") String book_id, @Field("status") String status, @Field("comment") String comment, @Field("rating") String rating);

    @FormUrlEncoded
    @PUT("book/{book_id}/collection")
    Observable<Result<BookCollections>> updateBookCollection(@Header("Authorization") String Authorization, @Path("book_id") String book_id, @Field("status") String status, @Field("comment") String comment, @Field("rating") String rating);

    @DELETE("book/{book_id}/collection")
    Observable<Result<Success>> deleteBookCollection(@Header("Authorization") String Authorization, @Path("book_id") String book_id);
}
