package com.opensell.backend.services;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.boot.test.context.SpringBootTest;
import com.opensell.controller.AdController;
import com.opensell.entities.Ad;
import com.opensell.entities.dto.AdSearchPreview;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class AdServiceTest {
	public static AdController adService;
	public static String goodLink;
	public static String wrongLink;
	public static String errorLink;

	@BeforeAll
	void setup() {
		goodLink = "113j4sh992z9VTQKDh6xjPmmdjDq52gLeE";
		wrongLink = "asdf89123412341234";
		errorLink = "%$-*!~`}[{@+\"''/";
		adService = new AdController();
	}

	@AfterEach
	void cleanAE() {

	}

	@AfterAll
	void cleanAA() {
		goodLink = null;
		wrongLink = null;
		errorLink = null;
	}

	@Test
	void getAdBuyerView() {
		assertTrue(adService.adBuyerView(goodLink) != null);
		//assertTrue(adService.adBuyerView(wrongLink) == null);
	}
	
	/*
	@Test
	void testGetFilteredSearch() {
		List<AdSearchPreview> adList = adService.adSearchPreview("E", 100, 300); 
		assertTrue(adList.size() == 2);
	}
	*/

}