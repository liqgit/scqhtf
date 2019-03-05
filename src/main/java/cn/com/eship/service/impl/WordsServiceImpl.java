package cn.com.eship.service.impl;

import cn.com.eship.dao.KindDicDao;
import cn.com.eship.dao.WordsDao;
import cn.com.eship.model.KindDic;
import cn.com.eship.model.Words;
import cn.com.eship.service.WordsService;
import cn.com.eship.utils.ConfigUtils;
import cn.com.eship.utils.PageUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by simon on 2016/10/7.
 */
@Service
public class WordsServiceImpl implements WordsService {
    @Autowired
    private WordsDao wordsDao;
    @Autowired
    private KindDicDao kindDicDao;

    /**
     * 根据条件查询分词
     *
     * @return
     * @throws Exception
     */
    @Override
    public String makeWordsDicListByCondition(String kindName, String word, String pageNo) throws Exception {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("word", word);
        map.put("kindName", kindName);
        if (StringUtils.isNotBlank(pageNo)) {
            map.put("pageNo", PageUtils.getFirstPosition(Integer.parseInt(pageNo)));
        } else {
            map.put("pageNo", 0);
        }
        jsonMap.put("wordsList", wordsDao.findWordsByCondition(map));
        jsonMap.put("wordsListCount", wordsDao.findWordsCountByCondition(map));
        return new ObjectMapper().writeValueAsString(jsonMap);
    }

    /**
     * @param id
     */
    @Override
    public void deleteWords(String id) throws Exception {
        wordsDao.deleteWords(id);
    }

    @Override
    public Words findWordsById(String id) throws Exception {
        return wordsDao.findWordsById(id);
    }

    @Override
    public void editWords(String id, String kindId, String word) throws Exception {
        Words words = wordsDao.findWordsById(id);
        words.setKindDic(kindDicDao.findKindDicById(kindId));
        words.setWord(word);
        wordsDao.editWordsById(words);
    }

    @Override
    public void addWords(Words words) throws Exception {
        String kindId = words.getKindDic().getId();
        words.setKindDic(kindDicDao.findKindDicById(kindId));
        wordsDao.addWords(words);

    }

    @Override
    public void addKindDic(KindDic kindDic) throws Exception {
        kindDicDao.addKindDic(kindDic);
    }

    @Override
    public void uploadWords() throws Exception {
        List<String> list = wordsDao.findAllWordsList();
        FileUtils.writeLines(new File(ConfigUtils.readValue("upfile.properties", "filePath")), "utf-8", list);
    }

    @Override
    public List<Words> getWordsList() throws Exception {
        List<Words> list = wordsDao.getWordsList();
        return list;
    }
}
