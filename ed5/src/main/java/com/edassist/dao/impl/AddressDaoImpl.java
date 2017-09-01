package com.edassist.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.edassist.dao.AddressDao;
import com.edassist.exception.ExceededMaxResultsException;
import com.edassist.models.domain.Address;

@Repository
@Transactional
public class AddressDaoImpl extends GenericDaoImpl<Address> implements AddressDao {

	static Logger logger = Logger.getLogger(AddressDaoImpl.class);

	@Override
	public List<Long> findEmails(String emailId, Long clientID) throws ExceededMaxResultsException {
		List<Long> emailList = null;

		try {
			Session session = this.getSessionFactory().getCurrentSession();
			String query = "SELECT DISTINCT TOP 2  x.participantId" + " FROM (" + "SELECT" + "  p.participantId" + " FROM	Participant p" + " INNER JOIN Address a ON p.WorkAddressID = a.AddressID"
					+ " WHERE	a.Email = ?" + "   AND p.Active IN ('Y','S')" + "   AND p.ClientID =" + clientID + " UNION ALL" + " SELECT" + " p.participantId" + " FROM	Participant p"
					+ " INNER JOIN Address a ON p.HomeAddressID = a.AddressID" + " WHERE	a.Email = ?" + "   AND p.Active IN ('Y','S')" + "   AND p.ClientID =" + clientID + " )x";

			System.out.println("New Query : " + query);

			Query q = session.createSQLQuery(query).addScalar("participantId", StandardBasicTypes.LONG);
			q.setString(0, emailId);
			q.setString(1, emailId);

			emailList = q.list();
		} catch (Exception e) {
			logger.error("error  in findEmails " + e);
			// e.printStackTrace();

		}
		if (emailList == null) {
			emailList = new ArrayList<Long>();
		}
		return emailList;
	}
}