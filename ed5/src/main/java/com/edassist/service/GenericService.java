package com.edassist.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.edassist.exception.ExceededMaxResultsException;

public interface GenericService<T> {

	List<T> findAll() throws ExceededMaxResultsException;

	T findById(Long entityId);

	List<T> findByParam(String fieldName, Object value);

	List<T> findByParam(String fieldName, Object value, String sortField) throws DataAccessException, ExceededMaxResultsException;

	List<T> findByParam(String fieldName, Object value, String sortField, boolean sortAscending) throws DataAccessException, ExceededMaxResultsException;

	List<T> findByParam(String fieldName, Object value, String sortField, String sortAlias) throws DataAccessException, ExceededMaxResultsException;

	void saveOrUpdate(T entity);

	T merge(T entity);

	void remove(T entity);

	void remove(Long id);

	T findByUniqueParam(String fieldName, Object value) throws DataAccessException;

	List<T> findByNamedParam(String query, String[] paramNames, Object[] values) throws DataAccessException, ExceededMaxResultsException;

	List<T> findByParams(String[] paramNames, Object[] values, String sortField, String distinctField) throws DataAccessException, ExceededMaxResultsException;

	List<T> findByParams(String[] paramNames, Object[] values, String sortField, String distinctField, String sortOrder) throws DataAccessException, ExceededMaxResultsException;

	T findEntityByParams(String[] paramNames, Object[] values, String sortField, String distinctField);

	T findById(Integer entityId);
}
