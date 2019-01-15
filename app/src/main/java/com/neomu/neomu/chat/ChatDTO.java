package com.neomu.neomu.chat;

public class ChatDTO {

    private String userName;
    private String message;
    private String adminName;

    public ChatDTO() {
    }

    public ChatDTO(String userName, String message) {
        this.userName = userName;
        this.message = message;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setAdminName(String adminName) { this.adminName = adminName; }

    public void setMessage(String message) {this.message = message; }

    public String getUserName() {
        return userName;
    }

    public String getAdminName() { return adminName; }

    public String getMessage() {
        return message;
    }
}
