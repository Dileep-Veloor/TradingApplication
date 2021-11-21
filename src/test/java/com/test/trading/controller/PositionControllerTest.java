package com.test.trading.controller;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.trading.fixture.TradingFixture;
import com.test.trading.service.PositionService;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PositionController.class)
public class PositionControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper mapper;
	
	@MockBean
	PositionService positionService;
	
	@Test
	void get_position_details_to_be_success() throws Exception {
		when(positionService.getPositionDetails()).thenReturn(TradingFixture.getPosition());
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/position");
		mockMvc.perform(mockRequest)
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(jsonPath("$", hasSize(2)));
	}
	
}
