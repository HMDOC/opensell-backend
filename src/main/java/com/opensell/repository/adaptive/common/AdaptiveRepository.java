package com.opensell.repository.adaptive.common;

import java.util.LinkedHashSet;
import java.util.Map;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public interface AdaptiveRepository {
	public int updateWithId(Map<String, Object> json);
}

class AdaptiveRepositoryImpl extends Adaptive implements AdaptiveRepository {
	@Override
	public int updateWithId(Map<String, Object> json) {
		try {
			NamedParameterJdbcTemplate npj = new NamedParameterJdbcTemplate(super.dataSource);
			super.createAssign(new LinkedHashSet<>(json.keySet()));

			return npj.update("UPDATE ad SET title = 'HAMIDIS DIS TOTO' WHERE id_ad = :id_ad", json);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
}