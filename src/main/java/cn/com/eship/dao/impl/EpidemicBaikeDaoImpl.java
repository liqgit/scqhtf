package cn.com.eship.dao.impl;

import cn.com.eship.dao.EpidemicBaikeDao;
import cn.com.eship.model.Baidubaike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by simon on 16/7/15.
 */
@Repository
public class EpidemicBaikeDaoImpl implements EpidemicBaikeDao {
    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override
    public List<Baidubaike> findAllEpidemicBaike() throws Exception {
        return (List<Baidubaike>) hibernateTemplate.find("from Baidubaike baidubaike left join fetch baidubaike.epidemic epidemictt where epidemictt.epidemicName != null or epidemictt.epidemicName != ''");
    }

    @Override
    public Baidubaike findBaidubaikeById(String id) throws Exception {
        return hibernateTemplate.get(Baidubaike.class, id);
    }
}
