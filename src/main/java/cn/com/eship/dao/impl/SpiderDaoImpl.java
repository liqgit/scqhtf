package cn.com.eship.dao.impl;

import cn.com.eship.dao.SpiderDao;
import cn.com.eship.model.Spiders;
import cn.com.eship.model.Words;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by simon on 2016/10/24.
 */
@Repository
public class SpiderDaoImpl implements SpiderDao {
    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override
    public List<Spiders> findSpiderByCondition(Map<String, Object> map) throws Exception {
        List<Object> valuesParts = new ArrayList<Object>();
        StringBuffer selectPart = new StringBuffer("from Spiders t where 1 = 1");
        StringBuffer wherePart = new StringBuffer("");
        if (map.get("spiderName") != null && StringUtils.isNotBlank((String) map.get("spiderName"))) {
            wherePart.append(" and t.name like ?");
            valuesParts.add("%" + map.get   ("spiderName") + "%");
        }
        String hql = selectPart.append(wherePart).toString();
        List<Spiders> list = (List<Spiders>) hibernateTemplate.execute(
                new HibernateCallback() {
                    public Object doInHibernate(Session session)
                            throws HibernateException, SQLException {
                        Query query = session.createQuery(hql);
                        for (int i = 0; i < valuesParts.size(); i++) {
                            query.setParameter(i, valuesParts.get(i));
                        }
                        List list = query.list();
                        return list;
                    }
                });
        return list;
    }

    @Override
    public int findSpiderCountByCondition(Map<String, Object> map) throws Exception {
        List<Object> valuesParts = new ArrayList<Object>();
        StringBuffer selectPart = new StringBuffer("from Spiders t where 1 = 1");
        StringBuffer wherePart = new StringBuffer("");
        if (map.get("spiderName") != null && StringUtils.isNotBlank((String) map.get("spiderName"))) {
            wherePart.append(" and t.name like ?");
            valuesParts.add("%" + map.get("spiderName") + "%");
        }
        String hql = selectPart.append(wherePart).toString();
        List<Spiders> list = (List<Spiders>) hibernateTemplate.execute(
                new HibernateCallback() {
                    public Object doInHibernate(Session session)
                            throws HibernateException, SQLException {
                        Query query = session.createQuery(hql);
                        for (int i = 0; i < valuesParts.size(); i++) {
                            query.setParameter(i, valuesParts.get(i));
                        }
                        List list = query.list();
                        return list;
                    }
                });
        return list != null ? list.size() : 0;
    }

    @Override
    public Spiders findSpiderById(Serializable id) throws Exception {
        return hibernateTemplate.get(Spiders.class, id);
    }

    @Override
    public void saveSpider(Spiders spiders) throws Exception {
        hibernateTemplate.save(spiders);
    }

    @Override
    public void deleteSpider(Spiders spiders) throws Exception {
        hibernateTemplate.delete(spiders);
    }
}
