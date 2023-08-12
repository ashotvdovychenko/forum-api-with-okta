package com.example.forum.api.data;

import static com.example.forum.api.data.JpaRepositoryUtils.findById;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.forum.api.domain.Comment;
import com.example.forum.api.domain.Topic;
import com.example.forum.api.repository.CommentRepository;
import com.example.forum.api.repository.TopicRepository;
import com.example.forum.api.repository.UserRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EntityLifecycleTests {
  @Autowired
  private CommentRepository commentRepository;

  @Autowired
  private TopicRepository topicRepository;

  @Autowired
  private UserRepository userRepository;

  @Test
  @Sql({"/users-create.sql", "/topics-create.sql"})
  public void addCommentToTopicAndUserCreatesComment() {
    var topic = findById(1L, topicRepository);
    var user = findById(1L, userRepository);
    var newComment = new Comment();

    user.addComment(newComment);
    topic.addComment(newComment);

    assertFalse(commentRepository.findAll().isEmpty());
  }

  @Test
  @Sql({"/users-create.sql", "/topics-create.sql", "/comments-create.sql"})
  public void deleteCommentDoesNotDeleteTopicAndUser() {
    commentRepository.deleteById(9L);

    assertFalse(commentRepository.existsById(9L));
    assertTrue(userRepository.existsById(1L));
    assertTrue(topicRepository.existsById(1L));
  }

  @Test
  @Sql({"/users-create.sql", "/topics-create.sql", "/comments-create.sql"})
  public void deleteCommentFromUserDeletesComment() {
    var user = findById(1L, userRepository);
    var comment = findById(9L, commentRepository);

    user.removeComment(comment);

    assertFalse(commentRepository.existsById(9L));
  }

  @Test
  @Sql({"/users-create.sql", "/topics-create.sql", "/comments-create.sql"})
  public void deleteCommentFromTopicDeletesComment() {
    var topic = findById(1L, topicRepository);
    var comment = findById(9L, commentRepository);

    topic.removeComment(comment);

    assertFalse(commentRepository.existsById(9L));
  }

  @Test
  @Sql({"/users-create.sql", "/topics-create.sql", "/comments-create.sql"})
  public void deleteUserDeletesComments() {
    userRepository.deleteById(1L);

    assertFalse(commentRepository.existsById(9L));
    assertFalse(commentRepository.existsById(4L));
  }

  @Test
  @Sql({"/users-create.sql", "/topics-create.sql", "/comments-create.sql"})
  public void deleteTopicDeletesComments() {
    topicRepository.deleteById(1L);

    assertFalse(commentRepository.existsById(9L));
    assertFalse(commentRepository.existsById(6L));
    assertFalse(commentRepository.existsById(3L));
  }

  @Test
  @Sql("/users-create.sql")
  public void addTopicToUserCreatesTopic() {
    var user = findById(1L, userRepository);
    var newTopic = new Topic();

    user.addCreatedTopic(newTopic);

    assertFalse(topicRepository.findAll().isEmpty());
  }

  @Test
  @Sql({"/users-create.sql", "/topics-create.sql"})
  public void deleteTopicFromUserDeletesTopic() {
    var user = findById(1L, userRepository);
    var topic = findById(1L, topicRepository);

    user.removeCreatedTopic(topic);

    assertFalse(topicRepository.existsById(1L));
  }

  @Test
  @Sql({"/users-create.sql", "/topics-create.sql", "/comments-create.sql"})
  public void deleteUserDeletesTopic() {
    userRepository.deleteById(1L);

    assertFalse(topicRepository.existsById(1L));
  }

  @Test
  @Sql({"/users-create.sql", "/topics-create.sql", "/comments-create.sql"})
  public void commentsInTopicAreSortedByCreationTime() {
    var actualTopicCommentsIds = findById(1L, topicRepository)
        .getComments()
        .stream()
        .map(Comment::getId)
        .toList();
    var expectedTopicCommentsIds = List.of(3L, 6L, 9L);

    assertThat(actualTopicCommentsIds).isEqualTo(expectedTopicCommentsIds);
  }
}
