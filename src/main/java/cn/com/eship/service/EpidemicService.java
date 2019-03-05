package cn.com.eship.service;

import cn.com.eship.model.EpidemicAppear;

import java.util.List;

/**
 * Created by simon on 16/7/17.
 */
public interface EpidemicService {
    public String makeEpidemicAppearListJson(String pageNo,String epidemicName,String regionCn,String startDate,String endDate) throws Exception;

    /**
     * 首页的疫情概览列表
     * @param flag
     * @return
     * @throws Exception
     */
    public String makeEpidemicAppearListOverideJson(String pageNo,String flag) throws Exception;
    public List<Object> getEpidemicNameList(String keyword) throws Exception;
    public List<Object> getEpidemicRegionList(String keyword) throws Exception;
    public EpidemicAppear getEpidemicAppearById(String id) throws Exception;


    public String makeEpidemicWordListJson(String rowKey) throws Exception;
}
