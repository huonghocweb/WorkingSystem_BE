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
@Table(name ="activity_logs")
@AllArgsConstructor
@NoArgsConstructor
public class ActivityLog {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "activity_log_id")
    private Integer activityLogId;

    @Column(name ="old_value")
    private String oldValue;

    @Column(name ="new_value")
    private String newValue;

    @Column(name ="create_at")
    private LocalDateTime createAt;

    @ManyToOne
    @JoinColumn(name = "action_type_id")
    private ActionType actionType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name ="card_id")
    private  Card card;
}
