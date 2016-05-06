package com.xuejinwei.doubanbookmovie.doubanbookmovie.api;

import com.xuejinwei.doubanbookmovie.doubanbookmovie.app.Setting;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.Book;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.BookCollections;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.BookResult;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.Celebrity;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.CollectionUpdate;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.CollectionsResult;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.Comments;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.Me;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.Movie;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.MovieResult;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.OAuthResult;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.Reviews;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.SubjectCollectionResult;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.Success;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.util.HtmlParser;

import java.util.List;

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

    public Observable<MovieResult> searchMovie(int star, int count, String q) {
        return mApi.searchMovie(star, count, q).flatMap(FlatHandler::flatResult);
    }

    public Observable<BookResult> searchBook(int star, int count, String q) {
        return mApi.searchBook(star, count, q).flatMap(FlatHandler::flatResult);
    }

    public Observable<Movie> getMovieById(String movie_id) {
        return mApi.getMovieById(movie_id).flatMap(FlatHandler::flatResult);
    }

    public Observable<Book> getBookById(String book_id) {
        return mApi.getBookById(book_id).flatMap(FlatHandler::flatResult);
    }

    public Observable<Celebrity> getCelebrityDetail(String celebrity_id) {
        return mApi.getCelebrityDetail(celebrity_id).flatMap(FlatHandler::flatResult);
    }

    public Observable<List<Comments>> getMovieComment(String id, int start, int limit, String sortType) {
        return mApi.getMovieComment(id, start, limit, sortType).map(htmlResult -> {
            List<Comments> commentsList = HtmlParser.getMovieCommentList(htmlResult.htmlBody);
            return commentsList;
        });
    }

    public Observable<List<Reviews>> getMovieReviews(String id, int start, int limit, String sortType) {
        return mApi.getMovieReviews(id, start, limit, sortType).map(htmlResult -> {
            List<Reviews> data = HtmlParser.getBookReviewList(htmlResult.htmlBody);
            return data;
        });
    }

    public Observable<List<Comments>> getBookComment(String id, int start, int limit, String sortType) {
        return mApi.getBookComment(id, start, limit, sortType).map(htmlResult -> {
            List<Comments> commentsList = HtmlParser.getBookCommentList(htmlResult.htmlBody);
            return commentsList;
        });
    }

    public Observable<List<Reviews>> getBookReviews(String id, int start, int limit, String sortType) {
        return mApi.getBookReviews(id, start, limit, sortType).map(htmlResult -> {
            List<Reviews> data = HtmlParser.getBookReviewList(htmlResult.htmlBody);
            return data;
        });
    }

    /**
     * 通过移动端豆瓣网页根据类型获取subjectcollection，
     *
     * @param type  类型，{@link com.xuejinwei.doubanbookmovie.doubanbookmovie.model.SubjectCollectionType}有book_nonfiction，book_fiction，filter_movie_classic_hot，movie_latest…………等等
     * @param star  返回结果起始值
     * @param count 返回结果个数
     * @return 返回有movie 和book
     */
    public Observable<SubjectCollectionResult> getSubjectCollection(String type, int star, int count) {
        return mApi.getSubjectCollection(type, star, count).flatMap(FlatHandler::flatResult);
    }

    public Observable<CollectionsResult> getBookCollections(String userid) {
        return mApi.getBookCollections(userid, Setting.getSetting(Setting.Key.access_token, "")).flatMap(FlatHandler::flatResult);
    }

    public Observable<BookCollections> getBookCollectionsDetail(String bookid) {
        return mApi.getBookCollectionsDetail(bookid, Setting.getSetting(Setting.Key.access_token, "")).flatMap(FlatHandler::flatResult);
    }

    public Observable<BookCollections> addBookCollections(String bookid, CollectionUpdate collectionUpdate) {
        return mApi.addBookCollection(Setting.getSetting(Setting.Key.access_token, ""), bookid, collectionUpdate.status, collectionUpdate.comment, collectionUpdate.rating).flatMap(FlatHandler::flatResult);
    }

    public Observable<BookCollections> updateBookCollections(String bookid, CollectionUpdate collectionUpdate) {
        return mApi.updateBookCollection(Setting.getSetting(Setting.Key.access_token, ""), bookid, collectionUpdate.status, collectionUpdate.comment, collectionUpdate.rating).flatMap(FlatHandler::flatResult);
    }

    public Observable<Success> deleteBookCollections(String bookid) {
        return mApi.deleteBookCollection(Setting.getSetting(Setting.Key.access_token, ""), bookid).flatMap(FlatHandler::flatToSuccess);
    }
}
