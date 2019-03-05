package cn.com.eship.controller;

import cn.com.eship.service.OIEDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/oieDashboard")
public class OIEDashboardController {

    private final OIEDashboardService oieDashboardService;

    @Autowired
    public OIEDashboardController(OIEDashboardService oieDashboardService) {
        this.oieDashboardService = oieDashboardService;
    }

    @RequestMapping("/toOIEDashboardPage")
    public String toOIEDashboardPage(Model model) throws Exception {
        model.addAttribute("outbreaks",oieDashboardService.getOutbreaks());
        model.addAttribute("diseases",oieDashboardService.getDiseases());
        return "OIEDashboardPage";
    }

    @RequestMapping("/getDiseaseClassPieData")
    public void getDiseaseClassPieData(int dateInterval, HttpServletResponse response)throws Exception {
        response.getOutputStream().write(oieDashboardService.getDiseaseClassPieData(dateInterval).getBytes("utf-8"));
    }

    @RequestMapping("/getCalendarHeatMapData")
    public void getCalendarHeatMapData(HttpServletResponse response)throws Exception {
        response.getOutputStream().write(oieDashboardService.getCalendarHeatMapData().getBytes("utf-8"));
    }
    @RequestMapping("/getDiseaseEventListData")
    public void getDiseaseEventListData(String pageNo,String startDate,String endDate,String epidemicClass,HttpServletResponse response)throws Exception {
        response.getOutputStream().write(oieDashboardService.getDiseaseEventListData(pageNo,startDate,endDate,epidemicClass).getBytes("utf-8"));
    }
    @RequestMapping("/getDiseaseScatterData")
    public void getDiseaseScatterData(HttpServletResponse response)throws Exception {
        response.getOutputStream().write(oieDashboardService.getDiseaseScatterData().getBytes("utf-8"));
    }
    @RequestMapping("/findGeneralFormData.do")
    public void findGeneralFormData(HttpServletResponse response)throws Exception {
        response.getOutputStream().write(oieDashboardService.findGeneralFormData().getBytes("utf-8"));
    }
}
