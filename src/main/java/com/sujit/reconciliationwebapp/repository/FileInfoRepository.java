package com.sujit.reconciliationwebapp.repository;

import com.sujit.reconciliationwebapp.model.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileInfoRepository extends JpaRepository<FileInfo, Long > {
    Optional<FileInfo> findFirstByUsername(String username);
    Optional<FileInfo> findAllByUsername(String username);
}
