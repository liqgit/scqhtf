package cn.com.eship.service;

/**
 * Created by guoji on 2017/7/28 0028.
 */
public interface WeeklyReportExportService {
    byte[] buildWordDoc(String startDateCondition, String svgString, String svgDiseaseDetailBar, String svgDiseaseHistoryBar, String svgReportReasonPie) throws Exception;
    String findDiseaseClassData(String startDateCondition)throws Exception;
    String findDiseaseDetailData(String startDateCondition)throws Exception;
    String findDiseaseHistoryData(String startDateCondition)throws Exception;
    String findReportReasonData(String startDateCondition)throws Exception;
}
