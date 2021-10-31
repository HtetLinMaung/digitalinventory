package com.techhype.digitalinventory.InventoryActivity;

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
public class InventoryActivity {
    @Id
    @SequenceGenerator(name = "inventory_activity_sequence", sequenceName = "inventory_activity_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inventory_activity_sequence")
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "label", nullable = false)
    private String label;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "qty", nullable = false)
    private int qty;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "itemref", nullable = false)
    private String itemref;

    @Column(name = "activityref", unique = true)
    private String activityref;

    @Column(name = "itemcode", nullable = false)
    private String itemcode = "";

    private String vouchercode = "";
    private String customername = "";
    private String remark = "";

    @Column(name = "amount", nullable = false)
    private double amount;

    @Column(name = "invstatus", nullable = false)
    private String invstatus;

    @Column(name = "voidstatus", nullable = false)
    private int voidstatus = 1;

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

    @Column(name = "shopid", columnDefinition = "varchar(255) default ''")
    private String shopid;

    @Column(name = "shopname", columnDefinition = "varchar(255) default ''")
    private String shopname;

    @Column(name = "createddate", nullable = false, columnDefinition = "timestamp default now()")
    private LocalDateTime createddate;

    @Column(name = "modifieddate", nullable = false, columnDefinition = "timestamp default now()")
    private LocalDateTime modifieddate;

    public InventoryActivity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
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

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    public String getVouchercode() {
        return vouchercode;
    }

    public void setVouchercode(String vouchercode) {
        this.vouchercode = vouchercode;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getInvstatus() {
        return invstatus;
    }

    public void setInvstatus(String invstatus) {
        this.invstatus = invstatus;
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

    public String getActivityref() {
        return activityref;
    }

    public void setActivityref(String activityref) {
        this.activityref = activityref;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getVoidstatus() {
        return voidstatus;
    }

    public void setVoidstatus(int voidstatus) {
        this.voidstatus = voidstatus;
    }

}
