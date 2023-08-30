package com.example.forum.api.service.impl;

import com.example.forum.api.domain.User;
import com.example.forum.api.repository.UserRepository;
import com.example.forum.api.service.UserService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;

  @Override
  public User create(User user) {
    return userRepository.save(user);
  }

  @Override
  public Optional<User> findById(Long id) {
    return userRepository.findById(id);
  }

  @Override
  public User update(User user) {
    return userRepository.save(user);
  }

  @Override
  public void deleteById(Long id) {
    userRepository.deleteById(id);
  }
}
