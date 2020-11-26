package com.mr.my_retail;

import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("my_retail")
public class MyRetailController {

	private Logger log = LogManager.getLogger(MyRetailController.class);
	private RestTemplate restTemplate;

	@Value("${myapp.catalog.endpoint}")
	private String catalogEndPoint;
	
	@Value("${myapp.pricing.endpoint}")
	private String pricingEndPoint;
	
	public MyRetailController() {
		restTemplate = new RestTemplate();
	}
	
	@GetMapping("/products/{id}")
	public Product getProducts(@PathVariable Long id) {
		//Example response: {"id":13860428,"name":"The Big Lebowski (Blu-ray) (Widescreen)","current_price":{"value": 13.49,"currency_code":"USD"}}
		log.debug("MyRetailController.getProducts():: Fetching Proudct details for id = " + id);
	
		Product responseBody = getProduct(id);
		Product.current_price currentPrice = getCurrentPrice(responseBody.getId());
		responseBody.setCurrent_price(currentPrice);
		return responseBody;
	}
	
	private Product getProduct(Long id) {
		String urlString = catalogEndPoint+id;
		String endpoint = "";
		try {
			URL url = new URL(urlString);
			endpoint = url.toString();
		}
		catch(Exception e) {
			log.error("MyRetailController.getProduct()"+ e.getStackTrace().toString());
		}
		Product responseBody = restTemplate.getForObject(endpoint, Product.class);
		return responseBody;	
	}
	
	private Product.current_price getCurrentPrice(Long id) {
		String urlString = pricingEndPoint+id;
		String endpoint = "";
		try {
			URL url = new URL(urlString);
			endpoint = url.toString();
		}
		catch(Exception e) {
			log.error("MyRetailController.getCurrentPrice()"+ e.getStackTrace().toString());
		}
		final Product.current_price current_price = restTemplate.getForObject(endpoint, Product.current_price.class);
		return current_price;	
	}
}
