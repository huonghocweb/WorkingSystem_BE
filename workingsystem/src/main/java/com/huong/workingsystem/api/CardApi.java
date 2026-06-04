package com.huong.workingsystem.api;

import com.cloudinary.Api;
import com.huong.workingsystem.annotation.TrackActivity;
import com.huong.workingsystem.model.dto.MoveCardRequest;
import com.huong.workingsystem.model.dto.UserDetailCustom;
import com.huong.workingsystem.model.enums.ActionType;
import com.huong.workingsystem.model.enums.ContextType;
import com.huong.workingsystem.model.enums.EntityType;
import com.huong.workingsystem.model.request.AttachmentRequest;
import com.huong.workingsystem.model.request.CardRequest;
import com.huong.workingsystem.model.request.CommentRequest;
import com.huong.workingsystem.model.response.ApiResponse;
import com.huong.workingsystem.service.AttachmentService;
import com.huong.workingsystem.service.CardService;
import com.huong.workingsystem.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/cards/v1")
@RequiredArgsConstructor
public class CardApi {
    private final CardService cardService;
    private  final AttachmentService attachmentService;
    private final CommentService commentService;

    @GetMapping("/{cardId}")
    public ResponseEntity<Object> getCardDetailById(@PathVariable("cardId") Integer cardId) {
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .success(true)
                .data(cardService.getCardDetailById(cardId))
                .message("Get cardDetail by carId success")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/cardSummary/{cardId}")
    public ResponseEntity<Object> getCardSummaryById(@PathVariable("cardId") Integer cardId) {
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .success(true)
                .data(cardService.getCardSummaryById(cardId))
                .message("Get card summary by id")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @TrackActivity(actionType = ActionType.ADD, entityType = EntityType.CARD ,
            contextType = ContextType.CARD , entityId = "#result.data.cardId")
    @PostMapping
    public ResponseEntity<Object> createCard(
            @RequestPart("cardRequest")CardRequest cardRequest){
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .success(true)
                .data(cardService.createCard(cardRequest))
                .message("Create card success")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @TrackActivity(entityType = EntityType.CARD, actionType = ActionType.UPDATE,
            contextType = ContextType.CARD , entityId = "#result.data.cardId")
    @PutMapping("/{cardId}")
    public ResponseEntity<Object> updateCard(
            @PathVariable("cardId") Integer cardId ,
            @RequestPart("cardRequest") CardRequest cardRequest
    ){
        System.out.println("Update card 123:  "+ cardRequest);
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .success(true)
                .data(cardService.updateCard(cardId, cardRequest))
                .message("Update card success")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/archive/{cardId}")
    public ResponseEntity<Object> archiveCard(
            @PathVariable("cardId") Integer cardId
    ){
        cardService.archiveCard(cardId);
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .success(true)
                .message("Archive card success")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/cardsArchive/{boardId}")
    public ResponseEntity<Object> getCardsArchive(
            @PathVariable("boardId") Integer boardId
    ){
        System.out.println("Get cards archive");
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .success(true)
                .message("Get cards archive success")
                .data(cardService.getCardsArchive(boardId))
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/restore/{cardId}")
    public ResponseEntity<Object> restoreCard(
            @PathVariable("cardId") Integer cardId
    ){
        ApiResponse<Object> apiResponse  = ApiResponse.builder()
                .success(true)
                .message("Restore Card success")
                .data(cardService.restoreCard(cardId))
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{cardId}")
    public  ResponseEntity<Object> deleteCard(
            @PathVariable("cardId") Integer cardId
    ){
        cardService.deleteCard(cardId);
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .success(true)
                .message("Delete card success")
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    @PutMapping("/moveCard/{cardId}")
    public ResponseEntity<Object> updateCard(
            @PathVariable("cardId") Integer cardId ,
            @RequestBody MoveCardRequest moveCardRequest
            ) {
        System.out.println("move Card");
        ApiResponse<Object> apiResponse= ApiResponse.builder()
                .success(true)
                .message("Move card success")
                .data(cardService.moveCard(cardId, moveCardRequest.getNewOrderIndex(), moveCardRequest.getNewBoardListId()))
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/{cardId}/cardLabels/{labelId}")
    public ResponseEntity<Object> addLabelToCard(
            @PathVariable("cardId") Integer cardId,
            @PathVariable("labelId") Integer labelId
    ){
        System.out.println("add lable to  card: " + cardId + ": " + labelId);
        ApiResponse<Object> apiResponse= ApiResponse.builder()
                .success(true)
                .data(cardService.addLabelToCard(cardId, labelId ))
                .message("Add label to card success")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{cardId}/cardLabels/{labelId}")
    public ResponseEntity<Object> deleteLabelFromCard(
            @PathVariable("cardId") Integer cardId,
            @PathVariable("labelId") Integer labelId
    ){
        System.out.println("deleted");
        cardService.deleteLabelFromCard(cardId, labelId);
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .success(true)
                .message("Delete label from card success")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @TrackActivity(actionType = ActionType.ADD ,
            entityType = EntityType.MEMBER,
            contextType = ContextType.CARD ,
            entityIdParam = "assigneeId",
            entityId = "#")
    @PostMapping("/{cardId}/cardAssignees/{assigneeId}")
    public  ResponseEntity<Object> addAssigneeToCard(
            @PathVariable("cardId") Integer cardId,
            @PathVariable("assigneeId") Integer  assigneeId
    ){
        System.out.println("add assignee" + cardId + "us" + assigneeId);
        ApiResponse<Object> apiResponse= ApiResponse.builder()
                .success(true)
                .data(cardService.addAssigneeToCard(cardId,  assigneeId))
                .message("Add assignee to card success")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @TrackActivity(actionType = ActionType.DELETE , entityType = EntityType.MEMBER,
                    contextType = ContextType.CARD , entityIdParam = "assigneeId")
    @DeleteMapping("/{cardId}/cardAssignees/{assigneeId}")
    public ResponseEntity<Object> deleteAssigneeFromCard(
            @PathVariable("cardId")  Integer  cardId,
            @PathVariable("assigneeId") Integer assigneeId
    ){
        cardService.deleteAssigneeFromCard(cardId , assigneeId);
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .success(true)
                .message("Delete assignee from card ")
                .build();
        return ResponseEntity.ok(apiResponse);
    }


    @GetMapping("/attachments/{attachmentId}")
    public ResponseEntity<Object> getAttachmentById(@PathVariable("attachmentId") Integer attachmentId) {
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .success(true)
                .data(attachmentService.getAttachmentById(attachmentId))
                .message("Get attachment by Id")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @TrackActivity(actionType = ActionType.ADD, entityType = EntityType.FILE,
            contextType = ContextType.CARD , entityId = "#result.data.attachmentId")
    @PostMapping("/{cardId}/attachments")
    public ResponseEntity<Object> createAttachment(
            @PathVariable("cardId")  Integer cardId,
            @RequestPart("file") MultipartFile   file,
            Authentication authentication
    ) throws IOException {
        UserDetailCustom userDetailCustom = (UserDetailCustom) authentication.getPrincipal();
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .success(true)
                .data(attachmentService.createAttachment(cardId, userDetailCustom.getUserId(), file ))
                .message("Create attachment success")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @TrackActivity(actionType = ActionType.DELETE , entityType = EntityType.FILE ,
            contextType = ContextType.CARD , entityId = "#result.data.attachmentId")
    @DeleteMapping("/attachments/{attachmentId}")
    public  ResponseEntity<Object> deleteAttachment(@PathVariable("attachmentId") Integer attachmentId) {
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .success(true)
                .message("Delete attachment success")
                .data( attachmentService.deleteAttachment(attachmentId))
                .build();
        return ResponseEntity.ok(apiResponse);
    }


    @TrackActivity(entityType =  EntityType.COMMENT, actionType = ActionType.ADD,
            contextType =  ContextType.CARD, entityId = "#result.data.commentId")
    @PostMapping("/{cardId}/comments")
    public ResponseEntity<Object> createComment(
            @PathVariable("cardId") Integer cardId,
            @RequestPart("commentRequest")CommentRequest commentRequest,
            Authentication authentication
            ){
        System.out.println("CommentRequest  create " + commentRequest);
        UserDetailCustom userDetailCustom = (UserDetailCustom) authentication.getPrincipal();
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .success(true)
                .data(commentService.createComment(commentRequest ,userDetailCustom.getUserId())  )
                .message("Create comment success")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @TrackActivity(actionType = ActionType.ADD, entityType = EntityType.COMMENT,
                    contextType = ContextType.CARD, entityId = "#result.data.commentId")
    @PostMapping("/{cardId}/comments/{commentParentId}")
    public ResponseEntity<Object> createCommentReply (
            @PathVariable("cardId") Integer cardId,
            @PathVariable("commentParentId") Integer commentParentId,
            @RequestPart("commentRequest") CommentRequest commentRequest,
            Authentication authentication
    ){
        UserDetailCustom userDetailCustom = (UserDetailCustom) authentication.getPrincipal();
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .success(true)
                .data(commentService.createCommentReply(commentParentId, commentRequest, userDetailCustom.getUserId()))
                .message("Create comment reply success")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @TrackActivity(actionType = ActionType.UPDATE, entityType = EntityType.COMMENT,
                    contextType = ContextType.CARD, entityId = "#result.data.commentId")
    @PutMapping("/comments/{commentId}")
    public  ResponseEntity<Object> updateComment(
            @PathVariable("commentId") Integer commentId,
            @RequestPart("commentRequest") CommentRequest commentRequest
    ){
        ApiResponse<Object>  apiResponse = ApiResponse.builder()
                .success(true)
                .data(commentService.updateComment(commentId, commentRequest))
                .message("Update comment sucess")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @TrackActivity(actionType = ActionType.DELETE,entityType = EntityType.COMMENT,
    contextType = ContextType.CARD, entityId = "#result.data.commentId")
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Object> deleteComment(
            @PathVariable("commentId")  Integer commentId)  {

        ApiResponse<Object>  apiResponse  = ApiResponse.builder()
                .success(true)
                .message("Delete comment  success")
                .data(commentService.deleteComment(commentId))
                .build();
        return ResponseEntity.ok(apiResponse);
    }

}
