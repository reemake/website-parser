import exception.BadArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parser.CarsDataParser;
import writer.ExcelFileWriter;

public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    private static boolean isArgsValid(String[] args) {
        if (Integer.parseInt(args[0]) < 0) {
            try {
                throw new BadArgumentException();
            } catch (BadArgumentException e) {
                logger.error("Number of pages can't be negative value!");
            }
            return false;
        }
        return true;
    }

    public static void main(String[] args) {

        if (isArgsValid(args)) {
            logger.info("Argument successfully validated: there are {} page(s) to parse", args[0]);
            ExcelFileWriter excelFileWriter = new ExcelFileWriter("src/main/resources");
            CarsDataParser carsDataParser = new CarsDataParser(excelFileWriter, Integer.parseInt(args[0]));
            logger.info("Starting parsing process...");
            carsDataParser.parse();
            logger.info("Parsing process is finished!");
        }
    }

}
