package cn.com.eship.dao.impl;

import cn.com.eship.dao.EpidemicAppearDao;
import cn.com.eship.model.EpidemicAppear;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by simon on 16/7/14.
 */
@Repository
public class EpidemicAppearDaoImpl implements EpidemicAppearDao {
    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override
    public List<Object> findEpidemicTopten(Map<String, Object> mapPram) throws Exception {
        List<Object> valueList = new ArrayList<Object>();
        StringBuffer selectAndFromPart = new StringBuffer("select epidemicAppear.epidemic.epidemicName,sum(epidemicAppear.appearTimes) as appearTimesSum from EpidemicAppear epidemicAppear");
        StringBuffer wherePart = new StringBuffer(" where 1=1");
        StringBuffer lastPart = new StringBuffer(" group by epidemicAppear.epidemic.id order by appearTimesSum DESC");
        if (!mapPram.isEmpty()) {
            for (Map.Entry<String, Object> entry : mapPram.entrySet()) {
                wherePart.append(" and ").append(entry.getKey() + " = ").append("?");
                valueList.add(entry.getValue());
            }
        }
        String hql = selectAndFromPart.append(wherePart).append(lastPart).toString();
        List<Object> list = (List<Object>) hibernateTemplate.execute(
                new HibernateCallback() {
                    public Object doInHibernate(Session session)
                            throws HibernateException, SQLException {
                        Query query = session.createQuery(hql);
                        for (int i = 0; i < valueList.size(); i++) {
                            query.setParameter(i, valueList.get(i));
                        }
                        query.setMaxResults(10);
                        List list = query.list();
                        return list;
                    }
                });

        return list != null ? list : null;
    }

    @Override
    public List<Object> findEpidemicAppearRegionCount(Map<String, String> mapPram) throws Exception {
        List<Object> valueList = new ArrayList<Object>();
        StringBuffer selectPart = new StringBuffer("select epidemicAppear.region.regionEn,sum(epidemicAppear.appearTimes) as appearTimesSum from EpidemicAppear epidemicAppear");
        StringBuffer wherePart = new StringBuffer(" where 1 = 1");
        StringBuffer lastPart = new StringBuffer(" group by epidemicAppear.region.regionEn");
        if (!mapPram.isEmpty()) {
            if (StringUtils.isNotBlank(mapPram.get("startDate"))) {
                wherePart.append(" and epidemicAppear.appearDate >= date_format(?,'%Y-%m-%d')");
                valueList.add(mapPram.get("startDate"));
            }
            if (StringUtils.isNotBlank(mapPram.get("endDate"))) {
                wherePart.append(" and epidemicAppear.appearDate <= date_format(?,'%Y-%m-%d')");
                valueList.add(mapPram.get("endDate"));
            }
            if (StringUtils.isNotBlank(mapPram.get("epidemicName"))) {
                wherePart.append(" and epidemicAppear.epidemic.epidemicName like ?");
                valueList.add("%" + mapPram.get("epidemicName") + "%");
            }
        }
        String hql = selectPart.append(wherePart).append(lastPart).toString();
        List<Object> list = (List<Object>) hibernateTemplate.execute(
                new HibernateCallback() {
                    public Object doInHibernate(Session session)
                            throws HibernateException, SQLException {
                        Query query = session.createQuery(hql);
                        for (int i = 0; i < valueList.size(); i++) {
                            query.setParameter(i, valueList.get(i));
                        }
                        List list = query.list();
                        return list;
                    }
                });

        return list != null ? list : null;
    }

    @Override
    public List<Object> findEpidemicAppearCount(Map<String, String> mapPram) throws Exception {
        List<Object> valueList = new ArrayList<Object>();
        StringBuffer selectPart = new StringBuffer("select epidemicAppear.epidemic.epidemicName,sum(epidemicAppear.appearTimes) as appearTimesSum from EpidemicAppear epidemicAppear");
        StringBuffer wherePart = new StringBuffer(" where 1 = 1");
        StringBuffer lastPart = new StringBuffer(" group by epidemicAppear.epidemic.id order by appearTimesSum DESC");
        if (!mapPram.isEmpty()) {
            if (StringUtils.isNotBlank(mapPram.get("startDate"))) {
                wherePart.append(" and epidemicAppear.appearDate >= date_format(?,'%Y-%m-%d')");
                valueList.add(mapPram.get("startDate"));
            }
            if (StringUtils.isNotBlank(mapPram.get("endDate"))) {
                wherePart.append(" and epidemicAppear.appearDate <= date_format(?,'%Y-%m-%d')");
                valueList.add(mapPram.get("endDate"));
            }
            if (StringUtils.isNotBlank(mapPram.get("region"))) {
                wherePart.append(" and epidemicAppear.region.regionCn = ?");
                valueList.add(mapPram.get("region"));
            }
        }
        String hql = selectPart.append(wherePart).append(lastPart).toString();

        List<Object> list = (List<Object>) hibernateTemplate.execute(
                new HibernateCallback() {
                    public Object doInHibernate(Session session)
                            throws HibernateException, SQLException {
                        Query query = session.createQuery(hql);
                        for (int i = 0; i < valueList.size(); i++) {
                            query.setParameter(i, valueList.get(i));
                        }
                        List list = query.list();
                        return list;
                    }
                });
        return list != null ? list : null;
    }


