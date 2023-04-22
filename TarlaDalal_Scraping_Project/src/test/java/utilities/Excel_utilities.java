package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel_utilities {

	
	static XSSFWorkbook workbook;
	static XSSFSheet sheet;
	public FileInputStream fis = null;
	File file;
	String excelPath;
	
	public Excel_utilities(String excelPath,String sheetName)
	{
		try {
			file=new File(excelPath);
			fis = new FileInputStream(file);
		workbook = new XSSFWorkbook(fis);
		sheet = workbook.getSheet(sheetName);
		}catch (Exception e) { 
			System.out.println("e.getMessage()");
			e.getCause();
			e.printStackTrace();
		}
	}
	public static int GetRowCount()
	{
		int rowcount=0;
		try {
		rowcount= sheet.getPhysicalNumberOfRows();
		//System.out.println("Number of rows"+rowcount);
		}catch (Exception e) { 
			System.out.println("e.getMessage()");
			e.getCause();
			e.printStackTrace();
		}
		return rowcount;
	}
	
	public static int GetColumnCount()
	{
		int colcount=0;
		try {
		colcount= sheet.getRow(0).getPhysicalNumberOfCells();
		//System.out.println("Number of columns"+colcount);
		}catch (Exception e) { 
			System.out.println("e.getMessage()");
			e.getCause();
			e.printStackTrace();
		}
		return colcount;
	}
	
	public static String getCellData(int rownum,int colnum)
	{
		String cellData=null;
		try {
		cellData= sheet.getRow(rownum).getCell(colnum).getStringCellValue();
		//System.out.println(cellData);
		}catch (Exception e) { 
		System.out.println("e.getMessage()");
		e.getCause();
		e.printStackTrace();
	}
		return cellData;
		 
	}
	public void setCellData(String sheetName,String cellData,int rownum,int colnum)
	{
		try {
		sheet.getRow(rownum).createCell(colnum).setCellValue(cellData);
		FileOutputStream fos=new FileOutputStream(new File(excelPath));
		workbook.write(fos);
		workbook.close();
		}catch (Exception e) {
			System.out.println("e.getMessage()");
			e.getCause();
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args)
	{
		//GerRowCount();
		getCellData(1,0);
	}
}
