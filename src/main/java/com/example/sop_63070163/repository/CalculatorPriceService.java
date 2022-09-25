package com.example.sop_63070163.repository;

import com.example.sop_63070163.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class CalculatorPriceService {
    @Autowired
    private ProductRepository repository;
    private Double resultPrice;

    public CalculatorPriceService(ProductRepository productRepository) {
        this.repository = productRepository;
    }

    public double getPrice(Double productCost, Double productProfit){
        resultPrice = productCost + productProfit;
        return resultPrice;
    }

}
