package com.alkemy.ong.models.mapper;

import com.alkemy.ong.models.entity.SlideEntity;
import com.alkemy.ong.models.response.SlideResponse;
import org.springframework.stereotype.Component;

@Component
public class SlideMapper {

    public SlideResponse entityToResponse(SlideEntity entity){
        SlideResponse response = new SlideResponse();
        response.setId(entity.getId());
        response.setImageUrl(entity.getImageUrl());
        response.setOrganizationId(entity.getOrganizationId());
        response.setSort(entity.getSort());
        response.setText(entity.getText());
        return response;
    }
}
