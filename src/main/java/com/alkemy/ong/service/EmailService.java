package com.alkemy.ong.service;

import java.io.IOException;

public interface EmailService {

    void sendEmailTo(String to) throws IOException;
}
