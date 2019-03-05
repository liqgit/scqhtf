package cn.com.eship.controller;

import cn.com.eship.service.DataWarehouseSercice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by simon on 16/9/13.
 */
@Controller
@RequestMapping("/dataWarehouse")
public class DataWarehouseController {
    @Autowired
    private DataWarehouseSercice dataWarehouseSercice;

    /**
     * 跳转到数据仓库首页
     *
     * @return
     */
    @RequestMapping("/toDatawarehousePage")
    public String toDatawarehousePage() {
        return "datawareHourseList";
    }

    @RequestMapping("/datawarehouseList")
    public void datawarehouseList(String word, HttpServletResponse response) throws Exception {
        response.getOutputStream().write(dataWarehouseSercice.makeDataWareHouseListJson(word).getBytes("UTF-8"));
    }

    @RequestMapping("/datawarehouseDetailPage")
    public String datawarehouseDetailsPage(String rowKey, Model model) throws Exception {
        model.addAttribute("rowKey", rowKey);
        return "datawarehouseDetail";
    }


    @RequestMapping("/datawarehouseWords")
    public void datawarehouseWords(String rowKey, HttpServletResponse response) throws Exception {
        response.getOutputStream().write(dataWarehouseSercice.makeDataWareHouseWordListJson(rowKey).getBytes("utf-8"));
    }

    @RequestMapping("/datawarehouseDetail")
    public void datawarehouseDetail(String rowKey, HttpServletResponse response) throws Exception {
        response.getOutputStream().write(dataWarehouseSercice.makeDataWareHouseDetailJson(rowKey).getBytes("utf-8"));
    }

}
