package com.alkemy.ong.service.impl;

import com.alkemy.ong.exception.OrgNotFoundException;
import com.alkemy.ong.models.entity.TestimonialEntity;
import com.alkemy.ong.models.mapper.TestimonialMapper;
import com.alkemy.ong.models.request.TestimonialRequest;
import com.alkemy.ong.models.response.TestimonialResponse;
import com.alkemy.ong.repository.TestimonialRepository;
import com.alkemy.ong.service.TestimonialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestimonialServiceImpl implements TestimonialService {

    @Autowired
    private TestimonialRepository testimonialRepository;

    @Autowired
    private TestimonialMapper testimonialMapper;

    @Override
    public TestimonialResponse createTestimonial(TestimonialRequest testimonialRequest) {

        TestimonialEntity testimonialEntity = testimonialMapper.testimonialEntity(testimonialRequest);

        TestimonialResponse response = testimonialMapper.
                testimonialResponse(testimonialRepository.save(testimonialEntity));
        return response;
    }

    @Override
    public TestimonialResponse updateTestimonial(Long id, TestimonialRequest testimonialRequest) {

        TestimonialEntity testimonialEntity = testimonialRepository.findById(id)
                        .orElseThrow(() -> new OrgNotFoundException("Testimonial not found"));

        TestimonialEntity updateEntity = testimonialMapper.updateTestimonial(
                        testimonialEntity, testimonialRequest);

        TestimonialResponse response = testimonialMapper.testimonialResponse(
                        testimonialRepository.save(updateEntity));

        return response;
    }
}
