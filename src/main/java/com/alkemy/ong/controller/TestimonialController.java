package com.alkemy.ong.controller;

import com.alkemy.ong.models.request.TestimonialRequest;
import com.alkemy.ong.service.TestimonialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.alkemy.ong.controller.ApiConstants.ROLE_ADMIN;

@RestController
@RequestMapping(path = "/testimonials")
@PreAuthorize(ROLE_ADMIN)
public class TestimonialController {

    @Autowired
    private TestimonialService testimonialService;

    @PostMapping
    public ResponseEntity<?> createTestimonial(@Valid @RequestBody TestimonialRequest testimonial){
        return new ResponseEntity<>(testimonialService.createTestimonial(testimonial), HttpStatus.CREATED);
    }
}
