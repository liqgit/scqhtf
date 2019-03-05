package cn.com.eship.service;

import cn.com.eship.model.EpidemicAppear;

/**
 * Created by simon on 16/7/14.
 */
public interface IndexService {
    /**
     * 疫情TOP10
     *
     * @return
     */
    public String makeEpidemicTopTenJson(EpidemicAppear epidemicAppear) throws Exception;


    public String makeEpidemicLocalTopTenJson(EpidemicAppear epidemicAppear) throws Exception;

    /**
     * 全球新增疫情数量
     *
     * @param epidemicAppear
     * @return
     */
    public Integer findNewEpidemicCount(EpidemicAppear epidemicAppear) throws Exception;


    /**
     * 我国新增疫情
     *
     * @param epidemicAppear
     * @return
     */
    public Integer findLocalEpidemicLocalCount(EpidemicAppear epidemicAppear) throws Exception;

    /**
     * 已知疫情
     *
     * @return
     */
    public Integer findEpidemicCount() throws Exception;
    public String findWorldEpidemicAppearsTimeline(String type) throws Exception;
}
