package com.opensell.entities.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class Obj implements Factor {
	private int idObj;
	private String license;
	private String type;
}
