package top.chende.shuinikg.dto;

import top.chende.shuinikg.model.ArticleBrief;
import top.chende.shuinikg.model.ArticleDetail;

import java.io.Serializable;

/**
 * @author: chende
 * @date: 2019/7/25 14:54
 * @description:
 */
public class ArticleInfoView implements Serializable {

    private ArticleDetail articleDetail;
    private ArticleBrief articleBrief;

    public ArticleInfoView() {
    }

    public ArticleInfoView(ArticleDetail articleDetail, ArticleBrief articleBrief) {
        this.articleDetail = articleDetail;
        this.articleBrief = articleBrief;
    }

    public ArticleDetail getArticleDetail() {
        return articleDetail;
    }

    public void setArticleDetail(ArticleDetail articleDetail) {
        this.articleDetail = articleDetail;
    }

    public ArticleBrief getArticleBrief() {
        return articleBrief;
    }

    public void setArticleBrief(ArticleBrief articleBrief) {
        this.articleBrief = articleBrief;
    }
}
