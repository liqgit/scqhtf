package cn.com.eship.dao;


import java.util.List;
import java.util.Map;

public interface OIEEpidemicDao {
    List<String> findEpidemicNameList() throws Exception;
    List<String> findEpidemicRegionList() throws Exception;
    List<String> findEpidemicKindList() throws Exception;
    int findEpidemicIdByCondition(String condition) throws Exception;
    int findRegionIdByCondition(String condition) throws Exception;
    List<Integer> findEpidemicIdListByCondition(String condition) throws Exception;
    List<Map<String,Object>> findEpidemicEventList(Map<String, Object> condition) throws Exception;
    Long findTotalRecord(Map<String, Object> condition)throws Exception;
    List<Map<String, Object>> findTotalOutbreaksOfDate(Map<String, Object> condition) throws Exception;
    List<Map<String, Object>> findEpidemicHistoryScatter() throws Exception;
    Map<String, Object> findTotalOutbreaksAndReportOfDays(int interval)throws Exception;
}
