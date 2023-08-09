package com.example.forum.api.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    @OneToMany(mappedBy = "topic", cascade = CascadeType.PERSIST, orphanRemoval = true)
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
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Topic topic = (Topic) o;
        return getId() != null && Objects.equals(getId(), topic.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}