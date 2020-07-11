package top.chende.shuinikg.modules.cnki;

import lombok.Data;
import top.chende.shuinikg.model.ArticleBrief;
import top.chende.shuinikg.model.ArticleDetail;

/**
 * 复用之前的代码吧
 * 一篇文章的信息 由brief和detail构成
 */
@Data
public class CnkiDTO {

    ArticleBrief articleBrief;

    ArticleDetail articleDetail;
}
