package com.sujit.reconciliationwebapp.controller;

import com.sujit.reconciliationwebapp.constraint.DaoType;
import com.sujit.reconciliationwebapp.exception.ApiError;
import com.sujit.reconciliationwebapp.exception.ViolationException;
import com.sujit.reconciliationwebapp.model.FileInfo;
import com.sujit.reconciliationwebapp.repository.FileInfoRepository;
import com.sujit.reconciliationwebapp.service.ReconciliationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReconciliationController {
    private final ReconciliationService reconciliationService;
    private final FileInfoRepository fileInfoRepository;

    @PostMapping(value = "/fileUpload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadFile(String fileType, boolean isSource, String displayName, @RequestPart("file") MultipartFile multipartFile) {

        String fileProp = isSource? "source": "target";
        log.info("Uploading " + fileType + " file");
        String fileName = displayName + "." + fileType.toLowerCase();
        String fileUrl = fileSave(fileName,fileProp, multipartFile);
        FileInfo fileInfo = fileInfoRepository.findFirstByUsername(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()).orElse(null);
        if(fileInfo == null) {
            fileInfo = new FileInfo();
            fileInfo.setUsername(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());

        }
        List<String> fileUrls = fileInfo.getFileUrls();
        if(fileUrls == null) {
            fileUrls = new ArrayList<>();
        }
        fileUrls.add(fileUrl);
        fileInfo.setFileUrls(fileUrls);
        reconciliationService.saveFileSystemInfo(fileInfo);
        log.info("File uploaded success");
        return ResponseEntity.ok().build();
    }

    @PostMapping("/compare")
    public ResponseEntity<Map<DaoType, List<Object>>> compareTransaction() {
        Map<DaoType, List<Object>> comparisonResult = reconciliationService.reconcile();
        log.info("Reconciliation completed");
        return ResponseEntity.ok( comparisonResult);
    }

    private String fileSave(String displayFileName, String fileProperty, MultipartFile multipartFile) {
        String username = "retrieveFromDatabase";
        log.info("Saving image");
        String fileName =   fileProperty + "_" + StringUtils.cleanPath(Objects.requireNonNull(displayFileName));
        String dir = "." + File.separator + "files" + File.separator + username;
        Path uploadPath = Paths.get(dir);
        try {
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            InputStream inputStream = multipartFile.getInputStream();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            inputStream.close();
            log.info("File saved to directory");
            return dir + File.separator + fileName;
        } catch (IOException ioException) {
            throw new ViolationException(ioException, new ApiError("image", "Error while saving image"));
        }
    }

}
