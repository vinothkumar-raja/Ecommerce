package com.spring.web.controller;

import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.web.data.ProductVo;
import com.spring.web.data.ShopperVo;
import com.spring.web.exception.InvalidInputException;
import com.spring.web.service.ECommerceProcessingService;

import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class ECommerceProcessingController {

	@Autowired
	private ECommerceProcessingService commerceService;

	@GetMapping("/healthcheck")
	public ResponseEntity<String> checkServerStatus() {
		String strVal = "Server Available. Loaded Successfully ---- " + System.currentTimeMillis();
		log.info(strVal);
		return new ResponseEntity<>(strVal, HttpStatus.OK);
	}

	@GetMapping("/getProduct/{id}")
	public ResponseEntity<ProductVo> getVehicleInformation(@PathVariable Long prodcutId) {

		if (prodcutId == null || prodcutId == 0) {
			throw new InvalidInputException("Invalid Vehicle Id passed for processing!!");
		}

		return new ResponseEntity<>(commerceService.getProductInformation(prodcutId), HttpStatus.OK);
	}

	@PostMapping("shopper/save")
	public ResponseEntity<String> save(@RequestBody ShopperVo shopper) {

		if (shopper == null) {
			throw new InvalidInputException("Invalid Vehicle data passed for processing!!");
		}

		return new ResponseEntity<>(commerceService.save(shopper), HttpStatus.OK);
	}

	@GetMapping("/getProduct/{shopperId}")
	public ResponseEntity<ShopperVo> getVehicleInformation(@PathVariable String shopperId, @RequestParam(name = "category", required = false) String category,
			@RequestParam(name = "brand", required = false) String brand,
			@RequestParam(name = "limit", required = false) @Range(min = 1, max = 100, message = "Limit must be between 1 and 100") int limit) {

		if (StringUtils.isEmpty(shopperId)) {
			throw new InvalidInputException("Invalid Vehicle Id passed for processing!!");
		}

		return new ResponseEntity<>(commerceService.getFilteredShopperInformation(shopperId, category, brand, limit), HttpStatus.OK);
	}
}
