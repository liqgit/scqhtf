package cn.com.eship.service.impl;

import cn.com.eship.dao.OieEpidemiologicalEventEntityDao;
import cn.com.eship.model.OieEpidemiologicalEventEntity;
import cn.com.eship.service.OIEEpidemicPathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by simon on 2017/9/19.
 */
@Service
public class OIEEpidemicPathServiceImpl implements OIEEpidemicPathService {
	@Autowired
	private OieEpidemiologicalEventEntityDao oieEpidemiologicalEventEntityDao;

	@Override
	public Map<String, Object> findOieEpidemiologicalEventEntityList(
			OieEpidemiologicalEventEntity epidemiologicalEventEntity) throws Exception {
		List<Object[]> list = oieEpidemiologicalEventEntityDao
				.findOieEpidemiologicalEventCountPath(epidemiologicalEventEntity);
		List<List<Object>> pathResult = new ArrayList<>();
		List<List<Object>> pathAndValueResult = new ArrayList<>();
		Map<String, Object> resultMap = new HashMap<>();
		// 上一次循环国家名字
		Object pCountryName = null;
		Date pDate = null;
		for (Object[] objectArray : list) {
			if (pCountryName != null && pDate != null) {
				
				Map<Object, Object> pathStartLocationMap = new HashMap<>();
				pathStartLocationMap.put("startDate", pDate);
				pathStartLocationMap.put("endDate", objectArray[0]);
				pathStartLocationMap.put("day", computedDays(pDate, (Date)objectArray[0]));
				pathStartLocationMap.put("name", pCountryName);
				Map<Object, Object> pathEndLocationMap = new HashMap<>();
				pathEndLocationMap.put("name", objectArray[1]);
				Map<Object, Object> pathEndLocationValueMap = new HashMap<>();
				pathEndLocationValueMap.put("startDate", pDate);
				pathEndLocationValueMap.put("endDate", objectArray[0]);
				pathStartLocationMap.put("day", computedDays(pDate, (Date)objectArray[0]));
				pathEndLocationValueMap.put("name", objectArray[1]);
				pathEndLocationValueMap.put("value", objectArray[2]);

				List<Object> pathResultListItem = new ArrayList<>();
				List<Object> pathResultValueListItem = new ArrayList<>();
				pathResultListItem.add(pathStartLocationMap);
				pathResultListItem.add(pathEndLocationMap);
				pathResultValueListItem.add(pathStartLocationMap);
				pathResultValueListItem.add(pathEndLocationValueMap);

				pathResult.add(pathResultListItem);
				pathAndValueResult.add(pathResultValueListItem);
				pDate = (Date)objectArray[0];
				pCountryName = objectArray[1];
			} else {
				pDate = (Date)objectArray[0];
				pCountryName = objectArray[1];
			}
		}
		resultMap.put("pathList", pathResult);
		resultMap.put("pathAndValueResult", pathAndValueResult);
		return resultMap;
	}

	private int computedDays(Date startDate, Date endDate) throws Exception {
		int days = (int) ((endDate.getTime() - startDate.getTime()) / (1000 * 3600 * 24));
		return days < 0 ? 0 : days;
	}
}
