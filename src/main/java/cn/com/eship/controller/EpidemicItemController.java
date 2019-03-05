package cn.com.eship.controller;

import cn.com.eship.model.ChineseStandardAnimalEpidemicItem;
import cn.com.eship.service.EpidemicItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by simon on 2017/9/25.
 */
@Controller
@RequestMapping("epidemicItem")
public class EpidemicItemController {
    private final EpidemicItemService epidemicItemService;

    @Autowired
    public EpidemicItemController(EpidemicItemService epidemicItemService) {
        this.epidemicItemService = epidemicItemService;
    }

    @RequestMapping("epidemicItemListPage")
    public String EpidemicItemListPage(ChineseStandardAnimalEpidemicItem  chineseStandardAnimalEpidemicItem, Model model) throws Exception {
        List<ChineseStandardAnimalEpidemicItem> epidemicItemList = epidemicItemService.findEpidemicItemList(chineseStandardAnimalEpidemicItem);
        model.addAttribute("epidemicItemList", epidemicItemList);
        return "epidemicItemList";
    }

    @RequestMapping("epidemicItemList")
    public @ResponseBody
    List<ChineseStandardAnimalEpidemicItem> getEpidemicItemList(ChineseStandardAnimalEpidemicItem  chineseStandardAnimalEpidemicItem, HttpServletResponse response) throws Exception {
        return epidemicItemService.findEpidemicItemList(chineseStandardAnimalEpidemicItem);
    }

    @RequestMapping("getEpidemicListByCondition")
    public @ResponseBody
    List<ChineseStandardAnimalEpidemicItem> getEpidemicListByCondition(String condition) throws Exception {
        return epidemicItemService.findEpidemicItemListByCondition(condition);
    }


}
