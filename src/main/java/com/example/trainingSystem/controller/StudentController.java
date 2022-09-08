package com.example.trainingSystem.controller;

import com.example.trainingSystem.entity.Groups;
import com.example.trainingSystem.entity.Student;
import com.example.trainingSystem.service.GroupService;
import com.example.trainingSystem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/students")
public class StudentController {
    public final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getStudents(){
       return studentService.getStudents();
    }

    @PostMapping
    public Student addNewStudent(@RequestBody Student student){

        return studentService.registerNewStudent(student);
    }

    @DeleteMapping
    public void deleteStudent(@PathVariable Long studentId){
        studentService.deleteStudent(studentId);
    }


}
