package com.xuejinwei.doubanbookmovie.kotlin.model;

import java.util.List;

/**
 * Created by xuejinwei on 16/4/19.
 * Email:xuejinwei@outlook.com
 */
public class Book {
    public String id;
    public String isbn10;
    public String isbn13;
    public String title;
    public String origin_title;
    public String alt_title;
    public String subtitle;
    public String url;
    public String alt;
    public String image;//对应medium
    public String publisher;
    public String pubdate;
    public String binding;
    public String price;
    public String pages;
    public String author_intro;
    public String summary;
    public String catalog;
    public String ebook_url; //该字段只在存在对应电子书时提供
    public String ebook_price; //该字段只在存在对应电子书时提供

    public String[]   author;
    public String[]   translator;
    public BookRating rating;
    public BookSeries series;
    public Images     images;
    public List<Tags> tags;

    public String getAuthor() {

        String string = "";
        for (String anAuthor : author) {
            string = string + anAuthor + "\n\n";
        }
        return string;

    }
}
