package com.poly.lab34.dao;

import com.poly.lab34.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<User, String> {
}