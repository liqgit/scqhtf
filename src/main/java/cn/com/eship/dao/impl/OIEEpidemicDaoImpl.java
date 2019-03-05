package cn.com.eship.dao.impl;

import cn.com.eship.dao.OIEEpidemicDao;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.*;

@Repository
public class OIEEpidemicDaoImpl implements OIEEpidemicDao {
    private HibernateTemplate hibernateTemplate;

    @Autowired
    public OIEEpidemicDaoImpl(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    /**
     * 获取所有疫情总数
     *
     * @return
     */
    @Override
    public List<String> findEpidemicNameList() throws Exception {
        String sql = "select disease_name_eng,disease_name_cn from oie_diseases";
        List<String> commonList = new ArrayList<String>();
        hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List list = query.list();
                for (int i=0;i<list.size();i++){
                    Map map = (Map)list.get(i);
                    commonList.add((String)map.get("disease_name_eng"));
                    commonList.add((String)map.get("disease_name_cn"));
                }
                return commonList;
            }
        });
        return commonList;
    }

    @Override
    public List<String> findEpidemicRegionList() throws Exception {
        String sql = "select region_name_eng,region_name_cn  from oie_world_region";
        List<String> commonList = new ArrayList<String>();
        hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List list = query.list();
                for (int i=0;i<list.size();i++){
                    Map map = (Map)list.get(i);
                    commonList.add((String)map.get("region_name_eng"));
                    commonList.add((String)map.get("region_name_cn"));
                }
                return commonList;
            }
        });
        return commonList;
    }

    @Override
    public List<String> findEpidemicKindList() throws Exception {
        String sql = "select DISTINCT disease_class from oie_diseases";
        List<String> commonList = new ArrayList<String>();
        HashSet set = new HashSet();
        hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List list = query.list();
                for (int i=0;i<list.size();i++){
                    Map map = (Map)list.get(i);
                    commonList.add((String)map.get("disease_class"));
                }
                return commonList;
            }
        });
        return commonList;
    }

    @Override
    public int findEpidemicIdByCondition(String condition) throws Exception {
        Session session = null;
        try{
            String hql = "select id from oie_diseases where disease_name_eng = ? or disease_name_cn = ?";
            session = hibernateTemplate.getSessionFactory().openSession();
            Query query = session.createSQLQuery(hql);
            query.setParameter(0, condition);
            query.setParameter(1, condition);
            List list = query.list();
            if (list!=null&&list.size()>0){
                return (int)list.get(0);
            }else {
                return 0;
            }
        }catch (Exception e){
            e.printStackTrace();
            return 0;

        }finally {
            if (session!=null&&session.isOpen()){
                session.close();
            }
        }

    }

    @Override
    public int findRegionIdByCondition(String condition){
        Session session = null;
        try{
            String hql = "select id from oie_world_region where region_name_eng = ? or region_name_cn = ?";
            session = hibernateTemplate.getSessionFactory().openSession();
            Query query = session.createSQLQuery(hql);
            query.setParameter(0, condition);
            query.setParameter(1, condition);
            List list = query.list();
            if (list!=null&&list.size()>0){
                return (int)list.get(0);
            }else {
                return 0;
            }
        }catch (Exception e){
            e.printStackTrace();
            return 0;

        }finally {
            if (session!=null&&session.isOpen()){
                session.close();
            }
        }
    }

    @Override
    public List<Integer> findEpidemicIdListByCondition(String condition) throws Exception {
        String hql = "select id  from OieDiseasesEntity where diseaseClass = ?";
        List<Integer> list=  (List<Integer>) hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                query.setParameter(0, condition);
                return query.list();
            }
        });
        return list;
    }

    @Override
    public List<Map<String, Object>> findEpidemicEventList(Map<String, Object> condition) throws Exception {
        List<Object> valuesPart = new ArrayList<>();
        StringBuffer hqlBuffer = new StringBuffer("SELECT a.disease,a.country,b.disease_name_cn,b.disease_name_eng,b.disease_class," +
                "c.region_name_cn,c.region_name_eng,a.date,a.reason,a.outbreaks,a.manifestation,a.report,a.date_res" +
                " FROM (oie_epidemiological_event a" +
                " LEFT JOIN oie_diseases b ON a.disease_id = b.id)" +
                " LEFT JOIN oie_world_region c ON a.country_id = c.id WHERE 1=1");
        StringBuffer wherePart = new StringBuffer();
        if (condition.get("epidemicId") != null) {
            wherePart.append(" and  b.id=?");
            valuesPart.add(condition.get("epidemicId"));
        }
        if (condition.get("regionId") != null) {
            wherePart.append(" and c.id=?");
            valuesPart.add(condition.get("regionId"));
        }
        if (condition.get("epidemicClass") != null) {
            wherePart.append(" and b.disease_class = ? ");
            valuesPart.add(condition.get("epidemicClass"));
        }
        if (condition.get("startDate") != null) {
            wherePart.append(" and a.date >= date_format(?,'%Y-%m-%d')");
            valuesPart.add(condition.get("startDate"));
        }
        if (condition.get("endDate") != null) {
            wherePart.append(" and a.date <= date_format(?,'%Y-%m-%d')");
            valuesPart.add(condition.get("endDate"));
        }
        if (condition.get("endDate") == null&&condition.get("startDate") == null&&condition.get("interval") != null) {
            wherePart.append(" and a.date >= date_sub(curdate(),interval ? day)");
            valuesPart.add(condition.get("interval"));
        }
        if (wherePart.length()<1){
            wherePart.append(" and a.date >= date_sub(curdate(),interval 30 day)");
        }
        hqlBuffer.append(wherePart);
        if (condition.get("orderBy") != null) {
            hqlBuffer.append(" order by ").append(condition.get("orderBy"));
        }else {
            hqlBuffer.append(" order by a.date DESC");
        }

        String hql = hqlBuffer.toString();
        List<Map<String,Object>> jsonList = new ArrayList<>();
        hibernateTemplate.execute(new HibernateCallback() {
                    public Object doInHibernate(Session session)
                            throws HibernateException, SQLException {
                        Query query = session.createSQLQuery(hql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                        if (condition.get("pageNo")!=null){
                            query.setFirstResult((int) condition.get("pageNo"));
                            query.setMaxResults(10);
                        }
                        for (int i = 0; i < valuesPart.size(); i++) {
                            query.setParameter(i, valuesPart.get(i));
                        }
                        List list = query.list();
                        for (int i=0;i<list.size();i++){
                            Map<String,Object> map2= new HashMap<>();
                            Map map = (Map)list.get(i);
                            map2.put("epidemicNameCn",map.get("disease_name_cn"));
                            map2.put("epidemicNameEng",map.get("disease_name_eng"));
                            map2.put("disease",map.get("disease"));
                            map2.put("diseaseClass",map.get("disease_class")!= null?map.get("disease_class"):"");
                            map2.put("country",map.get("country"));
                            map2.put("regionNameCn",map.get("region_name_cn"));
                            map2.put("regionNameEng",map.get("region_name_eng"));
                            map2.put("date",map.get("date"));
                            map2.put("reason",map.get("reason"));
                            map2.put("outbreaks",map.get("outbreaks"));
                            map2.put("manifestation",map.get("manifestation"));
                            map2.put("dateRes",map.get("date_res"));
                            map2.put("report",map.get("report"));
                            jsonList.add(map2);
                        }
                        return jsonList;
                    }
                });

        return jsonList;
    }

    @Override
    public Long findTotalRecord(Map<String, Object> condition) throws Exception {
        List<Object> valuesPart = new ArrayList<Object>();
        StringBuffer hqlBuffer = new StringBuffer("SELECT COUNT(a.disease) n" +
                " FROM (oie_epidemiological_event a" +
                " LEFT JOIN oie_diseases b ON a.disease_id = b.id)" +
                " LEFT JOIN oie_world_region c ON a.country_id = c.id WHERE 1=1");
        StringBuffer wherePart = new StringBuffer();
        if (condition.get("epidemicId") != null) {
            wherePart.append(" and  b.id=?");
            valuesPart.add(condition.get("epidemicId"));
        }
        if (condition.get("regionId") != null) {
            wherePart.append(" and c.id=?");
            valuesPart.add(condition.get("regionId"));
        }
        if (condition.get("epidemicClass") != null) {
            wherePart.append("  and b.disease_class = ? ");
            valuesPart.add(condition.get("epidemicClass"));
        }
        if (condition.get("startDate") != null) {
            wherePart.append(" and a.date >= date_format(?,'%Y-%m-%d')");
            valuesPart.add(condition.get("startDate"));
        }
        if (condition.get("endDate") != null) {
            wherePart.append(" and a.date <= date_format(?,'%Y-%m-%d')");
            valuesPart.add(condition.get("endDate"));
        }
        if (condition.get("endDate") == null&&condition.get("startDate") == null&&condition.get("interval") != null) {
            wherePart.append(" and a.date >= date_sub(curdate(),interval ? day)");
            valuesPart.add(condition.get("interval"));
        }
        if (wherePart.length()<1){
            wherePart.append(" and a.date >= date_sub(curdate(),interval 30 day)");
        }
        hqlBuffer.append(wherePart);
        hqlBuffer.append(" order by a.date DESC");
        String hql = hqlBuffer.toString();
        long l = (long)hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createSQLQuery(hql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                for (int i = 0; i < valuesPart.size(); i++) {
                    query.setParameter(i, valuesPart.get(i));
                }
                Map map = (Map)query.list().get(0);
                BigInteger co = (BigInteger) map.get("n");
                return co.longValue();
            }
        });
        return l;
    }

    @Override
    public List<Map<String, Object>> findTotalOutbreaksOfDate(Map<String, Object> condition) throws Exception {
        List<Object> valuesPart = new ArrayList<Object>();
        StringBuilder sqlBuilder = new StringBuilder("SELECT SUM(outbreaks) sum,date,DATE_FORMAT(date,'%Y') y FROM oie_epidemiological_event WHERE 1=1 ");
        StringBuffer wherePart = new StringBuffer();
        if (condition.get("startDate") != null) {
            wherePart.append(" and date >= date_format(?,'%Y-%m-%d')");
            valuesPart.add(condition.get("startDate"));
        }
        sqlBuilder.append(wherePart).append(" GROUP BY date ");
        String sql = sqlBuilder.toString();
        List<Map<String,Object>> jsonList = new ArrayList<>();
        hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                for (int i = 0; i < valuesPart.size(); i++) {
                    query.setParameter(i, valuesPart.get(i));
                }
                List list = query.list();
                for (int i=0;i<list.size();i++){
                    Map<String,Object> map2= new HashMap<>();
                    Map map = (Map)list.get(i);
                    map2.put("date",map.get("date"));
                    map2.put("sum",map.get("sum"));
                    map2.put("y",map.get("y"));
                    jsonList.add(map2);
                }
                return jsonList;
            }
        });
        return jsonList;
    }


    @Override
    public List<Map<String, Object>> findEpidemicHistoryScatter() throws Exception {
        List<Map<String,Object>> jsonList = new ArrayList<>();
        String sql = "SELECT disease,disease_name_cn,disease_class,COUNT(report) rn,SUM(outbreaks) sum,COUNT(DISTINCT country) cn " +
                "FROM oie_epidemiological_event a " +
                "LEFT JOIN oie_diseases b " +
                "   ON a.disease_id=b.id " +
                "GROUP BY disease " +
                "ORDER BY sum DESC ";
        hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List list = query.list();
                for (int i=0;i<list.size();i++){
                    Map map = (Map)list.get(i);
                    Map<String,Object> map2= new HashMap<>();
                    map2.put("disease",map.get("disease"));
                    map2.put("epidemicNameCn",map.get("disease_name_cn"));
                    map2.put("epidemicClass",map.get("disease_class"));
                    map2.put("sum",map.get("sum"));
                    map2.put("rn",map.get("rn"));
                    map2.put("cn",map.get("cn"));
                    jsonList.add(map2);
                }
                return jsonList;
            }
        });
        return jsonList;
    }

    @Override
    public Map<String, Object> findTotalOutbreaksAndReportOfDays(int interval)throws Exception{
        Map<String,Object> jsonMap = new HashMap<>();
        String sql = "SELECT SUM(outbreaks) so,COUNT(report) cr " +
                "FROM oie_epidemiological_event " +
                "WHERE date>=SUBDATE(CURDATE(),INTERVAL ? day)";
        hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                query.setParameter(0, interval);
                List list = query.list();
                if(list!=null && list.size()>0){
                    jsonMap.putAll((Map)list.get(0));
                }
                return jsonMap;
            }
        });
        return jsonMap;
    }

}
