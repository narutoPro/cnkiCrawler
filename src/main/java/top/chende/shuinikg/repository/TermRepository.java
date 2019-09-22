package top.chende.shuinikg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.chende.shuinikg.model.Term;

/**
 * @author: chende
 * @date: 2019/7/22 22:59
 * @description:
 */
public interface TermRepository extends JpaRepository<Term,Long> {
}
