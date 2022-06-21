package com.alkemy.ong.service.impl;

import com.alkemy.ong.models.entity.OrganizationEntity;
import com.alkemy.ong.models.mapper.OrganizationMapper;
import com.alkemy.ong.models.request.OrganizationRequest;
import com.alkemy.ong.models.response.OrganizationResponse;
import com.alkemy.ong.repository.OrganizationRepository;
import com.alkemy.ong.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;
    
    @Autowired
    private OrganizationMapper organizationMapper;
    
    @Override
    public OrganizationResponse save(OrganizationRequest request) {
        OrganizationEntity entity = organizationMapper.requestToEntity(request);
        organizationRepository.save(entity);
        return organizationMapper.entityToResponse(entity);
    }

}
