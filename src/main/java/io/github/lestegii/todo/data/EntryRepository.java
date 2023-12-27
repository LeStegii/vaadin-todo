package io.github.lestegii.todo.data;

import io.github.lestegii.todo.data.entity.Entry;
import io.github.lestegii.todo.data.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface EntryRepository extends JpaRepository<Entry, Long> {

    // Finds all entries where the title contains the given filter (or if the filter is empty) and the status is one of the given statuses (or if the statuses are null).
    @Query("select e from Entry e where " +
            "((:filter is null) or (:filter = '') or (lower(e.title) like lower(concat('%', :filter, '%')))) " +
            "and (:statuses is null or e.status in :statuses) " +
            "and ((e.owner = null) or (e.owner = :user))" +
            "order by e.created desc")
    List<Entry> findAll(@Param("user") String user, @Param("filter") String filter, @Param("statuses") Collection<Status> statuses);

    @Query("select distinct e.category from Entry e")
    List<String> findAllCategories();

}
