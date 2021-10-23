package com.techhype.digitalinventory.InventoryActivity;

import java.time.LocalDateTime;

import com.techhype.digitalinventory.constants.ServerMessage;
import com.techhype.digitalinventory.constants.ServerStatus;
import com.techhype.digitalinventory.models.BaseResponse;
import com.techhype.digitalinventory.models.PaginationResponse;
import com.techhype.digitalinventory.models.TokenData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/v1/inventory-activities")
public class InvActivityController {
    private InvActivityService iaService;

    @Autowired
    public InvActivityController(InvActivityService iaService) {
        this.iaService = iaService;
    }

    @PostMapping
    public ResponseEntity<BaseResponse> addInventoryActivity(@RequestBody InventoryActivity inventoryActivity) {
        try {
            var newinvactivity = iaService.addInventoryActivity(inventoryActivity, new TokenData());
            var ref = newinvactivity.getActivityref();
            return new ResponseEntity<>(
                    new BaseResponse(ServerStatus.CREATED, ServerMessage.Created(ref), newinvactivity),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(BaseResponse.internalServerError());
        }
    }

    @GetMapping(path = "remainings/{itemref}")
    public ResponseEntity<BaseResponse> getRemainingByItemref(@PathVariable String itemref) {
        try {
            return ResponseEntity.ok().body(BaseResponse.ok(iaService.getRemaining(itemref, new TokenData())));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(BaseResponse.internalServerError());
        }
    }

    @GetMapping(path = "{activityref}")
    public ResponseEntity<BaseResponse> getInvActivityByRef(@PathVariable String activityref) {
        try {
            var ia = iaService.getInvActivityByRef(activityref, new TokenData());
            if (!ia.isPresent()) {
                return new ResponseEntity<>(BaseResponse.notFound(), HttpStatus.NOT_FOUND);
            }

            return ResponseEntity.ok()
                    .body(BaseResponse.ok(iaService.getInvActivityByRef(activityref, new TokenData())));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(BaseResponse.internalServerError());
        }
    }

    @GetMapping
    public ResponseEntity<BaseResponse> getInvActivities(@RequestParam(required = false) String search,
            @RequestParam(defaultValue = "1") int page, @RequestParam int perpage,
            @RequestParam(defaultValue = "createddate") String sortby, @RequestParam(defaultValue = "1") String reverse,
            @RequestParam int voidstatus,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromdate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime todate,
            @RequestParam String invstatus) {
        try {
            var invpage = iaService.getInvActivities(search, page, perpage, sortby, reverse, voidstatus, fromdate,
                    todate, invstatus, new TokenData());
            var body = new PaginationResponse();
            body.setData(invpage.getContent());
            body.setPagecount(invpage.getTotalPages());
            body.setTotal(invpage.getTotalElements());
            body.setPage(page);
            body.setPerpage(perpage);
            return ResponseEntity.ok().body(body);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(BaseResponse.internalServerError());
        }
    }

    @GetMapping(path = "totals")
    public ResponseEntity<BaseResponse> getInvActivitiesTotal(@RequestParam(required = false) String search,
            @RequestParam(defaultValue = "1") int page, @RequestParam int perpage,
            @RequestParam(defaultValue = "createddate") String sortby, @RequestParam(defaultValue = "1") String reverse,
            @RequestParam int voidstatus,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromdate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime todate,
            @RequestParam String invstatus) {
        try {
            var totals = iaService.getInvActiviesTotal(search, page, perpage, sortby, reverse, voidstatus, fromdate,
                    todate, invstatus, new TokenData());

            return ResponseEntity.ok().body(BaseResponse.ok(totals));
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponse.ok(new IAFilterTotalDto(0, 0)));
        }
    }

    @PutMapping(value = "/void/{activityref}")
    public ResponseEntity<BaseResponse> putMethodName(@PathVariable String activityref,
            @RequestBody InventoryActivity inventoryActivity) {
        try {
            var voidstatus = inventoryActivity.getVoidstatus();
            var success = iaService.changeVoidStatus(activityref, voidstatus, new TokenData());

            if (!success) {
                return new ResponseEntity<>(BaseResponse.notFound(), HttpStatus.NOT_FOUND);
            }

            return ResponseEntity.ok()
                    .body(new BaseResponse(ServerStatus.OK,
                            String.format("%s %s successfully.", activityref, voidstatus == 0 ? "void" : "unvoid"),
                            activityref));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(BaseResponse.internalServerError());
        }
    }
}
