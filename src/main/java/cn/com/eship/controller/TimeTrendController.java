package cn.com.eship.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Kid
 * rq  2018/12/7
 */

@Controller
@RequestMapping("timeTrend")
public class TimeTrendController {

    @RequestMapping("timeTrendPage")
    public String timeTrendPage() {
        return "timeTrend";
    }


}
