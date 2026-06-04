package com.huong.workingsystem.model.request;

import com.huong.workingsystem.model.entity.Card;
import com.huong.workingsystem.model.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttachmentRequest {
    private Integer userId;
    private Integer cardId;
}
