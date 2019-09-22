package top.chende.shuinikg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.chende.shuinikg.repository.ArticleDetailRepository;

/**
 * @author: chende
 * @date: 2019/7/26 14:37
 * @description:
 */
@Service
public class ArticleBriefService {
    @Autowired
    ArticleDetailRepository articleDetailRepository;


}
