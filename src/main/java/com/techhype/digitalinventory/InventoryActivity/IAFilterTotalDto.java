package com.techhype.digitalinventory.InventoryActivity;

public class IAFilterTotalDto {
    private long totalqty;
    private double totalamount;

    public IAFilterTotalDto(long totalqty, double totalamount) {
        this.totalqty = totalqty;
        this.totalamount = totalamount;
    }

    public long getTotalqty() {
        return totalqty;
    }

    public void setTotalqty(long totalqty) {
        this.totalqty = totalqty;
    }

    public double getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(double totalamount) {
        this.totalamount = totalamount;
    }

}
