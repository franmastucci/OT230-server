package com.alkemy.ong.models.mapper;

import com.alkemy.ong.exception.OrgNotFoundException;
import com.alkemy.ong.exception.UserNotFoundException;
import com.alkemy.ong.models.entity.CommentEntity;
import com.alkemy.ong.models.request.CommentRequest;
import com.alkemy.ong.models.response.CommentResponse;
import com.alkemy.ong.repository.NewsRepository;
import com.alkemy.ong.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Component
public class CommentMapper {

    @Autowired
    NewsRepository newsRepo;

    @Autowired
    UserRepository userRepo;

    public CommentEntity toCommentEntity(CommentRequest commentReq) {
        return CommentEntity
                .builder()
                .body(commentReq.getBody())
                .news(newsRepo.findById(commentReq.getNewsId())
                        .orElseThrow(() -> new OrgNotFoundException("News doesn't exists")))
                .user(userRepo.findById(commentReq.getUserId())
                        .orElseThrow(() -> new UserNotFoundException("User doesn't exists")))
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
    }

    public CommentResponse toCommentResponse(CommentEntity commentEntity) {
        return CommentResponse
                .builder()
                .body(commentEntity.getBody())
                .build();
    }

    public List<CommentResponse> CommentsResponseList(List<CommentEntity> comments) {
        List<CommentResponse> commentRespList = new ArrayList<>();
        comments.forEach( c -> {
            CommentResponse commentResp = CommentResponse
                    .builder()
                    .body(c.getBody())
                    .build();
            commentRespList.add(commentResp);
        });
        return commentRespList;
    }
}
