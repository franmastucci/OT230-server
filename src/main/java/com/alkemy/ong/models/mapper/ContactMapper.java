package com.alkemy.ong.models.mapper;

import com.alkemy.ong.models.entity.ContactEntity;
import com.alkemy.ong.models.request.ContactRequest;
import org.springframework.stereotype.Component;

@Component
public class ContactMapper {

   public ContactEntity requestToContactEntity(ContactRequest request) {
      return ContactEntity.builder()
         .name(request.getName())
         .email(request.getEmail())
         .phone(request.getPhone())
         .message(request.getMessage())
         .build();
   }
}
