package com.alkemy.ong.controller;

import com.alkemy.ong.models.request.OrganizationRequest;
import com.alkemy.ong.models.response.OrganizationResponse;
import com.alkemy.ong.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("organization")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;
    
    @PostMapping
    public ResponseEntity<OrganizationResponse> save (@RequestBody OrganizationRequest organization){
        return ResponseEntity.status(HttpStatus.CREATED).body(organizationService.save(organization));
    }
}
