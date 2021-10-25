package com.techhype.digitalinventory.models;

public class TokenData {
    private String iamtoken;
    private String token;
    private String userid;
    private String username;
    private String companyid;
    private String companyname;
    private String role;
    private String shopname;

    public String getUserid() {
        return userid;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIamtoken() {
        return iamtoken;
    }

    public void setIamtoken(String iamtoken) {
        this.iamtoken = iamtoken;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

}
