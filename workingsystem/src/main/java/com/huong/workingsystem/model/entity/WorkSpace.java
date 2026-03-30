package com.huong.workingsystem.model.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "work_spaces")
public class WorkSpace {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "work_space_id")
    private Integer workSpaceId;

    @Column(name = "work_space_title") 
    private String workSpaceTitle;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @OneToMany(mappedBy="workSpace")
    private List<Board> boards;

    @ManyToMany(mappedBy="workSpaces")
    private List<User> users;
}
