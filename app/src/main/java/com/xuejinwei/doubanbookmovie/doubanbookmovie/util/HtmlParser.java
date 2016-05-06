package com.xuejinwei.doubanbookmovie.doubanbookmovie.util;

import com.orhanobut.logger.Logger;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.Comments;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.Reviews;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuejinwei on 16/5/5.
 * Email:xuejinwei@outlook.com
 * html抽取
 */
public class HtmlParser {

    /**
     * 获取短评评论列表
     *
     * @param htmlResult htmlresult返回
     * @return comments
     */
    public static List<Comments> getMovieCommentList(String htmlResult) {
        List<Comments> data = new ArrayList<>();

        Document doc = Jsoup.parse(htmlResult);
        Element commentBody = doc.select("div.article").get(0);

        if (commentBody == null) {
            return data;
        }
        Document document = Jsoup.parse(commentBody.toString());
        Element list = document.select("div.mod-bd").first();
        if (list == null)
            return data;
        Logger.i(list.toString());
        Elements comments = list.select("div.comment-item");
        Logger.i(comments.toString());
        if (comments == null) {
            return data;
        }
        for (Element item : comments) {
            Comments comment = new Comments();
            Element avatar = item.select("a").first();
            Element title = item.select("p").first();
            comment.comment = title.text();
            Element img = avatar.select("img").first();
            comment.img = img.attr("src");
            Element comment_info = item.select("span.comment-info").first();
            Logger.i(comment_info.toString());
            Elements span = comment_info.select("span");
            String rating = "";
            if (span.size() != 3) {
                rating = "0";
            } else {
                Logger.i(span.size() + "");
                rating = span.get(1).attr("class");
                rating = rating.substring(7, 9);
                Logger.i(span.get(0).toString());
                Logger.i( span.get(1).toString() + "-" + rating);
                Logger.i(span.get(2).toString());
            }
            Element span_votes = item.select("span.votes").first();
            String vote = "";
            if (span_votes != null) {
                vote = item.select("span.votes").first().text();
            }
            String time = span.get(span.size() - 1).text();
            comment.time = time;
            comment.rating = rating;
            comment.title = avatar.attr("title");
            comment.id = item.attr("mData-cid");
            comment.comment_vote = vote;
            data.add(comment);
        }
        return data;
    }

    public static List<Comments> getBookCommentList(String htmlResult) {
        List<Comments> data = new ArrayList<>();

        Document document = Jsoup.parse(htmlResult.toString());
        Element list = document.select("div.mod-bd").first();
        if (list == null)
            return data;
        Elements comments = list.select("div.comment-item");
        if (comments == null) {
            return data;
        }
        for (Element item : comments) {
            Comments comment = new Comments();
            Element avatar = item.select("a").first();
            Element title = item.select("p").first();
            comment.comment = title.text();
            Element img = avatar.select("img").first();
            comment.img = img.attr("src");
            Element comment_info = item.select("span.comment-info").first();
            Elements span = comment_info.select("span");
            String rating = "";
            if (span.size() != 3) {
                rating = "0";
            } else {
                rating = span.get(1).attr("class");
                rating = rating.substring(7, 9);
            }
            Element span_votes = item.select("span.votes").first();
            String vote = "";
            if (span_votes != null) {
                vote = item.select("span.votes").first().text();
            }
            String time = span.get(span.size() - 1).text();
            comment.time = time;
            comment.rating = rating;
            comment.title = avatar.attr("title");
            comment.id = item.attr("mData-cid");
            comment.comment_vote = vote;
            data.add(comment);
        }
        return data;
    }

    /**
     * 获取影评列表
     *
     * @param src source
     * @return mData
     */
    public static List<Reviews> getBookReviewList(String src) {
        List<Reviews> data = new ArrayList<>();
        Document doc = Jsoup.parse(src);
        Element content = doc.select("div.article").first();
        if (content == null) {
            return data;
        }
        Elements reviews = content.select("div.review");
        for (Element review : reviews) {
            Reviews bean = new Reviews();
            Element review_hd_avatar = review.select("a.review-hd-avatar").first();
            bean.name = review_hd_avatar.attr("title");
            bean.img = review_hd_avatar.child(0).attr("src");
            Element h3 = review.select("h3").first();
            Element a = h3.child(h3.children().size() - 1);
            bean.title = a.text();
            bean.link = a.attr("href");
            Element review_hd_info = review.select("div.review-hd-info").first();
            //review_hd_info.ownText()
            bean.time = review_hd_info.ownText();
            Element span = review_hd_info.select("span").first();
            String rating = span.attr("class").substring(7);
            if (rating.equals("None0")) {
                rating = "0";
            }
            bean.rating = rating;
            Element review_short = review.select("div.review-short").first();
            Element review_short_span = review_short.select("span").first();
            bean.review = review_short_span.text();
            Element review_short_ft = review.select("div.review-short-ft").first();
            Element review_short_ft_span = review_short_ft.select("span").first();
            bean.useful = review_short_ft_span.text();
            data.add(bean);
        }
        return data;
    }
}
