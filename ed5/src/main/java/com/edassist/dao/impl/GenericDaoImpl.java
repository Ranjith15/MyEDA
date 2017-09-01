package com.edassist.dao.impl;

import com.edassist.dao.GenericDao;
import com.edassist.exception.BadRequestException;
import com.edassist.exception.ExceededMaxResultsException;
import com.edassist.utils.CommonUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.InitialContext;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.*;

@Transactional
@SuppressWarnings("unchecked")
public class GenericDaoImpl<T> implements GenericDao<T> {
	private static final String UNCHECKED = "unchecked";
	private static Logger log = Logger.getLogger(GenericDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Class<? extends T> entityClass;

	private int maxResults = 500;

	public GenericDaoImpl() {
		Type type = getClass().getGenericSuperclass();
		ParameterizedType parameterizedType = (ParameterizedType) type;
		this.entityClass = (Class) parameterizedType.getActualTypeArguments()[0];
	}

	public GenericDaoImpl(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<T> findAll() throws ExceededMaxResultsException {
		List<T> results = (List<T>) this.getSessionFactory().getCurrentSession().createCriteria(entityClass).list();
		Set<T> set = new HashSet<T>(results);
		results = new ArrayList<T>(set);
		return results;
	}

	@Override
	@SuppressWarnings(UNCHECKED)
	public T findById(Long id) throws DataAccessException {

		T model = (T) this.getSessionFactory().getCurrentSession().get(entityClass, id);
		if (model == null) {
			throw new ObjectRetrievalFailureException(entityClass, id);
		} else {
			return model;
		}
	}

	@Override
	@SuppressWarnings(UNCHECKED)
	public T findById(Integer id) throws DataAccessException {

		T model = (T) this.getSessionFactory().getCurrentSession().get(entityClass, id);
		if (model == null) {
			throw new ObjectRetrievalFailureException(entityClass, id);
		} else {
			return model;
		}
	}

	@Override
	public void saveOrUpdate(T entity) throws DataAccessException {
		try {
			if (entity == null) {
				return;
			}
			Session session = this.getSessionFactory().getCurrentSession();

			session.saveOrUpdate(entity);
			session.flush();
		} catch (Exception e) {
			log.error("An error occurred while persisting entity.", e);
			throw new BadRequestException("An error occurred while persisting entity.");
		}
	}

	@Override
	@SuppressWarnings(UNCHECKED)
	public T merge(T entity) throws DataAccessException {
		return (T) this.getSessionFactory().getCurrentSession().merge(entity);
	}

	@Override
	public void remove(T entity) throws DataAccessException {
		this.getSessionFactory().getCurrentSession().delete(entity);
		this.getSessionFactory().getCurrentSession().flush();
	}

	@Override
	public void removeCommit(T entity) throws DataAccessException {
		Session session = this.getSessionFactory().getCurrentSession();
		session.beginTransaction().begin();
		session.delete(entity);
		session.flush();
		session.beginTransaction().commit();
		session.close();

	}

	@Override
	public void remove(Long id) {

		this.getSessionFactory().getCurrentSession().delete(this.findById(id));
		this.getSessionFactory().getCurrentSession().flush();
	}

	@Override
	public void removeAll() throws DataAccessException, ExceededMaxResultsException {
		this.getSessionFactory().getCurrentSession().delete(this.findAll());
		this.getSessionFactory().getCurrentSession().flush();
	}

	@Override
	public void removeAll(String fieldName, Long value) throws DataAccessException, ExceededMaxResultsException {
		this.getSessionFactory().getCurrentSession().delete(fieldName, value);
		this.getSessionFactory().getCurrentSession().flush();
	}

	@Override
	@SuppressWarnings(UNCHECKED)
	public List<T> findByNamedParam(String query, String[] paramNames, Object[] values) throws DataAccessException, ExceededMaxResultsException {
		Session session = this.getSessionFactory().getCurrentSession();
		Query q = session.createQuery(query);
		for (int i = 0; i < values.length; i++) {
			q.setParameter(paramNames[i], values[i]);
		}
		List<T> results = (List<T>) q.list();

		return results;

	}

	@Override
	public List<T> findByParams(String[] paramNames, Object[] values, String sortField, String distinctField) throws DataAccessException, ExceededMaxResultsException {
		return this.findByParams(paramNames, values, sortField, distinctField, "asc");
	}

	@Override
	public List<T> findByParams(String[] paramNames, Object[] values, String sortField, String distinctField, String sortOrder) throws DataAccessException, ExceededMaxResultsException {

		List<T> results = null;

		if (paramNames != null && values != null && paramNames.length == values.length) {
			Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(entityClass);

			for (int i = 0; i < paramNames.length; i++) {
				if (values[i] == null) {
					criteria.add(Restrictions.isNull(paramNames[i]));
				} else if (values[i] instanceof String && ((String) values[i]).equals(CommonUtil.RESTRICTION_NOT_NULL)) {
					criteria.add(Restrictions.isNotNull(paramNames[i]));
				} else {
					criteria.add(Restrictions.eq(paramNames[i], values[i]));
				}
			}

			if (StringUtils.isNotBlank(sortField)) {
				if ("desc".equals(sortOrder)) {
					criteria.addOrder(Order.desc(sortField));
				} else {
					criteria.addOrder(Order.asc(sortField));
				}
			}

			if (StringUtils.isNotBlank(distinctField)) {
				criteria.setProjection(Projections.distinct(Projections.property(distinctField)));
			}

			results = (List<T>) criteria.list();
		}

		return results;
	}

	@Override
	public T findEntityByParams(String[] paramNames, Object[] values, String sortField, String distinctField) {

		List<T> results = null;

		if (paramNames != null && values != null && paramNames.length == values.length) {
			Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(entityClass);

			for (int i = 0; i < paramNames.length; i++) {
				if (values[i] == null) {
					criteria.add(Restrictions.isNull(paramNames[i]));
				} else if (values[i] instanceof String && ((String) values[i]).equals(CommonUtil.RESTRICTION_NOT_NULL)) {
					criteria.add(Restrictions.isNotNull(paramNames[i]));
				} else {
					criteria.add(Restrictions.eq(paramNames[i], values[i]));
				}
			}

			if (StringUtils.isNotBlank(sortField)) {
				criteria.addOrder(Order.asc(sortField));
			}

			if (StringUtils.isNotBlank(distinctField)) {
				criteria.setProjection(Projections.distinct(Projections.property(distinctField)));
			}

			results = (List<T>) criteria.list();
			if (results == null || results.size() == 0) {
				return null;
			} else if (results.size() > 1) {
				throw new BadRequestException("Query yielded more results than expected. Criteria: " + criteria);
			} else {
				return results.get(0);
			}
		}

		return null;
	}

	@Override
	public List<T> findAll(String sortField) throws ExceededMaxResultsException {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(entityClass);
		criteria.addOrder(Order.asc(sortField));
		List<T> results = criteria.list();

		return results;
	}

	@Override
	public List<T> findByParam(String fieldName, Object value) {
		if (value == null) {
			return null;
		}
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(entityClass).add(Restrictions.eq(fieldName, value));
		criteria.addOrder(Order.asc(fieldName));

		List<T> results = criteria.list();

		return results;
	}

	@Override
	public List<T> findByParamAndNull(String fieldName, Object value) throws DataAccessException, ExceededMaxResultsException {
		if (value == null) {
			return null;
		}
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(entityClass).add(Restrictions.or(Restrictions.eq(fieldName, value), Restrictions.isNull(fieldName)));
		criteria.addOrder(Order.asc(fieldName));
		List<T> results = (List<T>) criteria.list();

		return results;
	}

	@Override
	public List<T> findByParam(String fieldName, Object value, String sortField) throws DataAccessException, ExceededMaxResultsException {
		return this.findByParam(fieldName, value, sortField, true);
	}

	@Override
	public List<T> findByParam(String fieldName, Object value, String sortField, boolean sortAscending) throws DataAccessException, ExceededMaxResultsException {
		if (value == null) {
			return null;
		}
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(entityClass).add(Restrictions.eq(fieldName, value));

		if (StringUtils.isNotBlank(sortField)) {
			if (sortAscending) {
				criteria.addOrder(Order.asc(sortField));
			} else {
				criteria.addOrder(Order.desc(sortField));
			}
		} else {
			if (sortAscending) {
				criteria.addOrder(Order.asc(fieldName));
			} else {
				criteria.addOrder(Order.desc(fieldName));
			}
		}
		List<T> results = (List<T>) criteria.list();

		return results;
	}

	@Override
	public List<T> findByParam(String fieldName, Object value, String sortField, String sortAlias) throws DataAccessException, ExceededMaxResultsException {
		if (value == null) {
			return null;
		}
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(entityClass).add(Restrictions.eq(fieldName, value));

		if (StringUtils.isNotBlank(sortField) && StringUtils.isNotBlank(sortAlias)) {
			criteria.createAlias(sortAlias, "genericAlias01");
			criteria.addOrder(Order.asc("genericAlias01." + sortField));
		} else {
			if (StringUtils.isNotBlank(sortField)) {
				criteria.addOrder(Order.asc(sortField));
			} else {
				criteria.addOrder(Order.asc(fieldName));
			}
		}

		List<T> results = (List<T>) criteria.list();
		return results;
	}

	@Override
	public T findByUniqueParam(String fieldName, Object value) throws DataAccessException {
		if (value == null) {
			return null;
		}
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(entityClass).add(Restrictions.eq(fieldName, value));
		List<T> searchResults = (List<T>) criteria.list();
		if (searchResults == null || searchResults.isEmpty()) {
			return null;
		}

		if (searchResults.size() > 1) {
			throw new BadRequestException("Search [" + criteria + "] did NOT yield a unique result.");
		}

		return searchResults.get(0);
	}

	@Override
	public List<T> findByParamLike(String fieldName, Object value) throws DataAccessException, ExceededMaxResultsException {
		if (value == null) {
			return null;
		}

		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(entityClass);
		criteria.add(Restrictions.like(fieldName, value));
		criteria.addOrder(Order.asc(fieldName));

		int count = ((Number) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
		checkMaxResultsExceeded(count);

		if (count > 0) {
			criteria.setProjection(null);
			return (List<T>) criteria.list();
		}
		return null;
	}

	@Override
	public List<T> findAllNotNull(String sortField, String fieldName) throws DataAccessException, ExceededMaxResultsException {

		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(entityClass);
		criteria.add(Restrictions.isNotNull(fieldName));

		criteria.addOrder(Order.asc(sortField));
		List<T> results = criteria.list();

		return results;
	}

	public int getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}

	protected void checkMaxResultsExceeded(Collection results) throws ExceededMaxResultsException {
		if (results != null) {
			checkMaxResultsExceeded(results.size());
		}
	}

	protected void checkMaxResultsExceeded(int size) throws ExceededMaxResultsException {
		if (size >= this.getMaxResults()) {
			throw new ExceededMaxResultsException(this.getMaxResults());
		}
	}

	@Override
	public void evict(T entity) {
		this.getSessionFactory().getCurrentSession().evict(entity);
	}

	@Override
	public boolean exists(String fieldName, Object value) {

		if (StringUtils.isEmpty(fieldName) || value == null) {
			return false;
		}

		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(entityClass).add(Restrictions.eq(fieldName, value));

		List<T> results = (List<T>) criteria.list();

		if (CollectionUtils.isNotEmpty(results)) {
			return true;
		}

		return false;
	}

	public Connection getConnection() throws Exception {

		Connection connection = null;
		javax.naming.Context context = null;

		try {
			context = new InitialContext();
			javax.sql.DataSource ds = (javax.sql.DataSource) context.lookup("java:comp/env/tamsDB");
			connection = ds.getConnection();
		} finally {
			try {
				if (context != null) {
					context.close();
				}
			} catch (javax.naming.NamingException ne) {
				throw ne;
			}
		}

		return connection;
	}

	public void closeDBResources(PreparedStatement preparedStatement, Connection connection) throws Exception {
		try {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
		} catch (Exception e) {
			;
		}
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (Exception e) {
			;
		}

	}

}
