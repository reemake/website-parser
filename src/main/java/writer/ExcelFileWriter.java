package writer;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parser.CarsDataParser;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.Set;

public class ExcelFileWriter {

    private static final Logger logger = LoggerFactory.getLogger(ExcelFileWriter.class);
    private String filePath;
    private XSSFWorkbook workbook;
    private XSSFSheet spreadsheet;

    public ExcelFileWriter(String filePath) {
        this.filePath = filePath;
        this.workbook = new XSSFWorkbook();
        this.spreadsheet = workbook.createSheet("Лист 1");
    }

    public void write(Map<Integer, String[]> carsData) {
        try {

            // setup custom width of columns
            spreadsheet.setDefaultColumnWidth(27);

            // setup cell-style for header cells
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

            // saving parsed data into 'cars.xls' file
            File currDir = new File(filePath);
            String path = currDir.getAbsolutePath();
            String fileLocation = path + "\\cars.xls";
            FileOutputStream out = new FileOutputStream(fileLocation);

            logger.info("Writing parsed data into a file with path {}", fileLocation);

            workbook.write(out);
            out.close();

        } catch (Exception e) {
            logger.error("Writing to file has been failed");
        }
        logger.info("Writing to file successfully finished");
    }
}
