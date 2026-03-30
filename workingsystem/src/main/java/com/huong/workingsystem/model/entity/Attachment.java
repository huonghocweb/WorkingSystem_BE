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
@Entity
@Table(name = "attachments")
@AllArgsConstructor
@NoArgsConstructor
public class Attachment {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)  
    @Column(name = "attachment_id")
    private Integer attachmentId;   

    @Column(name = "file_url")
    private String fileUrl;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @ManyToOne
    @JoinColumn(name ="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;
    
}