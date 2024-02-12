package com.opensell.entities.dto;


public interface AdView {
	public static AdView createDefault() {
		return new AdView() {
			public int idAdView = 0;
		};
	}
}
