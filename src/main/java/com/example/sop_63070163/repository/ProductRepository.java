package com.example.sop_63070163.repository;

import com.example.sop_63070163.pojo.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    @Query(value = "{ProductName:  '?0'}")
    public Product findByName(String name);

    @Query(value = "{_id:  '?0'}")
    public Product findBy_id(String id);
}
