package com.techhype.digitalinventory.Inventory;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IInventoryRepository extends JpaRepository<Inventory, Long> {
    // @Query("SELECT i FROM Inventory i WHERE i.itemref = ?1")
    Optional<Inventory> findInventoryByItemref(String itemref);
}
