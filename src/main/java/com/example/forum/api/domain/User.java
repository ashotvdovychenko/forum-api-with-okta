package com.example.forum.api.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  Long id;

  @Column
  String username;

  @Column
  String password;

  @Column(name = "created_at")
  Instant createdAt = Instant.now();

  @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL, orphanRemoval = true)
  List<Topic> createdTopics = new ArrayList<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  List<Comment> comments = new ArrayList<>();

  public void addCreatedTopic(Topic topic) {
    createdTopics.add(topic);
    topic.setCreator(this);
  }

  public void removeCreatedTopic(Topic topic) {
    createdTopics.remove(topic);
    topic.setCreator(null);
  }

  public void addComment(Comment comment) {
    comments.add(comment);
    comment.setUser(this);
  }

  public void removeComment(Comment comment) {
    comments.remove(comment);
    comment.topic.comments.remove(comment);
    comment.setTopic(null);
    comment.setUser(null);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    User user = (User) o;
    return getId() != null && Objects.equals(getId(), user.getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}