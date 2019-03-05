package cn.com.eship.utils;

import cn.com.eship.model.Words;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Repository
public class ExportExcelUtil {

    public void exportExcel(String fileName,String sheetName,String[] headers,List<Words> data, HttpServletResponse res) {
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet(sheetName);
            HSSFCellStyle headStyle = setHeadStyle(workbook);
            HSSFCellStyle bodyStyle = setBodyStyle(workbook);
            sheet.setDefaultColumnWidth(20);
            HSSFRow row = sheet.createRow(0);
            row.setHeightInPoints(30);
            for (int i = 0; i < headers.length; i++) {
                HSSFCell cell = row.createCell(i);
                cell.setCellStyle(headStyle);
                cell.setCellValue(headers[i]);
            }
            int rowNum = 1 ;
            for(Words word : data){
                row = sheet.createRow(rowNum++);
                row.setHeightInPoints(25);
                String[] strings = new String[3];
                String kindName = word.getKindDic().getKindName();
                String level = String.valueOf(word.getKindDic().getLevel());
                String name = word.getWord();
                strings[0]=(kindName!=null)?kindName:"null";
                strings[1]= (level!=null)?level:"null";
                strings[2]= (level!=null&&name!=null&&!level.equals(name))?name:"null";
                for(int cellNum = 0;cellNum<=strings.length-1;cellNum++){
                    HSSFCell cell = row.createCell(cellNum);
                    cell.setCellValue(strings[cellNum]);
                    cell.setCellStyle(bodyStyle);
                }
            }
            fileName = new String(fileName.getBytes("utf-8"), "iso8859-1");
            res.setContentType("application/vnd.ms-excel; charset=utf-8");
            res.setHeader("Content-Disposition","attachment;filename="+ fileName + ".xls");
            res.setCharacterEncoding("utf-8");
            workbook.write(res.getOutputStream());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static HSSFCellStyle setHeadStyle(HSSFWorkbook workbook) {

        HSSFCellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(HSSFColor.BLUE_GREY.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setFontHeightInPoints((short) 18);
        font.setFontName("黑体");
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        style.setFont(font);
        return style;
    }

    public static HSSFCellStyle setBodyStyle(HSSFWorkbook workbook) {

        HSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setFontHeightInPoints((short) 14);
        font.setFontName("微软雅黑");
        style.setFont(font);
        return style;
    }
}
