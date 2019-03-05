package cn.com.eship.utils.wordExportUtils;

import cn.com.eship.model.WordPicture;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class WorderToNewWordUtils {
    public static byte[] changWord(String inputUrl, Map<String, Object> textMap, List<List<String[]>> tableList) {
        CustomXWPFDocument document = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            document = new CustomXWPFDocument(POIXMLDocument.openPackage(inputUrl));
            List<XWPFParagraph> paragraphs = document.getParagraphs();
            processParagraphs(paragraphs, textMap, document);
//            WorderToNewWordUtils.changeText(document, textMap);
            WorderToNewWordUtils.changeTable(document,textMap,tableList);
            byteArrayOutputStream = new ByteArrayOutputStream();
            document.write(byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (document!=null){
                document.getPackage().revert();
            }
            if (byteArrayOutputStream != null){
                try {
                    byteArrayOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void processParagraphs(List<XWPFParagraph> paragraphList,Map<String, Object> param,CustomXWPFDocument doc){
        if(paragraphList != null && paragraphList.size() > 0){
            for(XWPFParagraph paragraph:paragraphList){
                List<XWPFRun> runs = paragraph.getRuns();
                for (XWPFRun run : runs) {
                    String text = run.text();
                    if(text != null){
                        for (Map.Entry<String, Object> entry : param.entrySet()) {
                            String key = entry.getKey();
                            if(text.contains(key)){
                                Object value = entry.getValue();
                                if (value instanceof String){
                                    text = text.replace(key, value.toString());
                                    run.setText(text,0);
                                } else if (value instanceof WordPicture){
                                    text = text.replace(key, "");
                                    run.setText(text,0);
                                    WordPicture wordPicture = (WordPicture)value;
                                    int width = wordPicture.getWidth();
                                    int height = wordPicture.getHeight();
                                    int picType = CustomXWPFDocument.PICTURE_TYPE_PNG;
                                    byte[] byteArray = wordPicture.getContent();
                                    ByteArrayInputStream byteInputStream = new ByteArrayInputStream(byteArray);
                                    try {
                                        String ind=doc.addPictureData(byteInputStream,picType);
                                        doc.createPicture(ind,picType, width , height,run);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void changeText(XWPFDocument document, Map<String, String> textMap){
        List<XWPFParagraph> paragraphs = document.getParagraphs();
        for (XWPFParagraph paragraph : paragraphs) {
            String text = paragraph.getText();
            System.out.println("--"+text);
            if(checkText(text)){
                List<XWPFRun> runs = paragraph.getRuns();
                for (XWPFRun run : runs) {
                    run.setText(changeValue(run.toString(), textMap),0);
                }
            }
        }

    }

    private static void changePic(XWPFDocument document){
        List<XWPFPictureData> pictures= document.getAllPictures();
        for (XWPFPictureData pictureData:pictures){
            System.out.println(pictureData);
        }
    }

    private static void changeTable(XWPFDocument document, Map<String, Object> textMap,
                                   List<List<String[]>> tableList){
        List<XWPFTable> tables = document.getTables();
        for (XWPFTable table : tables) {
            XWPFTableRow firstRow = table.getRow(0);
            List<XWPFTableCell> cells = firstRow.getTableCells();
            if (cells.size()==5){
                List<String[]> tl = tableList.get(0);
                if (tl!=null&&tl.size()>0) insertTable(table, tl);
            }else if (cells.size()==4){
                List<String[]> tl =tableList.get(1);
                if (tl!=null&&tl.size()>0) insertTable(table, tl);
            }else if (cells.size()==10){
                List<String[]> tl =tableList.get(2);
                if (tl!=null&&tl.size()>0) insertTable(table, tl);
            }
            System.out.println(table.getText());
            System.out.println(cells);

        }
    }


    private static void insertTable(XWPFTable table, List<String[]> tableList){
        for(int i = 0; i < tableList.size(); i++){
            XWPFTableRow row =table.createRow();
        }
        List<XWPFTableRow> rows = table.getRows();
        for(int i = 1; i < rows.size(); i++){
            XWPFTableRow newRow = table.getRow(i);
            List<XWPFTableCell> cells = newRow.getTableCells();
            for(int j = 0; j < cells.size(); j++){
                XWPFTableCell cell = cells.get(j);
                cell.setText(tableList.get(i-1)[j]);
            }
        }
    }

    private static boolean checkText(String text){
        boolean check  =  false;
        if(text.contains("$")){
            check = true;
        }
        return check;

    }


    private static String changeValue(String value, Map<String, String> textMap){
        Set<Map.Entry<String, String>> textSets = textMap.entrySet();
        for (Map.Entry<String, String> textSet : textSets) {
            String key = "${"+textSet.getKey()+"}";
            if(value.contains(key)){
                value = textSet.getValue();
            }
        }
        if(checkText(value)){
            value = "";
        }
        return value;
    }

    public  void mergeCellsHorizontal(XWPFTable table, int row, int fromCell, int toCell) {
        for (int cellIndex = fromCell; cellIndex <= toCell; cellIndex++) {
            XWPFTableCell cell = table.getRow(row).getCell(cellIndex);
            if ( cellIndex == fromCell ) {
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
            } else {
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
            }
        }
    }




}
