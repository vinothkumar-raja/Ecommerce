package com.spring.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.web.entity.Product;

@Repository
public interface ProdcutRepository  extends JpaRepository<Product, Long> {


	
	
}
