package cn.com.eship.dao.impl;

/**
 * Created by liq on 17/9/22.
 */
import cn.com.eship.dao.OIETestDao;
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
import java.util.List;
import java.util.Map;

@Repository
public class OIETestDaoImpl implements OIETestDao {
    private final HibernateTemplate hibernateTemplate;

    @Autowired
    public OIETestDaoImpl(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }
    @Override
    public List<Integer> getOutbreaks() throws Exception {
        String  sql="SELECT sum(outbreaks) FROM oie_epidemiological_event WHERE DATE_SUB(CURDATE(), INTERVAL 30 DAY) <= DATE GROUP BY disease  ORDER BY sum(outbreaks)  DESC LIMIT 10 ";
        List<Integer> commonList = new ArrayList<Integer>();
        hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List list = query.list();
                for (int i=0;i<list.size();i++){
                    Map map = (Map)list.get(i);
                    commonList.add( Integer.parseInt(map.get("sum(outbreaks)").toString()));
                }
                return commonList;
            }
        });
        return commonList;
    }

    @Override
    public List<String> getDiseases() throws Exception {
        String sql = "SELECT a.date,SUM(a.outbreaks),a.disease,b.disease_name_cn FROM oie_epidemiological_event a  LEFT JOIN oie_diseases b ON a.disease_id=b.id  WHERE DATE_SUB(CURDATE(), INTERVAL 30 DAY) <= a.date GROUP BY a.disease ORDER BY SUM(a.outbreaks) DESC LIMIT 10";
        List<String> commonList = new ArrayList<String>();
        hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List list = query.list();
                for (int i=0;i<list.size();i++){
                    Map map = (Map)list.get(i);
                    if(StringUtils.isNotEmpty((String)map.get("disease_name_cn"))){
                        commonList.add((String) map.get("disease_name_cn"));
                    }else{
                        commonList.add((String) map.get("disease"));
                    }

                }
                return commonList;
            }
        });
        return commonList;
    }
}
