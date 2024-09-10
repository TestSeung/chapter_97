package com.github.supercoding.repository.storeSales;

import java.util.Objects;

public class StoreSales {
    private Integer id;
    private String StoreName;
    private Integer amount;

    public StoreSales(Integer id, String storeName, Integer amount) {
        this.id = id;
        StoreName = storeName;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String storeName) {
        StoreName = storeName;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoreSales that = (StoreSales) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
