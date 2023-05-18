package com.ayush.studentservice.service;

import com.ayush.shared.model.Student;
import com.ayush.studentservice.repo.StudentRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepo studentRepo;

    public StudentService(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    public List<Student> addAll(List<Student> studentList){
        return studentRepo.saveAll(studentList);
    }

    public Student create(Student student){
        return studentRepo.save(student);
    }

    public Student findById(int id){
        return studentRepo.findById(id).get();
    }

    public Student findByName(String name){
        return studentRepo.findByName(name);
    }

    public List<Student> allStudents() {
        return studentRepo.findAll();
    }
}
