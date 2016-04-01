package com.xuejinwei.doubanbookmovie.doubanbookmovie.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by xuejinwei on 16/3/26.
 * Email:xuejinwei@outlook.com
 * 电影实体
 */
public class Movie {
    public String                      id;
    public String                      title;
    public String                      original_title;
    public List<String>                aka;  //又名
    public String                      alt;  //条目页URL
    public String                      mobile_url;  //移动版条目页URL，预告片
    public String                      share_url;//分享的URL，        上映时间，时长，预告片，短评3个，影评四个和入口
    public String                      schedule_url;//	影讯页URL(movie only)购票
    public Images                      images;  //电影海报图，分别提供288px x 465px(大)，96px x 155px(中) 64px x 103px(小)尺寸
    public MovieRating                 rating;  //评分
    public String                      ratings_count;  //评分人数
    public String                      wish_count;  //想看人数
    public String                      collect_count;  //看过人数
    public String                      comments_count;  //短评条数
    public String                      reviews_count;  // 影评条数
    public List<SimpleCelebrity>       directors;  //导演
    public LinkedList<SimpleCelebrity> casts;  //主演，最多四个
    public String                      douban_site;  //豆瓣小站
    public String                      year;
    public List<String>                genres; //影片类型，最多提供3个
    public List<String>                countries;  //制片国家/地区
    public String                      summary;//简介

    /**
     * @return 电影类型字符串，中间以空格分隔
     */
    public String getGenres() {
        String strGenres = "";

        for (int i = 0; i < genres.size(); i++) {
            if (i == 0) {
                strGenres = genres.get(i);
            } else {
                strGenres = strGenres + "\t\t" + genres.get(i);
            }
        }
        return strGenres;
    }

    public String getDirectors() {
        String strDirectors = "";
        for (int i = 0; i < directors.size(); i++) {
            if (i == 0) {
                strDirectors = directors.get(i).name;
            } else {
                strDirectors = strDirectors + "\t\t" + directors.get(i).name;
            }
        }
        return strDirectors;
    }

    public String getCasts() {
        String strCasts = "";
        for (int i = 0; i < casts.size(); i++) {
            if (i == 0) {
                strCasts = casts.get(i).name;
            } else {
                strCasts = strCasts + "\t\t" + casts.get(i).name;
            }
        }
        return strCasts;
    }
}
