package com.huong.workingsystem.model.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.SQLRestriction;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "board_lists")
public class BoardList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_list_id")
    private Integer boardListId;

    @Column(name ="board_list_title")
    private  String boardListTitle; 
    
    @Column(name ="position")
    private  Integer position;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name ="delete_at")
    private LocalDateTime deleteAt;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board ; 

    @OneToMany(mappedBy="boardList")
    @OrderBy("orderIndex ASC")
    @SQLRestriction("delete_at is null")
    private List<Card> cards;
}
