package com.alkemy.ong.controller;

import com.alkemy.ong.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    CommentService commentServ;

    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    @GetMapping()
    public ResponseEntity<?>getComments() {
        return new ResponseEntity<>(commentServ.findAllComments(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?>deleteComment(@Valid @PathVariable("id") Long id, HttpServletRequest request) {
        return new ResponseEntity<>(commentServ.removeComment(id, request.getHeader("Authorization")), HttpStatus.OK);
    }

}
