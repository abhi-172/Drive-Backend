package com.cfs.Drive_Backend.repo;

import com.cfs.Drive_Backend.entity.FileEntity;
import com.cfs.Drive_Backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileEntity , Long> {
    List<FileEntity> findByOwner(User user);
}
