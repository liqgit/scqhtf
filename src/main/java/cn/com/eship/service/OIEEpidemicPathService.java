package cn.com.eship.service;

import cn.com.eship.model.OieEpidemiologicalEventEntity;

import java.util.Map;

/**
 * Created by simon on 2017/9/19.
 */
public interface OIEEpidemicPathService {
    public Map<String,Object> findOieEpidemiologicalEventEntityList(OieEpidemiologicalEventEntity epidemiologicalEventEntity) throws Exception;

}
