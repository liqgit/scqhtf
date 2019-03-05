package cn.com.eship.service.impl;

import cn.com.eship.dao.OIEEpidemicDao;
import cn.com.eship.dao.OIETestDao;
import cn.com.eship.dao.WeeklyReportExportDao;
import cn.com.eship.service.OIEDashboardService;
import cn.com.eship.utils.PageUtils;
import cn.com.eship.utils.TimeUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OIEDashboardServiceImpl implements OIEDashboardService {
    private final WeeklyReportExportDao weeklyReportExportDao;
    private final OIEEpidemicDao oieEpidemicDao;
    private final OIETestDao oieTestDao;
    @Autowired
    public OIEDashboardServiceImpl(WeeklyReportExportDao weeklyReportExportDao, OIEEpidemicDao oieEpidemicDao,OIETestDao oieTestDao) {
        this.weeklyReportExportDao = weeklyReportExportDao;
        this.oieEpidemicDao = oieEpidemicDao;
        this.oieTestDao = oieTestDao;
    }

    public String getDiseaseClassPieData(int dateInterval) throws Exception {
        Map<String,String> DatesMap = TimeUtils.getFirstAndLastDayOfLastWeek(new Date());
        String startDate = DatesMap.get("beginDate");
        String endDate = DatesMap.get("endDate");
        List<Map<String, Object>> casesList = weeklyReportExportDao.findTotalCasesGroupByDiseaseClass(startDate,endDate);
        return new ObjectMapper().writeValueAsString(casesList);
    }

    @Override
    public String getCalendarHeatMapData() throws Exception {
        Map<String, Object> paramMap = new HashMap<>();
        Calendar cDate = Calendar.getInstance();
        cDate.setTime(new Date());
        cDate.set(Calendar.YEAR, cDate.get(Calendar.YEAR)-2);
        cDate.set(Calendar.MONTH,0);
        cDate.set(Calendar.DATE,1);
        String startDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate.getTime());
        paramMap.put("startDate",startDate );
        List<Map<String, Object>> casesList = oieEpidemicDao.findTotalOutbreaksOfDate(paramMap);
        if (casesList != null && casesList.size() > 0) {
            Map<String,List<List<Object>>> yearMap = new HashMap<>();
            List<List<Object>> yearList;
            for (Map<String, Object> map : casesList){
                List<Object> caseList = new ArrayList<>();
                String date = map.get("date").toString();
                int tto = map.get("sum")!=null?Integer.parseInt(map.get("sum").toString()):0;
                caseList.add(date);
                caseList.add(tto);
                String year = (String) map.get("y");
                if (yearMap.get(year)!=null){
                    yearList = yearMap.get(year);
                }else {
                    yearList = new ArrayList<>();
                }
                yearList.add(caseList);
                yearMap.put(year,yearList);
            }
            return new ObjectMapper().writeValueAsString(yearMap);
        }else {
            return null;
        }
    }

    @Override
    public String getDiseaseEventListData(String pageNo,String startDate, String endDate, String epidemicClass) throws Exception {
        Map<String, Object> jsonMap = new HashMap<>();
        Map<String, Object> parmMap = new HashMap<>();
        Map<String,String> DatesMap = TimeUtils.getFirstAndLastDayOfLastWeek(new Date());
        parmMap.put("pageNo", PageUtils.getFirstPosition(StringUtils.isNotBlank(pageNo) ? Integer.parseInt(pageNo) : 0));
        if (StringUtils.isNotBlank(epidemicClass)) {
            parmMap.put("epidemicClass", epidemicClass);
        }
        if (StringUtils.isNotBlank(startDate)) {
            parmMap.put("startDate", startDate);
        }else {
            parmMap.put("startDate", DatesMap.get("beginDate"));
        }
        if (StringUtils.isNotBlank(endDate)) {
            parmMap.put("endDate", endDate);
        }else {
            parmMap.put("endDate", DatesMap.get("endDate"));
        }
        List<Map<String, Object>> epidemicAppearList = oieEpidemicDao.findEpidemicEventList(parmMap);
        if (epidemicAppearList != null && epidemicAppearList.size() > 0) {
            jsonMap.put("epidemicEventList", epidemicAppearList);
            jsonMap.put("epidemicEventListCount", oieEpidemicDao.findTotalRecord(parmMap));
        }
        return new ObjectMapper().writeValueAsString(jsonMap);
    }

    @Override
    public String getDiseaseScatterData() throws Exception {
        List<Map<String, Object>> dataList = oieEpidemicDao.findEpidemicHistoryScatter();
        Map<String,List<Object>> jsonMap = new HashMap<>();
        if (dataList!=null&&dataList.size()>0){

            jsonMap.put("aClass",new ArrayList<>());
            jsonMap.put("bClass",new ArrayList<>());
            jsonMap.put("cClass",new ArrayList<>());
            List<Object> top10 = new ArrayList<>();
            for(int i=0;i<10;i++){
                int sum = Integer.valueOf(dataList.get(i).get("sum").toString());
                top10.add(sum);
            }
            jsonMap.put("top10",top10);
            for (Map<String, Object> map : dataList) {
                String eName = (map.get("epidemicNameCn") != null && !"".equals(map.get("epidemicNameCn"))) ? (String) map.get("epidemicNameCn") : (String) map.get("disease");
                int sum = map.get("sum")!=null?Integer.parseInt(map.get("sum").toString()):0;
                int rn = map.get("rn")!=null?Integer.parseInt(map.get("rn").toString()):0;
                int cn = map.get("cn")!=null?Integer.parseInt(map.get("cn").toString()):0;
                String epidemicClass = map.get("epidemicClass")!=null&& !"".equals(map.get("epidemicClass"))?(String) map.get("epidemicClass"):"其他类";
                Object[] da = new Object[]{eName,sum,rn,cn};
                List<Object> list = new ArrayList<>();
                switch (epidemicClass){
                    case "一类":
                        list = jsonMap.get("aClass");
                        break;
                    case "二类":
                        list = jsonMap.get("bClass");
                        break;
                    case "其他类":
                        list = jsonMap.get("cClass");
                        break;
                }
                list.add(da);
            }

        }
        return new ObjectMapper().writeValueAsString(jsonMap);
    }

    @Override
    public String findGeneralFormData() throws Exception{
        Map<String, Object> jsonData = new HashMap<>();
        int[] ra = findTotalOutbreaksAndReportOfDays(1);
        jsonData.put("oneDay",ra);
        ra = findTotalOutbreaksAndReportOfDays(7);
        jsonData.put("sevenDay",ra);
        ra = findTotalOutbreaksAndReportOfDays(30);
        jsonData.put("thirtyDay",ra);
        return new ObjectMapper().writeValueAsString(jsonData);

    }

    @Override
    public List<Integer> getOutbreaks() throws Exception {
        return oieTestDao.getOutbreaks();
    }

    @Override
    public List<String> getDiseases() throws Exception {
        return oieTestDao.getDiseases();
    }

    private int[] findTotalOutbreaksAndReportOfDays(int interval)throws Exception{
        Map<String, Object> map = oieEpidemicDao.findTotalOutbreaksAndReportOfDays(interval);
        int so = map.get("so")!=null?Integer.parseInt(map.get("so").toString()):0;
        int cr = map.get("cr")!=null?Integer.parseInt(map.get("cr").toString()):0;
        return new int[]{so,cr};
    }


}
