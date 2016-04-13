package com.xuejinwei.doubanbookmovie.doubanbookmovie.model;

import java.util.List;

/**
 * Created by xuejinwei on 16/4/13.
 * Email:xuejinwei@outlook.com
 * SubjectCollectionItems 移动端豆瓣数据抽取subject_collection返回的item，类型有movie，book
 */
public class SubjectCollectionItems {
    public Rating       rating;
    public List<String> actions;//事件，可播放……
    public Cover        cover;// 头像
    public String       original_price;
    public String       description;
    public String       price;
    public String       date;
    public String       id;
    public String       info;
    public String       title;
    public String       url;
    public String       uri;
    public String       subtype;
    public String       type;

}
