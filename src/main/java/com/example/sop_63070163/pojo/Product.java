package com.example.sop_63070163.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document("Product")
public class Product implements Serializable {
    @Id
    private String _id;
    private String ProductName;
    private Double ProductCost;
    private Double ProductProfit;
    private Double ProductPrice;

    public Product() {}

    public Product(String _id, String productName, Double productCost, Double productProfit, Double productPrice) {
        this._id = _id;
        ProductName = productName;
        ProductCost = productCost;
        ProductProfit = productProfit;
        ProductPrice = productPrice;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public Double getProductCost() {
        return ProductCost;
    }

    public void setProductCost(Double productCost) {
        ProductCost = productCost;
    }

    public Double getProductProfit() {
        return ProductProfit;
    }

    public void setProductProfit(Double productProfit) {
        ProductProfit = productProfit;
    }

    public Double getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(Double productPrice) {
        ProductPrice = productPrice;
    }
}
