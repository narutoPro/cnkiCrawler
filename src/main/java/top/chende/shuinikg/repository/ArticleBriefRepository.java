package top.chende.shuinikg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import top.chende.shuinikg.dto.ArticleBriefQueryDTO;
import top.chende.shuinikg.model.ArticleBrief;

import java.util.List;

/**
 * @author: chende
 * @date: 2019/7/25 09:42
 * @description:
 */
public interface ArticleBriefRepository extends JpaRepository<ArticleBrief, Long> {

 //todo

    /**
     * 查找所有ArticleBrief id
     * @return
     */
//    @Query(value = "select   from  ArticleBrief ")
//    List<Long> findAllId();

    @Query(value = "select new ArticleBrief (id ,  detailPath)  from ArticleBrief ")
    List<ArticleBrief> findQueryDTO();

    @Query(value = "SELECT * from article_brief where id in (SELECT  acticle_brief_id FROM  article_detail  where  article_abstract  IS NULL) ",nativeQuery = true)
    List<ArticleBrief> findAllByArticleDetailAbstractIsNull();
}

