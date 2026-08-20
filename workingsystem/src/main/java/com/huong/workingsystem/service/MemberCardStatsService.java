package com.huong.workingsystem.service;

import com.huong.workingsystem.model.dto.WorkspaceDTO.MemberCardStatsDTO;

import java.util.List;

public interface MemberCardStatsService {
    MemberCardStatsDTO getMemberCardCountByUserAndWorkspace(Integer userId , Integer workspaceId);
    List<MemberCardStatsDTO> getMemberCardsCountByWorkspace(Integer workspaceId);
}
