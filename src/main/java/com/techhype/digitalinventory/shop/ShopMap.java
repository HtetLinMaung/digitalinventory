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
    @SequenceGenerator(name = "shop_sequence", sequenceName = "shop_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inventory_activity_sequence")
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "shopid", nullable = false)
    private String shopid;

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
}
