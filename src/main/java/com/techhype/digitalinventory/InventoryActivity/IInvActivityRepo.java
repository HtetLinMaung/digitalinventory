package com.techhype.digitalinventory.InventoryActivity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IInvActivityRepo extends JpaRepository<InventoryActivity, Long> {

        @Query("select ia from InventoryActivity ia where status = 1 and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityBySearchContain(@Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByUseridSearchContain(@Param("userid") String userid,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and shopid = :shopid and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByShopidSearchContain(@Param("shopid") String shopid,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and companyid = :companyid and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByCompanyidSearchContain(@Param("companyid") String companyid,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and voidstatus = :voidstatus and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByVoidstatusSearchContain(@Param("voidstatus") int voidstatus,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and invstatus = :invstatus and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByInvstatusSearchContain(@Param("invstatus") String invstatus,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByDateBetweenSearchContain(@Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and shopid = :shopid and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByUseridShopidSearchContain(@Param("userid") String userid,
                        @Param("shopid") String shopid, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and companyid = :companyid and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByUseridCompanyidSearchContain(@Param("userid") String userid,
                        @Param("companyid") String companyid, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and voidstatus = :voidstatus and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByUseridVoidstatusSearchContain(@Param("userid") String userid,
                        @Param("voidstatus") int voidstatus, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and invstatus = :invstatus and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByUseridInvstatusSearchContain(@Param("userid") String userid,
                        @Param("invstatus") String invstatus, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByUseridDateBetweenSearchContain(@Param("userid") String userid,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and shopid = :shopid and companyid = :companyid and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByShopidCompanyidSearchContain(@Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and shopid = :shopid and voidstatus = :voidstatus and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByShopidVoidstatusSearchContain(@Param("shopid") String shopid,
                        @Param("voidstatus") int voidstatus, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and shopid = :shopid and invstatus = :invstatus and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByShopidInvstatusSearchContain(@Param("shopid") String shopid,
                        @Param("invstatus") String invstatus, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and shopid = :shopid and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByShopidDateBetweenSearchContain(@Param("shopid") String shopid,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and companyid = :companyid and voidstatus = :voidstatus and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByCompanyidVoidstatusSearchContain(@Param("companyid") String companyid,
                        @Param("voidstatus") int voidstatus, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and companyid = :companyid and invstatus = :invstatus and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByCompanyidInvstatusSearchContain(@Param("companyid") String companyid,
                        @Param("invstatus") String invstatus, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and companyid = :companyid and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByCompanyidDateBetweenSearchContain(@Param("companyid") String companyid,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and voidstatus = :voidstatus and invstatus = :invstatus and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByVoidstatusInvstatusSearchContain(@Param("voidstatus") int voidstatus,
                        @Param("invstatus") String invstatus, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and voidstatus = :voidstatus and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByVoidstatusDateBetweenSearchContain(@Param("voidstatus") int voidstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByInvstatusDateBetweenSearchContain(@Param("invstatus") String invstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and shopid = :shopid and companyid = :companyid and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByUseridShopidCompanyidSearchContain(@Param("userid") String userid,
                        @Param("shopid") String shopid, @Param("companyid") String companyid,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and shopid = :shopid and voidstatus = :voidstatus and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByUseridShopidVoidstatusSearchContain(@Param("userid") String userid,
                        @Param("shopid") String shopid, @Param("voidstatus") int voidstatus,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and shopid = :shopid and invstatus = :invstatus and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByUseridShopidInvstatusSearchContain(@Param("userid") String userid,
                        @Param("shopid") String shopid, @Param("invstatus") String invstatus,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and shopid = :shopid and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByUseridShopidDateBetweenSearchContain(@Param("userid") String userid,
                        @Param("shopid") String shopid, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and companyid = :companyid and voidstatus = :voidstatus and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByUseridCompanyidVoidstatusSearchContain(@Param("userid") String userid,
                        @Param("companyid") String companyid, @Param("voidstatus") int voidstatus,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and companyid = :companyid and invstatus = :invstatus and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByUseridCompanyidInvstatusSearchContain(@Param("userid") String userid,
                        @Param("companyid") String companyid, @Param("invstatus") String invstatus,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and companyid = :companyid and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByUseridCompanyidDateBetweenSearchContain(@Param("userid") String userid,
                        @Param("companyid") String companyid, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and voidstatus = :voidstatus and invstatus = :invstatus and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByUseridVoidstatusInvstatusSearchContain(@Param("userid") String userid,
                        @Param("voidstatus") int voidstatus, @Param("invstatus") String invstatus,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and voidstatus = :voidstatus and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByUseridVoidstatusDateBetweenSearchContain(
                        @Param("userid") String userid, @Param("voidstatus") int voidstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByUseridInvstatusDateBetweenSearchContain(@Param("userid") String userid,
                        @Param("invstatus") String invstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and shopid = :shopid and companyid = :companyid and voidstatus = :voidstatus and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByShopidCompanyidVoidstatusSearchContain(@Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("voidstatus") int voidstatus,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and shopid = :shopid and companyid = :companyid and invstatus = :invstatus and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByShopidCompanyidInvstatusSearchContain(@Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("invstatus") String invstatus,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and shopid = :shopid and companyid = :companyid and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByShopidCompanyidDateBetweenSearchContain(@Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and shopid = :shopid and voidstatus = :voidstatus and invstatus = :invstatus and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByShopidVoidstatusInvstatusSearchContain(@Param("shopid") String shopid,
                        @Param("voidstatus") int voidstatus, @Param("invstatus") String invstatus,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and shopid = :shopid and voidstatus = :voidstatus and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByShopidVoidstatusDateBetweenSearchContain(
                        @Param("shopid") String shopid, @Param("voidstatus") int voidstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and shopid = :shopid and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByShopidInvstatusDateBetweenSearchContain(@Param("shopid") String shopid,
                        @Param("invstatus") String invstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and companyid = :companyid and voidstatus = :voidstatus and invstatus = :invstatus and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByCompanyidVoidstatusInvstatusSearchContain(
                        @Param("companyid") String companyid, @Param("voidstatus") int voidstatus,
                        @Param("invstatus") String invstatus, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and companyid = :companyid and voidstatus = :voidstatus and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByCompanyidVoidstatusDateBetweenSearchContain(
                        @Param("companyid") String companyid, @Param("voidstatus") int voidstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and companyid = :companyid and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByCompanyidInvstatusDateBetweenSearchContain(
                        @Param("companyid") String companyid, @Param("invstatus") String invstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and voidstatus = :voidstatus and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByVoidstatusInvstatusDateBetweenSearchContain(
                        @Param("voidstatus") int voidstatus, @Param("invstatus") String invstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and shopid = :shopid and companyid = :companyid and voidstatus = :voidstatus and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByUseridShopidCompanyidVoidstatusSearchContain(
                        @Param("userid") String userid, @Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("voidstatus") int voidstatus,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and shopid = :shopid and companyid = :companyid and invstatus = :invstatus and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByUseridShopidCompanyidInvstatusSearchContain(
                        @Param("userid") String userid, @Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("invstatus") String invstatus,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and shopid = :shopid and companyid = :companyid and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByUseridShopidCompanyidDateBetweenSearchContain(
                        @Param("userid") String userid, @Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and shopid = :shopid and voidstatus = :voidstatus and invstatus = :invstatus and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByUseridShopidVoidstatusInvstatusSearchContain(
                        @Param("userid") String userid, @Param("shopid") String shopid,
                        @Param("voidstatus") int voidstatus, @Param("invstatus") String invstatus,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and shopid = :shopid and voidstatus = :voidstatus and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByUseridShopidVoidstatusDateBetweenSearchContain(
                        @Param("userid") String userid, @Param("shopid") String shopid,
                        @Param("voidstatus") int voidstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and shopid = :shopid and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByUseridShopidInvstatusDateBetweenSearchContain(
                        @Param("userid") String userid, @Param("shopid") String shopid,
                        @Param("invstatus") String invstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and companyid = :companyid and voidstatus = :voidstatus and invstatus = :invstatus and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByUseridCompanyidVoidstatusInvstatusSearchContain(
                        @Param("userid") String userid, @Param("companyid") String companyid,
                        @Param("voidstatus") int voidstatus, @Param("invstatus") String invstatus,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and companyid = :companyid and voidstatus = :voidstatus and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByUseridCompanyidVoidstatusDateBetweenSearchContain(
                        @Param("userid") String userid, @Param("companyid") String companyid,
                        @Param("voidstatus") int voidstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and companyid = :companyid and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByUseridCompanyidInvstatusDateBetweenSearchContain(
                        @Param("userid") String userid, @Param("companyid") String companyid,
                        @Param("invstatus") String invstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and voidstatus = :voidstatus and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByUseridVoidstatusInvstatusDateBetweenSearchContain(
                        @Param("userid") String userid, @Param("voidstatus") int voidstatus,
                        @Param("invstatus") String invstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and shopid = :shopid and companyid = :companyid and voidstatus = :voidstatus and invstatus = :invstatus and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByShopidCompanyidVoidstatusInvstatusSearchContain(
                        @Param("shopid") String shopid, @Param("companyid") String companyid,
                        @Param("voidstatus") int voidstatus, @Param("invstatus") String invstatus,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and shopid = :shopid and companyid = :companyid and voidstatus = :voidstatus and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByShopidCompanyidVoidstatusDateBetweenSearchContain(
                        @Param("shopid") String shopid, @Param("companyid") String companyid,
                        @Param("voidstatus") int voidstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and shopid = :shopid and companyid = :companyid and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByShopidCompanyidInvstatusDateBetweenSearchContain(
                        @Param("shopid") String shopid, @Param("companyid") String companyid,
                        @Param("invstatus") String invstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and shopid = :shopid and voidstatus = :voidstatus and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByShopidVoidstatusInvstatusDateBetweenSearchContain(
                        @Param("shopid") String shopid, @Param("voidstatus") int voidstatus,
                        @Param("invstatus") String invstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and companyid = :companyid and voidstatus = :voidstatus and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByCompanyidVoidstatusInvstatusDateBetweenSearchContain(
                        @Param("companyid") String companyid, @Param("voidstatus") int voidstatus,
                        @Param("invstatus") String invstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and shopid = :shopid and companyid = :companyid and voidstatus = :voidstatus and invstatus = :invstatus and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByUseridShopidCompanyidVoidstatusInvstatusSearchContain(
                        @Param("userid") String userid, @Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("voidstatus") int voidstatus,
                        @Param("invstatus") String invstatus, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and shopid = :shopid and companyid = :companyid and voidstatus = :voidstatus and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByUseridShopidCompanyidVoidstatusDateBetweenSearchContain(
                        @Param("userid") String userid, @Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("voidstatus") int voidstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and shopid = :shopid and companyid = :companyid and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByUseridShopidCompanyidInvstatusDateBetweenSearchContain(
                        @Param("userid") String userid, @Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("invstatus") String invstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and shopid = :shopid and voidstatus = :voidstatus and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByUseridShopidVoidstatusInvstatusDateBetweenSearchContain(
                        @Param("userid") String userid, @Param("shopid") String shopid,
                        @Param("voidstatus") int voidstatus, @Param("invstatus") String invstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and companyid = :companyid and voidstatus = :voidstatus and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByUseridCompanyidVoidstatusInvstatusDateBetweenSearchContain(
                        @Param("userid") String userid, @Param("companyid") String companyid,
                        @Param("voidstatus") int voidstatus, @Param("invstatus") String invstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and shopid = :shopid and companyid = :companyid and voidstatus = :voidstatus and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByShopidCompanyidVoidstatusInvstatusDateBetweenSearchContain(
                        @Param("shopid") String shopid, @Param("companyid") String companyid,
                        @Param("voidstatus") int voidstatus, @Param("invstatus") String invstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and shopid = :shopid and companyid = :companyid and voidstatus = :voidstatus and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        Page<InventoryActivity> findInvActivityByUseridShopidCompanyidVoidstatusInvstatusDateBetweenSearchContain(
                        @Param("userid") String userid, @Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("voidstatus") int voidstatus,
                        @Param("invstatus") String invstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid ")
        Page<InventoryActivity> findInvActivityByUserid(@Param("userid") String userid, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and shopid = :shopid ")
        Page<InventoryActivity> findInvActivityByShopid(@Param("shopid") String shopid, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and companyid = :companyid ")
        Page<InventoryActivity> findInvActivityByCompanyid(@Param("companyid") String companyid, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and voidstatus = :voidstatus ")
        Page<InventoryActivity> findInvActivityByVoidstatus(@Param("voidstatus") int voidstatus, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and invstatus = :invstatus ")
        Page<InventoryActivity> findInvActivityByInvstatus(@Param("invstatus") String invstatus, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and (date between :fromdate and :todate) ")
        Page<InventoryActivity> findInvActivityByDateBetween(@Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityBySearch(@Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and shopid = :shopid ")
        Page<InventoryActivity> findInvActivityByUseridShopid(@Param("userid") String userid,
                        @Param("shopid") String shopid, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and companyid = :companyid ")
        Page<InventoryActivity> findInvActivityByUseridCompanyid(@Param("userid") String userid,
                        @Param("companyid") String companyid, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and voidstatus = :voidstatus ")
        Page<InventoryActivity> findInvActivityByUseridVoidstatus(@Param("userid") String userid,
                        @Param("voidstatus") int voidstatus, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and invstatus = :invstatus ")
        Page<InventoryActivity> findInvActivityByUseridInvstatus(@Param("userid") String userid,
                        @Param("invstatus") String invstatus, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and (date between :fromdate and :todate) ")
        Page<InventoryActivity> findInvActivityByUseridDateBetween(@Param("userid") String userid,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByUseridSearch(@Param("userid") String userid,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and shopid = :shopid and companyid = :companyid ")
        Page<InventoryActivity> findInvActivityByShopidCompanyid(@Param("shopid") String shopid,
                        @Param("companyid") String companyid, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and shopid = :shopid and voidstatus = :voidstatus ")
        Page<InventoryActivity> findInvActivityByShopidVoidstatus(@Param("shopid") String shopid,
                        @Param("voidstatus") int voidstatus, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and shopid = :shopid and invstatus = :invstatus ")
        Page<InventoryActivity> findInvActivityByShopidInvstatus(@Param("shopid") String shopid,
                        @Param("invstatus") String invstatus, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and shopid = :shopid and (date between :fromdate and :todate) ")
        Page<InventoryActivity> findInvActivityByShopidDateBetween(@Param("shopid") String shopid,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and shopid = :shopid and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByShopidSearch(@Param("shopid") String shopid,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and companyid = :companyid and voidstatus = :voidstatus ")
        Page<InventoryActivity> findInvActivityByCompanyidVoidstatus(@Param("companyid") String companyid,
                        @Param("voidstatus") int voidstatus, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and companyid = :companyid and invstatus = :invstatus ")
        Page<InventoryActivity> findInvActivityByCompanyidInvstatus(@Param("companyid") String companyid,
                        @Param("invstatus") String invstatus, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and companyid = :companyid and (date between :fromdate and :todate) ")
        Page<InventoryActivity> findInvActivityByCompanyidDateBetween(@Param("companyid") String companyid,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and companyid = :companyid and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByCompanyidSearch(@Param("companyid") String companyid,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and voidstatus = :voidstatus and invstatus = :invstatus ")
        Page<InventoryActivity> findInvActivityByVoidstatusInvstatus(@Param("voidstatus") int voidstatus,
                        @Param("invstatus") String invstatus, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and voidstatus = :voidstatus and (date between :fromdate and :todate) ")
        Page<InventoryActivity> findInvActivityByVoidstatusDateBetween(@Param("voidstatus") int voidstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and voidstatus = :voidstatus and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByVoidstatusSearch(@Param("voidstatus") int voidstatus,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and invstatus = :invstatus and (date between :fromdate and :todate) ")
        Page<InventoryActivity> findInvActivityByInvstatusDateBetween(@Param("invstatus") String invstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and invstatus = :invstatus and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByInvstatusSearch(@Param("invstatus") String invstatus,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByDateBetweenSearch(@Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and shopid = :shopid and companyid = :companyid ")
        Page<InventoryActivity> findInvActivityByUseridShopidCompanyid(@Param("userid") String userid,
                        @Param("shopid") String shopid, @Param("companyid") String companyid, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and shopid = :shopid and voidstatus = :voidstatus ")
        Page<InventoryActivity> findInvActivityByUseridShopidVoidstatus(@Param("userid") String userid,
                        @Param("shopid") String shopid, @Param("voidstatus") int voidstatus, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and shopid = :shopid and invstatus = :invstatus ")
        Page<InventoryActivity> findInvActivityByUseridShopidInvstatus(@Param("userid") String userid,
                        @Param("shopid") String shopid, @Param("invstatus") String invstatus, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and shopid = :shopid and (date between :fromdate and :todate) ")
        Page<InventoryActivity> findInvActivityByUseridShopidDateBetween(@Param("userid") String userid,
                        @Param("shopid") String shopid, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and shopid = :shopid and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByUseridShopidSearch(@Param("userid") String userid,
                        @Param("shopid") String shopid, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and companyid = :companyid and voidstatus = :voidstatus ")
        Page<InventoryActivity> findInvActivityByUseridCompanyidVoidstatus(@Param("userid") String userid,
                        @Param("companyid") String companyid, @Param("voidstatus") int voidstatus, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and companyid = :companyid and invstatus = :invstatus ")
        Page<InventoryActivity> findInvActivityByUseridCompanyidInvstatus(@Param("userid") String userid,
                        @Param("companyid") String companyid, @Param("invstatus") String invstatus, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and companyid = :companyid and (date between :fromdate and :todate) ")
        Page<InventoryActivity> findInvActivityByUseridCompanyidDateBetween(@Param("userid") String userid,
                        @Param("companyid") String companyid, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and companyid = :companyid and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByUseridCompanyidSearch(@Param("userid") String userid,
                        @Param("companyid") String companyid, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and voidstatus = :voidstatus and invstatus = :invstatus ")
        Page<InventoryActivity> findInvActivityByUseridVoidstatusInvstatus(@Param("userid") String userid,
                        @Param("voidstatus") int voidstatus, @Param("invstatus") String invstatus, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and voidstatus = :voidstatus and (date between :fromdate and :todate) ")
        Page<InventoryActivity> findInvActivityByUseridVoidstatusDateBetween(@Param("userid") String userid,
                        @Param("voidstatus") int voidstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and voidstatus = :voidstatus and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByUseridVoidstatusSearch(@Param("userid") String userid,
                        @Param("voidstatus") int voidstatus, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and invstatus = :invstatus and (date between :fromdate and :todate) ")
        Page<InventoryActivity> findInvActivityByUseridInvstatusDateBetween(@Param("userid") String userid,
                        @Param("invstatus") String invstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and invstatus = :invstatus and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByUseridInvstatusSearch(@Param("userid") String userid,
                        @Param("invstatus") String invstatus, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByUseridDateBetweenSearch(@Param("userid") String userid,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and shopid = :shopid and companyid = :companyid and voidstatus = :voidstatus ")
        Page<InventoryActivity> findInvActivityByShopidCompanyidVoidstatus(@Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("voidstatus") int voidstatus, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and shopid = :shopid and companyid = :companyid and invstatus = :invstatus ")
        Page<InventoryActivity> findInvActivityByShopidCompanyidInvstatus(@Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("invstatus") String invstatus, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and shopid = :shopid and companyid = :companyid and (date between :fromdate and :todate) ")
        Page<InventoryActivity> findInvActivityByShopidCompanyidDateBetween(@Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and shopid = :shopid and companyid = :companyid and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByShopidCompanyidSearch(@Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and shopid = :shopid and voidstatus = :voidstatus and invstatus = :invstatus ")
        Page<InventoryActivity> findInvActivityByShopidVoidstatusInvstatus(@Param("shopid") String shopid,
                        @Param("voidstatus") int voidstatus, @Param("invstatus") String invstatus, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and shopid = :shopid and voidstatus = :voidstatus and (date between :fromdate and :todate) ")
        Page<InventoryActivity> findInvActivityByShopidVoidstatusDateBetween(@Param("shopid") String shopid,
                        @Param("voidstatus") int voidstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and shopid = :shopid and voidstatus = :voidstatus and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByShopidVoidstatusSearch(@Param("shopid") String shopid,
                        @Param("voidstatus") int voidstatus, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and shopid = :shopid and invstatus = :invstatus and (date between :fromdate and :todate) ")
        Page<InventoryActivity> findInvActivityByShopidInvstatusDateBetween(@Param("shopid") String shopid,
                        @Param("invstatus") String invstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and shopid = :shopid and invstatus = :invstatus and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByShopidInvstatusSearch(@Param("shopid") String shopid,
                        @Param("invstatus") String invstatus, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and shopid = :shopid and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByShopidDateBetweenSearch(@Param("shopid") String shopid,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and companyid = :companyid and voidstatus = :voidstatus and invstatus = :invstatus ")
        Page<InventoryActivity> findInvActivityByCompanyidVoidstatusInvstatus(@Param("companyid") String companyid,
                        @Param("voidstatus") int voidstatus, @Param("invstatus") String invstatus, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and companyid = :companyid and voidstatus = :voidstatus and (date between :fromdate and :todate) ")
        Page<InventoryActivity> findInvActivityByCompanyidVoidstatusDateBetween(@Param("companyid") String companyid,
                        @Param("voidstatus") int voidstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and companyid = :companyid and voidstatus = :voidstatus and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByCompanyidVoidstatusSearch(@Param("companyid") String companyid,
                        @Param("voidstatus") int voidstatus, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and companyid = :companyid and invstatus = :invstatus and (date between :fromdate and :todate) ")
        Page<InventoryActivity> findInvActivityByCompanyidInvstatusDateBetween(@Param("companyid") String companyid,
                        @Param("invstatus") String invstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and companyid = :companyid and invstatus = :invstatus and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByCompanyidInvstatusSearch(@Param("companyid") String companyid,
                        @Param("invstatus") String invstatus, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and companyid = :companyid and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByCompanyidDateBetweenSearch(@Param("companyid") String companyid,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and voidstatus = :voidstatus and invstatus = :invstatus and (date between :fromdate and :todate) ")
        Page<InventoryActivity> findInvActivityByVoidstatusInvstatusDateBetween(@Param("voidstatus") int voidstatus,
                        @Param("invstatus") String invstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and voidstatus = :voidstatus and invstatus = :invstatus and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByVoidstatusInvstatusSearch(@Param("voidstatus") int voidstatus,
                        @Param("invstatus") String invstatus, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and voidstatus = :voidstatus and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByVoidstatusDateBetweenSearch(@Param("voidstatus") int voidstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByInvstatusDateBetweenSearch(@Param("invstatus") String invstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and shopid = :shopid and companyid = :companyid and voidstatus = :voidstatus ")
        Page<InventoryActivity> findInvActivityByUseridShopidCompanyidVoidstatus(@Param("userid") String userid,
                        @Param("shopid") String shopid, @Param("companyid") String companyid,
                        @Param("voidstatus") int voidstatus, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and shopid = :shopid and companyid = :companyid and invstatus = :invstatus ")
        Page<InventoryActivity> findInvActivityByUseridShopidCompanyidInvstatus(@Param("userid") String userid,
                        @Param("shopid") String shopid, @Param("companyid") String companyid,
                        @Param("invstatus") String invstatus, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and shopid = :shopid and companyid = :companyid and (date between :fromdate and :todate) ")
        Page<InventoryActivity> findInvActivityByUseridShopidCompanyidDateBetween(@Param("userid") String userid,
                        @Param("shopid") String shopid, @Param("companyid") String companyid,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and shopid = :shopid and companyid = :companyid and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByUseridShopidCompanyidSearch(@Param("userid") String userid,
                        @Param("shopid") String shopid, @Param("companyid") String companyid,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and shopid = :shopid and voidstatus = :voidstatus and invstatus = :invstatus ")
        Page<InventoryActivity> findInvActivityByUseridShopidVoidstatusInvstatus(@Param("userid") String userid,
                        @Param("shopid") String shopid, @Param("voidstatus") int voidstatus,
                        @Param("invstatus") String invstatus, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and shopid = :shopid and voidstatus = :voidstatus and (date between :fromdate and :todate) ")
        Page<InventoryActivity> findInvActivityByUseridShopidVoidstatusDateBetween(@Param("userid") String userid,
                        @Param("shopid") String shopid, @Param("voidstatus") int voidstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and shopid = :shopid and voidstatus = :voidstatus and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByUseridShopidVoidstatusSearch(@Param("userid") String userid,
                        @Param("shopid") String shopid, @Param("voidstatus") int voidstatus,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and shopid = :shopid and invstatus = :invstatus and (date between :fromdate and :todate) ")
        Page<InventoryActivity> findInvActivityByUseridShopidInvstatusDateBetween(@Param("userid") String userid,
                        @Param("shopid") String shopid, @Param("invstatus") String invstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and shopid = :shopid and invstatus = :invstatus and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByUseridShopidInvstatusSearch(@Param("userid") String userid,
                        @Param("shopid") String shopid, @Param("invstatus") String invstatus,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and shopid = :shopid and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByUseridShopidDateBetweenSearch(@Param("userid") String userid,
                        @Param("shopid") String shopid, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and companyid = :companyid and voidstatus = :voidstatus and invstatus = :invstatus ")
        Page<InventoryActivity> findInvActivityByUseridCompanyidVoidstatusInvstatus(@Param("userid") String userid,
                        @Param("companyid") String companyid, @Param("voidstatus") int voidstatus,
                        @Param("invstatus") String invstatus, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and companyid = :companyid and voidstatus = :voidstatus and (date between :fromdate and :todate) ")
        Page<InventoryActivity> findInvActivityByUseridCompanyidVoidstatusDateBetween(@Param("userid") String userid,
                        @Param("companyid") String companyid, @Param("voidstatus") int voidstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and companyid = :companyid and voidstatus = :voidstatus and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByUseridCompanyidVoidstatusSearch(@Param("userid") String userid,
                        @Param("companyid") String companyid, @Param("voidstatus") int voidstatus,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and companyid = :companyid and invstatus = :invstatus and (date between :fromdate and :todate) ")
        Page<InventoryActivity> findInvActivityByUseridCompanyidInvstatusDateBetween(@Param("userid") String userid,
                        @Param("companyid") String companyid, @Param("invstatus") String invstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and companyid = :companyid and invstatus = :invstatus and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByUseridCompanyidInvstatusSearch(@Param("userid") String userid,
                        @Param("companyid") String companyid, @Param("invstatus") String invstatus,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and companyid = :companyid and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByUseridCompanyidDateBetweenSearch(@Param("userid") String userid,
                        @Param("companyid") String companyid, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and voidstatus = :voidstatus and invstatus = :invstatus and (date between :fromdate and :todate) ")
        Page<InventoryActivity> findInvActivityByUseridVoidstatusInvstatusDateBetween(@Param("userid") String userid,
                        @Param("voidstatus") int voidstatus, @Param("invstatus") String invstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and voidstatus = :voidstatus and invstatus = :invstatus and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByUseridVoidstatusInvstatusSearch(@Param("userid") String userid,
                        @Param("voidstatus") int voidstatus, @Param("invstatus") String invstatus,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and voidstatus = :voidstatus and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByUseridVoidstatusDateBetweenSearch(@Param("userid") String userid,
                        @Param("voidstatus") int voidstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByUseridInvstatusDateBetweenSearch(@Param("userid") String userid,
                        @Param("invstatus") String invstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and shopid = :shopid and companyid = :companyid and voidstatus = :voidstatus and invstatus = :invstatus ")
        Page<InventoryActivity> findInvActivityByShopidCompanyidVoidstatusInvstatus(@Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("voidstatus") int voidstatus,
                        @Param("invstatus") String invstatus, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and shopid = :shopid and companyid = :companyid and voidstatus = :voidstatus and (date between :fromdate and :todate) ")
        Page<InventoryActivity> findInvActivityByShopidCompanyidVoidstatusDateBetween(@Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("voidstatus") int voidstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and shopid = :shopid and companyid = :companyid and voidstatus = :voidstatus and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByShopidCompanyidVoidstatusSearch(@Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("voidstatus") int voidstatus,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and shopid = :shopid and companyid = :companyid and invstatus = :invstatus and (date between :fromdate and :todate) ")
        Page<InventoryActivity> findInvActivityByShopidCompanyidInvstatusDateBetween(@Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("invstatus") String invstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and shopid = :shopid and companyid = :companyid and invstatus = :invstatus and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByShopidCompanyidInvstatusSearch(@Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("invstatus") String invstatus,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and shopid = :shopid and companyid = :companyid and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByShopidCompanyidDateBetweenSearch(@Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and shopid = :shopid and voidstatus = :voidstatus and invstatus = :invstatus and (date between :fromdate and :todate) ")
        Page<InventoryActivity> findInvActivityByShopidVoidstatusInvstatusDateBetween(@Param("shopid") String shopid,
                        @Param("voidstatus") int voidstatus, @Param("invstatus") String invstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and shopid = :shopid and voidstatus = :voidstatus and invstatus = :invstatus and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByShopidVoidstatusInvstatusSearch(@Param("shopid") String shopid,
                        @Param("voidstatus") int voidstatus, @Param("invstatus") String invstatus,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and shopid = :shopid and voidstatus = :voidstatus and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByShopidVoidstatusDateBetweenSearch(@Param("shopid") String shopid,
                        @Param("voidstatus") int voidstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and shopid = :shopid and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByShopidInvstatusDateBetweenSearch(@Param("shopid") String shopid,
                        @Param("invstatus") String invstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and companyid = :companyid and voidstatus = :voidstatus and invstatus = :invstatus and (date between :fromdate and :todate) ")
        Page<InventoryActivity> findInvActivityByCompanyidVoidstatusInvstatusDateBetween(
                        @Param("companyid") String companyid, @Param("voidstatus") int voidstatus,
                        @Param("invstatus") String invstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and companyid = :companyid and voidstatus = :voidstatus and invstatus = :invstatus and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByCompanyidVoidstatusInvstatusSearch(
                        @Param("companyid") String companyid, @Param("voidstatus") int voidstatus,
                        @Param("invstatus") String invstatus, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and companyid = :companyid and voidstatus = :voidstatus and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByCompanyidVoidstatusDateBetweenSearch(
                        @Param("companyid") String companyid, @Param("voidstatus") int voidstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and companyid = :companyid and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByCompanyidInvstatusDateBetweenSearch(
                        @Param("companyid") String companyid, @Param("invstatus") String invstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and voidstatus = :voidstatus and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByVoidstatusInvstatusDateBetweenSearch(
                        @Param("voidstatus") int voidstatus, @Param("invstatus") String invstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and shopid = :shopid and companyid = :companyid and voidstatus = :voidstatus and invstatus = :invstatus ")
        Page<InventoryActivity> findInvActivityByUseridShopidCompanyidVoidstatusInvstatus(
                        @Param("userid") String userid, @Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("voidstatus") int voidstatus,
                        @Param("invstatus") String invstatus, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and shopid = :shopid and companyid = :companyid and voidstatus = :voidstatus and (date between :fromdate and :todate) ")
        Page<InventoryActivity> findInvActivityByUseridShopidCompanyidVoidstatusDateBetween(
                        @Param("userid") String userid, @Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("voidstatus") int voidstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and shopid = :shopid and companyid = :companyid and voidstatus = :voidstatus and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByUseridShopidCompanyidVoidstatusSearch(@Param("userid") String userid,
                        @Param("shopid") String shopid, @Param("companyid") String companyid,
                        @Param("voidstatus") int voidstatus, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and shopid = :shopid and companyid = :companyid and invstatus = :invstatus and (date between :fromdate and :todate) ")
        Page<InventoryActivity> findInvActivityByUseridShopidCompanyidInvstatusDateBetween(
                        @Param("userid") String userid, @Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("invstatus") String invstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and shopid = :shopid and companyid = :companyid and invstatus = :invstatus and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByUseridShopidCompanyidInvstatusSearch(@Param("userid") String userid,
                        @Param("shopid") String shopid, @Param("companyid") String companyid,
                        @Param("invstatus") String invstatus, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and shopid = :shopid and companyid = :companyid and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByUseridShopidCompanyidDateBetweenSearch(@Param("userid") String userid,
                        @Param("shopid") String shopid, @Param("companyid") String companyid,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and shopid = :shopid and voidstatus = :voidstatus and invstatus = :invstatus and (date between :fromdate and :todate) ")
        Page<InventoryActivity> findInvActivityByUseridShopidVoidstatusInvstatusDateBetween(
                        @Param("userid") String userid, @Param("shopid") String shopid,
                        @Param("voidstatus") int voidstatus, @Param("invstatus") String invstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and shopid = :shopid and voidstatus = :voidstatus and invstatus = :invstatus and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByUseridShopidVoidstatusInvstatusSearch(@Param("userid") String userid,
                        @Param("shopid") String shopid, @Param("voidstatus") int voidstatus,
                        @Param("invstatus") String invstatus, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and shopid = :shopid and voidstatus = :voidstatus and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByUseridShopidVoidstatusDateBetweenSearch(@Param("userid") String userid,
                        @Param("shopid") String shopid, @Param("voidstatus") int voidstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and shopid = :shopid and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByUseridShopidInvstatusDateBetweenSearch(@Param("userid") String userid,
                        @Param("shopid") String shopid, @Param("invstatus") String invstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and companyid = :companyid and voidstatus = :voidstatus and invstatus = :invstatus and (date between :fromdate and :todate) ")
        Page<InventoryActivity> findInvActivityByUseridCompanyidVoidstatusInvstatusDateBetween(
                        @Param("userid") String userid, @Param("companyid") String companyid,
                        @Param("voidstatus") int voidstatus, @Param("invstatus") String invstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and companyid = :companyid and voidstatus = :voidstatus and invstatus = :invstatus and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByUseridCompanyidVoidstatusInvstatusSearch(
                        @Param("userid") String userid, @Param("companyid") String companyid,
                        @Param("voidstatus") int voidstatus, @Param("invstatus") String invstatus,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and companyid = :companyid and voidstatus = :voidstatus and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByUseridCompanyidVoidstatusDateBetweenSearch(
                        @Param("userid") String userid, @Param("companyid") String companyid,
                        @Param("voidstatus") int voidstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and companyid = :companyid and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByUseridCompanyidInvstatusDateBetweenSearch(
                        @Param("userid") String userid, @Param("companyid") String companyid,
                        @Param("invstatus") String invstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and voidstatus = :voidstatus and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByUseridVoidstatusInvstatusDateBetweenSearch(
                        @Param("userid") String userid, @Param("voidstatus") int voidstatus,
                        @Param("invstatus") String invstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and shopid = :shopid and companyid = :companyid and voidstatus = :voidstatus and invstatus = :invstatus and (date between :fromdate and :todate) ")
        Page<InventoryActivity> findInvActivityByShopidCompanyidVoidstatusInvstatusDateBetween(
                        @Param("shopid") String shopid, @Param("companyid") String companyid,
                        @Param("voidstatus") int voidstatus, @Param("invstatus") String invstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and shopid = :shopid and companyid = :companyid and voidstatus = :voidstatus and invstatus = :invstatus and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByShopidCompanyidVoidstatusInvstatusSearch(
                        @Param("shopid") String shopid, @Param("companyid") String companyid,
                        @Param("voidstatus") int voidstatus, @Param("invstatus") String invstatus,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and shopid = :shopid and companyid = :companyid and voidstatus = :voidstatus and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByShopidCompanyidVoidstatusDateBetweenSearch(
                        @Param("shopid") String shopid, @Param("companyid") String companyid,
                        @Param("voidstatus") int voidstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and shopid = :shopid and companyid = :companyid and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByShopidCompanyidInvstatusDateBetweenSearch(
                        @Param("shopid") String shopid, @Param("companyid") String companyid,
                        @Param("invstatus") String invstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and shopid = :shopid and voidstatus = :voidstatus and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByShopidVoidstatusInvstatusDateBetweenSearch(
                        @Param("shopid") String shopid, @Param("voidstatus") int voidstatus,
                        @Param("invstatus") String invstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and companyid = :companyid and voidstatus = :voidstatus and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByCompanyidVoidstatusInvstatusDateBetweenSearch(
                        @Param("companyid") String companyid, @Param("voidstatus") int voidstatus,
                        @Param("invstatus") String invstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and shopid = :shopid and companyid = :companyid and voidstatus = :voidstatus and invstatus = :invstatus and (date between :fromdate and :todate) ")
        Page<InventoryActivity> findInvActivityByUseridShopidCompanyidVoidstatusInvstatusDateBetween(
                        @Param("userid") String userid, @Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("voidstatus") int voidstatus,
                        @Param("invstatus") String invstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and shopid = :shopid and companyid = :companyid and voidstatus = :voidstatus and invstatus = :invstatus and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByUseridShopidCompanyidVoidstatusInvstatusSearch(
                        @Param("userid") String userid, @Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("voidstatus") int voidstatus,
                        @Param("invstatus") String invstatus, @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and shopid = :shopid and companyid = :companyid and voidstatus = :voidstatus and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByUseridShopidCompanyidVoidstatusDateBetweenSearch(
                        @Param("userid") String userid, @Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("voidstatus") int voidstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and shopid = :shopid and companyid = :companyid and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByUseridShopidCompanyidInvstatusDateBetweenSearch(
                        @Param("userid") String userid, @Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("invstatus") String invstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and shopid = :shopid and voidstatus = :voidstatus and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByUseridShopidVoidstatusInvstatusDateBetweenSearch(
                        @Param("userid") String userid, @Param("shopid") String shopid,
                        @Param("voidstatus") int voidstatus, @Param("invstatus") String invstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and companyid = :companyid and voidstatus = :voidstatus and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByUseridCompanyidVoidstatusInvstatusDateBetweenSearch(
                        @Param("userid") String userid, @Param("companyid") String companyid,
                        @Param("voidstatus") int voidstatus, @Param("invstatus") String invstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and shopid = :shopid and companyid = :companyid and voidstatus = :voidstatus and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByShopidCompanyidVoidstatusInvstatusDateBetweenSearch(
                        @Param("shopid") String shopid, @Param("companyid") String companyid,
                        @Param("voidstatus") int voidstatus, @Param("invstatus") String invstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search, Pageable pageable);

        @Query("select ia from InventoryActivity ia where status = 1 and userid = :userid and shopid = :shopid and companyid = :companyid and voidstatus = :voidstatus and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        Page<InventoryActivity> findInvActivityByUseridShopidCompanyidVoidstatusInvstatusDateBetweenSearch(
                        @Param("userid") String userid, @Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("voidstatus") int voidstatus,
                        @Param("invstatus") String invstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search, Pageable pageable);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsBySearchContain(@Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridSearchContain(@Param("userid") String userid,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and shopid = :shopid and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByShopidSearchContain(@Param("shopid") String shopid,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and companyid = :companyid and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByCompanyidSearchContain(@Param("companyid") String companyid,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and voidstatus = :voidstatus and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByVoidstatusSearchContain(@Param("voidstatus") int voidstatus,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and invstatus = :invstatus and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByInvstatusSearchContain(@Param("invstatus") String invstatus,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByDateBetweenSearchContain(
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and shopid = :shopid and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridShopidSearchContain(@Param("userid") String userid,
                        @Param("shopid") String shopid, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and companyid = :companyid and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridCompanyidSearchContain(@Param("userid") String userid,
                        @Param("companyid") String companyid, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and voidstatus = :voidstatus and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridVoidstatusSearchContain(@Param("userid") String userid,
                        @Param("voidstatus") int voidstatus, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and invstatus = :invstatus and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridInvstatusSearchContain(@Param("userid") String userid,
                        @Param("invstatus") String invstatus, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridDateBetweenSearchContain(@Param("userid") String userid,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and shopid = :shopid and companyid = :companyid and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByShopidCompanyidSearchContain(@Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and shopid = :shopid and voidstatus = :voidstatus and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByShopidVoidstatusSearchContain(@Param("shopid") String shopid,
                        @Param("voidstatus") int voidstatus, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and shopid = :shopid and invstatus = :invstatus and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByShopidInvstatusSearchContain(@Param("shopid") String shopid,
                        @Param("invstatus") String invstatus, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and shopid = :shopid and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByShopidDateBetweenSearchContain(@Param("shopid") String shopid,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and companyid = :companyid and voidstatus = :voidstatus and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByCompanyidVoidstatusSearchContain(
                        @Param("companyid") String companyid, @Param("voidstatus") int voidstatus,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and companyid = :companyid and invstatus = :invstatus and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByCompanyidInvstatusSearchContain(
                        @Param("companyid") String companyid, @Param("invstatus") String invstatus,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and companyid = :companyid and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByCompanyidDateBetweenSearchContain(
                        @Param("companyid") String companyid, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and voidstatus = :voidstatus and invstatus = :invstatus and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByVoidstatusInvstatusSearchContain(
                        @Param("voidstatus") int voidstatus, @Param("invstatus") String invstatus,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and voidstatus = :voidstatus and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByVoidstatusDateBetweenSearchContain(
                        @Param("voidstatus") int voidstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByInvstatusDateBetweenSearchContain(
                        @Param("invstatus") String invstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and shopid = :shopid and companyid = :companyid and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridShopidCompanyidSearchContain(@Param("userid") String userid,
                        @Param("shopid") String shopid, @Param("companyid") String companyid,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and shopid = :shopid and voidstatus = :voidstatus and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridShopidVoidstatusSearchContain(
                        @Param("userid") String userid, @Param("shopid") String shopid,
                        @Param("voidstatus") int voidstatus, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and shopid = :shopid and invstatus = :invstatus and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridShopidInvstatusSearchContain(@Param("userid") String userid,
                        @Param("shopid") String shopid, @Param("invstatus") String invstatus,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and shopid = :shopid and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridShopidDateBetweenSearchContain(
                        @Param("userid") String userid, @Param("shopid") String shopid,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and companyid = :companyid and voidstatus = :voidstatus and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridCompanyidVoidstatusSearchContain(
                        @Param("userid") String userid, @Param("companyid") String companyid,
                        @Param("voidstatus") int voidstatus, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and companyid = :companyid and invstatus = :invstatus and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridCompanyidInvstatusSearchContain(
                        @Param("userid") String userid, @Param("companyid") String companyid,
                        @Param("invstatus") String invstatus, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and companyid = :companyid and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridCompanyidDateBetweenSearchContain(
                        @Param("userid") String userid, @Param("companyid") String companyid,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and voidstatus = :voidstatus and invstatus = :invstatus and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridVoidstatusInvstatusSearchContain(
                        @Param("userid") String userid, @Param("voidstatus") int voidstatus,
                        @Param("invstatus") String invstatus, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and voidstatus = :voidstatus and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridVoidstatusDateBetweenSearchContain(
                        @Param("userid") String userid, @Param("voidstatus") int voidstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridInvstatusDateBetweenSearchContain(
                        @Param("userid") String userid, @Param("invstatus") String invstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and shopid = :shopid and companyid = :companyid and voidstatus = :voidstatus and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByShopidCompanyidVoidstatusSearchContain(
                        @Param("shopid") String shopid, @Param("companyid") String companyid,
                        @Param("voidstatus") int voidstatus, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and shopid = :shopid and companyid = :companyid and invstatus = :invstatus and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByShopidCompanyidInvstatusSearchContain(
                        @Param("shopid") String shopid, @Param("companyid") String companyid,
                        @Param("invstatus") String invstatus, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and shopid = :shopid and companyid = :companyid and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByShopidCompanyidDateBetweenSearchContain(
                        @Param("shopid") String shopid, @Param("companyid") String companyid,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and shopid = :shopid and voidstatus = :voidstatus and invstatus = :invstatus and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByShopidVoidstatusInvstatusSearchContain(
                        @Param("shopid") String shopid, @Param("voidstatus") int voidstatus,
                        @Param("invstatus") String invstatus, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and shopid = :shopid and voidstatus = :voidstatus and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByShopidVoidstatusDateBetweenSearchContain(
                        @Param("shopid") String shopid, @Param("voidstatus") int voidstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and shopid = :shopid and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByShopidInvstatusDateBetweenSearchContain(
                        @Param("shopid") String shopid, @Param("invstatus") String invstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and companyid = :companyid and voidstatus = :voidstatus and invstatus = :invstatus and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByCompanyidVoidstatusInvstatusSearchContain(
                        @Param("companyid") String companyid, @Param("voidstatus") int voidstatus,
                        @Param("invstatus") String invstatus, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and companyid = :companyid and voidstatus = :voidstatus and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByCompanyidVoidstatusDateBetweenSearchContain(
                        @Param("companyid") String companyid, @Param("voidstatus") int voidstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and companyid = :companyid and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByCompanyidInvstatusDateBetweenSearchContain(
                        @Param("companyid") String companyid, @Param("invstatus") String invstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and voidstatus = :voidstatus and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByVoidstatusInvstatusDateBetweenSearchContain(
                        @Param("voidstatus") int voidstatus, @Param("invstatus") String invstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and shopid = :shopid and companyid = :companyid and voidstatus = :voidstatus and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridShopidCompanyidVoidstatusSearchContain(
                        @Param("userid") String userid, @Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("voidstatus") int voidstatus,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and shopid = :shopid and companyid = :companyid and invstatus = :invstatus and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridShopidCompanyidInvstatusSearchContain(
                        @Param("userid") String userid, @Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("invstatus") String invstatus,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and shopid = :shopid and companyid = :companyid and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridShopidCompanyidDateBetweenSearchContain(
                        @Param("userid") String userid, @Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and shopid = :shopid and voidstatus = :voidstatus and invstatus = :invstatus and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridShopidVoidstatusInvstatusSearchContain(
                        @Param("userid") String userid, @Param("shopid") String shopid,
                        @Param("voidstatus") int voidstatus, @Param("invstatus") String invstatus,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and shopid = :shopid and voidstatus = :voidstatus and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridShopidVoidstatusDateBetweenSearchContain(
                        @Param("userid") String userid, @Param("shopid") String shopid,
                        @Param("voidstatus") int voidstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and shopid = :shopid and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridShopidInvstatusDateBetweenSearchContain(
                        @Param("userid") String userid, @Param("shopid") String shopid,
                        @Param("invstatus") String invstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and companyid = :companyid and voidstatus = :voidstatus and invstatus = :invstatus and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridCompanyidVoidstatusInvstatusSearchContain(
                        @Param("userid") String userid, @Param("companyid") String companyid,
                        @Param("voidstatus") int voidstatus, @Param("invstatus") String invstatus,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and companyid = :companyid and voidstatus = :voidstatus and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridCompanyidVoidstatusDateBetweenSearchContain(
                        @Param("userid") String userid, @Param("companyid") String companyid,
                        @Param("voidstatus") int voidstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and companyid = :companyid and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridCompanyidInvstatusDateBetweenSearchContain(
                        @Param("userid") String userid, @Param("companyid") String companyid,
                        @Param("invstatus") String invstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and voidstatus = :voidstatus and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridVoidstatusInvstatusDateBetweenSearchContain(
                        @Param("userid") String userid, @Param("voidstatus") int voidstatus,
                        @Param("invstatus") String invstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and shopid = :shopid and companyid = :companyid and voidstatus = :voidstatus and invstatus = :invstatus and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByShopidCompanyidVoidstatusInvstatusSearchContain(
                        @Param("shopid") String shopid, @Param("companyid") String companyid,
                        @Param("voidstatus") int voidstatus, @Param("invstatus") String invstatus,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and shopid = :shopid and companyid = :companyid and voidstatus = :voidstatus and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByShopidCompanyidVoidstatusDateBetweenSearchContain(
                        @Param("shopid") String shopid, @Param("companyid") String companyid,
                        @Param("voidstatus") int voidstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and shopid = :shopid and companyid = :companyid and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByShopidCompanyidInvstatusDateBetweenSearchContain(
                        @Param("shopid") String shopid, @Param("companyid") String companyid,
                        @Param("invstatus") String invstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and shopid = :shopid and voidstatus = :voidstatus and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByShopidVoidstatusInvstatusDateBetweenSearchContain(
                        @Param("shopid") String shopid, @Param("voidstatus") int voidstatus,
                        @Param("invstatus") String invstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and companyid = :companyid and voidstatus = :voidstatus and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByCompanyidVoidstatusInvstatusDateBetweenSearchContain(
                        @Param("companyid") String companyid, @Param("voidstatus") int voidstatus,
                        @Param("invstatus") String invstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and shopid = :shopid and companyid = :companyid and voidstatus = :voidstatus and invstatus = :invstatus and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridShopidCompanyidVoidstatusInvstatusSearchContain(
                        @Param("userid") String userid, @Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("voidstatus") int voidstatus,
                        @Param("invstatus") String invstatus, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and shopid = :shopid and companyid = :companyid and voidstatus = :voidstatus and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridShopidCompanyidVoidstatusDateBetweenSearchContain(
                        @Param("userid") String userid, @Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("voidstatus") int voidstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and shopid = :shopid and companyid = :companyid and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridShopidCompanyidInvstatusDateBetweenSearchContain(
                        @Param("userid") String userid, @Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("invstatus") String invstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and shopid = :shopid and voidstatus = :voidstatus and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridShopidVoidstatusInvstatusDateBetweenSearchContain(
                        @Param("userid") String userid, @Param("shopid") String shopid,
                        @Param("voidstatus") int voidstatus, @Param("invstatus") String invstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and companyid = :companyid and voidstatus = :voidstatus and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridCompanyidVoidstatusInvstatusDateBetweenSearchContain(
                        @Param("userid") String userid, @Param("companyid") String companyid,
                        @Param("voidstatus") int voidstatus, @Param("invstatus") String invstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and shopid = :shopid and companyid = :companyid and voidstatus = :voidstatus and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByShopidCompanyidVoidstatusInvstatusDateBetweenSearchContain(
                        @Param("shopid") String shopid, @Param("companyid") String companyid,
                        @Param("voidstatus") int voidstatus, @Param("invstatus") String invstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and shopid = :shopid and companyid = :companyid and voidstatus = :voidstatus and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref like %:search% or label like %:search% or itemcode like %:search% or vouchercode like %:search% or customername like %:search%) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridShopidCompanyidVoidstatusInvstatusDateBetweenSearchContain(
                        @Param("userid") String userid, @Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("voidstatus") int voidstatus,
                        @Param("invstatus") String invstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid ")
        List<IAFilterTotalDto> findInvActivityTotalsByUserid(@Param("userid") String userid);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and shopid = :shopid ")
        List<IAFilterTotalDto> findInvActivityTotalsByShopid(@Param("shopid") String shopid);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and companyid = :companyid ")
        List<IAFilterTotalDto> findInvActivityTotalsByCompanyid(@Param("companyid") String companyid);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and voidstatus = :voidstatus ")
        List<IAFilterTotalDto> findInvActivityTotalsByVoidstatus(@Param("voidstatus") int voidstatus);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and invstatus = :invstatus ")
        List<IAFilterTotalDto> findInvActivityTotalsByInvstatus(@Param("invstatus") String invstatus);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and (date between :fromdate and :todate) ")
        List<IAFilterTotalDto> findInvActivityTotalsByDateBetween(@Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsBySearch(@Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and shopid = :shopid ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridShopid(@Param("userid") String userid,
                        @Param("shopid") String shopid);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and companyid = :companyid ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridCompanyid(@Param("userid") String userid,
                        @Param("companyid") String companyid);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and voidstatus = :voidstatus ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridVoidstatus(@Param("userid") String userid,
                        @Param("voidstatus") int voidstatus);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and invstatus = :invstatus ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridInvstatus(@Param("userid") String userid,
                        @Param("invstatus") String invstatus);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and (date between :fromdate and :todate) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridDateBetween(@Param("userid") String userid,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridSearch(@Param("userid") String userid,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and shopid = :shopid and companyid = :companyid ")
        List<IAFilterTotalDto> findInvActivityTotalsByShopidCompanyid(@Param("shopid") String shopid,
                        @Param("companyid") String companyid);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and shopid = :shopid and voidstatus = :voidstatus ")
        List<IAFilterTotalDto> findInvActivityTotalsByShopidVoidstatus(@Param("shopid") String shopid,
                        @Param("voidstatus") int voidstatus);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and shopid = :shopid and invstatus = :invstatus ")
        List<IAFilterTotalDto> findInvActivityTotalsByShopidInvstatus(@Param("shopid") String shopid,
                        @Param("invstatus") String invstatus);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and shopid = :shopid and (date between :fromdate and :todate) ")
        List<IAFilterTotalDto> findInvActivityTotalsByShopidDateBetween(@Param("shopid") String shopid,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and shopid = :shopid and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByShopidSearch(@Param("shopid") String shopid,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and companyid = :companyid and voidstatus = :voidstatus ")
        List<IAFilterTotalDto> findInvActivityTotalsByCompanyidVoidstatus(@Param("companyid") String companyid,
                        @Param("voidstatus") int voidstatus);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and companyid = :companyid and invstatus = :invstatus ")
        List<IAFilterTotalDto> findInvActivityTotalsByCompanyidInvstatus(@Param("companyid") String companyid,
                        @Param("invstatus") String invstatus);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and companyid = :companyid and (date between :fromdate and :todate) ")
        List<IAFilterTotalDto> findInvActivityTotalsByCompanyidDateBetween(@Param("companyid") String companyid,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and companyid = :companyid and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByCompanyidSearch(@Param("companyid") String companyid,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and voidstatus = :voidstatus and invstatus = :invstatus ")
        List<IAFilterTotalDto> findInvActivityTotalsByVoidstatusInvstatus(@Param("voidstatus") int voidstatus,
                        @Param("invstatus") String invstatus);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and voidstatus = :voidstatus and (date between :fromdate and :todate) ")
        List<IAFilterTotalDto> findInvActivityTotalsByVoidstatusDateBetween(@Param("voidstatus") int voidstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and voidstatus = :voidstatus and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByVoidstatusSearch(@Param("voidstatus") int voidstatus,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and invstatus = :invstatus and (date between :fromdate and :todate) ")
        List<IAFilterTotalDto> findInvActivityTotalsByInvstatusDateBetween(@Param("invstatus") String invstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and invstatus = :invstatus and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByInvstatusSearch(@Param("invstatus") String invstatus,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByDateBetweenSearch(@Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and shopid = :shopid and companyid = :companyid ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridShopidCompanyid(@Param("userid") String userid,
                        @Param("shopid") String shopid, @Param("companyid") String companyid);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and shopid = :shopid and voidstatus = :voidstatus ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridShopidVoidstatus(@Param("userid") String userid,
                        @Param("shopid") String shopid, @Param("voidstatus") int voidstatus);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and shopid = :shopid and invstatus = :invstatus ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridShopidInvstatus(@Param("userid") String userid,
                        @Param("shopid") String shopid, @Param("invstatus") String invstatus);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and shopid = :shopid and (date between :fromdate and :todate) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridShopidDateBetween(@Param("userid") String userid,
                        @Param("shopid") String shopid, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and shopid = :shopid and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridShopidSearch(@Param("userid") String userid,
                        @Param("shopid") String shopid, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and companyid = :companyid and voidstatus = :voidstatus ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridCompanyidVoidstatus(@Param("userid") String userid,
                        @Param("companyid") String companyid, @Param("voidstatus") int voidstatus);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and companyid = :companyid and invstatus = :invstatus ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridCompanyidInvstatus(@Param("userid") String userid,
                        @Param("companyid") String companyid, @Param("invstatus") String invstatus);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and companyid = :companyid and (date between :fromdate and :todate) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridCompanyidDateBetween(@Param("userid") String userid,
                        @Param("companyid") String companyid, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and companyid = :companyid and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridCompanyidSearch(@Param("userid") String userid,
                        @Param("companyid") String companyid, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and voidstatus = :voidstatus and invstatus = :invstatus ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridVoidstatusInvstatus(@Param("userid") String userid,
                        @Param("voidstatus") int voidstatus, @Param("invstatus") String invstatus);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and voidstatus = :voidstatus and (date between :fromdate and :todate) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridVoidstatusDateBetween(@Param("userid") String userid,
                        @Param("voidstatus") int voidstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and voidstatus = :voidstatus and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridVoidstatusSearch(@Param("userid") String userid,
                        @Param("voidstatus") int voidstatus, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and invstatus = :invstatus and (date between :fromdate and :todate) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridInvstatusDateBetween(@Param("userid") String userid,
                        @Param("invstatus") String invstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and invstatus = :invstatus and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridInvstatusSearch(@Param("userid") String userid,
                        @Param("invstatus") String invstatus, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridDateBetweenSearch(@Param("userid") String userid,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and shopid = :shopid and companyid = :companyid and voidstatus = :voidstatus ")
        List<IAFilterTotalDto> findInvActivityTotalsByShopidCompanyidVoidstatus(@Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("voidstatus") int voidstatus);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and shopid = :shopid and companyid = :companyid and invstatus = :invstatus ")
        List<IAFilterTotalDto> findInvActivityTotalsByShopidCompanyidInvstatus(@Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("invstatus") String invstatus);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and shopid = :shopid and companyid = :companyid and (date between :fromdate and :todate) ")
        List<IAFilterTotalDto> findInvActivityTotalsByShopidCompanyidDateBetween(@Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and shopid = :shopid and companyid = :companyid and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByShopidCompanyidSearch(@Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and shopid = :shopid and voidstatus = :voidstatus and invstatus = :invstatus ")
        List<IAFilterTotalDto> findInvActivityTotalsByShopidVoidstatusInvstatus(@Param("shopid") String shopid,
                        @Param("voidstatus") int voidstatus, @Param("invstatus") String invstatus);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and shopid = :shopid and voidstatus = :voidstatus and (date between :fromdate and :todate) ")
        List<IAFilterTotalDto> findInvActivityTotalsByShopidVoidstatusDateBetween(@Param("shopid") String shopid,
                        @Param("voidstatus") int voidstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and shopid = :shopid and voidstatus = :voidstatus and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByShopidVoidstatusSearch(@Param("shopid") String shopid,
                        @Param("voidstatus") int voidstatus, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and shopid = :shopid and invstatus = :invstatus and (date between :fromdate and :todate) ")
        List<IAFilterTotalDto> findInvActivityTotalsByShopidInvstatusDateBetween(@Param("shopid") String shopid,
                        @Param("invstatus") String invstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and shopid = :shopid and invstatus = :invstatus and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByShopidInvstatusSearch(@Param("shopid") String shopid,
                        @Param("invstatus") String invstatus, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and shopid = :shopid and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByShopidDateBetweenSearch(@Param("shopid") String shopid,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and companyid = :companyid and voidstatus = :voidstatus and invstatus = :invstatus ")
        List<IAFilterTotalDto> findInvActivityTotalsByCompanyidVoidstatusInvstatus(@Param("companyid") String companyid,
                        @Param("voidstatus") int voidstatus, @Param("invstatus") String invstatus);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and companyid = :companyid and voidstatus = :voidstatus and (date between :fromdate and :todate) ")
        List<IAFilterTotalDto> findInvActivityTotalsByCompanyidVoidstatusDateBetween(
                        @Param("companyid") String companyid, @Param("voidstatus") int voidstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and companyid = :companyid and voidstatus = :voidstatus and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByCompanyidVoidstatusSearch(@Param("companyid") String companyid,
                        @Param("voidstatus") int voidstatus, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and companyid = :companyid and invstatus = :invstatus and (date between :fromdate and :todate) ")
        List<IAFilterTotalDto> findInvActivityTotalsByCompanyidInvstatusDateBetween(
                        @Param("companyid") String companyid, @Param("invstatus") String invstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and companyid = :companyid and invstatus = :invstatus and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByCompanyidInvstatusSearch(@Param("companyid") String companyid,
                        @Param("invstatus") String invstatus, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and companyid = :companyid and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByCompanyidDateBetweenSearch(@Param("companyid") String companyid,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and voidstatus = :voidstatus and invstatus = :invstatus and (date between :fromdate and :todate) ")
        List<IAFilterTotalDto> findInvActivityTotalsByVoidstatusInvstatusDateBetween(
                        @Param("voidstatus") int voidstatus, @Param("invstatus") String invstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and voidstatus = :voidstatus and invstatus = :invstatus and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByVoidstatusInvstatusSearch(@Param("voidstatus") int voidstatus,
                        @Param("invstatus") String invstatus, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and voidstatus = :voidstatus and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByVoidstatusDateBetweenSearch(@Param("voidstatus") int voidstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByInvstatusDateBetweenSearch(@Param("invstatus") String invstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and shopid = :shopid and companyid = :companyid and voidstatus = :voidstatus ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridShopidCompanyidVoidstatus(@Param("userid") String userid,
                        @Param("shopid") String shopid, @Param("companyid") String companyid,
                        @Param("voidstatus") int voidstatus);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and shopid = :shopid and companyid = :companyid and invstatus = :invstatus ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridShopidCompanyidInvstatus(@Param("userid") String userid,
                        @Param("shopid") String shopid, @Param("companyid") String companyid,
                        @Param("invstatus") String invstatus);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and shopid = :shopid and companyid = :companyid and (date between :fromdate and :todate) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridShopidCompanyidDateBetween(@Param("userid") String userid,
                        @Param("shopid") String shopid, @Param("companyid") String companyid,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and shopid = :shopid and companyid = :companyid and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridShopidCompanyidSearch(@Param("userid") String userid,
                        @Param("shopid") String shopid, @Param("companyid") String companyid,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and shopid = :shopid and voidstatus = :voidstatus and invstatus = :invstatus ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridShopidVoidstatusInvstatus(@Param("userid") String userid,
                        @Param("shopid") String shopid, @Param("voidstatus") int voidstatus,
                        @Param("invstatus") String invstatus);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and shopid = :shopid and voidstatus = :voidstatus and (date between :fromdate and :todate) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridShopidVoidstatusDateBetween(@Param("userid") String userid,
                        @Param("shopid") String shopid, @Param("voidstatus") int voidstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and shopid = :shopid and voidstatus = :voidstatus and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridShopidVoidstatusSearch(@Param("userid") String userid,
                        @Param("shopid") String shopid, @Param("voidstatus") int voidstatus,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and shopid = :shopid and invstatus = :invstatus and (date between :fromdate and :todate) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridShopidInvstatusDateBetween(@Param("userid") String userid,
                        @Param("shopid") String shopid, @Param("invstatus") String invstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and shopid = :shopid and invstatus = :invstatus and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridShopidInvstatusSearch(@Param("userid") String userid,
                        @Param("shopid") String shopid, @Param("invstatus") String invstatus,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and shopid = :shopid and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridShopidDateBetweenSearch(@Param("userid") String userid,
                        @Param("shopid") String shopid, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and companyid = :companyid and voidstatus = :voidstatus and invstatus = :invstatus ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridCompanyidVoidstatusInvstatus(@Param("userid") String userid,
                        @Param("companyid") String companyid, @Param("voidstatus") int voidstatus,
                        @Param("invstatus") String invstatus);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and companyid = :companyid and voidstatus = :voidstatus and (date between :fromdate and :todate) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridCompanyidVoidstatusDateBetween(
                        @Param("userid") String userid, @Param("companyid") String companyid,
                        @Param("voidstatus") int voidstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and companyid = :companyid and voidstatus = :voidstatus and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridCompanyidVoidstatusSearch(@Param("userid") String userid,
                        @Param("companyid") String companyid, @Param("voidstatus") int voidstatus,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and companyid = :companyid and invstatus = :invstatus and (date between :fromdate and :todate) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridCompanyidInvstatusDateBetween(
                        @Param("userid") String userid, @Param("companyid") String companyid,
                        @Param("invstatus") String invstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and companyid = :companyid and invstatus = :invstatus and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridCompanyidInvstatusSearch(@Param("userid") String userid,
                        @Param("companyid") String companyid, @Param("invstatus") String invstatus,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and companyid = :companyid and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridCompanyidDateBetweenSearch(@Param("userid") String userid,
                        @Param("companyid") String companyid, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and voidstatus = :voidstatus and invstatus = :invstatus and (date between :fromdate and :todate) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridVoidstatusInvstatusDateBetween(
                        @Param("userid") String userid, @Param("voidstatus") int voidstatus,
                        @Param("invstatus") String invstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and voidstatus = :voidstatus and invstatus = :invstatus and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridVoidstatusInvstatusSearch(@Param("userid") String userid,
                        @Param("voidstatus") int voidstatus, @Param("invstatus") String invstatus,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and voidstatus = :voidstatus and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridVoidstatusDateBetweenSearch(@Param("userid") String userid,
                        @Param("voidstatus") int voidstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridInvstatusDateBetweenSearch(@Param("userid") String userid,
                        @Param("invstatus") String invstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and shopid = :shopid and companyid = :companyid and voidstatus = :voidstatus and invstatus = :invstatus ")
        List<IAFilterTotalDto> findInvActivityTotalsByShopidCompanyidVoidstatusInvstatus(@Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("voidstatus") int voidstatus,
                        @Param("invstatus") String invstatus);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and shopid = :shopid and companyid = :companyid and voidstatus = :voidstatus and (date between :fromdate and :todate) ")
        List<IAFilterTotalDto> findInvActivityTotalsByShopidCompanyidVoidstatusDateBetween(
                        @Param("shopid") String shopid, @Param("companyid") String companyid,
                        @Param("voidstatus") int voidstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and shopid = :shopid and companyid = :companyid and voidstatus = :voidstatus and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByShopidCompanyidVoidstatusSearch(@Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("voidstatus") int voidstatus,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and shopid = :shopid and companyid = :companyid and invstatus = :invstatus and (date between :fromdate and :todate) ")
        List<IAFilterTotalDto> findInvActivityTotalsByShopidCompanyidInvstatusDateBetween(
                        @Param("shopid") String shopid, @Param("companyid") String companyid,
                        @Param("invstatus") String invstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and shopid = :shopid and companyid = :companyid and invstatus = :invstatus and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByShopidCompanyidInvstatusSearch(@Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("invstatus") String invstatus,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and shopid = :shopid and companyid = :companyid and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByShopidCompanyidDateBetweenSearch(@Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and shopid = :shopid and voidstatus = :voidstatus and invstatus = :invstatus and (date between :fromdate and :todate) ")
        List<IAFilterTotalDto> findInvActivityTotalsByShopidVoidstatusInvstatusDateBetween(
                        @Param("shopid") String shopid, @Param("voidstatus") int voidstatus,
                        @Param("invstatus") String invstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and shopid = :shopid and voidstatus = :voidstatus and invstatus = :invstatus and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByShopidVoidstatusInvstatusSearch(@Param("shopid") String shopid,
                        @Param("voidstatus") int voidstatus, @Param("invstatus") String invstatus,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and shopid = :shopid and voidstatus = :voidstatus and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByShopidVoidstatusDateBetweenSearch(@Param("shopid") String shopid,
                        @Param("voidstatus") int voidstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and shopid = :shopid and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByShopidInvstatusDateBetweenSearch(@Param("shopid") String shopid,
                        @Param("invstatus") String invstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and companyid = :companyid and voidstatus = :voidstatus and invstatus = :invstatus and (date between :fromdate and :todate) ")
        List<IAFilterTotalDto> findInvActivityTotalsByCompanyidVoidstatusInvstatusDateBetween(
                        @Param("companyid") String companyid, @Param("voidstatus") int voidstatus,
                        @Param("invstatus") String invstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and companyid = :companyid and voidstatus = :voidstatus and invstatus = :invstatus and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByCompanyidVoidstatusInvstatusSearch(
                        @Param("companyid") String companyid, @Param("voidstatus") int voidstatus,
                        @Param("invstatus") String invstatus, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and companyid = :companyid and voidstatus = :voidstatus and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByCompanyidVoidstatusDateBetweenSearch(
                        @Param("companyid") String companyid, @Param("voidstatus") int voidstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and companyid = :companyid and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByCompanyidInvstatusDateBetweenSearch(
                        @Param("companyid") String companyid, @Param("invstatus") String invstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and voidstatus = :voidstatus and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByVoidstatusInvstatusDateBetweenSearch(
                        @Param("voidstatus") int voidstatus, @Param("invstatus") String invstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and shopid = :shopid and companyid = :companyid and voidstatus = :voidstatus and invstatus = :invstatus ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridShopidCompanyidVoidstatusInvstatus(
                        @Param("userid") String userid, @Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("voidstatus") int voidstatus,
                        @Param("invstatus") String invstatus);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and shopid = :shopid and companyid = :companyid and voidstatus = :voidstatus and (date between :fromdate and :todate) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridShopidCompanyidVoidstatusDateBetween(
                        @Param("userid") String userid, @Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("voidstatus") int voidstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and shopid = :shopid and companyid = :companyid and voidstatus = :voidstatus and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridShopidCompanyidVoidstatusSearch(
                        @Param("userid") String userid, @Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("voidstatus") int voidstatus,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and shopid = :shopid and companyid = :companyid and invstatus = :invstatus and (date between :fromdate and :todate) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridShopidCompanyidInvstatusDateBetween(
                        @Param("userid") String userid, @Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("invstatus") String invstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and shopid = :shopid and companyid = :companyid and invstatus = :invstatus and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridShopidCompanyidInvstatusSearch(
                        @Param("userid") String userid, @Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("invstatus") String invstatus,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and shopid = :shopid and companyid = :companyid and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridShopidCompanyidDateBetweenSearch(
                        @Param("userid") String userid, @Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and shopid = :shopid and voidstatus = :voidstatus and invstatus = :invstatus and (date between :fromdate and :todate) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridShopidVoidstatusInvstatusDateBetween(
                        @Param("userid") String userid, @Param("shopid") String shopid,
                        @Param("voidstatus") int voidstatus, @Param("invstatus") String invstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and shopid = :shopid and voidstatus = :voidstatus and invstatus = :invstatus and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridShopidVoidstatusInvstatusSearch(
                        @Param("userid") String userid, @Param("shopid") String shopid,
                        @Param("voidstatus") int voidstatus, @Param("invstatus") String invstatus,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and shopid = :shopid and voidstatus = :voidstatus and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridShopidVoidstatusDateBetweenSearch(
                        @Param("userid") String userid, @Param("shopid") String shopid,
                        @Param("voidstatus") int voidstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and shopid = :shopid and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridShopidInvstatusDateBetweenSearch(
                        @Param("userid") String userid, @Param("shopid") String shopid,
                        @Param("invstatus") String invstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and companyid = :companyid and voidstatus = :voidstatus and invstatus = :invstatus and (date between :fromdate and :todate) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridCompanyidVoidstatusInvstatusDateBetween(
                        @Param("userid") String userid, @Param("companyid") String companyid,
                        @Param("voidstatus") int voidstatus, @Param("invstatus") String invstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and companyid = :companyid and voidstatus = :voidstatus and invstatus = :invstatus and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridCompanyidVoidstatusInvstatusSearch(
                        @Param("userid") String userid, @Param("companyid") String companyid,
                        @Param("voidstatus") int voidstatus, @Param("invstatus") String invstatus,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and companyid = :companyid and voidstatus = :voidstatus and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridCompanyidVoidstatusDateBetweenSearch(
                        @Param("userid") String userid, @Param("companyid") String companyid,
                        @Param("voidstatus") int voidstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and companyid = :companyid and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridCompanyidInvstatusDateBetweenSearch(
                        @Param("userid") String userid, @Param("companyid") String companyid,
                        @Param("invstatus") String invstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and voidstatus = :voidstatus and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridVoidstatusInvstatusDateBetweenSearch(
                        @Param("userid") String userid, @Param("voidstatus") int voidstatus,
                        @Param("invstatus") String invstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and shopid = :shopid and companyid = :companyid and voidstatus = :voidstatus and invstatus = :invstatus and (date between :fromdate and :todate) ")
        List<IAFilterTotalDto> findInvActivityTotalsByShopidCompanyidVoidstatusInvstatusDateBetween(
                        @Param("shopid") String shopid, @Param("companyid") String companyid,
                        @Param("voidstatus") int voidstatus, @Param("invstatus") String invstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and shopid = :shopid and companyid = :companyid and voidstatus = :voidstatus and invstatus = :invstatus and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByShopidCompanyidVoidstatusInvstatusSearch(
                        @Param("shopid") String shopid, @Param("companyid") String companyid,
                        @Param("voidstatus") int voidstatus, @Param("invstatus") String invstatus,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and shopid = :shopid and companyid = :companyid and voidstatus = :voidstatus and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByShopidCompanyidVoidstatusDateBetweenSearch(
                        @Param("shopid") String shopid, @Param("companyid") String companyid,
                        @Param("voidstatus") int voidstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and shopid = :shopid and companyid = :companyid and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByShopidCompanyidInvstatusDateBetweenSearch(
                        @Param("shopid") String shopid, @Param("companyid") String companyid,
                        @Param("invstatus") String invstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and shopid = :shopid and voidstatus = :voidstatus and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByShopidVoidstatusInvstatusDateBetweenSearch(
                        @Param("shopid") String shopid, @Param("voidstatus") int voidstatus,
                        @Param("invstatus") String invstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and companyid = :companyid and voidstatus = :voidstatus and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByCompanyidVoidstatusInvstatusDateBetweenSearch(
                        @Param("companyid") String companyid, @Param("voidstatus") int voidstatus,
                        @Param("invstatus") String invstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and shopid = :shopid and companyid = :companyid and voidstatus = :voidstatus and invstatus = :invstatus and (date between :fromdate and :todate) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridShopidCompanyidVoidstatusInvstatusDateBetween(
                        @Param("userid") String userid, @Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("voidstatus") int voidstatus,
                        @Param("invstatus") String invstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and shopid = :shopid and companyid = :companyid and voidstatus = :voidstatus and invstatus = :invstatus and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridShopidCompanyidVoidstatusInvstatusSearch(
                        @Param("userid") String userid, @Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("voidstatus") int voidstatus,
                        @Param("invstatus") String invstatus, @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and shopid = :shopid and companyid = :companyid and voidstatus = :voidstatus and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridShopidCompanyidVoidstatusDateBetweenSearch(
                        @Param("userid") String userid, @Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("voidstatus") int voidstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and shopid = :shopid and companyid = :companyid and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridShopidCompanyidInvstatusDateBetweenSearch(
                        @Param("userid") String userid, @Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("invstatus") String invstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and shopid = :shopid and voidstatus = :voidstatus and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridShopidVoidstatusInvstatusDateBetweenSearch(
                        @Param("userid") String userid, @Param("shopid") String shopid,
                        @Param("voidstatus") int voidstatus, @Param("invstatus") String invstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and companyid = :companyid and voidstatus = :voidstatus and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridCompanyidVoidstatusInvstatusDateBetweenSearch(
                        @Param("userid") String userid, @Param("companyid") String companyid,
                        @Param("voidstatus") int voidstatus, @Param("invstatus") String invstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and shopid = :shopid and companyid = :companyid and voidstatus = :voidstatus and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByShopidCompanyidVoidstatusInvstatusDateBetweenSearch(
                        @Param("shopid") String shopid, @Param("companyid") String companyid,
                        @Param("voidstatus") int voidstatus, @Param("invstatus") String invstatus,
                        @Param("fromdate") LocalDateTime fromdate, @Param("todate") LocalDateTime todate,
                        @Param("search") String search);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = :userid and shopid = :shopid and companyid = :companyid and voidstatus = :voidstatus and invstatus = :invstatus and (date between :fromdate and :todate) and (activityref = :search or label = :search or itemcode = :search or vouchercode = :search or customername = :search) ")
        List<IAFilterTotalDto> findInvActivityTotalsByUseridShopidCompanyidVoidstatusInvstatusDateBetweenSearch(
                        @Param("userid") String userid, @Param("shopid") String shopid,
                        @Param("companyid") String companyid, @Param("voidstatus") int voidstatus,
                        @Param("invstatus") String invstatus, @Param("fromdate") LocalDateTime fromdate,
                        @Param("todate") LocalDateTime todate, @Param("search") String search);

        public Optional<InventoryActivity> findByCompanyidAndStatusAndActivityref(String companyid, int status,
                        String activityref);

        public Optional<InventoryActivity> findByStatusAndActivityref(int status, String activityref);

        public Optional<InventoryActivity> findByShopidAndCompanyidAndStatusAndActivityref(String userid,
                        String companyid, int status, String activityref);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.ActivityTotalDto(sum(qty), invstatus, itemref) from InventoryActivity  where companyid = ?1 and itemref = ?2 and voidstatus = 1 and status = 1 group by itemref, invstatus")
        public List<ActivityTotalDto> getTotalsByItemref(String companyid, String itemref);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.ActivityTotalDto(sum(qty), invstatus, itemref) from InventoryActivity  where userid = ?1 and shopid = ?2 and companyid = ?3 and itemref = ?4 and voidstatus = 1 and status = 1 group by itemref, invstatus")
        public List<ActivityTotalDto> getTotalsByItemref(String userid, String shopid, String companyid,
                        String itemref);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.ActivityTotalDto(sum(qty), invstatus, itemref) from InventoryActivity  where shopid = ?1 and companyid = ?2 and itemref = ?3 and voidstatus = 1 and status = 1 group by itemref, invstatus")
        public List<ActivityTotalDto> getTotalsByItemref(String shopid, String companyid, String itemref);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.ActivityTotalDto(sum(qty), invstatus, itemref) from InventoryActivity  where itemref = ?1 and voidstatus = 1 and status = 1 group by itemref, invstatus")
        public List<ActivityTotalDto> getTotalsByItemref(String itemref);

        @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1")
        public List<IAFilterTotalDto> getTotals();

        Page<InventoryActivity> findByStatus(int status, Pageable pageable);
}
