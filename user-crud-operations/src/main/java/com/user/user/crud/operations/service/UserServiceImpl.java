package com.user.user.crud.operations.service;

import com.user.user.crud.operations.custom.exception.UserNotFoundException;
import com.user.user.crud.operations.model.User;
import com.user.user.crud.operations.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
   private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
   @Autowired
   private UserRepository userRepository;

   public List<User> getAllUsers() {
      logger.info("Fetching all users");
      return this.userRepository.findAll();
   }

   public Optional<User> getUserById(Long id) {
      logger.info("Fetching user with ID: {}", id);
      return Optional.ofNullable((User)this.userRepository.findById(id).orElseThrow(() -> {
         logger.error("User with ID {} not found", id);
         return new UserNotFoundException("User not found with ID: " + id);
      }));
   }

   public User createUser(User user) {
      logger.info("Creating new user: Name = {}, Email = {}, Mobile = {}", new Object[]{user.getName(), user.getEmail(), user.getMobileNumber()});
      return (User)this.userRepository.save(user);
   }

   public User updateUser(Long id, User userDetails) {
      logger.info("Updating user with ID: {}", id);
      return (User)this.userRepository.findById(id).map((user) -> {
         logger.info("Before Update - Name: {}, Email: {}, Mobile: {}", new Object[]{user.getName(), user.getEmail(), user.getMobileNumber()});
         user.setName(userDetails.getName());
         user.setEmail(userDetails.getEmail());
         user.setMobileNumber(userDetails.getMobileNumber());
         User updatedUser = (User)this.userRepository.save(user);
         logger.info("After Update - Name: {}, Email: {}, Mobile: {}", new Object[]{updatedUser.getName(), updatedUser.getEmail(), updatedUser.getMobileNumber()});
         return updatedUser;
      }).orElseThrow(() -> {
         logger.error("User with ID {} not found", id);
         return new UserNotFoundException("User not found with ID: " + id);
      });
   }

   public void deleteUser(Long id) {
      if (!this.userRepository.existsById(id)) {
         logger.error("User with ID {} not found for deletion", id);
         throw new UserNotFoundException("User not found with ID: " + id);
      } else {
         logger.info("Deleting user with ID: {}", id);
         this.userRepository.deleteById(id);
      }
   }
}
