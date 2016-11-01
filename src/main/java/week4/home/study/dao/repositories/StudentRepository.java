package week4.home.study.dao.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import week4.home.study.entity.Student;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT s FROM Student s WHERE s.name = :name AND group_id = :group_id")
    Student getStudent(@Param("name") String name,
                       @Param("group_id") long group_id);

    @Query("SELECT s FROM Student s WHERE s.name = :name")
    Student getStudentByName(@Param("name") String name);

    @Query("SELECT s FROM Student s WHERE group_id = :group_id")
    List<Student> getStudentsByGroup(@Param("group_id") long group_id, Pageable pageable);

    @Query("SELECT s FROM Student s WHERE s.name LIKE :name")
    List<Student> getStudentsByNameLike(@Param("name") String name, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE Student s set s.name=:name, group_id=:group_id WHERE student_id = :student_id")
    void updateStudent(@Param("name") String name,
                       @Param("group_id") long group_id,
                       @Param("student_id") long student_id);
}
