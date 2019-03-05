package cn.com.eship.dao.impl;

import cn.com.eship.dao.WeeklyReportExportDao;
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
@Repository
public class WeeklyReportExportDaoImpl implements WeeklyReportExportDao {
    private final HibernateTemplate hibernateTemplate;

    @Autowired
    public WeeklyReportExportDaoImpl(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    @Override
    public List<Map<String,Object>> findTotalCasesGroupByReason(String startDate, String endDate) throws Exception{
        String sql = "SELECT SUM(c.outbreaks) ttc,c.reason " +
                     "FROM(" +
                        "SELECT a.reason,a.outbreaks " +
                        "FROM (oie_epidemiological_event a " +
                        "LEFT JOIN oie_diseases b ON a.disease_id = b.id) " +
                        "WHERE a.date >= date_format(?,'%Y-%m-%d') " +
                            "AND a.date <= date_format(?,'%Y-%m-%d')) c " +
                     "GROUP BY c.reason;";
        List<Map<String,Object>> jsonList = new ArrayList<>();
        hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                query.setParameter(0,startDate);
                query.setParameter(1,endDate);
                List list = query.list();
                for (int i=0;i<list.size();i++){
                    Map map = (Map)list.get(i);
                    Map<String,Object> map2= new HashMap<>();
                    map2.put("ttc",map.get("ttc"));
                    map2.put("reason",map.get("reason"));
                    jsonList.add(map2);
                }
                return jsonList;
            }
        });
        return jsonList;
    }

    @Override
    public Integer findTotalCasesOfOneWeek(String startDate, String endDate) throws Exception{
        String sql = "SELECT SUM(outbreaks) ttc " +
                     "FROM oie_epidemiological_event " +
                     "WHERE date >= date_format(?,'%Y-%m-%d') " +
                        "AND date <= date_format(?,'%Y-%m-%d');";
        int i = (int)hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                query.setParameter(0,startDate);
                query.setParameter(1,endDate);
                Map map = (Map)query.list().get(0);
                int co =map.get("ttc")!=null?Integer.parseInt(map.get("ttc").toString()):0;
                return co;
            }
        });
        return i;
    }

    public List<Map<String,Object>> findTotalCasesOfDiseaseClass(String startDate, String endDate) throws Exception{
        List<Map<String,Object>> jsonList = new ArrayList<>();
        String sql = "SELECT SUM(outbreaks) ttc,disease_class dc " +
                "FROM oie_epidemiological_event a " +
                "LEFT JOIN oie_diseases b ON a.disease_id=b.id " +
                "WHERE date >= date_format(?,'%Y-%m-%d') " +
                "AND date <= date_format(?,'%Y-%m-%d')" +
                "GROUP BY b.disease_class;";
        hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                query.setParameter(0,startDate);
                query.setParameter(1,endDate);
                List list = query.list();
                for (int i=0;i<list.size();i++){
                    Map map = (Map)list.get(i);
                    Map<String,Object> map2= new HashMap<>();
                    map2.put("ttc",map.get("ttc"));
                    map2.put("dc",map.get("dc"));
                    jsonList.add(map2);
                }
                return jsonList;
            }
        });
        return jsonList;
    }

    @Override
    public List<Map<String,Object>> findTotalCasesGroupByDiseaseClass(String startDate, String endDate) throws Exception{
        String sql ="SELECT SUM(d.outbreaks) ttc,COUNT(d.country) cn,d.disease_class dc " +
                    "FROM (" +
                        "SELECT a.country,b.disease_class,a.outbreaks " +
                        "FROM (oie_epidemiological_event a " +
                        "LEFT JOIN oie_diseases b ON a.disease_id = b.id)\n " +
                        "LEFT JOIN oie_world_region c ON a.country_id = c.id " +
                        "WHERE a.date >= date_format(?,'%Y-%m-%d') " +
                            "AND a.date <= date_format(?,'%Y-%m-%d')) d " +
                    "GROUP BY d.disease_class;";
        List<Map<String,Object>> jsonList = new ArrayList<>();
        hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                query.setParameter(0,startDate);
                query.setParameter(1,endDate);
                List list = query.list();
                for (int i=0;i<list.size();i++){
                    Map map = (Map)list.get(i);
                    Map<String,Object> map2= new HashMap<>();
                    map2.put("ttc",map.get("ttc"));
                    map2.put("cn",map.get("cn"));
                    map2.put("diseaseClass",map.get("dc"));
                    jsonList.add(map2);
                }
                return jsonList;
            }
        });
        return jsonList;
    }

    @Override
    public List<Map<String,Object>> findDiseaseHistoryData(String condition) throws Exception{
        String sql ="SELECT SUM(e.outbreaks) ttc,e.disease,e.disease_name_cn,e.country,e.dr,group_concat(distinct e.species) sp,e.region_name_cn,e.continent " +
                "FROM(SELECT a.disease,a.country,a.outbreaks,b.disease_name_cn,b.disease_name_eng,a.date_res dr,c.continent, " +
                "c.region_name_cn,d.species " +
                "FROM ((oie_epidemiological_event a " +
                "LEFT JOIN oie_diseases b ON a.disease_id = b.id) " +
                "LEFT JOIN oie_world_region c ON a.country_id = c.id) " +
                "LEFT JOIN oie_epidemiological_event_species d ON a.report=d.report_id)e " +
                "WHERE " +condition+
                " GROUP BY e.disease,e.country " +
                " ORDER BY ttc DESC ;";
        List<Map<String,Object>> jsonList = new ArrayList<>();
        hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List list = query.list();
                for (int i=0;i<list.size();i++){
                    Map map = (Map)list.get(i);
                    Map<String,Object> map2= new HashMap<>();
                    map2.put("ttc",map.get("ttc"));
                    map2.put("sp",map.get("sp"));
                    map2.put("dr",map.get("dr"));
                    map2.put("epidemicNameCn",map.get("disease_name_cn"));
                    map2.put("epidemicNameEng",map.get("disease_name_eng"));
                    map2.put("disease",map.get("disease"));
                    map2.put("country",map.get("country"));
                    map2.put("regionNameCn",map.get("region_name_cn"));
                    map2.put("regionNameEng",map.get("region_name_eng"));
                    jsonList.add(map2);
                }
                return jsonList;
            }
        });
        return jsonList;
    }

    @Override
    public List<Map<String,Object>> findContinentDiseaseHistoryData(String startDate, String endDate) throws Exception{
        String sql ="SELECT SUM(e.outbreaks) ttc,e.disease_name_cn,e.disease,e.country,group_concat(DISTINCT e.species) sp,e.continent " +
                "FROM(SELECT a.disease,a.country,a.outbreaks,b.disease_name_cn,b.disease_name_eng,b.disease_class," +
                "d.species,c.continent " +
                "FROM ((oie_epidemiological_event a " +
                "LEFT JOIN oie_diseases b ON a.disease_id = b.id) " +
                "LEFT JOIN oie_world_region c ON a.country_id = c.id) " +
                "LEFT JOIN oie_epidemiological_event_species d ON a.report=d.report_id)e " +
                "WHERE e.disease IN(SELECT f.disease FROM oie_epidemiological_event f WHERE f.date >= date_format(?,'%Y-%m-%d') " +
                "AND f.date <= date_format(?,'%Y-%m-%d')) " +
                "AND e.continent IN (SELECT DISTINCT h.continent FROM oie_epidemiological_event g LEFT JOIN oie_world_region h ON g.country_id=h.id WHERE g.date >= date_format(?,'%Y-%m-%d') " +
                "AND g.date <= date_format(?,'%Y-%m-%d')) " +
                "GROUP BY e.disease,e.continent " +
                "ORDER BY e.disease,ttc DESC ;";
        List<Map<String,Object>> jsonList = new ArrayList<>();
        hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                query.setParameter(0,startDate);
                query.setParameter(1,endDate);
                query.setParameter(2,startDate);
                query.setParameter(3,endDate);
                List list = query.list();
                for (int i=0;i<list.size();i++){
                    Map map = (Map)list.get(i);
                    Map<String,Object> map2= new HashMap<>();
                    map2.put("ttc",map.get("ttc"));
                    map2.put("sp",map.get("sp"));
                    map2.put("epidemicNameCn",map.get("disease_name_cn"));
                    map2.put("disease",map.get("disease"));
                    map2.put("continent",map.get("continent"));
                    jsonList.add(map2);
                }
                return jsonList;
            }
        });
        return jsonList;
    }


    @Override
    public List<Map<String,Object>> findDiseaseManifestationData(String startDate, String endDate) throws Exception{
        String sql ="SELECT a.reason,a.manifestation,a.disease,b.disease_name_cn " +
                "FROM oie_epidemiological_event a " +
                "LEFT JOIN oie_diseases b " +
                " ON a.disease_id = b.id " +
                "WHERE a.date >= date_format(?,'%Y-%m-%d') " +
                "AND a.date <= date_format(?,'%Y-%m-%d')" +
                "GROUP BY a.disease;";
        List<Map<String,Object>> jsonList = new ArrayList<>();
        hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                query.setParameter(0,startDate);
                query.setParameter(1,endDate);
                List list = query.list();
                for (int i=0;i<list.size();i++){
                    Map map = (Map)list.get(i);
                    Map<String,Object> map2= new HashMap<>();
                    map2.put("reason",map.get("reason"));
                    map2.put("manifestation",map.get("manifestation"));
                    map2.put("epidemicNameCn",map.get("disease_name_cn"));
                    map2.put("disease",map.get("disease"));
                    jsonList.add(map2);
                }
                return jsonList;
            }
        });
        return jsonList;
    }

    @Override
    public List<Map<String,Object>> findReportReasonData(String startDate, String endDate) throws Exception{
        String sql ="SELECT COUNT(reason) num,reason " +
                "FROM oie_epidemiological_event " +
                "WHERE date >= date_format(?,'%Y-%m-%d') " +
                "AND date <= date_format(?,'%Y-%m-%d') "+
                "GROUP BY reason ;";
        List<Map<String,Object>> jsonList = new ArrayList<>();
        hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                query.setParameter(0,startDate);
                query.setParameter(1,endDate);
                List list = query.list();
                for (int i=0;i<list.size();i++){
                    Map map = (Map)list.get(i);
                    Map<String,Object> map2= new HashMap<>();
                    map2.put("num",map.get("num"));
                    map2.put("reason",map.get("reason"));
                    jsonList.add(map2);
                }
                return jsonList;
            }
        });
        return jsonList;
    }

    @Override
    public List<Map<String,Object>> findDetailTableData(String startDate, String endDate) throws Exception{
        String sql ="SELECT a.date,a.disease,b.disease_name_cn,b.disease_class,a.country,a.outbreaks,a.reason, " +
                "c.region_name_cn,c.continent,a.date_res,a.manifestation,group_concat(DISTINCT d.species) sp " +
                "FROM ((oie_epidemiological_event a " +
                "LEFT JOIN oie_diseases b ON a.disease_id = b.id) " +
                "LEFT JOIN oie_world_region c ON a.country_id = c.id) " +
                "LEFT JOIN oie_epidemiological_event_species d ON a.report=d.report_id " +
                "WHERE a.date >= date_format(?,'%Y-%m-%d')" +
                "AND a.date <= date_format(?,'%Y-%m-%d') "+
                "GROUP BY a.report " +
                "ORDER BY a.date DESC ";
        List<Map<String,Object>> jsonList = new ArrayList<>();
        hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                query.setParameter(0,startDate);
                query.setParameter(1,endDate);
                List list = query.list();
                for (int i=0;i<list.size();i++){
                    Map map = (Map)list.get(i);
                    Map<String,Object> map2= new HashMap<>();
                    map2.put("date",map.get("date"));
                    map2.put("disease",map.get("disease"));
                    map2.put("diseaseNameCn",map.get("disease_name_cn"));
                    map2.put("diseaseClass",map.get("disease_class"));
                    map2.put("country",map.get("country"));
                    map2.put("outbreaks",map.get("outbreaks"));
                    map2.put("regionNameCn",map.get("region_name_cn"));
                    map2.put("continent",map.get("continent"));
                    map2.put("dateRes",map.get("date_res"));
                    map2.put("manifestation",map.get("manifestation"));
                    map2.put("sp",map.get("sp"));
                    map2.put("reason",map.get("reason"));
                    jsonList.add(map2);
                }
                return jsonList;
            }
        });
        return jsonList;
    }

}
