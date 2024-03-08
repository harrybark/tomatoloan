package com.devtomato.loan.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface FileStorageService {

    void save(Long applicationId, MultipartFile multipartFile);

    Resource load(Long applicationId, String fileName);

    Stream<Path> loadAll(Long applicationId);

    void deleteAll(Long applicationId);
}
