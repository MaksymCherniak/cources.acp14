package week4.home.study.dao.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import week4.home.study.entity.Subject;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    @Query("SELECT s FROM Subject s WHERE s.name = :name AND s.description = :description")
    Subject getSubject(@Param("name") String name,
                       @Param("description") String description);

    @Query("SELECT s FROM Subject s WHERE s.name = :name")
    Subject getSubjectByName(@Param("name") String name);

    @Query("SELECT s FROM Subject s WHERE s.name LIKE :name")
    List<Subject> getAllSubjectsByNameLike(@Param("name") String name, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE Subject c set c.name=:name, c.description=:description WHERE subject_id = :subject_id")
    void updateSubject(@Param("name") String name,
                       @Param("description") String description,
                       @Param("subject_id") long subject_id);
}
