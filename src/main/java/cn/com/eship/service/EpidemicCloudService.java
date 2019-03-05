package cn.com.eship.service;


import cn.com.eship.model.EpidemicAppear;

/**
 * Created by simon on 16/7/14.
 */
public interface EpidemicCloudService {
    public String makeAllRegionJson() throws Exception;

    public String makeEpidemicCloudJson(String startDate, String endDate,String region) throws Exception;
}
