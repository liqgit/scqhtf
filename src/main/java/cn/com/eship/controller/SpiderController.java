package cn.com.eship.controller;

import cn.com.eship.model.Spiders;
import cn.com.eship.service.SpiderService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/spider")
public class SpiderController {
    private final Logger logger = Logger.getLogger(SpiderController.class);
    @Autowired
    private SpiderService spiderService;

    @RequestMapping("/toSpiderListPage")
    public String toSpiderListPage(String spiderName, Model model) {
        try {
            model.addAttribute("spiderList", spiderService.findSpidersList(spiderName));
            model.addAttribute("spiderName", spiderName);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "spiderList";
    }


    @RequestMapping("/toCreateSpiderPage")
    public String toCreateSpiderPage() {
        return "createSpider";
    }

    @RequestMapping("/toEditSpiderPage")
    public String toEditSpiderPage(String id, Model model) {
        try {
            model.addAttribute("spider", spiderService.findSpidersById(id));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "editSpider";
    }

    @RequestMapping("/spiderList")
    public void spiderList(String pageNo, String spiderName, HttpServletResponse response) {
        try {
            String json = spiderService.makeSpiderListJson(spiderName, pageNo);
            response.getOutputStream().write(json.getBytes("utf-8"));
        } catch (Exception e) {
            logger.error(e.getMessage());
            //response.getOutputStream().write("0",);
        }

    }

    @RequestMapping("/createAndEditSpider")
    public String createAndEditSpider(Spiders spiders, Model model) {
        try {
            spiderService.saveOrUpdateSpider(spiders);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return toSpiderListPage("", model);
    }

    @RequestMapping("/deleteSpider")
    public String deleteSpider(String id, Model model) {
        try {
            spiderService.deleteSpider(id);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return toSpiderListPage("", model);
    }

}
