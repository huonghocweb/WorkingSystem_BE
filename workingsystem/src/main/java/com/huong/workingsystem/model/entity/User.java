package com.huong.workingsystem.model.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name ="user_id") 
    private Integer userId ;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "password")
    private String password;

    @Column(name = "birth_day")
    private LocalDate birthDay;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name ="address")
    private String address;

    @Column(name="image_public_id")
    private String imagePublicId;

    @Column(name = "gender")
    private Integer gender; 
    
    @Column(name = "email")
    private String  email;

    @ManyToMany
    @JoinTable(
        name = "user_role" , 
        joinColumns= @JoinColumn(name = "user_id")  , 
        inverseJoinColumns=@JoinColumn(name ="role_id")
    )
    private List<Role> roles ; 

    @OneToMany(mappedBy="user")
    private  List<Attachment>  attachments;

    @ManyToMany(mappedBy = "users")
    private List<Card> cards;

    @ManyToMany
    @JoinTable(
        name ="board_member" , 
        joinColumns= @JoinColumn(name = "user_id") , 
        inverseJoinColumns=@JoinColumn(name ="board_id")
    )
    private List<Board> boards;

    @OneToMany(mappedBy="user")
    private List<Comment> comments;

    @OneToMany(mappedBy="user")
    private  List<ActivityLog> activityLogs;

    @OneToMany(mappedBy = "user")
    private List<RefreshToken> reFreshTokens;

    @OneToMany(mappedBy = "user")
    private List<WorkspaceMember> workspaceMembers;
}
