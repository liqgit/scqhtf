package cn.com.eship.controller;


import cn.com.eship.model.KindDic;
import cn.com.eship.model.Words;
import cn.com.eship.service.WordsService;
import cn.com.eship.utils.ConfigUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;

/**
 * Created by simon on 16/9/13.
 */
@Controller
@RequestMapping("/words")
public class WordsController {
    private final Logger logger = Logger.getLogger(WordsController.class);
    @Autowired
    private WordsService wordsService;

    @RequestMapping("toWordsPage")
    public String wordsPage() {
        return "wordsList";
    }

    @RequestMapping(value = "/uploadWordsFile", method = RequestMethod.POST)
    public String uploadWordsFile(@RequestParam MultipartFile upfile, Model model) {
        try {
            if (upfile == null || upfile.getBytes() == null || upfile.getBytes().length <= 0) {
                model.addAttribute("upfileStatus", "分词文件不能为空");
                return "wordsList";
            }
            if (!"text/plain".equals(upfile.getContentType())) {
                model.addAttribute("upfileStatus", "请上传文本文件");
                return "wordsList";
            }

            File file = new File(ConfigUtils.readValue("upfile.properties", "filePath"));
            FileUtils.writeByteArrayToFile(file, upfile.getBytes());
            model.addAttribute("upfileStatus", "分词文件上传成功");
        } catch (Exception e) {
            model.addAttribute("upfileStatus", "分词文件上传失败");
        }

        return "wordsList";
    }


    @RequestMapping("/downLoadWordsFile")
    public void downLoadWordsFile(HttpServletResponse response) throws Exception {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/plain");
        IOUtils.copy(new FileInputStream(ConfigUtils.readValue("upfile.properties", "filePath")), response.getOutputStream());
    }

    /**
     * Ajax请求字典列表
     *
     * @param kindName
     * @param words
     * @param pageNo
     * @param response
     * @throws Exception
     */
    @RequestMapping("/wordsList")
    public void wordsList(String kindName, String words, String pageNo, HttpServletResponse response) throws Exception {
        response.getOutputStream().write(wordsService.makeWordsDicListByCondition(kindName, words, pageNo).getBytes("utf-8"));
    }

    /**
     * 删除字典
     *
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping("/deleteWords")
    public String deleteWords(String id) throws Exception {
        wordsService.deleteWords(id);
        return wordsPage();
    }

    /**
     * 字典编辑功能
     *
     * @throws Exception
     */
    @RequestMapping("/editWords")
    public String editWords(String id, Model model) throws Exception {
        model.addAttribute("word", wordsService.findWordsById(id));
        return "editWords";
    }

    @RequestMapping("/addWords")
    public String addWords(String kindName, String word) throws Exception {
        return wordsPage();
    }

    @RequestMapping("/editWordsAction")
    public String editWordsAction(String id, String kindId, String word, Model model) throws Exception {
        wordsService.editWords(id, kindId, word);
        model.addAttribute("message", "更新成功");
        return "wordsList";
    }

    /**
     * 添加新分词分类
     *
     * @param kindName
     * @param response
     */
    @RequestMapping("/addKindDic")
    public void addKindDic(String kindName,String level, HttpServletResponse response) {
        try {
            KindDic kindDic = new KindDic();
            kindDic.setKindName(kindName);
            kindDic.setLevel(Integer.parseInt(level));
            wordsService.addKindDic(kindDic);
            response.getOutputStream().write("1".getBytes());
        } catch (Exception e) {
            logger.error(e.getMessage());
            try {
                response.getOutputStream().write("0".getBytes());
            } catch (Exception e1) {
                logger.error(e1.getMessage());
            }
        }
    }

    /**
     * 添加新分词
     *
     * @param kindId
     * @param response
     * @throws Exception
     */
    @RequestMapping("/addWords2")
    public void addWords(String kindId, String word, HttpServletResponse response) throws Exception {
        try {
            Words words = new Words();
            words.setWord(word);
            KindDic kindDic = new KindDic();
            kindDic.setId(kindId);
            words.setKindDic(kindDic);
            wordsService.addWords(words);
            response.getOutputStream().write("1".getBytes());
        } catch (Exception e) {
            logger.error(e.getMessage());
            try {
                response.getOutputStream().write("0".getBytes());
            } catch (Exception e1) {
                logger.error(e1.getMessage());
            }
        }
    }

    @RequestMapping("/uploadWords")
    public void uploadWords(HttpServletResponse response) throws Exception {
        try {
            wordsService.uploadWords();
            response.getOutputStream().write("1".getBytes());
        } catch (Exception e) {
            logger.error(e.getMessage());
            try {
                response.getOutputStream().write("0".getBytes());
            } catch (Exception e1) {
                logger.error(e1.getMessage());
            }
        }
    }

}
