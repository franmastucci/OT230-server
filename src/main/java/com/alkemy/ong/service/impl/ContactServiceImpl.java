package com.alkemy.ong.service.impl;

import com.alkemy.ong.exception.EmailExistsException;
import com.alkemy.ong.models.entity.ContactEntity;
import com.alkemy.ong.models.mapper.ContactMapper;
import com.alkemy.ong.models.request.ContactRequest;
import com.alkemy.ong.repository.ContactsRepository;
import com.alkemy.ong.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

   private final ContactsRepository contactsRepository;
   private final ContactMapper contactMapper;

   @Override
   public void addContact(ContactRequest request) {
      if (contactsRepository.existsByEmail(request.getEmail()))
         throw new EmailExistsException("There is a contact with the same email");

      ContactEntity contact = contactMapper.requestToContactEntity(request);
      contactsRepository.save(contact);
   }
}
