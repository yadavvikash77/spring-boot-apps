package com.example.employee.controller;

import com.example.employee.service.FileService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.nio.file.FileAlreadyExistsException;

@RestController
@RequestMapping("/api/v1")
@ApiIgnore
public class FileController {

    private FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadeFile(@RequestParam("file") MultipartFile file) throws FileAlreadyExistsException {
        fileService.save(file);
        return ResponseEntity.ok().body("File Uploaded Successfully :: "+file.getOriginalFilename());
    }

    @GetMapping("/file/{filename}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename){
        Resource file = fileService.load(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
