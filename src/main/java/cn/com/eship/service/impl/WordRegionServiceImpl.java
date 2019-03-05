package cn.com.eship.service.impl;

import cn.com.eship.dao.EpidemicAppearDao;
import cn.com.eship.service.WordRegionService;
import org.apache.commons.collections.map.HashedMap;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by simon on 16/7/15.
 */
@Service
public class WordRegionServiceImpl implements WordRegionService {
    @Autowired
    private EpidemicAppearDao epidemicAppearDao;
    @Override
    public String makeWordRegionJson(String startDate, String endDate, String epidemicName) throws Exception {
        Map<String, String> mapParam = new HashedMap();
        mapParam.put("startDate", startDate);
        mapParam.put("endDate", endDate);
        mapParam.put("epidemicName",epidemicName);
        List<Object> epidemicAppearCountList = epidemicAppearDao.findEpidemicAppearRegionCount(mapParam);
        List<Map<String, Object>> jsonList = new ArrayList<Map<String, Object>>();
        if (epidemicAppearCountList != null && epidemicAppearCountList.size() > 0) {
            for (Object epidemicAppearCount : epidemicAppearCountList) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("name", ((Object[]) epidemicAppearCount)[0]);
                map.put("value", ((Object[]) epidemicAppearCount)[1]);
                jsonList.add(map);
            }
        }
        return new ObjectMapper().writeValueAsString(jsonList);
    }
}
