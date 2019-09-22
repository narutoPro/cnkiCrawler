package top.chende.shuinikg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import top.chende.shuinikg.dto.ArticleInfoView;
import top.chende.shuinikg.dto.ArticleSumDTO;
import top.chende.shuinikg.model.ArticleDetail;
import top.chende.shuinikg.model.ArticleBrief;
import top.chende.shuinikg.dto.ArticleInfoView;

import java.util.List;

/**
 * //todo  HQL问题很多
 * @author chende
 */
public interface ArticleDetailRepository extends JpaRepository<ArticleDetail, Long> {

    @Query(value = "select new top.chende.shuinikg.dto.ArticleSumDTO(a.id,a.articleAbstract) from  ArticleDetail a")
    List<ArticleSumDTO> findDto();

    /**
     * todo HQL查询有问题
     * 查找ArticleDetail表中所对应所有的acticleBriefId
     *
     * @return
     */
    //@Query(value = "select new top.chende.shuinikg.model.ArticleDetail (at.acticleBriefId) from ArticleDetail at ")
    @Query(value = "select  a.acticle_brief_id  from article_detail a", nativeQuery = true)
    public List<ArticleDetail> findAllBriefId();

    /**
     * 查找所有ArticleAbstractIsNull 的ArticleDetail记录
     *
     * @return
     */
    public List<ArticleDetail> findAllByArticleAbstractIsNull();

    /**
     * @param clc 中图分类号
     * @return
     */
    public List<ArticleDetail> findAllByClcContaining(String clc);


    @Query(value = "SELECT  detail.* FROM article_detail  as detail left join  article_brief  brief  on brief.id=detail.acticle_brief_id   WHERE brief.publish_date LIKE :publishyear ",nativeQuery = true)
    public List<ArticleDetail> findByPublishYear(@Param("publishyear") String publishyear);


    public List<ArticleDetail> findAllByClcNotContaining(String clc);


  //  @Query(value = " select new top.chende.shuinikg.dto.ArticleInfoViem(detail,brief) from ArticleDetail detail left join ArticleBrief brief on detail.acticleBriefId=brief.id")
   // public List<ArticleInfoView> findAriticleViews();

}
