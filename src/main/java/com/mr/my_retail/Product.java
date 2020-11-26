package com.mr.my_retail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {
	private Long id;
	private String name;
	private Product.current_price current_price;
	
	public Product() {
		super();
		current_price = new current_price();
	}
	
	public Product(Long productId, String productName, Product.current_price pricing) {
		super();
		this.id = productId;
		this.name = productName;
		this.current_price = pricing;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long productId) {
		this.id = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String productName) {
		this.name = productName;
	}
	
	public Product.current_price getCurrent_price() {
		return current_price;
	}

	public void setCurrent_price(Product.current_price productPrice) {
		this.current_price.setValue(productPrice.getValue());
		this.current_price.setCurrency_code(productPrice.getCurrency_code());
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class current_price {
		private Double value;
		private String currency_code;
		
		public current_price() {
			super();
		}
		public current_price(Double value, String currencyCode) {
			super();
			this.value = value;
			this.currency_code = currencyCode;
		}
		
		public String getCurrency_code() {
			return currency_code;
		}
		public void setCurrency_code(String currency_code) {
			this.currency_code = currency_code;
		}
		public Double getValue() {
			return value;
		}
		public void setValue(Double value) {
			this.value = value;
		}
	}
}
