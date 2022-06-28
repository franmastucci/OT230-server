package com.alkemy.ong.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alkemy.ong.models.request.NewsRequest;
import com.alkemy.ong.service.NewsService;

import lombok.NonNull;

@RestController
@RequestMapping("/news")
public class NewsController {
	
	@Autowired
	NewsService newsServ;

	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
	@GetMapping()
	ResponseEntity<?> getAllNews(){
		return new ResponseEntity<>(newsServ.getNewsList(), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
	@GetMapping(path = "/{id}")
	ResponseEntity<?> getNews(@PathVariable("id") @Valid @NonNull Long id){
		return new ResponseEntity<>(newsServ.getNewsById(id), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
	@PostMapping()
	ResponseEntity<?> getNews(@Valid @RequestBody NewsRequest news){
		return new ResponseEntity<>(newsServ.createNews(news), HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
	@PutMapping(path = "/{id}")
	ResponseEntity<?> putNews(@Valid @PathVariable("id") Long id, @RequestBody NewsRequest news){
		return new ResponseEntity<>(newsServ.updateNews(id, news), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
	@DeleteMapping(path = "/{id}")
	ResponseEntity<Void> deleteNews(@Valid @PathVariable("id") Long id){
		newsServ.removeNews(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	
}

