package com.techhype.digitalinventory.shop;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IShopRepo extends JpaRepository<Shop, Long> {
    Optional<Shop> findByShopidAndCompanyidAndStatus(String shopid, String companyid, int status);

    Optional<Shop> findByShopidAndStatus(String shopid, int status);

    Page<Shop> findByCompanyidAndStatus(String companyid, int status, Pageable pagable);

    Page<Shop> findByCompanyidAndStatusAndShopidContainingOrCompanyidAndStatusAndShopnameContaining(String companyid,
            int status, String s, String companyid2, int status2, String s2, Pageable pagable);

    Page<Shop> findByCompanyidAndStatusAndShopidOrCompanyidAndStatusAndShopname(String companyid, int status, String s,
            String companyid2, int status2, String s2, Pageable pagable);

    Page<Shop> findByStatus(int status, Pageable pagable);

    Page<Shop> findByStatusAndShopidContainingOrStatusAndShopnameContaining(int status, String s, int status2,
            String s2, Pageable pagable);

    Page<Shop> findByStatusAndShopidOrStatusAndShopname(int status, String s, int status2, String s2, Pageable pagable);
}
