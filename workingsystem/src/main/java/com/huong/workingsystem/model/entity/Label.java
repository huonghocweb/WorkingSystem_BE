package com.huong.workingsystem.model.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "labels")
public class Label {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "label_id")
    private Integer labelId;

    @Column(name = "label_name")
    private String labelName ; 

    @Column(name="label_color")
    private String labelColor;

    @ManyToOne
    @JoinColumn(name ="board_id")
    private Board board ;    

    @ManyToMany(mappedBy="labels")
    private List<Card> cards;
}
