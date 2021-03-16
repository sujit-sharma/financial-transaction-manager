package com.sujit.reconciliationwebapp.repository;

import com.sujit.reconciliationwebapp.model.FileInfo;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileInfoRepository extends JpaRepository<FileInfo, Long > {
    Optional<FileInfo> findAllByUserId(Long userId);
}
