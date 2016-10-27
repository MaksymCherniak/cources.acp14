package week4.home.study.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import week4.home.study.entity.Groups;

@Repository
public interface GroupRepository extends JpaRepository<Groups, Long> {
    @Query("SELECT g FROM Groups g WHERE g.name = :name")
    Groups getGroupByName(@Param("name") String name);

    @Modifying
    @Transactional
    @Query("UPDATE Groups c set c.name=:name WHERE group_id LIKE :group_id")
    void updateGroup(@Param("name") String name,
                     @Param("group_id") long group_id);
}
