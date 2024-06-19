package com.sparta.viewfinder.repository;

import com.sparta.viewfinder.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
