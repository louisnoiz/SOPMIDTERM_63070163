package com.example.sop_63070163.repository;

import com.example.sop_63070163.pojo.Product;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;
    public boolean addProduct(Product product){
        try {
            repository.insert(product);
            return true;
        }catch (Exception exception){
            return false;
        }
    }
    public boolean deleteProduct(Product product){
        try {repository.delete(product);
            return true;
        }catch (Exception exception){
            return false;
        }
    }

    public boolean updateProduct(Product product){
        try {
            repository.save(product);
            return true;
        }catch (Exception exception){
            return false;
        }
    }
    public List<Product> getAllProduct(){
        try {
            return repository.findAll();
        }catch (Exception exception){
            return null;
        }
    }
    public Product getProductByName(String name){
        try {
            return repository.findByName(name);
        }catch (Exception exception){
            return null;
        }
    }


}
