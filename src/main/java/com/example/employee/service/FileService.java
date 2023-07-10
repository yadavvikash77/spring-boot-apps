package com.example.employee.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;


import java.nio.file.FileAlreadyExistsException;

public interface FileService {
    public void save(MultipartFile file) throws FileAlreadyExistsException;
    public Resource load(String fileName);
}
