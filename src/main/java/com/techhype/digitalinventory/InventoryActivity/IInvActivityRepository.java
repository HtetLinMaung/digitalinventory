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

    public Page<InventoryActivity> findByUseridAndCompanyidAndStatusAndVoidstatusAndActivityrefOrUseridAndCompanyidAndStatusAndVoidstatusAndLabelOrUseridAndCompanyidAndStatusAndVoidstatusAndItemcodeOrUseridAndCompanyidAndStatusAndVoidstatusAndVouchercodeOrUseridAndCompanyidAndStatusAndVoidstatusAndCustomernameOrUseridAndCompanyidAndStatusAndVoidstatusAndInvstatus(
            String userid, String companyid, int status, int voidstatus, String activityref, String userid2,
            String companyid2, int status2, int voidstatus2, String label, String userid3, String companyid3,
            int status3, int voidstatus3, String itemcode, String userid4, String companyid4, int status4,
            int voidstatus4, String vouchercode, String userid5, String companyid5, int status5, int voidstatus5,
            String customername, String userid6, String companyid6, int status6, int voidstatus6, String invstatus,
            Pageable pagable);

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

    public Page<InventoryActivity> findByUseridAndCompanyidAndStatus(String userid, String companyid, int status,
            Pageable pagable);

    public Page<InventoryActivity> findByUseridAndCompanyidAndStatusAndDateBetween(String userid, String companyid,
            int status, LocalDateTime fromdate, LocalDateTime todate, Pageable pagable);

    public Page<InventoryActivity> findByUseridAndCompanyidAndStatusAndActivityrefContainingOrUseridAndCompanyidAndStatusAndLabelContainingOrUseridAndCompanyidAndStatusAndItemcodeContainingOrUseridAndCompanyidAndStatusAndVouchercodeContainingOrUseridAndCompanyidAndStatusAndCustomernameContainingOrUseridAndCompanyidAndStatusAndInvstatusContaining(
            String userid, String companyid, int status, String activityref, String userid2, String companyid2,
            int status2, String label, String userid3, String companyid3, int status3, String itemcode, String userid4,
            String companyid4, int status4, String vouchercode, String userid5, String companyid5, int status5,
            String customername, String userid6, String companyid6, int status6, String invstatus, Pageable pagable);

    public Page<InventoryActivity> findByUseridAndCompanyidAndStatusAndDateBetweenAndActivityrefContainingOrUseridAndCompanyidAndStatusAndDateBetweenAndLabelContainingOrUseridAndCompanyidAndStatusAndDateBetweenAndItemcodeContainingOrUseridAndCompanyidAndStatusAndDateBetweenAndVouchercodeContainingOrUseridAndCompanyidAndStatusAndDateBetweenAndCustomernameContainingOrUseridAndCompanyidAndStatusAndDateBetweenAndInvstatusContaining(
            String userid, String companyid, int status, LocalDateTime fromdate, LocalDateTime todate,
            String activityref, String userid2, String companyid2, int status2, LocalDateTime fromdate2,
            LocalDateTime todate2, String label, String userid3, String companyid3, int status3,
            LocalDateTime fromdate3, LocalDateTime todate3, String itemcode, String userid4, String companyid4,
            int status4, LocalDateTime fromdate4, LocalDateTime todate4, String vouchercode, String userid5,
            String companyid5, int status5, LocalDateTime fromdate5, LocalDateTime todate5, String customername,
            String userid6, String companyid6, int status6, LocalDateTime fromdate6, LocalDateTime todate6,
            String invstatus, Pageable pagable);

    public Page<InventoryActivity> findByUseridAndCompanyidAndStatusAndActivityrefOrUseridAndCompanyidAndStatusAndLabelOrUseridAndCompanyidAndStatusAndItemcodeOrUseridAndCompanyidAndStatusAndVouchercodeOrUseridAndCompanyidAndStatusAndCustomernameOrUseridAndCompanyidAndStatusAndInvstatus(
            String userid, String companyid, int status, String activityref, String userid2, String companyid2,
            int status2, String label, String userid3, String companyid3, int status3, String itemcode, String userid4,
            String companyid4, int status4, String vouchercode, String userid5, String companyid5, int status5,
            String customername, String userid6, String companyid6, int status6, String invstatus, Pageable pagable);

    public Page<InventoryActivity> findByUseridAndCompanyidAndStatusAndActivityrefOrUseridAndCompanyidAndStatusAndDateBetweenAndLabelOrUseridAndCompanyidAndStatusAndDateBetweenAndItemcodeOrUseridAndCompanyidAndStatusAndDateBetweenAndVouchercodeOrUseridAndCompanyidAndStatusAndDateBetweenAndCustomernameOrUseridAndCompanyidAndStatusAndDateBetweenAndInvstatus(
            String userid, String companyid, int status, LocalDateTime fromdate, LocalDateTime todate,
            String activityref, String userid2, String companyid2, int status2, LocalDateTime fromdate2,
            LocalDateTime todate2, String label, String userid3, String companyid3, int status3,
            LocalDateTime fromdate3, LocalDateTime todate3, String itemcode, String userid4, String companyid4,
            int status4, LocalDateTime fromdate4, LocalDateTime todate4, String vouchercode, String userid5,
            String companyid5, int status5, LocalDateTime fromdate5, LocalDateTime todate5, String customername,
            String userid6, String companyid6, int status6, LocalDateTime fromdate6, LocalDateTime todate6,
            String invstatus, Pageable pagable);

    public Page<InventoryActivity> findByUseridAndCompanyidAndStatusAndVoidstatus(String userid, String companyid,
            int status, int voidstatus, Pageable pagable);

    public Page<InventoryActivity> findByUseridAndCompanyidAndStatusAndVoidstatusAndDateBetween(String userid,
            String companyid, int status, int voidstatus, LocalDateTime fromdate, LocalDateTime todate,
            Pageable pagable);
}
