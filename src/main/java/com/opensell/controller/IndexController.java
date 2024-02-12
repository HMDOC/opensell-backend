package com.opensell.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.opensell.entities.dto.AdView;
import com.opensell.entities.test.Factor;
import com.opensell.entities.test.Obj;
import com.opensell.entities.test.Prod;

@RestController
public class IndexController {
	/* We can send to different object if they implement the
	same interface and we see the different attribut, it work
	with Object too*/
	@GetMapping("/test/{option}")
	public Object test(@PathVariable int option) {
		switch (option) {
		case 1: return new Prod(100, "Test");
		
		case 2: return new Obj(22, "aDFFADS", "Hello");
		
		default: return null;
		}
	}
	
	@GetMapping("/test/int")
	public AdView ad() {
		return AdView.createDefault();
	}
}
