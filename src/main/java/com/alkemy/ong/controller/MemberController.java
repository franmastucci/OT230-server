package com.alkemy.ong.controller;

import com.alkemy.ong.models.request.MemberRequest;
import com.alkemy.ong.models.request.UpdateMemberRequest;
import com.alkemy.ong.models.response.MemberPageResponse;
import com.alkemy.ong.models.response.MemberResponse;
import com.alkemy.ong.service.MemberService;
import com.amazonaws.services.pinpoint.model.ForbiddenException;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.alkemy.ong.utils.ApiConstants.*;

@RestController
@RequestMapping(path = "/members")
@Api(tags = {"Member Controller"})
@SwaggerDefinition(tags = {
        @Tag(name = "Member Controller", description = "Contains the CRUD methods of the ONG members")
})
public class MemberController {

   @Autowired
   private MemberService memberService;

   @GetMapping
   @PreAuthorize(ROLE_ADMIN)
   @Operation(summary = "Get all members")
   @ApiResponses(value = {
           @ApiResponse(responseCode = "200", description = "Return a list of members",
                           content = { @Content(mediaType = "application/json",
                           schema = @Schema(implementation = MemberResponse.class)) }),

           @ApiResponse(responseCode = "403", description = "Forbidden for Role USER standard",
                   content = @Content)
   })
   public ResponseEntity<List<MemberResponse>> getMembers() {
      return ResponseEntity.ok(memberService.getMembers());
   }

   @GetMapping(path = "/get-all")
   @PreAuthorize(ROLE_USER)
   @Operation(summary = "Get Pageable all members")
   @ApiResponses(value = {
           @ApiResponse(responseCode = "200", description = "Return a Page of members",
                   content = { @Content(mediaType = "application/json",
                           schema = @Schema(implementation = MemberPageResponse.class)) }),
           @ApiResponse(responseCode = "403", description = "Forbidden for USER no authenticated",
                   content = @Content)
   })
   public ResponseEntity<MemberPageResponse> getMembers(@RequestParam(defaultValue = "1") Integer page) {
      return ResponseEntity.ok(memberService.pagination(page));
   }

   @PostMapping
   @PreAuthorize(ROLE_USER)
   @Operation(summary = "POST Member")
   @ApiResponses(value = {
           @ApiResponse(responseCode = "201", description = "Return Created status code and Member body",
                   content = { @Content(mediaType = "application/json",
                           schema = @Schema(implementation = MemberResponse.class)) }),
           @ApiResponse(responseCode = "403", description = "Forbidden for USER no authenticated",
                   content = @Content)
   })
   public ResponseEntity<MemberResponse> createMember(@RequestBody @Valid
                                                         @ApiParam(
                                                                 name = "Member json",
                                                                 value = "A member that contains the valid fields described",
                                                                 type = "application/json",
                                                                 required = true)
                                                         MemberRequest request){
      return ResponseEntity.status(HttpStatus.OK).body(memberService.createMember(request));
   }

   @PutMapping(path = "/{id}")
   @PreAuthorize(ROLE_USER)
   @Operation(summary = "PUT Member")
   @ApiResponses(value = {
           @ApiResponse(responseCode = "200", description = "Return OK status code and Member updated",
                   content = { @Content(mediaType = "application/json",
                           schema = @Schema(implementation = MemberResponse.class)) }),
           @ApiResponse(responseCode = "403", description = "Forbidden for USER no authenticated",
                   content = @Content)
   })

   public ResponseEntity<MemberResponse> updateMember(@PathVariable("id") Long id,
                                            @RequestBody UpdateMemberRequest update) {
      return ResponseEntity.status(HttpStatus.OK).body(memberService.updateMember(id, update));
   }

   @DeleteMapping(path = "/{id}")
   @PreAuthorize(BOTH)
   public ResponseEntity<Void> deleteMember(@PathVariable("id") @ApiParam(
                                                                    name = "ID",
                                                                    type = "Long",
                                                                    value = "ID of the member requested",
                                                                    example = "1",
                                                                    required = true) Long id) {
      memberService.deleteMember(id);
      return ResponseEntity.status(HttpStatus.OK).build();
   }
}
