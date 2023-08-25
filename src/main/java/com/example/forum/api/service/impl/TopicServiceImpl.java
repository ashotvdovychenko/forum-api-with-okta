package com.example.forum.api.service.impl;

import com.example.forum.api.domain.Topic;
import com.example.forum.api.repository.TopicRepository;
import com.example.forum.api.repository.UserRepository;
import com.example.forum.api.service.TopicService;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

  private final TopicRepository topicRepository;
  private final UserRepository userRepository;

  @Override
  @Transactional
  public Topic create(Topic topic, String creatorUsername) {
    topic.setCreator(userRepository.findByUsername(creatorUsername)
        .orElseThrow(NoSuchElementException::new));
    return topicRepository.save(topic);
  }

  @Override
  public List<Topic> findAll() {
    return topicRepository.findAll();
  }

  @Override
  public Optional<Topic> findById(Long id) {
    return topicRepository.findById(id);
  }

  @Override
  public Topic update(Topic topic) {
    return topicRepository.save(topic);
  }

  @Override
  public void deleteById(Long id) {
    topicRepository.deleteById(id);
  }

}
