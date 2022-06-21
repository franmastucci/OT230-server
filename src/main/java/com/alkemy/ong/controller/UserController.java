package com.alkemy.ong.controller;

import com.alkemy.ong.auth.config.seeder.DataBaseSeeder;
import com.alkemy.ong.models.request.UserUpdateRequest;
import com.alkemy.ong.models.response.UserDetailsResponse;
import com.alkemy.ong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class UserController {

   @Autowired
   private UserService userService;

   @Autowired
   private DataBaseSeeder seeder;


   @PatchMapping(path = "/{id}")
   public ResponseEntity<Void> updateUser(@PathVariable("id") @Valid @NotNull Long id,
                                    @RequestBody @Valid UserUpdateRequest request) {
      userService.updateUser(id, request);
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
   }

   @DeleteMapping(path = "/{id}")
   public ResponseEntity<Void> deleteUser(@PathVariable("id") @Valid @NotNull Long id) {
      userService.deleteUser(id);
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
   }

   @GetMapping(path = "/users")
   public ResponseEntity<List<UserDetailsResponse>> getUsers() {
      return ResponseEntity.ok(userService.getUsers());
   }

}
