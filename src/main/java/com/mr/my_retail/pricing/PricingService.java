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

	public ProductPriceDAO getProducts(Long id) {
		log.debug("PricingService.getProducts() Fetching current_price Details for the given Proudct = " + id);
		
		Optional<ProductPriceDAO> pricing = pricingRepository.findById(id);
		if (!pricing.isPresent())
			throw new PricingNotFoundException("id-" + id);

		return pricing.get();
	}
	
	public Iterable<ProductPriceDAO> getProducts() {
		log.debug("PricingService.getProducts() Fetching all product pricing Details");
		return pricingRepository.findAll();
	}

	ResponseEntity<Object> addProduct(ProductPrice newPrice) {
		log.debug("PricingService.addProduct() Creating new product price, for Product id = "+newPrice.getId());

		ProductPriceDAO savedPricing = pricingRepository.save(new ProductPriceDAO(newPrice.getId(),newPrice.getValue(), newPrice.getCurrency_code()));

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedPricing.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}

	ResponseEntity<Object> updateProduct(ProductPrice price, Long id) {
		log.debug("PricingService.updateProduct() Updating product price, for id = "+id);
	
		Optional<ProductPriceDAO> pricingOptional = pricingRepository.findById(id);

		if (!pricingOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		ProductPriceDAO prodPriceDAO = pricingOptional.orElseGet(() -> new ProductPriceDAO());
		
		prodPriceDAO.setId(id);
		prodPriceDAO.setValue(price.getValue());
		prodPriceDAO.setCurrency_code(price.getCurrency_code());
		pricingRepository.save(prodPriceDAO);

		return ResponseEntity.noContent().build();
	}

	void deleteProduct(Long id) {
		log.debug("PricingService.deleteProduct() deleting product price, for id = "+id);
		pricingRepository.deleteById(id);
	}
}
