package com.mr.my_retail.pricing;

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

@RestController
@RequestMapping("pricing")
public class PricingController {

	private Logger log = LogManager.getLogger(PricingController.class);

	@Autowired(required = true)
	private PricingService pricingService;

	@GetMapping("/products/{id}")
	public ProductPrice getProducts(@PathVariable Long id) {
		log.debug("PricingController.getProducts() Fetching current_price Details for the given Proudct = " + id);
		return pricingService.getProducts(id);
	}
	
	@GetMapping("/products")
	public Iterable<ProductPrice> getProducts() {
		log.debug("PricingController.getProducts() Fetching all product pricing Details");
		return pricingService.getProducts();
	}

	@PostMapping("/add_product")
	public ResponseEntity<Object> addProduct(@RequestBody ProductPrice newPrice) {
		log.debug("PricingController.addProduct() Creating new product price, for Product id = "+newPrice.getId());
		return pricingService.addProduct(newPrice);
	}

	@PutMapping("/update_product/{id}")
	public ResponseEntity<Object> updateProduct(@RequestBody ProductPrice price, @PathVariable Long id) {
		log.debug("PricingController.updateProduct() Updating product price, for id = "+id);
		return pricingService.updateProduct(price, id);
	}

	@DeleteMapping("/delete_product/{id}")
	public void deleteProduct(@PathVariable Long id) {
		log.debug("PricingController.deleteProduct() deleting product price, for id = "+id);
		pricingService.deleteProduct(id);
	}
}
