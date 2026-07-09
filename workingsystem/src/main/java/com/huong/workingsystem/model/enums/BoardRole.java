package com.huong.workingsystem.model.enums;

import lombok.*;

public enum BoardRole {
    ADMIN("ADMIN","Quản trị viên "),
    MEMBER("MEMBER" , "Thành viên thường") ,
    GUEST("GUEST" , "Khách quan sát");
    private final String code;
    private final String displayName;
    BoardRole(String code , String displayName) {
        this.code = code;
        this.displayName = displayName;
    }
    public String getCode( ) {
        return code;
    }
    public String getDisplayName() {
        return displayName;
    }
}
