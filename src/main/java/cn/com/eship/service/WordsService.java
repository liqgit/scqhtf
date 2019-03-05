package cn.com.eship.service;

import cn.com.eship.model.KindDic;
import cn.com.eship.model.Words;

import java.util.List;
import java.util.Map;

/**
 * Created by simon on 2016/10/7.
 */
public interface WordsService {
    /**
     * 根据条件查询分词
     *
     * @return
     * @throws Exception
     */
    public String makeWordsDicListByCondition(String kindName, String word, String pageNo) throws Exception;

    /**
     * @param id
     */
    public void deleteWords(String id) throws Exception;

    public Words findWordsById(String id) throws Exception;

    public void editWords(String id, String kindId, String word) throws Exception;

    public void addWords(Words words) throws Exception;

    public void addKindDic(KindDic kindDic) throws Exception;

    public void uploadWords() throws Exception;

    public List<Words> getWordsList() throws Exception;
}
