package cn.com.eship.service.impl;

import cn.com.eship.dao.OIEEpidemicDao;
import cn.com.eship.service.OIEEpidemicSearchService;
import cn.com.eship.utils.CommenUtils;
import cn.com.eship.utils.PageUtils;
import cn.com.eship.utils.TimeUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OIEEpidemicSearchServiceImpl implements OIEEpidemicSearchService{


    private final OIEEpidemicDao epidemicDao;

    @Autowired
    public OIEEpidemicSearchServiceImpl(OIEEpidemicDao epidemicDao) {
        this.epidemicDao = epidemicDao;
    }

    @Override
    public String makeEpidemicNameListJson() throws Exception {
        return new ObjectMapper().writeValueAsString(epidemicDao.findEpidemicNameList());
    }

    @Override
    public String makeRegionListJson() throws Exception {
        return new ObjectMapper().writeValueAsString(epidemicDao.findEpidemicRegionList());
    }

    @Override
    public String makeEpidemicKindListJson() throws Exception {
        return new ObjectMapper().writeValueAsString(epidemicDao.findEpidemicKindList());
    }

    @Override
    public  Map<String, Object> makeEpidemicEventListJson(String pageNo, String epidemicName, String region,String epidemicClass, String startDate, String endDate,String interval) throws Exception {
        Map<String, Object> jsonMap = new HashMap<>();
        Map<String, Object> parameMap = new HashMap<>();
        if(StringUtils.isNotEmpty(pageNo)){
            parameMap.put("pageNo",PageUtils.getFirstPosition(Integer.parseInt(pageNo)));
        }
        if (StringUtils.isNotBlank(epidemicName)) {
            int eid = epidemicDao.findEpidemicIdByCondition(epidemicName);
            if (eid==0){
                return jsonMap;
            }else {
                parameMap.put("epidemicId", eid);
            }

        }
        if (StringUtils.isNotBlank(region)) {
            int rid = epidemicDao.findRegionIdByCondition(region);
            parameMap.put("regionId", rid);
        }
        if (StringUtils.isNotBlank(epidemicClass)) {
//            List<Integer> list = epidemicDao.findEpidemicIdListByCondition(epidemicClass);
            parameMap.put("epidemicClass", epidemicClass);
        }
        if (StringUtils.isNotBlank(startDate)) {
            parameMap.put("startDate", TimeUtils.convertToDateString(startDate));
        }
        if (StringUtils.isNotBlank(endDate)) {
            parameMap.put("endDate", TimeUtils.convertToDateString(endDate));
        }
        if (StringUtils.isNotBlank(interval)) {
            parameMap.put("interval", interval);
        }
        List<Map<String, Object>> epidemicAppearList = epidemicDao.findEpidemicEventList(parameMap);
        if (epidemicAppearList != null && epidemicAppearList.size() > 0) {
            jsonMap.put("epidemicEventList", epidemicAppearList);
            jsonMap.put("epidemicEventListCount", epidemicDao.findTotalRecord(parameMap));
            jsonMap.put("result",true);
        }
        return jsonMap;
    }

    @Override
    public String makeEpidemicSourceJson(String rowKey) throws Exception {

        return null;
    }

    @Override
    public List<Map<String, Object>> findAlertListData(String alertDataInterval) throws Exception {
        Map<String, Object> paramMap = new HashMap<>();
        List<Map<String, Object>> jsonList = new ArrayList<>();
        if (StringUtils.isNotBlank(alertDataInterval)) {
            paramMap.put("interval", alertDataInterval);
        }
        List<Map<String, Object>> epidemicAppearList = epidemicDao.findEpidemicEventList(paramMap);
        Map<String, Map<String, Object>> hashMap = new HashMap<>();
        if (epidemicAppearList != null && epidemicAppearList.size() > 0) {
            for (Map<String, Object> report : epidemicAppearList) {
                if (report.get("regionNameEng") != null && !"".equals(report.get("regionNameEng"))) {
                    Map<String, Object> map = new HashMap<>();
                    String region = (String) report.get("regionNameEng");
                    int outbreaks = (int) report.get("outbreaks");
                    String eName = (report.get("epidemicNameCn") != null && !"".equals(report.get("epidemicNameCn"))) ? (String) report.get("epidemicNameCn") : (String) report.get("disease");
                    String event = report.get("date") + " " + report.get("regionNameCn") + " 发生 "
                            + report.get("diseaseClass") + " " + eName + " "+ report.get("outbreaks") + "次";
                    if (hashMap.get(region) != null) {
                        map = hashMap.get(region);
                        map.put("name", region);
                        map.put("value", (int) map.get("value") + outbreaks);
                        map.put("data", map.get("data") + "</br>" +event);
                    } else {
                        map.put("name", region);
                        map.put("value", outbreaks);
                        map.put("data", event);
                    }
                    hashMap.put(region,map);
                }
            }
            jsonList.addAll(hashMap.values());
        }
        return jsonList;
    }

    @Override
    public Map<String, List> findMapListData(String mapDataInterval, String startDate, String endDate,String epidemicName) throws Exception {

        List<Map<String, Object>> epidemicAppearList = getEpidemicAppearList(mapDataInterval,startDate,endDate,epidemicName);
        Map<String, List> jsonMap = new HashMap<>();
        if (epidemicAppearList != null && epidemicAppearList.size() > 0) {
            Map<String, Map<String, List<String>>> regionEventMap = new HashMap<>();
            Map<String,Set<String>> rorMap = new HashMap<>();
            for (Map<String, Object> report : epidemicAppearList) {
                if (report.get("regionNameEng") != null && !"".equals(report.get("regionNameEng"))) {
                    String region = (String) report.get("regionNameEng");
                    String reason = StringUtils.isNotEmpty((String) report.get("reason")) ? (String) report.get("reason") : "";
                    String eName = (report.get("epidemicNameCn") != null && !"".equals(report.get("epidemicNameCn"))) ? (String) report.get("epidemicNameCn") : (String) report.get("disease");
                    String event = " * " + report.get("date") + " " + report.get("regionNameCn") + " 发生 "
                            + report.get("diseaseClass") + " " + eName + " " + report.get("outbreaks") + "次";
                    int level = transformReason(reason);
                    String levelStr = Integer.toString(level);
                    if (regionEventMap.get(region) != null) {
                        Map<String, List<String>> reasonMap = regionEventMap.get(region);
                        if (reasonMap.get(levelStr) != null) {
                            List<String> eventList = reasonMap.get(levelStr);
                            eventList.add(event);
                        } else {
                            List<String> eventList = new ArrayList<>();
                            eventList.add(event);
                            reasonMap.put(levelStr, eventList);
                        }
                    } else {
                        Map<String, List<String>> reasonMap = new HashMap<>();
                        List<String> eventList = new ArrayList<>();
                        eventList.add(event);
                        reasonMap.put(levelStr, eventList);
                        regionEventMap.put(region, reasonMap);
                    }

                    if(rorMap.get(levelStr)!=null){
                        Set<String> set= rorMap.get(levelStr);
                        set.add(region);
                    }else {
                        Set<String> set = new HashSet<>();
                        set.add(region);
                        rorMap.put(levelStr,set);
                    }
                }
            }

            for(Map.Entry<String,Set<String>> entry : rorMap.entrySet()){
                List<Object> jsonList = new ArrayList<>();
                String reason = entry.getKey();
                Set<String> regions= entry.getValue();
                for (String region:regions){
                    List<String> data = new ArrayList<>();
                    Map<String,Object> map = new HashMap<>();
                    Map<String, List<String>> rm = regionEventMap.get(region);
                    if (rm!=null){
                        map.put("name",region);
                        map.put("value",reason);
                        map.put("data",rm);
                        jsonList.add(map);
                    }else {
                        continue;
                    }
                }
                jsonMap.put(reason,jsonList);
            }
            return jsonMap;
        }else {
            return jsonMap;
        }
    }

    private int transformReason(String reason) {
        if (CommenUtils.compareString("First occurrence", reason)) {
            return 256;
        }
        if (CommenUtils.compareString("First occurrence in the country", reason)) {
            return 256;
        }
        if (CommenUtils.compareString("Emerging disease", reason)) {
            return 128;
        }
        if (CommenUtils.compareString("Change in epidemiology", reason)) {
            return 64;
        }
        if (CommenUtils.compareString("New host", reason)) {
            return 32;
        }
        if (CommenUtils.compareString("New pathogen", reason)) {
            return 16;
        }
        if (CommenUtils.compareString("New strain", reason)) {
            return 4;
        }
        if (CommenUtils.compareString("New strain in the country", reason)) {
            return 4;
        }
        if (CommenUtils.compareString("Recurrence", reason)) {
            return 2;
        }
        if (CommenUtils.compareString("Unexpected change or increase", reason)) {
            return 1;
        }
        if (CommenUtils.compareString("Unusual host", reason)) {
            return 0;
        }
        return 0;
    }

    private List<Map<String, Object>> getEpidemicAppearList(String mapDataInterval, String startDate, String endDate,String epidemicName)throws Exception{
        Map<String, Object> paramMap = new HashMap<>();
        if (StringUtils.isNotBlank(mapDataInterval)) {
            paramMap.put("interval", mapDataInterval);
        }
        if (StringUtils.isNotBlank(startDate)) {
            paramMap.put("startDate", TimeUtils.convertToDateString(startDate));
        }
        if (StringUtils.isNotBlank(endDate)) {
            paramMap.put("endDate", TimeUtils.convertToDateString(endDate));
        }
        if (StringUtils.isNotBlank(epidemicName)) {
            int eid = epidemicDao.findEpidemicIdByCondition(epidemicName);
            paramMap.put("epidemicId", eid);
        }
        return epidemicDao.findEpidemicEventList(paramMap);
    }
}
