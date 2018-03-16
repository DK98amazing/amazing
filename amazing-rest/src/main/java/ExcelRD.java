import com.mapper.vo.User;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import java.io.*;
import java.util.*;

public class ExcelRD {
    static String path;
    static String path2;
    static FileInputStream fileInputStream;
    static FileOutputStream fileInputStream2;
    static {
        path = "C:\\Users\\HZ08900\\Desktop\\Demo.xls";
        path2 = "C:\\Users\\HZ08900\\Desktop\\Demo2.xls";
        File excel = new File(path);
        File excel2 = new File(path2);
        try {
            if (!excel2.exists()) {
                excel2.createNewFile();
            }
            fileInputStream = new FileInputStream(excel);
            fileInputStream2 = new FileOutputStream(excel2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void runA() {
        try {
            String columns[] = {"name","age","score"};
            List<Map<String,String>> list = new ArrayList<Map<String,String>>();
            Workbook hssfWorkbook = new HSSFWorkbook(fileInputStream);
            HSSFSheet sheet = (HSSFSheet) hssfWorkbook.getSheetAt(0);
            int rownum = sheet.getPhysicalNumberOfRows(); //最大的行数
            HSSFRow row0 = sheet.getRow(0);  //第一行
            int colnum = row0.getPhysicalNumberOfCells();  //最大列数
            for (int i=1; i<rownum; i++) {
                Map<String,String> map = new LinkedHashMap<String,String>();
                for (int j=0; j<colnum; j++) {
                    HSSFRow row = sheet.getRow(i);
                    Cell cell = row.getCell(j);
                    map.put(String.valueOf(columns[j]), String.valueOf(cell.getNumericCellValue()));
                }
                list.add(map);
            }
            for (Map<String,String> map : list) {
                for (Map.Entry<String,String> entry : map.entrySet()) {
                    System.out.print(entry.getKey()+":"+entry.getValue()+",");
                }
                System.out.println();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //TestMain.runB()运行。
    public void runB(List<User> users) {
        try {
            Workbook workbook = new HSSFWorkbook();

            HSSFSheet sheet = (HSSFSheet) workbook.createSheet("用户表一");
            HSSFRow hssfRow = sheet.createRow(0);

            Class<User> userClass = User.class;
            for (int i=0; i<userClass.getDeclaredFields().length; i++) {
                HSSFCell cell = hssfRow.createCell(i);
                cell.setCellValue(userClass.getDeclaredFields()[i].getName());
            }

            for (int i = 0; i < users.size(); i++) {
                HSSFRow row1 = sheet.createRow(i + 1);
                User user = users.get(i);
                //创建单元格设值
                row1.createCell(0).setCellValue(Integer.valueOf(user.getId()));
                row1.createCell(1).setCellValue(user.getUsername());
                row1.createCell(2).setCellValue(user.getSex());
                row1.createCell(3).setCellValue(covert(user.getBirthday()));
                row1.createCell(4).setCellValue(user.getAddress());
            }

            //TODO  设置暂未生效。
            /*//设置字体
            HSSFFont font = (HSSFFont) workbook.createFont();
            font.setBold(true);
            font.setFontHeightInPoints((short) 16);
            font.setFontName("黑体");

            HSSFCellStyle setBorder = (HSSFCellStyle) workbook.createCellStyle();
            setBorder.setFillForegroundColor((short) 13);// 设置背景色*/

            workbook.write(fileInputStream2);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileInputStream2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String covert(Date date) {
        int year = date.getYear() + 1900;
        int month = date.getMonth();
        int day = date.getDay();
        return year + "-" + month + "-" + day;
    }
}
