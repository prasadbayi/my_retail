package com.mr.my_retail;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.mr.my_retail.pricing.ProductPrice;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyRetailApplication.class)
@AutoConfigureMockMvc
class MyRetailControllerTest {
		private static final String USD = "USD";

		private static ProductPrice mockProduct = new ProductPrice(new Long(15117729), new Double(15), USD);

		@Autowired
		private MockMvc mockMvc;
		
		@MockBean
		private MyRetailService myRetailService;

		@Test
		void testGetProductById() {
			try {
				ProductPrice mockProductPrice = new ProductPrice(mockProduct.getId(), mockProduct.getValue(), mockProduct.getCurrency_code());
				Product.current_price current_price = new Product.current_price(mockProductPrice.getValue(),mockProductPrice.getCurrency_code());
				Product product = new Product(mockProduct.getId(), "", current_price);
				
				Mockito.when(myRetailService.getProducts(Mockito.anyLong())).thenReturn(product);
				RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/my_retail/products/" + mockProductPrice.getId())
						.accept(MediaType.APPLICATION_JSON);

				MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

				int status = mvcResult.getResponse().getStatus();
				assertEquals(200, status);
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getMessage());
			}
		}
}
