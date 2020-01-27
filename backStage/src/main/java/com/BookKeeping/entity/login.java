package com.BookKeeping.entity;

public class login {
    private String encryptedData;

    private String vi;

    private String code;

    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }

    public String getVi() {
        return vi;
    }

    public void setVi(String vi) {
        this.vi = vi;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "login{" +
                "encryptedData='" + encryptedData + '\'' +
                ", vi='" + vi + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
