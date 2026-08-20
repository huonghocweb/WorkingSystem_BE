package com.huong.workingsystem.serviceImpl;

import com.huong.workingsystem.model.dto.WorkspaceDTO.MemberCardStatsDTO;
import com.huong.workingsystem.repo.ActivityLogRepo;
import com.huong.workingsystem.repo.CardRepo;
import com.huong.workingsystem.repo.UserRepo;
import com.huong.workingsystem.service.CloudinaryService;
import com.huong.workingsystem.service.MemberCardStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberCardStatsServiceImpl implements MemberCardStatsService {
    private final CardRepo cardRepo;
    private final UserRepo userRepo;
    private final ActivityLogRepo activityLogRepo;
    private final CloudinaryService cloudinaryService;
    @Override
    public MemberCardStatsDTO getMemberCardCountByUserAndWorkspace(Integer userId, Integer workspaceId) {
        return null;
    }

    @Override
    public List<MemberCardStatsDTO> getMemberCardsCountByWorkspace(Integer workspaceId) {
        LocalDateTime sevenDayAgo= LocalDateTime.now().minusDays(7);
        List<MemberCardStatsDTO> memberCardStatsDTOS = userRepo.getMemberCardsStatsByWorkspace(workspaceId);
        List<MemberCardStatsDTO> memberCardStatsDTOS1 = memberCardStatsDTOS.stream().map(memCard -> {
            Long completedCard = cardRepo.countCompletedCardByUserAndWorkspace(memCard.getUserId(),workspaceId);
            MemberCardStatsDTO newMemberCardStatsDTO = MemberCardStatsDTO.builder()
                    .userId(memCard.getUserId())
                    .userName(memCard.getUserName())
                    .imageUrl(cloudinaryService.getImageUrl(memCard.getImagePublicId()) )
                    .totalCards(memCard.getTotalCards())
                    .activeCards(activityLogRepo.countCardActiveByUserAndWorkspace(memCard.getUserId() ,workspaceId,sevenDayAgo))
                    .completedCards(completedCard)
                    .overdueCards(cardRepo.countCardOverdueByUserAndWorkspace(memCard.getUserId(), workspaceId))
                    .progress(Math.round((double) completedCard/memCard.getTotalCards() *100 *100)/100.0)
                    .build();
          //  System.out.println("new MemberCardStats: " + newMemberCardStatsDTO);
            return newMemberCardStatsDTO;
        }).toList();
        //System.out.println("New List : " + memberCardStatsDTOS1);
        return memberCardStatsDTOS1;
    }
}
