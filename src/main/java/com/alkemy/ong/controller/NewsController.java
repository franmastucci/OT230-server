package com.alkemy.ong.controller;

import javax.validation.Valid;

import com.alkemy.ong.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.alkemy.ong.models.request.NewsRequest;
import com.alkemy.ong.service.NewsService;

import lombok.NonNull;

import java.util.Optional;

@RestController
@RequestMapping("/news")
public class NewsController {
	
	@Autowired
	NewsService newsServ;

	@Autowired
	CommentService commentServ;

	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
	@GetMapping()
	ResponseEntity<?> getAllNews(@RequestParam Optional<Integer> page){
		if (page.isPresent()){
			return new ResponseEntity<>(newsServ.getPaginationNews(page.get()), HttpStatus.OK);
		}else{
			return new ResponseEntity<>(newsServ.getNewsList(), HttpStatus.OK);
		}
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
	@GetMapping(path = "/{id}")
	ResponseEntity<?> getNews(@PathVariable("id") @Valid @NonNull Long id){
		return new ResponseEntity<>(newsServ.getNewsById(id), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping()
	ResponseEntity<?> getNews(@Valid @RequestBody NewsRequest news){
		return new ResponseEntity<>(newsServ.createNews(news), HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping(path = "/{id}")
	ResponseEntity<?> putNews(@Valid @PathVariable("id") Long id, @RequestBody NewsRequest news){
		return new ResponseEntity<>(newsServ.updateNews(id, news), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping(path = "/{id}")
	ResponseEntity<?> deleteNews(@Valid @PathVariable("id") Long id){
		return new ResponseEntity<>(newsServ.removeNews(id), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN') OR (hasRole('ROLE_USER'))")
	@GetMapping(path = "/{id}/comments")
	ResponseEntity<?> getCommentsByNewsId(@Valid @PathVariable("id") Long id){
		return new ResponseEntity<>(commentServ.findCommentsByNews(id), HttpStatus.OK);
	}
	
}

