package cn.com.eship.dao;

import cn.com.eship.model.OieDiseasesEntity;

import java.util.List;

/**
 * Created by simon on 2017/9/19.
 */
public interface OIEDiseasesEntityDao {
    public List<OieDiseasesEntity> findOieDiseasesEntityList(OieDiseasesEntity oieDiseasesEntity) throws Exception;
}
