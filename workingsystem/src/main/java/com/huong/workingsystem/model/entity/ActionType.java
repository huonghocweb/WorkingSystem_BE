package com.huong.workingsystem.model.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name ="action_types")
@AllArgsConstructor
@NoArgsConstructor
public class ActionType {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "action_type_id")
    private Integer actionTypeId;

    @Column(name = "action_type_name")
    private String actionTypeName;

    @OneToMany(mappedBy="actionType")
    private List<ActivityLog> activityLogs;
    
}
