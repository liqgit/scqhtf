package cn.com.eship.controller;

import cn.com.eship.service.HbaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by simon on 16/8/30.
 */
@Controller
@RequestMapping("hbaseController")
public class HbaseController {
    @Autowired
    private HbaseService hbaseService;


    /**
     * 返回疫情百科信息
     *
     * @param rowKey   The row key of table epidemicBaike in Hbase
     * @param response
     * @throws Exception
     */
    @RequestMapping("epidemicBaike")
    public void epidemicBaike(String rowKey, HttpServletResponse response) throws Exception {
        response.getOutputStream().write(hbaseService.makeEpiedmicBaikeJson(rowKey).getBytes("utf-8"));
    }


    @RequestMapping("epidemicSource")
    public void epidemicSource(String rowKey, HttpServletResponse response) throws Exception {
        response.getOutputStream().write(hbaseService.makeEpidemicSourceJson(rowKey).getBytes("utf-8"));
    }
}
