package com.alkemy.ong.models.mapper;

import com.alkemy.ong.models.entity.TestimonialEntity;
import com.alkemy.ong.models.request.TestimonialRequest;
import com.alkemy.ong.models.response.TestimonialResponse;
import org.springframework.stereotype.Component;

@Component
public class TestimonialMapper {

    public TestimonialEntity testimonialEntity(TestimonialRequest testimonial){
        return TestimonialEntity.builder()
                .name(testimonial.getName())
                .image(testimonial.getImage())
                .content(testimonial.getContent())
                .build();
    }
    public TestimonialResponse testimonialResponse(TestimonialEntity testimonialEntity){
        return TestimonialResponse.builder()
                .id(testimonialEntity.getId())
                .name(testimonialEntity.getName())
                .image(testimonialEntity.getImage())
                .content(testimonialEntity.getContent())
                .timestamp(testimonialEntity.getTimestamp())
                .build();
    }
}
