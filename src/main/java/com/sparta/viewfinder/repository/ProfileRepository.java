package com.sparta.viewfinder.repository;

import com.sparta.viewfinder.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
