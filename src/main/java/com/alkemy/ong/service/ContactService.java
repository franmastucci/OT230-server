package com.alkemy.ong.service;

import com.alkemy.ong.models.request.ContactRequest;

import java.io.IOException;

public interface ContactService {

   void addContact(ContactRequest request) throws IOException;
}
