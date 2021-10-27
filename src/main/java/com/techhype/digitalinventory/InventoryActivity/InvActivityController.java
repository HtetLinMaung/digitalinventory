package com.techhype.digitalinventory.InventoryActivity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.techhype.digitalinventory.constants.ServerMessage;
import com.techhype.digitalinventory.constants.ServerStatus;
import com.techhype.digitalinventory.middlewares.AuthMiddleware;
import com.techhype.digitalinventory.models.BaseResponse;
import com.techhype.digitalinventory.models.FileUploadDto;
import com.techhype.digitalinventory.models.PaginationResponse;
import com.techhype.digitalinventory.utils.ExcelUtils;
import com.techhype.digitalinventory.utils.FileUtils;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/v1/inventory-activities")
public class InvActivityController {
    private InvActivityService iaService;
    private AuthMiddleware authMiddleware;

    @Autowired
    public InvActivityController(InvActivityService iaService, AuthMiddleware authMiddleware) {
        this.iaService = iaService;
        this.authMiddleware = authMiddleware;
    }

    @PostMapping
    public ResponseEntity<BaseResponse> addInventoryActivity(@RequestBody InventoryActivity inventoryActivity,
            @RequestHeader("Authorization") String authorization) {
        try {
            var auth = authMiddleware.checkToken(authorization);
            if (!auth.isAuth()) {
                return auth.getResponse();
            }
            var newinvactivity = iaService.addInventoryActivity(inventoryActivity, auth.getTokenData());
            var ref = newinvactivity.getActivityref();
            return new ResponseEntity<>(
                    new BaseResponse(ServerStatus.CREATED, ServerMessage.Created(ref), newinvactivity),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(BaseResponse.internalServerError());
        }
    }

    @GetMapping(path = "remainings/{itemref}")
    public ResponseEntity<BaseResponse> getRemainingByItemref(@PathVariable String itemref,
            @RequestHeader("Authorization") String authorization) {
        try {
            var auth = authMiddleware.checkToken(authorization);
            if (!auth.isAuth()) {
                return auth.getResponse();
            }
            return ResponseEntity.ok().body(BaseResponse.ok(iaService.getRemaining(itemref, auth.getTokenData())));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(BaseResponse.internalServerError());
        }
    }

    @GetMapping(path = "{activityref}")
    public ResponseEntity<BaseResponse> getInvActivityByRef(@PathVariable String activityref,
            @RequestHeader("Authorization") String authorization) {
        try {
            var auth = authMiddleware.checkToken(authorization);
            if (!auth.isAuth()) {
                return auth.getResponse();
            }
            var ia = iaService.getInvActivityByRef(activityref, auth.getTokenData());
            if (!ia.isPresent()) {
                return new ResponseEntity<>(BaseResponse.notFound(), HttpStatus.NOT_FOUND);
            }

            return ResponseEntity.ok().body(BaseResponse.ok(ia));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(BaseResponse.internalServerError());
        }
    }

    @PostMapping(path = "import")
    public ResponseEntity<?> importInvactivities(@RequestBody FileUploadDto dto,
            @RequestHeader("Authorization") String authorization) {
        try {
            var auth = authMiddleware.checkToken(authorization);
            if (!auth.isAuth()) {
                return auth.getResponse();
            }
            if (!List.of("xlsx", "xls", "csv").contains(dto.getFileext())) {
                return new ResponseEntity<>(new BaseResponse(ServerStatus.BAD_REQUEST, ServerMessage.BAD_REQUEST, null),
                        HttpStatus.BAD_REQUEST);
            }
            try {
                new XSSFWorkbook(FileUtils.getInputStreamFromBase64(dto.getFile())).close();
            } catch (Exception err) {
                return new ResponseEntity<>(new BaseResponse(ServerStatus.BAD_REQUEST, "Can't read excel file.", null),
                        HttpStatus.BAD_REQUEST);
            }
            var headers = List.of("no", "itemref", "qty", "vouchercode", "customername", "date", "status", "remark");
            var exceldata = ExcelUtils.excelToDataList(dto.getFile(), headers);
            var invactivities = iaService.importInvActivies(exceldata, auth.getTokenData());
            return ResponseEntity.ok(BaseResponse.ok(invactivities));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(BaseResponse.internalServerError());
        }
    }

    @GetMapping(path = "export")
    public ResponseEntity<?> exportInvActivities(@RequestParam(required = false) String search,
            @RequestParam(defaultValue = "1") int page, @RequestParam int perpage,
            @RequestParam(defaultValue = "createddate") String sortby, @RequestParam(defaultValue = "1") String reverse,
            @RequestParam int voidstatus,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromdate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime todate,
            @RequestParam String invstatus, @RequestParam String authorization) {
        try {
            var auth = authMiddleware.checkToken(authorization);
            if (!auth.isAuth()) {
                return auth.getResponse();
            }
            var invpage = iaService.getInvActivities(search, page, perpage, sortby, reverse, voidstatus, fromdate,
                    todate, invstatus, auth.getTokenData());
            List<LinkedHashMap<String, Object>> exceldata = new ArrayList<>();
            int total = 0;
            int i = 1;
            for (var item : invpage.getContent()) {
                var data = new LinkedHashMap<String, Object>();
                data.put("No.", String.valueOf(i++));
                data.put("Ref.", item.getActivityref());
                data.put("Label", item.getLabel());
                data.put("Qty", String.valueOf(item.getQty()));
                data.put("Price", String.valueOf(item.getPrice()));
                data.put("Amount", String.valueOf(item.getAmount()));
                data.put("Date", item.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                data.put("Voucher", item.getVouchercode());
                data.put("Customer", item.getCustomername());
                data.put("Status", item.getInvstatus());
                data.put("Void", item.getVoidstatus() == 1 ? "No" : "Yes");
                data.put("Remark", item.getRemark());
                total += item.getAmount();
                exceldata.add(data);
            }
            var finalrow = new LinkedHashMap<String, Object>();
            finalrow.put("Ref.", "");
            finalrow.put("Label", "");
            finalrow.put("Qty", "");
            finalrow.put("Price", "Total");
            finalrow.put("Amount", String.valueOf(total));
            finalrow.put("Date", "");
            finalrow.put("Voucher", "");
            finalrow.put("Customer", "");
            finalrow.put("Status", "");
            finalrow.put("Void", "");
            exceldata.add(finalrow);
            var out = ExcelUtils.writeExcel(exceldata, "Inventory In-Out");
            var resource = new ByteArrayResource(out.toByteArray());
            var headers = new HttpHeaders();
            var lDate = LocalDate.now();
            var exporteddate = String.valueOf(lDate.getDayOfMonth()) + String.valueOf(lDate.getMonthValue())
                    + String.valueOf(lDate.getYear());
            headers.add(HttpHeaders.CONTENT_DISPOSITION,
                    String.format("attachment; filename=inventory-in-out-%s.xls", exporteddate));
            return ResponseEntity.ok().headers(headers).contentLength(out.toByteArray().length)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
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
            @RequestParam String invstatus, @RequestHeader("Authorization") String authorization) {
        try {
            var auth = authMiddleware.checkToken(authorization);
            if (!auth.isAuth()) {
                return auth.getResponse();
            }
            var invpage = iaService.getInvActivities(search, page, perpage, sortby, reverse, voidstatus, fromdate,
                    todate, invstatus, auth.getTokenData());
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
            @RequestParam String invstatus, @RequestHeader("Authorization") String authorization) {
        try {
            var auth = authMiddleware.checkToken(authorization);
            if (!auth.isAuth()) {
                return auth.getResponse();
            }
            var totals = iaService.getInvActiviesTotal(search, page, perpage, sortby, reverse, voidstatus, fromdate,
                    todate, invstatus, auth.getTokenData());

            return ResponseEntity.ok().body(BaseResponse.ok(totals));
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponse.ok(new IAFilterTotalDto(0, 0)));
        }
    }

    @PutMapping(value = "/void/{activityref}")
    public ResponseEntity<BaseResponse> putMethodName(@PathVariable String activityref,
            @RequestBody InventoryActivity inventoryActivity, @RequestHeader("Authorization") String authorization) {
        try {
            var auth = authMiddleware.checkToken(authorization);
            if (!auth.isAuth()) {
                return auth.getResponse();
            }
            var voidstatus = inventoryActivity.getVoidstatus();
            var success = iaService.changeVoidStatus(activityref, voidstatus, auth.getTokenData());

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
