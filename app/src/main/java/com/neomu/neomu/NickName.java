package com.neomu.neomu;

public class NickName {

    public static String NICK_NAME;

    public NickName() {
    }

    public NickName(String userName) {
        this.NICK_NAME = userName;
    }

    public void setUserName(String userName) {
        this.NICK_NAME = userName;
    }

    public static String getNick() {
        return NICK_NAME;
    }

}
