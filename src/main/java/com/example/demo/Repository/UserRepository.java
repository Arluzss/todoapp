package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.TDUser;

public interface UserRepository extends JpaRepository<TDUser, Long> {
    // JpaRepository provides basic CRUD operations for User entity
    // Additional query methods can be defined here if needed
}
