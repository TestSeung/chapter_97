package com.github.supercoding.repository.users;

import java.util.Objects;

public class UserEntity {
    private Integer userId;
    private String userName;
    private String likeTravelPlace;
    private String phoneNum;

    public UserEntity(Integer userId, String userName, String likeTravelPlace, String phoneNum) {
        this.userId = userId;
        this.userName = userName;
        this.likeTravelPlace = likeTravelPlace;
        this.phoneNum = phoneNum;
    }

    public Integer getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userId);
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLikeTravelPlace() {
        return likeTravelPlace;
    }

    public void setLikeTravelPlace(String likeTravelPlace) {
        this.likeTravelPlace = likeTravelPlace;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
