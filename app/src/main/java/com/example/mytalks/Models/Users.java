package com.example.mytalks.Models;



public class Users {

    String profilepic,userName,password,userId,lastMessage,mail;

    public Users(String s, String toString, String string) {
//        this.profilepic = profilepic;
//        this.userName = userName;
        this.password = password;
        this.userId = userId;
//        this.lastMessage = lastMessage;
        this.mail = mail;
    }
    public class users{} //Empty Constructor as Per Firebase Guideline

    //Constructor For SignUp Button


    public Users() {
        this.password = password;
        this.mail = mail;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
