package com.opensell.backend.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.boot.test.context.SpringBootTest;

import com.opensell.controller.AdController;
import com.opensell.entities.dto.AdSearchPreview;

import java.sql.Date;
import java.util.List;

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

	/**
	 * @author Davide Fuoco
	 */
	@Test
	void testAdSearchWithPrice() {
		List<AdSearchPreview> adList = adService.adSearch("", 100.0, 1000.0,
				Date.valueOf("2020-01-01"), Date.valueOf("3000-01-01"),
				null, null, null, "addedDate");
		assertEquals(adList.size(), 6);
	}

	/**
	 * @author Davide Fuoco
	 */
	@Test
	void testAdSearchWithQuery() {
		List<AdSearchPreview> adList = adService.adSearch("Lorem", 0.0, 9999999.0,
				Date.valueOf("2020-01-01"), Date.valueOf("3000-01-01"),
				null, null, null, "addedDate");
		assertEquals(adList.size(), 3);
	}

	/**
	 * @author Davide Fuoco
	 */
	@Test
	void testAdSearchWithDate() {
		List<AdSearchPreview> adList = adService.adSearch("", 0.0, 9999999.0,
				Date.valueOf("2024-01-01"), Date.valueOf("2024-01-12"),
				null, null, null, "addedDate");
		assertEquals(adList.size(), 4);
	}
	
	/**
	 * @author Davide Fuoco
	 */
	@Test
	void testAdSearchWithImpossibleDate() {
		List<AdSearchPreview> adList = adService.adSearch("", 0.0, 9999999.0,
				Date.valueOf("2020-01-01"), Date.valueOf("2022-12-31"),
				null, null, null, "addedDate");
		assertEquals(adList.size(), 0);
	}


	/**
	 * @author Davide Fuoco
	 */
	@Test
	void testAdSearchWithType() {
		List<AdSearchPreview> adList = adService.adSearch("", 0.0, 9999999.0,
				Date.valueOf("2020-01-01"), Date.valueOf("3000-01-01"),
				8, null, null, "addedDate");
		assertEquals(adList.size(), 2);
	}


	/**
	 * @author Davide Fuoco
	 */
	@Test
	void testAdSearchWithShape() {
		List<AdSearchPreview> adList = adService.adSearch("", 0.0, 9999999.0,
				Date.valueOf("2020-01-01"), Date.valueOf("3000-01-01"),
				null, null, 2, "addedDate");
		assertEquals(adList.size(), 15);
	}


	/**
	 * @author Davide Fuoco
	 */
	@Test
	void testAdSearchWithShapeAndType() {
		List<AdSearchPreview> adList = adService.adSearch("", 0.0, 9999999.0,
				Date.valueOf("2020-01-01"), Date.valueOf("3000-01-01"),
				3, null, 2, "addedDate");
		assertEquals(adList.size(), 1);
	}


	/**
	 * @author Davide Fuoco
	 */
	@Test
	void testAdSearchWithPriceAndDate() {
		List<AdSearchPreview> adList = adService.adSearch("", 1500.0, 5000.0,
				Date.valueOf("2023-10-01"), Date.valueOf("2024-01-12"),
				null, null, null, "addedDate");
		assertEquals(adList.size(), 7);
	}


	/**
	 * @author Davide Fuoco
	 */
	@Test
	void testAdSearchOrderByTitle() {
		List<AdSearchPreview> adList = adService.adSearch("Lorem", 0.0, 9999999.0,
				Date.valueOf("2020-01-01"), Date.valueOf("3000-01-01"),
				null, null, null, "title");
		assertTrue(adList.get(0).adTitle().equalsIgnoreCase("NSX"));
	}


	/**
	 * @author Davide Fuoco
	 */
	@Test
	void testAdSearchOrderByPrice() {
		List<AdSearchPreview> adList = adService.adSearch("Ipsum", 0.0, 9999999.0,
				Date.valueOf("2020-01-01"), Date.valueOf("3000-01-01"),
				null, null, null, "price");
		assertEquals(adList.get(0).isAdSold(), 0);
	}


	/**
	 * @author Davide Fuoco
	 */
	@Test
	void testAdSearchOrderByDate() {
		List<AdSearchPreview> adList = adService.adSearch("Dolor", 1000.0, 6000.0,
				Date.valueOf("2020-01-01"), Date.valueOf("3000-01-01"),
				null, null, null, "addedDate");
		assertEquals(adList.get(0).adShape(), 0);
	}

}