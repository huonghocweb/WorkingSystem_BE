package com.huong.workingsystem.model.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="boards")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "board_id")
    private Integer boardId;

    @Column(name = "board_title")
    private String boardTitle; 

    @Column(name ="create_at")
    private LocalDateTime createAt;

    @ManyToOne
    @JoinColumn(name = "work_space_id") 
    private WorkSpace workSpace;

    @OneToMany(mappedBy="board")
    private List<BoardList> boardLists;

    @OneToMany(mappedBy="board")
    private List<Label> labels;

    @ManyToMany(mappedBy="boards") 
    private List<User> users;
    
}
