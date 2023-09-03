package com.chtrembl.petstore.pet.data;

import com.chtrembl.petstore.pet.model.Pet;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> { }