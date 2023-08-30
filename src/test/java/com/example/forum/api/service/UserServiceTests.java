package com.example.forum.api.service;

import static com.example.forum.api.data.JpaRepositoryUtils.findById;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.forum.api.domain.User;
import com.example.forum.api.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
public class UserServiceTests {

  @Autowired
  private UserService userService;
  @Autowired
  private UserRepository userRepository;

  @AfterEach
  public void cleanAll() {
    userRepository.deleteAll();
  }

  @Test
  public void createUser() {
    var user = userService.create(new User());

    assertThat(userRepository.findAll())
        .hasSize(1)
        .contains(user);
  }

  @Test
  @Sql("/users-create.sql")
  public void findByIdWithExistingId() {
    var userId = 1L;
    var user = userService.findById(userId);

    assertThat(user)
        .map(User::getId)
        .hasValue(userId);
  }

  @Test
  @Sql("/users-create.sql")
  public void updateWithExistingId() {
    var userId = 1L;
    var user = findById(userId, userRepository);
    var oldUsername = user.getUsername();
    var newUsername = "NewUsername";

    user.setUsername(newUsername);
    var updatedUser = userService.update(user);

    assertThat(updatedUser.getUsername()).isNotEqualTo(oldUsername);
    assertThat(updatedUser.getUsername()).isEqualTo(newUsername);
    assertThat(updatedUser.getId()).isEqualTo(userId);
  }

  @Test
  @Sql("/users-create.sql")
  public void deleteById() {
    var userId = 1L;
    userService.deleteById(userId);
    assertThat(userRepository.findById(userId)).isEmpty();
  }
}
