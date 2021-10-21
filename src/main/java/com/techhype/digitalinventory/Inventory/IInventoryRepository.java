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

        Page<Inventory> findByUseridAndCompanyidAndStatusAndItemrefContainingOrItemcodeContainingOrLabelContainingOrTagContaining(
                        String userid, String companyid, int status, String itemref, String itemcode, String label,
                        String tag, Pageable pagable);

        Page<Inventory> findByUseridAndCompanyidAndStatusAndItemrefOrItemcodeOrLabelOrTag(String userid,
                        String companyid, int status, String itemref, String itemcode, String label, String tag,
                        Pageable pagable);

        Page<Inventory> findByUseridAndCompanyidAndStatus(String userid, String companyid, int status,
                        Pageable pagable);

        List<InventoryDto> findByUseridAndCompanyidAndStatus(String userid, String companyid, int status);
}
