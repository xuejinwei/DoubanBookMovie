package com.xuejinwei.doubanbookmovie.kotlin.model;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

/**
 * Created by xuejinwei on 16/5/17.
 * Email:xuejinwei@outlook.com
 */
@AVClassName("MovieCollection")
public class MovieCollections extends AVObject {

    //此处为我们的默认实现，当然你也可以自行实现
    public static final Creator CREATOR = AVObjectCreator.instance;

    public String getUserId() {
        return getString("userId");
    }

    public void setUserId(String userId) {
        put("userId", userId);
    }

    public String getMovieId() {
        return getString("movieId");
    }

    public void setMovieId(String movieId) {
        put("movieId", movieId);
    }

    public String getMovieName() {
        return getString("movieName");
    }

    public void setMovieName(String movieName) {
        put("movieName", movieName);
    }

    public String getMovieImg() {
        return getString("movieImg");
    }

    public void setMovieImg(String movieImg) {
        put("movieImg", movieImg);
    }

    public int getMovieRating() {
        return getInt("movieRating");
    }

    public void setMovieRating(int movieRating) {
        put("movieRating", movieRating);
    }

    public int getMovieCollectionType() {
        return getInt("movieCollectionType");
    }

    public void setMovieCollectionType(int movieCollectionType) {
        put("movieCollectionType", movieCollectionType);
    }

    public String getMovieCollectionText() {
        return getString("movieCollectionText");
    }

    public void setMovieCollectionText(String movieCollectionText) {
        put("movieCollectionText", movieCollectionText);
    }

}
