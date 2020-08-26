package desk.sample;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

public class HibernateDao<DTO> {
	private Class<DTO> dto;
	
	public void insert(DTO dto) throws HibernateException {
		try (Session session = getSession()) {
			Transaction trans = session.beginTransaction();
			try {
				session.save(dto);
			} catch (HibernateException hibex) {
				if (trans != null) {
					trans.rollback();
				}
				throw hibex;
			}
			trans.commit();
		}
	}

	public void update(DTO dto) throws HibernateException {
		try (Session session = getSession()) {
			Transaction trans = session.beginTransaction();
			try {
				session.update(dto);
			} catch (HibernateException hibex) {
				if (trans != null) {
					trans.rollback();
				}
				throw hibex;
			}
			trans.commit();
		}
	}

	public void delete(DTO dto) throws HibernateException {
		try (Session session = getSession()) {
			Transaction trans = session.beginTransaction();
			try {
				session.delete(dto);
			} catch (HibernateException hibex) {
				if (trans != null) {
					trans.rollback();
				}
				throw hibex;
			}
			trans.commit();
		}
	}
	
	/** 主キーと一致する番号を取り出し
	 * @param id
	 * @return */
	public DTO search(Integer id) {
		DTO sdata;
		
		//Session.load()メソッドは主キーによる検索のみでそれ以外の列名の指定はできない。
		try (Session session = getSession()) {
			sdata = (DTO) session.load(dto, id);
			//DTO sdata = (DTO) session.load(ShohinMap.class, id);
		}

		return sdata;
	}
	
	/** テーブル全件取得後、List< T >型で返します。
	 * @return List< T > */
	public List<DTO> searchAll() {
		List<DTO> list;
		
		try (Session session = getSession()) {
			CriteriaBuilder cbuilder = session.getCriteriaBuilder();
			CriteriaQuery<DTO> query = cbuilder.createQuery(dto);
			query.from(dto);
			list = session.createQuery(query).getResultList();
			//CriteriaQuery<ShohinMap> query = cbuilder.createQuery(ShohinMap.class);
			//query.from(ShohinMap.class);
			//List<ShohinMap> list = session.createQuery(query).getResultList();
			
			//createCriteriaはHibernate5以上で非推奨
			//Criteria cr = session.createCriteria(ShohinMap.class);
			//List<DTO> list = cr.list();
		}

		return list;
	}

	public List<DTO> GoQuery(String hql) {
		List<DTO> list;
		
		try(Session session = getSession()) {
			Query<DTO> que = session.createQuery(hql);
			list = que.list();
		}
		
		return list;
	}

	public long getCount(String hql) {
		long count;
		
		try (Session session = getSession()) {
			count = (long) session.createQuery(hql).iterate().next();
		}
		
		return count;
	}
	
	private Session getSession() {
		Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
		cfg.addResource("map.hbm.xml");
		StandardServiceRegistryBuilder sb = new StandardServiceRegistryBuilder();
		sb.applySettings(cfg.getProperties());
		ServiceRegistry sr = sb.build();
		SessionFactory sfactory = cfg.buildSessionFactory(sr);
		Session session = sfactory.openSession();

		return session;
	}
}