package com.huong.workingsystem.model.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentResponse {
    private Integer attachmentId;   

    private String fileUrl;

    private LocalDateTime createAt;

}
