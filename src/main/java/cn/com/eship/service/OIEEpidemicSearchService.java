package cn.com.eship.service;


import java.util.List;
import java.util.Map;

public interface OIEEpidemicSearchService {
    String makeEpidemicNameListJson() throws Exception;
    String makeRegionListJson() throws Exception;
    String makeEpidemicKindListJson() throws Exception;
    Map<String, Object> makeEpidemicEventListJson(String pageNo, String epidemicName, String region,String epidemicClass, String startDate, String endDate,String interval) throws Exception;
    String makeEpidemicSourceJson(String rowKey) throws Exception;
    List<Map<String, Object>> findAlertListData(String alertDataInterval)throws Exception;
    Map<String, List> findMapListData(String mapDataInterval, String startDate, String endDate,String epidemicName) throws Exception;
}
