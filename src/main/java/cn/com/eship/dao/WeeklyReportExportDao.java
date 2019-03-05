package cn.com.eship.dao;

import java.util.List;
import java.util.Map;

public interface WeeklyReportExportDao {
    List<Map<String,Object>> findTotalCasesGroupByReason(String startDate, String endDate)throws Exception;
    Integer findTotalCasesOfOneWeek(String startDate, String endDate) throws Exception;
    List<Map<String,Object>> findTotalCasesGroupByDiseaseClass(String startDate, String endDate)throws Exception;
    List<Map<String,Object>> findDiseaseHistoryData(String condition) throws Exception;
    List<Map<String,Object>> findContinentDiseaseHistoryData(String startDate, String endDate) throws Exception;
    List<Map<String,Object>> findDiseaseManifestationData(String startDate, String endDate) throws Exception;
    List<Map<String,Object>> findReportReasonData(String startDate, String endDate) throws Exception;
    List<Map<String,Object>> findDetailTableData(String startDate, String endDate) throws Exception;
    List<Map<String,Object>> findTotalCasesOfDiseaseClass(String startDate, String endDate) throws Exception;
}
