package com.example.forum.api.service;

import com.example.forum.api.domain.Comment;
import com.example.forum.api.domain.Topic;
import java.util.List;
import java.util.Optional;

public interface TopicService {
  Topic create(Topic topic, String creatorUsername);

  List<Topic> findAll();

  Optional<Topic> findById(Long id);

  Topic update(Topic topic);

  void deleteById(Long id);

  Comment addComment(Comment comment, Long topicId, String creatorUsername);

  List<Comment> getComments(Long topicId);

  Comment updateComment(Comment comment);

  void deleteCommentById(Long id);
}
