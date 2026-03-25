package com.poly.lab34.service;

import java.util.List;

import com.poly.lab34.entity.Student;

public interface StudentService {
    List<Student> findAll();

    Student findById(String id);

    Student create(Student student);

    Student update(Student student);

    void deleteById(String id);
}