package com.techhype.digitalinventory.Inventory;

import com.techhype.digitalinventory.constants.ServerMessage;
import com.techhype.digitalinventory.constants.ServerStatus;
import com.techhype.digitalinventory.models.BaseResponse;
import com.techhype.digitalinventory.models.PaginationResponse;
import com.techhype.digitalinventory.models.TokenData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping(path = "api/v1/inventories")
public class InventoryController {
    private InventoryService iService;

    @Autowired
    public InventoryController(InventoryService iService) {
        this.iService = iService;
    }

    @GetMapping(path = "{itemref}")
    public ResponseEntity<BaseResponse> getInventoryByItemref(@PathVariable String itemref) {
        try {
            var inventory = iService.getInventoryByItemRef(itemref, new TokenData());
            if (!inventory.isPresent()) {
                return new ResponseEntity<>(BaseResponse.notFound(), HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok().body(BaseResponse.ok(inventory.get()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(BaseResponse.internalServerError());
        }
    }

    @PostMapping
    public ResponseEntity<BaseResponse> addInventory(@RequestBody Inventory inventory) {
        try {
            var newinv = iService.addInventory(inventory, new TokenData());
            var ref = newinv.getItemref();
            return new ResponseEntity<>(new BaseResponse(ServerStatus.CREATED, ServerMessage.Created(ref), newinv),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(BaseResponse.internalServerError());
        }
    }

    @PutMapping(path = "{itemref}")
    public ResponseEntity<BaseResponse> updateInventory(@PathVariable String itemref,
            @RequestBody Inventory inventory) {
        try {
            var success = iService.updateInventory(itemref, inventory, new TokenData());
            if (!success) {
                return new ResponseEntity<>(BaseResponse.notFound(), HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok().body(new BaseResponse(ServerStatus.OK, ServerMessage.Updated(itemref), itemref));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(BaseResponse.internalServerError());
        }
    }

    @DeleteMapping(path = "{itemref}")
    public ResponseEntity<BaseResponse> softDeleteInventory(@PathVariable String itemref) {
        try {
            var success = iService.softDeleteInventory(itemref, new TokenData());
            if (!success) {
                return new ResponseEntity<>(BaseResponse.notFound(), HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(BaseResponse.internalServerError());
        }
    }

    @GetMapping
    public ResponseEntity<BaseResponse> getInventories(@RequestParam(required = false) String search,
            @RequestParam(defaultValue = "1") int page, @RequestParam int perpage,
            @RequestParam(defaultValue = "createddate") String sortby) {
        try {
            var invpage = iService.getInventories(search, page, perpage, sortby, new TokenData());
            var body = new PaginationResponse();
            body.setData(invpage.getContent());
            body.setPagecount(invpage.getTotalPages());
            body.setTotal(invpage.getNumberOfElements());
            body.setPage(page);
            body.setPerpage(perpage);
            return ResponseEntity.ok().body(body);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(BaseResponse.internalServerError());
        }
    }
}
