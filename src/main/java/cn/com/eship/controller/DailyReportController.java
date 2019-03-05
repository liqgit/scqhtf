package cn.com.eship.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by guoji on 2017/4/20 0020.
 */
@Controller
@RequestMapping("/dailyReport")
public class DailyReportController {

    @RequestMapping("/toDailyReportPage")
    public String toDailyReportPage() {
            return "dailyReport";
        }
}
