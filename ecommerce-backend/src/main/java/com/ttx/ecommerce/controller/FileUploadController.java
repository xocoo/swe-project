package com.ttx.ecommerce.controller;

import com.ttx.ecommerce.model.FileInfo;
import com.ttx.ecommerce.service.FIleStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/fileUpload")
public class FileUploadController {

    @Autowired
    FIleStoreService fileStoreService;

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        return fileStoreService.store(file);
    }

    @GetMapping("/")
    public ResponseEntity<List<FileInfo>> getListFiles() {
        List<FileInfo> fileInfos = fileStoreService.loadAll().map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(FileUploadController.class, "getFile", path.getFileName().toString()).build()
                    .toString();

            return new FileInfo(filename, url);
        }).collect(Collectors.toList());

        fileStoreService.loadAll();
        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }

    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = fileStoreService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

}
