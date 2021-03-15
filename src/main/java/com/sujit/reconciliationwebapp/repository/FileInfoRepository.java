package com.sujit.reconciliationwebapp.repository;

import com.sujit.reconciliationwebapp.model.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileInfoRepository extends JpaRepository<FileInfo, Long > {

}
