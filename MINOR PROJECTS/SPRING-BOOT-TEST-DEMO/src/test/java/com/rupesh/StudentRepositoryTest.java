package com.rupesh;

import com.rupesh.entity.Student;
import com.rupesh.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    private Student student;

    @BeforeEach
    void init(){
        student = new Student();student.setFirstName("rupesh");
        student.setLastName("dulal");
        student.setEmail("sharma.rupesh75@gmail.com");

    }

    @Test
    @DisplayName("It should save student to the database.")
    @Rollback()
    void saveStudentTest() {
//        Student student = new Student();
//        student.setFirstName("rupesh");
//        student.setLastName("dulal");
//        student.setEmail("sharma.rupesh75@gmail.com");

        Student savedStudent = studentRepository.save(student);

        //Assert
        assertNotNull(savedStudent);

        assertThat(savedStudent.getId()).isNotEqualTo(null);
    }

    @Test
    @DisplayName("It should return all students.")
    public void testGetAllStudent() {
//        Student student = new Student();
//        student.setFirstName("rupesh");
//        student.setLastName("dulal");
//        student.setEmail("sharma.rupesh75@gmail.com");

        studentRepository.save(student);

        List<Student> allStudent = studentRepository.findAll();
        assertNotNull(allStudent);
        assertThat(allStudent).isNotNull();
        assertEquals(1, allStudent.size());
    }
}
