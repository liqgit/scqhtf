package cn.com.eship.dao.impl;

import cn.com.eship.dao.EpidemicItemDao;
import cn.com.eship.model.ChineseStandardAnimalEpidemicItem;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EpidemicItemDaoImpl implements EpidemicItemDao {
    private final HibernateTemplate hibernateTemplate;

    @Autowired
    public EpidemicItemDaoImpl(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    @Override
    public List<ChineseStandardAnimalEpidemicItem> findEpidemicItemList(ChineseStandardAnimalEpidemicItem epidemicItem) throws Exception {
        List<Object> valueList = new ArrayList<>();
        StringBuilder hqlBuilder = new StringBuilder("FROM ChineseStandardAnimalEpidemicItem epidemicItem WHERE 1 = 1");
        if (epidemicItem != null) {
            if (StringUtils.isNotBlank(epidemicItem.getDiseaseNameCn())) {
                hqlBuilder.append(" AND epidemicItem.diseaseNameCn LIKE ? OR epidemicItem.diseaseNameEng LIKE ?");
                valueList.add("%" + epidemicItem.getDiseaseNameCn() + "%");
                valueList.add("%" + epidemicItem.getDiseaseNameCn() + "%");
            }
            if (StringUtils.isNotBlank(epidemicItem.getDiseaseClass())) {
                hqlBuilder.append(" AND epidemicItem.diseaseClass = ?");
                valueList.add(epidemicItem.getDiseaseClass());
            }
        }
        List<ChineseStandardAnimalEpidemicItem> epidemicItemList = (List<ChineseStandardAnimalEpidemicItem>) hibernateTemplate.find(hqlBuilder.toString(), valueList.toArray());
        return epidemicItemList;
    }
}
