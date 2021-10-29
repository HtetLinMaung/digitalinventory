package com.techhype.digitalinventory.InventoryActivity;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techhype.digitalinventory.Inventory.IInventoryRepository;
import com.techhype.digitalinventory.Inventory.Inventory;
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
        String role = tokenData.getRole();
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
        var inventory = new Inventory();
        switch (role) {
        case "normaluser":
            inventory = iRepo.findByItemrefAndUseridAndCompanyidAndStatus(newinvactivity.getItemref(),
                    tokenData.getUserid(), tokenData.getCompanyid(), 1).get();
            break;
        case "admin":
            inventory = iRepo
                    .findByItemrefAndCompanyidAndStatus(newinvactivity.getItemref(), tokenData.getCompanyid(), 1).get();
            break;
        case "superadmin":
            inventory = iRepo.findByItemrefAndStatus(newinvactivity.getItemref(), 1).get();
            break;
        }

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
        String role = tokenData.getRole();
        var inventory = new Inventory();
        List<ActivityTotalDto> totals = new ArrayList<>();
        switch (role) {
        case "normaluser":
            inventory = iRepo.findByItemrefAndUseridAndCompanyidAndStatus(itemref, tokenData.getUserid(),
                    tokenData.getCompanyid(), 1).get();
            totals = iaRepo.getTotalsByItemref(tokenData.getUserid(), tokenData.getCompanyid(), itemref);
            break;
        case "admin":
            inventory = iRepo.findByItemrefAndCompanyidAndStatus(itemref, tokenData.getCompanyid(), 1).get();
            totals = iaRepo.getTotalsByItemref(tokenData.getCompanyid(), itemref);
            break;
        case "superadmin":
            inventory = iRepo.findByItemrefAndStatus(itemref, 1).get();
            totals = iaRepo.getTotalsByItemref(itemref);
            break;
        }

        int remaining = inventory.getCounts();

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
        String role = tokenData.getRole();
        switch (role) {
        case "admin":
            return iaRepo.findByCompanyidAndStatusAndActivityref(tokenData.getCompanyid(), 1, activityref);
        case "superadmin":
            return iaRepo.findByStatusAndActivityref(1, activityref);
        }
        return iaRepo.findByUseridAndCompanyidAndStatusAndActivityref(tokenData.getUserid(), tokenData.getCompanyid(),
                1, activityref);
    }

    public IAFilterTotalDto getInvActiviesTotal(String search, int page, int perpage, String sortby, String reverse,
            int voidstatus, LocalDateTime fromdate, LocalDateTime todate, String invstatus, TokenData tokenData) {
        var role = tokenData.getRole();
        if (search == null || search.isEmpty()) {
            if (voidstatus == 2) {
                if (fromdate != null && todate != null) {
                    if (!invstatus.equals("all")) {
                        switch (role) {
                        case "admin":
                            return iaRepo.getTotalsByDateBetweenAndInvstatus(tokenData.getCompanyid(), fromdate, todate,
                                    invstatus).get(0);
                        case "superadmin":
                            return iaRepo.getTotalsByDateBetweenAndInvstatus(fromdate, todate, invstatus).get(0);
                        }
                        return iaRepo.getTotalsByDateBetweenAndInvstatus(tokenData.getUserid(),
                                tokenData.getCompanyid(), fromdate, todate, invstatus).get(0);
                    }
                    switch (role) {
                    case "admin":
                        return iaRepo.getTotalsByDateBetween(tokenData.getCompanyid(), fromdate, todate).get(0);
                    case "superadmin":
                        return iaRepo.getTotalsByDateBetween(fromdate, todate).get(0);
                    }
                    return iaRepo
                            .getTotalsByDateBetween(tokenData.getUserid(), tokenData.getCompanyid(), fromdate, todate)
                            .get(0);
                }
                if (!invstatus.equals("all")) {
                    switch (role) {
                    case "admin":
                        return iaRepo.getTotalsByInvstatus(tokenData.getCompanyid(), invstatus).get(0);
                    case "superadmin":
                        return iaRepo.getTotalsByInvstatus(invstatus).get(0);
                    }
                    return iaRepo.getTotalsByInvstatus(tokenData.getUserid(), tokenData.getCompanyid(), invstatus)
                            .get(0);
                }
                switch (role) {
                case "admin":
                    return iaRepo.getTotals(tokenData.getCompanyid()).get(0);
                case "superadmin":
                    return iaRepo.getTotals().get(0);
                }
                return iaRepo.getTotals(tokenData.getUserid(), tokenData.getCompanyid()).get(0);
            }
            if (fromdate != null && todate != null) {
                if (!invstatus.equals("all")) {
                    switch (role) {
                    case "admin":
                        return iaRepo.getTotalsByVoidstatusAndDateBetweenAndInvstatus(tokenData.getCompanyid(),
                                voidstatus, fromdate, todate, invstatus).get(0);
                    case "superadmin":
                        return iaRepo.getTotalsByVoidstatusAndDateBetweenAndInvstatus(voidstatus, fromdate, todate,
                                invstatus).get(0);
                    }
                    return iaRepo.getTotalsByVoidstatusAndDateBetweenAndInvstatus(tokenData.getUserid(),
                            tokenData.getCompanyid(), voidstatus, fromdate, todate, invstatus).get(0);
                }
                switch (role) {
                case "admin":
                    return iaRepo
                            .getTotalsByVoidstatusAndDateBetween(tokenData.getCompanyid(), voidstatus, fromdate, todate)
                            .get(0);
                case "superadmin":
                    return iaRepo.getTotalsByVoidstatusAndDateBetween(voidstatus, fromdate, todate).get(0);
                }
                return iaRepo.getTotalsByVoidstatusAndDateBetween(tokenData.getUserid(), tokenData.getCompanyid(),
                        voidstatus, fromdate, todate).get(0);
            }

            if (!invstatus.equals("all")) {
                switch (role) {
                case "admin":
                    return iaRepo.getTotalsByVoidstatusAndInvstatus(tokenData.getCompanyid(), voidstatus, invstatus)
                            .get(0);
                case "superadmin":
                    return iaRepo.getTotalsByVoidstatusAndInvstatus(voidstatus, invstatus).get(0);
                }
                return iaRepo.getTotalsByVoidstatusAndInvstatus(tokenData.getUserid(), tokenData.getCompanyid(),
                        voidstatus, invstatus).get(0);
            }
            switch (role) {
            case "admin":
                return iaRepo.getTotalsByVoidstatus(tokenData.getCompanyid(), voidstatus).get(0);
            case "superadmin":
                return iaRepo.getTotalsByVoidstatus(voidstatus).get(0);
            }
            return iaRepo.getTotalsByVoidstatus(tokenData.getUserid(), tokenData.getCompanyid(), voidstatus).get(0);
        }

        String s = search.trim();
        if (s.startsWith("\"") && s.endsWith("\"")) {
            s = s.replaceAll("\"", "");
            if (voidstatus == 2) {
                if (fromdate != null && todate != null) {
                    if (!invstatus.equals("all")) {
                        switch (role) {
                        case "admin":
                            return iaRepo.getTotalsByDateBetweenAndInvstatusForSearch(tokenData.getCompanyid(),
                                    fromdate, todate, invstatus, s, s, s, s, s).get(0);
                        case "superadmin":
                            return iaRepo.getTotalsByDateBetweenAndInvstatusForSearch(fromdate, todate, invstatus, s, s,
                                    s, s, s).get(0);
                        }
                        return iaRepo.getTotalsByDateBetweenAndInvstatusForSearch(tokenData.getUserid(),
                                tokenData.getCompanyid(), fromdate, todate, invstatus, s, s, s, s, s).get(0);
                    }
                    switch (role) {
                    case "admin":
                        return iaRepo.getTotalsByDateBetweenForSearch(tokenData.getCompanyid(), fromdate, todate, s, s,
                                s, s, s, s).get(0);
                    case "superadmin":
                        return iaRepo.getTotalsByDateBetweenForSearch(fromdate, todate, s, s, s, s, s, s).get(0);
                    }
                    return iaRepo.getTotalsByDateBetweenForSearch(tokenData.getUserid(), tokenData.getCompanyid(),
                            fromdate, todate, s, s, s, s, s, s).get(0);
                }
                if (!invstatus.equals("all")) {
                    switch (role) {
                    case "admin":
                        return iaRepo.getTotalsByInvstatusForSearch(tokenData.getCompanyid(), invstatus, s, s, s, s, s)
                                .get(0);
                    case "superadmin":
                        return iaRepo.getTotalsByInvstatusForSearch(invstatus, s, s, s, s, s).get(0);
                    }
                    return iaRepo.getTotalsByInvstatusForSearch(tokenData.getUserid(), tokenData.getCompanyid(),
                            invstatus, s, s, s, s, s).get(0);
                }
                switch (role) {
                case "admin":
                    return iaRepo.getTotalsForSearch(tokenData.getCompanyid(), s, s, s, s, s, s).get(0);
                case "superadmin":
                    return iaRepo.getTotalsForSearch(s, s, s, s, s, s).get(0);
                }
                return iaRepo.getTotalsForSearch(tokenData.getUserid(), tokenData.getCompanyid(), s, s, s, s, s, s)
                        .get(0);
            }
            if (fromdate != null && todate != null) {
                if (!invstatus.equals("all")) {
                    switch (role) {
                    case "admin":
                        return iaRepo.getTotalsByVoidstatusAndDateBetweenAndInvstatusForSearch(tokenData.getCompanyid(),
                                voidstatus, fromdate, todate, invstatus, s, s, s, s, s).get(0);
                    case "superadmin":
                        return iaRepo.getTotalsByVoidstatusAndDateBetweenAndInvstatusForSearch(voidstatus, fromdate,
                                todate, invstatus, s, s, s, s, s).get(0);
                    }
                    return iaRepo
                            .getTotalsByVoidstatusAndDateBetweenAndInvstatusForSearch(tokenData.getUserid(),
                                    tokenData.getCompanyid(), voidstatus, fromdate, todate, invstatus, s, s, s, s, s)
                            .get(0);
                }
                switch (role) {
                case "admin":
                    return iaRepo.getTotalsByVoidstatusAndDateBetweenForSearch(tokenData.getCompanyid(), voidstatus,
                            fromdate, todate, s, s, s, s, s, s).get(0);
                case "superadmin":
                    return iaRepo.getTotalsByVoidstatusAndDateBetweenForSearch(voidstatus, fromdate, todate, s, s, s, s,
                            s, s).get(0);
                }
                return iaRepo.getTotalsByVoidstatusAndDateBetweenForSearch(tokenData.getUserid(),
                        tokenData.getCompanyid(), voidstatus, fromdate, todate, s, s, s, s, s, s).get(0);
            }
            if (!invstatus.equals("all")) {
                switch (role) {
                case "admin":
                    return iaRepo.getTotalsByVoidstatusAndInvstatusForSearch(tokenData.getCompanyid(), voidstatus,
                            invstatus, s, s, s, s, s).get(0);
                case "superadmin":
                    return iaRepo.getTotalsByVoidstatusAndInvstatusForSearch(voidstatus, invstatus, s, s, s, s, s)
                            .get(0);
                }
                return iaRepo.getTotalsByVoidstatusAndInvstatusForSearch(tokenData.getUserid(),
                        tokenData.getCompanyid(), voidstatus, invstatus, s, s, s, s, s).get(0);
            }
            switch (role) {
            case "admin":
                return iaRepo.getTotalsByVoidstatusForSearch(tokenData.getCompanyid(), voidstatus, s, s, s, s, s, s)
                        .get(0);
            case "superadmin":
                return iaRepo.getTotalsByVoidstatusForSearch(voidstatus, s, s, s, s, s, s).get(0);
            }
            return iaRepo.getTotalsByVoidstatusForSearch(tokenData.getUserid(), tokenData.getCompanyid(), voidstatus, s,
                    s, s, s, s, s).get(0);
        }
        if (voidstatus == 2) {
            if (fromdate != null & todate != null) {
                if (!invstatus.equals("all")) {
                    switch (role) {
                    case "admin":
                        return iaRepo.getTotalsByDateBetweenAndInvstatusForContainSearch(tokenData.getCompanyid(),
                                fromdate, todate, invstatus, s, s, s, s, s).get(0);
                    case "superadmin":
                        return iaRepo.getTotalsByDateBetweenAndInvstatusForContainSearch(fromdate, todate, invstatus, s,
                                s, s, s, s).get(0);
                    }
                    return iaRepo.getTotalsByDateBetweenAndInvstatusForContainSearch(tokenData.getUserid(),
                            tokenData.getCompanyid(), fromdate, todate, invstatus, s, s, s, s, s).get(0);
                }
                switch (role) {
                case "admin":
                    return iaRepo.getTotalsByDateBetweenForSearchContain(tokenData.getCompanyid(), fromdate, todate, s,
                            s, s, s, s, s).get(0);
                case "superadmin":
                    return iaRepo.getTotalsByDateBetweenForSearchContain(fromdate, todate, s, s, s, s, s, s).get(0);
                }
                return iaRepo.getTotalsByDateBetweenForSearchContain(tokenData.getUserid(), tokenData.getCompanyid(),
                        fromdate, todate, s, s, s, s, s, s).get(0);
            }
            if (!invstatus.equals("all")) {
                switch (role) {
                case "admin":
                    return iaRepo
                            .getTotalsByInvstatusForSearchContain(tokenData.getCompanyid(), invstatus, s, s, s, s, s)
                            .get(0);
                case "superadmin":
                    return iaRepo.getTotalsByInvstatusForSearchContain(invstatus, s, s, s, s, s).get(0);
                }
                return iaRepo.getTotalsByInvstatusForSearchContain(tokenData.getUserid(), tokenData.getCompanyid(),
                        invstatus, s, s, s, s, s).get(0);
            }
            switch (role) {
            case "admin":
                return iaRepo.getTotalsForSearchContain(tokenData.getCompanyid(), s, s, s, s, s, s).get(0);
            case "superadmin":
                return iaRepo.getTotalsForSearchContain(s, s, s, s, s, s).get(0);
            }
            return iaRepo.getTotalsForSearchContain(tokenData.getUserid(), tokenData.getCompanyid(), s, s, s, s, s, s)
                    .get(0);
        }
        if (fromdate != null && todate != null) {
            if (!invstatus.equals("all")) {
                switch (role) {
                case "admin":
                    return iaRepo.getTotalsByVoidstatusAndDateBetweenAndInvstatusForSearchContain(
                            tokenData.getCompanyid(), voidstatus, fromdate, todate, invstatus, s, s, s, s, s).get(0);
                case "superadmin":
                    return iaRepo.getTotalsByVoidstatusAndDateBetweenAndInvstatusForSearchContain(voidstatus, fromdate,
                            todate, invstatus, s, s, s, s, s).get(0);
                }
                return iaRepo
                        .getTotalsByVoidstatusAndDateBetweenAndInvstatusForSearchContain(tokenData.getUserid(),
                                tokenData.getCompanyid(), voidstatus, fromdate, todate, invstatus, s, s, s, s, s)
                        .get(0);
            }
            switch (role) {
            case "admin":
                return iaRepo.getTotalsByVoidstatusAndDateBetweenForSearchContain(tokenData.getCompanyid(), voidstatus,
                        fromdate, todate, s, s, s, s, s, s).get(0);
            case "superadmin":
                return iaRepo.getTotalsByVoidstatusAndDateBetweenForSearchContain(voidstatus, fromdate, todate, s, s, s,
                        s, s, s).get(0);
            }
            return iaRepo.getTotalsByVoidstatusAndDateBetweenForSearchContain(tokenData.getUserid(),
                    tokenData.getCompanyid(), voidstatus, fromdate, todate, s, s, s, s, s, s).get(0);
        }
        if (!invstatus.equals("all")) {
            switch (role) {
            case "admin":
                return iaRepo.getTotalsByVoidstatusAndInvstatusForSearchContain(tokenData.getCompanyid(), voidstatus,
                        invstatus, s, s, s, s, s).get(0);
            case "superadmin":
                return iaRepo.getTotalsByVoidstatusAndInvstatusForSearchContain(voidstatus, invstatus, s, s, s, s, s)
                        .get(0);
            }
            return iaRepo.getTotalsByVoidstatusAndInvstatusForSearchContain(tokenData.getUserid(),
                    tokenData.getCompanyid(), voidstatus, invstatus, s, s, s, s, s).get(0);
        }
        switch (role) {
        case "admin":
            return iaRepo.getTotalsByVoidstatusForSearchContain(tokenData.getCompanyid(), voidstatus, s, s, s, s, s, s)
                    .get(0);
        case "superadmin":
            return iaRepo.getTotalsByVoidstatusForSearchContain(voidstatus, s, s, s, s, s, s).get(0);
        }
        return iaRepo.getTotalsByVoidstatusForSearchContain(tokenData.getUserid(), tokenData.getCompanyid(), voidstatus,
                s, s, s, s, s, s).get(0);
    }

    public Page<InventoryActivity> getInvActivities(String search, int page, int perpage, String sortby, String reverse,
            int voidstatus, LocalDateTime fromdate, LocalDateTime todate, String invstatus, TokenData tokenData) {
        String role = tokenData.getRole();
        var pagable = PageRequest.of(page - 1, perpage,
                Sort.by(reverse.equals("1") ? Sort.Direction.DESC : Sort.Direction.ASC, sortby));
        if (search == null || search.isEmpty()) {
            if (voidstatus == 2) {
                if (fromdate != null && todate != null) {
                    if (!invstatus.equals("all")) {
                        switch (role) {
                        case "admin":
                            return iaRepo.findByCompanyidAndStatusAndDateBetweenAndInvstatus(tokenData.getCompanyid(),
                                    1, fromdate, todate, invstatus, pagable);
                        case "superadmin":
                            return iaRepo.findByStatusAndDateBetweenAndInvstatus(1, fromdate, todate, invstatus,
                                    pagable);
                        }
                        return iaRepo.findByUseridAndCompanyidAndStatusAndDateBetweenAndInvstatus(tokenData.getUserid(),
                                tokenData.getCompanyid(), 1, fromdate, todate, invstatus, pagable);
                    }
                    switch (role) {
                    case "admin":
                        return iaRepo.findByCompanyidAndStatusAndDateBetween(tokenData.getCompanyid(), 1, fromdate,
                                todate, pagable);
                    case "superadmin":
                        return iaRepo.findByStatusAndDateBetween(1, fromdate, todate, pagable);
                    }
                    return iaRepo.findByUseridAndCompanyidAndStatusAndDateBetween(tokenData.getUserid(),
                            tokenData.getCompanyid(), 1, fromdate, todate, pagable);
                }
                if (!invstatus.equals("all")) {
                    switch (role) {
                    case "admin":
                        return iaRepo.findByCompanyidAndStatusAndInvstatus(tokenData.getCompanyid(), 1, invstatus,
                                pagable);
                    case "superadmin":
                        return iaRepo.findByStatusAndInvstatus(1, invstatus, pagable);
                    }
                    return iaRepo.findByUseridAndCompanyidAndStatusAndInvstatus(tokenData.getUserid(),
                            tokenData.getCompanyid(), 1, invstatus, pagable);
                }
                switch (role) {
                case "admin":
                    return iaRepo.findByCompanyidAndStatus(tokenData.getCompanyid(), 1, pagable);
                case "superadmin":
                    return iaRepo.findByStatus(1, pagable);
                }
                return iaRepo.findByUseridAndCompanyidAndStatus(tokenData.getUserid(), tokenData.getCompanyid(), 1,
                        pagable);
            }
            if (fromdate != null && todate != null) {
                if (!invstatus.equals("all")) {
                    switch (role) {
                    case "admin":
                        return iaRepo.findByCompanyidAndStatusAndVoidstatusAndDateBetweenAndInvstatus(
                                tokenData.getCompanyid(), 1, voidstatus, fromdate, todate, invstatus, pagable);
                    case "superadmin":
                        return iaRepo.findByStatusAndVoidstatusAndDateBetweenAndInvstatus(1, voidstatus, fromdate,
                                todate, invstatus, pagable);
                    }
                    return iaRepo.findByUseridAndCompanyidAndStatusAndVoidstatusAndDateBetweenAndInvstatus(
                            tokenData.getUserid(), tokenData.getCompanyid(), 1, voidstatus, fromdate, todate, invstatus,
                            pagable);
                }
                switch (role) {
                case "admin":
                    return iaRepo.findByCompanyidAndStatusAndVoidstatusAndDateBetween(tokenData.getCompanyid(), 1,
                            voidstatus, fromdate, todate, pagable);
                case "superadmin":
                    return iaRepo.findByStatusAndVoidstatusAndDateBetween(1, voidstatus, fromdate, todate, pagable);
                }
                return iaRepo.findByUseridAndCompanyidAndStatusAndVoidstatusAndDateBetween(tokenData.getUserid(),
                        tokenData.getCompanyid(), 1, voidstatus, fromdate, todate, pagable);
            }

            if (!invstatus.equals("all")) {
                switch (role) {
                case "admin":
                    return iaRepo.findByCompanyidAndStatusAndVoidstatusAndInvstatus(tokenData.getCompanyid(), 1,
                            voidstatus, invstatus, pagable);
                case "superadmin":
                    return iaRepo.findByStatusAndVoidstatusAndInvstatus(1, voidstatus, invstatus, pagable);
                }
                return iaRepo.findByUseridAndCompanyidAndStatusAndVoidstatusAndInvstatus(tokenData.getUserid(),
                        tokenData.getCompanyid(), 1, voidstatus, invstatus, pagable);
            }
            switch (role) {
            case "admin":
                return iaRepo.findByCompanyidAndStatusAndVoidstatus(tokenData.getCompanyid(), 1, voidstatus, pagable);
            case "superadmin":
                return iaRepo.findByStatusAndVoidstatus(1, voidstatus, pagable);
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
                        switch (role) {
                        case "admin":
                            return iaRepo
                                    .findByCompanyidAndStatusAndDateBetweenAndInvstatusAndActivityrefOrCompanyidAndStatusAndDateBetweenAndInvstatusAndLabelOrCompanyidAndStatusAndDateBetweenAndInvstatusAndItemcodeOrCompanyidAndStatusAndDateBetweenAndInvstatusAndVouchercodeOrCompanyidAndStatusAndDateBetweenAndInvstatusAndCustomername(
                                            tokenData.getCompanyid(), 1, fromdate, todate, invstatus, s,
                                            tokenData.getCompanyid(), 1, fromdate, todate, invstatus, s,
                                            tokenData.getCompanyid(), 1, fromdate, todate, invstatus, s,
                                            tokenData.getCompanyid(), 1, fromdate, todate, invstatus, s,
                                            tokenData.getCompanyid(), 1, fromdate, todate, invstatus, s, pagable);
                        case "superadmin":
                            return iaRepo
                                    .findByStatusAndDateBetweenAndInvstatusAndActivityrefOrStatusAndDateBetweenAndInvstatusAndLabelOrStatusAndDateBetweenAndInvstatusAndItemcodeOrStatusAndDateBetweenAndInvstatusAndVouchercodeOrStatusAndDateBetweenAndInvstatusAndCustomername(
                                            1, fromdate, todate, invstatus, s, 1, fromdate, todate, invstatus, s, 1,
                                            fromdate, todate, invstatus, s, 1, fromdate, todate, invstatus, s, 1,
                                            fromdate, todate, invstatus, s, pagable);
                        }
                        return iaRepo
                                .findByUseridAndCompanyidAndStatusAndDateBetweenAndInvstatusAndActivityrefOrUseridAndCompanyidAndStatusAndDateBetweenAndInvstatusAndLabelOrUseridAndCompanyidAndStatusAndDateBetweenAndInvstatusAndItemcodeOrUseridAndCompanyidAndStatusAndDateBetweenAndInvstatusAndVouchercodeOrUseridAndCompanyidAndStatusAndDateBetweenAndInvstatusAndCustomername(
                                        tokenData.getUserid(), tokenData.getCompanyid(), 1, fromdate, todate, invstatus,
                                        s, tokenData.getUserid(), tokenData.getCompanyid(), 1, fromdate, todate,
                                        invstatus, s, tokenData.getUserid(), tokenData.getCompanyid(), 1, fromdate,
                                        todate, invstatus, s, tokenData.getUserid(), tokenData.getCompanyid(), 1,
                                        fromdate, todate, invstatus, s, tokenData.getUserid(), tokenData.getCompanyid(),
                                        1, fromdate, todate, invstatus, s, pagable);
                    }
                    switch (role) {
                    case "admin":
                        return iaRepo
                                .findByCompanyidAndStatusAndDateBetweenAndActivityrefOrCompanyidAndStatusAndDateBetweenAndLabelOrCompanyidAndStatusAndDateBetweenAndItemcodeOrCompanyidAndStatusAndDateBetweenAndVouchercodeOrCompanyidAndStatusAndDateBetweenAndCustomernameOrCompanyidAndStatusAndDateBetweenAndInvstatus(
                                        tokenData.getCompanyid(), 1, fromdate, todate, s, tokenData.getCompanyid(), 1,
                                        fromdate, todate, s, tokenData.getCompanyid(), 1, fromdate, todate, s,
                                        tokenData.getCompanyid(), 1, fromdate, todate, s, tokenData.getCompanyid(), 1,
                                        fromdate, todate, s, tokenData.getCompanyid(), 1, fromdate, todate, s, pagable);
                    case "superadmin":
                        return iaRepo
                                .findByStatusAndDateBetweenAndActivityrefOrStatusAndDateBetweenAndLabelOrStatusAndDateBetweenAndItemcodeOrStatusAndDateBetweenAndVouchercodeOrStatusAndDateBetweenAndCustomernameOrStatusAndDateBetweenAndInvstatus(
                                        1, fromdate, todate, s, 1, fromdate, todate, s, 1, fromdate, todate, s, 1,
                                        fromdate, todate, s, 1, fromdate, todate, s, 1, fromdate, todate, s, pagable);
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
                    switch (role) {
                    case "admin":
                        return iaRepo
                                .findByCompanyidAndStatusAndInvstatusAndActivityrefOrCompanyidAndStatusAndInvstatusAndLabelOrCompanyidAndStatusAndInvstatusAndItemcodeOrCompanyidAndStatusAndInvstatusAndVouchercodeOrCompanyidAndStatusAndInvstatusAndCustomername(
                                        tokenData.getCompanyid(), 1, invstatus, s, tokenData.getCompanyid(), 1,
                                        invstatus, s, tokenData.getCompanyid(), 1, invstatus, s,
                                        tokenData.getCompanyid(), 1, invstatus, s, tokenData.getCompanyid(), 1,
                                        invstatus, s, pagable);
                    case "superadmin":
                        return iaRepo
                                .findByStatusAndInvstatusAndActivityrefOrStatusAndInvstatusAndLabelOrStatusAndInvstatusAndItemcodeOrStatusAndInvstatusAndVouchercodeOrStatusAndInvstatusAndCustomername(
                                        1, invstatus, s, 1, invstatus, s, 1, invstatus, s, 1, invstatus, s, 1,
                                        invstatus, s, pagable);
                    }
                    return iaRepo
                            .findByUseridAndCompanyidAndStatusAndInvstatusAndActivityrefOrUseridAndCompanyidAndStatusAndInvstatusAndLabelOrUseridAndCompanyidAndStatusAndInvstatusAndItemcodeOrUseridAndCompanyidAndStatusAndInvstatusAndVouchercodeOrUseridAndCompanyidAndStatusAndInvstatusAndCustomername(
                                    tokenData.getUserid(), tokenData.getCompanyid(), 1, invstatus, s,
                                    tokenData.getUserid(), tokenData.getCompanyid(), 1, invstatus, s,
                                    tokenData.getUserid(), tokenData.getCompanyid(), 1, invstatus, s,
                                    tokenData.getUserid(), tokenData.getCompanyid(), 1, invstatus, s,
                                    tokenData.getUserid(), tokenData.getCompanyid(), 1, invstatus, s, pagable);
                }
                switch (role) {
                case "admin":
                    return iaRepo
                            .findByCompanyidAndStatusAndActivityrefOrCompanyidAndStatusAndLabelOrCompanyidAndStatusAndItemcodeOrCompanyidAndStatusAndVouchercodeOrCompanyidAndStatusAndCustomernameOrCompanyidAndStatusAndInvstatus(
                                    tokenData.getCompanyid(), 1, s, tokenData.getCompanyid(), 1, s,
                                    tokenData.getCompanyid(), 1, s, tokenData.getCompanyid(), 1, s,
                                    tokenData.getCompanyid(), 1, s, tokenData.getCompanyid(), 1, s, pagable);
                case "superadmin":
                    return iaRepo
                            .findByStatusAndActivityrefOrStatusAndLabelOrStatusAndItemcodeOrStatusAndVouchercodeOrStatusAndCustomernameOrStatusAndInvstatus(
                                    1, s, 1, s, 1, s, 1, s, 1, s, 1, s, pagable);
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
                    switch (role) {
                    case "admin":
                        return iaRepo
                                .findByCompanyidAndStatusAndVoidstatusAndDateBetweenAndInvstatusAndActivityrefOrCompanyidAndStatusAndVoidstatusAndDateBetweenAndInvstatusAndLabelOrCompanyidAndStatusAndVoidstatusAndDateBetweenAndInvstatusAndItemcodeOrCompanyidAndStatusAndVoidstatusAndDateBetweenAndInvstatusAndVouchercodeOrCompanyidAndStatusAndVoidstatusAndDateBetweenAndInvstatusAndCustomername(
                                        tokenData.getCompanyid(), 1, voidstatus, fromdate, todate, invstatus, s,
                                        tokenData.getCompanyid(), 1, voidstatus, fromdate, todate, invstatus, s,
                                        tokenData.getCompanyid(), 1, voidstatus, fromdate, todate, invstatus, s,
                                        tokenData.getCompanyid(), 1, voidstatus, fromdate, todate, invstatus, s,
                                        tokenData.getCompanyid(), 1, voidstatus, fromdate, todate, invstatus, s,
                                        pagable);
                    case "superadmin":
                        return iaRepo
                                .findByStatusAndVoidstatusAndDateBetweenAndInvstatusAndActivityrefOrStatusAndVoidstatusAndDateBetweenAndInvstatusAndLabelOrStatusAndVoidstatusAndDateBetweenAndInvstatusAndItemcodeOrStatusAndVoidstatusAndDateBetweenAndInvstatusAndVouchercodeOrStatusAndVoidstatusAndDateBetweenAndInvstatusAndCustomername(
                                        1, voidstatus, fromdate, todate, invstatus, s, 1, voidstatus, fromdate, todate,
                                        invstatus, s, 1, voidstatus, fromdate, todate, invstatus, s, 1, voidstatus,
                                        fromdate, todate, invstatus, s, 1, voidstatus, fromdate, todate, invstatus, s,
                                        pagable);
                    }
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
                switch (role) {
                case "admin":
                    return iaRepo
                            .findByCompanyidAndStatusAndVoidstatusAndDateBetweenAndActivityrefOrCompanyidAndStatusAndVoidstatusAndDateBetweenAndLabelOrCompanyidAndStatusAndVoidstatusAndDateBetweenAndItemcodeOrCompanyidAndStatusAndVoidstatusAndDateBetweenAndVouchercodeOrCompanyidAndStatusAndVoidstatusAndDateBetweenAndCustomernameOrCompanyidAndStatusAndVoidstatusAndDateBetweenAndInvstatus(
                                    tokenData.getCompanyid(), 1, voidstatus, fromdate, todate, s,
                                    tokenData.getCompanyid(), 1, voidstatus, fromdate, todate, s,
                                    tokenData.getCompanyid(), 1, voidstatus, fromdate, todate, s,
                                    tokenData.getCompanyid(), 1, voidstatus, fromdate, todate, s,
                                    tokenData.getCompanyid(), 1, voidstatus, fromdate, todate, s,
                                    tokenData.getCompanyid(), 1, voidstatus, fromdate, todate, s, pagable);
                case "superadmin":
                    return iaRepo
                            .findByStatusAndVoidstatusAndDateBetweenAndActivityrefOrStatusAndVoidstatusAndDateBetweenAndLabelOrStatusAndVoidstatusAndDateBetweenAndItemcodeOrStatusAndVoidstatusAndDateBetweenAndVouchercodeOrStatusAndVoidstatusAndDateBetweenAndCustomernameOrStatusAndVoidstatusAndDateBetweenAndInvstatus(
                                    1, voidstatus, fromdate, todate, s, 1, voidstatus, fromdate, todate, s, 1,
                                    voidstatus, fromdate, todate, s, 1, voidstatus, fromdate, todate, s, 1, voidstatus,
                                    fromdate, todate, s, 1, voidstatus, fromdate, todate, s, pagable);
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
                switch (role) {
                case "admin":
                    return iaRepo
                            .findByCompanyidAndStatusAndVoidstatusAndInvstatusAndActivityrefOrCompanyidAndStatusAndVoidstatusAndInvstatusAndLabelOrCompanyidAndStatusAndVoidstatusAndInvstatusAndItemcodeOrCompanyidAndStatusAndVoidstatusAndInvstatusAndVouchercodeOrCompanyidAndStatusAndVoidstatusAndInvstatusAndCustomername(
                                    tokenData.getCompanyid(), 1, voidstatus, invstatus, s, tokenData.getCompanyid(), 1,
                                    voidstatus, invstatus, s, tokenData.getCompanyid(), 1, voidstatus, invstatus, s,
                                    tokenData.getCompanyid(), 1, voidstatus, invstatus, s, tokenData.getCompanyid(), 1,
                                    voidstatus, invstatus, s, pagable);
                case "superadmin":
                    return iaRepo
                            .findByStatusAndVoidstatusAndInvstatusAndActivityrefOrStatusAndVoidstatusAndInvstatusAndLabelOrStatusAndVoidstatusAndInvstatusAndItemcodeOrStatusAndVoidstatusAndInvstatusAndVouchercodeOrStatusAndVoidstatusAndInvstatusAndCustomername(
                                    1, voidstatus, invstatus, s, 1, voidstatus, invstatus, s, 1, voidstatus, invstatus,
                                    s, 1, voidstatus, invstatus, s, 1, voidstatus, invstatus, s, pagable);
                }
                return iaRepo
                        .findByUseridAndCompanyidAndStatusAndVoidstatusAndInvstatusAndActivityrefOrUseridAndCompanyidAndStatusAndVoidstatusAndInvstatusAndLabelOrUseridAndCompanyidAndStatusAndVoidstatusAndInvstatusAndItemcodeOrUseridAndCompanyidAndStatusAndVoidstatusAndInvstatusAndVouchercodeOrUseridAndCompanyidAndStatusAndVoidstatusAndInvstatusAndCustomername(
                                tokenData.getUserid(), tokenData.getCompanyid(), 1, voidstatus, invstatus, s,
                                tokenData.getUserid(), tokenData.getCompanyid(), 1, voidstatus, invstatus, s,
                                tokenData.getUserid(), tokenData.getCompanyid(), 1, voidstatus, invstatus, s,
                                tokenData.getUserid(), tokenData.getCompanyid(), 1, voidstatus, invstatus, s,
                                tokenData.getUserid(), tokenData.getCompanyid(), 1, voidstatus, invstatus, s, pagable);
            }
            switch (role) {
            case "admin":
                return iaRepo
                        .findByCompanyidAndStatusAndVoidstatusAndActivityrefOrCompanyidAndStatusAndVoidstatusAndLabelOrCompanyidAndStatusAndVoidstatusAndItemcodeOrCompanyidAndStatusAndVoidstatusAndVouchercodeOrCompanyidAndStatusAndVoidstatusAndCustomernameOrCompanyidAndStatusAndVoidstatusAndInvstatus(
                                tokenData.getCompanyid(), 1, voidstatus, s, tokenData.getCompanyid(), 1, voidstatus, s,
                                tokenData.getCompanyid(), 1, voidstatus, s, tokenData.getCompanyid(), 1, voidstatus, s,
                                tokenData.getCompanyid(), 1, voidstatus, s, tokenData.getCompanyid(), 1, voidstatus, s,
                                pagable);
            case "superadmin":
                return iaRepo
                        .findByStatusAndVoidstatusAndActivityrefOrStatusAndVoidstatusAndLabelOrStatusAndVoidstatusAndItemcodeOrStatusAndVoidstatusAndVouchercodeOrStatusAndVoidstatusAndCustomernameOrStatusAndVoidstatusAndInvstatus(
                                1, voidstatus, s, 1, voidstatus, s, 1, voidstatus, s, 1, voidstatus, s, 1, voidstatus,
                                s, 1, voidstatus, s, pagable);
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
                    switch (role) {
                    case "admin":
                        return iaRepo
                                .findByCompanyidAndStatusAndDateBetweenAndInvstatusAndActivityrefContainingOrCompanyidAndStatusAndDateBetweenAndInvstatusAndLabelContainingOrCompanyidAndStatusAndDateBetweenAndInvstatusAndItemcodeContainingOrCompanyidAndStatusAndDateBetweenAndInvstatusAndVouchercodeContainingOrCompanyidAndStatusAndDateBetweenAndInvstatusAndCustomernameContaining(
                                        tokenData.getCompanyid(), 1, fromdate, todate, invstatus, s,
                                        tokenData.getCompanyid(), 1, fromdate, todate, invstatus, s,
                                        tokenData.getCompanyid(), 1, fromdate, todate, invstatus, s,
                                        tokenData.getCompanyid(), 1, fromdate, todate, invstatus, s,
                                        tokenData.getCompanyid(), 1, fromdate, todate, invstatus, s, pagable);
                    case "superadmin":
                        return iaRepo
                                .findByStatusAndDateBetweenAndInvstatusAndActivityrefContainingOrStatusAndDateBetweenAndInvstatusAndLabelContainingOrStatusAndDateBetweenAndInvstatusAndItemcodeContainingOrStatusAndDateBetweenAndInvstatusAndVouchercodeContainingOrStatusAndDateBetweenAndInvstatusAndCustomernameContaining(
                                        1, fromdate, todate, invstatus, s, 1, fromdate, todate, invstatus, s, 1,
                                        fromdate, todate, invstatus, s, 1, fromdate, todate, invstatus, s, 1, fromdate,
                                        todate, invstatus, s, pagable);
                    }
                    return iaRepo
                            .findByUseridAndCompanyidAndStatusAndDateBetweenAndInvstatusAndActivityrefContainingOrUseridAndCompanyidAndStatusAndDateBetweenAndInvstatusAndLabelContainingOrUseridAndCompanyidAndStatusAndDateBetweenAndInvstatusAndItemcodeContainingOrUseridAndCompanyidAndStatusAndDateBetweenAndInvstatusAndVouchercodeContainingOrUseridAndCompanyidAndStatusAndDateBetweenAndInvstatusAndCustomernameContaining(
                                    tokenData.getUserid(), tokenData.getCompanyid(), 1, fromdate, todate, invstatus, s,
                                    tokenData.getUserid(), tokenData.getCompanyid(), 1, fromdate, todate, invstatus, s,
                                    tokenData.getUserid(), tokenData.getCompanyid(), 1, fromdate, todate, invstatus, s,
                                    tokenData.getUserid(), tokenData.getCompanyid(), 1, fromdate, todate, invstatus, s,
                                    tokenData.getUserid(), tokenData.getCompanyid(), 1, fromdate, todate, invstatus, s,
                                    pagable);
                }
                switch (role) {
                case "admin":
                    return iaRepo
                            .findByCompanyidAndStatusAndDateBetweenAndActivityrefContainingOrCompanyidAndStatusAndDateBetweenAndLabelContainingOrCompanyidAndStatusAndDateBetweenAndItemcodeContainingOrCompanyidAndStatusAndDateBetweenAndVouchercodeContainingOrCompanyidAndStatusAndDateBetweenAndCustomernameContainingOrCompanyidAndStatusAndDateBetweenAndInvstatusContaining(
                                    tokenData.getCompanyid(), 1, fromdate, todate, s, tokenData.getCompanyid(), 1,
                                    fromdate, todate, s, tokenData.getCompanyid(), 1, fromdate, todate, s,
                                    tokenData.getCompanyid(), 1, fromdate, todate, s, tokenData.getCompanyid(), 1,
                                    fromdate, todate, s, tokenData.getCompanyid(), 1, fromdate, todate, s, pagable);
                case "superadmin":
                    return iaRepo
                            .findByStatusAndDateBetweenAndActivityrefContainingOrStatusAndDateBetweenAndLabelContainingOrStatusAndDateBetweenAndItemcodeContainingOrStatusAndDateBetweenAndVouchercodeContainingOrStatusAndDateBetweenAndCustomernameContainingOrStatusAndDateBetweenAndInvstatusContaining(
                                    1, fromdate, todate, s, 1, fromdate, todate, s, 1, fromdate, todate, s, 1, fromdate,
                                    todate, s, 1, fromdate, todate, s, 1, fromdate, todate, s, pagable);
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
                switch (role) {
                case "admin":
                    return iaRepo
                            .findByCompanyidAndStatusAndInvstatusAndActivityrefContainingOrCompanyidAndStatusAndInvstatusAndLabelContainingOrCompanyidAndStatusAndInvstatusAndItemcodeContainingOrCompanyidAndStatusAndInvstatusAndVouchercodeContainingOrCompanyidAndStatusAndInvstatusAndCustomernameContaining(
                                    tokenData.getCompanyid(), 1, invstatus, s, tokenData.getCompanyid(), 1, invstatus,
                                    s, tokenData.getCompanyid(), 1, invstatus, s, tokenData.getCompanyid(), 1,
                                    invstatus, s, tokenData.getCompanyid(), 1, invstatus, s, pagable);
                case "superadmin":
                    return iaRepo
                            .findByStatusAndInvstatusAndActivityrefContainingOrStatusAndInvstatusAndLabelContainingOrStatusAndInvstatusAndItemcodeContainingOrStatusAndInvstatusAndVouchercodeContainingOrStatusAndInvstatusAndCustomernameContaining(
                                    1, invstatus, s, 1, invstatus, s, 1, invstatus, s, 1, invstatus, s, 1, invstatus, s,
                                    pagable);
                }
                return iaRepo
                        .findByUseridAndCompanyidAndStatusAndInvstatusAndActivityrefContainingOrUseridAndCompanyidAndStatusAndInvstatusAndLabelContainingOrUseridAndCompanyidAndStatusAndInvstatusAndItemcodeContainingOrUseridAndCompanyidAndStatusAndInvstatusAndVouchercodeContainingOrUseridAndCompanyidAndStatusAndInvstatusAndCustomernameContaining(
                                tokenData.getUserid(), tokenData.getCompanyid(), 1, invstatus, s, tokenData.getUserid(),
                                tokenData.getCompanyid(), 1, invstatus, s, tokenData.getUserid(),
                                tokenData.getCompanyid(), 1, invstatus, s, tokenData.getUserid(),
                                tokenData.getCompanyid(), 1, invstatus, s, tokenData.getUserid(),
                                tokenData.getCompanyid(), 1, invstatus, s, pagable);
            }
            switch (role) {
            case "admin":
                return iaRepo
                        .findByCompanyidAndStatusAndActivityrefContainingOrCompanyidAndStatusAndLabelContainingOrCompanyidAndStatusAndItemcodeContainingOrCompanyidAndStatusAndVouchercodeContainingOrCompanyidAndStatusAndCustomernameContainingOrCompanyidAndStatusAndInvstatusContaining(
                                tokenData.getCompanyid(), 1, s, tokenData.getCompanyid(), 1, s,
                                tokenData.getCompanyid(), 1, s, tokenData.getCompanyid(), 1, s,
                                tokenData.getCompanyid(), 1, s, tokenData.getCompanyid(), 1, s, pagable);
            case "superadmin":
                return iaRepo
                        .findByStatusAndActivityrefContainingOrStatusAndLabelContainingOrStatusAndItemcodeContainingOrStatusAndVouchercodeContainingOrStatusAndCustomernameContainingOrStatusAndInvstatusContaining(
                                1, s, 1, s, 1, s, 1, s, 1, s, 1, s, pagable);
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
                switch (role) {
                case "admin":
                    return iaRepo
                            .findByCompanyidAndStatusAndVoidstatusAndDateBetweenAndInvstatusAndActivityrefContainingOrCompanyidAndStatusAndVoidstatusAndDateBetweenAndInvstatusAndLabelContainingOrCompanyidAndStatusAndVoidstatusAndDateBetweenAndInvstatusAndItemcodeContainingOrCompanyidAndStatusAndVoidstatusAndDateBetweenAndInvstatusAndVouchercodeContainingOrCompanyidAndStatusAndVoidstatusAndDateBetweenAndInvstatusAndCustomernameContaining(
                                    tokenData.getCompanyid(), 1, voidstatus, fromdate, todate, invstatus, s,
                                    tokenData.getCompanyid(), 1, voidstatus, fromdate, todate, invstatus, s,
                                    tokenData.getCompanyid(), 1, voidstatus, fromdate, todate, invstatus, s,
                                    tokenData.getCompanyid(), 1, voidstatus, fromdate, todate, invstatus, s,
                                    tokenData.getCompanyid(), 1, voidstatus, fromdate, todate, invstatus, s, pagable);
                case "superadmin":
                    return iaRepo
                            .findByStatusAndVoidstatusAndDateBetweenAndInvstatusAndActivityrefContainingOrStatusAndVoidstatusAndDateBetweenAndInvstatusAndLabelContainingOrStatusAndVoidstatusAndDateBetweenAndInvstatusAndItemcodeContainingOrStatusAndVoidstatusAndDateBetweenAndInvstatusAndVouchercodeContainingOrStatusAndVoidstatusAndDateBetweenAndInvstatusAndCustomernameContaining(
                                    1, voidstatus, fromdate, todate, invstatus, s, 1, voidstatus, fromdate, todate,
                                    invstatus, s, 1, voidstatus, fromdate, todate, invstatus, s, 1, voidstatus,
                                    fromdate, todate, invstatus, s, 1, voidstatus, fromdate, todate, invstatus, s,
                                    pagable);
                }
                return iaRepo
                        .findByUseridAndCompanyidAndStatusAndVoidstatusAndDateBetweenAndInvstatusAndActivityrefContainingOrUseridAndCompanyidAndStatusAndVoidstatusAndDateBetweenAndInvstatusAndLabelContainingOrUseridAndCompanyidAndStatusAndVoidstatusAndDateBetweenAndInvstatusAndItemcodeContainingOrUseridAndCompanyidAndStatusAndVoidstatusAndDateBetweenAndInvstatusAndVouchercodeContainingOrUseridAndCompanyidAndStatusAndVoidstatusAndDateBetweenAndInvstatusAndCustomernameContaining(
                                tokenData.getUserid(), tokenData.getCompanyid(), 1, voidstatus, fromdate, todate,
                                invstatus, s, tokenData.getUserid(), tokenData.getCompanyid(), 1, voidstatus, fromdate,
                                todate, invstatus, s, tokenData.getUserid(), tokenData.getCompanyid(), 1, voidstatus,
                                fromdate, todate, invstatus, s, tokenData.getUserid(), tokenData.getCompanyid(), 1,
                                voidstatus, fromdate, todate, invstatus, s, tokenData.getUserid(),
                                tokenData.getCompanyid(), 1, voidstatus, fromdate, todate, invstatus, s, pagable);
            }
            switch (role) {
            case "admin":
                return iaRepo
                        .findByCompanyidAndStatusAndVoidstatusAndDateBetweenAndActivityrefContainingOrCompanyidAndStatusAndVoidstatusAndDateBetweenAndLabelContainingOrCompanyidAndStatusAndVoidstatusAndDateBetweenAndItemcodeContainingOrCompanyidAndStatusAndVoidstatusAndDateBetweenAndVouchercodeContainingOrCompanyidAndStatusAndVoidstatusAndDateBetweenAndCustomernameContainingOrCompanyidAndStatusAndVoidstatusAndDateBetweenAndInvstatusContaining(
                                tokenData.getCompanyid(), 1, voidstatus, fromdate, todate, s, tokenData.getCompanyid(),
                                1, voidstatus, fromdate, todate, s, tokenData.getCompanyid(), 1, voidstatus, fromdate,
                                todate, s, tokenData.getCompanyid(), 1, voidstatus, fromdate, todate, s,
                                tokenData.getCompanyid(), 1, voidstatus, fromdate, todate, s, tokenData.getCompanyid(),
                                1, voidstatus, fromdate, todate, s, pagable);
            case "superadmin":
                return iaRepo
                        .findByStatusAndVoidstatusAndDateBetweenAndActivityrefContainingOrStatusAndVoidstatusAndDateBetweenAndLabelContainingOrStatusAndVoidstatusAndDateBetweenAndItemcodeContainingOrStatusAndVoidstatusAndDateBetweenAndVouchercodeContainingOrStatusAndVoidstatusAndDateBetweenAndCustomernameContainingOrStatusAndVoidstatusAndDateBetweenAndInvstatusContaining(
                                1, voidstatus, fromdate, todate, s, 1, voidstatus, fromdate, todate, s, 1, voidstatus,
                                fromdate, todate, s, 1, voidstatus, fromdate, todate, s, 1, voidstatus, fromdate,
                                todate, s, 1, voidstatus, fromdate, todate, s, pagable);
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
            switch (role) {
            case "admin":
                return iaRepo
                        .findByCompanyidAndStatusAndVoidstatusAndInvstatusAndActivityrefContainingOrCompanyidAndStatusAndVoidstatusAndInvstatusAndLabelContainingOrCompanyidAndStatusAndVoidstatusAndInvstatusAndItemcodeContainingOrCompanyidAndStatusAndVoidstatusAndInvstatusAndVouchercodeContainingOrCompanyidAndStatusAndVoidstatusAndInvstatusAndCustomernameContaining(
                                tokenData.getCompanyid(), 1, voidstatus, invstatus, s, tokenData.getCompanyid(), 1,
                                voidstatus, invstatus, s, tokenData.getCompanyid(), 1, voidstatus, invstatus, s,
                                tokenData.getCompanyid(), 1, voidstatus, invstatus, s, tokenData.getCompanyid(), 1,
                                voidstatus, invstatus, s, pagable);
            case "superadmin":
                return iaRepo
                        .findByStatusAndVoidstatusAndInvstatusAndActivityrefContainingOrStatusAndVoidstatusAndInvstatusAndLabelContainingOrStatusAndVoidstatusAndInvstatusAndItemcodeContainingOrStatusAndVoidstatusAndInvstatusAndVouchercodeContainingOrStatusAndVoidstatusAndInvstatusAndCustomernameContaining(
                                1, voidstatus, invstatus, s, 1, voidstatus, invstatus, s, 1, voidstatus, invstatus, s,
                                1, voidstatus, invstatus, s, 1, voidstatus, invstatus, s, pagable);
            }
            return iaRepo
                    .findByUseridAndCompanyidAndStatusAndVoidstatusAndInvstatusAndActivityrefContainingOrUseridAndCompanyidAndStatusAndVoidstatusAndInvstatusAndLabelContainingOrUseridAndCompanyidAndStatusAndVoidstatusAndInvstatusAndItemcodeContainingOrUseridAndCompanyidAndStatusAndVoidstatusAndInvstatusAndVouchercodeContainingOrUseridAndCompanyidAndStatusAndVoidstatusAndInvstatusAndCustomernameContaining(
                            tokenData.getUserid(), tokenData.getCompanyid(), 1, voidstatus, invstatus, s,
                            tokenData.getUserid(), tokenData.getCompanyid(), 1, voidstatus, invstatus, s,
                            tokenData.getUserid(), tokenData.getCompanyid(), 1, voidstatus, invstatus, s,
                            tokenData.getUserid(), tokenData.getCompanyid(), 1, voidstatus, invstatus, s,
                            tokenData.getUserid(), tokenData.getCompanyid(), 1, voidstatus, invstatus, s, pagable);
        }
        switch (role) {
        case "admin":
            return iaRepo
                    .findByCompanyidAndStatusAndVoidstatusAndActivityrefContainingOrCompanyidAndStatusAndVoidstatusAndLabelContainingOrCompanyidAndStatusAndVoidstatusAndItemcodeContainingOrCompanyidAndStatusAndVoidstatusAndVouchercodeContainingOrCompanyidAndStatusAndVoidstatusAndCustomernameContainingOrCompanyidAndStatusAndVoidstatusAndInvstatusContaining(
                            tokenData.getCompanyid(), 1, voidstatus, s, tokenData.getCompanyid(), 1, voidstatus, s,
                            tokenData.getCompanyid(), 1, voidstatus, s, tokenData.getCompanyid(), 1, voidstatus, s,
                            tokenData.getCompanyid(), 1, voidstatus, s, tokenData.getCompanyid(), 1, voidstatus, s,
                            pagable);
        case "superadmin":
            return iaRepo
                    .findByStatusAndVoidstatusAndActivityrefContainingOrStatusAndVoidstatusAndLabelContainingOrStatusAndVoidstatusAndItemcodeContainingOrStatusAndVoidstatusAndVouchercodeContainingOrStatusAndVoidstatusAndCustomernameContainingOrStatusAndVoidstatusAndInvstatusContaining(
                            1, voidstatus, s, 1, voidstatus, s, 1, voidstatus, s, 1, voidstatus, s, 1, voidstatus, s, 1,
                            voidstatus, s, pagable);
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
        var role = tokenData.getRole();
        var old = getInvActivityByRef(activityref, tokenData);
        if (!old.isPresent()) {
            return false;
        }
        var ia = old.get();
        ia.setVoidstatus(voidstatus);
        iaRepo.save(ia);
        var inv = new Inventory();
        switch (role) {
        case "normaluser":
            inv = iRepo.findByItemrefAndUseridAndCompanyidAndStatus(ia.getItemref(), tokenData.getUserid(),
                    tokenData.getCompanyid(), 1).get();
            break;
        case "admin":
            inv = iRepo.findByItemrefAndCompanyidAndStatus(ia.getItemref(), tokenData.getCompanyid(), 1).get();
            break;
        case "superadmin":
            inv = iRepo.findByItemrefAndStatus(ia.getItemref(), 1).get();
            break;
        }

        // int remaining = getRemaining(ia.getItemref(), tokenData) + inv.getCounts();
        if (voidstatus == 1) {
            inv.setRemaining(inv.getRemaining() + ia.getQty());
        } else {
            inv.setRemaining(inv.getRemaining() - ia.getQty());
        }

        iRepo.save(inv);
        return true;
    }

    public List<InventoryActivity> importInvActivies(List<Map<String, Object>> exceldata, TokenData tokenData)
            throws JsonMappingException, JsonProcessingException {
        List<InventoryActivity> invactivities = new ArrayList<>();
        var role = tokenData.getRole();
        for (var data : exceldata) {
            var now = LocalDateTime.now();
            data.remove("no");
            var qty = (String) data.get("qty");
            if (qty != null && !qty.isEmpty()) {
                data.put("qty", Integer.parseInt(qty.split("\\.")[0]));
            }
            LocalDateTime date = null;
            var datestr = (String) data.get("date");
            if (!datestr.isEmpty() && datestr.contains("/")) {
                var datearr = datestr.split("/");
                if (datearr.length == 3) {
                    var day = Integer.parseInt(datearr[1]);
                    var month = Integer.parseInt(datearr[0]);
                    var year = Integer.parseInt(datearr[2]);

                    date = LocalDateTime.of(year, month, day, 0, 0, 0, 0);
                    data.remove("date");
                }
            }
            var json = new ObjectMapper().writeValueAsString(data);
            var invactivity = new ObjectMapper().readValue(json, InventoryActivity.class);
            invactivity.setItemref(invactivity.getItemref().trim());

            var inventory = new Inventory();
            Optional<Inventory> invopt = Optional.empty();
            switch (role) {
            case "normaluser":
                invopt = iRepo.findByItemrefAndUseridAndCompanyidAndStatus(invactivity.getItemref(),
                        tokenData.getUserid(), tokenData.getCompanyid(), 1);
                break;
            case "admin":
                invopt = iRepo.findByItemrefAndCompanyidAndStatus(invactivity.getItemref(), tokenData.getCompanyid(),
                        1);
                break;
            case "superadmin":
                invopt = iRepo.findByItemrefAndStatus(invactivity.getItemref(), 1);
                break;
            }
            invactivity.setInvstatus(invactivity.getInvstatus().toLowerCase().trim());

            if (!invopt.isPresent() || !List.of("out", "in", "reject").contains(invactivity.getInvstatus())) {
                continue;
            }
            inventory = invopt.get();

            invactivity.setDate(date);

            invactivity.setUserid(tokenData.getUserid());
            invactivity.setUsername(tokenData.getUsername());
            invactivity.setCompanyid(tokenData.getCompanyid());
            invactivity.setCompanyname(tokenData.getCompanyname());
            invactivity.setCreateddate(now);
            invactivity.setModifieddate(now);
            invactivity.setItemcode(inventory.getItemcode());
            invactivity.setLabel(inventory.getLabel());
            invactivity.setPrice(inventory.getPrice());
            invactivity.setAmount(invactivity.getQty() * invactivity.getPrice());
            var newinvactivity = iaRepo.save(invactivity);
            newinvactivity.setActivityref(newinvactivity.getItemref() + "-" + String.valueOf(newinvactivity.getId()));

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
            invactivities.add(iaRepo.save(newinvactivity));
        }
        return invactivities;
    }

}
