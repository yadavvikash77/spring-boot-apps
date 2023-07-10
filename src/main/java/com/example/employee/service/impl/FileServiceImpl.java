package com.example.employee.service.impl;

import com.example.employee.service.FileService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
@Service
public class FileServiceImpl implements FileService {

    private final Path rootPath = Path.of("uploads");
    @Override
    public void save(MultipartFile file) throws FileAlreadyExistsException {
        try {
            Files.copy(file.getInputStream(),rootPath.resolve(file.getOriginalFilename()));
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Resource load(String fileName) {
        Resource resource = null;
        try {
            resource = new UrlResource(rootPath.resolve(fileName).toUri());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return resource;
    }
}
