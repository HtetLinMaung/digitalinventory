package com.techhype.digitalinventory.shop;

import com.techhype.digitalinventory.constants.ServerMessage;
import com.techhype.digitalinventory.constants.ServerStatus;
import com.techhype.digitalinventory.middlewares.AuthMiddleware;
import com.techhype.digitalinventory.models.BaseResponse;
import com.techhype.digitalinventory.models.PaginationResponse;

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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("api/v1/shops")
public class ShopController {
    private ShopService sService;
    private AuthMiddleware authMiddleware;

    @Autowired
    public ShopController(ShopService sService, AuthMiddleware authMiddleware) {
        this.sService = sService;
        this.authMiddleware = authMiddleware;
    }

    @GetMapping(path = "{shopid}")
    public ResponseEntity<BaseResponse> getShopByShopid(@PathVariable String shopid,
            @RequestHeader("Authorization") String authorization) {
        try {
            var auth = authMiddleware.checkToken(authorization);
            if (!auth.isAuth()) {
                return auth.getResponse();
            }
            var shop = sService.getShopByShopid(shopid, auth.getTokenData());
            if (!shop.isPresent()) {
                return new ResponseEntity<>(BaseResponse.notFound(), HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok().body(BaseResponse.ok(shop.get()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(BaseResponse.internalServerError());
        }
    }

    @PostMapping
    public ResponseEntity<BaseResponse> addShop(@RequestBody Shop shop,
            @RequestHeader("Authorization") String authorization) {
        try {
            var auth = authMiddleware.checkToken(authorization);
            if (!auth.isAuth()) {
                return auth.getResponse();
            }
            var newshop = sService.addShop(shop, auth.getTokenData());
            var ref = newshop.getShopid();
            return new ResponseEntity<>(new BaseResponse(ServerStatus.CREATED, ServerMessage.Created(ref), newshop),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(BaseResponse.internalServerError());
        }
    }

    @PutMapping(path = "{shopid}")
    public ResponseEntity<BaseResponse> updateShop(@PathVariable String shopid, @RequestBody Shop shop,
            @RequestHeader("Authorization") String authorization) {
        try {
            var auth = authMiddleware.checkToken(authorization);
            if (!auth.isAuth()) {
                return auth.getResponse();
            }
            var success = sService.updateShop(shopid, shop, auth.getTokenData());
            if (!success) {
                return new ResponseEntity<>(BaseResponse.notFound(), HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok().body(new BaseResponse(ServerStatus.OK, ServerMessage.Updated(shopid), shopid));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(BaseResponse.internalServerError());
        }
    }

    @DeleteMapping(path = "{shopid}")
    public ResponseEntity<BaseResponse> softDeleteShop(@PathVariable String shopid,
            @RequestHeader("Authorization") String authorization) {
        try {
            var auth = authMiddleware.checkToken(authorization);
            if (!auth.isAuth()) {
                return auth.getResponse();
            }
            var success = sService.softDeleteShop(shopid, auth.getTokenData());

            if (!success) {
                return new ResponseEntity<>(BaseResponse.notFound(), HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(BaseResponse.internalServerError());
        }
    }

    @GetMapping
    public ResponseEntity<BaseResponse> getShops(@RequestParam(required = false) String search,
            @RequestParam(defaultValue = "1") int page, @RequestParam int perpage,
            @RequestParam(defaultValue = "createddate") String sortby, @RequestParam(defaultValue = "1") String reverse,
            @RequestHeader("Authorization") String authorization) {
        try {
            var auth = authMiddleware.checkToken(authorization);
            if (!auth.isAuth()) {
                return auth.getResponse();
            }
            var shoppage = sService.getShops(search, page, perpage, sortby, reverse, auth.getTokenData());
            var body = new PaginationResponse();
            body.setData(shoppage.getContent());
            body.setPagecount(shoppage.getTotalPages());
            body.setTotal(shoppage.getTotalElements());
            body.setPage(page);
            body.setPerpage(perpage);
            return ResponseEntity.ok().body(body);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(BaseResponse.internalServerError());
        }
    }

}
