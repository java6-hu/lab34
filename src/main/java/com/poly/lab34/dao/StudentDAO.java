package com.poly.lab34.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.lab34.entity.Student;

public interface StudentDAO extends JpaRepository<Student, String> {
}