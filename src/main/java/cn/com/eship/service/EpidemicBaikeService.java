package cn.com.eship.service;

import java.util.List;
import java.util.Map;

/**
 * Created by simon on 16/7/15.
 */
public interface EpidemicBaikeService {
    public List<Map<String, String>> findAllepidemicBaikeList() throws Exception;

    public String findBaikeByRowkey(String rowKey) throws Exception;
}
