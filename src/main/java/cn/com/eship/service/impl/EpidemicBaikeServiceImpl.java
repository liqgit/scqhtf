package cn.com.eship.service.impl;


import cn.com.eship.service.EpidemicBaikeService;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by simon on 16/7/15.
 */
@Service
public class EpidemicBaikeServiceImpl implements EpidemicBaikeService {

    @Override
    public List<Map<String, String>> findAllepidemicBaikeList() throws Exception {
        List<Map<String, String>> jsonArray = new ArrayList<Map<String, String>>();
//        Table table = connection.getTable(TableName.valueOf("epidemicBaike"));
//        Scan scan = new Scan();
//        ResultScanner results = table.getScanner(scan);
//        Iterator<Result> iterator = results.iterator();
//        while (iterator.hasNext()) {
//            Map<String, String> jsonMap = new HashMap<String, String>();
//            Result result = iterator.next();
//            jsonMap.put("rowKey", new String(result.getRow(), "utf-8"));
//            jsonMap.put("epidemicName", new String(result.getValue("c1".getBytes("utf-8"), "epidemicName".getBytes("utf-8")), "utf-8"));
//            if(result.getValue("c1".getBytes("utf-8"), "imgUrl".getBytes("utf-8")) != null){
//            	jsonMap.put("imgUrl", new String(result.getValue("c1".getBytes("utf-8"), "imgUrl".getBytes("utf-8")), "utf-8"));
//            }
//            jsonArray.add(jsonMap);
//        }
//        table.close();
        return jsonArray;
    }

    @Override
    public String findBaikeByRowkey(String rowKey) throws Exception {
//        Get get = new Get(rowKey.getBytes("utf-8"));
//        Table table = connection.getTable(TableName.valueOf("epidemicBaike"));
//        Result result = table.get(get);
//        table.close();
//        return new String(result.getValue("c1".getBytes("utf-8"), "summary".getBytes("utf-8")));
        return "";
    }
}
