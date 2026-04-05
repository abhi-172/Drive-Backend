package com.cfs.Drive_Backend.repo;

import com.cfs.Drive_Backend.entity.FileEntity;
import com.cfs.Drive_Backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
