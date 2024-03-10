package com.devtomato.loan.service;

import com.devtomato.loan.exception.BaseException;
import com.devtomato.loan.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import static com.devtomato.loan.exception.ResultType.FILE_NOT_EXIST;
import static com.devtomato.loan.exception.ResultType.SYSTEM_ERROR;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FileStorageServiceImpl implements FileStorageService {

    private final ApplicationRepository applicationRespository;

    @Value("${spring.jpa.servlet.multipart.location}")
    private String uploadPath;

    @Override
    public void save(Long applicationId, MultipartFile multipartFile) {

        if (!isPresentApplication(applicationId)) {
            throw new BaseException(SYSTEM_ERROR);
        }

        try {
            String applicationPath = uploadPath.concat("/").concat(String.valueOf(applicationId));

            Path directoryPath = Paths.get(applicationPath);
            if ( !Files.exists(directoryPath) ) {
                Files.createDirectory(directoryPath);
            }

            Files.copy(multipartFile.getInputStream(),
                    directoryPath.resolve(multipartFile.getOriginalFilename()),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new BaseException(SYSTEM_ERROR);
        }
    }

    @Override
    public Resource load(Long applicationId, String fileName) {

        if (!isPresentApplication(applicationId)) {
            throw new BaseException(SYSTEM_ERROR);
        }

        try {
            String applicationPath = uploadPath.concat("/").concat(String.valueOf(applicationId));

            Path file = Paths.get(applicationPath).resolve(fileName);
            Resource resource = new UrlResource(file.toUri());

            if ( resource.isReadable() || resource.exists() ) {
                return resource;
            } else {
                throw new BaseException(FILE_NOT_EXIST);
            }
        } catch (MalformedURLException e) {
            throw new BaseException(SYSTEM_ERROR);
        } catch (Exception e) {
            throw new BaseException(SYSTEM_ERROR);
        }
    }

    @Override
    public Stream<Path> loadAll(Long applicationId) {

        if (!isPresentApplication(applicationId)) {
            throw new BaseException(SYSTEM_ERROR);
        }

        try {
            String applicationPath = uploadPath.concat("/").concat(String.valueOf(applicationId));
            return Files.walk(Paths.get(applicationPath), 1).filter((path) -> !path.equals(Paths.get(applicationPath)));
        } catch (IOException e) {
            throw new BaseException(FILE_NOT_EXIST);
        }
    }

    @Override
    public void deleteAll(Long applicationId) {

        if (!isPresentApplication(applicationId)) {
            throw new BaseException(SYSTEM_ERROR);
        }

        //FileSystemUtils.deleteRecursively(Paths.get(uploadPath).toFile());
        try {
            String applicationPath = uploadPath.concat("/").concat(String.valueOf(applicationId));
            FileUtils.cleanDirectory(new File(applicationPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isPresentApplication(Long applicationId) {
        return applicationRespository.findById(applicationId).isPresent();
    }
}
