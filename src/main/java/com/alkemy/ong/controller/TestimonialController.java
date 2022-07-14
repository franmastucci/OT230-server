package com.alkemy.ong.controller;

import com.alkemy.ong.models.request.TestimonialRequest;
import com.alkemy.ong.models.response.TestimonialPageResponse;
import com.alkemy.ong.models.response.TestimonialResponse;
import com.alkemy.ong.service.TestimonialService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.alkemy.ong.utils.ApiConstants.ROLE_ADMIN;
import static com.alkemy.ong.utils.ApiConstants.ROLE_USER;

@RestController
@Api(value = "Testimonial controller", description = "CRUD and Gets methods pertaining to Testimonials")
@RequestMapping(path = "/testimonials")
@PreAuthorize(ROLE_ADMIN)
public class TestimonialController {

    @Autowired
    private TestimonialService testimonialService;

    @PostMapping
    @ApiOperation(value = "Create a Testimonial", notes = "Create a Testimonial using TestimonialRequest save in DB " +
            "and return a TestimonialResponse")
    @ApiResponse(code = 200, message = "CREATED")
    public ResponseEntity<TestimonialResponse> createTestimonial(
                                                        @Valid @RequestBody @ApiParam(
                                                                name = "Create a Testimonial",
                                                                value = "TestimonialRequest",
                                                                required = true)TestimonialRequest testimonial){
        return new ResponseEntity<>(testimonialService.createTestimonial(testimonial), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update a testimonial", notes = "Update a Testimonial using an ID and a TestimonialRequest" +
            ", replacing the one that was in that ID")
    @PutMapping("/{id}")
    @ApiResponses({
    @ApiResponse(code = 201, message = "CREATED"),
    @ApiResponse(code = 404, message = "Testimonial ID is not found")})
    public ResponseEntity<TestimonialResponse> updateTestimonial(
                                                        @PathVariable Long id,
                                                        @Valid @RequestBody TestimonialRequest testimonial){

        return new ResponseEntity<>(testimonialService.updateTestimonial(id, testimonial),HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTestimonial(@PathVariable Long id){

        testimonialService.deleteTestimonial(id);
        return new ResponseEntity<>("Testemional deleted", HttpStatus.OK);
    }

    @GetMapping(path = "/get-all")
    @PreAuthorize(ROLE_USER)
    public ResponseEntity<TestimonialPageResponse> getTestimonials(@RequestParam(defaultValue = "1") Integer page) {
        return ResponseEntity.ok(testimonialService.pagination(page));
    }
}
