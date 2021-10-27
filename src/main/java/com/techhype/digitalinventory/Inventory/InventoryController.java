package com.techhype.digitalinventory.Inventory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping(path = "api/v1/inventories")
public class InventoryController {
    private InventoryService iService;
    private AuthMiddleware authMiddleware;

    @Autowired
    public InventoryController(InventoryService iService, AuthMiddleware authMiddleware) {
        this.iService = iService;
        this.authMiddleware = authMiddleware;
    }

    @GetMapping(path = "{itemref}")
    public ResponseEntity<BaseResponse> getInventoryByItemref(@PathVariable String itemref,
            @RequestHeader("Authorization") String authorization) {
        try {
            var auth = authMiddleware.checkToken(authorization);
            if (!auth.isAuth()) {
                return auth.getResponse();
            }
            var inventory = iService.getInventoryByItemRef(itemref, auth.getTokenData());
            if (!inventory.isPresent()) {
                return new ResponseEntity<>(BaseResponse.notFound(), HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok().body(BaseResponse.ok(inventory.get()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(BaseResponse.internalServerError());
        }
    }

    @PostMapping
    public ResponseEntity<BaseResponse> addInventory(@RequestBody Inventory inventory,
            @RequestHeader("Authorization") String authorization) {
        try {
            var auth = authMiddleware.checkToken(authorization);
            if (!auth.isAuth()) {
                return auth.getResponse();
            }
            var newinv = iService.addInventory(inventory, auth.getTokenData());
            var ref = newinv.getItemref();
            return new ResponseEntity<>(new BaseResponse(ServerStatus.CREATED, ServerMessage.Created(ref), newinv),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(BaseResponse.internalServerError());
        }
    }

    @PutMapping(path = "{itemref}")
    public ResponseEntity<BaseResponse> updateInventory(@PathVariable String itemref, @RequestBody Inventory inventory,
            @RequestHeader("Authorization") String authorization) {
        try {
            var auth = authMiddleware.checkToken(authorization);
            if (!auth.isAuth()) {
                return auth.getResponse();
            }
            var success = iService.updateInventory(itemref, inventory, auth.getTokenData());
            if (!success) {
                return new ResponseEntity<>(BaseResponse.notFound(), HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok().body(new BaseResponse(ServerStatus.OK, ServerMessage.Updated(itemref), itemref));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(BaseResponse.internalServerError());
        }
    }

    @DeleteMapping(path = "{itemref}")
    public ResponseEntity<BaseResponse> softDeleteInventory(@PathVariable String itemref,
            @RequestHeader("Authorization") String authorization) {
        try {
            var auth = authMiddleware.checkToken(authorization);
            if (!auth.isAuth()) {
                return auth.getResponse();
            }
            var success = iService.softDeleteInventory(itemref, auth.getTokenData());

            if (!success) {
                return new ResponseEntity<>(BaseResponse.notFound(), HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(BaseResponse.internalServerError());
        }
    }

    @PostMapping(path = "import")
    public ResponseEntity<?> importInventories(@RequestBody FileUploadDto dto,
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
            var headers = List.of("no", "label", "itemcode", "price", "counts", "tag", "remark");
            var exceldata = ExcelUtils.excelToDataList(dto.getFile(), headers);
            var inventories = iService.importInventories(exceldata, auth.getTokenData());
            return ResponseEntity.ok(BaseResponse.ok(inventories));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(BaseResponse.internalServerError());
        }
    }

    @GetMapping(path = "export")
    public ResponseEntity<?> exportInventories(@RequestParam(required = false) String search,
            @RequestParam(defaultValue = "1") int page, @RequestParam int perpage,
            @RequestParam(defaultValue = "createddate") String sortby, @RequestParam(defaultValue = "1") String reverse,
            @RequestParam String authorization) {
        try {
            var auth = authMiddleware.checkToken(authorization);
            if (!auth.isAuth()) {
                return auth.getResponse();
            }
            var invpage = iService.getInventories(search, page, perpage, sortby, reverse, auth.getTokenData());

            List<LinkedHashMap<String, Object>> exceldata = new ArrayList<>();

            int i = 1;
            for (var item : invpage.getContent()) {
                var data = new LinkedHashMap<String, Object>();
                data.put("No.", String.valueOf(i++));
                data.put("Ref.", item.getItemref());
                data.put("Label", item.getLabel());
                data.put("Inventory Code", item.getItemcode());
                data.put("Price", String.valueOf(item.getPrice()));
                data.put("Remaining", String.valueOf(item.getRemaining()));
                data.put("Tags", item.getTag());
                data.put("Remark", item.getRemark());
                exceldata.add(data);
            }

            var out = ExcelUtils.writeExcel(exceldata, "Inventory");
            var resource = new ByteArrayResource(out.toByteArray());
            var headers = new HttpHeaders();
            var lDate = LocalDate.now();
            var exporteddate = String.valueOf(lDate.getDayOfMonth()) + String.valueOf(lDate.getMonthValue())
                    + String.valueOf(lDate.getYear());
            headers.add(HttpHeaders.CONTENT_DISPOSITION,
                    String.format("attachment; filename=inventory-%s.xls", exporteddate));
            return ResponseEntity.ok().headers(headers).contentLength(out.toByteArray().length)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(BaseResponse.internalServerError());
        }
    }

    @GetMapping
    public ResponseEntity<BaseResponse> getInventories(@RequestParam(required = false) String search,
            @RequestParam(defaultValue = "1") int page, @RequestParam int perpage,
            @RequestParam(defaultValue = "createddate") String sortby, @RequestParam(defaultValue = "1") String reverse,
            @RequestHeader("Authorization") String authorization) {
        try {
            var auth = authMiddleware.checkToken(authorization);
            if (!auth.isAuth()) {
                return auth.getResponse();
            }
            var invpage = iService.getInventories(search, page, perpage, sortby, reverse, auth.getTokenData());
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

    @GetMapping(value = "inventory-combo")
    public ResponseEntity<BaseResponse> getInventoriesForCombo(@RequestHeader("Authorization") String authorization) {
        try {
            var auth = authMiddleware.checkToken(authorization);
            if (!auth.isAuth()) {
                return auth.getResponse();
            }
            return ResponseEntity.ok().body(BaseResponse.ok(iService.getInventoriesForCombo(auth.getTokenData())));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(BaseResponse.internalServerError());
        }
    }

}
