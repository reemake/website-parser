import writer.ExcelFileWriter;

public class Application {

    public static void main(String[] args) {
        ExcelFileWriter excelFileWriter = new ExcelFileWriter("src/main/resources");
        excelFileWriter.createExcelFile();
    }

}
