package writer;

import jxl.Workbook;
import jxl.write.*;

import java.io.File;

public class ExcelFileWriter {

    private String filepath;

    public ExcelFileWriter(String filepath) {
        this.filepath = filepath;
    }

    public void createExcelFile() {
        try {
            File currDir = new File(filepath);
            String path = currDir.getAbsolutePath();
            String fileLocation = path + "\\cars.xls";

            WritableWorkbook workbook = Workbook.createWorkbook(new File(fileLocation));
            WritableSheet sheet = workbook.createSheet("Лист 1", 0);

            WritableCellFormat headerFormat = new WritableCellFormat();
            WritableFont font
                    = new WritableFont(WritableFont.ARIAL, 16, WritableFont.BOLD);
            headerFormat.setFont(font);
            headerFormat.setBackground(Colour.LIGHT_BLUE);
            headerFormat.setWrap(true);

            workbook.write();

            Label headerLabel = new Label(0, 0, "ID", headerFormat);
            sheet.addCell(headerLabel);

            headerLabel = new Label(1, 0, "Марка", headerFormat);
            sheet.addCell(headerLabel);

            headerLabel = new Label(2, 0, "Год выпуска", headerFormat);
            sheet.addCell(headerLabel);

            headerLabel = new Label(3, 0, "Тип топлива", headerFormat);
            sheet.addCell(headerLabel);

            headerLabel = new Label(4, 0, "Тип КПП", headerFormat);
            sheet.addCell(headerLabel);

            headerLabel = new Label(5, 0, "Локация продажи", headerFormat);
            sheet.addCell(headerLabel);

            headerLabel = new Label(6, 0, "Цена", headerFormat);
            sheet.addCell(headerLabel);

            headerLabel = new Label(7, 0, "URL", headerFormat);
            sheet.addCell(headerLabel);

            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
