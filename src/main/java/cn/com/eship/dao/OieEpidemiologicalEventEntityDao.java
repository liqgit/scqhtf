package cn.com.eship.dao;

import cn.com.eship.model.OieEpidemiologicalEventEntity;

import java.util.List;

/**
 * Created by simon on 2017/9/19.
 */
public interface OieEpidemiologicalEventEntityDao {
    public List<Object[]> findOieEpidemiologicalEventCountPath(OieEpidemiologicalEventEntity oieEpidemiologicalEventEntity) throws Exception;
}
