package com.example.controller;

import com.example.entity.Department;
import com.example.repository.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    private static final Logger log = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    private DepartmentRepository departmentRepository;

    @PostMapping
    public Department saveDepartment(@RequestBody Department department) {
        log.info("called saveDepartment");
        return departmentRepository.save(department);
    }

    @GetMapping
    public List<Department> getAllDepartment() {
        log.info("called getAllDepartment");
        return departmentRepository.findAll();
    }
}
