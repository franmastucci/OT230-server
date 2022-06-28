package com.alkemy.ong.models.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
@Getter
@Setter
public class ImageDTO {
    private String title;
    private String file;
}
