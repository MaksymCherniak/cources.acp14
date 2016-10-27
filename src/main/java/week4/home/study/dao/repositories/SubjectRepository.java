package week4.home.study.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import week4.home.study.entity.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    @Query("SELECT s FROM Subject s WHERE s.name LIKE :name AND s.description LIKE :description")
    Subject getSubject(@Param("name") String name,
                       @Param("description") String description);

    @Modifying
    @Transactional
    @Query("UPDATE Subject c set c.name=:name, c.description=:description WHERE subject_id LIKE :subject_id")
    void updateSubject(@Param("name") String name,
                       @Param("description") String description,
                       @Param("subject_id") long subject_id);
}
