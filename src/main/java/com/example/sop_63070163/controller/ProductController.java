package com.example.sop_63070163.controller;

import com.example.sop_63070163.pojo.Product;
import com.example.sop_63070163.repository.ProductService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;
    @RabbitListener(queues = "AddProductQueue")
    public boolean serviceAddProduct(Product product){
        boolean checkAdd = productService.addProduct(product);
        return checkAdd;
    }

    @RabbitListener(queues = "UpdateProductQueue")
    public boolean serviceUpdateProduct(Product product){
        boolean checkUpdate = productService.updateProduct(product);
        return checkUpdate;
    }

    @RabbitListener(queues = "DeleteProductQueue")
    public boolean serviceDeleteProduct(Product product){
        boolean checkDelete = productService.deleteProduct(product);
        return checkDelete;
    }

    @RabbitListener(queues = "GetNameProductQueue")
    public Product serviceGetProductName(String name){
        try {
            Product wantedProduct = productService.getProductByName(name);
            return wantedProduct;
        }catch (Exception exception){
            return null;
        }
    }

    @RabbitListener(queues = "GetAllProductQueue")
    public List<Product> serviceGetAllProduct(){
        try {
            List<Product> products = productService.getAllProduct();
            return products;
        }catch (Exception exception){
            return null;
        }
    }


}
