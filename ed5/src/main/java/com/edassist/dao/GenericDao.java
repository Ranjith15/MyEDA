package com.edassist.dao;

import com.edassist.exception.ExceededMaxResultsException;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface GenericDao<T> {

	List<T> findAll() throws ExceededMaxResultsException;

	List<T> findAll(String sortField) throws ExceededMaxResultsException;

	T findById(Long id) throws DataAccessException;

	void saveOrUpdate(T entity) throws DataAccessException;

	T merge(T entity) throws DataAccessException;

	void remove(T entity) throws DataAccessException;

	void remove(Long id) throws DataAccessException;

	void removeAll() throws DataAccessException, ExceededMaxResultsException;

	List<T> findByNamedParam(String query, String[] paramNames, Object[] values) throws DataAccessException, ExceededMaxResultsException;

	List<T> findByParams(String[] paramNames, Object[] values, String sortField, String distinctField) throws DataAccessException, ExceededMaxResultsException;

	List<T> findByParams(String[] paramNames, Object[] values, String sortField, String distinctField, String sortOrder) throws DataAccessException, ExceededMaxResultsException;

	List<T> findByParam(String fieldName, Object value);

	List<T> findByParam(String fieldName, Object value, String sortField) throws DataAccessException, ExceededMaxResultsException;

	List<T> findByParam(String fieldName, Object value, String sortField, boolean sortAscending) throws DataAccessException, ExceededMaxResultsException;

	List<T> findByParam(String fieldName, Object value, String sortField, String sortAlias) throws DataAccessException, ExceededMaxResultsException;

	List<T> findByParamLike(String fieldName, Object value) throws DataAccessException, ExceededMaxResultsException;

	List<T> findByParamAndNull(String fieldName, Object value) throws DataAccessException, ExceededMaxResultsException;

	T findByUniqueParam(String fieldName, Object value) throws DataAccessException;

	void removeCommit(T entity) throws DataAccessException;

	List<T> findAllNotNull(String sortField, String fieldName) throws DataAccessException, ExceededMaxResultsException;

	void removeAll(String fieldName, Long value) throws DataAccessException, ExceededMaxResultsException;

	void evict(T entity);

	boolean exists(String fieldName, Object value);

	T findEntityByParams(String[] paramNames, Object[] values, String sortField, String distinctField);

	T findById(Integer id) throws DataAccessException;
}