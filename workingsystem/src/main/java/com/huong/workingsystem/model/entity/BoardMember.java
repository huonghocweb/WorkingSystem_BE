package com.huong.workingsystem.model.entity;

import com.huong.workingsystem.model.enums.BoardRole;
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
@Table(name ="board_members")
public class BoardMember {
    @EmbeddedId
    private BoardMemberId boardMemberId;

    @Enumerated(EnumType.STRING)
    @Column(name ="role")
    private BoardRole role;

    @ManyToOne
    @MapsId("boardId")
    @JoinColumn(name ="board_id")
    private Board board;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

}
