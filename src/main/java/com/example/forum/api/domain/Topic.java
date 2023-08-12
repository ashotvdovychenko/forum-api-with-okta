package com.example.forum.api.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
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
@Table(name = "topics")
public class Topic {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  Long id;

  @Column
  String name;

  @Column
  String description;

  @Column(name = "created_at")
  Instant createdAt = Instant.now();

  @ManyToOne(optional = false)
  @JoinColumn(name = "creator_id", nullable = false)
  User creator;

  @OrderBy(value = "createdAt asc")
  @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, orphanRemoval = true)
  List<Comment> comments = new ArrayList<>();

  public void addComment(Comment comment) {
    comments.add(comment);
    comment.setTopic(this);
  }

  public void removeComment(Comment comment) {
    comments.remove(comment);
    comment.user.comments.remove(comment);
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
    Topic topic = (Topic) o;
    return getId() != null && Objects.equals(getId(), topic.getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}