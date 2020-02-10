package top.chende.shuinikg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.chende.shuinikg.model.CementPage;
import top.chende.shuinikg.repository.CementPageRepository;
import top.chende.shuinikg.repository.OtherPlatformPageRepository;
import top.chende.shuinikg.repository.OtherPlatformWebContentRepository;
import top.chende.shuinikg.util.CementWebCrawl;

import javax.naming.ldap.PagedResultsControl;

/**
 * @author: chende
 * @date: 2019/11/18 11:08
 * @description: OtherPlatformCrawl逻辑代码
 */
/*其他平台  爬取网页的处理逻辑 页面的存取  解析 处理*/

@Service
public class OtherPlatformCrawlService {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    CementPageRepository cementPageRepository;


    CementWebCrawl cementWebCrawl;

    public CementPage cementdetail(Long id){
        return cementPageRepository.findById(id).get();
    }

    public  void firstStartCrawl(){
        cementWebCrawl=new CementWebCrawl(this.redisTemplate,this.cementPageRepository);
        //if (cementWebCrawl)
        cementWebCrawl.init();
    }

    public void stop(){
        cementWebCrawl.stopCrawl();
    }
    public void restart(){
        if (cementWebCrawl==null) cementWebCrawl=new CementWebCrawl(this.redisTemplate,this.cementPageRepository);
        cementWebCrawl.reStart();
    }

    public void shutdown(){
        cementWebCrawl.shutdownCrawl();
    }



}
