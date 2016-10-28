package week4.home.study.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import week4.home.study.entity.Teacher;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    @Query("SELECT s FROM Teacher s WHERE s.name LIKE :name AND s.experience LIKE :experience AND subject_id LIKE :subject_id")
    Teacher getTeacher(@Param("name") String name,
                       @Param("experience") int experience,
                       @Param("subject_id") long subject_id);

    @Query("SELECT s FROM Teacher s WHERE s.name = :name")
    Teacher getTeacherByName(@Param("name") String name);

    @Query("SELECT g FROM Teacher g WHERE subject_id LIKE :subject_id")
    List<Teacher> getTeachersBySubject(@Param("subject_id") long subject_id);

    @Modifying
    @Transactional
    @Query("UPDATE Teacher c set c.name=:name, c.experience=:experience, subject_id=:subject_id WHERE teacher_id LIKE :teacher_id")
    void updateTeacher(@Param("name") String name,
                       @Param("experience") int experience,
                       @Param("subject_id") long subject_id,
                       @Param("teacher_id") long teacher_id);

    @Query("SELECT t from Teacher t where t.experience >= :experience")
    List<Teacher> getTeachersMoreThanExperience(@Param("experience") int experience);

    @Query("select t from Teacher t WHERE t.experience=(SELECT max(experience) FROM Teacher)")
    List<Teacher> getMaxExpiriencedTeachers();

    @Query("select t from Teacher t WHERE t.experience=(SELECT min(experience) FROM Teacher)")
    List<Teacher> getMinExpiriencedTeachers();
}
