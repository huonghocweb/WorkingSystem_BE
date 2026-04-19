package com.huong.workingsystem.model.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "workspaces")
public class Workspace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workspace_id")
    private Integer workspaceId;

    @Column(name = "workspace_title")
    private String workspaceTitle;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @OneToMany(mappedBy="workspace")
    private List<Board> boards;

    @ManyToOne
    @JoinColumn(name ="visibility_id")
    private Visibility visibility;

    @OneToMany(mappedBy = "workspace")
    private List<WorkspaceMember> workspaceMembers;
}
