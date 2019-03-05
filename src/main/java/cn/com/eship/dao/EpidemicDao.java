package cn.com.eship.dao;

import cn.com.eship.model.Epidemic;

import java.util.List;

/**
 * Created by simon on 16/7/14.
 */
public interface EpidemicDao {
    /**
     * 获取所有疫情总数
     * @return
     */
    public Long findEpidemicCount() throws Exception;
    public List<Epidemic> findEpidemicList() throws Exception;
    public List<Object> findEpidemicNameList(String keyword) throws Exception;
    public List<Object> findEpidemicRegionList(String keyword) throws Exception;

    public List<String> findEpidemicNameList1() throws Exception;
    public List<String> findEpidemicRegionList1() throws Exception;
}
