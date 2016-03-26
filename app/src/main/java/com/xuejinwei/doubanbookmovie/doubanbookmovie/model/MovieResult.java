package com.xuejinwei.doubanbookmovie.doubanbookmovie.model;

import java.util.List;

/**
 * Created by xuejinwei on 16/3/24.
 * 电影列表的返回结果，
 */
public class MovieResult {
    public int               count;//返回个数
    public int               start;//起始数
    public int               total;//总数
    public List<MovieSimple> subjects;
    public String            title;//标题
}
