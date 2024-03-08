package com.devtomato.loan.service;

import com.devtomato.loan.exception.BaseException;
import com.devtomato.loan.exception.ResultType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static com.devtomato.loan.exception.ResultType.SYSTEM_ERROR;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${spring.jpa.servlet.multipart.location}")
    private String uploadPath;

    @Override
    public void save(MultipartFile multipartFile) {

        try {
            Files.copy(multipartFile.getInputStream(),
                    Paths.get(uploadPath).resolve(multipartFile.getOriginalFilename()),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new BaseException(SYSTEM_ERROR);
        }
    }
}
