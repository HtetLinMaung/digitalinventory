package com.techhype.digitalinventory.shop;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IShopRepo extends JpaRepository<Shop, Long> {
        Optional<Shop> findByShopidAndCompanyidAndStatus(String shopid, String companyid, int status);

        Optional<Shop> findByShopidAndStatus(String shopid, int status);

        Page<Shop> findByCompanyidAndStatus(String companyid, int status, Pageable pagable);

        @Query("select s from Shop s where status = 1 and companyid = :companyid and (shopid like %:search% or shopname like %:search%)")
        Page<Shop> findCompanyWithContain(@Param("companyid") String companyid, @Param("search") String search,
                        Pageable pagable);

        @Query("select s from Shop s where status = 1 and companyid = :companyid and (shopid = :search or shopname = :search)")
        Page<Shop> findCompany(@Param("companyid") String companyid, @Param("search") String search, Pageable pagable);

        Page<Shop> findByStatus(int status, Pageable pagable);

        @Query("select s from Shop s where status = 1 and (shopid like %:search% or shopname like %:search%)")
        Page<Shop> findCompanyWithContain(@Param("search") String search, Pageable pagable);

        @Query("select s from Shop s where status = 1 and (shopid = :search or shopname = :search)")
        Page<Shop> findCompany(@Param("search") String search, Pageable pagable);

}
