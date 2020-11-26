package com.mr.my_retail.pricing;

import java.net.URI;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("pricing")
public class PricingController {

	private Logger log = LogManager.getLogger(PricingController.class);

	@Autowired
	private PricingRepository pricingRepository;

	@GetMapping("/products/{id}")
	public ProductPrice getProducts(@PathVariable Long id) {
		log.debug("PricingController.getProducts() Fetching current_price Details for the given Proudct = " + id);
		
		Optional<ProductPrice> pricing = pricingRepository.findById(id);
		if (!pricing.isPresent())
			throw new PricingNotFoundException("id-" + id);

		return pricing.get();
	}
	
	@GetMapping("/products")
	public Iterable<ProductPrice> getProducts() {
		log.debug("PricingController.getProducts() Fetching all product pricing Details");
		return pricingRepository.findAll();
	}

	@PostMapping("/add_product")
	ResponseEntity<Object> addProduct(@RequestBody ProductPrice newPrice) {
		log.debug("PricingController.addProduct() Creating new product price.");

		ProductPrice savedPricing = pricingRepository.save(newPrice);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedPricing.getId()).toUri();

		return ResponseEntity.created(location).build();
	}

	@PutMapping("/update_product/{id}")
	ResponseEntity<Object> updateProduct(@RequestBody ProductPrice price, @PathVariable Long id) {
		log.debug("PricingController.updateProduct() Creating new product price.");
	
		Optional<ProductPrice> pricingOptional = pricingRepository.findById(id);

		if (!pricingOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		price.setId(id);
		pricingRepository.save(price);

		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/delete_product/{id}")
	void deleteProduct(@PathVariable Long id) {
		log.debug("PricingController.deleteProduct() deleting product price, for id = "+id);
		pricingRepository.deleteById(id);
	}
}
