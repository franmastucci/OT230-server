package com.alkemy.ong.controller;

import com.alkemy.ong.models.request.ContactRequest;
import com.alkemy.ong.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor
public class ContactController {

   private final ContactService contactService;

   @PostMapping
   public ResponseEntity<Void> addContact(@RequestBody @Valid ContactRequest request) {
      contactService.addContact(request);
      return ResponseEntity.status(HttpStatus.OK).build();
   }
}
