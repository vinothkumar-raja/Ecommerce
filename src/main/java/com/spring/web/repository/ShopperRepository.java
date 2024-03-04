package com.spring.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.web.entity.Shopper;


@Repository
public interface ShopperRepository extends JpaRepository<Shopper, Long> {
	

}
