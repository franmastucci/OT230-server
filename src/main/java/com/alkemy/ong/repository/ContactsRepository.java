package com.alkemy.ong.repository;

import com.alkemy.ong.models.entity.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactsRepository extends JpaRepository<ContactEntity, Long> {

   boolean existsByEmail(String email);
}