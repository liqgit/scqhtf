package cn.com.eship.controller;

import cn.com.eship.service.WordRegionService;
import cn.com.eship.utils.TimeUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by simon on 16/7/15.
 */
@Controller
@RequestMapping("wordRegion")
public class WordRegionController {
    private final Logger logger = Logger.getLogger(WordRegionController.class);
    @Autowired
    private WordRegionService wordRegionService;

    @RequestMapping("wordRegionPage")
    public String wordRegionPage() {
        return "wordRegion";
    }

    @RequestMapping("wordRegion")
    public void wordRegion(String startDate, String endDate, String epidemicName, HttpServletResponse response) throws Exception {
            String json = wordRegionService.makeWordRegionJson(TimeUtils.convertToDateString(startDate), TimeUtils.convertToDateString(endDate),epidemicName);
            response.getOutputStream().write(json.getBytes("utf-8"));
    }

}
