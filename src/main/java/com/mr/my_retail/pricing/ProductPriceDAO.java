package com.mr.my_retail.pricing;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="product_price_dao")
public class ProductPriceDAO {
	@Id
	protected Long id;
	protected Double value;
	protected String currency_code;
	
	public ProductPriceDAO() {
		
	}
	
	public ProductPriceDAO(Long id, Double value, String currencyCode) {
		super();
		this.id = id;
		this.value = value;
		this.currency_code = currencyCode;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public String getCurrency_code() {
		return currency_code;
	}
	public void setCurrency_code(String currency_code) {
		this.currency_code = currency_code;
	}
}

