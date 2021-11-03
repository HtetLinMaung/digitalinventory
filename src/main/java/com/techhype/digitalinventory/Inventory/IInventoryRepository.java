package com.techhype.digitalinventory.Inventory;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IInventoryRepository extends JpaRepository<Inventory, Long> {
        Optional<Inventory> findByItemrefAndShopidAndCompanyidAndStatus(String itemref, String shopid, String companyid,
                        int status);

        @Query("select i from Inventory i where status = 1 and shopid = :shopid and companyid = :companyid and (itemref like %:search% or itemcode like %:search% or label like %:search% or tag like %:search%)")
        Page<Inventory> findInventoryWithContain(@Param("shopid") String shopid, @Param("companyid") String companyid,
                        @Param("search") String search, Pageable pagable);

        @Query("select i from Inventory i where status = 1 and userid = :userid and shopid = :shopid and companyid = :companyid and (itemref like %:search% or itemcode like %:search% or label like %:search% or tag like %:search%)")
        Page<Inventory> findInventoryWithContain(@Param("userid") String userid, @Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("search") String search, Pageable pagable);

        @Query("select i from Inventory i where status = 1 and shopid = :shopid and companyid = :companyid and (itemref = :search or itemcode = :search or label = :search or tag = :search)")
        Page<Inventory> findInventory(@Param("shopid") String shopid, @Param("companyid") String companyid,
                        @Param("search") String search, Pageable pagable);

        @Query("select i from Inventory i where status = 1 and userid = :userid and shopid = :shopid and companyid = :companyid and (itemref = :search or itemcode = :search or label = :search or tag = :search)")
        Page<Inventory> findInventory(@Param("userid") String userid, @Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("search") String search, Pageable pagable);

        Page<Inventory> findByShopidAndCompanyidAndStatus(String shopid, String companyid, int status,
                        Pageable pagable);

        Page<Inventory> findByUseridAndShopidAndCompanyidAndStatus(String userid, String shopid, String companyid,
                        int status, Pageable pagable);

        List<InventoryDto> findByShopidAndCompanyidAndStatus(String shopid, String companyid, int status);

        // List<InventoryDto> findByUseridAndShopidAndCompanyidAndStatus(String userid,
        // String shopid, String companyid,
        // int status);

        // for super admin
        Optional<Inventory> findByItemrefAndStatus(String itemref, int status);

        @Query("select i from Inventory i where status = 1 and (itemref like %:search% or itemcode like %:search% or label like %:search% or tag like %:search%)")
        Page<Inventory> findInventoryWithContain(@Param("search") String search, Pageable pagable);

        @Query("select i from Inventory i where status = 1 and (itemref = :search or itemcode = :search or label = :search or tag = :search)")
        Page<Inventory> findInventory(@Param("search") String search, Pageable pagable);

        Page<Inventory> findByStatus(int status, Pageable pagable);

        List<InventoryDto> findByStatus(int status);

        // For Admin
        Optional<Inventory> findByItemrefAndCompanyidAndStatus(String itemref, String companyid, int status);

        @Query("select i from Inventory i where status = 1 and companyid = :companyid and (itemref like %:search% or itemcode like %:search% or label like %:search% or tag like %:search%)")
        Page<Inventory> findInventoryWithContain(@Param("companyid") String companyid, @Param("search") String search,
                        Pageable pagable);

        @Query("select i from Inventory i where status = 1 and companyid = :companyid and (itemref = :search or itemcode = :search or label = :search or tag = :search)")
        Page<Inventory> findInventory(@Param("companyid") String companyid, @Param("search") String search,
                        Pageable pagable);

        Page<Inventory> findByCompanyidAndStatus(String companyid, int status, Pageable pagable);

        List<InventoryDto> findByCompanyidAndStatus(String companyid, int status);
}
