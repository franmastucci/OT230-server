package com.alkemy.ong.models.mapper;

import com.alkemy.ong.models.entity.OrganizationEntity;
import com.alkemy.ong.models.request.OrganizationRequest;
import com.alkemy.ong.models.response.OrganizationResponse;
import org.springframework.stereotype.Component;

@Component
public class OrganizationMapper {

    public OrganizationEntity requestToEntity(OrganizationRequest request){
        OrganizationEntity entity = new OrganizationEntity();
        entity.setName(request.getName());
        entity.setImage(request.getImage());
        entity.setAddress(request.getAddress());
        entity.setPhone(request.getPhone());
        entity.setEmail(request.getEmail());
        entity.setWelcomeText(request.getWelcomeText());
        entity.setAbautUsText(request.getAbautUsText());
        return entity;
    }
    
    public OrganizationResponse entityToResponse(OrganizationEntity entity){
        OrganizationResponse response = new OrganizationResponse();
        response.setName(entity.getName());
        response.setImage(entity.getImage());
        response.setAddress(entity.getAddress());
        response.setPhone(entity.getPhone());
        response.setEmail(entity.getEmail());
        response.setWelcomeText(entity.getWelcomeText());
        response.setAbautUsText(entity.getAbautUsText());
        return response;
    }
}
