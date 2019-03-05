package cn.com.eship.dao;

import cn.com.eship.model.Region;

import java.util.List;

/**
 * Created by simon on 16/7/14.
 */
public interface RegionDao {
    /**
     * 获取所有地域
     * @return
     */
    public List<Region> findAllRegion() throws Exception;
}
