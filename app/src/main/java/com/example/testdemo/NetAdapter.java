package com.example.testdemo;

/**
 * author:tdz
 * email:tdz@geniatech.com
 * created:2020/4/16 15:03
 */
public class NetAdapter {
    
    public String getMomentTitle() {
        return momentTitle;
    }
    
    public String getUserInfo() {
        return userInfo;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public int getAvatar() {
        return avatar;
    }
    
    private String momentTitle;
    private String userInfo;
    private String userName;
    private int avatar;
  

    public NetAdapter(String momentTitle, String userInfo,String userName,int avatar){
        this.momentTitle = momentTitle;
        this.userInfo = userInfo;
        this.userName = userName;
        this.avatar = avatar;
    }
}
