package cn.com.eship.service.impl;

import cn.com.eship.service.HbaseService;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by simon on 16/8/30.
 */
@Service
public class HbaseServiceImpl implements HbaseService {

    /**
     * 生成疫情百科json
     *
     * @param rowkey
     * @return
     * @throws Exception
     */
    @Override
    public String makeEpiedmicBaikeJson(String rowkey) throws Exception {
        Map<String, String> jsonMap = new HashMap<String, String>();
//        Table table = connection.getTable(TableName.valueOf("epidemicBaike"));
//        Get get = new Get(rowkey.getBytes("utf-8"));
//        Result result = table.get(get);
//        if (result != null) {
//            byte[] imgUrlByte = result.getValue("c1".getBytes("utf-8"), "imgUrl".getBytes("utf-8"));
//            byte[] epidemicNameByte = result.getValue("c1".getBytes("utf-8"), "epidemicName".getBytes("utf-8"));
//            byte[] contentUrlByte = result.getValue("c1".getBytes("utf-8"), "contentUrl".getBytes("utf-8"));
//            byte[] summaryByte = result.getValue("c1".getBytes("utf-8"), "summary".getBytes("utf-8"));
//
//
//            String imgUrl = (imgUrlByte != null && imgUrlByte.length > 1) ? new String(imgUrlByte, "utf-8") : "";
//            String epidemicName = (epidemicNameByte != null && epidemicNameByte.length > 1) ? new String(epidemicNameByte, "utf-8") : "";
//            String contentUrl = (contentUrlByte != null && contentUrlByte.length > 1) ? new String(contentUrlByte, "utf-8") : "";
//            String summary = (summaryByte != null && summaryByte.length > 1) ? new String(summaryByte, "utf-8") : "";
//
//            jsonMap.put("rowKey", rowkey);
//            jsonMap.put("imgUrl", imgUrl);
//            jsonMap.put("epidemicName", epidemicName);
//            jsonMap.put("contentUrl", contentUrl);
//            jsonMap.put("summary", summary);
//        }
        return new ObjectMapper().writeValueAsString(jsonMap);
    }

    @Override
    public String makeEpidemicSourceJson(String rowKey) throws Exception {
        Map<String, String> jsonMap = new HashMap<String, String>();
//        Table table = connection.getTable(TableName.valueOf("epidemicInfo"));
//        Get get = new Get(rowKey.getBytes("utf-8"));
//        Result result = table.get(get);
//        if (result != null) {
//            byte[] contentByte = result.getValue("c1".getBytes("utf-8"), "content".getBytes("utf-8"));
//            byte[] timeByte = result.getValue("c1".getBytes("utf-8"), "time".getBytes("utf-8"));
//            byte[] titleByte = result.getValue("c1".getBytes("utf-8"), "articleTitle".getBytes("utf-8"));
//            String content = (contentByte != null && contentByte.length > 1) ? new String(contentByte, "utf-8") : "";
//            String time = (timeByte != null && timeByte.length > 1) ? new String(timeByte, "utf-8") : "";
//            String title = (titleByte != null && titleByte.length > 1) ? new String(titleByte, "utf-8") : "";
//            jsonMap.put("rowKey", rowKey);
//            jsonMap.put("content", content);
//            jsonMap.put("time", time);
//            jsonMap.put("title", title);
//        }
        return new ObjectMapper().writeValueAsString(jsonMap);
    }
}