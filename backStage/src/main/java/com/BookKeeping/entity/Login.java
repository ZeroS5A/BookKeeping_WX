package com.BookKeeping.entity;

public class Login {
    private String encryptedData;

    private String iv;

    private String code;

    private String token;

    private String session;

    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    @Override
    public String toString() {
        return "Login{" +
                "encryptedData='" + encryptedData + '\'' +
                ", iv='" + iv + '\'' +
                ", code='" + code + '\'' +
                ", token='" + token + '\'' +
                ", session='" + session + '\'' +
                '}';
    }
}
