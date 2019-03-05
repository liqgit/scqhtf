package cn.com.eship.controller;

import cn.com.eship.service.OIEEpidemicSearchService;
import cn.com.eship.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/oieEpidemicSearch")
public class OIEEpidemicSearchController {
    @Autowired
    private OIEEpidemicSearchService oieEpidemicSearchService;

    @RequestMapping("/toOIEEpidemicSearchPage")
    public String toOIEEpidemicSearchPage() {
        return "OIEEpidemicSearch";
    }

    @RequestMapping("/toOIEDetailPage")
    public String toOIEDetailPage() {
        return "oieDetailPage";
    }

    @RequestMapping("/regionList")
    public void regionList(HttpServletResponse response) throws Exception {
        response.getOutputStream().write(oieEpidemicSearchService.makeRegionListJson().getBytes("utf-8"));

    }

    @RequestMapping("/epidemicNameList")
    public void epidemicNameList(HttpServletResponse response) throws Exception {
        response.getOutputStream().write(oieEpidemicSearchService.makeEpidemicNameListJson().getBytes("utf-8"));
    }

    @RequestMapping("/epidemicKindList")
    public void epidemicKindList(HttpServletResponse response) throws Exception {
        response.getOutputStream().write(oieEpidemicSearchService.makeEpidemicKindListJson().getBytes("utf-8"));
    }

    @RequestMapping("/epidemicEventList")
    public @ResponseBody
    Map<String, Object> epidemicEventList(String pageNo, String epidemicName, String region, String epidemicClass, String startDate, String endDate, String interval, HttpServletResponse response) throws Exception {
        return oieEpidemicSearchService.makeEpidemicEventListJson(pageNo,epidemicName,region,epidemicClass,startDate,endDate,interval);
    }

    @RequestMapping("epidemicSource")
    public void epidemicSource(String rowKey, HttpServletResponse response) throws Exception {
        response.getOutputStream().write(oieEpidemicSearchService.makeEpidemicSourceJson(rowKey).getBytes("utf-8"));
    }

    @RequestMapping("/getLastWeekEpidemicEventList")
    public @ResponseBody
    Map<String, Object> getLastWeekEpidemicEventList() throws Exception {
        Map<String,String> DatesMap = TimeUtils.getFirstAndLastDayOfLastWeek(new Date());
        String startDate = new SimpleDateFormat("MM/dd/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(DatesMap.get("beginDate")));
        String endDate = new SimpleDateFormat("MM/dd/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(DatesMap.get("endDate")));;
        return oieEpidemicSearchService.makeEpidemicEventListJson(null,null,null,null,startDate,endDate,null);
    }

}
