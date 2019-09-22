package top.chende.shuinikg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.chende.shuinikg.model.SearchPage;

import java.util.List;

public interface SearchPageRepository extends JpaRepository<SearchPage,Long> {


     List<SearchPage> findAllByIsParsedIsNull() ;
}
