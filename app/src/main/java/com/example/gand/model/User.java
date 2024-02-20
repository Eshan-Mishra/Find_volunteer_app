package com.example.gand.model;

public class User {
    String profile_pic,mail,userName,password,userId,lastMessage,status,mobile_no,residence;

    String age_gender,skills,about;


    public User(String userId, String userName, String mail, String password , String profile_pic, String status,
                String mobile_no, String residence, String age_gender, String skills, String about) {

        this.profile_pic = profile_pic;
        this.mail = mail;
        this.userName = userName;
        this.password = password;
        this.userId = userId;
        this.status = status;
        this.mobile_no = mobile_no;
        this.residence = residence;
        this.age_gender = age_gender;
        this.skills = skills;
        this.about = about;
    }

    public User(){}

    public User(String age_gender, String skills, String about) {
        this.age_gender = age_gender;
        this.skills = skills;
        this.about = about;
    }

    public User(String userId, String userName, String mail, String password , String profile_pic, String status, String mobile_no, String residence){
        this.userId = userId;
        this.userName = userName;
        this.mail = mail;
        this.password = password;
        this.profile_pic = profile_pic;
        this.status = status;
        this.residence=residence;
        this.mobile_no=mobile_no;
    }

    public String getAge_gender() {
        return age_gender;
    }

    public void setAge_gender(String age_gender) {
        this.age_gender = age_gender;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }
}
