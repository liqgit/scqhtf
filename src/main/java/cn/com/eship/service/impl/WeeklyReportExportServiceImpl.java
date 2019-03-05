package cn.com.eship.service.impl;

import cn.com.eship.dao.OIEEpidemicDao;
import cn.com.eship.dao.WeeklyReportExportDao;
import cn.com.eship.service.WeeklyReportExportService;
import cn.com.eship.utils.CommenUtils;
import cn.com.eship.utils.TimeUtils;
import cn.com.eship.utils.wordExportUtils.WorderToNewWordUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class WeeklyReportExportServiceImpl implements WeeklyReportExportService {
	private final WeeklyReportExportDao weeklyReportExportDao;
	private final OIEEpidemicDao oieEpidemicDao;

	private Map<String, Object> textMap;
	private List<List<String[]>> textList;
	private String startDate;
	private String endDate;

	@Autowired
	public WeeklyReportExportServiceImpl(WeeklyReportExportDao weeklyReportExportDao, OIEEpidemicDao oieEpidemicDao) {
		this.weeklyReportExportDao = weeklyReportExportDao;
		this.oieEpidemicDao = oieEpidemicDao;
		Map<String, String> dateMap = TimeUtils.getFirstAndLastDayOfThisWeek(new Date());
		this.startDate = dateMap.get("beginDate");
		this.endDate = dateMap.get("endDate");
	}

	private void buildFirstPart() throws Exception {
		Map<String, String> rm = new HashMap<>();
		rm.put("${startDate}", TimeUtils.convertToChineseDateString(this.startDate));
		rm.put("${endDate}", TimeUtils.convertToChineseDateString(this.endDate));
		List<Map<String, Object>> tcl = weeklyReportExportDao.findTotalCasesGroupByReason(this.startDate, this.endDate);
		Integer totalCases = weeklyReportExportDao.findTotalCasesOfOneWeek(this.startDate, this.endDate);
		Map<String, String> dateMap = TimeUtils.getFirstAndLastDayOfLastWeek(new Date());
		String lastWeekStartDate = dateMap.get("beginDate");
		String lastWeekEndDate = dateMap.get("endDate");
		Integer lastWeekTotalCases = weeklyReportExportDao.findTotalCasesOfOneWeek(lastWeekStartDate, lastWeekEndDate);
		String totalIncrease = String.format("%.0f",
				(double) (totalCases - lastWeekTotalCases) / (lastWeekTotalCases == 0 ? 1 : lastWeekTotalCases) * 100)
				+ "%";
		rm.put("${totalIncrease}", totalIncrease);
		StringBuilder stringBuilder = new StringBuilder();
		if (tcl != null && tcl.size() > 0) {
			for (Map<String, Object> map : tcl) {
				int cases = map.get("ttc") != null ? Integer.parseInt(map.get("ttc").toString()) : 0;
				String reason = "("
						+ (map.get("reason") != null ? (String) map.get("reason") : "Unexpected change or increase")
						+ ")";
				String reasonCn = transformReason(reason);
				if (stringBuilder.length() < 1) {
					if (tcl.size() > 1)
						stringBuilder.append("按OIE动物疫情公布原因统计：本周爆发");
				} /*else {
					stringBuilder.append("；");
				}*/
				stringBuilder.append(reasonCn).append(reason).append("的疫情 ");
				stringBuilder.append(cases).append(" 例 ");
			}
			rm.put("${firstParagraphDetail}", stringBuilder.toString());
			rm.put("${totalCases}", totalCases.toString());
		} else {
			rm.put("${totalCases}", "0");
		}
		if (rm.size() > 0)
			textMap.putAll(rm);
	}

	private void addDiseaseClassData() throws Exception {
		Map<String, String> dateMap = TimeUtils.getFirstAndLastDayOfLastWeek(new Date());
		String lastWeekStartDate = dateMap.get("beginDate");
		String lastWeekEndDate = dateMap.get("endDate");
		Integer lastWeekAClassCases = 0;
		Integer lastWeekBClassCases = 0;
		Integer lastWeekCClassCases = 0;
		Integer thisWeekAClassCases = 0;
		Integer thisWeekBClassCases = 0;
		Integer thisWeekCClassCases = 0;
		List<Map<String, Object>> lastWeekCasesList = weeklyReportExportDao
				.findTotalCasesOfDiseaseClass(lastWeekStartDate, lastWeekEndDate);
		if (lastWeekCasesList != null && lastWeekCasesList.size() > 0) {
			for (Map<String, Object> map : lastWeekCasesList) {
				Integer cases = map.get("ttc") != null ? Integer.parseInt(map.get("ttc").toString()) : 0;
				String diseaseClass = (map.get("dc") != null) ? (String) map.get("dc") : "其他类";
				switch (diseaseClass) {
				case "一类":
					lastWeekAClassCases = cases;
					break;
				case "二类":
					lastWeekBClassCases = cases;
					break;
				case "其他类":
					lastWeekCClassCases = cases;
					break;
				}
			}
		}
		List<Map<String, Object>> thisWeekCasesList = weeklyReportExportDao.findTotalCasesOfDiseaseClass(this.startDate,
				this.endDate);
		if (thisWeekCasesList != null && thisWeekCasesList.size() > 0) {
			for (Map<String, Object> map : thisWeekCasesList) {
				Integer cases = map.get("ttc") != null ? Integer.parseInt(map.get("ttc").toString()) : 0;
				String diseaseClass = (map.get("dc") != null) ? (String) map.get("dc") : "其他类";
				switch (diseaseClass) {
				case "一类":
					thisWeekAClassCases = cases;
					break;
				case "二类":
					thisWeekBClassCases = cases;
					break;
				case "其他类":
					thisWeekCClassCases = cases;
					break;
				}
			}
			textMap.put("${aClassCases}", thisWeekAClassCases.toString());
			textMap.put("${aClassIncrease}", String.format("%.0f", (double) (thisWeekAClassCases - lastWeekAClassCases)
					/ (lastWeekAClassCases == 0 ? 1 : lastWeekAClassCases) * 100) + "%");
			textMap.put("${bClassCases}", thisWeekBClassCases.toString());
			textMap.put("${bClassIncrease}", String.format("%.0f", (double) (thisWeekBClassCases - lastWeekBClassCases)
					/ (lastWeekBClassCases == 0 ? 1 : lastWeekBClassCases) * 100) + "%");
			textMap.put("${cClassCases}", thisWeekCClassCases.toString());
			textMap.put("${cClassIncrease}", String.format("%.0f", (double) (thisWeekCClassCases - lastWeekCClassCases)
					/ (lastWeekCClassCases == 0 ? 1 : lastWeekCClassCases) * 100) + "%");
		}
	}

	private void buildSecondPart() throws Exception {
		this.addDiseaseClassData();
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("startDate", this.startDate);
		paramMap.put("endDate", this.endDate);
		Map<String, String> rm = new HashMap<>();
		List<Map<String, Object>> casesList = oieEpidemicDao.findEpidemicEventList(paramMap);
		/*if (casesList != null && casesList.size() > 0) {
			StringBuilder aClassStr = new StringBuilder("具体情况为：");
			StringBuilder bClassStr = new StringBuilder("具体情况为：");
			StringBuilder cClassStr = new StringBuilder("具体情况为：");
			for (Map<String, Object> map : casesList) {
				StringBuilder stringBuilder = new StringBuilder();
				String diseaseClass = (map.get("diseaseClass") != null) ? (String) map.get("diseaseClass") : "其他类";
				int outbreaks = map.get("outbreaks") != null ? Integer.parseInt(map.get("outbreaks").toString()) : 0;
				String reason = "("
						+ (map.get("reason") != null ? (String) map.get("reason") : "Unexpected change or increase")
						+ ")";
				String reasonCn = transformReason(reason);
				String eName = (map.get("epidemicNameCn") != null && !"".equals(map.get("epidemicNameCn")))
						? (String) map.get("epidemicNameCn")
						: (String) map.get("disease");
				String region = (map.get("regionNameCn") != null && !"".equals(map.get("regionNameCn")))
						? (String) map.get("regionNameCn")
						: (String) map.get("country");
				stringBuilder.append(eName).append("在").append(region).append("共发生 ").append(outbreaks)
						.append(" 例，发布原因: ").append(reasonCn).append(reason);
				switch (diseaseClass) {
				case "一类":
					aClassStr.append(stringBuilder).append("；");
					break;
				case "二类":
					bClassStr.append(stringBuilder).append("；");
					break;
				case "其他类":
					cClassStr.append(stringBuilder).append("；");
					break;
				}
			}
			if (aClassStr.length() > 6) {
				rm.put("${aTypeDisease}", aClassStr.toString());
			} else {
				rm.put("${aTypeDisease}", "");
			}
			if (bClassStr.length() > 6) {
				rm.put("${bTypeDisease}", bClassStr.toString());
			} else {
				rm.put("${bTypeDisease}", "");
			}
			if (cClassStr.length() > 6) {
				rm.put("${cTypeDisease}", cClassStr.toString());
			} else {
				rm.put("${cTypeDisease}", "");
			}
		}*/
		if (rm.size() > 0)
			textMap.putAll(rm);
	}

	/*private void buildLastPart() throws Exception {
		List<Map<String, Object>> tcl = weeklyReportExportDao.findDiseaseManifestationData(this.startDate,
				this.endDate);
		if (tcl != null && tcl.size() > 0) {
			Map<String, StringBuilder> reasonMap = new HashMap<>();
			StringBuilder rs = new StringBuilder("本周疫情中，");
			for (Map<String, Object> map : tcl) {
				String reason = map.get("reason") != null ? (String) map.get("reason")
						: "Unexpected change or increase";
				String manifestation = (map.get("manifestation") != null && !"".equals(map.get("manifestation")))
						? (String) map.get("manifestation")
						: "不详";
				String reasonCn = transformReason(reason);
				String eName = (map.get("epidemicNameCn") != null && !"".equals(map.get("epidemicNameCn")))
						? (String) map.get("epidemicNameCn")
						: (String) map.get("disease");
				if (reasonMap.get(reason) == null) {
					StringBuilder stringBuilder = new StringBuilder();
					stringBuilder.append(reasonCn).append("的疫情有 ").append(eName).append("其症状为 ").append(manifestation);
					reasonMap.put(reason, stringBuilder);
				} else {
					StringBuilder stringBuilder = reasonMap.get(reason);
					stringBuilder.append(" ; ").append(eName).append("其症状为 ").append(manifestation);
					reasonMap.put(reason, stringBuilder);
				}
			}
			for (Map.Entry<String, StringBuilder> entry : reasonMap.entrySet()) {
				rs.append(entry.getValue()).append("。");
			}
			if (rs.length() > 6)
				textMap.put("${reasonManifestation}", rs.toString());
		}

	}*/

	private void buildThirdPart() throws Exception {
		StringBuilder s = new StringBuilder("本周");
		List<Map<String, Object>> tcl = weeklyReportExportDao.findTotalCasesGroupByDiseaseClass(this.startDate,
				this.endDate);
		if (tcl != null && tcl.size() > 0) {
			StringBuilder aClassStr = new StringBuilder("一类疫情发生 ");
			StringBuilder bClassStr = new StringBuilder("二类疫情发生 ");
			StringBuilder cClassStr = new StringBuilder("其他类疫情发生 ");
			int totalCases = Integer.parseInt((String) textMap.get("${totalCases}"));
			for (Map<String, Object> map : tcl) {
				int cases = map.get("ttc") != null ? Integer.parseInt(map.get("ttc").toString()) : 0;
				if (cases == 0)
					continue;
				int countryNum = map.get("cn") != null ? Integer.parseInt(map.get("cn").toString()) : 0;
				String diseaseClass = (map.get("diseaseClass") != null) ? (String) map.get("diseaseClass") : "其他类";
				switch (diseaseClass) {
				case "一类":
					aClassStr.append(cases).append(" 例");
							//.append(String.format("%.0f", (double) cases / totalCases * 100)).append("%，涉及 ");
					//aClassStr.append(countryNum).append(" 个国家；");
					break;
				case "二类":
					bClassStr.append(cases).append(" 例");
							//.append(String.format("%.0f", (double) cases / totalCases * 100)).append("%，涉及 ");
					//bClassStr.append(countryNum).append(" 个国家；");
					break;
				case "其他类":
					cClassStr.append(cases).append(" 例");
							//.append(String.format("%.0f", (double) cases / totalCases * 100)).append("%，涉及 ");
					//cClassStr.append(countryNum).append(" 个国家；");
					break;
				}
			}
			if (aClassStr.length() > 7)
				s.append(aClassStr);
			if (bClassStr.length() > 7)
				s.append(bClassStr);
			if (cClassStr.length() > 8)
				s.append(cClassStr);
			if (textMap.size() > 0 && s.length() > 2)
				textMap.put("${classification}", s.toString());
		}
	}

	@Override
	public String findDiseaseClassData(String startDateCondition) throws Exception {
		if (StringUtils.isNotEmpty(startDateCondition)) {
			Map<String, String> dateMap = TimeUtils
					.getFirstAndLastDayOfThisWeek(new SimpleDateFormat("MM/dd/yyyy").parse(startDateCondition));
			this.startDate = dateMap.get("beginDate");
			this.endDate = dateMap.get("endDate");
		}
		List<Map<String, Object>> casesList = weeklyReportExportDao.findTotalCasesGroupByDiseaseClass(this.startDate,
				this.endDate);
		return new ObjectMapper().writeValueAsString(casesList);
	}

	@Override
	public String findDiseaseHistoryData(String startDateCondition) throws Exception {
		if (StringUtils.isNotEmpty(startDateCondition)) {
			Map<String, String> dateMap = TimeUtils
					.getFirstAndLastDayOfThisWeek(new SimpleDateFormat("MM/dd/yyyy").parse(startDateCondition));
			this.startDate = dateMap.get("beginDate");
			this.endDate = dateMap.get("endDate");
		}
		List<String[]> list = findDiseaseHistoryDataList();
		return new ObjectMapper().writeValueAsString(list);

	}

	@Override
	public String findReportReasonData(String startDateCondition) throws Exception {
		if (StringUtils.isNotEmpty(startDateCondition)) {
			Map<String, String> dateMap = TimeUtils
					.getFirstAndLastDayOfThisWeek(new SimpleDateFormat("MM/dd/yyyy").parse(startDateCondition));
			this.startDate = dateMap.get("beginDate");
			this.endDate = dateMap.get("endDate");
		}
		List<Map<String, Object>> casesList = weeklyReportExportDao.findReportReasonData(this.startDate, this.endDate);
		if (casesList != null && casesList.size() > 0) {
			for (Map<String, Object> map : casesList) {
				String reason = map.get("reason") != null ? (String) map.get("reason")
						: "Unexpected change or increase";
				String reasonCn = transformReason(reason);
				map.put("reason", reasonCn);
			}
		}
		return new ObjectMapper().writeValueAsString(casesList);
	}

	@Override
	public String findDiseaseDetailData(String startDateCondition) throws Exception {
		if (StringUtils.isNotEmpty(startDateCondition)) {
			Map<String, String> dateMap = TimeUtils
					.getFirstAndLastDayOfThisWeek(new SimpleDateFormat("MM/dd/yyyy").parse(startDateCondition));
			this.startDate = dateMap.get("beginDate");
			this.endDate = dateMap.get("endDate");
		}
		Map<String, Object> paramMap = new HashMap<>();
		List<Map<String, Object>> rl = new ArrayList<>();
		Map<String, List<Object>> rm = new HashMap<>();
		paramMap.put("startDate", this.startDate);
		paramMap.put("endDate", this.endDate);
		paramMap.put("orderBy", "a.outbreaks Desc");
		List<Map<String, Object>> tcl = oieEpidemicDao.findEpidemicEventList(paramMap);
		if (tcl != null && tcl.size() > 0) {
			for (Map<String, Object> map : tcl) {
				int outbreaks = map.get("outbreaks") != null ? Integer.parseInt(map.get("outbreaks").toString()) : 0;
				String eName = (map.get("epidemicNameCn") != null && !"".equals(map.get("epidemicNameCn")))
						? (String) map.get("epidemicNameCn")
						: (String) map.get("disease");
				String region = (map.get("regionNameCn") != null && !"".equals(map.get("regionNameCn")))
						? (String) map.get("regionNameCn")
						: (String) map.get("country");
				List<Object> dataList;
				List<Object> tempList = new ArrayList<>();
				if (rm.get(region) != null) {
					dataList = rm.get(region);
				} else {
					dataList = new ArrayList<>();
				}
				tempList.add(eName);
				tempList.add(outbreaks);
				dataList.add(tempList);
				rm.put(region, dataList);
			}
			for (Map.Entry<String, List<Object>> entry : rm.entrySet()) {
				Map<String, Object> fm = new HashMap<>();
				fm.put("name", entry.getKey());
				fm.put("data", entry.getValue());
				rl.add(fm);
			}
			return new ObjectMapper().writeValueAsString(rl);
		}
		return "";

	}

	private List<String[]> findDiseaseHistoryDataList() throws Exception {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("startDate", this.startDate);
		paramMap.put("endDate", this.endDate);
		List<Map<String, Object>> casesList = oieEpidemicDao.findEpidemicEventList(paramMap);
		StringBuilder condition = new StringBuilder();
		if (casesList != null && casesList.size() > 0) {
			for (Map<String, Object> map : casesList) {
				String eName = (map.get("disease") != null && !"".equals(map.get("disease")))
						? (String) map.get("disease")
						: "";
				String country = (map.get("country") != null && !"".equals(map.get("country")))
						? (String) map.get("country")
						: "";
				if (condition.length() > 1) {
					condition.append(" OR ");
				}
				condition.append("(e.disease='").append(eName.replace("'", "\\'")).append("' AND e.country='")
						.append(country.replace("'", "\\'")).append("') ");
			}
		} else {
			return null;
		}
		List<Map<String, Object>> tcl = weeklyReportExportDao.findDiseaseHistoryData(condition.toString());
		List<String[]> tableList = new ArrayList<>();
		if (tcl != null && tcl.size() > 0) {
			for (Map<String, Object> map : tcl) {
				String eName = (map.get("epidemicNameCn") != null && !"".equals(map.get("epidemicNameCn")))
						? (String) map.get("epidemicNameCn")
						: (String) map.get("disease");
				String region = (map.get("regionNameCn") != null && !"".equals(map.get("regionNameCn")))
						? (String) map.get("regionNameCn")
						: (String) map.get("country");
				int outbreaks = map.get("ttc") != null ? Integer.parseInt(map.get("ttc").toString()) : 0;
				String dr = (map.get("dr") != null && !"".equals(map.get("dr"))) ? (String) map.get("dr") : "";
				String species = (map.get("sp") != null && !"".equals(map.get("sp"))) ? (String) map.get("sp") : "";
				tableList.add(new String[] { eName, region, Integer.toString(outbreaks), dr, species });
			}
		}
		return tableList;
	}

	private List<String[]> findContinentDiseaseHistoryData() throws Exception {
		List<Map<String, Object>> tcl = weeklyReportExportDao.findContinentDiseaseHistoryData(this.startDate,
				this.endDate);
		List<String[]> tableList = new ArrayList<>();
		if (tcl != null && tcl.size() > 0) {
			for (Map<String, Object> map : tcl) {
				String eName = (map.get("epidemicNameCn") != null && !"".equals(map.get("epidemicNameCn")))
						? (String) map.get("epidemicNameCn")
						: (String) map.get("disease");
				int outbreaks = map.get("ttc") != null ? Integer.parseInt(map.get("ttc").toString()) : 0;
				String continent = (map.get("continent") != null && !"".equals(map.get("continent")))
						? (String) map.get("continent")
						: "";
				String species = (map.get("sp") != null && !"".equals(map.get("sp"))) ? (String) map.get("sp") : "";
				tableList.add(new String[] { continent, eName, Integer.toString(outbreaks), species });
			}
		}
		return tableList;
	}

	private List<String[]> findDetailTableData() throws Exception {
		List<Map<String, Object>> tcl = weeklyReportExportDao.findDetailTableData(this.startDate, this.endDate);
		List<String[]> tableList = new ArrayList<>();
		if (tcl != null && tcl.size() > 0) {
			for (Map<String, Object> map : tcl) {
				String eName = (map.get("diseaseNameCn") != null && !"".equals(map.get("diseaseNameCn")))
						? (String) map.get("diseaseNameCn")
						: (String) map.get("disease");
				String diseaseClass = (map.get("diseaseClass") != null) ? (String) map.get("diseaseClass") : "其他类";
				int outbreaks = map.get("outbreaks") != null ? Integer.parseInt(map.get("outbreaks").toString()) : 0;
				String continent = (map.get("continent") != null && !"".equals(map.get("continent")))
						? (String) map.get("continent")
						: "";
				String species = (map.get("sp") != null && !"".equals(map.get("sp"))) ? (String) map.get("sp") : "";
				String date = map.get("date").toString();
				String region = (map.get("regionNameCn") != null && !"".equals(map.get("regionNameCn")))
						? (String) map.get("regionNameCn")
						: (String) map.get("country");
				String dateRes = (map.get("dateRes") != null && !"".equals(map.get("dateRes")))
						? (String) map.get("dateRes")
						: "";
				String reason = map.get("reason") != null ? (String) map.get("reason")
						: "Unexpected change or increase";
				String manifestation = (map.get("manifestation") != null && !"".equals(map.get("manifestation")))
						? (String) map.get("manifestation")
						: "不详";
				String reasonCn = transformReason(reason);
				tableList.add(new String[] { date, eName, diseaseClass, reasonCn, manifestation,
						Integer.toString(outbreaks), dateRes, region, continent, species });
			}
		}
		return tableList;
	}

	private void addToPicsMap(String svgString, int height, int width, String mapKey) {
	/*	if (StringUtils.isEmpty(svgString)) {
			textMap.put(mapKey, "");
		}
		WordPicture wordPicture = new WordPicture();
		ByteArrayInputStream inputStream = null;
		try {
			inputStream = CommenUtils.convertToPng(svgString);
			byte[] content = CommenUtils.inputStream2ByteArray(inputStream, true);
			wordPicture.setContent(content);
			wordPicture.setHeight(height);
			wordPicture.setWidth(width);
			wordPicture.setType("jpg");
			textMap.put(mapKey, wordPicture);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}*/
	}

	@Override
	public byte[] buildWordDoc(String startDateCondition, String svgDiseaseClassPie, String svgDiseaseDetailBar,
			String svgDiseaseHistoryBar, String svgReportReasonPie) throws Exception {
		this.textMap = new HashMap<>();
		this.textList = new ArrayList<>();
		if (StringUtils.isNotEmpty(startDateCondition)) {
			Map<String, String> dateMap = TimeUtils
					.getFirstAndLastDayOfThisWeek(new SimpleDateFormat("MM/dd/yyyy").parse(startDateCondition));
			this.startDate = dateMap.get("beginDate");
			this.endDate = dateMap.get("endDate");
		}
		buildFirstPart();
		buildSecondPart();
		buildThirdPart();
		//buildLastPart();

		/*addToPicsMap(svgDiseaseClassPie, 300, 450, "${pic1}");
		addToPicsMap(svgDiseaseDetailBar, 350, 600, "${pic2}");
		addToPicsMap(svgDiseaseHistoryBar, 350, 600, "${pic3}");
		addToPicsMap(svgReportReasonPie, 300, 450, "${pic4}");
		*/
		textList.add(findDiseaseHistoryDataList());
		textList.add(findContinentDiseaseHistoryData());
		textList.add(findDetailTableData());
		//String inputUrl = "/Volumes/Transcend/app/reportTemplet.docx";
		String templatePath = this.getClass().getClassLoader().getResource("").getPath() + "app/";
		String inputUrl = templatePath + "reportTemplet.docx";
		if (textMap.get("${totalCases}") == null || "0".equals(textMap.get("${totalCases}"))) {
			inputUrl = templatePath + "no_data_reportTemplet.docx";
			//inputUrl = "/Volumes/Transcend/app/reportTemplet.docx";
		}
		return WorderToNewWordUtils.changWord(inputUrl, textMap, textList);
	}

	private String transformReason(String reason) {
		if (CommenUtils.compareString("First occurrence", reason)) {
			return "第一次发生";
		} else if (CommenUtils.compareString("First occurrence in the country", reason)) {
			return "第一次在该国发生";
		} else if (CommenUtils.compareString("Emerging disease", reason)) {
			return "紧急发病";
		} else if (CommenUtils.compareString("Change in epidemiology", reason)) {
			return "流行病学发生改变";
		} else if (CommenUtils.compareString("New host", reason)) {
			return "新的宿主";
		} else if (CommenUtils.compareString("New pathogen", reason)) {
			return "新的致病源";
		} else if (CommenUtils.compareString("New strain", reason)) {
			return "新的血清型";
		} else if (CommenUtils.compareString("New strain in the country", reason)) {
			return "该国发生某种疫病新的血清型";
		} else if (CommenUtils.compareString("Recurrence", reason)) {
			return "再次发生";
		} else if (CommenUtils.compareString("Unexpected change or increase", reason)) {
			return "发生预料之外的变化及增加";
		} else if (CommenUtils.compareString("Unusual host", reason)) {
			return "不常见的宿主";
		} else {
			return "发生预料之外的变化及增加";
		}
	}

}
