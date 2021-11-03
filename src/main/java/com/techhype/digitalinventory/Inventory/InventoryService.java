package com.techhype.digitalinventory.Inventory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techhype.digitalinventory.InventoryActivity.ActivityTotalDto;
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
        String role = tokenData.getRole();
        if (role.equals("admin")) {
            return iRepo.findByItemrefAndCompanyidAndStatus(itemref, tokenData.getCompanyid(), 1);
        } else if (role.equals("superadmin")) {
            return iRepo.findByItemrefAndStatus(itemref, 1);
        }
        return iRepo.findByItemrefAndShopidAndCompanyidAndStatus(itemref, tokenData.getShopid(),
                tokenData.getCompanyid(), 1);
    }

    public int getRemaining(String itemref, TokenData tokenData) {
        String role = tokenData.getRole();
        var inventory = new Inventory();
        List<ActivityTotalDto> totals = new ArrayList<>();
        switch (role) {
        case "normaluser":
            inventory = iRepo.findByItemrefAndShopidAndCompanyidAndStatus(itemref, tokenData.getShopid(),
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

    public boolean updateInventory(String itemref, Inventory inventory, TokenData tokenData) {
        var oldinv = getInventoryByItemRef(itemref, tokenData);
        var role = tokenData.getRole();
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
        oldinventory.setMinthreshold(inventory.getMinthreshold());
        switch (role) {
        case "admin":
            oldinventory.setShopid(inventory.getShopid());
            oldinventory.setShopname(inventory.getShopname());
            break;
        case "superadmin":
            oldinventory.setCompanyid(inventory.getCompanyid());
            oldinventory.setCompanyname(inventory.getCompanyname());
            oldinventory.setShopid(inventory.getShopid());
            oldinventory.setShopname(inventory.getShopname());
        }

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
        var role = tokenData.getRole();
        switch (role) {
        case "admin":
            inventory.setCompanyid(tokenData.getCompanyid());
            inventory.setCompanyname(tokenData.getCompanyname());
            break;
        case "normaluser":
            inventory.setCompanyid(tokenData.getCompanyid());
            inventory.setCompanyname(tokenData.getCompanyname());
            inventory.setShopid(tokenData.getShopid());
            inventory.setShopname(tokenData.getShopname());
            break;
        }

        inventory.setId(null);
        inventory.setCreateddate(now);
        inventory.setModifieddate(now);
        inventory.setRemaining(inventory.getCounts());
        inventory.setUserid(tokenData.getUserid());
        inventory.setUsername(tokenData.getUsername());

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
            return iRepo.findByShopidAndCompanyidAndStatus(tokenData.getShopid(), tokenData.getCompanyid(), 1, pagable);
        }

        String s = search.trim();
        if (s.startsWith("\"") && s.endsWith("\"")) {
            s = s.replaceAll("\"", "");
            if (role.equals("admin")) {
                return iRepo.findInventory(tokenData.getCompanyid(), s, pagable);
            } else if (role.equals("superadmin")) {
                return iRepo.findInventory(s, pagable);
            }
            return iRepo.findInventory(tokenData.getShopid(), tokenData.getCompanyid(), s, pagable);
        }
        if (role.equals("admin")) {
            return iRepo.findInventoryWithContain(tokenData.getCompanyid(), s, pagable);
        } else if (role.equals("superadmin")) {
            return iRepo.findInventoryWithContain(s, pagable);
        }
        return iRepo.findInventoryWithContain(tokenData.getShopid(), tokenData.getCompanyid(), s, pagable);
    }

    public List<InventoryDto> getInventoriesForCombo(TokenData tokenData) {
        String role = tokenData.getRole();
        if (role.equals("admin")) {
            return iRepo.findByCompanyidAndStatus(tokenData.getCompanyid(), 1);
        } else if (role.equals("superadmin")) {
            return iRepo.findByStatus(1);
        }
        return iRepo.findByShopidAndCompanyidAndStatus(tokenData.getUserid(), tokenData.getCompanyid(), 1);
    }

    public List<Inventory> importInventories(List<Map<String, Object>> exceldata, TokenData tokenData)
            throws JsonProcessingException {
        List<Inventory> inventories = new ArrayList<>();
        for (var data : exceldata) {
            var now = LocalDateTime.now();
            data.remove("no");
            var counts = (String) data.get("counts");
            if (counts != null && !counts.isEmpty()) {
                data.put("counts", Integer.parseInt(counts.split("\\.")[0]));
            }
            var json = new ObjectMapper().writeValueAsString(data);
            var inventory = new ObjectMapper().readValue(json, Inventory.class);
            inventory.setUserid(tokenData.getUserid());
            inventory.setUsername(tokenData.getUsername());
            inventory.setCompanyid(tokenData.getCompanyid());
            inventory.setCompanyname(tokenData.getCompanyname());
            inventory.setCreateddate(now);
            inventory.setModifieddate(now);
            inventory.setRemaining(inventory.getCounts());
            inventory.setNetprice(inventory.getPrice());
            inventory.setItemcode(inventory.getItemcode().split("\\.")[0]);
            var newInv = iRepo.save(inventory);
            newInv.setItemref(String.format("I%06d", newInv.getId()));
            inventories.add(iRepo.save(newInv));
        }
        return inventories;
    }
}
