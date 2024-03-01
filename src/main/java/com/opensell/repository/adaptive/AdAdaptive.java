package com.opensell.repository.adaptive;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.opensell.repository.adaptive.common.Adaptive;

public interface AdAdaptive {
	public int adaptiveChange();
}

class AdAdaptiveImpl extends Adaptive implements AdAdaptive {
	@Override
	public int adaptiveChange() {
		try {
			System.out.println("Olivier");
			NamedParameterJdbcTemplate npj = new NamedParameterJdbcTemplate(super.dataSource);
			Map<String, Object> map = new LinkedHashMap<>();
			map.put("id_ad", 1);
			System.out.println(npj.update("UPDATE ad SET title = 'HAMIDIS DIS TOTO' WHERE id_ad = :id_ad", map));
		} catch (Exception e) {
		}
		
		return 0;
	}
	
}