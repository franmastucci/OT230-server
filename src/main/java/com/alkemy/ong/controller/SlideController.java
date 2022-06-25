package com.alkemy.ong.controller;

import com.alkemy.ong.models.mapper.SlideMapper;
import com.alkemy.ong.models.response.SlideResponse;
import com.alkemy.ong.models.response.SlidesBasicResponse;
import com.alkemy.ong.repository.SlideRepository;
import com.alkemy.ong.service.SlideService;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.alkemy.ong.controller.ApiConstants.ROLE_ADMIN;


@RestController
@RequestMapping("/slides")
public class SlideController {

    private final SlideService slideService;
    private final SlideMapper slideMapper;
    private final SlideRepository slideRepository;

    public SlideController(SlideService slideService, SlideMapper slideMapper, SlideRepository slideRepository) {
        this.slideService = slideService;
        this.slideMapper = slideMapper;
        this.slideRepository = slideRepository;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Object> details(@PathVariable("id") @Valid @NotNull Long id){
         SlideResponse response = new SlideResponse();
        try {
           response = this.slideService.details(id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") @Valid @NotNull Long id){
        try {
            return ResponseEntity.ok(this.slideService.delete(id));
        } catch (Exception e) {
           return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize(ROLE_ADMIN)
    @GetMapping
    public ResponseEntity<?> getSlideList(){
        List<SlidesBasicResponse> slidesBasicResponse = this.slideService.getSlideList();
        return ResponseEntity.ok().body(slidesBasicResponse);
    }


}
