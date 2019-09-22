package top.chende.shuinikg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "top.chende.shuinikg.repository")
public class ShuinikgApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShuinikgApplication.class, args);
	}

}
