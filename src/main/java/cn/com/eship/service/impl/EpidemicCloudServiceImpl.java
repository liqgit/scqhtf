package cn.com.eship.service.impl;

import cn.com.eship.dao.EpidemicAppearDao;
import cn.com.eship.dao.RegionDao;
import cn.com.eship.model.EpidemicAppear;
import cn.com.eship.service.EpidemicCloudService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.collections.map.LinkedMap;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by simon on 16/7/14.
 */
@Service
public class EpidemicCloudServiceImpl implements EpidemicCloudService {
    @Autowired
    private RegionDao regionDao;
    @Autowired
    private EpidemicAppearDao epidemicAppearDao;

    @Override
    public String makeAllRegionJson() throws Exception {

        return new ObjectMapper().writeValueAsString(regionDao.findAllRegion());
    }

    public String makeEpidemicCloudJson(String startDate, String endDate, String region) throws Exception {
        Map<String, String> mapParam = new HashedMap();
        mapParam.put("startDate", startDate);
        mapParam.put("endDate", endDate);
        mapParam.put("region", region);
        List<Object> epidemicAppearCountList = epidemicAppearDao.findEpidemicAppearCount(mapParam);
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
