package cn.com.eship.service.impl;

import cn.com.eship.dao.EpidemicItemDao;
import cn.com.eship.model.ChineseStandardAnimalEpidemicItem;
import cn.com.eship.service.EpidemicItemService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by simon on 2017/9/25.
 */
@Service
public class EpidemicItemServiceImpl implements EpidemicItemService {
    private final EpidemicItemDao epidemicItemDao;

    @Autowired
    public EpidemicItemServiceImpl(EpidemicItemDao epidemicItemDao) {
        this.epidemicItemDao = epidemicItemDao;
    }

    @Override
    public List<ChineseStandardAnimalEpidemicItem> findEpidemicItemList(ChineseStandardAnimalEpidemicItem epidemicItem) throws Exception {
        return epidemicItemDao.findEpidemicItemList(epidemicItem);
    }

    @Override
    public List<ChineseStandardAnimalEpidemicItem> findEpidemicItemListByCondition(String condition) throws Exception {
        ChineseStandardAnimalEpidemicItem csae = new ChineseStandardAnimalEpidemicItem();
        if (StringUtils.isNotEmpty(condition)){
            Pattern pattern = Pattern.compile("一类|二类|其他类|其它类");
            String conditionStr = condition.trim();
            Matcher whoMatch = pattern.matcher(conditionStr);
            if(whoMatch.find()){
                csae.setDiseaseClass(conditionStr);
            }else {
                csae.setDiseaseNameCn(conditionStr);
            }
        }
        return epidemicItemDao.findEpidemicItemList(csae);
    }

}
