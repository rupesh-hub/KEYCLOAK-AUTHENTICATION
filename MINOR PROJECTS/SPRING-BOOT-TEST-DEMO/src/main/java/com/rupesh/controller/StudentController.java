package com.rupesh.controller;

import com.rupesh.entity.Student;
import com.rupesh.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/register")
    public ResponseEntity<Student> save(@RequestBody final Student student) {
        return new ResponseEntity(studentService.save(student), HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public ResponseEntity<List<Student>> allStudents() {
        return new ResponseEntity(studentService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Student> studentById(@PathVariable final Long id) {
        return new ResponseEntity(studentService.getById(id), HttpStatus.OK);
    }
}
