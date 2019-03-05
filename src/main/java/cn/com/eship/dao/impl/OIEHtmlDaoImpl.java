package cn.com.eship.dao.impl;

import cn.com.eship.dao.OIEHtmlDao;
import cn.com.eship.model.OieHtml;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.Date;

@Repository
public class OIEHtmlDaoImpl implements OIEHtmlDao {
    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override
    public void saveOrUpdateOieHtml(OieHtml oieHtml) {
        hibernateTemplate.saveOrUpdate(oieHtml);
    }

    @Override
    public void insertOieData() {
        Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        Date date = new Date();
        String currentYear = simpleDateFormat.format(date);
        String sql = "insert into oie_epidemiological_event" +
                " select LAST_INSERT_ID()          as id," +
                "       t.report_id               as report," +
                "       t.country                 as country," +
                "       t1.id                     as country_id," +
                "       t.date                    as date," +
                "       t.disease                 as disease," +
                "       t2.id                     as disease_id," +
                "       t.reason_for_notification as reason," +
                "       t.disease_manifestation   as manifestation," +
                "       t.outbreaks               as outbreaks," +
                "       t.date_resolved           as date_res" +
                " from t_oie_html t" +
                "       left join oie_world_region t1 on t.country = t1.region_name_eng" +
                "       left join oie_diseases t2 on t.disease = t2.disease_name_eng" +
                " where t.date > '" + currentYear + "-01-01'" +
                "  and t2.id is not null" +
                "  and t.report_id not in (select t.report_id" +
                "                          from t_oie_html t" +
                "                                 join oie_epidemiological_event t1 on t1.report = t.report_id" +
                "                          where t.date >= '" + currentYear + "-01-01')";
        transaction.begin();
        session.createSQLQuery(sql).executeUpdate();
        transaction.commit();
    }

    @Override
    public OieHtml findOieHtmlById(String id) {
        return hibernateTemplate.get(OieHtml.class,id);
    }

    @Override
    public void updateCountry() {
        Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        String sql = "update oie_epidemiological_event set country = 'China',country_id='31' where country like '%China%'";
        transaction.begin();
        session.createSQLQuery(sql).executeUpdate();
        transaction.commit();
    }
}
