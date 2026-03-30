package com.huong.workingsystem.model.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board ; 

    @OneToMany(mappedBy="boardList")
    private List<Card> cards;
}
