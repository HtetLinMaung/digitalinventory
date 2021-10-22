package com.techhype.digitalinventory.InventoryActivity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IInvActivityRepository extends JpaRepository<InventoryActivity, Long> {
    @Query("select new com.techhype.digitalinventory.InventoryActivity.ActivityTotalDto(sum(qty), invstatus, itemref) from InventoryActivity  where userid = ?1 and companyid = ?2 and itemref = ?3 and voidstatus = 1 and status = 1 group by itemref, invstatus")
    public List<ActivityTotalDto> getTotalsByItemref(String userid, String companyid, String itemref);

    public Optional<InventoryActivity> findByUseridAndCompanyidAndStatusAndActivityref(String userid, String companyid,
            int status, String activityref);

    public Page<InventoryActivity> findByUseridAndCompanyidAndStatusAndVoidstatusAndActivityrefContainingOrUseridAndCompanyidAndStatusAndVoidstatusAndLabelContainingOrUseridAndCompanyidAndStatusAndVoidstatusAndItemcodeContainingOrUseridAndCompanyidAndStatusAndVoidstatusAndVouchercodeContainingOrUseridAndCompanyidAndStatusAndVoidstatusAndCustomernameContainingOrUseridAndCompanyidAndStatusAndVoidstatusAndInvstatusContaining(
            String userid, String companyid, int status, int voidstatus, String activityref, String userid2,
            String companyid2, int status2, int voidstatus2, String label, String userid3, String companyid3,
            int status3, int voidstatus3, String itemcode, String userid4, String companyid4, int status4,
            int voidstatus4, String vouchercode, String userid5, String companyid5, int status5, int voidstatus5,
            String customername, String userid6, String companyid6, int status6, int voidstatus6, String invstatus,
            Pageable pagable);

    @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = ?1 and companyid = ?2 and voidstatus = ?3 and (activityref like %?4% or label like %?5% or itemcode like %?6% or vouchercode like %?7% or customername like %?8% or invstatus like %?9%)")
    public List<IAFilterTotalDto> getTotalsByVoidstatusForSearchContain(String userid, String companyid, int voidstatus,
            String activityref, String label, String itemcode, String vouchercode, String customername,
            String invstatus);

    public Page<InventoryActivity> findByUseridAndCompanyidAndStatusAndVoidstatusAndInvstatusAndActivityrefContainingOrUseridAndCompanyidAndStatusAndVoidstatusAndInvstatusAndLabelContainingOrUseridAndCompanyidAndStatusAndVoidstatusAndInvstatusAndItemcodeContainingOrUseridAndCompanyidAndStatusAndVoidstatusAndInvstatusAndVouchercodeContainingOrUseridAndCompanyidAndStatusAndVoidstatusAndInvstatusAndCustomernameContaining(
            String userid, String companyid, int status, int voidstatus, String invstatus, String activityref,
            String userid2, String companyid2, int status2, int voidstatus2, String invstatus2, String label,
            String userid3, String companyid3, int status3, int voidstatus3, String invstatus3, String itemcode,
            String userid4, String companyid4, int status4, int voidstatus4, String invstatus4, String vouchercode,
            String userid5, String companyid5, int status5, int voidstatus5, String invstatus5, String customername,
            Pageable pagable);

    @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = ?1 and companyid = ?2 and voidstatus = ?3 and invstatus = ?4 and (activityref like %?5% or label like %?6% or itemcode like %?7% or vouchercode like %?8% or customername like %?9%)")
    public List<IAFilterTotalDto> getTotalsByVoidstatusAndInvstatusForSearchContain(String userid, String companyid,
            int voidstatus, String invstatus, String activityref, String label, String itemcode, String vouchercode,
            String customername);

    public Page<InventoryActivity> findByUseridAndCompanyidAndStatusAndVoidstatusAndDateBetweenAndActivityrefContainingOrUseridAndCompanyidAndStatusAndVoidstatusAndDateBetweenAndLabelContainingOrUseridAndCompanyidAndStatusAndVoidstatusAndDateBetweenAndItemcodeContainingOrUseridAndCompanyidAndStatusAndVoidstatusAndDateBetweenAndVouchercodeContainingOrUseridAndCompanyidAndStatusAndVoidstatusAndDateBetweenAndCustomernameContainingOrUseridAndCompanyidAndStatusAndVoidstatusAndDateBetweenAndInvstatusContaining(
            String userid, String companyid, int status, int voidstatus, LocalDateTime fromdate, LocalDateTime todate,
            String activityref, String userid2, String companyid2, int status2, int voidstatus2,
            LocalDateTime fromdate2, LocalDateTime todate2, String label, String userid3, String companyid3,
            int status3, int voidstatus3, LocalDateTime fromdate3, LocalDateTime todate3, String itemcode,
            String userid4, String companyid4, int status4, int voidstatus4, LocalDateTime fromdate4,
            LocalDateTime todate4, String vouchercode, String userid5, String companyid5, int status5, int voidstatus5,
            LocalDateTime fromdate5, LocalDateTime todate5, String customername, String userid6, String companyid6,
            int status6, int voidstatus6, LocalDateTime fromdate6, LocalDateTime todate6, String invstatus,
            Pageable pagable);

    @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = ?1 and companyid = ?2 and voidstatus = ?3 and (date between ?4 and ?5) and (activityref like %?6% or label like %?7% or itemcode like %?8% or vouchercode like %?9% or customername like %?10% or invstatus like %?11%)")
    public List<IAFilterTotalDto> getTotalsByVoidstatusAndDateBetweenForSearchContain(String userid, String companyid,
            int voidstatus, LocalDateTime fromdate, LocalDateTime todate, String activityref, String label,
            String itemcode, String vouchercode, String customername, String invstatus);

    public Page<InventoryActivity> findByUseridAndCompanyidAndStatusAndVoidstatusAndDateBetweenAndInvstatusAndActivityrefContainingOrUseridAndCompanyidAndStatusAndVoidstatusAndDateBetweenAndInvstatusAndLabelContainingOrUseridAndCompanyidAndStatusAndVoidstatusAndDateBetweenAndInvstatusAndItemcodeContainingOrUseridAndCompanyidAndStatusAndVoidstatusAndDateBetweenAndInvstatusAndVouchercodeContainingOrUseridAndCompanyidAndStatusAndVoidstatusAndDateBetweenAndInvstatusAndCustomernameContaining(
            String userid, String companyid, int status, int voidstatus, LocalDateTime fromdate, LocalDateTime todate,
            String invstatus, String activityref, String userid2, String companyid2, int status2, int voidstatus2,
            LocalDateTime fromdate2, LocalDateTime todate2, String invstatus2, String label, String userid3,
            String companyid3, int status3, int voidstatus3, LocalDateTime fromdate3, LocalDateTime todate3,
            String invstatus3, String itemcode, String userid4, String companyid4, int status4, int voidstatus4,
            LocalDateTime fromdate4, LocalDateTime todate4, String invstatus4, String vouchercode, String userid5,
            String companyid5, int status5, int voidstatus5, LocalDateTime fromdate5, LocalDateTime todate5,
            String invstatus5, String customername, Pageable pagable);

    @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = ?1 and companyid = ?2 and voidstatus = ?3 and (date between ?4 and ?5) and invstatus = ?6 and (activityref like %?7% or label like %?8% or itemcode like %?9% or vouchercode like %?10% or customername like %?11%)")
    public List<IAFilterTotalDto> getTotalsByVoidstatusAndDateBetweenAndInvstatusForSearchContain(String userid,
            String companyid, int voidstatus, LocalDateTime fromdate, LocalDateTime todate, String invstatus,
            String activityref, String label, String itemcode, String vouchercode, String customername);

    public Page<InventoryActivity> findByUseridAndCompanyidAndStatusAndVoidstatusAndActivityrefOrUseridAndCompanyidAndStatusAndVoidstatusAndLabelOrUseridAndCompanyidAndStatusAndVoidstatusAndItemcodeOrUseridAndCompanyidAndStatusAndVoidstatusAndVouchercodeOrUseridAndCompanyidAndStatusAndVoidstatusAndCustomernameOrUseridAndCompanyidAndStatusAndVoidstatusAndInvstatus(
            String userid, String companyid, int status, int voidstatus, String activityref, String userid2,
            String companyid2, int status2, int voidstatus2, String label, String userid3, String companyid3,
            int status3, int voidstatus3, String itemcode, String userid4, String companyid4, int status4,
            int voidstatus4, String vouchercode, String userid5, String companyid5, int status5, int voidstatus5,
            String customername, String userid6, String companyid6, int status6, int voidstatus6, String invstatus,
            Pageable pagable);

    @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = ?1 and companyid = ?2 and voidstatus = ?3 and (activityref = ?4 or label = ?5 or itemcode = ?6 or vouchercode = ?7 or customername = ?8 or invstatus = ?9)")
    public List<IAFilterTotalDto> getTotalsByVoidstatusForSearch(String userid, String companyid, int voidstatus,
            String activityref, String label, String itemcode, String vouchercode, String customername,
            String invstatus);

    public Page<InventoryActivity> findByUseridAndCompanyidAndStatusAndVoidstatusAndInvstatusAndActivityrefOrUseridAndCompanyidAndStatusAndVoidstatusAndInvstatusAndLabelOrUseridAndCompanyidAndStatusAndVoidstatusAndInvstatusAndItemcodeOrUseridAndCompanyidAndStatusAndVoidstatusAndInvstatusAndVouchercodeOrUseridAndCompanyidAndStatusAndVoidstatusAndInvstatusAndCustomername(
            String userid, String companyid, int status, int voidstatus, String invstatus, String activityref,
            String userid2, String companyid2, int status2, int voidstatus2, String invstatus2, String label,
            String userid3, String companyid3, int status3, int voidstatus3, String invstatus3, String itemcode,
            String userid4, String companyid4, int status4, int voidstatus4, String invstatus4, String vouchercode,
            String userid5, String companyid5, int status5, int voidstatus5, String invstatus5, String customername,
            Pageable pagable);

    @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = ?1 and companyid = ?2 and voidstatus = ?3 and invstatus = ?4 and (activityref = ?5 or label = ?6 or itemcode = ?7 or vouchercode = ?8 or customername = ?9)")
    public List<IAFilterTotalDto> getTotalsByVoidstatusAndInvstatusForSearch(String userid, String companyid,
            int voidstatus, String invstatus, String activityref, String label, String itemcode, String vouchercode,
            String customername);

    public Page<InventoryActivity> findByUseridAndCompanyidAndStatusAndVoidstatusAndDateBetweenAndActivityrefOrUseridAndCompanyidAndStatusAndVoidstatusAndDateBetweenAndLabelOrUseridAndCompanyidAndStatusAndVoidstatusAndDateBetweenAndItemcodeOrUseridAndCompanyidAndStatusAndVoidstatusAndDateBetweenAndVouchercodeOrUseridAndCompanyidAndStatusAndVoidstatusAndDateBetweenAndCustomernameOrUseridAndCompanyidAndStatusAndVoidstatusAndDateBetweenAndInvstatus(
            String userid, String companyid, int status, int voidstatus, LocalDateTime fromdate, LocalDateTime todate,
            String activityref, String userid2, String companyid2, int status2, int voidstatus2,
            LocalDateTime fromdate2, LocalDateTime todate2, String label, String userid3, String companyid3,
            int status3, int voidstatus3, LocalDateTime fromdate3, LocalDateTime todate3, String itemcode,
            String userid4, String companyid4, int status4, int voidstatus4, LocalDateTime fromdate4,
            LocalDateTime todate4, String vouchercode, String userid5, String companyid5, int status5, int voidstatus5,
            LocalDateTime fromdate5, LocalDateTime todate5, String customername, String userid6, String companyid6,
            int status6, int voidstatus6, LocalDateTime fromdate6, LocalDateTime todate6, String invstatus,
            Pageable pagable);

    @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = ?1 and companyid = ?2 and voidstatus = ?3 and (date between ?4 and ?5) and (activityref = ?6 or label = ?7 or itemcode = ?8 or vouchercode = ?9 or customername = ?10 or invstatus = ?11)")
    public List<IAFilterTotalDto> getTotalsByVoidstatusAndDateBetweenForSearch(String userid, String companyid,
            int voidstatus, LocalDateTime fromdate, LocalDateTime todate, String activityref, String label,
            String itemcode, String vouchercode, String customername, String invstatus);

    public Page<InventoryActivity> findByUseridAndCompanyidAndStatusAndVoidstatusAndDateBetweenAndInvstatusAndActivityrefOrUseridAndCompanyidAndStatusAndVoidstatusAndDateBetweenAndInvstatusAndLabelOrUseridAndCompanyidAndStatusAndVoidstatusAndDateBetweenAndInvstatusAndItemcodeOrUseridAndCompanyidAndStatusAndVoidstatusAndDateBetweenAndInvstatusAndVouchercodeOrUseridAndCompanyidAndStatusAndVoidstatusAndDateBetweenAndInvstatusAndCustomername(
            String userid, String companyid, int status, int voidstatus, LocalDateTime fromdate, LocalDateTime todate,
            String invstatus, String activityref, String userid2, String companyid2, int status2, int voidstatus2,
            LocalDateTime fromdate2, LocalDateTime todate2, String invstatus2, String label, String userid3,
            String companyid3, int status3, int voidstatus3, LocalDateTime fromdate3, LocalDateTime todate3,
            String invstatus3, String itemcode, String userid4, String companyid4, int status4, int voidstatus4,
            LocalDateTime fromdate4, LocalDateTime todate4, String invstatus4, String vouchercode, String userid5,
            String companyid5, int status5, int voidstatus5, LocalDateTime fromdate5, LocalDateTime todate5,
            String invstatus5, String customername, Pageable pagable);

    @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = ?1 and companyid = ?2 and voidstatus = ?3 and (date between ?4 and ?5) and invstatus = ?6 and (activityref = ?7 or label = ?8 or itemcode = ?9 or vouchercode = ?10 or customername = ?11)")
    public List<IAFilterTotalDto> getTotalsByVoidstatusAndDateBetweenAndInvstatusForSearch(String userid,
            String companyid, int voidstatus, LocalDateTime fromdate, LocalDateTime todate, String invstatus,
            String activityref, String label, String itemcode, String vouchercode, String customername);

    public Page<InventoryActivity> findByUseridAndCompanyidAndStatus(String userid, String companyid, int status,
            Pageable pagable);

    @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = ?1 and companyid = ?2")
    public List<IAFilterTotalDto> getTotals(String userid, String companyid);

    public Page<InventoryActivity> findByUseridAndCompanyidAndStatusAndInvstatus(String userid, String companyid,
            int status, String invstatus, Pageable pagable);

    @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = ?1 and companyid = ?2 and invstatus = ?3")
    public List<IAFilterTotalDto> getTotalsByInvstatus(String userid, String companyid, String invstatus);

    public Page<InventoryActivity> findByUseridAndCompanyidAndStatusAndDateBetween(String userid, String companyid,
            int status, LocalDateTime fromdate, LocalDateTime todate, Pageable pagable);

    @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = ?1 and companyid = ?2 and (date between ?3 and ?4)")
    public List<IAFilterTotalDto> getTotalsByDateBetween(String userid, String companyid, LocalDateTime fromdate,
            LocalDateTime todate);

    public Page<InventoryActivity> findByUseridAndCompanyidAndStatusAndDateBetweenAndInvstatus(String userid,
            String companyid, int status, LocalDateTime fromdate, LocalDateTime todate, String invstatus,
            Pageable pagable);

    @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = ?1 and companyid = ?2 and (date between ?3 and ?4) and invstatus = ?5")
    public List<IAFilterTotalDto> getTotalsByDateBetweenAndInvstatus(String userid, String companyid,
            LocalDateTime fromdate, LocalDateTime todate, String invstatus);

    public Page<InventoryActivity> findByUseridAndCompanyidAndStatusAndActivityrefContainingOrUseridAndCompanyidAndStatusAndLabelContainingOrUseridAndCompanyidAndStatusAndItemcodeContainingOrUseridAndCompanyidAndStatusAndVouchercodeContainingOrUseridAndCompanyidAndStatusAndCustomernameContainingOrUseridAndCompanyidAndStatusAndInvstatusContaining(
            String userid, String companyid, int status, String activityref, String userid2, String companyid2,
            int status2, String label, String userid3, String companyid3, int status3, String itemcode, String userid4,
            String companyid4, int status4, String vouchercode, String userid5, String companyid5, int status5,
            String customername, String userid6, String companyid6, int status6, String invstatus, Pageable pagable);

    @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = ?1 and companyid = ?2 and (activityref like %?3% or label like %?4% or itemcode like %?5% or vouchercode like %?6% or customername like %?7% or invstatus like %?8%)")
    public List<IAFilterTotalDto> getTotalsForSearchContain(String userid, String companyid, String activityref,
            String label, String itemcode, String vouchercode, String customername, String invstatus);

    public Page<InventoryActivity> findByUseridAndCompanyidAndStatusAndInvstatusAndActivityrefContainingOrUseridAndCompanyidAndStatusAndInvstatusAndLabelContainingOrUseridAndCompanyidAndStatusAndInvstatusAndItemcodeContainingOrUseridAndCompanyidAndStatusAndInvstatusAndVouchercodeContainingOrUseridAndCompanyidAndStatusAndInvstatusAndCustomernameContaining(
            String userid, String companyid, int status, String invstatus, String activityref, String userid2,
            String companyid2, int status2, String invstatus2, String label, String userid3, String companyid3,
            int status3, String invstatus3, String itemcode, String userid4, String companyid4, int status4,
            String invstatus4, String vouchercode, String userid5, String companyid5, int status5, String invstatus5,
            String customername, Pageable pagable);

    @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = ?1 and companyid = ?2 and invstatus = ?3 and (activityref like %?4% or label like %?5% or itemcode like %?6% or vouchercode like %?7% or customername like %?8%)")
    public List<IAFilterTotalDto> getTotalsByInvstatusForSearchContain(String userid, String companyid,
            String invstatus, String activityref, String label, String itemcode, String vouchercode,
            String customername);

    public Page<InventoryActivity> findByUseridAndCompanyidAndStatusAndDateBetweenAndActivityrefContainingOrUseridAndCompanyidAndStatusAndDateBetweenAndLabelContainingOrUseridAndCompanyidAndStatusAndDateBetweenAndItemcodeContainingOrUseridAndCompanyidAndStatusAndDateBetweenAndVouchercodeContainingOrUseridAndCompanyidAndStatusAndDateBetweenAndCustomernameContainingOrUseridAndCompanyidAndStatusAndDateBetweenAndInvstatusContaining(
            String userid, String companyid, int status, LocalDateTime fromdate, LocalDateTime todate,
            String activityref, String userid2, String companyid2, int status2, LocalDateTime fromdate2,
            LocalDateTime todate2, String label, String userid3, String companyid3, int status3,
            LocalDateTime fromdate3, LocalDateTime todate3, String itemcode, String userid4, String companyid4,
            int status4, LocalDateTime fromdate4, LocalDateTime todate4, String vouchercode, String userid5,
            String companyid5, int status5, LocalDateTime fromdate5, LocalDateTime todate5, String customername,
            String userid6, String companyid6, int status6, LocalDateTime fromdate6, LocalDateTime todate6,
            String invstatus, Pageable pagable);

    @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = ?1 and companyid = ?2 and (date between ?3 and ?4) and (activityref like %?5% or label like %?6% or itemcode like %?7% or vouchercode like %?8% or customername like %?9% or invstatus like %?10%)")
    public List<IAFilterTotalDto> getTotalsByDateBetweenForSearchContain(String userid, String companyid,
            LocalDateTime fromdate, LocalDateTime todate, String activityref, String label, String itemcode,
            String vouchercode, String customername, String invstatus);

    public Page<InventoryActivity> findByUseridAndCompanyidAndStatusAndDateBetweenAndInvstatusAndActivityrefContainingOrUseridAndCompanyidAndStatusAndDateBetweenAndInvstatusAndLabelContainingOrUseridAndCompanyidAndStatusAndDateBetweenAndInvstatusAndItemcodeContainingOrUseridAndCompanyidAndStatusAndDateBetweenAndInvstatusAndVouchercodeContainingOrUseridAndCompanyidAndStatusAndDateBetweenAndInvstatusAndCustomernameContaining(
            String userid, String companyid, int status, LocalDateTime fromdate, LocalDateTime todate, String invstatus,
            String activityref, String userid2, String companyid2, int status2, LocalDateTime fromdate2,
            LocalDateTime todate2, String invstatus2, String label, String userid3, String companyid3, int status3,
            LocalDateTime fromdate3, LocalDateTime todate3, String invstatus3, String itemcode, String userid4,
            String companyid4, int status4, LocalDateTime fromdate4, LocalDateTime todate4, String invstatus4,
            String vouchercode, String userid5, String companyid5, int status5, LocalDateTime fromdate5,
            LocalDateTime todate5, String invstatus5, String customername, Pageable pagable);

    @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = ?1 and companyid = ?2 and (date between ?3 and ?4) and invstatus = ?5 and (activityref like %?6% or label like %?7% or itemcode like %?8% or vouchercode like %?9% or customername like %?10%)")
    public List<IAFilterTotalDto> getTotalsByDateBetweenAndInvstatusForContainSearch(String userid, String companyid,
            LocalDateTime fromdate, LocalDateTime todate, String invstatus, String activityref, String label,
            String itemcode, String vouchercode, String customername);

    public Page<InventoryActivity> findByUseridAndCompanyidAndStatusAndActivityrefOrUseridAndCompanyidAndStatusAndLabelOrUseridAndCompanyidAndStatusAndItemcodeOrUseridAndCompanyidAndStatusAndVouchercodeOrUseridAndCompanyidAndStatusAndCustomernameOrUseridAndCompanyidAndStatusAndInvstatus(
            String userid, String companyid, int status, String activityref, String userid2, String companyid2,
            int status2, String label, String userid3, String companyid3, int status3, String itemcode, String userid4,
            String companyid4, int status4, String vouchercode, String userid5, String companyid5, int status5,
            String customername, String userid6, String companyid6, int status6, String invstatus, Pageable pagable);

    @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = ?1 and companyid = ?2 and (activityref = ?3 or label = ?4 or itemcode = ?5 or vouchercode = ?6 or customername = ?7 or invstatus = ?8)")
    public List<IAFilterTotalDto> getTotalsForSearch(String userid, String companyid, String activityref, String label,
            String itemcode, String vouchercode, String customername, String invstatus);

    public Page<InventoryActivity> findByUseridAndCompanyidAndStatusAndInvstatusAndActivityrefOrUseridAndCompanyidAndStatusAndInvstatusAndLabelOrUseridAndCompanyidAndStatusAndInvstatusAndItemcodeOrUseridAndCompanyidAndStatusAndInvstatusAndVouchercodeOrUseridAndCompanyidAndStatusAndInvstatusAndCustomername(
            String userid, String companyid, int status, String invstatus, String activityref, String userid2,
            String companyid2, int status2, String invstatus2, String label, String userid3, String companyid3,
            int status3, String invstatus3, String itemcode, String userid4, String companyid4, int status4,
            String invstatus4, String vouchercode, String userid5, String companyid5, int status5, String invstatus5,
            String customername, Pageable pagable);

    @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = ?1 and companyid = ?2 and invstatus = ?3 and (activityref = ?4 or label = ?5 or itemcode = ?6 or vouchercode = ?7 or customername = ?8)")
    public List<IAFilterTotalDto> getTotalsByInvstatusForSearch(String userid, String companyid, String invstatus,
            String activityref, String label, String itemcode, String vouchercode, String customername);

    public Page<InventoryActivity> findByUseridAndCompanyidAndStatusAndDateBetweenAndActivityrefOrUseridAndCompanyidAndStatusAndDateBetweenAndLabelOrUseridAndCompanyidAndStatusAndDateBetweenAndItemcodeOrUseridAndCompanyidAndStatusAndDateBetweenAndVouchercodeOrUseridAndCompanyidAndStatusAndDateBetweenAndCustomernameOrUseridAndCompanyidAndStatusAndDateBetweenAndInvstatus(
            String userid, String companyid, int status, LocalDateTime fromdate, LocalDateTime todate,
            String activityref, String userid2, String companyid2, int status2, LocalDateTime fromdate2,
            LocalDateTime todate2, String label, String userid3, String companyid3, int status3,
            LocalDateTime fromdate3, LocalDateTime todate3, String itemcode, String userid4, String companyid4,
            int status4, LocalDateTime fromdate4, LocalDateTime todate4, String vouchercode, String userid5,
            String companyid5, int status5, LocalDateTime fromdate5, LocalDateTime todate5, String customername,
            String userid6, String companyid6, int status6, LocalDateTime fromdate6, LocalDateTime todate6,
            String invstatus, Pageable pagable);

    @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = ?1 and companyid = ?2 and (date between ?3 and ?4) and (activityref = ?5 or label = ?6 or itemcode = ?7 or vouchercode = ?8 or customername = ?9 or invstatus = ?10)")
    public List<IAFilterTotalDto> getTotalsByDateBetweenForSearch(String userid, String companyid,
            LocalDateTime fromdate, LocalDateTime todate, String activityref, String label, String itemcode,
            String vouchercode, String customername, String invstatus);

    public Page<InventoryActivity> findByUseridAndCompanyidAndStatusAndDateBetweenAndInvstatusAndActivityrefOrUseridAndCompanyidAndStatusAndDateBetweenAndInvstatusAndLabelOrUseridAndCompanyidAndStatusAndDateBetweenAndInvstatusAndItemcodeOrUseridAndCompanyidAndStatusAndDateBetweenAndInvstatusAndVouchercodeOrUseridAndCompanyidAndStatusAndDateBetweenAndInvstatusAndCustomername(
            String userid, String companyid, int status, LocalDateTime fromdate, LocalDateTime todate, String invstatus,
            String activityref, String userid2, String companyid2, int status2, LocalDateTime fromdate2,
            LocalDateTime todate2, String invstatus2, String label, String userid3, String companyid3, int status3,
            LocalDateTime fromdate3, LocalDateTime todate3, String invstatus3, String itemcode, String userid4,
            String companyid4, int status4, LocalDateTime fromdate4, LocalDateTime todate4, String invstatus4,
            String vouchercode, String userid5, String companyid5, int status5, LocalDateTime fromdate5,
            LocalDateTime todate5, String invstatus5, String customername, Pageable pagable);

    @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = ?1 and companyid = ?2 and (date between ?3 and ?4) and invstatus = ?5 and (activityref = ?6 or label = ?7 or itemcode = ?8 or vouchercode = ?9 or customername = ?10)")
    public List<IAFilterTotalDto> getTotalsByDateBetweenAndInvstatusForSearch(String userid, String companyid,
            LocalDateTime fromdate, LocalDateTime todate, String invstatus, String activityref, String label,
            String itemcode, String vouchercode, String customername);

    public Page<InventoryActivity> findByUseridAndCompanyidAndStatusAndVoidstatus(String userid, String companyid,
            int status, int voidstatus, Pageable pagable);

    @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = ?1 and companyid = ?2 and voidstatus = ?3")
    public List<IAFilterTotalDto> getTotalsByVoidstatus(String userid, String companyid, int voidstatus);

    public Page<InventoryActivity> findByUseridAndCompanyidAndStatusAndVoidstatusAndInvstatus(String userid,
            String companyid, int status, int voidstatus, String invstatus, Pageable pagable);

    @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = ?1 and companyid = ?2 and voidstatus = ?3 and invstatus = ?4")
    public List<IAFilterTotalDto> getTotalsByVoidstatusAndInvstatus(String userid, String companyid, int voidstatus,
            String invstatus);

    public Page<InventoryActivity> findByUseridAndCompanyidAndStatusAndVoidstatusAndDateBetween(String userid,
            String companyid, int status, int voidstatus, LocalDateTime fromdate, LocalDateTime todate,
            Pageable pagable);

    @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = ?1 and companyid = ?2 and voidstatus = ?3 and (date between ?4 and ?5)")
    public List<IAFilterTotalDto> getTotalsByVoidstatusAndDateBetween(String userid, String companyid, int voidstatus,
            LocalDateTime fromdate, LocalDateTime todate);

    public Page<InventoryActivity> findByUseridAndCompanyidAndStatusAndVoidstatusAndDateBetweenAndInvstatus(
            String userid, String companyid, int status, int voidstatus, LocalDateTime fromdate, LocalDateTime todate,
            String invstatus, Pageable pagable);

    @Query("select new com.techhype.digitalinventory.InventoryActivity.IAFilterTotalDto(sum(qty), sum(amount)) from InventoryActivity where status = 1 and userid = ?1 and companyid = ?2 and voidstatus = ?3 and (date between ?4 and ?5) and invstatus = ?6")
    public List<IAFilterTotalDto> getTotalsByVoidstatusAndDateBetweenAndInvstatus(String userid, String companyid,
            int voidstatus, LocalDateTime fromdate, LocalDateTime todate, String invstatus);
}
