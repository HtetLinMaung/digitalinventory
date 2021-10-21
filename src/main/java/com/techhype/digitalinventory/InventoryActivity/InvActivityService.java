package com.techhype.digitalinventory.InventoryActivity;

import java.time.LocalDateTime;
import java.util.Optional;

import com.techhype.digitalinventory.Inventory.IInventoryRepository;
import com.techhype.digitalinventory.models.TokenData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class InvActivityService {
    private IInvActivityRepository iaRepo;
    private IInventoryRepository iRepo;

    @Autowired
    public InvActivityService(IInvActivityRepository iaRepo, IInventoryRepository iRepo) {
        this.iaRepo = iaRepo;
        this.iRepo = iRepo;
    }

    public InventoryActivity addInventoryActivity(InventoryActivity inventoryActivity, TokenData tokenData) {
        var now = LocalDateTime.now();
        inventoryActivity.setId(null);
        inventoryActivity.setCreateddate(now);
        inventoryActivity.setModifieddate(now);
        inventoryActivity.setUserid(tokenData.getUserid());
        inventoryActivity.setUsername(tokenData.getUsername());
        inventoryActivity.setCompanyid(tokenData.getCompanyid());
        inventoryActivity.setCompanyname(tokenData.getCompanyname());
        inventoryActivity.setAmount(inventoryActivity.getQty() * inventoryActivity.getPrice());
        ;
        var newinvactivity = iaRepo.save(inventoryActivity);
        newinvactivity.setActivityref(newinvactivity.getItemref() + "-" + String.valueOf(newinvactivity.getId()));
        var inventory = iRepo.findByItemrefAndUseridAndCompanyidAndStatus(newinvactivity.getItemref(),
                tokenData.getUserid(), tokenData.getCompanyid(), 1).get();
        switch (newinvactivity.getInvstatus()) {
        case "in":
            inventory.setRemaining(inventory.getRemaining() + newinvactivity.getQty());
            break;
        case "out":
        case "reject":
            inventory.setRemaining(inventory.getRemaining() - newinvactivity.getQty());
            break;
        }
        iRepo.save(inventory);

        return iaRepo.save(newinvactivity);
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

    public Optional<InventoryActivity> getInvActivityByRef(String activityref, TokenData tokenData) {
        return iaRepo.findByUseridAndCompanyidAndStatusAndActivityref(tokenData.getUserid(), tokenData.getCompanyid(),
                1, activityref);
    }

    public Page<InventoryActivity> getInvActivities(String search, int page, int perpage, String sortby, String reverse,
            int voidstatus, TokenData tokenData) {
        var pagable = PageRequest.of(page - 1, perpage,
                Sort.by(reverse.equals("1") ? Sort.Direction.DESC : Sort.Direction.ASC, sortby));
        if (search == null || search.isEmpty()) {
            if (voidstatus == 2) {
                return iaRepo.findByUseridAndCompanyidAndStatus(tokenData.getUserid(), tokenData.getCompanyid(), 1,
                        pagable);
            }
            return iaRepo.findByUseridAndCompanyidAndStatusAndVoidstatus(tokenData.getUserid(),
                    tokenData.getCompanyid(), 1, voidstatus, pagable);
        }

        String s = search.trim();
        if (s.startsWith("\"") && s.endsWith("\"")) {
            s = s.replaceAll("\"", "");
            if (voidstatus == 2) {
                return iaRepo
                        .findByUseridAndCompanyidAndStatusAndActivityrefOrUseridAndCompanyidAndStatusAndLabelOrUseridAndCompanyidAndStatusAndItemcodeOrUseridAndCompanyidAndStatusAndVouchercodeOrUseridAndCompanyidAndStatusAndCustomernameOrUseridAndCompanyidAndStatusAndInvstatus(
                                tokenData.getUserid(), tokenData.getCompanyid(), 1, s, tokenData.getUserid(),
                                tokenData.getCompanyid(), 1, s, tokenData.getUserid(), tokenData.getCompanyid(), 1, s,
                                tokenData.getUserid(), tokenData.getCompanyid(), 1, s, tokenData.getUserid(),
                                tokenData.getCompanyid(), 1, s, tokenData.getUserid(), tokenData.getCompanyid(), 1, s,
                                pagable);
            }
            return iaRepo
                    .findByUseridAndCompanyidAndStatusAndVoidstatusAndActivityrefOrUseridAndCompanyidAndStatusAndVoidstatusAndLabelOrUseridAndCompanyidAndStatusAndVoidstatusAndItemcodeOrUseridAndCompanyidAndStatusAndVoidstatusAndVouchercodeOrUseridAndCompanyidAndStatusAndVoidstatusAndCustomernameOrUseridAndCompanyidAndStatusAndVoidstatusAndInvstatus(
                            tokenData.getUserid(), tokenData.getCompanyid(), 1, voidstatus, s, tokenData.getUserid(),
                            tokenData.getCompanyid(), 1, voidstatus, s, tokenData.getUserid(), tokenData.getCompanyid(),
                            1, voidstatus, s, tokenData.getUserid(), tokenData.getCompanyid(), 1, voidstatus, s,
                            tokenData.getUserid(), tokenData.getCompanyid(), 1, voidstatus, s, tokenData.getUserid(),
                            tokenData.getCompanyid(), 1, voidstatus, s, pagable);
        }
        if (voidstatus == 2) {
            return iaRepo
                    .findByUseridAndCompanyidAndStatusAndActivityrefContainingOrUseridAndCompanyidAndStatusAndLabelContainingOrUseridAndCompanyidAndStatusAndItemcodeContainingOrUseridAndCompanyidAndStatusAndVouchercodeContainingOrUseridAndCompanyidAndStatusAndCustomernameContainingOrUseridAndCompanyidAndStatusAndInvstatusContaining(
                            tokenData.getUserid(), tokenData.getCompanyid(), 1, s, tokenData.getUserid(),
                            tokenData.getCompanyid(), 1, s, tokenData.getUserid(), tokenData.getCompanyid(), 1, s,
                            tokenData.getUserid(), tokenData.getCompanyid(), 1, s, tokenData.getUserid(),
                            tokenData.getCompanyid(), 1, s, tokenData.getUserid(), tokenData.getCompanyid(), 1, s,
                            pagable);
        }
        return iaRepo
                .findByUseridAndCompanyidAndStatusAndVoidstatusAndActivityrefContainingOrUseridAndCompanyidAndStatusAndVoidstatusAndLabelContainingOrUseridAndCompanyidAndStatusAndVoidstatusAndItemcodeContainingOrUseridAndCompanyidAndStatusAndVoidstatusAndVouchercodeContainingOrUseridAndCompanyidAndStatusAndVoidstatusAndCustomernameContainingOrUseridAndCompanyidAndStatusAndVoidstatusAndInvstatusContaining(
                        tokenData.getUserid(), tokenData.getCompanyid(), 1, voidstatus, s, tokenData.getUserid(),
                        tokenData.getCompanyid(), 1, voidstatus, s, tokenData.getUserid(), tokenData.getCompanyid(), 1,
                        voidstatus, s, tokenData.getUserid(), tokenData.getCompanyid(), 1, voidstatus, s,
                        tokenData.getUserid(), tokenData.getCompanyid(), 1, voidstatus, s, tokenData.getUserid(),
                        tokenData.getCompanyid(), 1, voidstatus, s, pagable);
    }

    public boolean changeVoidStatus(String activityref, int voidstatus, TokenData tokenData) {
        var old = getInvActivityByRef(activityref, tokenData);
        if (!old.isPresent()) {
            return false;
        }
        var ia = old.get();
        ia.setVoidstatus(voidstatus);
        iaRepo.save(ia);
        var inv = iRepo.findByItemrefAndUseridAndCompanyidAndStatus(ia.getItemref(), tokenData.getUserid(),
                tokenData.getCompanyid(), 1).get();
        // int remaining = getRemaining(ia.getItemref(), tokenData) + inv.getCounts();
        if (voidstatus == 1) {
            inv.setRemaining(inv.getRemaining() + ia.getQty());
        } else {
            inv.setRemaining(inv.getRemaining() - ia.getQty());
        }

        iRepo.save(inv);
        return true;
    }
}
