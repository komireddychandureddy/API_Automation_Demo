package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @Author Chandu
 * @Date 15-Nov-2018
 */
public class ExcelTestDataReader {
	public LinkedList<Object[]> getRowDataMap(String inputFile, String sheetName) {
		int count = 0;
		Sheet sheet = null;
		Map<String, String> rowdatamap = null;
		FileInputStream fis = null;
		Workbook workbook = null;
		final LinkedList<Object[]> dataBeans = new LinkedList<Object[]>();

		// Creating index map
		Map<String, Integer> index = new HashMap<String, Integer>();

		try {
			fis = new FileInputStream(inputFile);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheet(sheetName);
			workbook.close();

			// mapping column index with column name.
			Row firstRow = sheet.getRow(0);
			for (Cell cell : firstRow) {
				index.put(cell.getStringCellValue(), count);
				count++;
			}

			//get total rows count present in sheet
			int rowCount = sheet.getLastRowNum();
			//running for loop for each excel row and storing values in map
			for (int iCounter = 1; iCounter <= rowCount; iCounter++) {
				//initialize excel row map
				rowdatamap = new HashMap<String, String>();
				Row rowData = sheet.getRow(iCounter);
				for (String key : index.keySet()) {
					int columnnum = (Integer) index.get(key);
					if(rowData.getCell(columnnum)==null){
						rowdatamap.put(key,null);
					}
					else{
						
						String cellValue = rowData.getCell(columnnum).toString();
						if(cellValue.contains("UNIQUE")){
							cellValue = getUNiqueData(cellValue);
						}
						rowdatamap.put(key,cellValue );
					}
					
				}
				dataBeans.add(new Object[] { rowdatamap });
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dataBeans;

	}
	
	public String getUNiqueData(String cellValue){
		String randomValue =  System.currentTimeMillis()+"";
		return cellValue.replace("UNIQUE",randomValue);
	}
}

