package com.techhype.digitalinventory.Inventory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.techhype.digitalinventory.InventoryActivity.IInvActivityRepository;
import com.techhype.digitalinventory.models.TokenData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {
    private IInventoryRepository iRepo;
    private IInvActivityRepository iaRepo;

    @Autowired
    public InventoryService(IInventoryRepository iRepo, IInvActivityRepository iaRepo) {
        this.iRepo = iRepo;
        this.iaRepo = iaRepo;
    }

    public Optional<Inventory> getInventoryByItemRef(String itemref, TokenData tokenData) {
        return iRepo.findByItemrefAndUseridAndCompanyidAndStatus(itemref, tokenData.getUserid(),
                tokenData.getCompanyid(), 1);
    }

    public int getRemaining(String itemref, TokenData tokenData) {
        var inventory = iRepo.findByItemrefAndUseridAndCompanyidAndStatus(itemref, tokenData.getUserid(),
                tokenData.getCompanyid(), 1).get();
        int remaining = inventory.getCounts();
        var totals = iaRepo.getTotalsByItemref(tokenData.getUserid(), tokenData.getCompanyid(), itemref);
        for (var total : totals) {
            switch (total.getInvstatus()) {
            case "out":
            case "reject":
                remaining -= total.getTotal();
                break;
            case "in":
                remaining += total.getTotal();
                break;
            }
        }
        return remaining;
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
        oldinventory.setRemaining(getRemaining(itemref, tokenData));
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
        inventory.setId(null);
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

    public Page<Inventory> getInventories(String search, int page, int perpage, String sortby, String reverse,
            TokenData tokenData) {
        String role = tokenData.getRole();
        var pagable = PageRequest.of(page - 1, perpage,
                Sort.by(reverse.equals("1") ? Sort.Direction.DESC : Sort.Direction.ASC, sortby));
        if (search == null || search.isEmpty()) {
            if (role.equals("admin")) {
                return iRepo.findByCompanyidAndStatus(tokenData.getCompanyid(), 1, pagable);
            } else if (role.equals("superadmin")) {
                return iRepo.findByStatus(1, pagable);
            }
            return iRepo.findByUseridAndCompanyidAndStatus(tokenData.getUserid(), tokenData.getCompanyid(), 1, pagable);
        }

        String s = search.trim();
        if (s.startsWith("\"") && s.endsWith("\"")) {
            s = s.replaceAll("\"", "");
            if (role.equals("admin")) {
                return iRepo
                        .findByCompanyidAndStatusAndItemrefOrCompanyidAndStatusAndItemcodeOrCompanyidAndStatusAndLabelOrCompanyidAndStatusAndTag(
                                tokenData.getCompanyid(), 1, s, tokenData.getCompanyid(), 1, s,
                                tokenData.getCompanyid(), 1, s, tokenData.getCompanyid(), 1, s, pagable);
            } else if (role.equals("superadmin")) {
                return iRepo.findByStatusAndItemrefOrStatusAndItemcodeOrStatusAndLabelOrStatusAndTag(1, s, 1, s, 1, s,
                        1, s, pagable);
            }
            return iRepo
                    .findByUseridAndCompanyidAndStatusAndItemrefOrUseridAndCompanyidAndStatusAndItemcodeOrUseridAndCompanyidAndStatusAndLabelOrUseridAndCompanyidAndStatusAndTag(
                            tokenData.getUserid(), tokenData.getCompanyid(), 1, s, tokenData.getUserid(),
                            tokenData.getCompanyid(), 1, s, tokenData.getUserid(), tokenData.getCompanyid(), 1, s,
                            tokenData.getUserid(), tokenData.getCompanyid(), 1, s, pagable);
        }
        if (role.equals("admin")) {
            return iRepo
                    .findByCompanyidAndStatusAndItemrefContainingOrCompanyidAndStatusAndItemcodeContainingOrCompanyidAndStatusAndLabelContainingOrCompanyidAndStatusAndTagContaining(
                            tokenData.getCompanyid(), 1, s, tokenData.getCompanyid(), 1, s, tokenData.getCompanyid(), 1,
                            s, tokenData.getCompanyid(), 1, s, pagable);
        } else if (role.equals("superadmin")) {
            return iRepo
                    .findByStatusAndItemrefContainingOrStatusAndItemcodeContainingOrStatusAndLabelContainingOrStatusAndTagContaining(
                            1, s, 1, s, 1, s, 1, s, pagable);
        }
        return iRepo
                .findByUseridAndCompanyidAndStatusAndItemrefContainingOrUseridAndCompanyidAndStatusAndItemcodeContainingOrUseridAndCompanyidAndStatusAndLabelContainingOrUseridAndCompanyidAndStatusAndTagContaining(
                        tokenData.getUserid(), tokenData.getCompanyid(), 1, s, tokenData.getUserid(),
                        tokenData.getCompanyid(), 1, s, tokenData.getUserid(), tokenData.getCompanyid(), 1, s,
                        tokenData.getUserid(), tokenData.getCompanyid(), 1, s, pagable);
    }

    public List<InventoryDto> getInventoriesForCombo(TokenData tokenData) {
        return iRepo.findByUseridAndCompanyidAndStatus(tokenData.getUserid(), tokenData.getCompanyid(), 1);
    }
}
