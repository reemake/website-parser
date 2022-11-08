package parser;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import writer.ExcelFileWriter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CarsDataParser {

    private static final Logger logger = LoggerFactory.getLogger(CarsDataParser.class);
    private ExcelFileWriter excelFileWriter;

    public CarsDataParser(ExcelFileWriter excelFileWriter) {
        this.excelFileWriter = excelFileWriter;
    }

    public void parse() {

        Map<Integer, String[]> carsData = new HashMap<>();

        for (int page = 1, carCount = 1; page <= 3; page++) {

            try {
                String url = (page == 1) ? "https://auto.drom.ru" : "https://auto.drom.ru/all/page" + page;

                logger.info("Trying to connect {}", url);
                Connection.Response response = Jsoup.connect(url)
                        .timeout(5000)
                        .execute();

                int statusCode = response.statusCode();
                if (statusCode == 200) {
                    logger.info("Successfully connected");
                    logger.info("Parsing...");
                    Document document = response.parse();

                    Elements cars = document.getElementsByClass("css-xb5nz8 ewrty961");
                    for (Element car : cars) {

                        logger.info("Car #{} is parsing...", carCount);
                        String[] brandAndYear = car
                                .select("[data-ftid='bull_title']")
                                .text()
                                .split(",");

                        String brand = brandAndYear[0];
                        String year = brandAndYear[1]
                                .substring(1);      // deleting unnecessary 'space' at the beginning of string

                        String fuelType = car
                                .select("[data-ftid='bull_description-item']")
                                .eq(1)
                                .text()
                                .replaceAll(".$", "");                    // deleting unnecessary ','

                        String transmissionType = car
                                .select("[data-ftid='bull_description-item']")
                                .eq(2)
                                .text()
                                .replaceAll(".$", "");                    // deleting unnecessary ','

                        String location = car
                                .select("[data-ftid='bull_location']")
                                .text();

                        String price = car
                                .select("[data-ftid='bull_price']")
                                .text();

                        String link = car
                                .select("a")
                                .attr("href");

                        carsData.put(
                                carCount,
                                new String[]{ brand, year, fuelType, transmissionType, location, price, link });

                        carCount++;
                    }
                }
            } catch (IOException e) {
                logger.error("Connection failed!");
                continue;
            }
            logger.info("Parsing of page {} successfully finished", page);
            excelFileWriter.write(carsData);
            logger.info("Writing parsed data of page {} successfully finished", page);
        }
        logger.info("Parsing process is finished!");
    }

}
