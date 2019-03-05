package cn.com.eship.controller;

import cn.com.eship.model.EpidemicAppear;
import cn.com.eship.model.Region;
import cn.com.eship.service.IndexService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by simon on 16/7/14.
 */
@Controller
@RequestMapping("index")
public class IndexController {
    private final Logger logger = Logger.getLogger(IndexController.class);
    @Autowired
    private IndexService indexService;

    @RequestMapping("indexPage")
    public String indexPage(Model model) throws Exception {
            //全球昨日新增疫情
            model.addAttribute("newEpidemicCount", indexService.findNewEpidemicCount(new EpidemicAppear()));
            //我国昨日新增疫情
            model.addAttribute("newLocalEpidemicCount", indexService.findLocalEpidemicLocalCount(new EpidemicAppear()));
            //疫情总数
            model.addAttribute("epidemicCount", indexService.findEpidemicCount());
        return "index";
    }

    /**
     * 全球疫情饼状图Top10
     *
     * @param response
     */
    @RequestMapping("epidemicTopTenPie")
    public void epidemicTopTenPie(String regionName, HttpServletResponse response) {
        EpidemicAppear epidemicAppear = new EpidemicAppear();
        epidemicAppear.setRegion(new Region());
        epidemicAppear.getRegion().setRegionCn(regionName);
        try {
            response.getOutputStream().write(indexService.makeEpidemicTopTenJson(epidemicAppear).getBytes("utf-8"));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }


    /**
     * 疫情柱状图TOP10
     *
     * @param regionName
     * @param response
     */
    @RequestMapping("epidemicTopTenBar")
    public void epidemicTopTenBar(String regionName, HttpServletResponse response) {
        EpidemicAppear epidemicAppear = new EpidemicAppear();
        epidemicAppear.setRegion(new Region());
        epidemicAppear.getRegion().setRegionCn(regionName);
        try {
            response.getOutputStream().write(indexService.makeEpidemicLocalTopTenJson(epidemicAppear).getBytes("utf-8"));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

    }

    @RequestMapping("epidemicTimeline")
    public void findWorldEpidemicAppearsTimeline(String type,HttpServletResponse response) {
        try {
            String str = indexService.findWorldEpidemicAppearsTimeline(type);
            response.getOutputStream().write(str.getBytes("utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
