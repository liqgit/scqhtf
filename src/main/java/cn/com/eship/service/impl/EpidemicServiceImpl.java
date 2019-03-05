package cn.com.eship.service.impl;

import cn.com.eship.dao.EpidemicAppearDao;
import cn.com.eship.dao.EpidemicDao;
import cn.com.eship.model.EpidemicAppear;
import cn.com.eship.service.EpidemicService;
import cn.com.eship.utils.ConfigUtils;
import cn.com.eship.utils.MapValueComparator;
import cn.com.eship.utils.PageUtils;
import cn.com.eship.utils.TimeUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by simon on 16/7/17.
 */
@Service
public class EpidemicServiceImpl implements EpidemicService {
    @Autowired
    private EpidemicAppearDao epidemicAppearDao;
    @Autowired
    private EpidemicDao epidemicDao;

    /**
     * 疫情综合查询
     *
     * @param pageNo
     * @param epidemicName
     * @param regionCn
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     */
    @Override
    public String makeEpidemicAppearListJson(String pageNo, String epidemicName, String regionCn, String startDate, String endDate) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        Map<String, Object> parameMap = new HashMap<String, Object>();
        parameMap.put("pageNo", PageUtils.getFirstPosition(StringUtils.isNotBlank(pageNo) ? Integer.parseInt(pageNo) : 0));

        if (StringUtils.isNotBlank(epidemicName)) {
            parameMap.put("epidemicName", epidemicName);
        }
        if (StringUtils.isNotBlank(regionCn)) {
            parameMap.put("regionCn", regionCn);
        }
        if (StringUtils.isNotBlank(startDate)) {
            parameMap.put("startDate", TimeUtils.convertToDateString(startDate));
        }
        if (StringUtils.isNotBlank(endDate)) {
            parameMap.put("endDate", TimeUtils.convertToDateString(endDate));
        }
        List<EpidemicAppear> epidemicAppearList = epidemicAppearDao.findEpidemicAppearListByCondition(parameMap);
        if (epidemicAppearList != null && epidemicAppearList.size() > 0) {
            jsonMap.put("epidemicAppearList", epidemicAppearList);
            jsonMap.put("epidemicAppearListCount", epidemicAppearDao.findEpidemicAppearList(parameMap).size());
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.getSerializationConfig().setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        return objectMapper.writeValueAsString(jsonMap);
    }

    @Override
    public String makeEpidemicAppearListOverideJson(String pageNo, String flag) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        Map<String, Object> parameMap = new HashMap<String, Object>();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String yesterday = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
        parameMap.put("startDate", yesterday);
        parameMap.put("pageNo", PageUtils.getFirstPosition(StringUtils.isNotBlank(pageNo) ? Integer.parseInt(pageNo) : 0));
        if ("1".equals(flag)) {
            parameMap.put("regionCn", "中国");
        }
        //TODO 坑2 parameMap
        List<EpidemicAppear> epidemicAppearList = epidemicAppearDao.findEpidemicAppearListOverride(parameMap);
        if (epidemicAppearList != null && epidemicAppearList.size() > 0) {
            jsonMap.put("epidemicAppearList", epidemicAppearList);
            //TODO 坑1
            jsonMap.put("epidemicAppearListCount", epidemicAppearDao.findEpidemicAppearListOverrideCount(parameMap));
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.getSerializationConfig().setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        return objectMapper.writeValueAsString(jsonMap);
    }

    @Override
    public List<Object> getEpidemicNameList(String keyword) throws Exception {
        List<Object> epidemicNameList = epidemicDao.findEpidemicNameList(keyword);
        return epidemicNameList;
    }

    @Override
    public List<Object> getEpidemicRegionList(String keyword) throws Exception {
        List<Object> epidemicNameList = epidemicDao.findEpidemicRegionList(keyword);
        return epidemicNameList;
    }

    @Override
    public EpidemicAppear getEpidemicAppearById(String id) throws Exception {
        return epidemicAppearDao.findEpidemicAppearById(id);
    }

    @Override
    public String makeEpidemicWordListJson(String rowKey) throws Exception {
        // 请求发布在本地 Tomcat上服务
        Map<String, Object> resultMapJson = new HashMap<String, Object>();
        PostMethod method = new PostMethod(ConfigUtils.readValue("esconfig.properties", "eshost") + "/words/wordline/_search");
        String reslut = "";
        try {
            HttpClient client = new HttpClient();

            method.setRequestHeader("Content-type", "application/json; charset=UTF-8");
            method.setRequestHeader("Accept", "application/json; charset=UTF-8");
            Map<String, Object> jsonMap = new HashMap<String, Object>();
            Map<String, Object> queryMap = new HashMap<String, Object>();
            Map<String, Object> matchMap = new HashMap<String, Object>();
            matchMap.put("rowKey", rowKey);
            queryMap.put("match", matchMap);
            jsonMap.put("query", queryMap);
            method.setRequestBody(new ObjectMapper().writeValueAsString(jsonMap));
            client.executeMethod(method);
            String receive = method.getResponseBodyAsString();
            Map<String, Object> map = new ObjectMapper().readValue(receive, Map.class);
            List<Object> array = new ArrayList<Object>();
            array = (List<Object>) ((Map<String, Object>) map.get("hits")).get("hits");
            for (Object mapTemp : array) {
                Map<String, Object> map1 = (Map<String, Object>) mapTemp;
                Map<String, Object> map2 = (Map<String, Object>) map1.get("_source");
                resultMapJson = (Map<String, Object>) map2.get("wordsMap");
                reslut = sortMapByValue(new ObjectMapper().writeValueAsString(resultMapJson));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            method.releaseConnection();
        }
        return reslut != null ? reslut : "{}";
    }


    private String sortMapByValue(String mapJson) throws Exception {
        //mapJson
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Integer> oriMap = objectMapper.readValue(mapJson, Map.class);
        if (oriMap == null || oriMap.isEmpty()) {
            return null;
        }
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        List<Map.Entry<String, Integer>> entryList = new ArrayList<Map.Entry<String, Integer>>(
                oriMap.entrySet());
        Collections.sort(entryList, new MapValueComparator());

        Iterator<Map.Entry<String, Integer>> iter = entryList.iterator();
        Map.Entry<String, Integer> tmpEntry = null;
        while (iter.hasNext()) {
            tmpEntry = iter.next();
            sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
        }
        return objectMapper.writeValueAsString(sortedMap);
    }


}
