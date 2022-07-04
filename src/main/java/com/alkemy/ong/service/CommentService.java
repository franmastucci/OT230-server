package com.alkemy.ong.service;

import com.alkemy.ong.models.response.CommentResponse;

import java.util.List;

public interface CommentService {
    List<CommentResponse> findAllComments();
    String removeComment(Long id, String token);
    List<CommentResponse> findCommentsByNews(Long id);
}
