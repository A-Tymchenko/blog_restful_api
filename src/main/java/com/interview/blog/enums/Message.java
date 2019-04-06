package com.interview.blog.enums;


public enum Message {
    USER_WITH_EMAIL_NOT_EXIST("User with current email doesn't exist!"),
    USER_ALREADY_EXISTS("Email already exists!"),
    USER_NOT_FOUND("User not found!"),
    UNAUTHORIZED_ACCESS("You're not authorized to access this resource!"),
    AUTHENTICATION_FAILED("Authentication failed!"),
    INVALID_TOKEN("Invalid token!");

    private final String msgBody;

    Message(String msgBody) {
        this.msgBody = msgBody;
    }

    public String getMsgBody() {
        return msgBody;
    }
}
