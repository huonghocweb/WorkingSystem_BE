package com.huong.workingsystem.model.entity;

import com.huong.workingsystem.model.enums.WorkspaceRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name ="workspace_members")
public class WorkspaceMember {

    @EmbeddedId
    private  WorkspaceMemberId workspaceMemberId;

    @Enumerated(EnumType.STRING)
    @Column(name ="role")
    private WorkspaceRole role;

    @ManyToOne
    @MapsId("workspaceId")
    @JoinColumn(name="workspace_id")
    private Workspace workspace;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name="user_id")
    private User user ;
}
