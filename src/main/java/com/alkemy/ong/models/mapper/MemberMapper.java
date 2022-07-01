package com.alkemy.ong.models.mapper;

import com.alkemy.ong.models.entity.MemberEntity;
import com.alkemy.ong.models.request.MemberRequest;
import com.alkemy.ong.models.response.MemberResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MemberMapper {

   public MemberEntity requestToMemberEntity(MemberRequest request) {
      return MemberEntity.builder()
         .name(request.getName())
         .facebookUrl(request.getFacebook())
         .instagramUrl(request.getInstagram())
         .linkedinUrl(request.getLinkedIn())
         .description(request.getDescription())
         .image(request.getImage())
         .build();
   }

   public MemberResponse entityToMemberResponse(MemberEntity entity) {
      return MemberResponse.builder()
         .name(entity.getName())
         .facebook(entity.getFacebookUrl())
         .instagram(entity.getInstagramUrl())
         .linkedIn(entity.getLinkedinUrl())
         .description(entity.getDescription())
         .timestamp(entity.getTimeStamp())
         .build();
   }

   public List<MemberResponse> entitiesToListOfResponses(List<MemberEntity> entities) {
      List<MemberResponse> memberResponses = new ArrayList<>(entities.size());

      for (MemberEntity entity : entities) {
         memberResponses.add( entityToMemberResponse(entity) );
      }

      return memberResponses;
   }
}
