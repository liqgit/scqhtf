package cn.com.eship.dao;

import cn.com.eship.model.OieHtml;

/**
 * Created by simon on 16/7/14.
 */
public interface OIEHtmlDao {
    public void saveOrUpdateOieHtml(OieHtml oieHtml);
    public void insertOieData();
    public OieHtml findOieHtmlById(String id);
    public void updateCountry();
}
