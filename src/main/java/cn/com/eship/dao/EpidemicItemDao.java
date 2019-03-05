package cn.com.eship.dao;

import cn.com.eship.model.ChineseStandardAnimalEpidemicItem;

import java.util.List;

/**
 * Created by simon on 2017/9/25.
 */
public interface EpidemicItemDao {
    List<ChineseStandardAnimalEpidemicItem> findEpidemicItemList(ChineseStandardAnimalEpidemicItem epidemicItem) throws Exception;
}
