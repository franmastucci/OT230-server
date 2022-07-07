package com.alkemy.ong.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This class allows us to avoid unparameterized raw matches. Using raw types will make us lose all the safety and expressiveness benefits of generics.
 * @param <T> Objects that are entities
 * @param <ID> Data type used in the entity ID
 * @param <R> Repository created for the entity
 */
public abstract class PageUtil<T, ID, R extends JpaRepository<T, ID>> {

   @Autowired
   private R repository;
   private static final Integer PAGE_SIZE = 10;

   protected Page<T> getPage(Integer page) {
      return repository.findAll(PageRequest.of(page-1, PAGE_SIZE));
   }

   protected String getPrevious(String path, Integer page) {
      if(page > 1){ return String.format(path, page-1); }
      return null;
   }

   protected String getNext(Page<T> tPage, String path, Integer page) {
      if(tPage.hasNext()){ return String.format(path, page+1); }
      return null;
   }
}
