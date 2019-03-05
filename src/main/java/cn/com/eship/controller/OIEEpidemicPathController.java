package cn.com.eship.controller;

import cn.com.eship.model.OieEpidemiologicalEventEntity;
import cn.com.eship.service.CommonService;
import cn.com.eship.service.OIEEpidemicPathService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by simon on 2017/9/19.
 */
@Controller
@RequestMapping("oIEEpidemicPathController")
public class OIEEpidemicPathController {
    @Autowired
    private OIEEpidemicPathService oieEpidemicPathService;
    @Autowired
    private CommonService commonService;

    @RequestMapping("oieEpidemicEventPathResult")
    public void oieEpidemicEventPathResult(OieEpidemiologicalEventEntity oieEpidemiologicalEventEntity, HttpServletResponse response) throws Exception {
        Map<String, Object> map = oieEpidemicPathService.findOieEpidemiologicalEventEntityList(oieEpidemiologicalEventEntity);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(map);
        response.getOutputStream().write(json.getBytes("utf-8"));
    }

    @RequestMapping("oieEpidemicPathPage")
    public String tooieEpidemicPathPage(Model model) throws Exception {
        model.addAttribute("oieDiseasesEntityList",commonService.findOieDiseasesEntityList(null));
        return "oieEpidemicPath";
    }
}
