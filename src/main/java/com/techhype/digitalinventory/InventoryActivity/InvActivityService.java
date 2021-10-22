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

    public IAFilterTotalDto getInvActiviesTotal(String search, int page, int perpage, String sortby, String reverse,
            int voidstatus, LocalDateTime fromdate, LocalDateTime todate, String invstatus, TokenData tokenData) {
        if (search == null || search.isEmpty()) {
            if (voidstatus == 2) {
                if (fromdate != null && todate != null) {
                    if (!invstatus.equals("all")) {
                        return iaRepo.getTotalsByDateBetweenAndInvstatus(tokenData.getUserid(),
                                tokenData.getCompanyid(), fromdate, todate, invstatus).get(0);
                    }
                    return iaRepo
                            .getTotalsByDateBetween(tokenData.getUserid(), tokenData.getCompanyid(), fromdate, todate)
                            .get(0);
                }
                if (!invstatus.equals("all")) {
                    return iaRepo.getTotalsByInvstatus(tokenData.getUserid(), tokenData.getCompanyid(), invstatus)
                            .get(0);
                }
                return iaRepo.getTotals(tokenData.getUserid(), tokenData.getCompanyid()).get(0);
            }
            if (fromdate != null && todate != null) {
                if (!invstatus.equals("all")) {
                    return iaRepo.getTotalsByVoidstatusAndDateBetweenAndInvstatus(tokenData.getUserid(),
                            tokenData.getCompanyid(), voidstatus, fromdate, todate, invstatus).get(0);
                }
                return iaRepo.getTotalsByVoidstatusAndDateBetween(tokenData.getUserid(), tokenData.getCompanyid(),
                        voidstatus, fromdate, todate).get(0);
            }

            if (!invstatus.equals("all")) {
                return iaRepo.getTotalsByVoidstatusAndInvstatus(tokenData.getUserid(), tokenData.getCompanyid(),
                        voidstatus, invstatus).get(0);
            }
            return iaRepo.getTotalsByVoidstatus(tokenData.getUserid(), tokenData.getCompanyid(), voidstatus).get(0);
        }

        String s = search.trim();
        if (s.startsWith("\"") && s.endsWith("\"")) {
            s = s.replaceAll("\"", "");
            if (voidstatus == 2) {
                if (fromdate != null && todate != null) {
                    if (!invstatus.equals("all")) {
                        return iaRepo.getTotalsByDateBetweenAndInvstatusForSearch(tokenData.getUserid(),
                                tokenData.getCompanyid(), fromdate, todate, invstatus, s, s, s, s, s).get(0);
                    }
                    return iaRepo.getTotalsByDateBetweenForSearch(tokenData.getUserid(), tokenData.getCompanyid(),
                            fromdate, todate, s, s, s, s, s, s).get(0);
                }
                if (!invstatus.equals("all")) {
                    return iaRepo.getTotalsByInvstatusForSearch(tokenData.getUserid(), tokenData.getCompanyid(),
                            invstatus, s, s, s, s, s).get(0);
                }
                return iaRepo.getTotalsForSearch(tokenData.getUserid(), tokenData.getCompanyid(), s, s, s, s, s, s)
                        .get(0);
            }
            if (fromdate != null && todate != null) {
                if (!invstatus.equals("all")) {
                    return iaRepo
                            .getTotalsByVoidstatusAndDateBetweenAndInvstatusForSearch(tokenData.getUserid(),
                                    tokenData.getCompanyid(), voidstatus, fromdate, todate, invstatus, s, s, s, s, s)
                            .get(0);
                }
                return iaRepo.getTotalsByVoidstatusAndDateBetweenForSearch(tokenData.getUserid(),
                        tokenData.getCompanyid(), voidstatus, fromdate, todate, s, s, s, s, s, s).get(0);
            }
            if (!invstatus.equals("all")) {
                return iaRepo.getTotalsByVoidstatusAndInvstatusForSearch(tokenData.getUserid(),
                        tokenData.getCompanyid(), voidstatus, invstatus, s, s, s, s, s).get(0);
            }
            return iaRepo.getTotalsByVoidstatusForSearch(tokenData.getUserid(), tokenData.getCompanyid(), voidstatus, s,
                    s, s, s, s, s).get(0);
        }
        if (voidstatus == 2) {
            if (fromdate != null & todate != null) {
                if (!invstatus.equals("all")) {
                    return iaRepo.getTotalsByDateBetweenAndInvstatusForContainSearch(tokenData.getUserid(),
                            tokenData.getCompanyid(), fromdate, todate, invstatus, s, s, s, s, s).get(0);
                }
                return iaRepo.getTotalsByDateBetweenForSearchContain(tokenData.getUserid(), tokenData.getCompanyid(),
                        fromdate, todate, s, s, s, s, s, s).get(0);
            }
            if (!invstatus.equals("all")) {
                return iaRepo.getTotalsByInvstatusForSearchContain(tokenData.getUserid(), tokenData.getCompanyid(),
                        invstatus, s, s, s, s, s).get(0);
            }
            return iaRepo.getTotalsForSearchContain(tokenData.getUserid(), tokenData.getCompanyid(), s, s, s, s, s, s)
                    .get(0);
        }
        if (fromdate != null && todate != null) {
            if (!invstatus.equals("all")) {
                return iaRepo
                        .getTotalsByVoidstatusAndDateBetweenAndInvstatusForSearchContain(tokenData.getUserid(),
                                tokenData.getCompanyid(), voidstatus, fromdate, todate, invstatus, s, s, s, s, s)
                        .get(0);
            }
            return iaRepo.getTotalsByVoidstatusAndDateBetweenForSearchContain(tokenData.getUserid(),
                    tokenData.getCompanyid(), voidstatus, fromdate, todate, s, s, s, s, s, s).get(0);
        }
        if (!invstatus.equals("all")) {
            return iaRepo.getTotalsByVoidstatusAndInvstatusForSearchContain(tokenData.getUserid(),
                    tokenData.getCompanyid(), voidstatus, invstatus, s, s, s, s, s).get(0);
        }
        return iaRepo.getTotalsByVoidstatusForSearchContain(tokenData.getUserid(), tokenData.getCompanyid(), voidstatus,
                s, s, s, s, s, s).get(0);
    }

    public Page<InventoryActivity> getInvActivities(String search, int page, int perpage, String sortby, String reverse,
            int voidstatus, LocalDateTime fromdate, LocalDateTime todate, String invstatus, TokenData tokenData) {
        var pagable = PageRequest.of(page - 1, perpage,
                Sort.by(reverse.equals("1") ? Sort.Direction.DESC : Sort.Direction.ASC, sortby));
        if (search == null || search.isEmpty()) {
            if (voidstatus == 2) {
                if (fromdate != null && todate != null) {
                    if (!invstatus.equals("all")) {
                        return iaRepo.findByUseridAndCompanyidAndStatusAndDateBetweenAndInvstatus(tokenData.getUserid(),
                                tokenData.getCompanyid(), 1, fromdate, todate, invstatus, pagable);
                    }
                    return iaRepo.findByUseridAndCompanyidAndStatusAndDateBetween(tokenData.getUserid(),
                            tokenData.getCompanyid(), 1, fromdate, todate, pagable);
                }
                if (!invstatus.equals("all")) {
                    return iaRepo.findByUseridAndCompanyidAndStatusAndInvstatus(tokenData.getUserid(),
                            tokenData.getCompanyid(), 1, invstatus, pagable);
                }
                return iaRepo.findByUseridAndCompanyidAndStatus(tokenData.getUserid(), tokenData.getCompanyid(), 1,
                        pagable);
            }
            if (fromdate != null && todate != null) {
                if (!invstatus.equals("all")) {
                    return iaRepo.findByUseridAndCompanyidAndStatusAndVoidstatusAndDateBetweenAndInvstatus(
                            tokenData.getUserid(), tokenData.getCompanyid(), 1, voidstatus, fromdate, todate, invstatus,
                            pagable);
                }
                return iaRepo.findByUseridAndCompanyidAndStatusAndVoidstatusAndDateBetween(tokenData.getUserid(),
                        tokenData.getCompanyid(), 1, voidstatus, fromdate, todate, pagable);
            }

            if (!invstatus.equals("all")) {
                return iaRepo.findByUseridAndCompanyidAndStatusAndVoidstatusAndInvstatus(tokenData.getUserid(),
                        tokenData.getCompanyid(), 1, voidstatus, invstatus, pagable);
            }
            return iaRepo.findByUseridAndCompanyidAndStatusAndVoidstatus(tokenData.getUserid(),
                    tokenData.getCompanyid(), 1, voidstatus, pagable);
        }

        String s = search.trim();
        if (s.startsWith("\"") && s.endsWith("\"")) {
            s = s.replaceAll("\"", "");
            if (voidstatus == 2) {
                if (fromdate != null && todate != null) {
                    if (!invstatus.equals("all")) {
                        return iaRepo
                                .findByUseridAndCompanyidAndStatusAndDateBetweenAndInvstatusAndActivityrefOrUseridAndCompanyidAndStatusAndDateBetweenAndInvstatusAndLabelOrUseridAndCompanyidAndStatusAndDateBetweenAndInvstatusAndItemcodeOrUseridAndCompanyidAndStatusAndDateBetweenAndInvstatusAndVouchercodeOrUseridAndCompanyidAndStatusAndDateBetweenAndInvstatusAndCustomername(
                                        tokenData.getUserid(), tokenData.getCompanyid(), 1, fromdate, todate, invstatus,
                                        s, tokenData.getUserid(), tokenData.getCompanyid(), 1, fromdate, todate,
                                        invstatus, s, tokenData.getUserid(), tokenData.getCompanyid(), 1, fromdate,
                                        todate, invstatus, s, tokenData.getUserid(), tokenData.getCompanyid(), 1,
                                        fromdate, todate, invstatus, s, tokenData.getUserid(), tokenData.getCompanyid(),
                                        1, fromdate, todate, invstatus, s, pagable);
                    }
                    return iaRepo
                            .findByUseridAndCompanyidAndStatusAndDateBetweenAndActivityrefOrUseridAndCompanyidAndStatusAndDateBetweenAndLabelOrUseridAndCompanyidAndStatusAndDateBetweenAndItemcodeOrUseridAndCompanyidAndStatusAndDateBetweenAndVouchercodeOrUseridAndCompanyidAndStatusAndDateBetweenAndCustomernameOrUseridAndCompanyidAndStatusAndDateBetweenAndInvstatus(
                                    tokenData.getUserid(), tokenData.getCompanyid(), 1, fromdate, todate, s,
                                    tokenData.getUserid(), tokenData.getCompanyid(), 1, fromdate, todate, s,
                                    tokenData.getUserid(), tokenData.getCompanyid(), 1, fromdate, todate, s,
                                    tokenData.getUserid(), tokenData.getCompanyid(), 1, fromdate, todate, s,
                                    tokenData.getUserid(), tokenData.getCompanyid(), 1, fromdate, todate, s,
                                    tokenData.getUserid(), tokenData.getCompanyid(), 1, fromdate, todate, s, pagable);
                }
                if (!invstatus.equals("all")) {
                    return iaRepo
                            .findByUseridAndCompanyidAndStatusAndInvstatusAndActivityrefOrUseridAndCompanyidAndStatusAndInvstatusAndLabelOrUseridAndCompanyidAndStatusAndInvstatusAndItemcodeOrUseridAndCompanyidAndStatusAndInvstatusAndVouchercodeOrUseridAndCompanyidAndStatusAndInvstatusAndCustomername(
                                    tokenData.getUserid(), tokenData.getCompanyid(), 1, invstatus, s,
                                    tokenData.getUserid(), tokenData.getCompanyid(), 1, invstatus, s,
                                    tokenData.getUserid(), tokenData.getCompanyid(), 1, invstatus, s,
                                    tokenData.getUserid(), tokenData.getCompanyid(), 1, invstatus, s,
                                    tokenData.getUserid(), tokenData.getCompanyid(), 1, invstatus, s, pagable);
                }
                return iaRepo
                        .findByUseridAndCompanyidAndStatusAndActivityrefOrUseridAndCompanyidAndStatusAndLabelOrUseridAndCompanyidAndStatusAndItemcodeOrUseridAndCompanyidAndStatusAndVouchercodeOrUseridAndCompanyidAndStatusAndCustomernameOrUseridAndCompanyidAndStatusAndInvstatus(
                                tokenData.getUserid(), tokenData.getCompanyid(), 1, s, tokenData.getUserid(),
                                tokenData.getCompanyid(), 1, s, tokenData.getUserid(), tokenData.getCompanyid(), 1, s,
                                tokenData.getUserid(), tokenData.getCompanyid(), 1, s, tokenData.getUserid(),
                                tokenData.getCompanyid(), 1, s, tokenData.getUserid(), tokenData.getCompanyid(), 1, s,
                                pagable);
            }
            if (fromdate != null && todate != null) {
                if (!invstatus.equals("all")) {
                    return iaRepo
                            .findByUseridAndCompanyidAndStatusAndVoidstatusAndDateBetweenAndInvstatusAndActivityrefOrUseridAndCompanyidAndStatusAndVoidstatusAndDateBetweenAndInvstatusAndLabelOrUseridAndCompanyidAndStatusAndVoidstatusAndDateBetweenAndInvstatusAndItemcodeOrUseridAndCompanyidAndStatusAndVoidstatusAndDateBetweenAndInvstatusAndVouchercodeOrUseridAndCompanyidAndStatusAndVoidstatusAndDateBetweenAndInvstatusAndCustomername(
                                    tokenData.getUserid(), tokenData.getCompanyid(), 1, voidstatus, fromdate, todate,
                                    invstatus, s, tokenData.getUserid(), tokenData.getCompanyid(), 1, voidstatus,
                                    fromdate, todate, invstatus, s, tokenData.getUserid(), tokenData.getCompanyid(), 1,
                                    voidstatus, fromdate, todate, invstatus, s, tokenData.getUserid(),
                                    tokenData.getCompanyid(), 1, voidstatus, fromdate, todate, invstatus, s,
                                    tokenData.getUserid(), tokenData.getCompanyid(), 1, voidstatus, fromdate, todate,
                                    invstatus, s, pagable);
                }
                return iaRepo
                        .findByUseridAndCompanyidAndStatusAndVoidstatusAndDateBetweenAndActivityrefOrUseridAndCompanyidAndStatusAndVoidstatusAndDateBetweenAndLabelOrUseridAndCompanyidAndStatusAndVoidstatusAndDateBetweenAndItemcodeOrUseridAndCompanyidAndStatusAndVoidstatusAndDateBetweenAndVouchercodeOrUseridAndCompanyidAndStatusAndVoidstatusAndDateBetweenAndCustomernameOrUseridAndCompanyidAndStatusAndVoidstatusAndDateBetweenAndInvstatus(
                                tokenData.getUserid(), tokenData.getCompanyid(), 1, voidstatus, fromdate, todate, s,
                                tokenData.getUserid(), tokenData.getCompanyid(), 1, voidstatus, fromdate, todate, s,
                                tokenData.getUserid(), tokenData.getCompanyid(), 1, voidstatus, fromdate, todate, s,
                                tokenData.getUserid(), tokenData.getCompanyid(), 1, voidstatus, fromdate, todate, s,
                                tokenData.getUserid(), tokenData.getCompanyid(), 1, voidstatus, fromdate, todate, s,
                                tokenData.getUserid(), tokenData.getCompanyid(), 1, voidstatus, fromdate, todate, s,
                                pagable);
            }
            if (!invstatus.equals("all")) {
                return iaRepo
                        .findByUseridAndCompanyidAndStatusAndVoidstatusAndInvstatusAndActivityrefOrUseridAndCompanyidAndStatusAndVoidstatusAndInvstatusAndLabelOrUseridAndCompanyidAndStatusAndVoidstatusAndInvstatusAndItemcodeOrUseridAndCompanyidAndStatusAndVoidstatusAndInvstatusAndVouchercodeOrUseridAndCompanyidAndStatusAndVoidstatusAndInvstatusAndCustomername(
                                tokenData.getUserid(), tokenData.getCompanyid(), 1, voidstatus, invstatus, s,
                                tokenData.getUserid(), tokenData.getCompanyid(), 1, voidstatus, invstatus, s,
                                tokenData.getUserid(), tokenData.getCompanyid(), 1, voidstatus, invstatus, s,
                                tokenData.getUserid(), tokenData.getCompanyid(), 1, voidstatus, invstatus, s,
                                tokenData.getUserid(), tokenData.getCompanyid(), 1, voidstatus, invstatus, s, pagable);
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
            if (fromdate != null & todate != null) {
                if (!invstatus.equals("all")) {
                    return iaRepo
                            .findByUseridAndCompanyidAndStatusAndDateBetweenAndInvstatusAndActivityrefContainingOrUseridAndCompanyidAndStatusAndDateBetweenAndInvstatusAndLabelContainingOrUseridAndCompanyidAndStatusAndDateBetweenAndInvstatusAndItemcodeContainingOrUseridAndCompanyidAndStatusAndDateBetweenAndInvstatusAndVouchercodeContainingOrUseridAndCompanyidAndStatusAndDateBetweenAndInvstatusAndCustomernameContaining(
                                    tokenData.getUserid(), tokenData.getCompanyid(), 1, fromdate, todate, invstatus, s,
                                    tokenData.getUserid(), tokenData.getCompanyid(), 1, fromdate, todate, invstatus, s,
                                    tokenData.getUserid(), tokenData.getCompanyid(), 1, fromdate, todate, invstatus, s,
                                    tokenData.getUserid(), tokenData.getCompanyid(), 1, fromdate, todate, invstatus, s,
                                    tokenData.getUserid(), tokenData.getCompanyid(), 1, fromdate, todate, invstatus, s,
                                    pagable);
                }
                return iaRepo
                        .findByUseridAndCompanyidAndStatusAndDateBetweenAndActivityrefContainingOrUseridAndCompanyidAndStatusAndDateBetweenAndLabelContainingOrUseridAndCompanyidAndStatusAndDateBetweenAndItemcodeContainingOrUseridAndCompanyidAndStatusAndDateBetweenAndVouchercodeContainingOrUseridAndCompanyidAndStatusAndDateBetweenAndCustomernameContainingOrUseridAndCompanyidAndStatusAndDateBetweenAndInvstatusContaining(
                                tokenData.getUserid(), tokenData.getCompanyid(), 1, fromdate, todate, s,
                                tokenData.getUserid(), tokenData.getCompanyid(), 1, fromdate, todate, s,
                                tokenData.getUserid(), tokenData.getCompanyid(), 1, fromdate, todate, s,
                                tokenData.getUserid(), tokenData.getCompanyid(), 1, fromdate, todate, s,
                                tokenData.getUserid(), tokenData.getCompanyid(), 1, fromdate, todate, s,
                                tokenData.getUserid(), tokenData.getCompanyid(), 1, fromdate, todate, s, pagable);
            }
            if (!invstatus.equals("all")) {
                return iaRepo
                        .findByUseridAndCompanyidAndStatusAndInvstatusAndActivityrefContainingOrUseridAndCompanyidAndStatusAndInvstatusAndLabelContainingOrUseridAndCompanyidAndStatusAndInvstatusAndItemcodeContainingOrUseridAndCompanyidAndStatusAndInvstatusAndVouchercodeContainingOrUseridAndCompanyidAndStatusAndInvstatusAndCustomernameContaining(
                                tokenData.getUserid(), tokenData.getCompanyid(), 1, invstatus, s, tokenData.getUserid(),
                                tokenData.getCompanyid(), 1, invstatus, s, tokenData.getUserid(),
                                tokenData.getCompanyid(), 1, invstatus, s, tokenData.getUserid(),
                                tokenData.getCompanyid(), 1, invstatus, s, tokenData.getUserid(),
                                tokenData.getCompanyid(), 1, invstatus, s, pagable);
            }
            return iaRepo
                    .findByUseridAndCompanyidAndStatusAndActivityrefContainingOrUseridAndCompanyidAndStatusAndLabelContainingOrUseridAndCompanyidAndStatusAndItemcodeContainingOrUseridAndCompanyidAndStatusAndVouchercodeContainingOrUseridAndCompanyidAndStatusAndCustomernameContainingOrUseridAndCompanyidAndStatusAndInvstatusContaining(
                            tokenData.getUserid(), tokenData.getCompanyid(), 1, s, tokenData.getUserid(),
                            tokenData.getCompanyid(), 1, s, tokenData.getUserid(), tokenData.getCompanyid(), 1, s,
                            tokenData.getUserid(), tokenData.getCompanyid(), 1, s, tokenData.getUserid(),
                            tokenData.getCompanyid(), 1, s, tokenData.getUserid(), tokenData.getCompanyid(), 1, s,
                            pagable);
        }
        if (fromdate != null && todate != null) {
            if (!invstatus.equals("all")) {
                return iaRepo
                        .findByUseridAndCompanyidAndStatusAndVoidstatusAndDateBetweenAndInvstatusAndActivityrefContainingOrUseridAndCompanyidAndStatusAndVoidstatusAndDateBetweenAndInvstatusAndLabelContainingOrUseridAndCompanyidAndStatusAndVoidstatusAndDateBetweenAndInvstatusAndItemcodeContainingOrUseridAndCompanyidAndStatusAndVoidstatusAndDateBetweenAndInvstatusAndVouchercodeContainingOrUseridAndCompanyidAndStatusAndVoidstatusAndDateBetweenAndInvstatusAndCustomernameContaining(
                                tokenData.getUserid(), tokenData.getCompanyid(), 1, voidstatus, fromdate, todate,
                                invstatus, s, tokenData.getUserid(), tokenData.getCompanyid(), 1, voidstatus, fromdate,
                                todate, invstatus, s, tokenData.getUserid(), tokenData.getCompanyid(), 1, voidstatus,
                                fromdate, todate, invstatus, s, tokenData.getUserid(), tokenData.getCompanyid(), 1,
                                voidstatus, fromdate, todate, invstatus, s, tokenData.getUserid(),
                                tokenData.getCompanyid(), 1, voidstatus, fromdate, todate, invstatus, s, pagable);
            }
            return iaRepo
                    .findByUseridAndCompanyidAndStatusAndVoidstatusAndDateBetweenAndActivityrefContainingOrUseridAndCompanyidAndStatusAndVoidstatusAndDateBetweenAndLabelContainingOrUseridAndCompanyidAndStatusAndVoidstatusAndDateBetweenAndItemcodeContainingOrUseridAndCompanyidAndStatusAndVoidstatusAndDateBetweenAndVouchercodeContainingOrUseridAndCompanyidAndStatusAndVoidstatusAndDateBetweenAndCustomernameContainingOrUseridAndCompanyidAndStatusAndVoidstatusAndDateBetweenAndInvstatusContaining(
                            tokenData.getUserid(), tokenData.getCompanyid(), 1, voidstatus, fromdate, todate, s,
                            tokenData.getUserid(), tokenData.getCompanyid(), 1, voidstatus, fromdate, todate, s,
                            tokenData.getUserid(), tokenData.getCompanyid(), 1, voidstatus, fromdate, todate, s,
                            tokenData.getUserid(), tokenData.getCompanyid(), 1, voidstatus, fromdate, todate, s,
                            tokenData.getUserid(), tokenData.getCompanyid(), 1, voidstatus, fromdate, todate, s,
                            tokenData.getUserid(), tokenData.getCompanyid(), 1, voidstatus, fromdate, todate, s,
                            pagable);
        }
        if (!invstatus.equals("all")) {
            return iaRepo
                    .findByUseridAndCompanyidAndStatusAndVoidstatusAndInvstatusAndActivityrefContainingOrUseridAndCompanyidAndStatusAndVoidstatusAndInvstatusAndLabelContainingOrUseridAndCompanyidAndStatusAndVoidstatusAndInvstatusAndItemcodeContainingOrUseridAndCompanyidAndStatusAndVoidstatusAndInvstatusAndVouchercodeContainingOrUseridAndCompanyidAndStatusAndVoidstatusAndInvstatusAndCustomernameContaining(
                            tokenData.getUserid(), tokenData.getCompanyid(), 1, voidstatus, invstatus, s,
                            tokenData.getUserid(), tokenData.getCompanyid(), 1, voidstatus, invstatus, s,
                            tokenData.getUserid(), tokenData.getCompanyid(), 1, voidstatus, invstatus, s,
                            tokenData.getUserid(), tokenData.getCompanyid(), 1, voidstatus, invstatus, s,
                            tokenData.getUserid(), tokenData.getCompanyid(), 1, voidstatus, invstatus, s, pagable);
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
