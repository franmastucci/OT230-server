package com.alkemy.ong.service.impl;

import com.alkemy.ong.exception.SlideNotFoundException;
import com.alkemy.ong.models.entity.SlideEntity;
import com.alkemy.ong.models.mapper.SlideMapper;
import com.alkemy.ong.models.request.SlidesRequest;
import com.alkemy.ong.models.response.SlideResponse;
import com.alkemy.ong.models.response.SlidesBasicResponse;
import com.alkemy.ong.repository.SlideRepository;
import com.alkemy.ong.service.SlideService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SlideServiceImpl implements SlideService {

    private SlideRepository slideRepository;
    private SlideMapper slideMapper;

    public SlideServiceImpl(SlideRepository slideRepository, SlideMapper slideMapper) {
        this.slideRepository = slideRepository;
        this.slideMapper = slideMapper;
    }

    @Override
    public SlideResponse details(Long id) throws SlideNotFoundException{
        SlideEntity entity = this.slideRepository.findById(id).orElse(null);
        
        if (entity == null) {
            throw new SlideNotFoundException("No slide found with that id");
        }
        
        return this.slideMapper.entityToResponse(entity);
    }

    @Override
    public String delete(Long id) throws SlideNotFoundException {
        if (this.slideRepository.existsById(id)) {
            this.slideRepository.deleteById(id);
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

    @Override
    public SlideResponse create(SlidesRequest slidesRequest) {
        if (slidesRequest.getSort() == null) {
            try {
                Integer lastSort = (this.slideRepository.findAll().size() - 1) + 1;
            } catch (SlideNotFoundException e) {
                slidesRequest.setSort(1);
            }
        }
        // todo: pasar todo el request como entidad y persistirla en la DB
    }


}
