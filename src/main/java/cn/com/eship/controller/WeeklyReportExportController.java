package cn.com.eship.controller;

import cn.com.eship.service.WeeklyReportExportService;
import cn.com.eship.utils.TimeUtils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/weeklyReportExport")
public class WeeklyReportExportController {

	private final WeeklyReportExportService weeklyReportExportService;

	@Autowired
	public WeeklyReportExportController(WeeklyReportExportService weeklyReportExportService) {
		this.weeklyReportExportService = weeklyReportExportService;
	}

	@RequestMapping("/toReportPage")
	public String toDailyReportPage() {
		return "weeklyReportExportPage";
	}

	@RequestMapping("importSvgString")
	public void findRecentOutbreakRegion(String startDateCondition, String svgString, String svgDiseaseDetailBar,
			String svgDiseaseHistoryBar, String svgReportReasonPie, HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		String startDate;
		String endDate;
		Date date = new Date();
		if (StringUtils.isNotEmpty(startDateCondition)) {
			date = new SimpleDateFormat("MM/dd/yyyy").parse(startDateCondition);
		}
		Map<String, String> dateMap = TimeUtils.getFirstAndLastDayOfThisWeek(date);
		startDate = dateMap.get("beginDate");
		endDate = dateMap.get("endDate");
		response.reset();
		response.setContentType("application/msword");
		StringBuilder fileName = new StringBuilder("疫情周报(").append(startDate).append("至").append(endDate)
				.append(").docx");
		byte[] bytes;
		OutputStream out = null;
		try {
			if (request.getHeader("User-Agent").toLowerCase().contains("firefox")) {
				response.setHeader("Content-Disposition",
						new String(("attachment;filename=\"" + fileName + "\"").getBytes("utf-8"), "iso8859-1"));
			} else {
				response.setHeader("Content-Disposition",
						new String(("attachment;filename=" + fileName.toString()).getBytes("utf-8"), "iso8859-1"));
			}

			response.setCharacterEncoding("utf-8");
			bytes = weeklyReportExportService.buildWordDoc(startDateCondition, svgString, svgDiseaseDetailBar,
					svgDiseaseHistoryBar, svgReportReasonPie);
			response.addHeader("Content-Length", "" + bytes.length);
			out = response.getOutputStream();
			if (bytes.length > 0) {
				out.write(bytes);
				out.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@RequestMapping("importSvgStringTwo")
	public void findRecentOutbreakRegion2(String startDateCondition, String svgString, String svgDiseaseDetailBar,
			String svgDiseaseHistoryBar, String svgReportReasonPie, HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		String startDate;
		String endDate;
		Date date = new Date();
		if (StringUtils.isNotEmpty(startDateCondition)) {
			date = new SimpleDateFormat("MM/dd/yyyy").parse(startDateCondition);
		}
		Map<String, String> dateMap = TimeUtils.getFirstAndLastDayOfThisWeek(date);
		startDate = dateMap.get("beginDate");
		endDate = dateMap.get("endDate");
		response.reset();
		response.setContentType("application/msword");
		// StringBuilder fileName = new
		// StringBuilder("疫情周报(").append(startDate).append("至").append(endDate).append(").docx");
		byte[] bytes = weeklyReportExportService.buildWordDoc(startDateCondition, svgString, svgDiseaseDetailBar,
				svgDiseaseHistoryBar, svgReportReasonPie);
		String name = UUID.randomUUID().toString().replace("-", "");
		File file = new File("/usr/local/tomcat8/webapps/ROOT/zbs/" + name);
		FileUtils.writeByteArrayToFile(file, bytes);
		response.getOutputStream().write(name.getBytes());

	}

	@RequestMapping("findDiseaseClassData")
	public void findDiseaseClassData(String startDateCondition, HttpServletResponse response) throws Exception {
		response.getOutputStream()
				.write(weeklyReportExportService.findDiseaseClassData(startDateCondition).getBytes("utf-8"));
	}

	@RequestMapping("findDiseaseDetailData")
	public void findDiseaseDetailData(String startDateCondition, HttpServletResponse response) throws Exception {
		response.getOutputStream()
				.write(weeklyReportExportService.findDiseaseDetailData(startDateCondition).getBytes("utf-8"));
	}

	@RequestMapping("findContinentDiseaseHistoryData")
	public void findContinentDiseaseHistoryData(String startDateCondition, HttpServletResponse response)
			throws Exception {
		response.getOutputStream()
				.write(weeklyReportExportService.findDiseaseHistoryData(startDateCondition).getBytes("utf-8"));
	}

	@RequestMapping("findReportReasonData")
	public void findReportReasonData(String startDateCondition, HttpServletResponse response) throws Exception {
		response.getOutputStream()
				.write(weeklyReportExportService.findReportReasonData(startDateCondition).getBytes("utf-8"));
	}

}
