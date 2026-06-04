package com.huong.workingsystem.model.request;

import com.huong.workingsystem.model.entity.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardRequest {
    private String cardTitle;
    private String cardDescription;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Double orderIndex;
    private Integer  boardListId;
   // private List<Integer> labelIds;
    //private  List<Integer> assigneeIds;
//    private List<Integer> attachmentIds;
//    private List<Integer> commentIds;
}
