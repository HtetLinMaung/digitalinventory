package com.techhype.digitalinventory.shop;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IShopMapRepo extends JpaRepository<ShopMap, Long> {
    Optional<ShopMap> findByUserrefAndCompanyidAndStatus(String userref, String companyid, int status);

    Optional<ShopMap> findByUserrefAndStatus(String userref, int status);

    List<ShopMap> findByShopidAndStatus(String shopid, int status);

    List<ShopMap> findByCompanyidAndShopidAndStatus(String companyid, String shopid, int status);
}
