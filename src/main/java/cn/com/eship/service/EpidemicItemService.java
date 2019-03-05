package cn.com.eship.service;

import cn.com.eship.model.ChineseStandardAnimalEpidemicItem;

import java.util.List;

/**
 * Created by simon on 2017/9/25.
 */
public interface EpidemicItemService {
    List<ChineseStandardAnimalEpidemicItem> findEpidemicItemList(ChineseStandardAnimalEpidemicItem epidemicItem) throws Exception;
    List<ChineseStandardAnimalEpidemicItem> findEpidemicItemListByCondition(String condition) throws Exception;
}
