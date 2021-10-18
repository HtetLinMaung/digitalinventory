package com.techhype.digitalinventory.Inventory;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {
    private IInventoryRepository iRepo;

    @Autowired
    public InventoryService(IInventoryRepository iRepo) {
        this.iRepo = iRepo;
    }

    public String addInventory(Inventory inventory) {
        var now = LocalDateTime.now();
        inventory.setCreateddate(now);
        inventory.setModifieddate(now);
        var newInv = iRepo.save(inventory);
        newInv.setItemref(String.format("I%06d", newInv.getId()));
        newInv = iRepo.save(newInv);
        return newInv.getItemref();
    }

    public Page<Inventory> getInventories(String search, int page, int perpage) {
        var pagable = PageRequest.of(page - 1, perpage);
        if (search == null || search.isEmpty()) {
            return iRepo.findAll(pagable);
        }

        String s = search.trim();
        return iRepo.findByItemrefContainingOrItemcodeContainingOrLabelContaining(s, s, s, pagable);
    }
}
