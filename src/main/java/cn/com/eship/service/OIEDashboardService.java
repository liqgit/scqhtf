package cn.com.eship.service;

import java.util.List;

public interface OIEDashboardService {
    String getDiseaseClassPieData(int dateInterval) throws Exception;
    String getCalendarHeatMapData() throws Exception;
    String getDiseaseEventListData(String pageNo,String startDate,String endDate,String epidemicClass)throws Exception;
    String getDiseaseScatterData() throws Exception;
    String findGeneralFormData() throws Exception;
    List<Integer> getOutbreaks()throws Exception;
    List<String> getDiseases() throws Exception;

}
