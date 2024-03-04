package com.spring.web.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.web.data.ProductVo;
import com.spring.web.data.ShelfItemVo;
import com.spring.web.data.ShopperVo;
import com.spring.web.entity.Product;
import com.spring.web.entity.ShelfItem;
import com.spring.web.entity.Shopper;
import com.spring.web.exception.NoDataException;
import com.spring.web.repository.ProdcutRepository;
import com.spring.web.repository.ShopperRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class ECommerceProcessingService {

	@PersistenceContext
    private EntityManager entityManager;
	 
	@Autowired
	private ShopperRepository shopperRepository;

	@Autowired
	private ProdcutRepository productRepository;

	public ProductVo getProductInformation(Long id) {
		Optional<Product> opProduct = productRepository.findById(id);
		if (opProduct.isPresent()) {
			return convertEntityToVo(opProduct.get());
		} else {
			throw new NoDataException("No product information found for the product id passed to the system!");
		}
	}

	private List<ProductVo> convertEntityToVo(List<Product> products) {
		List<ProductVo> list = new ArrayList<>();
		for(Product product :products) {
			 list.add(convertEntityToVo(product));
		}
		return list;
		
	}
	
	private ProductVo convertEntityToVo(Product product) {
		ProductVo productVo = new ProductVo();
		productVo.setBrand(product.getBrand());
		productVo.setCategory(product.getCategory());
		productVo.setProductId(product.getProductId());
		return productVo;
	}

	public String save(ShopperVo data) {
		shopperRepository.save(convertVoToEntity(data));
		return "Data Saved successfully!!";
	}

	private Shopper convertVoToEntity(ShopperVo ShopperVo) {
		List<ShelfItem> shelfItemList = new ArrayList<>();
		for(ShelfItemVo shelfItemVo :ShopperVo.getShelf()) {
			ShelfItem shelfItem = new ShelfItem();
			shelfItem.setProductId(shelfItemVo.getProductId());
			shelfItem.setRelevancyScore(shelfItemVo.getRelevancyScore());
			shelfItemList.add(shelfItem);
		}
		
		Shopper shopper = new Shopper();
		shopper.setShopperId(ShopperVo.getShopperId());
		shopper.setShelf(shelfItemList);
		return shopper;
	}

	public ShopperVo getFilteredShopperInformation(String shopperId, String category, String brand, int limit) {
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		List<Predicate> predicates = new ArrayList<>();
		
		if (category != null) {
            predicates.add(criteriaBuilder.equal(root.get("category"), category));
        }
		
		if (brand != null) {
            predicates.add(criteriaBuilder.equal(root.get("brand"), brand));
        }
		
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		
		
		List<Product> product = entityManager.createQuery(criteriaQuery).setMaxResults(limit).getResultList();
		
		ShopperVo shopperVo = new ShopperVo();
		shopperVo.setShopperId(shopperId);
		shopperVo.setProducts(convertEntityToVo(product));
		
		return shopperVo;
	}

}
