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

@RestController
@RequestMapping("/slides")
public class SlideController {

    private final SlideService slideService;

    public SlideController(SlideService slideService) {
        this.slideService = slideService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Object> details(@PathVariable("id") @Valid @NotNull Long id) {
        return ResponseEntity.ok(slideService.details(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") @Valid @NotNull Long id) {
        return ResponseEntity.ok(this.slideService.delete(id));
    }

    @PreAuthorize(ROLE_ADMIN)
    @GetMapping
    public ResponseEntity<List<SlidesBasicResponse>> getSlideList() {
        List<SlidesBasicResponse> slidesBasicResponse = this.slideService.getSlideList();
        return ResponseEntity.ok().body(slidesBasicResponse);
    }

    @PreAuthorize(ROLE_ADMIN)
    @PostMapping
    public ResponseEntity<SlideResponse> create(@RequestBody @Valid SlidesRequest slidesRequest) throws IOException {
        SlideResponse saveResponse = this.slideService.create(slidesRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveResponse);
    }
}
