package com.alkemy.ong.models.mapper;

import com.alkemy.ong.models.entity.SlideEntity;
import com.alkemy.ong.models.response.SlideResponse;
import com.alkemy.ong.models.response.SlidesBasicResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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

    public SlidesBasicResponse toBasicResponse(SlideEntity slideEntity) {
        return new SlidesBasicResponse(slideEntity.getImageUrl(), slideEntity.getSort());
    }

    public List<SlidesBasicResponse> toBasicListResponse(List<SlideEntity> entities){
        return entities.stream()
            .map(slideEntity -> toBasicResponse(slideEntity))
            .collect(Collectors.toList());
    }
}
