package com.smartech.i2019.adaptiveinterviews.repository;

import com.smartech.i2019.adaptiveinterviews.model.Interview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long> {

    List<Interview> findByDate(Date date);

    List<Interview> findAllByDateGreaterThanEqualAndDescriptionEquals(Date date, String string);

    List<Interview> findAllByDescriptionEquals(String string);

    @Query("select e from Interview e where e.id = :id")
    List<Interview> getEmployees(@Param("id") Long id);

}
