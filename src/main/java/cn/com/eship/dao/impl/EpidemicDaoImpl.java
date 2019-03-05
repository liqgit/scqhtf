package cn.com.eship.dao.impl;

import cn.com.eship.dao.EpidemicDao;
import cn.com.eship.model.Epidemic;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

import static java.awt.SystemColor.info;

/**
 * Created by simon on 16/7/14.
 */
@Repository
public class EpidemicDaoImpl implements EpidemicDao {
    @Autowired
    private HibernateTemplate hibernateTemplate;

    /**
     * 获取所有疫情总数
     *
     * @return
     */
    @Override
    public Long findEpidemicCount() throws Exception {
        return (long) hibernateTemplate.find("from Epidemic epidemic").size();
    }

    @Override
    public List<Epidemic> findEpidemicList() throws Exception {
        return hibernateTemplate.loadAll(Epidemic.class);
    }

    @Override
    public List<Object> findEpidemicNameList(String keyword) throws Exception {
        String hql = "select epidemicName from Epidemic where epidemicName like ?";
        List<Object> commonList = (List<Object>) hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                query.setParameter(0, "%" + keyword + "%");
                query.setMaxResults(5);
                return query.list();
            }
        });
        return commonList;
    }

    @Override
    public List<Object> findEpidemicRegionList(String keyword) throws Exception {
        String hql = "select regionCn from Region where regionCn like ?";
        List<Object> commonList = (List<Object>) hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                query.setParameter(0, "%" + keyword + "%");
                query.setMaxResults(5);
                return query.list();
            }
        });
        return commonList;
    }

    @Override
    public List<String> findEpidemicNameList1() throws Exception {
        String hql = "select epidemicName from Epidemic";
        List<String> commonList = (List<String>) hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                return query.list();
            }
        });
        return commonList;
    }

    @Override
    public List<String> findEpidemicRegionList1() throws Exception {
        String hql = "select regionCn from Region";
        List<String> commonList = (List<String>) hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                return query.list();
            }
        });
        return commonList;
    }
}
