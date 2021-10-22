package com.techhype.digitalinventory.Inventory;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IInventoryRepository extends JpaRepository<Inventory, Long> {
        Optional<Inventory> findByItemrefAndUseridAndCompanyidAndStatus(String itemref, String userid, String companyid,
                        int status);

        Page<Inventory> findByUseridAndCompanyidAndStatusAndItemrefContainingOrUseridAndCompanyidAndStatusAndItemcodeContainingOrUseridAndCompanyidAndStatusAndLabelContainingOrUseridAndCompanyidAndStatusAndTagContaining(
                        String userid, String companyid, int status, String itemref, String userid2, String companyid2,
                        int status2, String itemcode, String userid3, String companyid3, int status3, String label,
                        String userid4, String companyid4, int status4, String tag, Pageable pagable);

        Page<Inventory> findByUseridAndCompanyidAndStatusAndItemrefOrUseridAndCompanyidAndStatusAndItemcodeOrUseridAndCompanyidAndStatusAndLabelOrUseridAndCompanyidAndStatusAndTag(
                        String userid, String companyid, int status, String itemref, String userid2, String companyid2,
                        int status2, String itemcode, String userid3, String companyid3, int status3, String label,
                        String userid4, String companyid4, int status4, String tag, Pageable pagable);

        Page<Inventory> findByUseridAndCompanyidAndStatus(String userid, String companyid, int status,
                        Pageable pagable);

        List<InventoryDto> findByUseridAndCompanyidAndStatus(String userid, String companyid, int status);
}
