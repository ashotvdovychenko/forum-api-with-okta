package com.example.forum.api.data;

import org.springframework.data.jpa.repository.JpaRepository;

public class JpaRepositoryUtils {
  public static <T, ID> T findById(ID id, JpaRepository<T, ID> repository) {
    return repository.findById(id).orElseThrow(AssertionError::new);
  }
}
