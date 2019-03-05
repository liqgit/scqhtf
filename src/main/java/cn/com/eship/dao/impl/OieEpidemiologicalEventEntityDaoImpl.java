package cn.com.eship.dao.impl;

import cn.com.eship.dao.OieEpidemiologicalEventEntityDao;
import cn.com.eship.model.OieEpidemiologicalEventEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by simon on 2017/9/19.
 */
@Repository
public class OieEpidemiologicalEventEntityDaoImpl implements OieEpidemiologicalEventEntityDao {
    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override
    public List<Object[]> findOieEpidemiologicalEventCountPath(OieEpidemiologicalEventEntity oieEpidemiologicalEventEntity) throws Exception {
        StringBuffer sqlBuffer = new StringBuffer("select oieEpidemiologicalEventEntity.date,oieEpidemiologicalEventEntity.oieWorldRegionEntity.regionNameCn,sum(oieEpidemiologicalEventEntity.outbreaks) from OieEpidemiologicalEventEntity oieEpidemiologicalEventEntity where 1 = 1 and oieEpidemiologicalEventEntity.oieWorldRegionEntity.regionNameCn != ''");
        StringBuffer groupbyPart = new StringBuffer(" group by oieEpidemiologicalEventEntity.date,oieEpidemiologicalEventEntity.oieWorldRegionEntity.regionNameCn");
        StringBuffer orderByPart = new StringBuffer(" order by oieEpidemiologicalEventEntity.date");
        List<Object> values = new ArrayList<>();
        StringBuffer wherePart = new StringBuffer();
        if (oieEpidemiologicalEventEntity != null) {
            if (StringUtils.isNotBlank(oieEpidemiologicalEventEntity.getDisease())) {
                wherePart.append(" and oieEpidemiologicalEventEntity.oieDiseasesEntity.diseaseNameCn = ?");
                values.add(oieEpidemiologicalEventEntity.getDisease());
            }
            if (StringUtils.isNotBlank(oieEpidemiologicalEventEntity.getStartDate())) {
                wherePart.append(" and oieEpidemiologicalEventEntity.date >= date_format(?,'%Y-%m-%d')");
                values.add(oieEpidemiologicalEventEntity.getStartDate());
            }
            if (StringUtils.isNotBlank(oieEpidemiologicalEventEntity.getEndDate())) {
                wherePart.append(" and oieEpidemiologicalEventEntity.date <= date_format(?,'%Y-%m-%d')");
                values.add(oieEpidemiologicalEventEntity.getEndDate());
            }
        }
        List<Object[]> resultList = (List<Object[]>) hibernateTemplate.find(sqlBuffer.append(wherePart).append(groupbyPart).append(orderByPart).toString(), values.toArray());
        return resultList;
    }
}
