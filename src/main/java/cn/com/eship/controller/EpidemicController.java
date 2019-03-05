package cn.com.eship.controller;


import cn.com.eship.service.EpidemicService;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by simon on 16/7/16.
 */
@Controller
@RequestMapping("epidemic")
public class EpidemicController {
    @Autowired
    private EpidemicService epidemicService;

    /**
     * 跳转到疫情列表  0标示全球新增 1 我国新增
     *
     * @param flag
     * @return
     */
    @RequestMapping("epidemicListPage")
    public String epidemicListPage(String flag, Model model) {
        model.addAttribute("flag", flag);
        return "epidemicList";

    }

    /**
     * 疫情列表
     *
     * @param pageNo
     * @param response
     * @throws Exception
     */
    @RequestMapping("epidemicList")
    public void epidemicList(String pageNo, String epidemicName, String regionCn, String startDate, String endDate, HttpServletResponse response) throws Exception {
        response.getOutputStream().write(epidemicService.makeEpidemicAppearListJson(pageNo, epidemicName, regionCn, startDate, endDate).getBytes("utf-8"));
    }

    @RequestMapping("epidemicListOverRide")
    public void epidemicListOverRide(String pageNo, String flag, HttpServletResponse response) throws Exception {
        response.getOutputStream().write(epidemicService.makeEpidemicAppearListOverideJson(pageNo, flag).getBytes("utf-8"));
    }

    @ResponseBody
    @RequestMapping("epidemicNameList")
    public void epidemicNameList(String keyword, HttpServletResponse response) throws Exception {
        if (keyword != null && !"".equals(keyword)) {
            List<Object> list = epidemicService.getEpidemicNameList(keyword);
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(list);
            response.getOutputStream().write(jsonArray.toString().getBytes("utf-8"));
        }
    }

    @ResponseBody
    @RequestMapping("epidemicRegionList")
    public void epidemicRegionList(String keyword, HttpServletResponse response) throws Exception {
        if (keyword != null && !"".equals(keyword)) {
            List<Object> list = epidemicService.getEpidemicRegionList(keyword);
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(list);
            response.getOutputStream().write(jsonArray.toString().getBytes("utf-8"));
        }
    }

    @RequestMapping("epidemicDetail")
    public String epidemicDetail(String epidemicAppearId, Model model) throws Exception {
        model.addAttribute("epidemicAppear", epidemicService.getEpidemicAppearById(epidemicAppearId));
        return "epidemicDetail";
    }


    @RequestMapping("epidemicWords")
    public void epidemicWords(String rowKey, HttpServletResponse response) throws Exception {
        response.getOutputStream().write(epidemicService.makeEpidemicWordListJson(rowKey).getBytes("utf-8"));
    }
}
