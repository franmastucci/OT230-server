package com.alkemy.ong.controller;

import com.alkemy.ong.models.request.SlidesRequest;
import com.alkemy.ong.models.response.SlideResponse;
import com.alkemy.ong.models.response.SlidesBasicResponse;
import com.alkemy.ong.service.SlideService;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static com.alkemy.ong.controller.ApiConstants.ROLE_ADMIN;
import static com.alkemy.ong.controller.ApiConstants.ROLE_USER;


@RestController
@RequestMapping("/slides")
public class SlideController {

    private final SlideService slideService;

    public SlideController(SlideService slideService) {
        this.slideService = slideService;
    }

    @PreAuthorize(ROLE_ADMIN)
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
    
    @PreAuthorize(ROLE_ADMIN)
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
    public ResponseEntity<List<SlidesBasicResponse>> getAllSlide(){
        List<SlidesBasicResponse> slidesBasicResponse = this.slideService.getAllSlides();
        return ResponseEntity.ok().body(slidesBasicResponse);
    }

    @PreAuthorize(ROLE_ADMIN)
    @PostMapping
    public ResponseEntity<SlideResponse> create(@RequestBody @Valid SlidesRequest slidesRequest) throws IOException {
        SlideResponse saveResponse = this.slideService.create(slidesRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveResponse);
    }

    @PreAuthorize(ROLE_ADMIN)
    @PutMapping("{id}")
    public ResponseEntity<SlideResponse> update(
            @PathVariable Long id, @RequestBody @Valid SlidesRequest slidesRequest) throws IOException {
        SlideResponse updatedSlide = this.slideService.update(id, slidesRequest);
        return ResponseEntity.status(HttpStatus.OK).body(updatedSlide);
    }

    @PreAuthorize(ROLE_USER)
    @GetMapping("/organization")
    public ResponseEntity<List<SlideResponse>> getList4Users(@RequestParam Long orgId){
        List<SlideResponse> slideResponses = this.slideService.getList4Users(orgId);
        return ResponseEntity.ok(slideResponses);
    }

}
