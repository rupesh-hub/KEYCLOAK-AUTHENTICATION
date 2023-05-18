package com.ayush.studentservice.repo;

import com.ayush.shared.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepo extends JpaRepository<Student,Integer> {
    @Query(value="select * from student s where s.name=?1 ",nativeQuery=true)
    Student findByName(String name);
}
