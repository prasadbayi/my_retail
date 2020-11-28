/**
 * 
 */
package com.mr.my_retail.pricing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mr.my_retail.MyRetailApplication;

/**
 * @author Prasad Bayi1
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyRetailApplication.class)
@AutoConfigureMockMvc
class PricingControllerTest {

	private static final String USD = "USD";
	private static final String INR = "INR";

	private static ProductPrice mockProduct = new ProductPrice(new Long(15117729), new Double(15), USD);

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private PricingRepository pricingRepository;

	private <T> T mapFromJson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}

	private static final String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}

	@Test
	void testGetProductById() {
		try {
			ProductPrice mockProductPrice = new ProductPrice(mockProduct.getId(), mockProduct.getValue(), mockProduct.getCurrency_code());
			
			Mockito.when(pricingRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(mockProductPrice));

			RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/pricing/products/" + mockProductPrice.getId())
					.accept(MediaType.APPLICATION_JSON);

			MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

			int status = mvcResult.getResponse().getStatus();
			assertEquals(200, status);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	void testGetProducts() {

		try {
			RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/pricing/products/");

			MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

			int status = mvcResult.getResponse().getStatus();
			assertEquals(200, status);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	void testUpdateProduct() {
		try {
		
			ProductPrice mockProductPrice = new ProductPrice(mockProduct.getId(), mockProduct.getValue(), mockProduct.getCurrency_code());
			
			Mockito.when(pricingRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(mockProductPrice));

			RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/pricing/update_product/"+mockProductPrice.getId())
					.contentType("application/json").content(mapToJson(mockProductPrice));

			MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

			int status = mvcResult.getResponse().getStatus();
			assertEquals(204, status);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	void testDeleteProductById() {
		try {
			ProductPrice mockProductPrice = new ProductPrice(mockProduct.getId(), mockProduct.getValue(), mockProduct.getCurrency_code());

			Mockito.doNothing().when(pricingRepository).deleteById(mockProductPrice.getId());
		    mockMvc.perform(
		    		MockMvcRequestBuilders.delete("/pricing/delete_product/"+ mockProductPrice.getId()))
		            .andExpect(status().isOk());
		    verify(pricingRepository, times(1)).deleteById(mockProductPrice.getId());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	void testAddProduct() {
		try {
			ProductPrice mockProductPrice = new ProductPrice(mockProduct.getId(), mockProduct.getValue(), mockProduct.getCurrency_code());
			
			Mockito.when(pricingRepository.save(Mockito.any(ProductPrice.class))).thenReturn(mockProductPrice);

			RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/pricing/add_product/")
					.contentType("application/json").content(mapToJson(mockProduct));

			MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

			int status = mvcResult.getResponse().getStatus();
			assertEquals(201, status);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
}
