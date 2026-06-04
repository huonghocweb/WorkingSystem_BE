package com.huong.workingsystem.model.entity;

import com.huong.workingsystem.model.enums.WorkspaceInvitationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name ="workspace_invitations")
public class WorkspaceInvitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="invitation_id")
    private Integer invitationId ;

    @Enumerated(EnumType.STRING)
    @Column(name ="status")
    private WorkspaceInvitationStatus status ;

    @Column(name ="invite_token")
    private String inviteToken;

    @Column(name ="email")
    private String email;

    @ManyToOne
    @JoinColumn(name ="workspace_id")
    private Workspace workspace;

    @ManyToOne
    @JoinColumn(name ="inviter_id")
    private User inviter ;
}
