package com.techhype.digitalinventory.Inventory;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name = "Inventory")
@Table
public class Inventory {
    @Id
    @SequenceGenerator(name = "inventory_sequence", sequenceName = "inventory_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inventory_sequence")
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "label", nullable = false)
    private String label;

    @Column(name = "itemref", unique = true)
    private String itemref;

    @Column(name = "itemcode", nullable = false)
    private String itemcode = "";

    @Column(name = "netprice", nullable = false)
    private double netprice;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "isinfinite", nullable = false, columnDefinition = "boolean default true")
    private boolean isinfinite = true;

    private int counts = 0;
    private String remark = "";
    private int remaining = 0;
    private String tag = "";

    @Column(name = "minthreshold", nullable = false, columnDefinition = "int4 default 10")
    private int minthreshold = 10;

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

    public Inventory() {
    }

    public int getMinthreshold() {
        return minthreshold;
    }

    public void setMinthreshold(int minthreshold) {
        this.minthreshold = minthreshold;
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

    public String getLabel() {
        return label;
    }

    public int getRemaining() {
        return remaining;
    }

    public void setRemaining(int remaining) {
        this.remaining = remaining;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isIsinfinite() {
        return isinfinite;
    }

    public void setIsinfinite(boolean isinfinite) {
        this.isinfinite = isinfinite;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemref() {
        return itemref;
    }

    public void setItemref(String itemref) {
        this.itemref = itemref;
    }

    public String getItemcode() {
        return itemcode;
    }

    public void setItemcode(String itemcode) {
        this.itemcode = itemcode;
    }

    public double getNetprice() {
        return netprice;
    }

    public void setNetprice(double netprice) {
        this.netprice = netprice;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
