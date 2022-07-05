package com.alkemy.ong.controller;

import com.alkemy.ong.models.request.CommentRequest;
import com.alkemy.ong.models.response.CommentCompleteResponse;
import com.alkemy.ong.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.alkemy.ong.controller.ApiConstants.ROLE_ADMIN;

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

    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    @PostMapping
    public ResponseEntity<CommentCompleteResponse> createComment (@Valid @RequestBody CommentRequest commentRequest){
        CommentCompleteResponse commentCreated = this.commentServ.create(commentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentCreated);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateComment(
            @PathVariable("id") Long id, CommentRequest commentRequest, HttpServletRequest request){

        return new ResponseEntity<>(this.commentServ.update(id,commentRequest, request.getHeader("Authorization")),
                HttpStatus.OK);

    }
}
