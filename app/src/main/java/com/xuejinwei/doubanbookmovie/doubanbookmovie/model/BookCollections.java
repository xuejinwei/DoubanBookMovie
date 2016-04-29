package com.xuejinwei.doubanbookmovie.doubanbookmovie.model;

/**
 * Created by xuejinwei on 16/4/25.
 * Email:xuejinwei@outlook.com
 */
public class BookCollections {
    public String   status;//状态，
    public  String   comment;//评价
    public  String   updated;// 更新时间
    public  String   user_id;
    public  String[] tags;
    public  Rating   rating;//只有value 和max，没有count
    public  Book     book;
    public  String   book_id;
    public  String   id;
    public  Me       user;//有些字段没有，后面标注

    public String getStatus() {
        if (status.equals("wish")) {
            return "想读";
        }
        if (status.equals("reading")) {
            return "在读";
        }
        if (status.equals("read")) {
            return "读过";
        }

        return " ";
    }


}
