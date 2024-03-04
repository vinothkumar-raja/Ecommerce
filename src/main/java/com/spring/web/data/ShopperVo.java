package com.spring.web.data;


import java.util.List;

import lombok.Data;

@Data
public class ShopperVo {
    private String shopperId;
    private List<ShelfItemVo> shelf;
    private List<ProductVo> products;

    public String getShopperId() {
        return shopperId;
    }

    public void setShopperId(String shopperId) {
        this.shopperId = shopperId;
    }

    public List<ShelfItemVo> getShelf() {
        return shelf;
    }

    public void setShelf(List<ShelfItemVo> shelf) {
        this.shelf = shelf;
    }
}

