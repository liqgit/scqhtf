package cn.com.eship.dao.impl;

import cn.com.eship.dao.OIEDiseasesEntityDao;
import cn.com.eship.model.OieDiseasesEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by simon on 2017/9/19.
 */
@Repository
public class OIEDiseasesEntityDaoImpl implements OIEDiseasesEntityDao {
    @Autowired
    private HibernateTemplate hibernateTemplate;
    @Override
    public List<OieDiseasesEntity> findOieDiseasesEntityList(OieDiseasesEntity oieDiseasesEntity) throws Exception {
        StringBuffer sqlBuffer = new StringBuffer("from OieDiseasesEntity oieDiseasesEntity where 1 = 1");
        List<OieDiseasesEntity> oieDiseasesEntityList = (List<OieDiseasesEntity>) hibernateTemplate.find(sqlBuffer.toString(),null);
        return oieDiseasesEntityList;
    }
}
