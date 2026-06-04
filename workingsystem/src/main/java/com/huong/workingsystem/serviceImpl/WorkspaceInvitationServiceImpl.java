package com.huong.workingsystem.serviceImpl;

import com.huong.workingsystem.mapper.WorkspaceInvitationMapper;
import com.huong.workingsystem.model.dto.MailInfo;
import com.huong.workingsystem.model.entity.WorkspaceInvitation;
import com.huong.workingsystem.model.enums.WorkspaceInvitationStatus;
import com.huong.workingsystem.model.request.WorkspaceInvitationRequest;
import com.huong.workingsystem.model.response.workspace.WorkspaceInvitationResponse;
import com.huong.workingsystem.repo.UserRepo;
import com.huong.workingsystem.repo.WorkspaceInvitationRepo;
import com.huong.workingsystem.repo.WorkspaceRepo;
import com.huong.workingsystem.service.MailService;
import com.huong.workingsystem.service.WorkspaceInvitationService;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkspaceInvitationServiceImpl implements WorkspaceInvitationService {
    private final WorkspaceInvitationRepo workspaceInvitationRepo;
    private final WorkspaceInvitationMapper workspaceInvitationMapper;
    private final UserRepo userRepo;
    private final WorkspaceRepo workspaceRepo;
    private final MailService mailService;

    @Transactional
    @Override
    public WorkspaceInvitationResponse createWorkspaceInvitation(WorkspaceInvitationRequest workspaceInvitationRequest) throws MessagingException {
        WorkspaceInvitation workspaceInvitation = new WorkspaceInvitation();
        workspaceInvitation.setEmail(workspaceInvitationRequest.getEmail());
        workspaceInvitation.setWorkspace(workspaceRepo.findById(workspaceInvitationRequest.getWorkspaceId())
                .orElseThrow(()-> new EntityNotFoundException("Not found Workspace")));
        workspaceInvitation.setInviter(userRepo.findById(workspaceInvitationRequest.getInviterId())
                .orElseThrow(()->  new EntityNotFoundException("Not found User")));
        workspaceInvitation.setInviteToken(UUID.randomUUID().toString().substring(0,8));
        workspaceInvitation.setStatus(WorkspaceInvitationStatus.PENDING);
        WorkspaceInvitation workspaceInvitationCreated = workspaceInvitationRepo.save(workspaceInvitation);
        if(workspaceInvitationCreated != null){
            StringBuilder bodyBuilder = new StringBuilder();
            bodyBuilder.append("<html><body style='font-family: Arial, sans-serif; line-height: 1.6; color: #333;'>");
            bodyBuilder.append("<div style='max-width: 600px; margin: 20px auto; padding: 20px; border: 1px solid #dddddd; border-radius: 8px;'>");
            bodyBuilder.append("<h2 style='color: #4A90E2;'>You've been invited!</h2>");
            bodyBuilder.append("<p>Hello,</p>");
            bodyBuilder.append("<p>You have been invited to join the <strong> " + workspaceInvitationCreated.getWorkspace().getWorkspaceTitle() +  " </strong> workspace. Our team uses this space to collaborate and manage projects effectively.</p>");
            bodyBuilder.append("<div style='text-align: center; margin: 30px 0;'>");
            bodyBuilder.append("<a href='[Invitation_URL]' style='background-color: #4A90E2; color: white; padding: 12px 24px; text-decoration: none; border-radius: 4px; font-weight: bold; display: inline-block;'>Join Workspace</a>");
            bodyBuilder.append("</div>");
            bodyBuilder.append("<p style='font-size: 12px; color: #777;'>If the button above doesn't work, copy and paste this link into your browser:</p>");
            bodyBuilder.append("<p style='font-size: 12px; color: #4A90E2;'>[Invitation_URL]</p>");
            bodyBuilder.append("<hr style='border: none; border-top: 1px solid #eee; margin-top: 20px;'>");
            bodyBuilder.append("<p style='font-size: 13px; color: #999;'>Regards,<br>The TaskNova Team</p>");
            bodyBuilder.append("</div>");
            bodyBuilder.append("</body></html>");
            MailInfo mailInfo = MailInfo.builder()
                    .to(workspaceInvitationCreated.getEmail())
                    .subject(workspaceInvitationCreated.getInviter().getLastName() +
                            " " +workspaceInvitationCreated.getInviter().getFirstName() + " has invited you join "
                            +workspaceInvitationCreated.getWorkspace().getWorkspaceTitle() + " workspace in TaskNova" )
                    .body(bodyBuilder.toString())
                    .from(workspaceInvitationCreated.getInviter().getEmail())
                    .build();
            mailService.send(mailInfo);
        }
        return workspaceInvitationMapper.convertEnToRes(workspaceInvitationCreated);
    }

    @Override
    public List<WorkspaceInvitationResponse> getWorkspaceInvitationsByWorkspaceId(Integer workspaceId) {
        List<WorkspaceInvitation>  workspaceInvitations = workspaceInvitationRepo.getWorkspaceInvitationByWorkspaceId(workspaceId);
        return workspaceInvitations.stream()
                .map(workspaceInvitationMapper :: convertEnToRes)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteWorkspaceInvitation(Integer invitationId) {
        WorkspaceInvitation workspaceInvitation = workspaceInvitationRepo.findById(invitationId)
                .orElseThrow(()-> new EntityNotFoundException("Not found  workspaceInvitation"));
        workspaceInvitationRepo.delete(workspaceInvitation);
    }
}
