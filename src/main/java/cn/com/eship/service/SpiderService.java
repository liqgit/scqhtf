package cn.com.eship.service;

import cn.com.eship.model.Spiders;

import java.io.Serializable;
import java.util.List;

/**
 * Created by simon on 2016/10/24.
 */
public interface SpiderService {
    public String makeSpiderListJson(String spiderName,String pageNo) throws Exception;
    public List<Spiders> findSpidersList(String spiderName) throws Exception;
    public Spiders findSpidersById(Serializable id) throws Exception;
    public void saveOrUpdateSpider(Spiders spiders) throws Exception;
    public void deleteSpider(String id) throws Exception;
}
