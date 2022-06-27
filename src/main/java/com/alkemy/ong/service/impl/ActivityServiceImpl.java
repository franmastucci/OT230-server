package com.alkemy.ong.service.impl;

import com.alkemy.ong.models.entity.ActivityEntity;
import com.alkemy.ong.models.mapper.ActivityMapper;
import com.alkemy.ong.models.request.ActivityRequest;
import com.alkemy.ong.models.response.ActivityResponse;
import com.alkemy.ong.repository.ActivityRepository;
import com.alkemy.ong.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityServiceImpl implements ActivityService{

    @Autowired
    private ActivityRepository acitivityRepository;
    
    @Autowired
    private ActivityMapper activityMapper;
    
    @Override
    public ActivityResponse create(ActivityRequest request) {
        ActivityEntity entity = activityMapper.requestToEntity(request);
        acitivityRepository.save(entity);
        return activityMapper.entityToResponse(entity);
    }

    
}
