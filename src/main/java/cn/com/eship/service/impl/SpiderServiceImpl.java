package cn.com.eship.service.impl;

import cn.com.eship.dao.SpiderDao;
import cn.com.eship.model.Spiders;
import cn.com.eship.service.SpiderService;
import cn.com.eship.utils.PageUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by simon on 2016/10/24.
 */
@Service
public class SpiderServiceImpl implements SpiderService {
    @Autowired
    private SpiderDao spiderDao;

    @Override
    public String makeSpiderListJson(String spiderName, String pageNo) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        PageUtils.getFirstPosition(StringUtils.isNotBlank(pageNo) ? Integer.parseInt(pageNo) : 0);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageNo", pageNo);
        map.put("spiderName", spiderName);
        List<Spiders> spidersList = spiderDao.findSpiderByCondition(map);
        int spiderConut = spiderDao.findSpiderCountByCondition(map);
        jsonMap.put("spidersList", spidersList);
        jsonMap.put("spiderConut", spiderConut);
        return new ObjectMapper().writeValueAsString(jsonMap);
    }

    @Override
    public List<Spiders> findSpidersList(String spiderName) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("spiderName", spiderName);
        List<Spiders> spidersList = spiderDao.findSpiderByCondition(map);
        return spidersList;
    }

    @Override
    public Spiders findSpidersById(Serializable id) throws Exception {
        return spiderDao.findSpiderById(id);
    }

    @Override
    public void saveOrUpdateSpider(Spiders spiders) throws Exception {
        if (StringUtils.isNotBlank(spiders.getId())) {
            //编辑操作
            Spiders spiderResult = spiderDao.findSpiderById(spiders.getId());
            BeanUtils.copyProperties(spiderResult, spiders);
        } else {
            //保存操作
            spiderDao.saveSpider(spiders);
        }

    }

    @Override
    public void deleteSpider(String id) throws Exception {
        spiderDao.deleteSpider(spiderDao.findSpiderById(id));
    }
}
