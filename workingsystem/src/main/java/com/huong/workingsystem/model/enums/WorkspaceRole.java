package com.huong.workingsystem.model.enums;


public enum WorkspaceRole {
     ADMIN("ADMIN" , "Quản  trị viên "),
     MEMBER("MEMBER", "Thành viên  thường"),
     GUEST("GUEST" , "Khách quan  sát");

     private final String code;
     private final String displayName;
      WorkspaceRole(String code , String displayName) {
          this.code = code ;
          this.displayName = displayName;
     }
     public String getCode() {
           return code ;
     }
     public String getDisplayName() {
           return displayName;
     }
}
