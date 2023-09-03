package com.chtrembl.petstore.product.data;

import com.chtrembl.petstore.product.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> { }
