package com.ncepulcy.battery_monitor.util;
import java.io.FileOutputStream;
import java.util.List;
import javax.servlet.ServletOutputStream;

import com.ncepulcy.battery_monitor.entity.BatterySingleTest;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;


/**
 * excel工具类
 * @author
 *
 */
public class ExportUtil {

    /**
     * 将用户的信息导入到excel文件中去
     * @param userList 用户列表
     * @param out 输出表
     */
    public static void exportUserExcel(List<BatterySingleTest> userList, FileOutputStream out)
    {
        try{
            //1.创建工作簿
            HSSFWorkbook workbook = new HSSFWorkbook();
            //1.1创建合并单元格对象
            CellRangeAddress callRangeAddress = new CellRangeAddress(0,0,0,4);//起始行,结束行,起始列,结束列
            //1.2头标题样式
            HSSFCellStyle headStyle = createCellStyle(workbook,(short)16);
            //1.3列标题样式
            HSSFCellStyle colStyle = createCellStyle(workbook,(short)13);
            //2.创建工作表
            HSSFSheet sheet = workbook.createSheet("充放电信息表");
            //2.1加载合并单元格对象
            sheet.addMergedRegion(callRangeAddress);
            //设置默认列宽
            sheet.setDefaultColumnWidth(25);
            //3.创建行
            //3.1创建头标题行;并且设置头标题
            HSSFRow row = sheet.createRow(0);
            HSSFCell cell = row.createCell(0);

            //加载单元格样式
            cell.setCellStyle(headStyle);
            cell.setCellValue("电池充放电信息");

            //3.2创建列标题;并且设置列标题
            HSSFRow row2 = sheet.createRow(1);
            String[] titles = {"组ID","单体ID","起始电压","终止电压","串号"};
            for(int i=0;i<titles.length;i++)
            {
                HSSFCell cell2 = row2.createCell(i);
                //加载单元格样式
                cell2.setCellStyle(colStyle);
                cell2.setCellValue(titles[i]);
            }


            //4.操作单元格;将用户列表写入excel
            if(userList != null)
            {
                for(int j=0;j<userList.size();j++)
                {
                    //创建数据行,前面有两行,头标题行和列标题行
                    HSSFRow row3 = sheet.createRow(j+2);
                    HSSFCell cell1 = row3.createCell(0);
                    cell1.setCellValue(userList.get(j).getGroupID());
                    HSSFCell cell2 = row3.createCell(1);
                    cell2.setCellValue(userList.get(j).getBatteryID());
                    HSSFCell cell3 = row3.createCell(2);
                    cell3.setCellValue(userList.get(j).getStartU());
                    HSSFCell cell4 = row3.createCell(3);
                    cell4.setCellValue(userList.get(j).getEndU());
                    HSSFCell cell5 = row3.createCell(4);
                    cell5.setCellValue(userList.get(j).getSerialNumber());
                }
            }
            //5.输出
            workbook.write(out);
            workbook.close();
//            FileOutputStream fileOutputStream = new FileOutputStream("C:/Users/cculi/Desktop/user.xls");//指定路径与名字和格式
//            workbook.write(fileOutputStream);//将数据写出去
//            fileOutputStream.close();//关闭输出流
            //out.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param workbook
     * @param fontsize
     * @return 单元格样式
     */
    private static HSSFCellStyle createCellStyle(HSSFWorkbook workbook, short fontsize) {
        // TODO Auto-generated method stub
        HSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);//水平居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        //创建字体
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints(fontsize);
        //加载字体
        style.setFont(font);
        return style;
    }
}
