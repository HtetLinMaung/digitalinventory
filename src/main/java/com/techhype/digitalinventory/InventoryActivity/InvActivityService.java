package com.techhype.digitalinventory.InventoryActivity;

import java.time.LocalDateTime;
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
    private IInvActivityRepo iaRepo;
    private IInventoryRepository iRepo;

    @Autowired
    public InvActivityService(IInvActivityRepo iaRepo, IInventoryRepository iRepo) {
        this.iaRepo = iaRepo;
        this.iRepo = iRepo;
    }

    public List<InventoryActivity> addInventoryActivities(List<InventoryActivity> list, TokenData tokenData) {
        var newlist = new ArrayList<InventoryActivity>();
        for (var item : list) {
            newlist.add(addInventoryActivity(item, tokenData));
        }
        return newlist;
    }

    public InventoryActivity addInventoryActivity(InventoryActivity inventoryActivity, TokenData tokenData) {
        var role = tokenData.getRole();
        var now = LocalDateTime.now();

        switch (role) {
        case "admin":
            inventoryActivity.setCompanyid(tokenData.getCompanyid());
            inventoryActivity.setCompanyname(tokenData.getCompanyname());
            break;
        case "normaluser":
            inventoryActivity.setCompanyid(tokenData.getCompanyid());
            inventoryActivity.setCompanyname(tokenData.getCompanyname());
            inventoryActivity.setShopid(tokenData.getShopid());
            inventoryActivity.setShopname(tokenData.getShopname());
            break;
        }

        inventoryActivity.setId(null);
        inventoryActivity.setCreateddate(now);
        inventoryActivity.setModifieddate(now);
        inventoryActivity.setUserid(tokenData.getUserid());
        inventoryActivity.setUsername(tokenData.getUsername());
        inventoryActivity.setAmount(inventoryActivity.getQty() * inventoryActivity.getPrice());
        ;
        var newinvactivity = iaRepo.save(inventoryActivity);
        newinvactivity.setActivityref(newinvactivity.getItemref() + "-" + String.valueOf(newinvactivity.getId()));
        var inventory = new Inventory();
        switch (role) {
        case "normaluser":
            inventory = iRepo.findByItemrefAndShopidAndCompanyidAndStatus(newinvactivity.getItemref(),
                    tokenData.getShopid(), tokenData.getCompanyid(), 1).get();
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
            inventory = iRepo.findByItemrefAndShopidAndCompanyidAndStatus(itemref, tokenData.getShopid(),
                    tokenData.getCompanyid(), 1).get();
            // iaRepo.findInvActivityTotalsByCompanyidInvstatusSearch(companyid, invstatus,
            // search)
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
        return iaRepo.findByShopidAndCompanyidAndStatusAndActivityref(tokenData.getShopid(), tokenData.getCompanyid(),
                1, activityref);
    }

    public IAFilterTotalDto getInvActiviesTotal(String search, int page, int perpage, String sortby, String reverse,
            int voidstatus, LocalDateTime fromdate, LocalDateTime todate, String invstatus, TokenData tokenData) {
        var role = tokenData.getRole();
        var companyid = tokenData.getCompanyid();
        var shopid = tokenData.getShopid();
        var userid = tokenData.getUserid();
        if (search == null || search.isEmpty()) {
            if (voidstatus == 2) {
                if (fromdate != null && todate != null) {
                    if (!invstatus.equals("all")) {
                        switch (role) {
                        case "admin":
                            iaRepo.findInvActivityTotalsByCompanyidInvstatusDateBetween(companyid, invstatus, fromdate,
                                    todate).get(0);
                        case "superadmin":
                            return iaRepo.findInvActivityTotalsByInvstatusDateBetween(invstatus, fromdate, todate)
                                    .get(0);
                        }
                        return iaRepo.findInvActivityTotalsByShopidCompanyidInvstatusDateBetween(shopid, companyid,
                                invstatus, fromdate, todate).get(0);
                    }
                    switch (role) {
                    case "admin":
                        return iaRepo.findInvActivityTotalsByCompanyidDateBetween(companyid, fromdate, todate).get(0);
                    case "superadmin":
                        return iaRepo.findInvActivityTotalsByDateBetween(fromdate, todate).get(0);
                    }
                    return iaRepo.findInvActivityTotalsByShopidCompanyidDateBetween(shopid, companyid, fromdate, todate)
                            .get(0);
                }
                if (!invstatus.equals("all")) {
                    switch (role) {
                    case "admin":
                        return iaRepo.findInvActivityTotalsByCompanyidInvstatus(companyid, invstatus).get(0);
                    case "superadmin":
                        return iaRepo.findInvActivityTotalsByInvstatus(invstatus).get(0);
                    }
                    return iaRepo.findInvActivityTotalsByShopidCompanyidInvstatus(shopid, companyid, invstatus).get(0);
                }
                switch (role) {
                case "admin":
                    return iaRepo.findInvActivityTotalsByCompanyid(companyid).get(0);
                case "superadmin":
                    return iaRepo.getTotals().get(0);
                }
                return iaRepo.findInvActivityTotalsByShopidCompanyid(shopid, companyid).get(0);
            }
            if (fromdate != null && todate != null) {
                if (!invstatus.equals("all")) {
                    switch (role) {
                    case "admin":
                        return iaRepo.findInvActivityTotalsByCompanyidVoidstatusInvstatusDateBetween(companyid,
                                voidstatus, invstatus, fromdate, todate).get(0);
                    case "superadmin":
                        return iaRepo.findInvActivityTotalsByVoidstatusInvstatusDateBetween(voidstatus, invstatus,
                                fromdate, todate).get(0);
                    }
                    return iaRepo.findInvActivityTotalsByShopidCompanyidVoidstatusInvstatusDateBetween(shopid,
                            companyid, voidstatus, invstatus, fromdate, todate).get(0);
                }
                switch (role) {
                case "admin":
                    return iaRepo.findInvActivityTotalsByCompanyidVoidstatusDateBetween(companyid, voidstatus, fromdate,
                            todate).get(0);
                case "superadmin":
                    return iaRepo.findInvActivityTotalsByVoidstatusDateBetween(voidstatus, fromdate, todate).get(0);
                }
                return iaRepo.findInvActivityTotalsByShopidCompanyidVoidstatusDateBetween(shopid, companyid, voidstatus,
                        fromdate, todate).get(0);
            }

            if (!invstatus.equals("all")) {
                switch (role) {
                case "admin":
                    return iaRepo.findInvActivityTotalsByCompanyidVoidstatusInvstatus(companyid, voidstatus, invstatus)
                            .get(0);
                case "superadmin":
                    return iaRepo.findInvActivityTotalsByVoidstatusInvstatus(voidstatus, invstatus).get(0);
                }
                return iaRepo.findInvActivityTotalsByShopidCompanyidVoidstatusInvstatus(shopid, companyid, voidstatus,
                        invstatus).get(0);
            }
            switch (role) {
            case "admin":
                return iaRepo.findInvActivityTotalsByCompanyidVoidstatus(companyid, voidstatus).get(0);
            case "superadmin":
                return iaRepo.findInvActivityTotalsByVoidstatus(voidstatus).get(0);
            }
            return iaRepo.findInvActivityTotalsByShopidCompanyidVoidstatus(shopid, companyid, voidstatus).get(0);
        }

        String s = search.trim();
        if (s.startsWith("\"") && s.endsWith("\"")) {
            s = s.replaceAll("\"", "");
            if (voidstatus == 2) {
                if (fromdate != null && todate != null) {
                    if (!invstatus.equals("all")) {
                        switch (role) {
                        case "admin":
                            return iaRepo.findInvActivityTotalsByCompanyidInvstatusDateBetweenSearch(companyid,
                                    invstatus, fromdate, todate, s).get(0);
                        case "superadmin":
                            return iaRepo
                                    .findInvActivityTotalsByInvstatusDateBetweenSearch(invstatus, fromdate, todate, s)
                                    .get(0);
                        }
                        return iaRepo.findInvActivityTotalsByShopidCompanyidInvstatusDateBetweenSearch(shopid,
                                companyid, invstatus, fromdate, todate, s).get(0);
                    }
                    switch (role) {
                    case "admin":
                        return iaRepo.findInvActivityTotalsByCompanyidDateBetweenSearch(companyid, fromdate, todate, s)
                                .get(0);
                    case "superadmin":
                        return iaRepo.findInvActivityTotalsByDateBetweenSearch(fromdate, todate, s).get(0);
                    }
                    return iaRepo.findInvActivityTotalsByShopidCompanyidDateBetweenSearch(shopid, companyid, fromdate,
                            todate, s).get(0);
                }
                if (!invstatus.equals("all")) {
                    switch (role) {
                    case "admin":
                        return iaRepo.findInvActivityTotalsByCompanyidInvstatusSearch(companyid, invstatus, s).get(0);
                    case "superadmin":
                        return iaRepo.findInvActivityTotalsByInvstatusSearch(invstatus, s).get(0);
                    }
                    return iaRepo.findInvActivityTotalsByShopidCompanyidInvstatusSearch(shopid, companyid, invstatus, s)
                            .get(0);
                }
                switch (role) {
                case "admin":
                    return iaRepo.findInvActivityTotalsByCompanyidSearch(companyid, s).get(0);
                case "superadmin":
                    return iaRepo.findInvActivityTotalsBySearch(s).get(0);
                }
                return iaRepo.findInvActivityTotalsByShopidCompanyidSearch(shopid, companyid, s).get(0);
            }
            if (fromdate != null && todate != null) {
                if (!invstatus.equals("all")) {
                    switch (role) {
                    case "admin":
                        return iaRepo.findInvActivityTotalsByCompanyidVoidstatusInvstatusDateBetweenSearch(companyid,
                                voidstatus, invstatus, fromdate, todate, s).get(0);
                    case "superadmin":
                        return iaRepo.findInvActivityTotalsByVoidstatusInvstatusDateBetweenSearch(voidstatus, invstatus,
                                fromdate, todate, s).get(0);
                    }
                    return iaRepo.findInvActivityTotalsByShopidCompanyidVoidstatusInvstatusDateBetweenSearch(shopid,
                            companyid, voidstatus, invstatus, fromdate, todate, s).get(0);
                }
                switch (role) {
                case "admin":
                    return iaRepo.findInvActivityTotalsByCompanyidVoidstatusDateBetweenSearch(companyid, voidstatus,
                            fromdate, todate, s).get(0);
                case "superadmin":
                    return iaRepo.findInvActivityTotalsByVoidstatusDateBetweenSearch(voidstatus, fromdate, todate, s)
                            .get(0);
                }
                return iaRepo.findInvActivityTotalsByShopidCompanyidVoidstatusDateBetweenSearch(shopid, companyid,
                        voidstatus, fromdate, todate, s).get(0);
            }
            if (!invstatus.equals("all")) {
                switch (role) {
                case "admin":
                    return iaRepo.findInvActivityTotalsByCompanyidVoidstatusInvstatusSearch(companyid, voidstatus,
                            invstatus, s).get(0);
                case "superadmin":
                    return iaRepo.findInvActivityTotalsByVoidstatusInvstatusSearch(voidstatus, invstatus, s).get(0);
                }
                return iaRepo.findInvActivityTotalsByShopidCompanyidVoidstatusInvstatusSearch(shopid, companyid,
                        voidstatus, invstatus, s).get(0);
            }
            switch (role) {
            case "admin":
                return iaRepo.findInvActivityTotalsByCompanyidVoidstatusSearch(companyid, voidstatus, s).get(0);
            case "superadmin":
                return iaRepo.findInvActivityTotalsByVoidstatusSearch(voidstatus, s).get(0);
            }
            return iaRepo.findInvActivityTotalsByShopidCompanyidVoidstatusSearch(shopid, companyid, voidstatus, s)
                    .get(0);
        }
        if (voidstatus == 2) {
            if (fromdate != null & todate != null) {
                if (!invstatus.equals("all")) {
                    switch (role) {
                    case "admin":
                        return iaRepo.findInvActivityTotalsByCompanyidInvstatusDateBetweenSearchContain(companyid,
                                invstatus, fromdate, todate, s).get(0);
                    case "superadmin":
                        return iaRepo.findInvActivityTotalsByInvstatusDateBetweenSearchContain(invstatus, fromdate,
                                todate, s).get(0);
                    }
                    return iaRepo.findInvActivityTotalsByShopidCompanyidInvstatusDateBetweenSearchContain(shopid,
                            companyid, invstatus, fromdate, todate, s).get(0);
                }
                switch (role) {
                case "admin":
                    return iaRepo
                            .findInvActivityTotalsByCompanyidDateBetweenSearchContain(companyid, fromdate, todate, s)
                            .get(0);
                case "superadmin":
                    return iaRepo.findInvActivityTotalsByDateBetweenSearchContain(fromdate, todate, s).get(0);
                }
                return iaRepo.findInvActivityTotalsByShopidCompanyidDateBetweenSearchContain(shopid, companyid,
                        fromdate, todate, s).get(0);
            }
            if (!invstatus.equals("all")) {
                switch (role) {
                case "admin":
                    return iaRepo.findInvActivityTotalsByCompanyidInvstatusSearchContain(companyid, invstatus, s)
                            .get(0);
                case "superadmin":
                    return iaRepo.findInvActivityTotalsByInvstatusSearchContain(invstatus, s).get(0);
                }
                return iaRepo
                        .findInvActivityTotalsByShopidCompanyidInvstatusSearchContain(shopid, companyid, invstatus, s)
                        .get(0);
            }
            switch (role) {
            case "admin":
                return iaRepo.findInvActivityTotalsByCompanyidSearchContain(companyid, s).get(0);
            case "superadmin":
                return iaRepo.findInvActivityTotalsBySearchContain(s).get(0);
            }
            return iaRepo.findInvActivityTotalsByShopidCompanyidSearchContain(shopid, companyid, s).get(0);
        }
        if (fromdate != null && todate != null) {
            if (!invstatus.equals("all")) {
                switch (role) {
                case "admin":
                    return iaRepo.findInvActivityTotalsByCompanyidVoidstatusInvstatusDateBetweenSearchContain(companyid,
                            voidstatus, invstatus, fromdate, todate, s).get(0);
                case "superadmin":
                    return iaRepo.findInvActivityTotalsByVoidstatusInvstatusDateBetweenSearchContain(voidstatus,
                            invstatus, fromdate, todate, s).get(0);
                }
                return iaRepo.findInvActivityTotalsByShopidCompanyidVoidstatusInvstatusDateBetweenSearchContain(shopid,
                        companyid, voidstatus, invstatus, fromdate, todate, s).get(0);
            }
            switch (role) {
            case "admin":
                return iaRepo.findInvActivityTotalsByCompanyidVoidstatusDateBetweenSearchContain(companyid, voidstatus,
                        fromdate, todate, s).get(0);
            case "superadmin":
                return iaRepo.findInvActivityTotalsByVoidstatusDateBetweenSearchContain(voidstatus, fromdate, todate, s)
                        .get(0);
            }
            return iaRepo.findInvActivityTotalsByShopidCompanyidVoidstatusDateBetweenSearchContain(shopid, companyid,
                    voidstatus, fromdate, todate, s).get(0);
        }
        if (!invstatus.equals("all")) {
            switch (role) {
            case "admin":
                return iaRepo.findInvActivityTotalsByCompanyidVoidstatusInvstatusSearchContain(companyid, voidstatus,
                        invstatus, s).get(0);
            case "superadmin":
                return iaRepo.findInvActivityTotalsByVoidstatusInvstatusSearchContain(voidstatus, invstatus, s).get(0);
            }
            return iaRepo.findInvActivityTotalsByShopidCompanyidVoidstatusInvstatusSearchContain(shopid, companyid,
                    voidstatus, invstatus, s).get(0);
        }
        switch (role) {
        case "admin":
            return iaRepo.findInvActivityTotalsByCompanyidVoidstatusSearchContain(companyid, voidstatus, s).get(0);
        case "superadmin":
            return iaRepo.findInvActivityTotalsByVoidstatusSearchContain(voidstatus, s).get(0);
        }
        return iaRepo.findInvActivityTotalsByShopidCompanyidVoidstatusSearchContain(shopid, companyid, voidstatus, s)
                .get(0);
    }

    public Page<InventoryActivity> getInvActivities(String search, int page, int perpage, String sortby, String reverse,
            int voidstatus, LocalDateTime fromdate, LocalDateTime todate, String invstatus, String cid, String sid,
            String uid, TokenData tokenData) {
        var role = tokenData.getRole();
        var shopid = tokenData.getShopid();
        var companyid = tokenData.getCompanyid();
        var userid = tokenData.getUserid();
        var pageable = PageRequest.of(page - 1, perpage,
                Sort.by(reverse.equals("1") ? Sort.Direction.DESC : Sort.Direction.ASC, sortby));
        if (search == null || search.isEmpty()) {
            if (voidstatus == 2) {
                if (fromdate != null && todate != null) {
                    if (!invstatus.equals("all")) {
                        switch (role) {
                        case "admin":
                            if (!sid.equals("all") && !uid.equals("all")) {
                                return iaRepo.findInvActivityByUseridShopidCompanyidInvstatusDateBetween(uid, sid,
                                        companyid, invstatus, fromdate, todate, pageable);
                            }
                            if (!sid.equals("all")) {
                                return iaRepo.findInvActivityByShopidCompanyidInvstatusDateBetween(sid, companyid,
                                        invstatus, fromdate, todate, pageable);
                            }
                            return iaRepo.findInvActivityByCompanyidInvstatusDateBetween(companyid, invstatus, fromdate,
                                    todate, pageable);
                        case "superadmin":
                            if (!cid.equals("all") && !sid.equals("all") && !uid.equals("all")) {
                                return iaRepo.findInvActivityByUseridShopidCompanyidInvstatusDateBetween(uid, sid, cid,
                                        invstatus, fromdate, todate, pageable);
                            }
                            if (!cid.equals("all") && !sid.equals("all")) {
                                return iaRepo.findInvActivityByShopidCompanyidInvstatusDateBetween(sid, cid, invstatus,
                                        fromdate, todate, pageable);
                            }
                            if (!cid.equals("all")) {
                                return iaRepo.findInvActivityByCompanyidInvstatusDateBetween(cid, invstatus, fromdate,
                                        todate, pageable);
                            }
                            return iaRepo.findInvActivityByInvstatusDateBetween(invstatus, fromdate, todate, pageable);
                        }
                        return iaRepo.findInvActivityByShopidCompanyidInvstatusDateBetween(shopid, companyid, invstatus,
                                fromdate, todate, pageable);
                    }
                    switch (role) {
                    case "admin":
                        if (!sid.equals("all") && !uid.equals("all")) {
                            return iaRepo.findInvActivityByUseridShopidCompanyidDateBetween(uid, sid, companyid,
                                    fromdate, todate, pageable);
                        }
                        if (!sid.equals("all")) {
                            return iaRepo.findInvActivityByUseridCompanyidDateBetween(uid, cid, fromdate, todate,
                                    pageable);
                        }
                        return iaRepo.findInvActivityByCompanyidDateBetween(companyid, fromdate, todate, pageable);
                    case "superadmin":
                        if (!cid.equals("all") && !sid.equals("all") && !uid.equals("all")) {
                            return iaRepo.findInvActivityByUseridShopidCompanyidDateBetween(uid, sid, cid, fromdate,
                                    todate, pageable);
                        }
                        if (!cid.equals("all") && !sid.equals("all")) {
                            return iaRepo.findInvActivityByShopidCompanyidDateBetween(sid, cid, fromdate, todate,
                                    pageable);
                        }
                        if (!cid.equals("all")) {
                            return iaRepo.findInvActivityByCompanyidDateBetween(cid, fromdate, todate, pageable);
                        }
                        return iaRepo.findInvActivityByDateBetween(fromdate, todate, pageable);
                    }
                    return iaRepo.findInvActivityByShopidCompanyidDateBetween(shopid, companyid, fromdate, todate,
                            pageable);
                }
                if (!invstatus.equals("all")) {
                    switch (role) {
                    case "admin":
                        if (!sid.equals("all") && !uid.equals("all")) {
                            return iaRepo.findInvActivityByUseridShopidCompanyidInvstatus(uid, sid, companyid,
                                    invstatus, pageable);
                        }
                        if (!sid.equals("all")) {
                            return iaRepo.findInvActivityByShopidCompanyidInvstatus(sid, companyid, invstatus,
                                    pageable);
                        }
                        return iaRepo.findInvActivityByCompanyidInvstatus(companyid, invstatus, pageable);
                    case "superadmin":
                        if (!cid.equals("all") && !sid.equals("all") && !uid.equals("all")) {
                            return iaRepo.findInvActivityByUseridShopidCompanyidInvstatus(uid, sid, cid, invstatus,
                                    pageable);
                        }
                        if (!cid.equals("all") && !sid.equals("all")) {
                            return iaRepo.findInvActivityByShopidCompanyidInvstatus(sid, cid, invstatus, pageable);
                        }
                        if (!cid.equals("all")) {
                            return iaRepo.findInvActivityByCompanyidInvstatus(cid, invstatus, pageable);
                        }
                        return iaRepo.findInvActivityByInvstatus(invstatus, pageable);
                    }
                    return iaRepo.findInvActivityByShopidCompanyidInvstatus(shopid, companyid, invstatus, pageable);
                }
                switch (role) {
                case "admin":
                    if (!sid.equals("all") && !uid.equals("all")) {
                        return iaRepo.findInvActivityByUseridShopidCompanyid(uid, sid, companyid, pageable);
                    }
                    if (!sid.equals("all")) {
                        return iaRepo.findInvActivityByShopidCompanyid(sid, companyid, pageable);
                    }
                    return iaRepo.findInvActivityByCompanyid(companyid, pageable);
                case "superadmin":
                    if (!cid.equals("all") && !sid.equals("all") && !uid.equals("all")) {
                        return iaRepo.findInvActivityByUseridShopidCompanyid(uid, sid, cid, pageable);
                    }
                    if (!cid.equals("all") && !sid.equals("all")) {
                        return iaRepo.findInvActivityByShopidCompanyid(sid, cid, pageable);
                    }
                    if (!cid.equals("all")) {
                        return iaRepo.findInvActivityByCompanyid(cid, pageable);
                    }
                    return iaRepo.findByStatus(1, pageable);
                }
                return iaRepo.findInvActivityByShopidCompanyid(shopid, companyid, pageable);
            }
            if (fromdate != null && todate != null) {
                if (!invstatus.equals("all")) {
                    switch (role) {
                    case "admin":
                        if (!sid.equals("all") && !uid.equals("all")) {
                            return iaRepo.findInvActivityByUseridShopidCompanyidVoidstatusInvstatusDateBetween(uid, sid,
                                    companyid, voidstatus, invstatus, fromdate, todate, pageable);
                        }
                        if (!sid.equals("all")) {
                            return iaRepo.findInvActivityByShopidCompanyidVoidstatusInvstatusDateBetween(sid, companyid,
                                    voidstatus, invstatus, fromdate, todate, pageable);
                        }
                        return iaRepo.findInvActivityByCompanyidVoidstatusInvstatusDateBetween(companyid, voidstatus,
                                invstatus, fromdate, todate, pageable);

                    case "superadmin":
                        if (!cid.equals("all") && !sid.equals("all") && !uid.equals("all")) {
                            return iaRepo.findInvActivityByUseridShopidCompanyidVoidstatusInvstatusDateBetween(uid, sid,
                                    cid, voidstatus, invstatus, fromdate, todate, pageable);
                        }
                        if (!cid.equals("all") && !sid.equals("all")) {
                            return iaRepo.findInvActivityByShopidCompanyidVoidstatusInvstatusDateBetween(sid, cid,
                                    voidstatus, invstatus, fromdate, todate, pageable);
                        }
                        if (!cid.equals("all")) {
                            return iaRepo.findInvActivityByCompanyidVoidstatusInvstatusDateBetween(cid, voidstatus,
                                    invstatus, fromdate, todate, pageable);
                        }
                        return iaRepo.findInvActivityByVoidstatusInvstatusDateBetween(voidstatus, invstatus, fromdate,
                                todate, pageable);
                    }
                    return iaRepo.findInvActivityByShopidCompanyidVoidstatusInvstatusDateBetween(shopid, companyid,
                            voidstatus, invstatus, fromdate, todate, pageable);
                }
                switch (role) {
                case "admin":
                    if (!sid.equals("all") && !uid.equals("all")) {
                        return iaRepo.findInvActivityByUseridShopidCompanyidVoidstatusDateBetween(uid, sid, companyid,
                                voidstatus, fromdate, todate, pageable);
                    }
                    if (!sid.equals("all")) {
                        return iaRepo.findInvActivityByShopidCompanyidVoidstatusDateBetween(sid, companyid, voidstatus,
                                fromdate, todate, pageable);
                    }
                    return iaRepo.findInvActivityByCompanyidVoidstatusDateBetween(companyid, voidstatus, fromdate,
                            todate, pageable);
                case "superadmin":
                    if (!cid.equals("all") && !sid.equals("all") && !uid.equals("all")) {
                        return iaRepo.findInvActivityByUseridShopidCompanyidVoidstatusDateBetween(uid, sid, cid,
                                voidstatus, fromdate, todate, pageable);
                    }
                    if (!cid.equals("all") && !sid.equals("all")) {
                        return iaRepo.findInvActivityByShopidCompanyidVoidstatusDateBetween(sid, cid, voidstatus,
                                fromdate, todate, pageable);
                    }
                    if (!cid.equals("all")) {
                        return iaRepo.findInvActivityByCompanyidVoidstatusDateBetween(cid, voidstatus, fromdate, todate,
                                pageable);
                    }
                    return iaRepo.findInvActivityByVoidstatusDateBetween(voidstatus, fromdate, todate, pageable);
                }
                return iaRepo.findInvActivityByShopidCompanyidVoidstatusDateBetween(shopid, companyid, voidstatus,
                        fromdate, todate, pageable);
            }

            if (!invstatus.equals("all")) {
                switch (role) {
                case "admin":
                    if (!sid.equals("all") && !uid.equals("all")) {
                        return iaRepo.findInvActivityByUseridShopidCompanyidVoidstatusInvstatus(uid, sid, companyid,
                                voidstatus, invstatus, pageable);
                    }
                    if (!sid.equals("all")) {
                        return iaRepo.findInvActivityByShopidCompanyidVoidstatusInvstatus(sid, companyid, voidstatus,
                                invstatus, pageable);
                    }
                    return iaRepo.findInvActivityByCompanyidVoidstatusInvstatus(companyid, voidstatus, invstatus,
                            pageable);
                case "superadmin":
                    if (!cid.equals("all") && !sid.equals("all") && !uid.equals("all")) {
                        return iaRepo.findInvActivityByUseridShopidCompanyidVoidstatusInvstatus(uid, sid, cid,
                                voidstatus, invstatus, pageable);
                    }
                    if (!cid.equals("all") && !sid.equals("all")) {
                        return iaRepo.findInvActivityByShopidCompanyidVoidstatusInvstatus(sid, cid, voidstatus,
                                invstatus, pageable);
                    }
                    if (!cid.equals("all")) {
                        return iaRepo.findInvActivityByCompanyidVoidstatusInvstatus(cid, voidstatus, invstatus,
                                pageable);
                    }
                    return iaRepo.findInvActivityByVoidstatusInvstatus(voidstatus, invstatus, pageable);
                }
                return iaRepo.findInvActivityByShopidCompanyidVoidstatusInvstatus(shopid, companyid, voidstatus,
                        invstatus, pageable);
            }
            switch (role) {
            case "admin":
                if (!sid.equals("all") && !uid.equals("all")) {
                    return iaRepo.findInvActivityByUseridShopidCompanyidVoidstatus(uid, sid, companyid, voidstatus,
                            pageable);
                }
                if (!sid.equals("all")) {
                    return iaRepo.findInvActivityByShopidCompanyidVoidstatus(sid, companyid, voidstatus, pageable);
                }
                return iaRepo.findInvActivityByCompanyidVoidstatus(companyid, voidstatus, pageable);
            case "superadmin":
                if (!cid.equals("all") && !sid.equals("all") && !uid.equals("all")) {
                    return iaRepo.findInvActivityByUseridShopidCompanyidVoidstatus(uid, sid, cid, voidstatus, pageable);
                }
                if (!cid.equals("all") && !sid.equals("all")) {
                    return iaRepo.findInvActivityByShopidCompanyidVoidstatus(sid, cid, voidstatus, pageable);
                }
                if (!cid.equals("all")) {
                    return iaRepo.findInvActivityByCompanyidVoidstatus(cid, voidstatus, pageable);
                }
                return iaRepo.findInvActivityByVoidstatus(voidstatus, pageable);
            }
            return iaRepo.findInvActivityByShopidCompanyidVoidstatus(shopid, companyid, voidstatus, pageable);
        }

        String s = search.trim();
        if (s.startsWith("\"") && s.endsWith("\"")) {
            s = s.replaceAll("\"", "");
            if (voidstatus == 2) {
                if (fromdate != null && todate != null) {
                    if (!invstatus.equals("all")) {
                        switch (role) {
                        case "admin":
                            if (!sid.equals("all") && !uid.equals("all")) {
                                return iaRepo.findInvActivityByUseridShopidCompanyidInvstatusDateBetweenSearch(uid, sid,
                                        companyid, invstatus, fromdate, todate, s, pageable);
                            }
                            if (!sid.equals("all")) {
                                return iaRepo.findInvActivityByShopidCompanyidInvstatusDateBetweenSearch(sid, companyid,
                                        invstatus, fromdate, todate, s, pageable);
                            }
                            return iaRepo.findInvActivityByCompanyidInvstatusDateBetweenSearch(companyid, invstatus,
                                    fromdate, todate, s, pageable);
                        case "superadmin":
                            if (!cid.equals("all") && !sid.equals("all") && !uid.equals("all")) {
                                return iaRepo.findInvActivityByUseridShopidCompanyidInvstatusDateBetweenSearch(uid, sid,
                                        cid, invstatus, fromdate, todate, s, pageable);
                            }
                            if (!cid.equals("all") && !sid.equals("all")) {
                                return iaRepo.findInvActivityByShopidCompanyidInvstatusDateBetweenSearch(sid, cid,
                                        invstatus, fromdate, todate, s, pageable);
                            }
                            if (!cid.equals("all")) {
                                return iaRepo.findInvActivityByCompanyidInvstatusDateBetweenSearch(cid, invstatus,
                                        fromdate, todate, s, pageable);
                            }
                            return iaRepo.findInvActivityByInvstatusDateBetweenSearch(invstatus, fromdate, todate, s,
                                    pageable);
                        }
                        return iaRepo.findInvActivityByShopidCompanyidInvstatusDateBetweenSearch(shopid, companyid,
                                invstatus, fromdate, todate, s, pageable);
                    }
                    switch (role) {
                    case "admin":
                        if (!sid.equals("all") && !uid.equals("all")) {
                            return iaRepo.findInvActivityByUseridShopidCompanyidDateBetweenSearch(uid, sid, companyid,
                                    fromdate, todate, s, pageable);
                        }
                        if (!sid.equals("all")) {
                            return iaRepo.findInvActivityByShopidCompanyidDateBetweenSearch(sid, companyid, fromdate,
                                    todate, s, pageable);
                        }
                        return iaRepo.findInvActivityByCompanyidDateBetweenSearch(companyid, fromdate, todate, s,
                                pageable);
                    case "superadmin":
                        if (!cid.equals("all") && !sid.equals("all") && !uid.equals("all")) {
                            return iaRepo.findInvActivityByUseridShopidCompanyidDateBetweenSearch(uid, sid, cid,
                                    fromdate, todate, s, pageable);
                        }
                        if (!cid.equals("all") && !sid.equals("all")) {
                            return iaRepo.findInvActivityByShopidCompanyidDateBetweenSearch(sid, cid, fromdate, todate,
                                    s, pageable);
                        }
                        if (!cid.equals("all")) {
                            return iaRepo.findInvActivityByCompanyidDateBetweenSearch(cid, fromdate, todate, s,
                                    pageable);
                        }
                        return iaRepo.findInvActivityByDateBetweenSearch(fromdate, todate, s, pageable);
                    }
                    return iaRepo.findInvActivityByShopidCompanyidDateBetweenSearch(shopid, companyid, fromdate, todate,
                            s, pageable);
                }
                if (!invstatus.equals("all")) {
                    switch (role) {
                    case "admin":
                        if (!sid.equals("all") && !uid.equals("all")) {
                            return iaRepo.findInvActivityByUseridShopidCompanyidInvstatusSearch(uid, sid, companyid,
                                    invstatus, s, pageable);
                        }
                        if (!sid.equals("all")) {
                            return iaRepo.findInvActivityByShopidCompanyidInvstatusSearch(sid, companyid, invstatus, s,
                                    pageable);
                        }
                        return iaRepo.findInvActivityByCompanyidInvstatusSearch(companyid, invstatus, s, pageable);
                    case "superadmin":
                        if (!cid.equals("all") && !sid.equals("all") && !uid.equals("all")) {
                            return iaRepo.findInvActivityByUseridShopidCompanyidInvstatusSearch(uid, sid, cid,
                                    invstatus, s, pageable);
                        }
                        if (!cid.equals("all") && !sid.equals("all")) {
                            return iaRepo.findInvActivityByShopidCompanyidInvstatusSearch(sid, cid, invstatus, s,
                                    pageable);
                        }
                        if (!cid.equals("all")) {
                            return iaRepo.findInvActivityByCompanyidInvstatusSearch(cid, invstatus, s, pageable);
                        }
                        return iaRepo.findInvActivityByInvstatusSearch(invstatus, s, pageable);
                    }
                    return iaRepo.findInvActivityByShopidCompanyidInvstatusSearch(shopid, companyid, invstatus, s,
                            pageable);
                }
                switch (role) {
                case "admin":
                    if (!sid.equals("all") && !uid.equals("all")) {
                        return iaRepo.findInvActivityByUseridShopidCompanyidSearch(uid, sid, companyid, s, pageable);
                    }
                    if (!sid.equals("all")) {
                        return iaRepo.findInvActivityByShopidCompanyidSearch(sid, companyid, s, pageable);
                    }
                    return iaRepo.findInvActivityByCompanyidSearch(companyid, s, pageable);
                case "superadmin":
                    if (!cid.equals("all") && !sid.equals("all") && !uid.equals("all")) {
                        return iaRepo.findInvActivityByUseridShopidCompanyidSearch(uid, sid, cid, s, pageable);
                    }
                    if (!cid.equals("all") && !sid.equals("all")) {
                        return iaRepo.findInvActivityByShopidCompanyidSearch(sid, cid, s, pageable);
                    }
                    if (!cid.equals("all")) {
                        return iaRepo.findInvActivityByCompanyidSearch(cid, s, pageable);
                    }
                    return iaRepo.findInvActivityBySearch(s, pageable);
                }
                return iaRepo.findInvActivityByShopidCompanyidSearch(shopid, companyid, s, pageable);
            }
            if (fromdate != null && todate != null) {
                if (!invstatus.equals("all")) {
                    switch (role) {
                    case "admin":
                        if (!sid.equals("all") && !uid.equals("all")) {
                            return iaRepo.findInvActivityByUseridShopidCompanyidVoidstatusInvstatusDateBetweenSearch(
                                    uid, sid, companyid, voidstatus, invstatus, fromdate, todate, s, pageable);
                        }
                        if (!sid.equals("all")) {
                            return iaRepo.findInvActivityByShopidCompanyidVoidstatusInvstatusDateBetweenSearch(sid,
                                    companyid, voidstatus, invstatus, fromdate, todate, s, pageable);
                        }
                        return iaRepo.findInvActivityByCompanyidVoidstatusInvstatusDateBetweenSearch(companyid,
                                voidstatus, invstatus, fromdate, todate, s, pageable);
                    case "superadmin":
                        if (!cid.equals("all") && !sid.equals("all") && !uid.equals("all")) {
                            return iaRepo.findInvActivityByUseridShopidCompanyidVoidstatusInvstatusDateBetweenSearch(
                                    uid, sid, cid, voidstatus, invstatus, fromdate, todate, s, pageable);
                        }
                        if (!cid.equals("all") && !sid.equals("all")) {
                            return iaRepo.findInvActivityByShopidCompanyidVoidstatusInvstatusDateBetweenSearch(sid, cid,
                                    voidstatus, invstatus, fromdate, todate, s, pageable);
                        }
                        if (!cid.equals("all")) {
                            return iaRepo.findInvActivityByCompanyidVoidstatusInvstatusDateBetweenSearch(cid,
                                    voidstatus, invstatus, fromdate, todate, s, pageable);
                        }
                        return iaRepo.findInvActivityByVoidstatusInvstatusDateBetweenSearch(voidstatus, invstatus,
                                fromdate, todate, s, pageable);
                    }
                    return iaRepo.findInvActivityByShopidCompanyidVoidstatusInvstatusDateBetweenSearch(shopid,
                            companyid, voidstatus, invstatus, fromdate, todate, s, pageable);
                }
                switch (role) {
                case "admin":
                    if (!sid.equals("all") && !uid.equals("all")) {
                        return iaRepo.findInvActivityByUseridShopidCompanyidVoidstatusDateBetweenSearch(uid, sid, cid,
                                voidstatus, fromdate, todate, s, pageable);
                    }
                    if (!sid.equals("all")) {
                        return iaRepo.findInvActivityByShopidCompanyidVoidstatusDateBetweenSearch(sid, companyid,
                                voidstatus, fromdate, todate, s, pageable);
                    }
                    return iaRepo.findInvActivityByCompanyidVoidstatusDateBetweenSearch(companyid, voidstatus, fromdate,
                            todate, s, pageable);
                case "superadmin":
                    if (!cid.equals("all") && !sid.equals("all") && !uid.equals("all")) {
                        return iaRepo.findInvActivityByUseridShopidCompanyidVoidstatusDateBetweenSearch(uid, sid, cid,
                                voidstatus, fromdate, todate, s, pageable);
                    }
                    if (!cid.equals("all") && !sid.equals("all")) {
                        return iaRepo.findInvActivityByShopidCompanyidVoidstatusDateBetweenSearch(sid, cid, voidstatus,
                                fromdate, todate, s, pageable);
                    }
                    if (!cid.equals("all")) {
                        return iaRepo.findInvActivityByCompanyidVoidstatusDateBetweenSearch(cid, voidstatus, fromdate,
                                todate, s, pageable);
                    }
                    return iaRepo.findInvActivityByVoidstatusDateBetweenSearch(voidstatus, fromdate, todate, s,
                            pageable);
                }
                return iaRepo.findInvActivityByShopidCompanyidVoidstatusDateBetweenSearch(shopid, companyid, voidstatus,
                        fromdate, todate, s, pageable);
            }
            if (!invstatus.equals("all")) {
                switch (role) {
                case "admin":
                    if (!sid.equals("all") && !uid.equals("all")) {
                        return iaRepo.findInvActivityByUseridShopidCompanyidVoidstatusInvstatusSearch(uid, sid,
                                companyid, voidstatus, invstatus, s, pageable);
                    }
                    if (!sid.equals("all")) {
                        return iaRepo.findInvActivityByShopidCompanyidVoidstatusInvstatusSearch(sid, companyid,
                                voidstatus, invstatus, s, pageable);
                    }
                    return iaRepo.findInvActivityByCompanyidVoidstatusInvstatusSearch(companyid, voidstatus, invstatus,
                            s, pageable);
                case "superadmin":
                    if (!cid.equals("all") && !sid.equals("all") && !uid.equals("all")) {
                        return iaRepo.findInvActivityByUseridShopidCompanyidVoidstatusInvstatusSearch(uid, sid, cid,
                                voidstatus, invstatus, s, pageable);
                    }
                    if (!cid.equals("all") && !sid.equals("all")) {
                        return iaRepo.findInvActivityByShopidCompanyidVoidstatusInvstatusSearch(sid, cid, voidstatus,
                                invstatus, s, pageable);
                    }
                    if (!cid.equals("all")) {
                        return iaRepo.findInvActivityByCompanyidVoidstatusInvstatusSearch(cid, voidstatus, invstatus, s,
                                pageable);
                    }
                    return iaRepo.findInvActivityByVoidstatusInvstatusSearch(voidstatus, invstatus, s, pageable);
                }
                return iaRepo.findInvActivityByShopidCompanyidVoidstatusInvstatusSearch(shopid, companyid, voidstatus,
                        invstatus, s, pageable);
            }
            switch (role) {
            case "admin":
                if (!sid.equals("all") && !uid.equals("all")) {
                    return iaRepo.findInvActivityByUseridShopidCompanyidVoidstatusSearch(uid, sid, companyid,
                            voidstatus, s, pageable);
                }
                if (!sid.equals("all")) {
                    return iaRepo.findInvActivityByShopidCompanyidVoidstatusSearch(sid, cid, voidstatus, s, pageable);
                }
                return iaRepo.findInvActivityByCompanyidVoidstatusSearch(companyid, voidstatus, s, pageable);
            case "superadmin":
                if (!cid.equals("all") && !sid.equals("all") && !uid.equals("all")) {
                    return iaRepo.findInvActivityByUseridShopidCompanyidVoidstatusSearch(uid, sid, cid, voidstatus, s,
                            pageable);
                }
                if (!cid.equals("all") && !sid.equals("all")) {
                    return iaRepo.findInvActivityByShopidCompanyidVoidstatusSearch(sid, cid, voidstatus, s, pageable);
                }
                if (!cid.equals("all")) {
                    return iaRepo.findInvActivityByCompanyidVoidstatusSearch(cid, voidstatus, s, pageable);
                }
                return iaRepo.findInvActivityByVoidstatusSearch(voidstatus, s, pageable);
            }
            return iaRepo.findInvActivityByShopidCompanyidVoidstatusSearch(shopid, companyid, voidstatus, s, pageable);
        }
        if (voidstatus == 2) {
            if (fromdate != null & todate != null) {
                if (!invstatus.equals("all")) {
                    switch (role) {
                    case "admin":
                        if (!sid.equals("all") && !uid.equals("all")) {
                            return iaRepo.findInvActivityByUseridShopidCompanyidInvstatusDateBetweenSearchContain(uid,
                                    sid, companyid, invstatus, fromdate, todate, s, pageable);
                        }
                        if (!sid.equals("all")) {
                            return iaRepo.findInvActivityByShopidCompanyidInvstatusDateBetweenSearchContain(sid,
                                    companyid, invstatus, fromdate, todate, s, pageable);
                        }
                        return iaRepo.findInvActivityByCompanyidInvstatusDateBetweenSearchContain(companyid, invstatus,
                                fromdate, todate, s, pageable);
                    case "superadmin":
                        if (!cid.equals("all") && !sid.equals("all") && !uid.equals("all")) {
                            return iaRepo.findInvActivityByUseridShopidCompanyidInvstatusDateBetweenSearchContain(uid,
                                    sid, cid, invstatus, fromdate, todate, s, pageable);
                        }
                        if (!cid.equals("all") && !sid.equals("all")) {
                            return iaRepo.findInvActivityByShopidCompanyidInvstatusDateBetweenSearchContain(sid, cid,
                                    invstatus, fromdate, todate, s, pageable);
                        }
                        if (!cid.equals("all")) {
                            return iaRepo.findInvActivityByCompanyidInvstatusDateBetweenSearchContain(cid, invstatus,
                                    fromdate, todate, s, pageable);
                        }
                        return iaRepo.findInvActivityByInvstatusDateBetweenSearchContain(invstatus, fromdate, todate, s,
                                pageable);
                    }
                    return iaRepo.findInvActivityByShopidCompanyidInvstatusDateBetweenSearchContain(shopid, companyid,
                            invstatus, fromdate, todate, s, pageable);
                }
                switch (role) {
                case "admin":
                    if (!sid.equals("all") && !uid.equals("all")) {
                        return iaRepo.findInvActivityByUseridShopidCompanyidDateBetweenSearchContain(uid, sid,
                                companyid, fromdate, todate, s, pageable);
                    }
                    if (!sid.equals("all")) {
                        return iaRepo.findInvActivityByShopidCompanyidDateBetweenSearchContain(sid, companyid, fromdate,
                                todate, s, pageable);
                    }
                    return iaRepo.findInvActivityByCompanyidDateBetweenSearchContain(companyid, fromdate, todate, s,
                            pageable);
                case "superadmin":
                    if (!cid.equals("all") && !sid.equals("all") && !uid.equals("all")) {
                        return iaRepo.findInvActivityByUseridShopidCompanyidDateBetweenSearchContain(uid, sid, cid,
                                fromdate, todate, s, pageable);
                    }
                    if (!cid.equals("all") && !sid.equals("all")) {
                        return iaRepo.findInvActivityByShopidCompanyidDateBetweenSearchContain(sid, cid, fromdate,
                                todate, s, pageable);
                    }
                    if (!cid.equals("all")) {
                        return iaRepo.findInvActivityByCompanyidDateBetweenSearchContain(cid, fromdate, todate, s,
                                pageable);
                    }
                    return iaRepo.findInvActivityByDateBetweenSearchContain(fromdate, todate, s, pageable);
                }
                return iaRepo.findInvActivityByShopidCompanyidDateBetweenSearchContain(shopid, companyid, fromdate,
                        todate, s, pageable);
            }
            if (!invstatus.equals("all")) {
                switch (role) {
                case "admin":
                    if (!sid.equals("all") && !uid.equals("all")) {
                        return iaRepo.findInvActivityByUseridShopidCompanyidInvstatusSearchContain(uid, sid, companyid,
                                invstatus, s, pageable);
                    }
                    if (!sid.equals("all")) {
                        return iaRepo.findInvActivityByShopidCompanyidInvstatusSearchContain(sid, companyid, invstatus,
                                s, pageable);
                    }
                    return iaRepo.findInvActivityByCompanyidInvstatusSearchContain(companyid, invstatus, s, pageable);
                case "superadmin":
                    if (!cid.equals("all") && !sid.equals("all") && !uid.equals("all")) {
                        return iaRepo.findInvActivityByUseridShopidCompanyidInvstatusSearchContain(uid, sid, cid,
                                invstatus, search, pageable);
                    }
                    if (!cid.equals("all") && !sid.equals("all")) {
                        return iaRepo.findInvActivityByShopidCompanyidInvstatusSearchContain(sid, cid, invstatus, s,
                                pageable);
                    }
                    if (!cid.equals("all")) {
                        return iaRepo.findInvActivityByCompanyidInvstatusSearchContain(cid, invstatus, s, pageable);
                    }
                    return iaRepo.findInvActivityByInvstatusSearchContain(invstatus, s, pageable);
                }
                return iaRepo.findInvActivityByShopidCompanyidInvstatusSearchContain(shopid, companyid, invstatus, s,
                        pageable);
            }
            switch (role) {
            case "admin":
                if (!sid.equals("all") && !uid.equals("all")) {
                    return iaRepo.findInvActivityByUseridShopidCompanyidSearchContain(uid, sid, companyid, s, pageable);
                }
                if (!sid.equals("all")) {
                    return iaRepo.findInvActivityByShopidCompanyidSearchContain(sid, companyid, s, pageable);
                }
                return iaRepo.findInvActivityByCompanyidSearchContain(companyid, s, pageable);
            case "superadmin":
                if (!cid.equals("all") && !sid.equals("all") && !uid.equals("all")) {
                    return iaRepo.findInvActivityByUseridShopidCompanyidSearchContain(uid, sid, cid, s, pageable);
                }
                if (!cid.equals("all") && !sid.equals("all")) {
                    return iaRepo.findInvActivityByShopidCompanyidSearchContain(sid, cid, s, pageable);
                }
                if (!cid.equals("all")) {
                    return iaRepo.findInvActivityByCompanyidSearchContain(cid, s, pageable);
                }
                return iaRepo.findInvActivityBySearchContain(s, pageable);
            }
            return iaRepo.findInvActivityByShopidCompanyidSearchContain(shopid, companyid, s, pageable);
        }
        if (fromdate != null && todate != null) {
            if (!invstatus.equals("all")) {
                switch (role) {
                case "admin":
                    if (!sid.equals("all") && !uid.equals("all")) {
                        return iaRepo.findInvActivityByUseridShopidCompanyidVoidstatusInvstatusDateBetweenSearchContain(
                                uid, sid, companyid, voidstatus, invstatus, fromdate, todate, s, pageable);
                    }
                    if (!sid.equals("all")) {
                        return iaRepo.findInvActivityByShopidCompanyidVoidstatusInvstatusDateBetweenSearchContain(sid,
                                companyid, voidstatus, invstatus, fromdate, todate, s, pageable);
                    }
                    return iaRepo.findInvActivityByCompanyidVoidstatusInvstatusDateBetweenSearchContain(companyid,
                            voidstatus, invstatus, fromdate, todate, s, pageable);
                case "superadmin":
                    if (!cid.equals("all") && !sid.equals("all") && !uid.equals("all")) {
                        return iaRepo.findInvActivityByUseridShopidCompanyidVoidstatusInvstatusDateBetweenSearchContain(
                                uid, sid, cid, voidstatus, invstatus, fromdate, todate, s, pageable);
                    }
                    if (!cid.equals("all") && !sid.equals("all")) {
                        return iaRepo.findInvActivityByShopidCompanyidVoidstatusInvstatusDateBetweenSearchContain(sid,
                                cid, voidstatus, invstatus, fromdate, todate, s, pageable);
                    }
                    if (!cid.equals("all")) {
                        return iaRepo.findInvActivityByCompanyidVoidstatusInvstatusDateBetweenSearchContain(cid,
                                voidstatus, invstatus, fromdate, todate, s, pageable);
                    }
                    return iaRepo.findInvActivityByVoidstatusInvstatusDateBetweenSearchContain(voidstatus, invstatus,
                            fromdate, todate, s, pageable);
                }
                return iaRepo.findInvActivityByShopidCompanyidVoidstatusInvstatusDateBetweenSearchContain(shopid,
                        companyid, voidstatus, invstatus, fromdate, todate, s, pageable);
            }
            switch (role) {
            case "admin":
                if (!sid.equals("all") && !uid.equals("all")) {
                    return iaRepo.findInvActivityByUseridShopidCompanyidVoidstatusDateBetweenSearchContain(uid, sid,
                            companyid, voidstatus, fromdate, todate, s, pageable);
                }
                if (!sid.equals("all")) {
                    return iaRepo.findInvActivityByShopidCompanyidVoidstatusDateBetweenSearchContain(sid, companyid,
                            voidstatus, fromdate, todate, s, pageable);
                }
                return iaRepo.findInvActivityByCompanyidVoidstatusDateBetweenSearchContain(companyid, voidstatus,
                        fromdate, todate, s, pageable);
            case "superadmin":
                if (!cid.equals("all") && !sid.equals("all") && !uid.equals("all")) {
                    return iaRepo.findInvActivityByUseridShopidCompanyidVoidstatusDateBetweenSearchContain(uid, sid,
                            cid, voidstatus, fromdate, todate, s, pageable);
                }
                if (!cid.equals("all") && !sid.equals("all")) {
                    return iaRepo.findInvActivityByShopidCompanyidVoidstatusDateBetweenSearchContain(sid, cid,
                            voidstatus, fromdate, todate, s, pageable);
                }
                if (!cid.equals("all")) {
                    return iaRepo.findInvActivityByCompanyidVoidstatusDateBetweenSearchContain(cid, voidstatus,
                            fromdate, todate, s, pageable);
                }
                return iaRepo.findInvActivityByVoidstatusDateBetweenSearchContain(voidstatus, fromdate, todate, s,
                        pageable);
            }
            return iaRepo.findInvActivityByShopidCompanyidVoidstatusDateBetweenSearchContain(shopid, companyid,
                    voidstatus, fromdate, todate, s, pageable);
        }
        if (!invstatus.equals("all")) {
            switch (role) {
            case "admin":
                if (!sid.equals("all") && !uid.equals("all")) {
                    return iaRepo.findInvActivityByUseridShopidCompanyidVoidstatusInvstatusSearchContain(uid, sid,
                            companyid, voidstatus, invstatus, s, pageable);
                }
                if (!sid.equals("all")) {
                    return iaRepo.findInvActivityByShopidCompanyidVoidstatusInvstatusSearchContain(sid, companyid,
                            voidstatus, invstatus, s, pageable);
                }
                return iaRepo.findInvActivityByCompanyidVoidstatusInvstatusSearchContain(companyid, voidstatus,
                        invstatus, s, pageable);
            case "superadmin":
                if (!cid.equals("all") && !sid.equals("all") && !uid.equals("all")) {
                    return iaRepo.findInvActivityByUseridShopidCompanyidVoidstatusInvstatusSearchContain(uid, sid, cid,
                            voidstatus, invstatus, s, pageable);
                }
                if (!cid.equals("all") && !sid.equals("all")) {
                    return iaRepo.findInvActivityByShopidCompanyidVoidstatusInvstatusSearchContain(sid, cid, voidstatus,
                            invstatus, s, pageable);
                }
                if (!cid.equals("all")) {
                    return iaRepo.findInvActivityByCompanyidVoidstatusInvstatusSearchContain(cid, voidstatus, invstatus,
                            s, pageable);
                }
                return iaRepo.findInvActivityByVoidstatusInvstatusSearchContain(voidstatus, invstatus, s, pageable);
            }
            return iaRepo.findInvActivityByShopidCompanyidVoidstatusInvstatusSearchContain(shopid, companyid,
                    voidstatus, invstatus, s, pageable);
        }
        switch (role) {
        case "admin":
            if (!sid.equals("all") && !uid.equals("all")) {
                return iaRepo.findInvActivityByUseridShopidCompanyidVoidstatusSearchContain(uid, sid, companyid,
                        voidstatus, s, pageable);
            }
            if (!sid.equals("all")) {
                return iaRepo.findInvActivityByShopidCompanyidVoidstatusSearchContain(sid, companyid, voidstatus, s,
                        pageable);
            }
            return iaRepo.findInvActivityByCompanyidVoidstatusSearchContain(companyid, voidstatus, s, pageable);
        case "superadmin":
            if (!cid.equals("all") && !sid.equals("all") && !uid.equals("all")) {
                return iaRepo.findInvActivityByUseridShopidCompanyidVoidstatusSearchContain(uid, sid, cid, voidstatus,
                        s, pageable);
            }
            if (!cid.equals("all") && !sid.equals("all")) {
                return iaRepo.findInvActivityByShopidCompanyidVoidstatusSearchContain(sid, cid, voidstatus, s,
                        pageable);
            }
            if (!cid.equals("all")) {
                return iaRepo.findInvActivityByCompanyidVoidstatusSearchContain(cid, voidstatus, s, pageable);
            }
            return iaRepo.findInvActivityByVoidstatusSearchContain(voidstatus, s, pageable);
        }
        return iaRepo.findInvActivityByShopidCompanyidVoidstatusSearchContain(shopid, companyid, voidstatus, s,
                pageable);
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
            inv = iRepo.findByItemrefAndShopidAndCompanyidAndStatus(ia.getItemref(), tokenData.getShopid(),
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
                invopt = iRepo.findByItemrefAndShopidAndCompanyidAndStatus(invactivity.getItemref(),
                        tokenData.getShopid(), tokenData.getCompanyid(), 1);
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
