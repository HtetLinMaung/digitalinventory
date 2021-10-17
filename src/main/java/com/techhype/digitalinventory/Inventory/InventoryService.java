package com.techhype.digitalinventory.Inventory;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
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
        inventory.setUserid("1");
        inventory.setUsername("admin");
        var newInv = iRepo.save(inventory);
        newInv.setItemref(String.format("I%06d", newInv.getId()));
        newInv = iRepo.save(newInv);
        return newInv.getItemref();
    }
}
