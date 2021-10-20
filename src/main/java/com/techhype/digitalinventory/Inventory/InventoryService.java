package com.techhype.digitalinventory.Inventory;

import java.time.LocalDateTime;
import java.util.Optional;

import com.techhype.digitalinventory.models.TokenData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {
    private IInventoryRepository iRepo;

    @Autowired
    public InventoryService(IInventoryRepository iRepo) {
        this.iRepo = iRepo;
    }

    public Optional<Inventory> getInventoryByItemRef(String itemref, TokenData tokenData) {
        return iRepo.findByItemrefAndUseridAndCompanyidAndStatus(itemref, tokenData.getUserid(),
                tokenData.getCompanyid(), 1);
    }

    public boolean updateInventory(String itemref, Inventory inventory, TokenData tokenData) {
        var oldinv = getInventoryByItemRef(itemref, tokenData);
        if (!oldinv.isPresent()) {
            return false;
        }
        var oldinventory = oldinv.get();
        oldinventory.setCounts(inventory.getCounts());
        oldinventory.setModifieddate(LocalDateTime.now());
        oldinventory.setIsinfinite(inventory.isIsinfinite());
        oldinventory.setItemcode(inventory.getItemcode());
        oldinventory.setLabel(inventory.getLabel());
        oldinventory.setNetprice(inventory.getNetprice());
        oldinventory.setPrice(inventory.getPrice());
        oldinventory.setRemark(inventory.getRemark());
        oldinventory.setTag(inventory.getTag());
        oldinventory.setCounts(inventory.getCounts());
        oldinventory.setRemaining(inventory.getCounts());
        iRepo.save(oldinventory);
        return true;
    }

    public boolean softDeleteInventory(String itemref, TokenData tokenData) {
        var oldinv = getInventoryByItemRef(itemref, tokenData);
        if (!oldinv.isPresent()) {
            return false;
        }
        var inventory = oldinv.get();
        inventory.setStatus(0);
        iRepo.save(inventory);
        return true;
    }

    public Inventory addInventory(Inventory inventory, TokenData tokenData) {
        var now = LocalDateTime.now();
        inventory.setCreateddate(now);
        inventory.setModifieddate(now);
        inventory.setRemaining(inventory.getCounts());
        inventory.setUserid(tokenData.getUserid());
        inventory.setUsername(tokenData.getUsername());
        inventory.setCompanyid(tokenData.getCompanyid());
        inventory.setCompanyname(tokenData.getCompanyname());
        var newInv = iRepo.save(inventory);
        newInv.setItemref(String.format("I%06d", newInv.getId()));
        return iRepo.save(newInv);
    }

    public Page<Inventory> getInventories(String search, int page, int perpage, String sortby, TokenData tokenData) {
        var pagable = PageRequest.of(page - 1, perpage, Sort.by(Sort.Direction.DESC, sortby));
        if (search == null || search.isEmpty()) {
            return iRepo.findByUseridAndCompanyidAndStatus(tokenData.getUserid(), tokenData.getCompanyid(), 1, pagable);
        }

        String s = search.trim();
        return iRepo
                .findByUseridAndCompanyidAndStatusAndItemrefContainingOrItemcodeContainingOrLabelContainingOrTagContaining(
                        tokenData.getUserid(), tokenData.getCompanyid(), 1, s, s, s, s, pagable);
    }
}
