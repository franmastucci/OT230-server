package com.alkemy.ong.controller;

import com.alkemy.ong.models.entity.OrganizationEntity;
import com.alkemy.ong.models.request.OrganizationRequest;
import com.alkemy.ong.models.response.OrganizationResponse;
import com.alkemy.ong.models.response.OrganizationResponseInfo;
import com.alkemy.ong.repository.OrganizationRepository;
import com.alkemy.ong.service.OrganizationService;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("organizations")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<OrganizationResponse> save (@RequestBody @Valid OrganizationRequest organization){
        return ResponseEntity.status(HttpStatus.CREATED).body(organizationService.save(organization));
    }
    
    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
    @GetMapping("/public")
    public ResponseEntity<List<OrganizationResponseInfo>> dataOrganization(){
        return ResponseEntity.ok(organizationService.GetInfo());
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/public/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") @Valid @NotNull Long id, @Valid @RequestBody OrganizationRequest request){
        OrganizationResponse response = null;
        try {
           response = organizationService.update(id, request);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }
}
