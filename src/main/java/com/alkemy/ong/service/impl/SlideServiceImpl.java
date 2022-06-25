package com.alkemy.ong.service.impl;

import com.alkemy.ong.exception.SlideNotFoundException;
import com.alkemy.ong.models.entity.SlideEntity;
import com.alkemy.ong.models.mapper.SlideMapper;
import com.alkemy.ong.models.response.SlideResponse;
import com.alkemy.ong.models.response.SlidesBasicResponse;
import com.alkemy.ong.repository.SlideRepository;
import com.alkemy.ong.service.SlideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SlideServiceImpl implements SlideService {

    @Autowired
    private SlideRepository slideRepository;
    
    @Autowired
    private SlideMapper slideMapper;
    
    @Override
    public SlideResponse details(Long id) throws SlideNotFoundException{
        SlideEntity entity = slideRepository.findById(id).orElse(null);
        
        if (entity == null) {
            throw new SlideNotFoundException("No slide found with that id");
        }
        
        return slideMapper.entityToResponse(entity);
    }

    @Override
    public String delete(Long id) throws SlideNotFoundException {
        if (slideRepository.existsById(id)) {
            slideRepository.deleteById(id);
            return "The slide was deleted correctly";
        } else{
            throw new SlideNotFoundException("No slide found with that id");
        }
    }

    @Override
    public List<SlidesBasicResponse> getSlideList() {
        List<SlideEntity> entities = slideRepository.findAll();
        if (entities.isEmpty()){
            throw new SlideNotFoundException("The Slide List is Empty");
        }
        return slideMapper.toBasicListResponse(entities);
    }


}
