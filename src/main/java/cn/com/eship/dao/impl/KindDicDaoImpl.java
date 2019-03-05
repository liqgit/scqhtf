package cn.com.eship.dao.impl;

import cn.com.eship.dao.KindDicDao;
import cn.com.eship.model.KindDic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by simon on 2016/10/8.
 */
@Repository
public class KindDicDaoImpl implements KindDicDao {
    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override
    public List<KindDic> findAllKindDicList() throws Exception {
        String hql = "from KindDic kindDic";
        return (List<KindDic>) hibernateTemplate.find(hql);
    }

    @Override
    public KindDic findKindDicById(String id) throws Exception {
        return hibernateTemplate.get(KindDic.class, id);
    }

    @Override
    public void addKindDic(KindDic kindDic) throws Exception {
        hibernateTemplate.save(kindDic);
    }
}
