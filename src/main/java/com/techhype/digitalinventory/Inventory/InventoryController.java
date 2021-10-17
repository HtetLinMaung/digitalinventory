package com.techhype.digitalinventory.Inventory;

import com.techhype.digitalinventory.models.BaseResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/inventories")
public class InventoryController {
    private InventoryService iService;

    @Autowired
    public InventoryController(InventoryService iService) {
        this.iService = iService;
    }

    @PostMapping
    public ResponseEntity<BaseResponse> addInventory(@RequestBody Inventory inventory) {
        try {
            return new ResponseEntity<>(new BaseResponse(201, "Created Successfully", iService.addInventory(inventory)),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new BaseResponse(500, "Internal Server Error", null));
        }
    }

    @GetMapping
    public ResponseEntity<BaseResponse> getInventories(@RequestParam(required = false) String search,
            @RequestParam(defaultValue = "1") int page, @RequestParam int perpage) {
        try {
            return ResponseEntity.ok()
                    .body(new BaseResponse(200, "Success", iService.getInventories(search, page, perpage)));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new BaseResponse(500, "Internal Server Error", null));
        }
    }
}
