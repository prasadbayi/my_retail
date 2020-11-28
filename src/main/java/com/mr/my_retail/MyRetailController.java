package com.mr.my_retail;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("my_retail")
public class MyRetailController {

	private Logger log = LogManager.getLogger(MyRetailController.class);
	
	@Autowired(required = true)
	private MyRetailService myRetailService;

	@GetMapping("/products/{id}")
	public Product getProducts(@PathVariable Long id) {
		log.debug("MyRetailController.getProducts():: Fetching Proudct details for id = " + id);
		return myRetailService.getProducts(id);
	}
}
