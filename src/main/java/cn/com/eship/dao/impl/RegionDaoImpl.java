package cn.com.eship.dao.impl;

import cn.com.eship.dao.RegionDao;
import cn.com.eship.model.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by simon on 16/7/14.
 */
@Repository
public class RegionDaoImpl implements RegionDao {
    @Autowired
    private HibernateTemplate hibernateTemplate;
    /**
     * 获取所有地域
     *
     * @return
     */
    @Override
    public List<Region> findAllRegion() throws Exception {
        String hql = "from Region ";
        Object object = (Object) hibernateTemplate.find(hql);
        return object != null ? (List<Region>) object:null;
    }
}
