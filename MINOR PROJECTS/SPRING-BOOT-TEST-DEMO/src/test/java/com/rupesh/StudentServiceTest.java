package com.rupesh;

import com.rupesh.entity.Student;
import com.rupesh.repository.StudentRepository;
import com.rupesh.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @InjectMocks
    private StudentService studentService;

    @Mock
    private StudentRepository studentRepository;

    private Student student;

    @BeforeEach
    void init() {
        student = new Student();
        student.setFirstName("rupesh");
        student.setLastName("dulal");
        student.setEmail("sharma.rupesh75@gmail.com");
    }

    @Test
    @DisplayName("Should save the student object to database.")
    void saveStudentTest() {
        Mockito.when(studentRepository.save(any(Student.class))).thenReturn(student);
        Student studentSaved = studentService.save(student);
        assertNotNull(studentSaved);
        assertThat(studentSaved.getFirstName()).isEqualTo("rupesh");
    }

    @Test
    @DisplayName("Should return list of students")
    void getStudentsTest() {
        assertNotNull(studentRepository.save(student));
        Mockito.when(studentRepository.findAll()).thenReturn(null);
        List<Student> allStudents = studentService.getAll();

        assertEquals(1, allStudents.size());
        assertNotNull(allStudents);
    }

    @Test
    @DisplayName("")
    void getStudentByIdTest() {
        Mockito.when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));
        Student existStudent = studentService.getById(1L);
        assertNotNull(existStudent);
        assertThat(existStudent.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("")
    void getStudentByIdForException() {
        Mockito.when(studentRepository.findById(2L)).thenReturn(Optional.of(student));
        assertThrows(RuntimeException.class, () -> {
           studentService.getById(2L);
        });
    }

    @Test
    @DisplayName("")
    void deleteStudent() {
        Mockito.when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));
        doNothing().when(studentRepository).delete(student);

        //studentService.deleteStudent(1L);
        //verify(studentRepository, times(1)).delete(student);
    }

}
