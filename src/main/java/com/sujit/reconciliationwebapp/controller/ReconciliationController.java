package com.sujit.reconciliationwebapp.controller;

import com.sujit.reconciliationwebapp.exception.ApiError;
import com.sujit.reconciliationwebapp.exception.ViolationException;
import com.sujit.reconciliationwebapp.model.FileInfo;
import com.sujit.reconciliationwebapp.service.ReconciliationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import java.util.Date;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReconciliationController {
    private final ReconciliationService reconciliationService;

    @GetMapping("/test")
    public ResponseEntity<String> testApi() {
        return ResponseEntity.ok("Hello Reconciliation test !");
    }

    @PostMapping(value = "/fileUpload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadFile(String fileType, boolean isSource, String displayName, @RequestPart("file") MultipartFile multipartFile) {
        FileInfo fileInfo = new FileInfo();
        String fileProp = isSource? "source": "target";
        log.info("Uploading " + fileType + " file");
        String fileName = displayName + "." + fileType.toLowerCase();
        String fileUrl = fileSave(fileName,fileProp, multipartFile);
        if(isSource) {
            fileInfo.setSourceFileUrl(fileUrl);
        }
        else {
            fileInfo.setTargetFileUrl(fileUrl);
        }
        fileInfo.setUserId(12L);
        reconciliationService.saveFileSystemInfo(fileInfo);
        log.info("File uploaded success");
        return ResponseEntity.ok().build();
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
