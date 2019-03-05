package cn.com.eship.service;

import cn.com.eship.model.OieDiseasesEntity;
import cn.com.eship.model.OieHtml;

import java.util.List;

/**
 * Created by simon on 16/9/19.
 */
public interface CommonService {
    public String makeEpidemicNameListJson() throws Exception;

    public String makeRegionListJson() throws Exception;

    public String makekindWordsListJson() throws Exception;

    public List<OieDiseasesEntity> findOieDiseasesEntityList(OieDiseasesEntity oieDiseasesEntity) throws Exception;
    public OieHtml findOieHtmlById(String id);
}
