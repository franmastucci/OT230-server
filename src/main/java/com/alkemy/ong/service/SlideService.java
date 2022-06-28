package com.alkemy.ong.service;

import com.alkemy.ong.exception.SlideNotFoundException;
import com.alkemy.ong.models.request.SlidesRequest;
import com.alkemy.ong.models.response.SlideResponse;
import com.alkemy.ong.models.response.SlidesBasicResponse;

import java.util.List;

public interface SlideService {

    SlideResponse details(Long id) throws SlideNotFoundException;
    
    String delete(Long id) throws SlideNotFoundException;

    List<SlidesBasicResponse> getSlideList();

    SlideResponse create(SlidesRequest slidesRequest);
}
