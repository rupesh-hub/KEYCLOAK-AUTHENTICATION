package com.rupesh.service;

import com.rupesh.entity.Student;
import com.rupesh.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student save(final Student student) {
        return studentRepository.save(student);
    }

    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    public Student getById(final Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("no record found"));
    }

}
