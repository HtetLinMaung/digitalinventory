package com.techhype.digitalinventory.shop;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IShopMapRepo extends JpaRepository<ShopMap, Long> {

}
