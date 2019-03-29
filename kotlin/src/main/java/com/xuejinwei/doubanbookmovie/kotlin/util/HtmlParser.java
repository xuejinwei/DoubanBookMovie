package com.xuejinwei.doubanbookmovie.kotlin.util;

import com.xuejinwei.doubanbookmovie.kotlin.model.Comments;
import com.xuejinwei.doubanbookmovie.kotlin.model.Reviews;
import com.xuejinwei.doubanbookmovie.kotlin.model.ReviewsDetail;

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

    public static List<Comments> getCommentList(String htmlResult) {
        List<Comments> data = new ArrayList<>();
        Document doc = Jsoup.parse(htmlResult);
        Element review_list_parent = doc.select("div.card>section>ol.comments").first();
        if (review_list_parent == null) {
            return data;
        }
        Elements comments = review_list_parent.getElementsByClass("comment-item");

        for (Element comment : comments) {
            Comments bean = new Comments();

            bean.name = comment.select("div.comment-author>img.avatar").first().attr("alt");
            bean.img = comment.select("div.comment-author>img.avatar").first().attr("src");
            bean.comment = comment.select("p.comment-content").first().ownText();
            bean.comment_vote = comment.select("div.comment-info>span.vote").first().ownText();
            bean.time = comment.select("div.comment-info>span.time").first().ownText();
            int rating = (int) Double.parseDouble(comment.select("div.comment-author>span.rating-stars").first().attr("data-rating"));
            bean.rating = rating / 20;
            data.add(bean);
        }
        return data;
    }

    /**
     * 获取影评列表
     *
     * @param src source
     * @return mData
     */
    public static List<Reviews> getReviewList(String src) {
        List<Reviews> data = new ArrayList<>();
        Document doc = Jsoup.parse(src);
        Element review_list_parent = doc.select("div.card>section.review-list>div.bd>ul.list").first();
        if (review_list_parent == null) {
            return data;
        }
        Elements reviews = review_list_parent.getElementsByTag("li");
        ;
        for (Element review : reviews) {
            Reviews bean = new Reviews();
            Element a = review.child(0);

            bean.img = a.select("div>img").first().attr("src");
            bean.title = a.select("h3").first().ownText();
            bean.rating = Integer.parseInt(a.select("div>span").first().attr("data-rating")) / 20;
            bean.useful = a.select("div.info").first().ownText();
            bean.link = a.attr("href");

            data.add(bean);
        }
        return data;
    }

    public static ReviewsDetail getReviewDetail(String src) {
        ReviewsDetail data = new ReviewsDetail();
        Document doc = Jsoup.parse(src);
        Element card = doc.select("div.card").first();
        Element user = card.select("div.user-title").first();
        Element paperfull = card.select("section.paper>div.full").first();

        if (paperfull!=null){
            data.reviews_text = paperfull.ownText();
        }else {
            data.reviews_text = card.select("section.paper").first().ownText();
        }
        data.title = card.select("h1.title").first().ownText();

        data.user_img = user.select("img").first().attr("src");
        data.user_name = user.select("span").first().ownText();
        data.rating = Integer.parseInt(user.select("span.rating-stars").first().attr("data-rating")) / 20;

        return data;
    }
}
