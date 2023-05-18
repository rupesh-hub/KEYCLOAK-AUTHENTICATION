package com.ayush.accountservice.repo;

import com.ayush.shared.model.Fee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepo extends JpaRepository<Fee,Long> {

    @Query(value="select * from fee f where f.student_id=?1",nativeQuery=true)
    List<Fee> findByStudentId(int studentId);
}
