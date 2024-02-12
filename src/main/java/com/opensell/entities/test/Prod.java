package com.opensell.entities.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class Prod implements Factor {
	private int idProd;
	private String name;
}
