package com.alkemy.ong.service.impl;

import com.alkemy.ong.auth.service.JwtUtils;
import com.alkemy.ong.auth.service.impl.UserDetailsServiceImpl;
import com.alkemy.ong.exception.OrgNotFoundException;
import com.alkemy.ong.exception.UserNotFoundException;
import com.alkemy.ong.models.entity.CommentEntity;
import com.alkemy.ong.models.entity.UserEntity;
import com.alkemy.ong.models.mapper.CommentMapper;
import com.alkemy.ong.models.response.CommentResponse;
import com.alkemy.ong.repository.CommentRepository;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.service.CommentService;
import com.amazonaws.services.pinpoint.model.ForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRepository commentRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserDetailsServiceImpl userDetails;
    @Autowired
    CommentMapper commentMapper;

    @Override
    public List<CommentResponse> findAllComments() {
        List<CommentEntity> commentsList = commentRepo.findAll(Sort.by(Sort.Direction.DESC, "timestamp"));
        return commentMapper.CommentsResponseList(commentsList);
    }

    @Override
    public String removeComment(Long id, String token) {
        var commentDB = commentRepo.findById(id).orElseThrow(() -> new OrgNotFoundException("Comment not found"));

        var tokenSplit = token.split(" ");
        var username = jwtUtils.extractUsername(tokenSplit[1]);
        var user = userDetails.loadUserByUsername(username);
        var role = user.getAuthorities().iterator().next().getAuthority();

        var userDB = userRepo.findByEmail(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (role.equalsIgnoreCase("ROLE_ADMIN") | commentDB.getUser().equals(userDB)) {
            commentRepo.deleteById(id);
            return "Successfully removed";
        } else {
            throw new BadCredentialsException("Invalid credentials");
        }
    }

    @Override
    public List<CommentResponse> findCommentsByNews(Long id) {
        List<CommentEntity> comments = commentRepo.findByNewsId(id);
        List<CommentResponse> commentsResp = commentMapper.CommentsResponseList(comments);
        return commentsResp;
    }
}
