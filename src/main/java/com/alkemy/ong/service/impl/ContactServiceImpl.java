package com.alkemy.ong.service.impl;

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
      ContactEntity contact = contactMapper.requestToContactEntity(request);
      contactsRepository.save(contact);
   }
}
