package com.chtrembl.petstore.product.data;

import com.chtrembl.petstore.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT DISTINCT p FROM product p LEFT JOIN FETCH p.tags")
    List<Product> findAllWithTags();



}
