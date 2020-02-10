package top.chende.shuinikg;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.chende.shuinikg.model.CementPage;
import top.chende.shuinikg.repository.CementPageRepository;

import java.util.Iterator;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShuinikgApplicationTests {

	@Autowired
	CementPageRepository cementPageRepository;

	@Test
	public void contextLoads() {
	  CementPage page=cementPageRepository.findById(new Long(24431)).get();

	  //jsoup  selenium解析
		String html=page.getHtml();
		Document document= Jsoup.parse(html);
		System.out.println("url:"+page.getUrl());

		System.out.println("######分割###########");
		System.out.println("######分割###########");
		System.out.println("######分割###########");
//		Elements elements=document.getAllElements();
//		Iterator<Element>  iterator=elements.iterator();
//		while(iterator.hasNext()){
//			Element e=iterator.next();
//			System.out.println(e.text());
//		}

		Element body=document.getElementsByTag("body").first();
		System.out.println(body.text());
		System.out.println("######分割###########");
		System.out.println("######分割###########");
		System.out.println("######分割###########");



	}

}
