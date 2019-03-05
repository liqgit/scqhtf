package cn.com.eship.dao;

import cn.com.eship.model.Spiders;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by simon on 2016/10/24.
 */
public interface SpiderDao {
    public List<Spiders> findSpiderByCondition(Map<String, Object> map) throws Exception;
    public int findSpiderCountByCondition(Map<String, Object> map) throws Exception;
    public Spiders findSpiderById(Serializable id) throws Exception;
    public void saveSpider(Spiders spiders) throws Exception;
    public void deleteSpider(Spiders spiders) throws Exception;

}
