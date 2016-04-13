package com.xuejinwei.doubanbookmovie.doubanbookmovie.model;

import java.util.List;

/**
 * Created by xuejinwei on 16/4/13.
 * Email:xuejinwei@outlook.com
 * SubjectCollectionItems 移动端豆瓣数据抽取subject_collection返回的内容
 */
public class SubjectCollectionResult {
    public int                          count;
    public int                          start;
    public int                          total;
    public List<SubjectCollectionItems> subject_collection_items;
    public SubjectCollection            subject_collection;
}
