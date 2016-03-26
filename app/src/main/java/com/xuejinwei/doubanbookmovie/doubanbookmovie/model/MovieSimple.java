package com.xuejinwei.doubanbookmovie.doubanbookmovie.model;

import java.util.List;

/**
 * Created by xuejinwei on 15/12/25.
 * mMovieRating : {"max":10,"average":8,"stars":"40","min":0}
 * genres : ["剧情","动作","奇幻"]
 * collect_count : 100733
 * casts : [{"avatars":{"small":"https://img1.doubanio.com/img/celebrity/small/1327.jpg","large":"https://img1.doubanio.com/img/celebrity/large/1327.jpg","medium":"https://img1.doubanio.com/img/celebrity/medium/1327.jpg"},"alt":"http://movie.douban.com/celebrity/1053618/","id":"1053618","name":"陈坤"},{"avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/1656.jpg","large":"https://img3.doubanio.com/img/celebrity/large/1656.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/1656.jpg"},"alt":"http://movie.douban.com/celebrity/1274242/","id":"1274242","name":"黄渤"},{"avatars":{"small":"https://img1.doubanio.com/img/celebrity/small/1268.jpg","large":"https://img1.doubanio.com/img/celebrity/large/1268.jpg","medium":"https://img1.doubanio.com/img/celebrity/medium/1268.jpg"},"alt":"http://movie.douban.com/celebrity/1138320/","id":"1138320","name":"舒淇"}]
 * title : 寻龙诀
 * original_title : 寻龙诀
 * subtype : movie
 * directors : [{"avatars":{"small":"https://img1.doubanio.com/img/celebrity/small/1361431845.09.jpg","large":"https://img1.doubanio.com/img/celebrity/large/1361431845.09.jpg","medium":"https://img1.doubanio.com/img/celebrity/medium/1361431845.09.jpg"},"alt":"http://movie.douban.com/celebrity/1275211/","id":"1275211","name":"乌尔善"}]
 * year : 2015
 * images : {"small":"https://img1.doubanio.com/view/movie_poster_cover/ipst/public/p2284620292.jpg","large":"https://img1.doubanio.com/view/movie_poster_cover/lpst/public/p2284620292.jpg","medium":"https://img1.doubanio.com/view/movie_poster_cover/spst/public/p2284620292.jpg"}
 * alt : http://movie.douban.com/subject/3077412/
 * id : 3077412
 */

public class MovieSimple {

    public String id;
    public String title;
    public String alt;
    public String year;
    public String collect_count;
    public String original_title;
    public String subtype;

    public MovieRating rating;
    public Images images;
    public List<String> genres;
    public List<SimpleCelebrity> casts;
    public List<SimpleCelebrity> directors;

}
