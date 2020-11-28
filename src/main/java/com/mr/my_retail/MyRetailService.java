package com.mr.my_retail;

import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MyRetailService {
	private Logger log = LogManager.getLogger(MyRetailService.class);
	private RestTemplate restTemplate;

	@Value("${myapp.catalog.endpoint}")
	private String catalogEndPoint;

	@Value("${myapp.pricing.endpoint}")
	private String pricingEndPoint;

	public MyRetailService() {
		restTemplate = new RestTemplate();
	}

	public Product getProducts(Long id) {
		// Example response: {"id":13860428,"name":"The Big Lebowski (Blu-ray)
		// (Widescreen)","current_price":{"value": 13.49,"currency_code":"USD"}}
		log.debug("MyRetailService.getProducts():: Fetching Proudct details for id = " + id);

		Product responseBody = getProduct(id);
		if (responseBody != null) {
			Product.current_price currentPrice = getCurrentPrice(responseBody.getId());
			responseBody.setCurrent_price(currentPrice);
		}
		return responseBody;
	}

	private Product getProduct(Long id) {
		String urlString = catalogEndPoint + id;
		String endpoint = "";
		try {
			URL url = new URL(urlString);
			endpoint = url.toString();
		} catch (Exception e) {
			log.error("MyRetailService.getProduct()" + e.getStackTrace().toString());
		}
		Product responseBody = restTemplate.getForObject(endpoint, Product.class);
		return responseBody;
	}

	private Product.current_price getCurrentPrice(Long id) {
		String urlString = pricingEndPoint + id;
		String endpoint = "";
		try {
			URL url = new URL(urlString);
			endpoint = url.toString();
		} catch (Exception e) {
			log.error("MyRetailService.getCurrentPrice()" + e.getStackTrace().toString());
		}
		final Product.current_price current_price = restTemplate.getForObject(endpoint, Product.current_price.class);
		return current_price;
	}
}
