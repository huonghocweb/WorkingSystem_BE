package com.huong.workingsystem.model.request;

import com.huong.workingsystem.model.enums.BoardRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardMemberRequest {
    private Integer boardId;
    private Integer userId;
    private BoardRole role;
}
