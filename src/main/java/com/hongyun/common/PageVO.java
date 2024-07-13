package com.hongyun.common;


import lombok.Data;

@Data
public class PageVO {
    private int total;
    private int pageSize = 25;
    private int pageNum = 1;
    private int pageTotal;

    public int getPageTotal() {
        if (total > 0) {
            if (total % pageSize != 0) {
                pageTotal = total / pageSize + 1;
            } else {
                pageTotal = total / pageSize;
            }

            return pageTotal;
        } else {
            return 0;
        }
    }
}
