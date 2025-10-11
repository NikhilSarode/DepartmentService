package com.example.controller;

import com.example.entity.Department;
import com.example.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentRepository departmentRepository;

    @PostMapping
    public Department saveDepartment(@RequestBody Department department) {
        System.out.println("called saveDepartment");
        return departmentRepository.save(department);
    }

    @GetMapping
    public List<Department> getAllDepartment() {
        return departmentRepository.findAll();
    }
}
