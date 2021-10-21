package com.techhype.digitalinventory.Inventory;

public class InventoryDto {
    private String itemref;
    private String itemcode;
    private double price;
    private String label;
    private int remaining;

    public InventoryDto(String itemref, String itemcode, double price, String label, int remaining) {
        this.itemref = itemref;
        this.itemcode = itemcode;
        this.price = price;
        this.label = label;
        this.remaining = remaining;
    }

    public int getRemaining() {
        return remaining;
    }

    public void setRemaining(int remaining) {
        this.remaining = remaining;
    }

    public String getItemref() {
        return itemref;
    }

    public void setItemref(String itemref) {
        this.itemref = itemref;
    }

    public String getItemcode() {
        return itemcode;
    }

    public void setItemcode(String itemcode) {
        this.itemcode = itemcode;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}
