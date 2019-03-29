package com.xuejinwei.doubanbookmovie.kotlin.model;

import java.util.List;

/**
 * Created by xuejinwei on 16/4/1.
 * Email:xuejinwei@outlook.com
 */
public class Celebrity {

    public String       id;
    public String       name;
    public String       name_en;
    public String       alt;
    public String       mobile_url;
    public Images       avatars; // 影人头像，分别提供420px x 600px(大)，140px x 200px(中) 70px x 100px(小)尺寸
    public String       summary;//简介,不返回
    public List<String> aka;//更多中文名
    public List<String> aka_en;
    public String       gender;
    public String       born_place;
    public List<Work>   works;
}
