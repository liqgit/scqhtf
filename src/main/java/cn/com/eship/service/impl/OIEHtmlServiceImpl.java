package cn.com.eship.service.impl;

import cn.com.eship.dao.OIEHtmlDao;
import cn.com.eship.model.OieHtml;
import cn.com.eship.service.OIEHtmlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("oieHtmlService")
public class OIEHtmlServiceImpl implements OIEHtmlService {
    @Autowired
    private OIEHtmlDao oieHtmlDao;

    @Override
    public void saveOrUpdateHtml(OieHtml oieHtml) {
        oieHtmlDao.saveOrUpdateOieHtml(oieHtml);
    }


    @Override
    public void inertOieData() {
        oieHtmlDao.insertOieData();
    }
}