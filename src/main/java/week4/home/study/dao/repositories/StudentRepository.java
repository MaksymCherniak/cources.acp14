package week4.home.study.dao.repositories;

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
    @Query("SELECT s FROM Student s WHERE s.name LIKE :name AND group_id LIKE :group_id")
    Student getStudent(@Param("name") String name,
                       @Param("group_id") long group_id);

    @Query("SELECT g FROM Student g WHERE group_id LIKE :group_id")
    List<Student> getStudentsByGroup(@Param("group_id") long group_id);

    @Modifying
    @Transactional
    @Query("UPDATE Student c set c.name=:name, group_id=:group_id WHERE student_id LIKE :student_id")
    void updateStudent(@Param("name") String name,
                       @Param("group_id") long group_id,
                       @Param("student_id") long student_id);
}
