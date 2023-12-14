package testData;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataMapper {

	public Object[][] getPagesData(String TestcaseID, String sheetName) throws InvalidFormatException, IOException {

		HashMap<String, String> quoteeachrowdata = new HashMap<>();
		HashMap<String, String> proposaleachrowdata = new HashMap<>();

		HashMap<String, String> map = null;

		XSSFWorkbook workbook = new XSSFWorkbook(
				new File(System.getProperty("user.dir") + "\\src\\main\\java\\TestData\\TestDataExcel.xlsx"));
		Iterator<Sheet> sheetIt = workbook.sheetIterator();

		while (sheetIt.hasNext()) {
			Sheet sheet = sheetIt.next();
			if (sheet.getSheetName().equalsIgnoreCase(sheetName)) {

				Iterator<Row> rowIt = sheet.rowIterator();

				Row firstrow = rowIt.next();
				while (rowIt.hasNext()) {
					Row datarow = rowIt.next();
					int size = firstrow.getLastCellNum();

					if (datarow.getCell(0).getStringCellValue().equalsIgnoreCase(TestcaseID)
							&& sheet.getSheetName().equalsIgnoreCase(sheetName)) {

						for (int i = 1; i < size; i++) {

							if ((datarow.getCell(i).getCellType() != CellType.STRING)) {
								DataFormatter formatnumerictostring = new DataFormatter();
								quoteeachrowdata.put(firstrow.getCell(i).getStringCellValue().trim(),
										formatnumerictostring.formatCellValue(datarow.getCell(i)).trim());

							}

							else {
								quoteeachrowdata.put(firstrow.getCell(i).getStringCellValue(),
										datarow.getCell(i).getStringCellValue());
							}
						}
						map = quoteeachrowdata;
					} else if (datarow.getCell(0).getStringCellValue().equalsIgnoreCase(TestcaseID)
							&& sheet.getSheetName().equalsIgnoreCase("ProposalPage")) {

						for (int i = 0; i < size; i++) {

							if ((datarow.getCell(i).getCellType() != CellType.STRING)) {
								DataFormatter formatnumerictostring = new DataFormatter();
								proposaleachrowdata.put(firstrow.getCell(i).getStringCellValue(),
										formatnumerictostring.formatCellValue(datarow.getCell(i)));

							}

							else {
								proposaleachrowdata.put(firstrow.getCell(i).getStringCellValue(),
										datarow.getCell(i).getStringCellValue());
							}
						}
						map = proposaleachrowdata;
					}

				}

			}

		}
		return new Object[][] { { map } };
	}
}