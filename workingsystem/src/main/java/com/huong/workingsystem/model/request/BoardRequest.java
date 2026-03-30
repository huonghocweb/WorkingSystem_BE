package com.huong.workingsystem.model.request;

import com.huong.workingsystem.model.entity.BoardList;
import com.huong.workingsystem.model.entity.Label;
import com.huong.workingsystem.model.entity.User;
import com.huong.workingsystem.model.entity.WorkSpace;
import com.huong.workingsystem.model.response.user.UserSummaryResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardRequest {
    private String boardTitle;
    private LocalDateTime createAt;
}
