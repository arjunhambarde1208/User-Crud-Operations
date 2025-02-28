package com.user.user.crud.operations.controller;

import com.user.user.crud.operations.model.User;
import com.user.user.crud.operations.service.UserService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/users"})
public class UserController {
   @Autowired
   private UserService userService;

   @GetMapping({"/fetch"})
   public List<User> getAllUsers() {
      return this.userService.getAllUsers();
   }

   @GetMapping({"/fetch/{id}"})
   public ResponseEntity<User> getUserById(@PathVariable Long id) {
      Optional<User> user = this.userService.getUserById(id);
      return (ResponseEntity)user.map(ResponseEntity::ok).orElseGet(() -> {
         return ResponseEntity.notFound().build();
      });
   }

   @PostMapping({"/create"})
   public ResponseEntity<String> createUser(@RequestBody User user) {
      this.userService.createUser(user);
      return ResponseEntity.status(201).body("User created successfully");
   }

   @PutMapping({"/update/{id}"})
   public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
      try {
         User updatedUser = this.userService.updateUser(id, userDetails);
         return ResponseEntity.ok("User updated successfully: " + updatedUser.getName());
      } catch (RuntimeException var4) {
         return ResponseEntity.status(404).body("User not found");
      }
   }

   @DeleteMapping({"/delete/{id}"})
   public ResponseEntity<String> deleteUser(@PathVariable Long id) {
      Optional<User> user = this.userService.getUserById(id);
      if (user.isPresent()) {
         this.userService.deleteUser(id);
         return ResponseEntity.ok("User deleted successfully");
      } else {
         return ResponseEntity.status(404).body("User not found");
      }
   }
}
