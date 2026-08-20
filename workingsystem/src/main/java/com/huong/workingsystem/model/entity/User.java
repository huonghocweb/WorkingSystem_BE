package com.huong.workingsystem.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.huong.workingsystem.model.enums.UserStatus;
import jakarta.persistence.*;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserStatus status;

    @Column(name = "is_online")
    private Boolean isOnline;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    @Column(name ="last_active_at")
    private  LocalDateTime lastActiveAt;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "update_at")
    private  LocalDateTime updateAt;

    @OneToMany(mappedBy = "owner")
    private List<Workspace> ownedWorkspaces;

    @OneToMany(mappedBy = "owner")
    private List<Card> ownedCards;

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

    @OneToMany(mappedBy="user")
    private List<Comment> comments;

    @OneToMany(mappedBy = "user")
    private List<RefreshToken> reFreshTokens;

    @OneToMany(mappedBy = "user")
    private List<WorkspaceMember> workspaceMembers;

    @OneToMany(mappedBy = "inviter")
    private List<WorkspaceInvitation> workspaceInvitations;

    @OneToMany(mappedBy = "user")
    private List<BoardMember> boardMembers;

}
