package com.huong.workingsystem.model.response.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSearchResponse {
    private Integer userId ;
    private String userName;
    private String email;
    private String imageUrl;
    @JsonProperty("isExisted")
    private boolean isExisted;
    @JsonProperty("isJoined")
    private  boolean isJoined;
}
