package com.dorogo.MoneyCalculatorService;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.Charset;

@SpringBootTest
@AutoConfigureMockMvc
class MoneyCalculatorServiceApplicationTests {

	//TODO
	/*
	@Autowired
	private MockMvc mvc;

	@Test
	public void getHello() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string(Matchers.equalTo("hello")));
	}

	@Test
	public void testProcessValidList() throws Exception {
		MediaType textPlainUtf8 = new MediaType(MediaType.TEXT_PLAIN, Charset.forName("UTF-8"));
		String requestParam = "[{\"name\":\"test0\",\"spent\":\"0\"},{\"name\":\"test1\",\"spent\":\"100\"},{\"name\":\"test2\",\"spent\":\"500\"}]";
		mvc.perform(MockMvcRequestBuilders.post("/process")
				.content(requestParam)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(textPlainUtf8));
	}

	@Test
	public void testProcessInvalidList() throws Exception {
		//empty name
		String requestParam = "[{\"name\":\"\",\"spent\":\"0\"},{\"name\":\"test1\",\"spent\":\"100\"},{\"name\":\"test2\",\"spent\":\"500\"}]";
		mvc.perform(MockMvcRequestBuilders.post("/process").content(requestParam).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
		//no name field
		requestParam = "[{\"spent\":\"0\"},{\"name\":\"test1\",\"spent\":\"100\"},{\"name\":\"test2\",\"spent\":\"500\"}]";
		mvc.perform(MockMvcRequestBuilders.post("/process").content(requestParam).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
		//no fields
		requestParam = "[{},{\"name\":\"test1\",\"spent\":\"100\"},{\"name\":\"test2\",\"spent\":\"500\"}]";
		mvc.perform(MockMvcRequestBuilders.post("/process").content(requestParam).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
		//has negative spent field
		requestParam = "[{\"name\":\"test0\",\"spent\":\"-5\"},{\"name\":\"test1\",\"spent\":\"100\"},{\"name\":\"test2\",\"spent\":\"500\"}]";
		mvc.perform(MockMvcRequestBuilders.post("/process").content(requestParam).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

	}
*/
}
