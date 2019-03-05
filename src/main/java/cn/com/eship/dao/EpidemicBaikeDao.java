package cn.com.eship.dao;

import cn.com.eship.model.Baidubaike;

import java.util.List;

/**
 * Created by simon on 16/7/15.
 */
public interface EpidemicBaikeDao {
    public List<Baidubaike> findAllEpidemicBaike() throws Exception;

    public Baidubaike findBaidubaikeById(String id) throws Exception;

}
