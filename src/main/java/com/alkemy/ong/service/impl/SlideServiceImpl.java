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

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class SlideServiceImpl implements SlideService {

    private SlideRepository slideRepository;
    private SlideMapper slideMapper;

    public SlideServiceImpl(SlideRepository slideRepository, SlideMapper slideMapper) {
        this.slideRepository = slideRepository;
        this.slideMapper = slideMapper;
    }

    @Override
    public SlideResponse details(Long id) throws SlideNotFoundException {
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
        } else {
            throw new SlideNotFoundException("No slide found with that id");
        }
    }

    @Override
    public List<SlidesBasicResponse> getAllSlides() throws SlideNotFoundException {
        List<SlideEntity> entities = slideRepository.findAll();
        if (entities.isEmpty()) {
            throw new SlideNotFoundException("The Slide List is Empty");
        }
        return this.slideMapper.toBasicListResponse(entities);
    }

    private void verification(SlidesRequest slidesRequest){
        if (slidesRequest.getSort() == null) {
            try {
                Integer lastSort = (this.slideRepository.findAll().get(slideRepository.findAll().size() - 1)
                        .getSort()) + 1;
                slidesRequest.setSort(lastSort);
            } catch (SlideNotFoundException e) {
                slidesRequest.setSort(1);
            }
        }
    }

    @Override
    public SlideResponse create(SlidesRequest slidesRequest) throws IOException {
        verification(slidesRequest);
        SlideEntity slideEntity = slideMapper.toSlideEntityS3(slidesRequest);
        this.slideRepository.save(slideEntity);
        return this.slideMapper.entityToResponse(slideEntity);
    }

    private SlideEntity getById(Long id){
        return slideRepository.findById(id)
                .orElseThrow(() -> new SlideNotFoundException("Slide ID not found"));
    }

    @Override
    public SlideResponse update(Long id, SlidesRequest slidesRequest) throws SlideNotFoundException, IOException {
        SlideEntity slideObteined = getById(id);
        verification(slidesRequest);
        this.slideMapper.changeValues(slideObteined, slidesRequest);
        SlideEntity slideUpdated = this.slideRepository.save(slideObteined);
        return this.slideMapper.entityToResponse(slideUpdated);
    }

}
