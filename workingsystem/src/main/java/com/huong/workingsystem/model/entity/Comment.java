package com.huong.workingsystem.model.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Integer  comment_id;

    @Column(name ="comment_content")
    private String commentContent;

    @Column(name="create_at")
    private LocalDateTime createAt;
    
    @Column(name="update_at")
    private LocalDateTime updateAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;
}
