package org.sairaa.atmfinder.Model;

public class UserAdmin {
    private String userName;
    private String passWord;
    private int userType;

    public UserAdmin(String userName, String passWord, int userType) {
        this.userName = userName;
        this.passWord = passWord;
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }
}
