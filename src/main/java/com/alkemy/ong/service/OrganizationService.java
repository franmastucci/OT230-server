package com.alkemy.ong.service;

import com.alkemy.ong.models.request.OrganizationRequest;
import com.alkemy.ong.models.response.OrganizationResponse;

public interface OrganizationService {

    public OrganizationResponse save(OrganizationRequest request);
}
