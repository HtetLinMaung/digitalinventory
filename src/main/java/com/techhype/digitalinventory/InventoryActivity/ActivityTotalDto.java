package com.techhype.digitalinventory.InventoryActivity;

public class ActivityTotalDto {
    private long total;
    private String invstatus;
    private String itemref;

    public ActivityTotalDto(long total, String invstatus, String itemref) {
        this.total = total;
        this.invstatus = invstatus;
        this.itemref = itemref;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public String getInvstatus() {
        return invstatus;
    }

    public void setInvstatus(String invstatus) {
        this.invstatus = invstatus;
    }

    public String getItemref() {
        return itemref;
    }

    public void setItemref(String itemref) {
        this.itemref = itemref;
    }

}
