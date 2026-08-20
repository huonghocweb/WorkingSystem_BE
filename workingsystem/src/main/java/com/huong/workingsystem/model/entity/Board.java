package com.huong.workingsystem.model.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.huong.workingsystem.model.enums.BoardStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

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

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @Column(name = "start_date")
    private LocalDateTime  startDate ;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private BoardStatus boardStatus;

    @Column(name ="color")
    private String color;

    @ManyToOne
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;

    @OneToMany(mappedBy="board")
    private List<BoardList> boardLists;

    @OneToMany(mappedBy="board")
    private List<Label> labels;

    @OneToMany(mappedBy = "board")
    private List<BoardMember> boardMembers;

    @OneToMany(mappedBy = "board")
    private List<CardListDuration> cardListDurations;
}
