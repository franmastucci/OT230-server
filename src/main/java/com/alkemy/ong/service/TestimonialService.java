package com.alkemy.ong.service;

import com.alkemy.ong.models.request.TestimonialRequest;
import com.alkemy.ong.models.response.TestimonialResponse;

public interface TestimonialService {

    TestimonialResponse createTestimonial(TestimonialRequest testimonialRequest);

    TestimonialResponse updateTestimonial(Long id, TestimonialRequest testimonialRequest);
}
