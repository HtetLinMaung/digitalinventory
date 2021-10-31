package com.techhype.digitalinventory.shop;

import java.time.LocalDateTime;
import java.util.Optional;

import com.techhype.digitalinventory.models.TokenData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ShopService {
    private IShopRepo isRepo;
    private IShopMapRepo ismRepo;

    @Autowired
    public ShopService(IShopRepo isRepo, IShopMapRepo ismRepo) {
        this.isRepo = isRepo;
        this.ismRepo = ismRepo;
    }

    public Optional<Shop> getShopByShopid(String shopid, TokenData tokenData) {
        var role = tokenData.getRole();
        if (role.equals("admin")) {
            return isRepo.findByShopidAndCompanyidAndStatus(shopid, tokenData.getCompanyid(), 1);
        } else {
            return isRepo.findByShopidAndStatus(shopid, 1);
        }
    }

    public Shop addShop(Shop shop, TokenData tokenData) {
        var now = LocalDateTime.now();
        shop.setId(null);
        shop.setCreateddate(now);
        shop.setModifieddate(now);
        shop.setUserid(tokenData.getUserid());
        shop.setUsername(tokenData.getUsername());
        shop.setCompanyid(tokenData.getCompanyid());
        shop.setCompanyname(tokenData.getCompanyname());
        var newshop = isRepo.save(shop);
        newshop.setShopid(String.format("S%06d", newshop.getId()));
        return isRepo.save(newshop);
    }

    public boolean updateShop(String shopid, Shop shop, TokenData tokenData) {
        var data = getShopByShopid(shopid, tokenData);
        if (!data.isPresent()) {
            return false;
        }
        var oldshop = data.get();
        oldshop.setShopname(shop.getShopname());
        oldshop.setModifieddate(LocalDateTime.now());
        isRepo.save(oldshop);
        return true;
    }

    public boolean softDeleteShop(String shopid, TokenData tokenData) {
        var data = getShopByShopid(shopid, tokenData);
        if (!data.isPresent()) {
            return false;
        }
        var shop = data.get();
        shop.setStatus(0);
        isRepo.save(shop);
        return true;
    }

    public Page<Shop> getShops(String search, int page, int perpage, String sortby, String reverse,
            TokenData tokenData) {
        var role = tokenData.getRole();
        var pagable = PageRequest.of(page - 1, perpage,
                Sort.by(reverse.equals("1") ? Sort.Direction.DESC : Sort.Direction.ASC, sortby));

        if (search == null || search.isEmpty()) {
            if (role.equals("admin")) {
                return isRepo.findByCompanyidAndStatus(tokenData.getCompanyid(), 1, pagable);
            } else {
                return isRepo.findByStatus(1, pagable);
            }
        }

        String s = search.trim();
        if (s.startsWith("\"") && s.endsWith("\"")) {
            s = s.replaceAll("\"", "");
            if (role.equals("admin")) {
                return isRepo.findByCompanyidAndStatusAndShopidOrCompanyidAndStatusAndShopname(tokenData.getCompanyid(),
                        1, s, tokenData.getCompanyid(), 1, s, pagable);
            } else {
                return isRepo.findByStatusAndShopidOrStatusAndShopname(1, s, 1, s, pagable);
            }
        }
        if (role.equals("admin")) {
            return isRepo.findByCompanyidAndStatusAndShopidContainingOrCompanyidAndStatusAndShopnameContaining(
                    tokenData.getCompanyid(), 1, s, tokenData.getCompanyid(), 1, s, pagable);
        } else {
            return isRepo.findByStatusAndShopidContainingOrStatusAndShopnameContaining(1, s, 1, s, pagable);
        }
    }

    public Optional<ShopMap> getShopMapByUserid(String userid, TokenData tokenData) {
        var role = tokenData.getRole();
        if (role.equals("admin")) {
            return ismRepo.findByUserrefAndCompanyidAndStatus(userid, tokenData.getCompanyid(), 1);
        } else {
            return ismRepo.findByUserrefAndStatus(userid, 1);
        }
    }

    public boolean mapShopWithUserid(ShopMap shopMap, TokenData tokenData) {
        var now = LocalDateTime.now();
        var data = getShopMapByUserid(shopMap.getUserref(), tokenData);
        if (data.isPresent()) {
            var old = data.get();
            old.setStatus(0);
            old.setModifieddate(now);
            ismRepo.save(old);
        }
        shopMap.setId(null);
        shopMap.setCreateddate(now);
        shopMap.setModifieddate(now);
        shopMap.setUserid(tokenData.getUserid());
        shopMap.setUsername(tokenData.getUsername());
        shopMap.setCompanyid(tokenData.getCompanyid());
        shopMap.setCompanyname(tokenData.getCompanyname());
        ismRepo.save(shopMap);
        return true;
    }
}
