package com.mr.my_retail.pricing;

import java.net.URI;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class PricingService {
	private Logger log = LogManager.getLogger(PricingService.class);

	@Autowired
	private PricingRepository pricingRepository;

	public ProductPrice getProducts(Long id) {
		log.debug("PricingService.getProducts() Fetching current_price Details for the given Proudct = " + id);
		
		Optional<ProductPrice> pricing = pricingRepository.findById(id);
		if (!pricing.isPresent())
			throw new PricingNotFoundException("id-" + id);

		return pricing.get();
	}
	
	public Iterable<ProductPrice> getProducts() {
		log.debug("PricingService.getProducts() Fetching all product pricing Details");
		return pricingRepository.findAll();
	}

	ResponseEntity<Object> addProduct(ProductPrice newPrice) {
		log.debug("PricingService.addProduct() Creating new product price, for Product id = "+newPrice.getId());

		ProductPrice savedPricing = pricingRepository.save(newPrice);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedPricing.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}

	ResponseEntity<Object> updateProduct(ProductPrice price, Long id) {
		log.debug("PricingService.updateProduct() Updating product price, for id = "+id);
	
		Optional<ProductPrice> pricingOptional = pricingRepository.findById(id);

		if (!pricingOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		price.setId(id);
		pricingRepository.save(price);

		return ResponseEntity.noContent().build();
	}

	void deleteProduct(Long id) {
		log.debug("PricingService.deleteProduct() deleting product price, for id = "+id);
		pricingRepository.deleteById(id);
	}
}