    @Override
    public List<EpidemicAppear> findEpidemicAppearListByCondition(Map<String, Object> mapPram) throws Exception {
        List<Object> valuesPart = new ArrayList<Object>();
        StringBuffer hqlBuffer = new StringBuffer("from EpidemicAppear epidemicAppear join fetch epidemicAppear.epidemic t1 join fetch epidemicAppear.region t2 where 1 = 1");
        StringBuffer wherePart = new StringBuffer();
        if (mapPram.get("epidemicName") != null) {
            wherePart.append(" and t1.epidemicName like ?");
            valuesPart.add("%" + mapPram.get("epidemicName") + "%");
        }
        if (mapPram.get("regionCn") != null) {
            wherePart.append(" and t2.regionCn like ?");
            valuesPart.add("%" + mapPram.get("regionCn") + "%");
        }
        if (mapPram.get("startDate") != null) {
            wherePart.append(" and epidemicAppear.appearDate >= date_format(?,'%Y-%m-%d')");
            valuesPart.add(mapPram.get("startDate"));
        }
        if (mapPram.get("endDate") != null) {
            wherePart.append(" and epidemicAppear.appearDate <= date_format(?,'%Y-%m-%d')");
            valuesPart.add(mapPram.get("endDate"));
        }
        String hql = hqlBuffer.append(wherePart).toString();
        List<EpidemicAppear> list = (List<EpidemicAppear>) hibernateTemplate.execute(
                new HibernateCallback() {
                    public Object doInHibernate(Session session)
                            throws HibernateException, SQLException {
                        Query query = session.createQuery(hql);
                        query.setFirstResult((int) mapPram.get("pageNo"));
                        query.setMaxResults(10);
                        for (int i = 0; i < valuesPart.size(); i++) {
                            query.setParameter(i, valuesPart.get(i));
                        }
                        List list = query.list();
                        return list;
                    }
                });

        return list;
    }

    @Override
    public EpidemicAppear findEpidemicAppearById(String id) throws Exception {
        return hibernateTemplate.get(EpidemicAppear.class, id);
    }

    @Override
    public List<EpidemicAppear> findEpidemicAppearList(Map<String, Object> mapPram) throws Exception {
        List<Object> valuesPart = new ArrayList<Object>();
        StringBuffer hqlBuffer = new StringBuffer("from EpidemicAppear epidemicAppear join fetch epidemicAppear.epidemic t1 join fetch epidemicAppear.region t2 where 1 = 1");
        StringBuffer wherePart = new StringBuffer();
        if (mapPram.get("epidemicName") != null && StringUtils.isNotBlank((String) mapPram.get("epidemicName"))) {
            wherePart.append(" and t1.epidemicName like ?");
            valuesPart.add("%" + mapPram.get("epidemicName") + "%");
        }
        if (mapPram.get("regionCn") != null && StringUtils.isNotBlank((String) mapPram.get("regionCn"))) {
            wherePart.append(" and t2.regionCn like ?");
            valuesPart.add("%" + mapPram.get("regionCn") + "%");
        }
        if (mapPram.get("startDate") != null && StringUtils.isNotBlank((String) mapPram.get("startDate"))) {
            wherePart.append(" and epidemicAppear.appearDate >= date_format(?,'%Y-%m-%d')");
            valuesPart.add(mapPram.get("startDate"));
        }
        if (mapPram.get("endDate") != null && StringUtils.isNotBlank((String) mapPram.get("endDate"))) {
            wherePart.append(" and epidemicAppear.appearDate <= date_format(?,'%Y-%m-%d')");
            valuesPart.add(mapPram.get("endDate"));
        }
        String hql = hqlBuffer.append(wherePart).toString();
        List<EpidemicAppear> list = (List<EpidemicAppear>) hibernateTemplate.execute(
                new HibernateCallback() {
                    public Object doInHibernate(Session session)
                            throws HibernateException, SQLException {
                        Query query = session.createQuery(hql);
                        for (int i = 0; i < valuesPart.size(); i++) {
                            query.setParameter(i, valuesPart.get(i));
                        }
                        List list = query.list();
                        return list;
                    }
                });
        return list;
    }

