package cn.com.eship.controller;

import cn.com.eship.model.OieHtml;
import cn.com.eship.service.CommonService;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by simon on 16/9/19.
 */
@Controller
@RequestMapping("/common")
public class CommonController {
    @Autowired
    private CommonService commonService;

    @RequestMapping("/regionList")
    public void regionList(HttpServletResponse response) throws Exception {
        response.getOutputStream().write(commonService.makeRegionListJson().getBytes("utf-8"));
    }

    @RequestMapping("/epidemicNameList")
    public void epidemicNameList(HttpServletResponse response) throws Exception {
        response.getOutputStream().write(commonService.makeEpidemicNameListJson().getBytes("utf-8"));
    }

    @RequestMapping("/kindNameList")
    public void kindNameList(HttpServletResponse response) throws Exception {
        response.getOutputStream().write(commonService.makekindWordsListJson().getBytes("utf-8"));
    }

    @RequestMapping("/fetchESDataById")
    public @ResponseBody
    Map<String, Object> fetchESDataById(String url, String id) throws Exception {
        OieHtml oieHtml = commonService.findOieHtmlById(id);
        Map<String, Object> oiehtmlmap = new HashMap<>();
        oiehtmlmap.put("disease", oieHtml.getDisease());
        oiehtmlmap.put("date", oieHtml.getDate());
        oiehtmlmap.put("html", oieHtml.getHtml());
        Map<String, Object> map = new HashMap<>();
        map.put("_source", oiehtmlmap);
        return map;
    }
}
