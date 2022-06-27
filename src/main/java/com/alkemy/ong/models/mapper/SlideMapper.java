package com.alkemy.ong.models.mapper;

import com.alkemy.ong.models.entity.SlideEntity;
import com.alkemy.ong.models.request.SlidesRequest;
import com.alkemy.ong.models.response.SlideResponse;
import com.alkemy.ong.models.response.SlidesBasicResponse;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SlideMapper {

    public SlideResponse entityToResponse(SlideEntity entity) {
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

    public List<SlidesBasicResponse> toBasicListResponse(List<SlideEntity> entities) {
        return entities.stream()
                .map(slideEntity -> toBasicResponse(slideEntity))
                .collect(Collectors.toList());
    }

    //temporal
    public SlideEntity toSlideEntityS3(SlidesRequest slidesRequest) {
        return SlideEntity.builder()
                .imageUrl(this.base64Decoder(slidesRequest.getImageUrl()))
                .text(slidesRequest.getText())
                .sort(slidesRequest.getSort())
                .organizationId(slidesRequest.getOrganizationId())
                .build();
    }

    //temporal
    private String base64Decoder(String imgUrl) {
        byte[] bytes = Base64.getDecoder().decode(imgUrl);
        String actualString = new String(bytes);
        return actualString;
    }
}
