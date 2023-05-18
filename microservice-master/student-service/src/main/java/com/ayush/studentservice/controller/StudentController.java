package com.ayush.studentservice.controller;

import com.ayush.shared.model.Student;
import com.ayush.studentservice.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student create(@RequestBody Student student){
        return studentService.create(student);
    }

    @GetMapping
    public List<Student> getAllStudent(){
        return studentService.allStudents();
    }


    @GetMapping("/{id}")
    public Student getStudent(@PathVariable("id") int id){
        return studentService.findById(id);
    }
}
