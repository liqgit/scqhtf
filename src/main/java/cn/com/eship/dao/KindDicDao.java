package cn.com.eship.dao;

import cn.com.eship.model.KindDic;
import cn.com.eship.model.Words;

import java.util.List;

/**
 * Created by simon on 2016/10/8.
 */
public interface KindDicDao {
    public List<KindDic> findAllKindDicList() throws Exception;

    public KindDic findKindDicById(String id) throws Exception;

    public void addKindDic(KindDic kindDic) throws Exception;
}
