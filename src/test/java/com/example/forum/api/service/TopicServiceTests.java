package com.example.forum.api.service;

import static com.example.forum.api.data.JpaRepositoryUtils.findById;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.forum.api.domain.Comment;
import com.example.forum.api.domain.Topic;
import com.example.forum.api.repository.CommentRepository;
import com.example.forum.api.repository.TopicRepository;
import com.example.forum.api.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
public class TopicServiceTests {

  @Autowired
  private TopicService topicService;
  @Autowired
  private TopicRepository topicRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private CommentRepository commentRepository;

  @AfterEach
  public void cleanAll() {
    userRepository.deleteAll();
    topicRepository.deleteAll();
    commentRepository.deleteAll();
  }

  @Test
  @Sql("/users-create.sql")
  public void createWithExistingCreator() {
    var topic = topicService.create(new Topic(), "first");

    assertThat(topicRepository.findAll())
        .hasSize(1)
        .contains(topic);
  }

  @Test
  @Sql({"/users-create.sql", "/topics-create.sql"})
  public void findAll() {
    var topicsList = topicService.findAll();

    assertThat(topicsList).hasSize(3);
  }

  @Test
  @Sql({"/users-create.sql", "/topics-create.sql"})
  public void findByIdWithExistingId() {
    var topicId = 1L;
    var topic = topicService.findById(topicId);

    assertThat(topic)
        .map(Topic::getId)
        .hasValue(topicId);
  }

  @Test
  @Sql({"/users-create.sql", "/topics-create.sql"})
  public void updateWithExistingId() {
    var topicId = 1L;
    var topic = findById(topicId, topicRepository);
    var oldTopicName = topic.getName();
    var newTopicName = "NewTopicName";

    topic.setName(newTopicName);
    var updatedTopic = topicService.update(topic);

    assertThat(updatedTopic.getName()).isNotEqualTo(oldTopicName);
    assertThat(updatedTopic.getName()).isEqualTo(newTopicName);
    assertThat(updatedTopic.getId()).isEqualTo(topicId);
  }

  @Test
  @Sql({"/users-create.sql", "/topics-create.sql"})
  public void deleteById() {
    var topicId = 1L;
    topicService.deleteById(topicId);
    assertThat(topicRepository.findById(topicId)).isEmpty();
  }

  @Test
  @Sql({"/users-create.sql", "/topics-create.sql"})
  public void addCommentWithExistingCreatorAndTopic() {
    var comment = topicService.addComment(new Comment(), 1L, "first");

    assertThat(commentRepository.findAll())
        .hasSize(1)
        .contains(comment);
  }

  @Test
  @Sql({"/users-create.sql", "/topics-create.sql", "/comments-create.sql"})
  public void getCommentsWithExistingTopic() {
    var comments = topicService.getComments(1L);

    assertThat(comments).hasSize(3);
  }

  @Test
  @Sql({"/users-create.sql", "/topics-create.sql", "/comments-create.sql"})
  public void updateCommentWithExistingId() {
    var commentId = 1L;
    var comment = findById(commentId, commentRepository);
    var oldText = comment.getText();
    var newText = "NewText";

    comment.setText(newText);
    var updatedComment = topicService.updateComment(comment);

    assertThat(updatedComment.getText()).isNotEqualTo(oldText);
    assertThat(updatedComment.getText()).isEqualTo(newText);
    assertThat(updatedComment.getId()).isEqualTo(commentId);
  }

  @Test
  @Sql({"/users-create.sql", "/topics-create.sql", "/comments-create.sql"})
  public void deleteCommentById() {
    var commentId = 1L;
    topicService.deleteCommentById(commentId);
    assertThat(commentRepository.findById(commentId)).isEmpty();
  }
}
