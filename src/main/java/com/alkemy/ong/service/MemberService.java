package com.alkemy.ong.service;

import com.alkemy.ong.models.request.MemberRequest;
import com.alkemy.ong.models.request.UpdateMemberRequest;
import com.alkemy.ong.models.response.MemberResponse;

import java.util.List;

public interface MemberService {

   List<MemberResponse> getMembers();
   void createMember(MemberRequest request);
   void deleteMember(Long id);
   void updateMember(Long id, UpdateMemberRequest request);
}
