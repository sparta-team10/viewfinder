package com.sparta.viewfinder.entity;

public enum UserRoleEnum {
    ADMIN(UserRole.ADMIN),
    USER(UserRole.NON_ADMIN);

    private final String role;

    UserRoleEnum(String role) {
        this.role = role;
    }

    public String getRole(){
        return this.role;
    }


    public static class UserRole{
        public static final String ADMIN = "ADMIN";
        public static final String NON_ADMIN = "NON_ADMIN";
    }
}
