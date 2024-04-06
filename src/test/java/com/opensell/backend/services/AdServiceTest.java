package com.opensell.backend.services;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
import java.util.ArrayList;
import java.util.List;

@SpringBootTest()
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
	void adSearchWithPrice() {
		/*List<AdSearchPreview> adList = adService.adSearch("", 100.0, 1000.0,
				null, null,
				null, null, null, null, "addedDate", false);
        assertEquals(6, adList.size());*/
	}

	/**
	 * @author Davide Fuoco
	 */
	@Test
	void adSearchWithQuery() {
		/*List<AdSearchPreview> adList = adService.adSearch("Lorem", null, null,
				null, null,
				null, null, null, null, "addedDate", false);
        assertEquals(3, adList.size());*/
	}

	/**
	 * @author Davide Fuoco
	 */
	@Test
	void adSearchWithDate() {
		/*List<AdSearchPreview> adList = adService.adSearch("", null, null,
				Date.valueOf("2024-01-01"), Date.valueOf("2024-01-15"),
				null, null, null, null, "addedDate", false);
        assertEquals(4, adList.size());*/
	}
	
	/**
	 * @author Davide Fuoco
	 */
	@Test
	void adSearchWithImpossibleDate() {
		/*List<AdSearchPreview> adList = adService.adSearch("", null, null,
				Date.valueOf("2020-01-01"), Date.valueOf("2022-12-31"),
				null, null, null, null, "addedDate", false);
        assertEquals(0, adList.size());*/
	}


	/**
	 * @author Davide Fuoco
	 */
	@Test
	void adSearchWithType() {
		/*List<AdSearchPreview> adList = adService.adSearch("", null, null,
				null, null,
				8, null, null, null, "addedDate", false);
        assertEquals(2, adList.size());*/
	}


	/**
	 * @author Davide Fuoco
	 */
	@Test
	void adSearchWithShape() {
		/*List<AdSearchPreview> adList = adService.adSearch("", null, null,
				null, null,
				null, null, 2, null, "addedDate", false);
        assertEquals(15, adList.size());*/
	}


	/**
	 * @author Davide Fuoco
	 */
	@Test
	void adSearchWithShapeAndType() {
		/*List<AdSearchPreview> adList = adService.adSearch("", null, null,
				null, null,
				3, null, 2, null, "addedDate", false);
        assertEquals(1, adList.size());*/
	}


	/**
	 * @author Davide Fuoco
	 */
	@Test
	void adSearchWithPriceAndDate() {
		/*List<AdSearchPreview> adList = adService.adSearch("", 1500.0, 5000.0,
				Date.valueOf("2023-10-01"), Date.valueOf("2024-01-12"),
				null, null, null, null, "addedDate", false);*/
        //assertEquals(8, adList.size());
	}


	/**
	 * @author Davide Fuoco
	 */
	@Test
	void adSearchOrderByTitle() {
		/*List<AdSearchPreview> adList = adService.adSearch(new ArrayList<>(), null, null,
				null, null,
				null, null, null, null, "title", false);
		assertTrue(adList.get(0).adTitle().equalsIgnoreCase("NSX"));*/
	}


	/**
	 * @author Davide Fuoco
	 */
	@Test
	void adSearchOrderByPrice() {
		/*List<AdSearchPreview> adList = adService.adSearch(new ArrayList<>(), null, null,
				null, null,
				null, null, null, null, "price", false);
		// 0 is false
        assertEquals(0, adList.get(0).isAdSold());*/
	}


	/**
	 * @author Davide Fuoco
	 */
	@Test
	void adSearchOrderByDate() {
		/*List<AdSearchPreview> adList = adService.adSearch(new ArrayList<>(), 1000.0, 6000.0,
				null, null,
				null, null, null, null, "addedDate", false);
        assertEquals(3, adList.get(1).adShape());*/
	}
	

	/**
	 * @author Davide Fuoco
	 */
	@Test
	void adSearchFilterSold() {
		/*List<AdSearchPreview> adList = adService.adSearch(new ArrayList<>(), null, null,
				null, null, null, null, null, true, "addedDate", false);
        
		for(AdSearchPreview ad : adList) {
			if (!ad.isAdSold()) {
				fail("Ad " + ad.adLink() + " is not sold.");
				break;
			}
		}
		*/
	}
	
	@Test
	void adSearchFailTest() {
		/*List<AdSearchPreview> adList = adService.adSearch(new ArrayList<>(), null, null,
				null, null, null, null, null, false, "addedDate", false);
		assertNotEquals( 0, adList.get(0).adShape());*/
	}
	
	void adSearchErrorTest() {
		/*boolean flag = false;
		try {
			List<AdSearchPreview> adList = adService.adSearch(new ArrayList<>(), null, null,
					null, null, null, null, null, false, "addate", false);
		}catch(Exception e) {
			flag = true;
		}finally {
			if (flag) {
				assertTrue( flag );
			}else {
				fail("Program was meant to fail.");
			}
		}*/
	}

}