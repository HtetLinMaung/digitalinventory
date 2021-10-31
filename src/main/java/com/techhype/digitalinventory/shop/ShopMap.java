package com.techhype.digitalinventory.shop;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table
public class ShopMap {
    @Id
    @SequenceGenerator(name = "shop_map_sequence", sequenceName = "shop_map_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shop_map_sequence")
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "shopid", nullable = false)
    private String shopid;

    @Column(name = "shopname", nullable = false)
    private String shopname;

    @Column(name = "userref", nullable = false)
    private String userref;

    @Column(name = "status", nullable = false, columnDefinition = "int4 default 1")
    private int status = 1;

    @Column(name = "userid", nullable = false)
    private String userid;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "companyid", nullable = false)
    private String companyid;

    @Column(name = "companyname", nullable = false)
    private String companyname;

    @Column(name = "createddate", nullable = false, columnDefinition = "timestamp default now()")
    private LocalDateTime createddate;

    @Column(name = "modifieddate", nullable = false, columnDefinition = "timestamp default now()")
    private LocalDateTime modifieddate;

    public ShopMap() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getUserref() {
        return userref;
    }

    public void setUserref(String userref) {
        this.userref = userref;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUserid() {
        return userid;
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

    public LocalDateTime getCreateddate() {
        return createddate;
    }

    public void setCreateddate(LocalDateTime createddate) {
        this.createddate = createddate;
    }

    public LocalDateTime getModifieddate() {
        return modifieddate;
    }

    public void setModifieddate(LocalDateTime modifieddate) {
        this.modifieddate = modifieddate;
    }

}
