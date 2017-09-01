package com.edassist.service.impl;

import com.edassist.dao.GenericDao;
import com.edassist.exception.ExceededMaxResultsException;
import com.edassist.service.GenericService;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import java.util.List;

public class GenericServiceImpl<T> implements GenericService<T> {
	private static Logger log = Logger.getLogger(GenericServiceImpl.class);

	private GenericDao<T> dao;

	private String sortField;

	public GenericServiceImpl(GenericDao<T> dao) {
		this.dao = dao;
	}

	public GenericServiceImpl() {
	}

	public GenericDao<T> getDao() {
		return dao;
	}

	public void setDao(GenericDao<T> dao) {
		this.dao = dao;
	}

	@Override
	public List<T> findAll() throws ExceededMaxResultsException {
		return dao.findAll();
	}

	@Override
	public T findById(Long id) {
		return (T) dao.findById(id);
	}

	@Override
	public T findById(Integer id) {
		return (T) dao.findById(id);
	}

	@Override
	public T findEntityByParams(String[] paramNames, Object[] values, String sortField, String distinctField) {
		return (T) dao.findEntityByParams(paramNames, values, sortField, distinctField);
	}

	@Override
	public T merge(T entity) {
		return dao.merge(entity);
	}

	@Override
	public void remove(T entity) {
		dao.remove(entity);
	}

	@Override
	public void remove(Long id) {
		dao.remove(id);
	}

	@Override
	public void saveOrUpdate(T entity) {
		dao.saveOrUpdate(entity);
	}

	/**
	 * @return the sortField
	 */
	public String getSortField() {
		return sortField;
	}

	/**
	 * @param sortField the sortField to set
	 */
	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	@Override
	public List<T> findByParam(String fieldName, Object value) {
		return dao.findByParam(fieldName, value);
	}

	@Override
	public List<T> findByParam(String fieldName, Object value, String sortField) throws DataAccessException, ExceededMaxResultsException {
		return dao.findByParam(fieldName, value, sortField);
	}

	@Override
	public List<T> findByParam(String fieldName, Object value, String sortField, boolean sortAscending) throws DataAccessException, ExceededMaxResultsException {
		return dao.findByParam(fieldName, value, sortField, sortAscending);
	}

	@Override
	public List<T> findByParam(String fieldName, Object value, String sortField, String sortAlias) throws DataAccessException, ExceededMaxResultsException {
		return dao.findByParam(fieldName, value, sortField, sortAlias);
	}

	@Override
	public T findByUniqueParam(String fieldName, Object value) throws DataAccessException {
		return (T) dao.findByUniqueParam(fieldName, value);
	}

	@Override
	public List<T> findByNamedParam(String query, String[] paramNames, Object[] values) throws DataAccessException, ExceededMaxResultsException {
		return dao.findByNamedParam(query, paramNames, values);
	}

	@Override
	public List<T> findByParams(String[] paramNames, Object[] values, String sortField, String distinctField) throws DataAccessException, ExceededMaxResultsException {
		return dao.findByParams(paramNames, values, sortField, distinctField);
	}

	@Override
	public List<T> findByParams(String[] paramNames, Object[] values, String sortField, String distinctField, String sortOrder) throws DataAccessException, ExceededMaxResultsException {
		return dao.findByParams(paramNames, values, sortField, distinctField, sortOrder);
	}

}
