package com.alkemy.ong.service.impl;

import com.alkemy.ong.exception.MemberNotFoundException;
import com.alkemy.ong.models.entity.MemberEntity;
import com.alkemy.ong.models.mapper.MemberMapper;
import com.alkemy.ong.models.request.MemberRequest;
import com.alkemy.ong.models.request.UpdateMemberRequest;
import com.alkemy.ong.models.response.MemberResponse;
import com.alkemy.ong.repository.MembersRepository;
import com.alkemy.ong.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

   @Autowired
   private MembersRepository membersRepository;

   @Autowired
   private MemberMapper memberMapper;

   private MemberEntity findById(Long id) {
      return membersRepository.findById(id)
         .orElseThrow(() -> new MemberNotFoundException("Member not found"));
   }

   @Override
   @Transactional(readOnly = true)
   public List<MemberResponse> getMembers() {
      return memberMapper.entitiesToListOfResponses(membersRepository.findAll());
   }

   @Override
   @Transactional
   public void createMember(MemberRequest request) {
      MemberEntity member = memberMapper.requestToMemberEntity(request);
      membersRepository.save(member);
   }

   @Transactional
   public void deleteMember(Long id) {
      MemberEntity entity = findById(id);
      membersRepository.delete(entity);
   }

   @Transactional
   public void updateMember(Long id, UpdateMemberRequest request) {
      MemberEntity member = findById(id);

      member.setName(request.getName());
      member.setFacebookUrl(request.getFacebook());
      member.setInstagramUrl(request.getInstagram());
      member.setLinkedinUrl(request.getLinkedIn());
      member.setDescription(request.getDescription());
      member.setImage(request.getImage());

      membersRepository.save(member);
   }
}
