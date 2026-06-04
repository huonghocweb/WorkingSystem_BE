package com.huong.workingsystem.model.response.board;

import com.huong.workingsystem.model.entity.Board;
import com.huong.workingsystem.model.entity.BoardMemberId;
import com.huong.workingsystem.model.entity.User;
import com.huong.workingsystem.model.enums.BoardRole;
import com.huong.workingsystem.model.response.user.UserSummaryResponse;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardMemberResponse {
    private BoardMemberId boardMemberId;
    private BoardRole role;
    private BoardSummaryResponse board;
    private UserSummaryResponse user;
}
