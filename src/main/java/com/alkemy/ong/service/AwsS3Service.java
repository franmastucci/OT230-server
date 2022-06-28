package com.alkemy.ong.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AwsS3Service {
    String uploadFile(MultipartFile file) throws IOException;
    String uploadFile(String file) throws IOException;
}
