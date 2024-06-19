package com.sparta.viewfinder.entity;

public enum UserStatusEnum {
    USER(Status.USER),
    NON_USER(Status.NON_USER);

    private final String status;

    UserStatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public static class Status {
        public static final String USER = "USER";
        public static final String NON_USER = "NON_USER";
    }
}
