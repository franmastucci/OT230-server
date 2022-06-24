package com.alkemy.ong.service;

import com.alkemy.ong.exception.SlideNotFoundException;
import com.alkemy.ong.models.response.SlideResponse;

public interface SlideService {

    public SlideResponse details(Long id) throws SlideNotFoundException;
    
    public String delete(Long id) throws SlideNotFoundException;
}
