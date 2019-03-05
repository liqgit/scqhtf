package cn.com.eship.dao;

import cn.com.eship.model.EpidemicAppear;

import java.util.List;
import java.util.Map;

/**
 * Created by simon on 16/7/14.
 */
public interface EpidemicAppearDao {
    /**
     * 获取全球疫情TOP10
     *
     * @return
     * @throws Exception
     */
    public List<Object> findEpidemicTopten(Map<String, Object> mapPram) throws Exception;

    public List<Object> findEpidemicAppearRegionCount(Map<String, String> mapPram) throws Exception;

    public List<Object> findEpidemicAppearCount(Map<String,String> mapPram) throws Exception;

    /**
     * 综合查询
     * @param mapPram
     * @return
     * @throws Exception
     */
    public List<EpidemicAppear> findEpidemicAppearListByCondition(Map<String,Object> mapPram) throws Exception;

    public EpidemicAppear findEpidemicAppearById(String id) throws Exception;

    public List<EpidemicAppear> findEpidemicAppearList(Map<String, Object> mapPram) throws Exception;
    public List<EpidemicAppear> findEpidemicAppearListOverride(Map<String, Object> mapPram) throws Exception;
    public int findEpidemicAppearListOverrideCount(Map<String, Object> mapPram) throws Exception;
    public List<Map<String, String>> findWorldEpidemicAppearsTimeline(String type) throws Exception;
}
