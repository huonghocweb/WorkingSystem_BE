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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "attachments")
@SQLRestriction("delete_at IS NULL")
public class Attachment {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)  
    @Column(name = "attachment_id")
    private Integer attachmentId;   

    @Column(name = "file_public_id")
    private String filePublicId;

    @Column(name =  "file_name")
    private String fileName ;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "file_size")
    private  Long fileSize;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "delete_at")
    private LocalDateTime deleteAt;

    @ManyToOne
    @JoinColumn(name ="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;
    
}