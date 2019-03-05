package cn.com.eship.service.impl;

import cn.com.eship.dao.EpidemicAppearDao;
import cn.com.eship.dao.EpidemicDao;
import cn.com.eship.model.EpidemicAppear;
import cn.com.eship.service.IndexService;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by simon on 16/7/14.
 */
@Service
public class IndexServiceImpl implements IndexService {
    @Autowired
    private EpidemicAppearDao epidemicAppearDao;
    @Autowired
    private EpidemicDao epidemicDao;

    /**
     * 全球疫情TOP10
     *
     * @return
     */
    @Override
    public String makeEpidemicTopTenJson(EpidemicAppear epidemicAppear)
            throws Exception {
        Map<String, Object> jsonMap = new TreeMap<String, Object>();
        jsonMap.put("epidemicNames", new ArrayList<Object>());
        jsonMap.put("epidemicTop", new ArrayList<Object>());
        List<Object> epidemicToptenList = epidemicAppearDao
                .findEpidemicTopten(setEpidemicAppearDaoParamer(epidemicAppear));
        if (epidemicToptenList != null && epidemicToptenList.size() > 0) {
            List<String> epidemicNamesList = new ArrayList<>();
            List<Map<String, Object>> epidemicTopList = new ArrayList<Map<String, Object>>();
            for (Object epidemic : epidemicToptenList) {
                Map<String, Object> map = new HashMap<String, Object>();
                epidemicNamesList.add((String) (((Object[]) epidemic)[0]));
                map.put("value", ((Object[]) epidemic)[1]);
                map.put("name", ((Object[]) epidemic)[0]);
                epidemicTopList.add(map);
            }
            jsonMap.put("epidemicNames", epidemicNamesList);
            jsonMap.put("epidemicTop", epidemicTopList);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(jsonMap);
    }

    @Override
    public String makeEpidemicLocalTopTenJson(EpidemicAppear epidemicAppear)
            throws Exception {
        Map<String, Object> jsonMap = new TreeMap<String, Object>();
        jsonMap.put("epidemicLocalTopTenNames", new ArrayList<String>());
        jsonMap.put("epidemicLocalTopTenValues", new ArrayList<Long>());
        List<Object> epidemicLocalTopTenList = epidemicAppearDao
                .findEpidemicTopten(setEpidemicAppearDaoParamer(epidemicAppear));
        if (epidemicLocalTopTenList != null
                && epidemicLocalTopTenList.size() > 0) {
            List<Long> epidemicLocalTopTenValues = new ArrayList<Long>();
            List<String> epidemicLocalTopTenNames = new ArrayList<String>();
            for (Object epidemicLocalTopTen : epidemicLocalTopTenList) {
                epidemicLocalTopTenNames
                        .add((String) ((Object[]) epidemicLocalTopTen)[0]);
                epidemicLocalTopTenValues
                        .add((Long) ((Object[]) epidemicLocalTopTen)[1]);
            }
            jsonMap.put("epidemicLocalTopTenNames", epidemicLocalTopTenNames);
            jsonMap.put("epidemicLocalTopTenValues", epidemicLocalTopTenValues);
        }
        return new ObjectMapper().writeValueAsString(jsonMap);
    }

    /**
     * 全球新增疫情数量
     *
     * @param epidemicAppear
     * @return
     */
    @Override
    public Integer findNewEpidemicCount(EpidemicAppear epidemicAppear)
            throws Exception {
        Map<String, Object> mapParam = setEpidemicAppearDaoParamer(epidemicAppear);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        mapParam.put("startDate",
                new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
        List<EpidemicAppear> epidemicAppearListByOrder = epidemicAppearDao
                .findEpidemicAppearList(mapParam);
        return epidemicAppearListByOrder != null ? epidemicAppearListByOrder
                .size() : 0;
    }

    /**
     * 我国新增疫情
     *
     * @param epidemicAppear
     * @return
     */
    @Override
    public Integer findLocalEpidemicLocalCount(EpidemicAppear epidemicAppear)
            throws Exception {
        Map<String, Object> mapParam = setEpidemicAppearDaoParamer(epidemicAppear);
        mapParam.put("region", "31");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        mapParam.put("startDate",
                new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
        List<EpidemicAppear> epidemicAppearListByOrder = epidemicAppearDao
                .findEpidemicAppearList(mapParam);
        return epidemicAppearListByOrder != null ? epidemicAppearListByOrder
                .size() : 0;
    }

    /**
     * 已知疫情
     *
     * @return
     */
    @Override
    public Integer findEpidemicCount() throws Exception {
        return epidemicDao.findEpidemicCount().intValue();
    }

    private Map<String, Object> setEpidemicAppearDaoParamer(
            EpidemicAppear epidemicAppear) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        if (epidemicAppear != null) {
            // 设置地域名称
            if (epidemicAppear.region != null
                    && StringUtils.isNotBlank(epidemicAppear.region
                    .getRegionCn())) {
                map.put("epidemicAppear.region.regionCn",
                        epidemicAppear.region.getRegionCn());
            }
        }
        return map;
    }

    public String findWorldEpidemicAppearsTimeline(String type) throws Exception{
        List<Map<String, String>> list = epidemicAppearDao.findWorldEpidemicAppearsTimeline(type);
        return new ObjectMapper().writeValueAsString(list);
    }
}
