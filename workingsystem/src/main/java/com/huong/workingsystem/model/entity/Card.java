package com.huong.workingsystem.model.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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
@Table(name = "cards")
public class Card {
 
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "card_id")
    private Integer cardId;

    @Column(name ="card_title")
    private String cardTitle;

    @Column(name ="card_description")
    private String cardDescription;

    @Column(name ="start_date")
    private LocalDateTime startDate; 

    @Column(name ="end_date")
    private LocalDateTime endDate;

    @Column(name = "position")
    private Integer position;

    @ManyToOne 
    @JoinColumn(name = "board_list_id")
    private BoardList boardList;

    @OneToMany(mappedBy="card")
    private List<Attachment> attachments;

    @ManyToMany
    @JoinTable(
        name= "card_label",
        joinColumns= @JoinColumn(name = "card_id") , 
        inverseJoinColumns=@JoinColumn(name = "label_id")
    )
    private List<Label> labels;
    
    @ManyToMany
    @JoinTable(
            name = "card_user" ,
            joinColumns =  @JoinColumn(name = "card_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private  List<User> users;

    @OneToMany(mappedBy="card")
    private List<Comment> comments;

    @OneToMany(mappedBy="card")
    private List<ActivityLog> activityLogs;
}
