package com.example.forum.api.service;

import com.example.forum.api.domain.User;
import java.util.Optional;

public interface UserService {
  User create(User user);

  Optional<User> findById(Long id);

  User update(User user);

  void deleteById(Long id);
}
