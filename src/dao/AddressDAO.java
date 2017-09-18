package dao;

import java.util.Arrays;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import model.Address;
import util.HibernateUtil;

public class AddressDAO {
	public void insert(Address address) throws Exception {
		try {
			Session session = HibernateUtil.getSession();
			session.save(address);
		} catch (Exception ex) {
			throw ex;
		}
	}

	public void delete(Address address) throws Exception {
		try {
			Session session = HibernateUtil.getSession();
			session.delete(address);
		} catch (Exception ex) {
			throw ex;
		}
	}

	public void update(Address address) throws Exception {
		try {
			Session session = HibernateUtil.getSession();
			session.update(address);
		} catch (Exception ex) {
			throw ex;
		}
	}

	public Address findByPK(long addrId) throws Exception {
		Address address = null;
		try {
			Session session = HibernateUtil.getSession();
			address = (Address) session.get(Address.class, addrId);
		} catch (Exception ex) {
			throw ex;
		}
		return address;
	}

	public List<Address> findByCusNam(String name) throws Exception {
		List<Address> list = null;
		try {
			Session session = HibernateUtil.getSession();
			Criteria crit = session.createCriteria(Address.class);
			Criteria custCrit = crit.createCriteria("customer");
			custCrit.add(Restrictions.eq("firstname", name));
			if ((!crit.list().isEmpty())) {
				list = toList(crit.list());
			}
		} catch (Exception ex) {
			throw ex;
		}

		return list;
	}

	public List<Address> findByCustId(long id) throws Exception {

		List<Address> list = null;
		try {

			Session session = HibernateUtil.getSession();
			Criteria crit = session.createCriteria(Address.class);
			Criteria usrCrit = crit.createCriteria("customer");
			usrCrit.add(Restrictions.eq("id", id));
			if (!crit.list().isEmpty()) {
				list = toList(crit.list());
			}

		} catch (Exception ex) {
			throw ex;
		}

		return list;

	}

	public static List<Address> toList(final List<?> beans) {
		if (beans == null)
			return null;
		if (beans.isEmpty())
			return null;

		int size = beans.size();
		Address[] list = new Address[size];
		list = (Address[]) beans.toArray(list);
		return Arrays.asList(list);
	}

}
