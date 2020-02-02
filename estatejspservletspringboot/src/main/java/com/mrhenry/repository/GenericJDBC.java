package com.mrhenry.repository;

import java.util.List;
import java.util.Map;

import com.mrhenry.paging.Pageable;

public interface GenericJDBC<T> {
//	List<T> query(Object ... parameters);
	void update(Object entity, Long id);
	Long insert(Object entity);
	T findById(Long id);
	void delete(Long id, String sql);
	List<T> findAll(Map<String, Object> properties, Pageable pageable, Object ... where);
	Integer countAll(Map<String, Object> properties, Object ... where);
}
