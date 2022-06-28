package com.alkemy.ong.utils;

import org.springframework.stereotype.Component;

import java.util.Base64;


@Component
public class ImageHelper {

    private MultiPartFileCreator multiPartFileCreator;


    private String base64Decoder(String imgUrl) {
        byte[] bytes = Base64.getDecoder().decode(imgUrl);

