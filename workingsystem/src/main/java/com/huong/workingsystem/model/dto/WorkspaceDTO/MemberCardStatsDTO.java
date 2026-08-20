package com.huong.workingsystem.model.dto.WorkspaceDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberCardStatsDTO {
    private Integer userId ;
    private String userName  ;
    private String imagePublicId;
    private String imageUrl;
    private Long totalCards;
    private Long activeCards;
    private Long completedCards;
    private Long overdueCards;
    private Double progress;

    public MemberCardStatsDTO(Integer userId, String userName, String imagePublicId, Long totalCards) {
        this.userId = userId;
        this.userName = userName;
        this.imagePublicId = imagePublicId;
        this.totalCards = totalCards;
    }
}
