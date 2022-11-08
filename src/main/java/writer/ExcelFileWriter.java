package writer;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.Set;

public class ExcelFileWriter {

    private String filepath;
    private XSSFWorkbook workbook;
    private XSSFSheet spreadsheet;

    public ExcelFileWriter(String filepath) {
        this.filepath = filepath;
        this.workbook = new XSSFWorkbook();
        this.spreadsheet = workbook.createSheet("Лист 1");
    }

    public void write(Map<Integer, String[]> carsData) {
        try {

            // setup cell-style for header cells
            spreadsheet.setDefaultColumnWidth(27);
            XSSFCellStyle style = workbook.createCellStyle();
            style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
            style.setFillPattern(FillPatternType.FINE_DOTS);

            // fill header row
            XSSFRow row = spreadsheet.createRow(0);
            String[] header = { "Марка", "Год выпуска", "Тип топлива", "Тип КПП", "Локация", "Цена (руб.)", "URL" };
            int headerCellCounter = 0;
            for (String line : header) {
                Cell cell = row.createCell(headerCellCounter++);
                cell.setCellValue(line);
                cell.setCellStyle(style);
            }

            // fill main rows
            Set<Integer> keys = carsData.keySet();
            int rowCounter = 1;
            for (Integer key : keys) {

                row = spreadsheet.createRow(rowCounter++);
                String[] data = carsData.get(key);
                int mainCellCounter = 0;

                for (String line : data) {
                    Cell cell = row.createCell(mainCellCounter++);
                    cell.setCellValue(line);
                }
            }

            // writing parsed data into 'cars.xls' file
            File currDir = new File(filepath);
            String path = currDir.getAbsolutePath();
            String fileLocation = path + "\\cars.xls";
            FileOutputStream out = new FileOutputStream(fileLocation);

            workbook.write(out);
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