    @Override
    public List<EpidemicAppear> findEpidemicAppearListOverride(Map<String, Object> mapPram) throws Exception {
        List<Object> valuesPart = new ArrayList<Object>();
        StringBuffer hqlBuffer = new StringBuffer("from EpidemicAppear epidemicAppear join fetch epidemicAppear.epidemic t1 join fetch epidemicAppear.region t2 where 1 = 1");
        StringBuffer wherePart = new StringBuffer();
        if (mapPram.get("regionCn") != null) {
            wherePart.append(" and t2.regionCn like ?");
            valuesPart.add("%" + mapPram.get("regionCn") + "%");
        }
        if (mapPram.get("startDate") != null) {
            wherePart.append(" and epidemicAppear.appearDate = date_format(?,'%Y-%m-%d')");
            valuesPart.add(mapPram.get("startDate"));
        }
        String hql = hqlBuffer.append(wherePart).toString();
        List<EpidemicAppear> list = (List<EpidemicAppear>) hibernateTemplate.execute(
                new HibernateCallback() {
                    public Object doInHibernate(Session session)
                            throws HibernateException, SQLException {
                        Query query = session.createQuery(hql);
                        query.setFirstResult((int) mapPram.get("pageNo"));
                        query.setMaxResults(10);
                        for (int i = 0; i < valuesPart.size(); i++) {
                            query.setParameter(i, valuesPart.get(i));
                        }
                        List list = query.list();
                        return list;
                    }
                });

        return list;
    }

    @Override
    public int findEpidemicAppearListOverrideCount(Map<String, Object> mapPram) throws Exception {
        List<Object> valuesPart = new ArrayList<Object>();
        StringBuffer hqlBuffer = new StringBuffer("from EpidemicAppear epidemicAppear join fetch epidemicAppear.epidemic t1 join fetch epidemicAppear.region t2 where 1 = 1");
        StringBuffer wherePart = new StringBuffer();
        if (mapPram.get("regionCn") != null) {
            wherePart.append(" and t2.regionCn like ?");
            valuesPart.add("%" + mapPram.get("regionCn") + "%");
        }
        if (mapPram.get("startDate") != null) {
            wherePart.append(" and epidemicAppear.appearDate = date_format(?,'%Y-%m-%d')");
            valuesPart.add(mapPram.get("startDate"));
        }
        String hql = hqlBuffer.append(wherePart).toString();
        List<EpidemicAppear> list = (List<EpidemicAppear>) hibernateTemplate.execute(
                new HibernateCallback() {
                    public Object doInHibernate(Session session)
                            throws HibernateException, SQLException {
                        Query query = session.createQuery(hql);
                        for (int i = 0; i < valuesPart.size(); i++) {
                            query.setParameter(i, valuesPart.get(i));
                        }
                        List list = query.list();
                        return list;
                    }
                });

        return list.size();
    }

    public List<Map<String, String>> findWorldEpidemicAppearsTimeline(String type)throws Exception{
        String whereCondition = "";
        if (StringUtils.isNotEmpty(type)&&"china".equals(type)){
            whereCondition = "AND a.region_id=31 ";
        }
        String sql = "SELECT epidemic_name,SUM(appear_times) sat,DATE_FORMAT(appear_date,'%Y') date " +
                "FROM t_epidemic_appear a LEFT JOIN t_epidemic b ON a.epidemic_id = b.id " +
                "WHERE 1=1 " +whereCondition+
                "GROUP BY epidemic_id,DATE_FORMAT(appear_date,'%Y') HAVING date<2017 and date>2006 ORDER BY date desc,sat desc";
        List<Map<String, String>> jsonList = new ArrayList<Map<String, String>>();
        hibernateTemplate.execute(
                new HibernateCallback() {
                    public Object doInHibernate(Session session)throws HibernateException, SQLException {
                        Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                        List list = query.list();
                        for (int i=0;i<list.size();i++){
                            Map<String,String> map2= new HashMap<>();
                            Map map = (Map)list.get(i);
                            map2.put("name",(String) map.get("epidemic_name"));
                            map2.put("value",map.get("sat").toString());
                            map2.put("date",map.get("date").toString());
                            jsonList.add(map2);
                        }
                        return jsonList;
                    }
                });
        return jsonList;
    }
}
