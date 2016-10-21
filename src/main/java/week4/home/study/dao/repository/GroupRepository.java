package week4.home.study.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import week4.home.study.entity.Groups;

public interface GroupRepository extends JpaRepository<Groups, Long> {
}
