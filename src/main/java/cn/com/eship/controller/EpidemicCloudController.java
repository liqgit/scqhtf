package cn.com.eship.controller;

import cn.com.eship.service.EpidemicCloudService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;

/**
 * Created by simon on 16/7/14.
 */
@Controller
@RequestMapping("epidemicCloud")
public class EpidemicCloudController {
    private final Logger logger = Logger.getLogger(EpidemicCloudController.class);
    @Autowired
    private EpidemicCloudService epidemicCloudService;

    @RequestMapping("epidemicCloudPage")
    public String epidemicCloudPage() {
        return "epidemicCloud";
    }


    @RequestMapping("regionList")
    public void regionList(HttpServletResponse response) {
        String json = "";
        try {
            json = epidemicCloudService.makeAllRegionJson();
            response.getOutputStream().write(json.getBytes("utf-8"));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }


    @RequestMapping("epidemicCloud")
    public void epidemicCloud(String startDate, String endDate,String region,HttpServletResponse response) throws Exception {
        String json = epidemicCloudService.makeEpidemicCloudJson(StringUtils.isNotBlank(startDate) ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new SimpleDateFormat("dd/MM/yyyy").parse(startDate)) : "", StringUtils.isNotBlank(startDate) ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new SimpleDateFormat("dd/MM/yyyy").parse(endDate)) : "",region);
        response.getOutputStream().write(json.getBytes("utf-8"));
    }
}
