package week4.home.study.dao.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import week4.home.study.entity.Groups;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Groups, Long> {
    @Query("SELECT g FROM Groups g WHERE g.name = :name")
    Groups getGroupByName(@Param("name") String name);

    @Modifying
    @Transactional
    @Query("UPDATE Groups c set c.name=:name WHERE group_id = :group_id")
    void updateGroup(@Param("name") String name,
                     @Param("group_id") long group_id);

    @Query("SELECT g FROM Groups g WHERE g.name like :name")
    List<Groups> getGroupsLike(@Param("name") String name, Pageable pageable);

    @Query("SELECT g FROM Groups g join g.subjects subject where subject.name = :subjectName")
    List<Groups> getGroupsBySubject(@Param("subjectName") String subjectName, Pageable pageable);
}
