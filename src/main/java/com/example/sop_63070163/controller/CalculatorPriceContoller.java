package com.example.sop_63070163.controller;

import com.example.sop_63070163.pojo.Product;
import com.example.sop_63070163.repository.CalculatorPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CalculatorPriceContoller {
    @Autowired
    private CalculatorPriceService calculatorPriceService;
    @RequestMapping(value = "/getPrice/{cost}/{profit}", method = RequestMethod.GET)
    public ResponseEntity<?> serviceGetProducts(@PathVariable("cost") double cost, @PathVariable("profit") double profit) {
        Double productPrice = calculatorPriceService.getPrice(cost, profit);
        return ResponseEntity.ok(productPrice);
    }
}
